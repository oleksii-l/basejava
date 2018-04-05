package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.DataStreamUtil;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.basejava.util.DataStreamUtil.getOptionalValue;
import static ru.javawebinar.basejava.util.DataStreamUtil.handlingConsumerWrapper;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, Section> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                dos.writeUTF(entry.getValue().getClass().getName());
                dos.writeUTF(entry.getKey().name());

                writeSectionTo(dos, entry.getValue());

            }
        }
    }

    private void writeSectionTo(DataOutputStream dos, Section section) throws IOException {

        String className = section.getClass().getName();
        dos.writeUTF(className);

        if (className.equals(ListSection.class.getName())) {
            ListSection listSection = (ListSection) section;
            dos.writeInt(listSection.getItems().size());
            listSection.getItems().forEach(handlingConsumerWrapper(dos::writeUTF, IOException.class));
        } else if (className.equals(OrganizationSection.class.getName())) {
            OrganizationSection orgSection = (OrganizationSection) section;
            dos.writeInt(orgSection.getOrganizations().size());
            orgSection.getOrganizations().forEach(handlingConsumerWrapper(org -> {
                dos.writeUTF(org.getHomePage().getName());
                String url = org.getHomePage().getUrl();
                dos.writeUTF(getOptionalValue(url));
                dos.writeInt(org.getPositions().size());
                org.getPositions().forEach(handlingConsumerWrapper(p -> {
                    dos.writeUTF(p.getStartDate().toString());
                    dos.writeUTF(p.getEndDate().toString());
                    dos.writeUTF(p.getTitle());
                    String desc = p.getDescription();
                    dos.writeUTF(getOptionalValue(desc));
                }, IOException.class));
            }, IOException.class));

        } else if (className.equals(TextSection.class.getName())) {
            TextSection textSection = (TextSection) section;
            dos.writeUTF(textSection.getContent());

        } else {
            throw new IOException("Unsupported class: " + className);
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sectionSize = dis.readInt();
            for (int i = 0; i < sectionSize; i++) {
                String className = dis.readUTF();
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Class clazz;
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    throw new IOException("Could not instantiate class: " + className);
                }
                Section section = (Section) clazz.newInstance();
                section = readSectionFrom(dis, section);
                resume.addSection(sectionType, section);
            }

            return resume;
        } catch (IllegalAccessException e) {
            throw new IOException("Read operation failed", e);
        } catch (InstantiationException e) {
            throw new IOException("Read operation failed", e);
        }
    }


    private Section readSectionFrom(DataInputStream dis, Section section) throws IOException {

        String className = section.getClass().getName();

        if (className.equals(ListSection.class.getName())) {
            ListSection listSection = (ListSection) section;
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                listSection.getItems().add(dis.readUTF());
            }

            return listSection;
        } else if (className.equals(OrganizationSection.class.getName())) {
            OrganizationSection orgSection = (OrganizationSection) section;
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                String name = dis.readUTF();
                String url = dis.readUTF();
                Link link = new Link(name, DataStreamUtil.getOptionalValue(url));
                int posSize = dis.readInt();
                List<Organization.Position> positions = new ArrayList<>(posSize);
                for (int y = 0; y < posSize; y++) {
                    LocalDate startDate = LocalDate.parse(dis.readUTF());
                    LocalDate endDate = LocalDate.parse(dis.readUTF());
                    String title = dis.readUTF();
                    String desc = dis.readUTF();
                    Organization.Position position = new Organization.Position(
                            startDate, endDate, title, DataStreamUtil.getOptionalValue(desc)
                    );
                    positions.add(position);
                }
                orgSection.getOrganizations().add(new Organization(link, positions));
            }

            return orgSection;
        } else if (className.equals(TextSection.class.getName())) {
            TextSection textSection = (TextSection) section;
            textSection.setContent(dis.readUTF());

            return textSection;
        } else {
            throw new IOException("Unsupported class: " + className);
        }

    }
}
