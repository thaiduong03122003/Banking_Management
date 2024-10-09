/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.DTO;

public class CalendarDTO {
    private String weekday, calDay, calMonth, calYear, event;

    public CalendarDTO() {
    }

    public String getCalDay() {
        return calDay;
    }

    public void setCalDay(String calDay) {
        this.calDay = calDay;
    }

    public String getCalMonth() {
        return calMonth;
    }

    public void setCalMonth(String calMonth) {
        this.calMonth = calMonth;
    }

    public String getCalYear() {
        return calYear;
    }

    public void setCalYear(String calYear) {
        this.calYear = calYear;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }
    
    @Override
    public String toString() {
        return "CalendarDTO{" +
                "weekday='" + weekday + '\'' +
                ", calDay='" + calDay + '\'' +
                ", calMonth='" + calMonth + '\'' +
                ", calYear='" + calYear + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
