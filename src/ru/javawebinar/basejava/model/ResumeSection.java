package ru.javawebinar.basejava.model;

public abstract class ResumeSection {

    private final ResumeSectionType type;

    public ResumeSection(ResumeSectionType type) {
        this.type = type;
    }

    public ResumeSectionType getType() {
        return type;
    }
}
