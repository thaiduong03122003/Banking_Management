package quanlynganhang.BUS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.DAO.LoaiTheATMDAO;
import quanlynganhang.DAO.TheATMDAO;
import quanlynganhang.DTO.LoaiTheATMDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TheATMDTO;
import quanlynganhang.DTO.TrangThaiDTO;

public class TheATMBUS {
    
    private final TheATMDAO theATMDAO = new TheATMDAO();
    private final LoaiTheATMDAO loaiTheATMDAO = new LoaiTheATMDAO();
    private final FormatDate fDate = new FormatDate();
    
    public TheATMBUS() {
    }
    
    public List<TheATMDTO> getDSThe() {
        try {
            return theATMDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[][] doiSangObjectThe(boolean isFiltered, boolean isSearched, List<TheATMDTO> listThe) {
        List<TheATMDTO> list = new ArrayList<>();

        if (isFiltered || isSearched) {
            list = listThe;
        } else {
            list = getDSThe();
        }

        Object[][] data = new Object[list.size()][7];
        int rowIndex = 0;
        for (TheATMDTO theATM : list) {
            data[rowIndex][0] = theATM.getMaThe();
            data[rowIndex][1] = theATM.getSoThe();
            data[rowIndex][2] = theATM.getHoTenKH();
            data[rowIndex][3] = fDate.toString(theATM.getNgayTao());
            data[rowIndex][4] = fDate.toString(theATM.getThoiHanThe());
            data[rowIndex][5] = theATM.getTenLoaiThe();
            data[rowIndex][6] = theATM.getTenTrangThai();
            rowIndex++;
        }
        return data;
    }
    
    

    public int addTheATM(TheATMDTO theATM) {
        try {
            
            return theATMDAO.insert(theATM);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean updateTheATM(TheATMDTO theATM) {
        try {
            return theATMDAO.update(theATM);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean doiTrangThai(int maThe, int maTrangThai) {
        try {
            return theATMDAO.switchStatus(maThe, maTrangThai);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean giaHanThe(TheATMDTO theATM) {
        try {
            return theATMDAO.giaHanThe(theATM);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public TheATMDTO getTheById(int maThe) {
        try {
            return theATMDAO.selectById(maThe);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<TheATMDTO> getTheByMaKH(int maKhachHang) {
        try {
            return theATMDAO.selectByMaKH(maKhachHang);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<TheATMDTO> locThe(java.util.Date dateFrom, java.util.Date dateTo, int maKhachHang, int maLoaiThe, int maTrangThai) {
        java.sql.Date dateBatDau = null;
        java.sql.Date dateKetThuc = null;

        if (dateFrom != null && dateTo != null) {
            dateBatDau = new Date(dateFrom.getTime());
            dateKetThuc = new Date(dateTo.getTime());
        } else if (dateFrom != null && dateTo == null) {
            dateBatDau = new Date(dateFrom.getTime());
        } else if (dateFrom == null && dateTo != null) {
            dateKetThuc = new Date(dateTo.getTime());
        }

        return theATMDAO.filter(dateBatDau, dateKetThuc, maKhachHang, maLoaiThe, maTrangThai);
    }

    public boolean doiMaPIN(TheATMDTO theATM) {
        try {
            return theATMDAO.changePIN(theATM);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public List<LoaiTheATMDTO> getDSLoaiTheATM() {
        try {
            return loaiTheATMDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map<Integer, String> convertListLoaiTheATMToMap() {
        List<LoaiTheATMDTO> list = getDSLoaiTheATM();
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (LoaiTheATMDTO loaiThe : list) {
            map.put(loaiThe.getMaLoaiThe(), loaiThe.getTenLoaiThe());
        }

        return map;
    }
    
    public Integer getIdFromTenLoaiThe(String tenLoaiThe) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListLoaiTheATMToMap();
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenLoaiThe)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public List<TheATMDTO> timKiemTheoLoai(String typeName, String inputValue) {
        try {
            return theATMDAO.searchByInputType(typeName, inputValue);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void xuatExcel(File file, String userName, List<TheATMDTO> list) throws FileNotFoundException, IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách thẻ của khách hàng");

        String[] titles = {"Mã thẻ", "Số thẻ", "Tên khách hàng", "Ngày tạo", "Thời hạn thẻ", "Loại thẻ", "Trạng thái thẻ"};
        LocalDate ngayHienTai = LocalDate.now();

        Row timeRow = sheet.createRow(0);
        Cell cell = timeRow.createCell(0);
        cell.setCellValue("Ngày xuất danh sách: " + ngayHienTai.toString());

        Row userRow = sheet.createRow(1);
        cell = userRow.createCell(0);
        cell.setCellValue("Nhân viên xuất file: " + userName);

        Row titleRow = sheet.createRow(3);
        for (int i = 0; i < titles.length; i++) {
            cell = titleRow.createCell(i);
            cell.setCellValue(titles[i]);
        }

        int rowCount = 4;
        for (TheATMDTO theATM : list) {
            Row dataRow = sheet.createRow(rowCount++);
            cell = dataRow.createCell(0);
            cell.setCellValue(theATM.getMaThe());
            cell = dataRow.createCell(1);
            cell.setCellValue(theATM.getSoThe());
            cell = dataRow.createCell(2);
            cell.setCellValue(theATM.getHoTenKH());
            cell = dataRow.createCell(3);
            cell.setCellValue(fDate.toString(theATM.getNgayTao()));
            cell = dataRow.createCell(4);
            cell.setCellValue(fDate.toString(theATM.getThoiHanThe()));
            cell = dataRow.createCell(5);
            cell.setCellValue(theATM.getTenLoaiThe());
            cell = dataRow.createCell(6);
            cell.setCellValue(theATM.getTenTrangThai());
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
    
    public TheATMDTO getTheATMBySoThe(String soThe) {
        return theATMDAO.selectByCardNum(soThe);
    }
    
    public String taoSoTheTuDong() {
        String soThe = theATMDAO.getNewSoThe();

        if (soThe.isEmpty()) {
            soThe = "100000000011";
        }

        BigInteger newSoThe = new BigInteger(soThe);

        boolean flag = true;
        int count = 1;
        while (flag) {
            newSoThe = newSoThe.add(new BigInteger("" + count));

            if (getTheATMBySoThe(newSoThe.toString()) != null) {
                count++;
            } else {
                flag = false;
            }
        }
        return newSoThe.toString();
    }
}
