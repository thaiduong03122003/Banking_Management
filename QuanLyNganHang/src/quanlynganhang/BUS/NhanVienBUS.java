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
import quanlynganhang.DAO.ChucVuDAO;
import quanlynganhang.DAO.NhanVienDAO;
import quanlynganhang.DTO.NhanVienDTO;

public class NhanVienBUS {
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private final FormatDate fDate = new FormatDate();

    public NhanVienBUS() {
    }

    public List<NhanVienDTO> getDSNhanVien(int biXoa) {
        return nhanVienDAO.selectAll(biXoa);
    }

    public Object[][] doiSangObjectNhanVien(int biXoa, boolean isFiltered, boolean isSearched, List<NhanVienDTO> listNV) {
        List<NhanVienDTO> list = new ArrayList<>();

        list = (isFiltered || isSearched) ? listNV : getDSNhanVien(biXoa);

        String diaChi = "";
        Object[][] data = new Object[list.size()][10];
        int rowIndex = 0;
        for (NhanVienDTO nhanVien : list) {
            data[rowIndex][0] = nhanVien.getMaNV();
            data[rowIndex][1] = nhanVien.getHoDem();
            data[rowIndex][2] = nhanVien.getTen();
            data[rowIndex][3] = nhanVien.getGioiTinh();
            data[rowIndex][4] = fDate.toString(nhanVien.getNgaySinh());
            data[rowIndex][5] = nhanVien.getDiaChi();
            data[rowIndex][6] = nhanVien.getEmail();
            data[rowIndex][7] = nhanVien.getSdt();
            data[rowIndex][8] = nhanVien.getCccd();
            data[rowIndex][9] = nhanVien.getTenChucVu();

            rowIndex++;
        }
        return data;
    }

    public NhanVienDTO addNhanVien(NhanVienDTO nhanVien, int biXoa) {
        return nhanVienDAO.insert(nhanVien, biXoa);
    }

    public boolean updateNhanVien(NhanVienDTO nhanVien, int biXoa) {
        return nhanVienDAO.update(nhanVien, biXoa);
    }

    public boolean deleteNhanVien(int maNhanVien) {
        return nhanVienDAO.delete(maNhanVien);
    }

    public boolean restoreNhanVien(int maNhanVien) {
        return nhanVienDAO.restore(maNhanVien);
    }

    public NhanVienDTO getNhanVienById(int maNhanVien, int biXoa) {
        return nhanVienDAO.selectById(maNhanVien, biXoa);
    }

    public List<NhanVienDTO> locNhanVien(String gender, java.util.Date dateFrom, java.util.Date dateTo, int provinceId, int districtId, int wardId, int roleId) {
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

        return nhanVienDAO.filter(0, gender, dateBatDau, dateKetThuc, provinceId, districtId, wardId, roleId);
    }

    public List<NhanVienDTO> timKiemTheoLoai(int biXoa, String typeName, String inputValue) {
        return nhanVienDAO.searchByInputType(biXoa, typeName, inputValue);
    }

    public boolean doiChucVu(int maNhanVien, int maChucVu) {
        try {
            return nhanVienDAO.changeRole(maNhanVien, maChucVu);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void xuatExcel(File file, String userName, List<NhanVienDTO> list) throws FileNotFoundException, IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh sách nhân viên");

        String[] titles = {"Mã nhân viên", "Họ", "Tên", "Giới tính", "Ngày sinh", "Mã phường xã", "Địa chỉ", "Email", "Số điện thoại", "Mã CCCD", "Ảnh đại diện", "Mã chức vụ", "Tên chức vụ", "Ngày vào làm", "Bị xóa"};

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
        for (NhanVienDTO nhanVien : list) {
            Row dataRow = sheet.createRow(rowCount++);
            cell = dataRow.createCell(0);
            cell.setCellValue(nhanVien.getMaNV());
            cell = dataRow.createCell(1);
            cell.setCellValue(nhanVien.getHoDem());
            cell = dataRow.createCell(2);
            cell.setCellValue(nhanVien.getTen());
            cell = dataRow.createCell(3);
            cell.setCellValue(nhanVien.getGioiTinh());
            cell = dataRow.createCell(4);
            cell.setCellValue(fDate.toString(nhanVien.getNgaySinh()));
            cell = dataRow.createCell(5);
            cell.setCellValue(nhanVien.getMaPhuongXa());
            cell = dataRow.createCell(6);
            cell.setCellValue(nhanVien.getDiaChi());
            cell = dataRow.createCell(7);
            cell.setCellValue(nhanVien.getEmail());
            cell = dataRow.createCell(8);
            cell.setCellValue(nhanVien.getSdt());
            cell = dataRow.createCell(9);
            cell.setCellValue(nhanVien.getCccd());
            cell = dataRow.createCell(10);
            cell.setCellValue(nhanVien.getAnhDaiDien());
            cell = dataRow.createCell(11);
            cell.setCellValue(nhanVien.getMaChucVu());
            cell = dataRow.createCell(12);
            cell.setCellValue(nhanVien.getTenChucVu());
            cell = dataRow.createCell(13);
            cell.setCellValue(fDate.toString(nhanVien.getNgayVaoLam()));
            cell = dataRow.createCell(14);
            cell.setCellValue(nhanVien.getBiXoa());
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
