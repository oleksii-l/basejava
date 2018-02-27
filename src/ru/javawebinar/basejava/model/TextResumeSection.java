package ru.javawebinar.basejava.model;

public class TextResumeSection extends ResumeSection {
    private final String text;

    public TextResumeSection(ResumeSectionType type, String text) {
        super(type);

        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "TextResumeSection{" +
                "text='" + text + '\'' +
                '}';
    }
}
