/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.BUS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import quanlynganhang.DTO.CalendarDTO;

public class CalendarBUS {

    public static CalendarDTO getInfoToday() {
        LocalDate today = LocalDate.now();

        Locale locale = new Locale.Builder().setLanguage("vi").setRegion("VN").build();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE", locale);
        String formattedWeekday = today.format(formatter);

        String calDay = String.valueOf(today.getDayOfMonth());
        String calMonth = "Tháng " + today.getMonthValue();
        String calYear = String.valueOf(today.getYear());

        CalendarDTO calendar = new CalendarDTO();
        calendar.setWeekday(formattedWeekday);
        calendar.setCalDay(calDay);
        calendar.setCalMonth(calMonth);
        calendar.setCalYear(calYear);

        String event = getEvent(calDay, String.valueOf(today.getMonthValue()));
        calendar.setEvent(event);

        return calendar;
    }

    private static String getEvent(String day, String month) {
        Map<String, String> events = new HashMap<>();

        events.put("1-1", "Tết Dương Lịch");
        events.put("9-1", "Ngày truyền thống học sinh, sinh viên Việt Nam");
        events.put("3-2", "Ngày thành lập Đảng Cộng Sản Việt Nam");
        events.put("14-2", "Lễ tình nhân (Valentine)");
        events.put("27-2", "Ngày thầy thuốc Việt Nam");
        events.put("8-3", "Ngày Quốc tế Phụ nữ");
        events.put("20-3", "Ngày Quốc tế Hạnh phúc");
        events.put("26-3", "Ngày thành lập Đoàn TNCS Hồ Chí Minh");
        events.put("1-4", "Ngày Cá tháng Tư");
        events.put("30-4", "Ngày giải phóng miền Nam");
        events.put("1-5", "Ngày Quốc tế Lao động");
        events.put("7-5", "Ngày chiến thắng Điện Biên Phủ");
        events.put("13-5", "Ngày của mẹ");
        events.put("19-5", "Ngày sinh Chủ tịch Hồ Chí Minh");
        events.put("1-6", "Ngày Quốc tế thiếu nhi");
        events.put("17-6", "Ngày của cha");
        events.put("21-6", "Ngày báo chí Việt Nam");
        events.put("28-6", "Ngày gia đình Việt Nam");
        events.put("11-7", "Ngày dân số thế giới");
        events.put("27-7", "Ngày Thương binh liệt sĩ");
        events.put("28-7", "Ngày thành lập công đoàn Việt Nam");
        events.put("19-8", "Ngày kỷ niệm Cách mạng Tháng 8 thành công");
        events.put("2-9", "Ngày Quốc Khánh");
        events.put("10-9", "Ngày thành lập Mặt trận Tổ quốc Việt Nam");
        events.put("1-10", "Ngày quốc tế người cao tuổi");
        events.put("10-10", "Ngày giải phóng thủ đô");
        events.put("13-10", "Ngày doanh nhân Việt Nam");
        events.put("20-10", "Ngày Phụ nữ Việt Nam");
        events.put("31-10", "Ngày Halloween");
        events.put("9-11", "Ngày pháp luật Việt Nam");
        events.put("19-11", "Ngày Quốc tế Nam giới");
        events.put("20-11", "Ngày Nhà giáo Việt Nam");
        events.put("23-11", "Ngày thành lập Hội chữ thập đỏ Việt Nam");
        events.put("1-12", "Ngày thế giới phòng chống AIDS");
        events.put("19-12", "Ngày toàn quốc kháng chiến");
        events.put("22-12", "Ngày thành lập quân đội nhân dân Việt Nam");
        events.put("24-12  ", "Ngày lễ Giáng sinh");

        String key = day + "-" + month;
        return events.getOrDefault(key, "Không có sự kiện");
    }

    public static void main(String[] args) {
        CalendarBUS.getInfoToday();
    }
}
