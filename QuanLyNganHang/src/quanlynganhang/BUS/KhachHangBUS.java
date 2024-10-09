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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.DAO.ChucVuDAO;
import quanlynganhang.DAO.KhachHangDAO;
import quanlynganhang.DAO.NhanVienDAO;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.NhanVienDTO;

public class KhachHangBUS {

    private final KhachHangDAO khachHangDAO = new KhachHangDAO();
    private KhachHangDTO khachHang;
    private FormatDate fDate = new FormatDate();

    public KhachHangBUS() {
    }

    public KhachHangBUS(KhachHangDTO khachHang) {
        this.khachHang = khachHang;
    }

    public List<KhachHangDTO> getDSKhachHang(int biXoa) {
        return khachHangDAO.selectAll(biXoa);
    }

    public Object[][] doiSangObjectKhachHang(int biXoa, boolean isFiltered, boolean isSearched, List<KhachHangDTO> listKH) {
        FormatDate fDate = new FormatDate();
        List<KhachHangDTO> list = new ArrayList<>();

        list = (isFiltered || isSearched) ? listKH : getDSKhachHang(biXoa);

        String diaChi = "";
        Object[][] data = new Object[list.size()][10];
        int rowIndex = 0;
        for (KhachHangDTO khachHang : list) {
            data[rowIndex][0] = khachHang.getMaKH();
            data[rowIndex][1] = khachHang.getHoDem();
            data[rowIndex][2] = khachHang.getTen();
            data[rowIndex][3] = khachHang.getGioiTinh();
            data[rowIndex][4] = fDate.toString(khachHang.getNgaySinh());
            data[rowIndex][5] = khachHang.getDiaChi();
            data[rowIndex][6] = khachHang.getEmail();
            data[rowIndex][7] = khachHang.getSdt();
            data[rowIndex][8] = khachHang.getCccd();

            if (khachHang.getNoXau() == 0) {
                data[rowIndex][9] = "Không";
            } else {
                data[rowIndex][9] = "Có";
            }

            rowIndex++;
        }
        return data;
    }

    public int addKhachHang(KhachHangDTO khachHang, int biXoa) {
        return khachHangDAO.insert(khachHang, biXoa);
    }

    public boolean updateKhachHang(KhachHangDTO khachHang, int biXoa) {
        return khachHangDAO.update(khachHang, biXoa);
    }

    public boolean deleteKhachHang(int maKhachHang) {
        return khachHangDAO.delete(maKhachHang);
    }

    public boolean restoreKhachHang(int maKhachHang) {
        return khachHangDAO.restore(maKhachHang);
    }

    public KhachHangDTO getKhachHangById(int maKhachHang, int biXoa) {
        return khachHangDAO.selectById(maKhachHang, biXoa);
    }

    public List<KhachHangDTO> locKhachHang(String gender, java.util.Date dateFrom, java.util.Date dateTo, int provinceId, int districtId, int wardId, int noXau) {
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

        return khachHangDAO.filter(0, gender, dateBatDau, dateKetThuc, provinceId, districtId, wardId, noXau);
    }

    public List<KhachHangDTO> timKiemTheoLoai(int biXoa, String typeName, String inputValue) {
        return khachHangDAO.searchByInputType(biXoa, typeName, inputValue);
    }

    public boolean xoaNoXau(int maKhachHang) {
        return khachHangDAO.xoaNoXau(maKhachHang);
    }

    public void xuatExcel(File file, String userName, List<KhachHangDTO> list) throws FileNotFoundException, IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách khách hàng");

        String[] titles = {"Mã khách hàng", "Họ", "Tên", "Giới tính", "Ngày sinh", "Mã phường xã", "Địa chỉ", "Email", "Số điện thoại", "Mã CCCD", "Ảnh đại diện", "Nợ xấu", "Bị xóa"};

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
        for (KhachHangDTO khachHang : list) {
            Row dataRow = sheet.createRow(rowCount++);
            cell = dataRow.createCell(0);
            cell.setCellValue(khachHang.getMaKH());
            cell = dataRow.createCell(1);
            cell.setCellValue(khachHang.getHoDem());
            cell = dataRow.createCell(2);
            cell.setCellValue(khachHang.getTen());
            cell = dataRow.createCell(3);
            cell.setCellValue(khachHang.getGioiTinh());
            cell = dataRow.createCell(4);
            cell.setCellValue(fDate.toString(khachHang.getNgaySinh()));
            cell = dataRow.createCell(5);
            cell.setCellValue(khachHang.getMaPhuongXa());
            cell = dataRow.createCell(6);
            cell.setCellValue(khachHang.getDiaChi());
            cell = dataRow.createCell(7);
            cell.setCellValue(khachHang.getEmail());
            cell = dataRow.createCell(8);
            cell.setCellValue(khachHang.getSdt());
            cell = dataRow.createCell(9);
            cell.setCellValue(khachHang.getCccd());
            cell = dataRow.createCell(10);
            cell.setCellValue(khachHang.getAnhDaiDien());
            cell = dataRow.createCell(11);
            cell.setCellValue(khachHang.getNoXau());
            cell = dataRow.createCell(12);
            cell.setCellValue(khachHang.getBiXoa());
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
