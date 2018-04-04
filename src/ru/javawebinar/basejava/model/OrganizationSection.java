package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.DataStreamUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DataStreamUtil.getOptionalValue;
import static ru.javawebinar.basejava.util.DataStreamUtil.handlingConsumerWrapper;

/**
 * gkislin
 * 19.07.2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends Section {
    private static final long serialVersionUID = 1L;

    private List<Organization> organizations = new ArrayList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);

    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }

    @Override
    public void writeTo(DataOutputStream dos) throws IOException {
        dos.writeInt(organizations.size());
        organizations.forEach(handlingConsumerWrapper(org -> {
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
    }

    @Override
    public void readFrom(DataInputStream dis) throws IOException {
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
            organizations.add(new Organization(link, positions));
        }
    }

}