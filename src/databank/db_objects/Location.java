/*
@author Sieben Veldeman
 */
package databank.db_objects;

public class Location {

    private int id;
    private String name;

    public Location(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String updated) {
        name = updated;
    }

    @Override
    public String toString() {
        return name;
    }
}
