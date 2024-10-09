package quanlynganhang.BUS.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.GUI.model.message.MessageBox;

public class FormatDate {

    public String pattern = "dd/MM/yyyy";
    public String pattern1 = "HH:mm:ss dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    private SimpleDateFormat sdfTime = new SimpleDateFormat(pattern1);

    public String toString(Date date) {
        return sdf.format(date);
    }

    public Date toDate(String stDate) {
        try {
            return sdf.parse(stDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date getToday() {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return toDate(formattedToday);
    }

    public Date addDay(int nODays) {
        LocalDate today = LocalDate.now();
        LocalDate newDate = today.plusDays(nODays);
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return toDate(formattedToday);
    }

    public Date addMonth(int nOMonths) {
        LocalDate today = LocalDate.now();
        LocalDate newDate = today.plusMonths(nOMonths);
        return Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public Date addMonth(Date date, int nOMonths) {
        if (nOMonths == 0) {
            return date;
        }
        
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate localDate1 = LocalDate.parse(toString(date), format);
            LocalDate newDate = localDate1.plusMonths(nOMonths);
            
            return Date.from(newDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean compareDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    public int tinhSoNgay(Date date1, Date date2) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {

            LocalDate localDate1 = LocalDate.parse(toString(date1), format);
            LocalDate localDate2 = LocalDate.parse(toString(date2), format);

            long daysBetween = ChronoUnit.DAYS.between(localDate2, localDate1);

            return (int) daysBetween;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int tinhSoThang(Date date1, Date date2) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {

            LocalDate localDate1 = LocalDate.parse(toString(date1), format);
            LocalDate localDate2 = LocalDate.parse(toString(date2), format);

            long monthsBetween = ChronoUnit.MONTHS.between(localDate2, localDate1);

            return (int) monthsBetween;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
