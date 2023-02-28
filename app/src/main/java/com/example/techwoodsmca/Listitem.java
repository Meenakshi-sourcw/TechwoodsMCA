package com.example.techwoodsmca;

public class Listitem {

    private String head;
    private String desc;
    private String participants;
    private String date;
    private String venue;
    private String category;
    private String incharge;

    public Listitem(String head, String desc, String participants,String date,String venue,String category,String incharge) {
        this.head = head;
        this.desc = desc;
        this.participants = participants;
        this.date = date;
        this.category = category;
        this.venue = venue;
        this.incharge = incharge;
    }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getParticipants() {
        return participants;
    }

    public String getDate() {
        return date;
    }

    public String getVenue() {
        return venue;
    }

    public String getCategory() {
        return category;
    }

    public String getIncharge() {
        return incharge;
    }
}
