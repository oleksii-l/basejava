package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * gkislin
 * 19.07.2016
 */
@XmlAccessorType(XmlAccessType.FIELD)
abstract public class Section implements Serializable {
    public abstract void writeTo(DataOutputStream dos) throws IOException;

    public abstract void readFrom(DataInputStream dis) throws IOException;
}