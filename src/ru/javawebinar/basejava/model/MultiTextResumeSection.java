package ru.javawebinar.basejava.model;

import java.util.List;

public class MultiTextResumeSection extends ResumeSection {
    private final List<String> textLines;

    public MultiTextResumeSection(ResumeSectionType type, List<String> textLines) {
        super(type);

        this.textLines = textLines;
    }

    public List<String> getTextLines() {
        return textLines;
    }

    @Override
    public String toString() {
        return "MultiTextResumeSection{" +
                "textLines=" + textLines +
                '}';
    }
}
