package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.List;

public class DateRangeResumeSection extends ResumeSection {
    private final List<Entry> entries;

    public DateRangeResumeSection(ResumeSectionType type, List<Entry> entries) {
        super(type);

        this.entries = entries;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void addEntry(Entry e) {
        entries.add(e);
    }

    public static class Entry {
        public String entryName;
        public String entryUrl;
        public LocalDate startDate;
        public LocalDate endDate;
        public String title;
        public String description;

        @Override
        public String toString() {
            return "Entry{" +
                    "entryName='" + entryName + '\'' +
                    ", entryUrl='" + entryUrl + '\'' +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DateRangeResumeSection{" +
                "entries=" + entries +
                '}';
    }
}
