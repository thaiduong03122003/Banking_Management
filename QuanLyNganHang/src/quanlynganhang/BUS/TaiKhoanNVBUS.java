package quanlynganhang.BUS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.DAO.TaiKhoanNVDAO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;

public class TaiKhoanNVBUS {

    private final TaiKhoanNVDAO taiKhoanNVDAO = new TaiKhoanNVDAO();
    private FormatDate fDate = new FormatDate();
    private int maNhanVien, maTrangThai;
    private Date dateFrom, dateTo;

    public TaiKhoanNVBUS() {
    }

    public List<TaiKhoanNVDTO> getDSTaiKhoanNV() {
        try {
            return taiKhoanNVDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[][] doiSangObjectTaiKhoanNV(boolean isFiltered, List<TaiKhoanNVDTO> listTaiKhoanNV) {
        List<TaiKhoanNVDTO> list = new ArrayList<>();

        if (isFiltered) {
            list = listTaiKhoanNV;
        } else {
            list = getDSTaiKhoanNV();
        }

        FormatDate fDate = new FormatDate();

        Object[][] data = new Object[list.size()][5];
        int rowIndex = 0;
        for (TaiKhoanNVDTO taiKhoanNV : list) {
            data[rowIndex][0] = taiKhoanNV.getMaTKNV();
            data[rowIndex][1] = taiKhoanNV.getTenNhanVien();
            data[rowIndex][2] = taiKhoanNV.getTenDangNhap();
            data[rowIndex][3] = fDate.toString(taiKhoanNV.getNgayTaoTK());
            data[rowIndex][4] = taiKhoanNV.getTenTrangThai();
            rowIndex++;
        }
        return data;
    }

    public boolean addTaiKhoanNV(TaiKhoanNVDTO taiKhoanNV) {
        try {
            return taiKhoanNVDAO.insert(taiKhoanNV);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateTaiKhoanNV(TaiKhoanNVDTO taiKhoanNV) {
        try {
            return taiKhoanNVDAO.update(taiKhoanNV);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean doiTrangThai(int maTaiKhoanNV, int maTrangThai) {
        try {
            return taiKhoanNVDAO.switchStatus(maTaiKhoanNV, maTrangThai);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public TaiKhoanNVDTO getTaiKhoanNVById(int maTaiKhoanNV) {
        try {
            return taiKhoanNVDAO.selectById(maTaiKhoanNV);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<TaiKhoanNVDTO> locTaiKhoanNV(java.util.Date dateFrom, java.util.Date dateTo, int maNhanVien, int maTrangThai) throws Exception {
        java.sql.Date dateBatDau = null;
        java.sql.Date dateKetThuc = null;

        if (dateFrom != null && dateTo != null) {
            dateBatDau = new Date(dateFrom.getTime());
            dateKetThuc = new Date(dateTo.getTime());
        }

        this.maNhanVien = maNhanVien;
        this.maTrangThai = maTrangThai;

        return taiKhoanNVDAO.filter(dateBatDau, dateKetThuc, maNhanVien, maTrangThai);
    }

    public void xuatExcel(File file, String userName, List<TaiKhoanNVDTO> list) throws FileNotFoundException, IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách tài khoản nhân viên");

        String[] titles = {"Mã tài khoản", "Họ tên nhân viên", "Tên đăng nhập", "Ngày tạo tài khoản", "Trạng thái tài khoản"};
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
        for (TaiKhoanNVDTO taiKhoanNV : list) {
            Row dataRow = sheet.createRow(rowCount++);
            cell = dataRow.createCell(0);
            cell.setCellValue(taiKhoanNV.getMaTKNV());
            cell = dataRow.createCell(1);
            cell.setCellValue(taiKhoanNV.getTenNhanVien());
            cell = dataRow.createCell(2);
            cell.setCellValue(taiKhoanNV.getTenDangNhap());
            cell = dataRow.createCell(3);
            cell.setCellValue(fDate.toString(taiKhoanNV.getNgayTaoTK()));
            cell = dataRow.createCell(4);
            cell.setCellValue(taiKhoanNV.getTenTrangThai());
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public List<TaiKhoanNVDTO> nhapExcel(File file) throws IOException {
        List<TaiKhoanNVDTO> list = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            String sheetName = workbook.getSheetName(0);
            if (!sheetName.equals("Danh sách tài khoản nhân viên")) {
                return null;
            } else {
                Row row;
                try {
                    int value = (int) sheet.getRow(4).getCell(0).getNumericCellValue();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                    return null;
                }
                for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                    row = sheet.getRow(i);

                    try {
                        TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                        taiKhoanNV.setMaTKNV((int) row.getCell(0).getNumericCellValue());
                        taiKhoanNV.setTenNhanVien(row.getCell(1).getStringCellValue());
                        taiKhoanNV.setTenDangNhap(row.getCell(2).getStringCellValue());
                        taiKhoanNV.setNgayTaoTK(fDate.toDate(row.getCell(3).getStringCellValue()));
                        taiKhoanNV.setTenTrangThai(row.getCell(4).getStringCellValue());

                        list.add(taiKhoanNV);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return list;
            }
        }
    }
}
