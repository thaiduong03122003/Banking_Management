package quanlynganhang.BUS.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormatDate {

    public String pattern = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    public String toString(Date date) {
        return sdf.format(date);
    }

    public Date toDate(String stDate) throws ParseException {
        return sdf.parse(stDate);
    }
    
    public Date getToday() {
        LocalDate today = LocalDate.now();
        String formattedToday = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        try {
            return toDate(formattedToday);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
