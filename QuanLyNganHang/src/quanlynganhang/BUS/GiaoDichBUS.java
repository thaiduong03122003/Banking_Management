package quanlynganhang.BUS;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
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
import quanlynganhang.BUS.validation.FormatNumber;
import quanlynganhang.DAO.ChuyenTienDAO;
import quanlynganhang.DAO.GiaoDichDAO;
import quanlynganhang.DAO.KhoTienDAO;
import quanlynganhang.DAO.TaiKhoanKHDAO;
import quanlynganhang.DTO.ChuyenTienDTO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.KhoTienDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TheATMDTO;

public class GiaoDichBUS {

    private final GiaoDichDAO giaoDichDAO = new GiaoDichDAO();
    private final KhoTienDAO khoTienDAO = new KhoTienDAO();
    private final ChuyenTienDAO chuyenTienDAO = new ChuyenTienDAO();
    private KhoTienDTO khoTien;
    private final TaiKhoanKHDAO taiKhoanKHDAO = new TaiKhoanKHDAO();
    private final FormatDate fDate = new FormatDate();

    public GiaoDichBUS() {
        layTienTuKho();
    }

    private void layTienTuKho() {
        try {
            this.khoTien = khoTienDAO.selectMoney();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<GiaoDichDTO> getDSGiaoDich() {
        try {
            return giaoDichDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[][] doiSangObjectMaxGiaoDich() throws Exception {
        List<GiaoDichDTO> list = new ArrayList<>();

        list = giaoDichDAO.getMaxGiaoDich();

        Object[][] data = new Object[list.size()][8];
        int rowIndex = 0;
        for (GiaoDichDTO giaoDich : list) {
            data[rowIndex][0] = giaoDich.getMaGiaoDich();
            data[rowIndex][1] = giaoDich.getSoTaiKhoan();
            data[rowIndex][2] = giaoDich.getTenKhachHang();
            data[rowIndex][3] = giaoDich.getSoTien();
            data[rowIndex][4] = fDate.toString(giaoDich.getNgayGiaoDich());
            data[rowIndex][5] = giaoDich.getTenLoaiGiaoDich();
            data[rowIndex][6] = giaoDich.getTenNhanVien();
            data[rowIndex][7] = giaoDich.getTenTrangThai();
            rowIndex++;
        }
        return data;
    }

    public Object[][] doiSangObjectGiaoDichTK(boolean isFiltered, List<GiaoDichDTO> listGiaoDich, int loaiGD) {
        List<GiaoDichDTO> list = new ArrayList<>();

        if (isFiltered) {
            list = listGiaoDich;
        } else {
            list = giaoDichDAO.filter(null, null, 0, 0, 0, loaiGD);
        }

        Object[][] data = new Object[list.size()][8];
        int rowIndex = 0;
        for (GiaoDichDTO giaoDich : list) {
            data[rowIndex][0] = giaoDich.getMaGiaoDich();
            data[rowIndex][1] = giaoDich.getSoTaiKhoan();

            String tenKhachHang = giaoDich.getTenKhachHang();
            if (tenKhachHang.equals("null null") || tenKhachHang == null) {
                data[rowIndex][2] = "<html><p style='color:rgb(255,0,0);font-weight:bold;'>Hệ thống</p></html>";
            } else {
                data[rowIndex][2] = giaoDich.getTenKhachHang();
            }

            data[rowIndex][3] = giaoDich.getSoTien();
            data[rowIndex][4] = fDate.toString(giaoDich.getNgayGiaoDich());
            data[rowIndex][5] = giaoDich.getTenLoaiGiaoDich();
            data[rowIndex][6] = giaoDich.getTenNhanVien();
            data[rowIndex][7] = giaoDich.getTenTrangThai();
            rowIndex++;
        }
        return data;
    }

    public Object[][] doiSangObjectGiaoDich(boolean isFiltered, boolean isSearched, List<GiaoDichDTO> listGiaoDich) {
        List<GiaoDichDTO> list = new ArrayList<>();

        list = (isFiltered || isSearched) ? listGiaoDich : getDSGiaoDich();

        Object[][] data = new Object[list.size()][8];
        int rowIndex = 0;
        for (GiaoDichDTO giaoDich : list) {
            data[rowIndex][0] = giaoDich.getMaGiaoDich();

            int maTrangThaiTKKH = giaoDich.getMaTrangThaiTKKH();
            int biXoa = giaoDich.getBiXoa();

            if (maTrangThaiTKKH == 1 || biXoa == 1) {
                data[rowIndex][1] = "<html><p style='color:rgb(255,0,0);'>" + giaoDich.getSoTaiKhoan() + "</p></html>";
            } else {
                data[rowIndex][1] = giaoDich.getSoTaiKhoan();
            }

            String tenKhachHang = giaoDich.getTenKhachHang();

            if (tenKhachHang.equals("null null") || tenKhachHang == null) {
                data[rowIndex][2] = "<html><p style='color:rgb(255,0,0);font-weight:bold;'>Hệ thống</p></html>";
            } else {
                data[rowIndex][2] = giaoDich.getTenKhachHang();
            }

            data[rowIndex][3] = FormatNumber.convertNumToVND(new BigInteger(giaoDich.getSoTien())) + " VND";
            data[rowIndex][4] = fDate.toString(giaoDich.getNgayGiaoDich());
            data[rowIndex][5] = giaoDich.getTenLoaiGiaoDich();
            data[rowIndex][6] = giaoDich.getTenNhanVien();
            data[rowIndex][7] = giaoDich.getTenTrangThai();
            rowIndex++;
        }
        return data;
    }

    public List<GiaoDichDTO> locGiaoDich(java.util.Date dateFrom, java.util.Date dateTo, int maKhachHang, int maNhanVien, int maTaiKhoanKH, int maLoaiGiaoDich) {
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

        return giaoDichDAO.filter(dateBatDau, dateKetThuc, maKhachHang, maNhanVien, maTaiKhoanKH, maLoaiGiaoDich);
    }

    public List<GiaoDichDTO> timKiemTheoLoai(String typeName, String inputValue) {
        return giaoDichDAO.searchByInputType(typeName, inputValue);
    }

    public boolean napTien(GiaoDichDTO giaoDich) {
        layTienTuKho();

        BigInteger tienTK = new BigInteger(khoTien.getTienTKNganHang());
        BigInteger tienMat = new BigInteger(khoTien.getTienMat());
        BigInteger soTien = new BigInteger(String.valueOf(giaoDich.getSoTien()));

        boolean isSufficient = tienTK.subtract(soTien).compareTo(BigInteger.ZERO) <= 0;
        if (isSufficient) {
            return false;
        } else {
            tienMat = tienMat.add(soTien);
            khoTien.setTienMat(tienMat.toString());

            tienTK = tienTK.subtract(soTien);
            khoTien.setTienTKNganHang(tienTK.toString());

            if (khoTienDAO.updateMoney(khoTien)) {
                try {
                    TaiKhoanKHDTO taiKhoanKH = taiKhoanKHDAO.selectById(giaoDich.getMaTaiKhoanKH());
                    BigInteger soDu = new BigInteger(taiKhoanKH.getSoDu());
                    soDu = soDu.add(soTien);

                    String newSoDu = soDu.toString();

                    if (themGiaoDich(giaoDich) == 0) {
                        return false;
                    }

                    return taiKhoanKHDAO.updateMoney(taiKhoanKH.getMaTKKH(), newSoDu);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }

            return false;
        }
    }

    public boolean rutTien(GiaoDichDTO giaoDich) {
        layTienTuKho();

        BigInteger tienTK = new BigInteger(khoTien.getTienTKNganHang());
        BigInteger tienMat = new BigInteger(khoTien.getTienMat());
        BigInteger soTien = new BigInteger(giaoDich.getSoTien());

        boolean isSufficient = tienMat.subtract(soTien).compareTo(BigInteger.ZERO) <= 0;
        if (isSufficient) {
            return false;
        } else {
            tienMat = tienMat.subtract(soTien);
            khoTien.setTienMat(tienMat.toString());

            tienTK = tienTK.add(soTien);
            khoTien.setTienTKNganHang(tienTK.toString());

            if (khoTienDAO.updateMoney(khoTien)) {
                try {
                    TaiKhoanKHDTO taiKhoanKH = taiKhoanKHDAO.selectById(giaoDich.getMaTaiKhoanKH());

                    BigInteger soDu = new BigInteger(taiKhoanKH.getSoDu());
                    soDu = soDu.subtract(soTien);
                    String newSoDu = soDu.toString();

                    if (themGiaoDich(giaoDich) == 0) {
                        return false;
                    }

                    return taiKhoanKHDAO.updateMoney(taiKhoanKH.getMaTKKH(), newSoDu);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }

            return false;
        }
    }

    public boolean chuyenTien(GiaoDichDTO giaoDich, boolean isTienTK, TaiKhoanKHDTO taiKhoanKHNhan, int maNganHang) {
        layTienTuKho();
        BigInteger tienTK = new BigInteger(khoTien.getTienTKNganHang());
        BigInteger tienMat = new BigInteger(khoTien.getTienMat());
        BigInteger soTien = new BigInteger(giaoDich.getSoTien());

        if (isTienTK) {

            try {
                TaiKhoanKHDTO taiKhoanKH = taiKhoanKHDAO.selectById(giaoDich.getMaTaiKhoanKH());
                BigInteger soDuNguoiGui = new BigInteger(taiKhoanKH.getSoDu());
                soDuNguoiGui = soDuNguoiGui.subtract(soTien);
                String newSoDuNguoiGui = soDuNguoiGui.toString();

                BigInteger soDuNguoiNhan = new BigInteger(taiKhoanKHNhan.getSoDu());
                soDuNguoiNhan = soDuNguoiNhan.add(soTien);
                String newSoDuNguoiNhan = soDuNguoiNhan.toString();

                if (!taiKhoanKHDAO.updateMoney(taiKhoanKHNhan.getMaTKKH(), newSoDuNguoiNhan)) {
                    return false;
                }

                int maGiaoDich = themGiaoDich(giaoDich);
                if (maGiaoDich == 0) {
                    return false;
                }

                ChuyenTienDTO chuyenTien = new ChuyenTienDTO();
                chuyenTien.setMaGiaoDich(maGiaoDich);
                chuyenTien.setMaTKNguoiNhan(taiKhoanKHNhan.getMaTKKH());
                chuyenTien.setMaNganHang(1);
                if (!chuyenTienDAO.insert(chuyenTien)) {
                    return false;
                }

                return taiKhoanKHDAO.updateMoney(taiKhoanKH.getMaTKKH(), newSoDuNguoiGui);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }

        } else {
            boolean isSufficient = tienTK.subtract(soTien).compareTo(BigInteger.ZERO) <= 0;
            if (isSufficient) {
                return false;
            } else {
                tienMat = tienMat.add(soTien);
                khoTien.setTienMat(tienMat.toString());

                tienTK = tienTK.subtract(soTien);
                khoTien.setTienTKNganHang(tienTK.toString());

                if (khoTienDAO.updateMoney(khoTien)) {
                    try {

                        BigInteger soDuNguoiNhan = new BigInteger(taiKhoanKHNhan.getSoDu());
                        soDuNguoiNhan = soDuNguoiNhan.add(soTien);
                        String newSoDuNguoiNhan = soDuNguoiNhan.toString();

                        if (!taiKhoanKHDAO.updateMoney(taiKhoanKHNhan.getMaTKKH(), newSoDuNguoiNhan)) {
                            return false;
                        }

                        int maGiaoDich = themGiaoDich(giaoDich);
                        if (maGiaoDich == 0) {
                            return false;
                        }

                        ChuyenTienDTO chuyenTien = new ChuyenTienDTO();
                        chuyenTien.setMaGiaoDich(maGiaoDich);
                        chuyenTien.setMaTKNguoiNhan(taiKhoanKHNhan.getMaTKKH());
                        chuyenTien.setMaNganHang(maNganHang);

                        return chuyenTienDAO.insert(chuyenTien);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
                }

                return false;
            }
        }
    }

    public ChuyenTienDTO getByIdGD(int maGiaoDich) {
        return chuyenTienDAO.selectByIdGD(maGiaoDich);
    }

    public boolean truTienTKTrongKho(String soTienTru) {
        layTienTuKho();

        BigInteger tienTK = new BigInteger(khoTien.getTienTKNganHang());
        BigInteger tienMat = new BigInteger(khoTien.getTienMat());
        BigInteger soTien = new BigInteger(soTienTru);

        boolean isSufficient = tienTK.subtract(soTien).compareTo(BigInteger.ZERO) <= 0;
        if (isSufficient) {
            return false;
        } else {
            khoTien.setTienMat(tienMat.toString());

            tienTK = tienTK.subtract(soTien);
            khoTien.setTienTKNganHang(tienTK.toString());

            return khoTienDAO.updateMoney(khoTien);
        }
    }

    public boolean themTienTKTrongKho(String soTienThem) {
        layTienTuKho();

        BigInteger tienTK = new BigInteger(khoTien.getTienTKNganHang());
        BigInteger tienMat = new BigInteger(khoTien.getTienMat());
        BigInteger soTien = new BigInteger(soTienThem);

        khoTien.setTienMat(tienMat.toString());

        tienTK = tienTK.add(soTien);
        khoTien.setTienTKNganHang(tienTK.toString());

        return khoTienDAO.updateMoney(khoTien);
    }

    public boolean themTienMatTrongKho(String soTienThem) {
        layTienTuKho();

        BigInteger tienTK = new BigInteger(khoTien.getTienTKNganHang());
        BigInteger tienMat = new BigInteger(khoTien.getTienMat());
        BigInteger soTien = new BigInteger(soTienThem);

        khoTien.setTienTKNganHang(tienTK.toString());

        tienMat = tienMat.add(soTien);
        khoTien.setTienMat(tienMat.toString());

        return khoTienDAO.updateMoney(khoTien);
    }

    public boolean chuyenTienLaiTKVeTK(GiaoDichDTO giaoDich) {
        if (truTienTKTrongKho(giaoDich.getSoTien())) {
            System.out.println("Da tru tien trong kho!");
            try {
                BigInteger soTien = new BigInteger(String.valueOf(giaoDich.getSoTien()));

                TaiKhoanKHDTO taiKhoanKH = taiKhoanKHDAO.selectById(giaoDich.getMaTaiKhoanKH());

                if (taiKhoanKH == null) {
                    System.out.println("Khong tim thay TKKH!");
                } else {
                    System.out.println("Lay thong tin TKKH thanh cong!");
                }
                BigInteger soDu = new BigInteger(taiKhoanKH.getSoDu());
                soDu = soDu.add(soTien);

                System.out.println("So du moi: " + soDu.toString());

                String newSoDu = soDu.toString();

                if (themGiaoDich(giaoDich) == 0) {
                    System.out.println("Them giao dich that bai!");
                    return false;
                }

                return taiKhoanKHDAO.updateMoney(taiKhoanKH.getMaTKKH(), newSoDu);
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }

    public String thanhToanKhoanVay(GiaoDichDTO giaoDich, boolean isTienTuTK) {

        if (giaoDich == null) {
            return "Lỗi giao dịch";
        }

        BigInteger soTienGiaoDich = new BigInteger(giaoDich.getSoTien());

        if (soTienGiaoDich.compareTo(BigInteger.ZERO) <= 0) {
            return "Số tiền giao dịch không hợp lệ";
        }

        if (isTienTuTK) {
            try {
                TaiKhoanKHDTO taiKhoanKH = taiKhoanKHDAO.selectById(giaoDich.getMaTaiKhoanKH());

                if (taiKhoanKH == null) {
                    return "Không tìm thấy tài khoản khách hàng";
                }

                BigInteger soDu, soDuMoi;
                soDu = new BigInteger(taiKhoanKH.getSoDu());

                soDuMoi = soDu.subtract(soTienGiaoDich);

                if (soDuMoi.compareTo(BigInteger.ZERO) < 0) {
                    return "Số dư tài khoản không đủ!";
                }

                if (!taiKhoanKHDAO.updateMoney(taiKhoanKH.getMaTKKH(), soDuMoi.toString())) {
                    return "Cập nhật số dư mới cho tài khoản thất bại!";
                }

                if (!themTienTKTrongKho(soTienGiaoDich.toString())) {
                    return "Lỗi chuyển tiền từ tài khoản vào kho!";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Lỗi trong quá trình xử lý giao dịch!";
            }
        } else {
            if (!themTienMatTrongKho(soTienGiaoDich.toString())) {
                return "Lỗi chuyển tiền mặt vào kho!";
            }
        }

        if (themGiaoDich(giaoDich) == 0) {
            return "Lỗi thực hiện giao dịch!";
        }
        return "1";
    }

    private int themGiaoDich(GiaoDichDTO giaoDich) {
        try {
            return giaoDichDAO.insert(giaoDich);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public GiaoDichDTO getGiaoDichById(int maGiaoDich) {
        try {
            return giaoDichDAO.selectById(maGiaoDich);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean xuatExcel(File file, String userName, List<GiaoDichDTO> list) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Danh sách thông tin giao dịch");

            String[] titles = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Trạng thái"};
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
            for (GiaoDichDTO giaoDich : list) {
                Row dataRow = sheet.createRow(rowCount++);
                cell = dataRow.createCell(0);
                cell.setCellValue(giaoDich.getMaGiaoDich());
                cell = dataRow.createCell(1);
                cell.setCellValue(giaoDich.getSoTaiKhoan());
                cell = dataRow.createCell(2);
                cell.setCellValue(giaoDich.getTenKhachHang());
                cell = dataRow.createCell(3);
                cell.setCellValue(FormatNumber.convertNumToVND(new BigInteger(giaoDich.getSoTien())) + " VND");
                cell = dataRow.createCell(4);
                cell.setCellValue(fDate.toString(giaoDich.getNgayGiaoDich()));
                cell = dataRow.createCell(5);
                cell.setCellValue(giaoDich.getTenLoaiGiaoDich());
                cell = dataRow.createCell(6);
                cell.setCellValue(giaoDich.getTenNhanVien());
                cell = dataRow.createCell(7);
                cell.setCellValue(giaoDich.getTenTrangThai());
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
