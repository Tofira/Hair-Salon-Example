package booking.server.data;

public class Appointment {
    private String title;
    private String id;
    private String start;
    private String end;

    public Appointment(String title,String id,String start,String end)
    {
        this.title = title;
        this.id = id;
        this.start = start;
        this.end = end;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
