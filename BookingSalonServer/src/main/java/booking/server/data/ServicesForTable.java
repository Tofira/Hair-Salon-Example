package booking.server.data;


import java.util.ArrayList;

/**
 * A wrapper class for JSON and the client side's table.
 */
public class ServicesForTable {
    private ArrayList<Service> data;


    public ServicesForTable(ArrayList<Service> records)
    {
        this.data = records;

    }

    public ArrayList<Service> getData() {
        return data;
    }

    public void setData(ArrayList<Service> data) {
        this.data = data;
    }
}
