package quanlynganhang.BUS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.FormatNumber;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DAO.TaiKhoanKHDAO;
import quanlynganhang.DTO.TaiKhoanKHDTO;

public class TaiKhoanKHBUS {

    private final TaiKhoanKHDAO taiKhoanKHDAO = new TaiKhoanKHDAO();
    private FormatDate fDate = new FormatDate();

    public TaiKhoanKHBUS() {
    }

    public List<TaiKhoanKHDTO> getDSTaiKhoanKH() {
        return taiKhoanKHDAO.selectAll();
    }

    public List<TaiKhoanKHDTO> getAllDSTaiKhoanKH() {
        return taiKhoanKHDAO.selectAllIncludeDeleted();
    }

    public List<TaiKhoanKHDTO> getDSTaiKhoanNguon(int maKhachHang) {
        return taiKhoanKHDAO.selectByMaKH(maKhachHang);
    }

    private List<TaiKhoanKHDTO> getDSTaiKhoanVay() {
        return taiKhoanKHDAO.selectTKVay();
    }

    public Object[][] doiSangObjectTaiKhoanKH(boolean isFiltered, boolean isSearched, List<TaiKhoanKHDTO> listTaiKhoanKH, boolean isBiXoa) {
        List<TaiKhoanKHDTO> list = new ArrayList<>();

        if (isFiltered || isSearched) {
            list = listTaiKhoanKH;
        } else {
            if (isBiXoa) {
                list = getDSTaiKhoanKH();
            } else {
                list = getAllDSTaiKhoanKH();
            }
        }

        FormatDate fDate = new FormatDate();

        Object[][] data = new Object[list.size()][8];
        int rowIndex = 0;
        for (TaiKhoanKHDTO taiKhoanKH : list) {
            data[rowIndex][0] = taiKhoanKH.getMaTKKH();
            data[rowIndex][1] = taiKhoanKH.getSoTaiKhoan();
            data[rowIndex][2] = taiKhoanKH.getTenTaiKhoan();
            data[rowIndex][3] = taiKhoanKH.getTenKhachHang();
            if (InputValidation.kiemTraSoTien(taiKhoanKH.getSoDu())) {
                data[rowIndex][4] = FormatNumber.convertNumToVND(new BigInteger(taiKhoanKH.getSoDu())) + " VND";
            } else {
                data[rowIndex][4] = "<html><p style='color:rgb(255,0,0);'>Không xác định</p></html>";
            }
            
            data[rowIndex][5] = fDate.toString(taiKhoanKH.getNgayTao());
            data[rowIndex][6] = taiKhoanKH.getTenLoaiTaiKhoan();

            int maTrangThai = taiKhoanKH.getMaTrangThai();

            if (maTrangThai == 1 || maTrangThai == 2) {
                data[rowIndex][7] = "<html><p style='color:rgb(255,0,0);'>" + taiKhoanKH.getTenTrangThai() + "</p></html>";
            } else {
                data[rowIndex][7] = taiKhoanKH.getTenTrangThai();
            }

            rowIndex++;
        }
        return data;
    }

    public Object[][] doiSangObjectTaiKhoanVay() {
        List<TaiKhoanKHDTO> list = getDSTaiKhoanVay();

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

    public Map<Integer, String> convertListTKNguonToMap(int maKhachHang) {
        List<TaiKhoanKHDTO> list = getDSTaiKhoanNguon(maKhachHang);
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (TaiKhoanKHDTO taiKhoanKH : list) {
            map.put(taiKhoanKH.getMaTKKH(), taiKhoanKH.getSoTaiKhoan() + " - " + taiKhoanKH.getTenLoaiTaiKhoan());
        }

        return map;
    }

    public Integer getIdFromSTKNguon(String tenTKNguon, int maKhachHang) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListTKNguonToMap(maKhachHang);

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenTKNguon)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int addTaiKhoanKH(TaiKhoanKHDTO taiKhoanKH) {
        return taiKhoanKHDAO.insert(taiKhoanKH);
    }

    public boolean updateTaiKhoanKH(TaiKhoanKHDTO taiKhoanKH) {
        return taiKhoanKHDAO.update(taiKhoanKH);
    }

    public boolean doiTrangThai(int maTaiKhoanKH, int maTrangThai) {
        return taiKhoanKHDAO.switchStatus(maTaiKhoanKH, maTrangThai);
    }

    public TaiKhoanKHDTO getTaiKhoanKHById(int maTaiKhoanKH) {
        return taiKhoanKHDAO.selectById(maTaiKhoanKH);
    }

    public TaiKhoanKHDTO getTaiKhoanKHBySTK(String soTK) {
        return taiKhoanKHDAO.selectByAccountNum(soTK, 1);
    }

    public TaiKhoanKHDTO getTaiKhoanKHBySTK(String sotaiKhoan, int maNganHang) {
        return taiKhoanKHDAO.selectByAccountNum(sotaiKhoan, maNganHang);
    }

    private String getNewSTK() {
        return taiKhoanKHDAO.getNewSTK();
    }

    public List<TaiKhoanKHDTO> locTaiKhoanKH(java.util.Date dateFrom, java.util.Date dateTo, int maKhachHang, int maLoaiTaiKhoan, int maTrangThai) {
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

        return taiKhoanKHDAO.filter(dateBatDau, dateKetThuc, maKhachHang, maLoaiTaiKhoan, maTrangThai);
    }

    public List<TaiKhoanKHDTO> timKiemTheoLoai(String typeName, String inputValue) {
        return taiKhoanKHDAO.searchByInputType(typeName, inputValue);
    }

    public boolean doiMatKhau(TaiKhoanKHDTO taiKhoanKH) {
        return taiKhoanKHDAO.changePassword(taiKhoanKH);
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
            cell.setCellValue(taiKhoanKH.getSoDu());
            cell = dataRow.createCell(4);
            cell.setCellValue(fDate.toString(taiKhoanKH.getNgayTao()));
            cell = dataRow.createCell(5);
            cell.setCellValue(taiKhoanKH.getTenTrangThai());
        }

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public String taoSTKTuDong() {
        String soTaiKhoan = getNewSTK();

        if (soTaiKhoan.equals("")) {
            soTaiKhoan = "100000000011";
        }

        BigInteger newSTK = new BigInteger(soTaiKhoan);

        boolean flag = true;
        int count = 1;
        while (flag) {
            newSTK = newSTK.add(new BigInteger("" + count));

            if (getTaiKhoanKHBySTK(newSTK.toString()) != null) {
                count++;
            } else {
                flag = false;
            }
        }
        return newSTK.toString();
    }

    public List<TaiKhoanKHDTO> getTaiKhoanKHByMaKH(int maTaiKhoanKH) {
        return taiKhoanKHDAO.selectByMaKH(maTaiKhoanKH);
    }
}
