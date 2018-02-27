package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.Arrays;

import static ru.javawebinar.basejava.model.ContactType.*;
import static ru.javawebinar.basejava.model.ResumeSectionType.*;

public class MainResume {
    public static void main(String[] args) {
        Resume oleksii = new Resume("Oleksii Leshchenko");

        /*  Contacts  */
        oleksii.addContact(PHONE, "+38067-277-87-99");
        oleksii.addContact(EMAIL, "oleksii.leshchenko@gmail.com");
        oleksii.addContact(GITHUB, "https://github.com/oleksii-l");
        oleksii.addContact(SKYPE, "leshchenko_alexey");

        /* Позиция */
        oleksii.addSection(OBJECTIVE, new TextResumeSection(OBJECTIVE, "Java developer"));

        /* Личные качества */
        oleksii.addSection(PERSONAL, new TextResumeSection(PERSONAL, "Аналитический склад ума, сильная логика, креативность, инициативность."));

        /* Достижения */
        oleksii.addSection(ACHIEVEMENT, new MultiTextResumeSection(ACHIEVEMENT
                , Arrays.asList(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP)."
                , "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike"
                , "Реализация протоколов по приему платежей всех основных платежных системы России"
        )));

        /* Квалификация */
        oleksii.addSection(QUALIFICATIONS, new MultiTextResumeSection(QUALIFICATIONS
                , Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2"
                , "Version control: Subversion, Git, Mercury, ClearCase, Perforce"
                , "MySQL, SQLite, MS SQL, HSQLDB"
        )));

        /* Опыт работы */
        DateRangeResumeSection.Entry entry1, entry2;
        entry1 = new DateRangeResumeSection.Entry();
        entry1.entryName = "Java Online Projects";
        entry1.entryUrl = "http://javaops.ru/";
        entry1.title = "Автор проекта";
        entry1.description = "Создание, организация и проведение Java онлайн проектов и стажировок.";
        entry1.startDate = LocalDate.of(2013, 10, 1);

        entry2 = new DateRangeResumeSection.Entry();
        entry2.entryName = "Wrike";
        entry2.entryUrl = "https://www.wrike.com/";
        entry2.title = "Старший разработчик (backend)";
        entry2.description = "Проектирование и разработка онлайн платформы управления проектами Wrike";
        entry2.startDate = LocalDate.of(2014, 10, 1);
        entry2.endDate = LocalDate.of(2016, 1, 1);

        oleksii.addSection(EXPERIENCE, new DateRangeResumeSection(EXPERIENCE
                , Arrays.asList(entry1, entry2)));

        /* Образование */
        entry1 = new DateRangeResumeSection.Entry();
        entry1.entryName = "Coursera";
        entry1.entryUrl = "https://www.coursera.org/course/progfun";
        entry1.title = "\"Functional Programming Principles in Scala\" by Martin Odersky";
        entry1.startDate = LocalDate.of(2013, 3, 1);
        entry1.endDate = LocalDate.of(2013, 5, 1);

        entry2 = new DateRangeResumeSection.Entry();
        entry2.entryName = "Luxoft";
        entry2.entryUrl = "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366";
        entry2.title = "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"";
        entry2.startDate = LocalDate.of(2011, 3, 1);
        entry2.endDate = LocalDate.of(2011, 4, 1);
        oleksii.addSection(EDUCATION, new DateRangeResumeSection(EDUCATION, Arrays.asList(entry1, entry2)));

        System.out.println(oleksii);
    }


}
