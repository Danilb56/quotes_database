package com.example.demo.models;

import java.sql.Date;

public class Quote {
    private int id;
    private String text;
    private Date quote_date;
    private Date added_date;
    private String subject;
    private String teacher;
    private String creator;

    public Quote(int id,
                 String teacher,
                 String creator,
                 String text,
                 String subject,
                 Date quote_date,
                 Date added_date
    ) {
        this.id = id;
        this.teacher = teacher;
        this.creator = creator;
        this.text = text;
        this.subject = subject;
        this.quote_date = quote_date;
        this.added_date = added_date;
    }
    public Quote(Quote quote)
    {
        this.id = quote.id;
        this.text = quote.getText();
        this.quote_date = quote.getQuoteDate();
        this.added_date = quote.getAddedDate();
        this.subject = quote.getTeacher();
        this.creator = quote.getCreator();
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public Date getAddedDate() {
        return added_date;
    }

    public Date getQuoteDate() {
        return quote_date;
    }

    public String getSubject() {
        return subject;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getCreator() {
        return creator;
    }


    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", creationDate=" + quote_date +
                ", subject='" + subject + '\'' +
                ", teacherID=" + teacher +
                ", teacher=" + teacher +
                ", creatorID=" + creator +
                '}';
    }
}
