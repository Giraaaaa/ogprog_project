/*
@author Sieben Veldeman
 */

package databank.db_objects;

public class Lecture {

    private int student_id;
    private int teacher_id;
    private int location_id;
    private String course;
    private int day;
    private int first_block;
    private int duration;

    public Lecture(int student_id, int teacher_id, int location_id, String course, int day, int first_block, int duration) {
        this.student_id = student_id;
        this.teacher_id = teacher_id;
        this.location_id = location_id;
        this.course = course;
        // 1 for monday, 5 for friday
        this.day = day;
        this.first_block = first_block;

        this.duration = duration;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public String getCourse() {
        return course;
    }

    public int getDay() {
        return day;
    }

    public int getFirst_block() {
        return first_block;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return course;
    }
}
