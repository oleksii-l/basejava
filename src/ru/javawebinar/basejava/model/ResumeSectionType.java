package ru.javawebinar.basejava.model;

public enum ResumeSectionType {

    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private final String title;

    ResumeSectionType(String s) {
        this.title = s;
    }

    public String getTitle() {
        return title;
    }
}
