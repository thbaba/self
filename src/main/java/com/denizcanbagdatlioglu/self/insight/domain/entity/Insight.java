package com.denizcanbagdatlioglu.self.insight.domain.entity;

import com.denizcanbagdatlioglu.self.common.domain.valueobject.ID;

import java.time.LocalDateTime;

public class Insight {

    private static final int MAX_TITLE_LENGTH = 32;

    private static final int MAX_CONTENT_LENGTH = 512;

    private final ID id;

    private final String title;

    private final LocalDateTime date;

    private final InsightAuthor author;

    private final String content;

    public ID id() {
        return id;
    }

    public String title() {
        return title;
    }

    public LocalDateTime date() {
        return date;
    }

    public InsightAuthor author() {
        return author;
    }

    public String content() {
        return content;
    }

    public boolean checkTitleLength() {
        return title.length() < MAX_TITLE_LENGTH;
    }

    public boolean checkContentLength() {
        return content.length() < MAX_CONTENT_LENGTH;
    }

    public static final class InsightBuilder {
        private ID id;
        private String title;
        private LocalDateTime date;
        private InsightAuthor author;
        private String content;

        private InsightBuilder() {
        }

        public InsightBuilder id(ID id) {
            this.id = id;
            return this;
        }

        public InsightBuilder id(String id) {
            this.id = ID.of(id);
            return this;
        }

        public InsightBuilder title(String title) {
            this.title = title;
            return this;
        }

        public InsightBuilder date(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public InsightBuilder author(InsightAuthor author) {
            this.author = author;
            return this;
        }

        public InsightBuilder content(String content) {
            this.content = content;
            return this;
        }

        public Insight build() {
            return new Insight(this);
        }
    }

    private Insight(InsightBuilder builder) {
        id = builder.id;
        title = builder.title;
        date = builder.date;
        author = builder.author;
        content = builder.content;
    }

    public static InsightBuilder builder() {
        return new InsightBuilder();
    }
}
