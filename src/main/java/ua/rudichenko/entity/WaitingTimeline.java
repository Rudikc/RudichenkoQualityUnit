package ua.rudichenko.entity;

import java.util.Date;

public class WaitingTimeline {
    final private String service;
    final private String question;
    final private Date date;
    final private String responseType;
    final private int time;

    public WaitingTimeline(String service, String question, String responseType, Date date, int time) {
        this.service = service;
        this.question = question;
        this.responseType = responseType;
        this.date = date;
        this.time = time;
    }

    public String getService() {
        return service;
    }

    public String getQuestion() {
        return question;
    }

    public Date getDate() {
        return date;
    }

    public String getResponseType() {
        return responseType;
    }

    public int getTime() {
        return time;
    }
}
