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
import quanlynganhang.DAO.TaiKhoanKHDAO;
import quanlynganhang.DTO.TaiKhoanKHDTO;

public class TaiKhoanKHBUS {

    private final TaiKhoanKHDAO taiKhoanKHDAO = new TaiKhoanKHDAO();
    private FormatDate fDate = new FormatDate();

    public TaiKhoanKHBUS() {
    }

    public List<TaiKhoanKHDTO> getDSTaiKhoanKH() {
        try {
            return taiKhoanKHDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[][] doiSangObjectTaiKhoanKH(boolean isFiltered, List<TaiKhoanKHDTO> listTaiKhoanKH) {
        List<TaiKhoanKHDTO> list = new ArrayList<>();

        if (isFiltered) {
            list = listTaiKhoanKH;
        } else {
            list = getDSTaiKhoanKH();
        }

        FormatDate fDate = new FormatDate();

        Object[][] data = new Object[list.size()][7];
        int rowIndex = 0;
        for (TaiKhoanKHDTO taiKhoanKH : list) {
            data[rowIndex][0] = taiKhoanKH.getMaTKKH();
            data[rowIndex][1] = taiKhoanKH.getSoTaiKhoan();
            data[rowIndex][2] = taiKhoanKH.getTenTaiKhoan();
            data[rowIndex][3] = taiKhoanKH.getTenKhachHang();
            data[rowIndex][4] = fDate.toString(taiKhoanKH.getNgayTao());
            data[rowIndex][5] = taiKhoanKH.getTenLoaiTaiKhoan();
            data[rowIndex][6] = taiKhoanKH.getTenTrangThai();
            rowIndex++;
        }
        return data;
    }

    public boolean addTaiKhoanKH(TaiKhoanKHDTO taiKhoanKH) {
        try {
            return taiKhoanKHDAO.insert(taiKhoanKH);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateTaiKhoanNV(TaiKhoanKHDTO taiKhoanKH) {
        try {
            return taiKhoanKHDAO.update(taiKhoanKH);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean doiTrangThai(int maTaiKhoanKH, int maTrangThai) {
        try {
            return taiKhoanKHDAO.switchStatus(maTaiKhoanKH, maTrangThai);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public TaiKhoanKHDTO getTaiKhoanKHById(int maTaiKhoanKH) {
        try {
            return taiKhoanKHDAO.selectById(maTaiKhoanKH);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<TaiKhoanKHDTO> locTaiKhoanKH(java.util.Date dateFrom, java.util.Date dateTo, int maKhachHang, int maLoaiTaiKhoan, int maTrangThai) throws Exception {
        java.sql.Date dateBatDau = null;
        java.sql.Date dateKetThuc = null;

        if (dateFrom != null && dateTo != null) {
            dateBatDau = new Date(dateFrom.getTime());
            dateKetThuc = new Date(dateTo.getTime());
        }

        return taiKhoanKHDAO.filter(dateBatDau, dateKetThuc, maKhachHang, maLoaiTaiKhoan, maTrangThai);
    }

    public boolean doiMatKhau(TaiKhoanKHDTO taiKhoanKH) {
        try {
            return taiKhoanKHDAO.changePassword(taiKhoanKH);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void xuatExcel(File file, String userName, List<TaiKhoanKHDTO> list) throws FileNotFoundException, IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách tài khoản khách hàng");

        String[] titles = {"Mã tài khoản", "Số tài khoản", "Họ tên khách hàng", "Ngày tạo tài khoản", "Trạng thái tài khoản"};
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
        for (TaiKhoanKHDTO taiKhoanKH : list) {
            Row dataRow = sheet.createRow(rowCount++);
            cell = dataRow.createCell(0);
            cell.setCellValue(taiKhoanKH.getMaTKKH());
            cell = dataRow.createCell(1);
            cell.setCellValue(taiKhoanKH.getSoTaiKhoan());
            cell = dataRow.createCell(2);
            cell.setCellValue(taiKhoanKH.getTenKhachHang());
            cell = dataRow.createCell(3);
            cell.setCellValue(fDate.toString(taiKhoanKH.getNgayTao()));
            cell = dataRow.createCell(4);
            cell.setCellValue(taiKhoanKH.getTenTrangThai());
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public List<TaiKhoanKHDTO> nhapExcel(File file) throws IOException {
        List<TaiKhoanKHDTO> list = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            String sheetName = workbook.getSheetName(0);
            if (!sheetName.equals("Danh sách tài khoản khách hàng")) {
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
                        TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                        taiKhoanKH.setMaTKKH((int) row.getCell(0).getNumericCellValue());
                        taiKhoanKH.setSoTaiKhoan(row.getCell(1).getStringCellValue());
                        taiKhoanKH.setTenKhachHang(row.getCell(2).getStringCellValue());
                        taiKhoanKH.setNgayTao(fDate.toDate(row.getCell(3).getStringCellValue()));
                        taiKhoanKH.setTenTrangThai(row.getCell(4).getStringCellValue());

                        list.add(taiKhoanKH);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return list;
            }
        }
    }
}
