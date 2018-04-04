package ru.javawebinar.basejava.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.basejava.util.DataStreamUtil.handlingConsumerWrapper;

/**
 * gkislin
 * 14.07.2016
 */
public class ListSection extends Section {

    private static final long serialVersionUID = 1L;

    private List<String> items = new ArrayList<>();

    public ListSection() {
    }

    public ListSection(String... items) {
        this(Arrays.asList(items));
    }

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return items.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection that = (ListSection) o;

        return items.equals(that.items);

    }

    @Override
    public int hashCode() {
        return items.hashCode();
    }

    @Override
    public void writeTo(DataOutputStream dos) throws IOException {
        dos.writeInt(items.size());
        items.forEach(handlingConsumerWrapper(dos::writeUTF, IOException.class));
    }

    @Override
    public void readFrom(DataInputStream dis) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            items.add(dis.readUTF());
        }
    }
}

