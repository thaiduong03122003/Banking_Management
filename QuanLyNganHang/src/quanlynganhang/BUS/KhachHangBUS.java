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
        try {
            return khachHangDAO.selectAll(biXoa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[][] doiSangObjectKhachHang(int biXoa, boolean isFiltered, List<KhachHangDTO> listKH) throws Exception {
        FormatDate fDate = new FormatDate();
        List<KhachHangDTO> list = new ArrayList<>();

        if (isFiltered) {
            list = listKH;
        } else {
            list = getDSKhachHang(biXoa);
        }

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
        try {
            return khachHangDAO.insert(khachHang, biXoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean updateKhachHang(KhachHangDTO khachHang, int biXoa) {
        try {
            return khachHangDAO.update(khachHang, biXoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deleteKhachHang(int maKhachHang) {
        try {
            return khachHangDAO.delete(maKhachHang);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean restoreKhachHang(int maKhachHang) {
        try {
            return khachHangDAO.restore(maKhachHang);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public KhachHangDTO getKhachHangById(int maKhachHang, int biXoa) {
        try {
            return khachHangDAO.selectById(maKhachHang, biXoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<KhachHangDTO> locKhachHang(String gender, java.util.Date dateFrom, java.util.Date dateTo, int provinceId, int districtId, int wardId, int noXau) throws Exception {
        java.sql.Date dateBatDau = null;
        java.sql.Date dateKetThuc = null;

        if (dateFrom != null && dateTo != null) {
            dateBatDau = new Date(dateFrom.getTime());
            dateKetThuc = new Date(dateTo.getTime());
        }

        return khachHangDAO.filter(0, gender, dateBatDau, dateKetThuc, provinceId, districtId, wardId, noXau);
    }
    
    public boolean xoaNoXau(int maKhachHang) {
        try {
            return khachHangDAO.xoaNoXau(maKhachHang);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
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

    public List<KhachHangDTO> nhapExcel(File file) throws IOException {
        List<KhachHangDTO> list = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            String sheetName = workbook.getSheetName(0);
            if (!sheetName.equals("Danh sách khách hàng")) {
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
                        KhachHangDTO khachHang = new KhachHangDTO();
                        khachHang.setMaKH((int) row.getCell(0).getNumericCellValue());
                        khachHang.setHoDem(row.getCell(1).getStringCellValue());
                        khachHang.setTen(row.getCell(2).getStringCellValue());
                        khachHang.setGioiTinh(row.getCell(3).getStringCellValue());
                        khachHang.setNgaySinh(fDate.toDate(row.getCell(4).getStringCellValue()));
                        khachHang.setMaPhuongXa((int) row.getCell(5).getNumericCellValue());
                        khachHang.setDiaChi(row.getCell(6).getStringCellValue());
                        khachHang.setEmail(row.getCell(7).getStringCellValue());
                        khachHang.setSdt(row.getCell(8).getStringCellValue());
                        khachHang.setCccd(row.getCell(9).getStringCellValue());
                        khachHang.setAnhDaiDien(row.getCell(10).getStringCellValue());
                        khachHang.setNoXau((int) row.getCell(11).getNumericCellValue());
                        khachHang.setBiXoa((int) row.getCell(12).getNumericCellValue());

                        list.add(khachHang);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return list;
            }
        }
    }
}
