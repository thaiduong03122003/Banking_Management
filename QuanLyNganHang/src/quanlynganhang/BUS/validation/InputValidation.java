package quanlynganhang.BUS.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Pattern;

public class InputValidation {

    public static boolean kiemTraTen(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        if (name.length() > 50) {
            return false;
        }

        if (!Pattern.matches("^[\\p{L}]+( [\\p{L}]+)*$", name)) {
            return false;
        }

        return true;
    }
    
    public static boolean kiemTraTenDangNhap(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9@_.-]+$", name)) {
            return false;
        }

        return true;
    }

    public static boolean kiemTraSDT(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^0[0-9]{9,10}$";
            return Pattern.matches(regex, phoneNumber);
        }
    }

    public static boolean kiemTraEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^[a-zA-Z0-9.+_-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
            return Pattern.matches(regex, email);
        }
    }

    public static boolean kiemTraCCCD(String cccd) {
        if (cccd == null || cccd.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^[0-9]{12}$";
            return Pattern.matches(regex, cccd);
        }
    }

    public static boolean kiemTraNgay(String date) {
        String pattern = "dd/MM/yyyy";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);

            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean kiemTratuoi(String date) {
        if (date.length() != 10) {
            return false;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate ngaySinh = LocalDate.parse(date, format);
            LocalDate ngayHienTai = LocalDate.now();

            Period period = Period.between(ngaySinh, ngayHienTai);

            return period.getYears() >= 18 && period.getYears() <= 75;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean kiemTraTrinhTuNhapNgay(java.util.Date dateFrom, java.util.Date dateTo) {
        if (dateFrom.compareTo(dateTo) > 0) {
            return false;
        }
        return true;
    }

    public static boolean kiemTraMatKhau(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,12}$";

        return password.matches(pattern);
    }

    public static boolean kiemTraMaPIN(String pin) {
        if (pin == null || pin.isEmpty()) {
            return false;
        }

        String pattern = "^\\d{6}$";

        return pin.matches(pattern);
    }

    public static boolean kiemTraNgayKhoa(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
            LocalDate ngayKhoa = LocalDate.parse(dateString, formatter);
            LocalDate today = LocalDate.now();
            
            return ngayKhoa.isAfter(today);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean kiemTraSoTien(String soTien) {
        if (soTien == null || soTien.trim().isEmpty()) {
            return false;
        } else {
            String regex = "[0-9]+";
            return Pattern.matches(regex, soTien);
        }
    }

    public static boolean kiemTraMaXacNhan(String soTien) {
        if (soTien == null || soTien.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^\\d{6}$";
            return Pattern.matches(regex, soTien);
        }
    }

    public static String catNganString(String originalString) {
        int maxLength = 20;
        if (originalString.length() > maxLength) {
            return originalString.substring(0, maxLength - 3) + "...";
        } else {
            return originalString;
        }
    }

    public static String anEmail(String email) {

        String[] parts = email.split("@");
        String localPart = parts[0];
        String domainPart = parts[1];

        if (localPart.length() <= 3) {
            return email;
        }

        String anKytu = localPart.charAt(0) + "***" + localPart.substring(localPart.length() - 2);

        return anKytu + "@" + domainPart;
    }
}
