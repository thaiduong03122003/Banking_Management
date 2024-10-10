package quanlynganhang.BUS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.DAO.TaiKhoanNVDAO;
import quanlynganhang.DAO.TinhTrangDangNhapDAO;
import quanlynganhang.DTO.KhoiPhucMatKhauDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;

public class TaiKhoanNVBUS {

    private final TaiKhoanNVDAO taiKhoanNVDAO = new TaiKhoanNVDAO();
    private final TinhTrangDangNhapDAO tinhTrangDangNhapDAO = new TinhTrangDangNhapDAO();
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

    public Object[][] doiSangObjectTaiKhoanNV(boolean isFiltered, boolean isSerached, List<TaiKhoanNVDTO> listTaiKhoanNV) {
        List<TaiKhoanNVDTO> list = new ArrayList<>();

        list = (isFiltered || isSerached) ? listTaiKhoanNV : getDSTaiKhoanNV();

        FormatDate fDate = new FormatDate();

        Object[][] data = new Object[list.size()][6];
        int rowIndex = 0;
        for (TaiKhoanNVDTO taiKhoanNV : list) {

            data[rowIndex][0] = taiKhoanNV.getMaTKNV();
            data[rowIndex][1] = taiKhoanNV.getTenNhanVien();
            data[rowIndex][2] = taiKhoanNV.getTenDangNhap();
            data[rowIndex][3] = fDate.toString(taiKhoanNV.getNgayTaoTK());

            maTrangThai = taiKhoanNV.getMaTrangThai();
            if (maTrangThai == 1 || maTrangThai == 2) {
                data[rowIndex][4] = "<html><p style='color:rgb(255,0,0);'>" + taiKhoanNV.getTenTrangThai() + "</p></html>";
            } else {
                data[rowIndex][4] = taiKhoanNV.getTenTrangThai();
            }

            int tinhTrang = taiKhoanNV.getTinhTrangDangNhap();

            if (tinhTrang == 0) {
                data[rowIndex][5] = "Đã đăng xuất";
            } else if (tinhTrang == 1) {
                data[rowIndex][5] = "<html><p style='color:rgb(0, 255, 13);'>Đang đăng nhập</p></html>";
            } else {
                data[rowIndex][5] = "<html><p style='color:rgb(255, 0, 0);'>Chưa từng đăng nhập</p></html>";
            }

            rowIndex++;
        }
        return data;
    }

    public int addTaiKhoanNV(TaiKhoanNVDTO taiKhoanNV) {
        return taiKhoanNVDAO.insert(taiKhoanNV);
    }

    public boolean updateTaiKhoanNV(TaiKhoanNVDTO taiKhoanNV) {
        return taiKhoanNVDAO.update(taiKhoanNV);
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

    public List<TaiKhoanNVDTO> locTaiKhoanNV(java.util.Date dateFrom, java.util.Date dateTo, int maNhanVien, int maTrangThai, int maTinhTrang) {
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

        return taiKhoanNVDAO.filter(dateBatDau, dateKetThuc, maNhanVien, maTrangThai, maTinhTrang);
    }

    public List<TaiKhoanNVDTO> timKiemTheoLoai(String typeName, String inputValue) {
        return taiKhoanNVDAO.searchByInputType(typeName, inputValue);
    }

    public boolean doiMatKhau(TaiKhoanNVDTO taiKhoanNV) {
        return taiKhoanNVDAO.changePassword(taiKhoanNV);
    }

    public KhoiPhucMatKhauDTO getEmailByUsername(String username) {
        try {
            return taiKhoanNVDAO.getEmailFromUsername(username);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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
}
