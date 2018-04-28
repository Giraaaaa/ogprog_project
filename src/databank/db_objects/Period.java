/*
@author Sieben Veldeman
 */

package databank.db_objects;

public class Period {

    private int id;
    private int hour;
    private int minute;

    public Period(int id, int hour, int minute) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
    }

    public int getId() {
        return id;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        String extended_min = minute < 10? "0" + Integer.toString(minute) : Integer.toString(minute);
        String extended_hour = hour < 10? "0" + Integer.toString(hour) : Integer.toString(hour);
        return extended_hour + ":" + extended_min;
    }
}

