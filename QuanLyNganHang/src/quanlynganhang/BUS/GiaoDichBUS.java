package quanlynganhang.BUS;

import java.math.BigInteger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.BUS.validation.FormatDate;
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
    
    public Object[][] doiSangObjectGiaoDich(boolean isFiltered, List<GiaoDichDTO> listGiaoDich) {
        List<GiaoDichDTO> list = new ArrayList<>();

        if (isFiltered) {
            list = listGiaoDich;
        } else {
            list = getDSGiaoDich();
        }

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
    
    public List<GiaoDichDTO> locGiaoDich(java.util.Date dateFrom, java.util.Date dateTo, int maKhachHang, int maNhanVien, int maTaiKhoanKH, int maLoaiGiaoDich) throws Exception {
        java.sql.Date dateBatDau = null;
        java.sql.Date dateKetThuc = null;

        if (dateFrom != null && dateTo != null) {
            dateBatDau = new Date(dateFrom.getTime());
            dateKetThuc = new Date(dateTo.getTime());
        }

        return giaoDichDAO.filter(dateBatDau, dateKetThuc, maKhachHang, maNhanVien, maTaiKhoanKH, maLoaiGiaoDich);
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

    public boolean truTienTKTrongKho(String soTienTru) {
        layTienTuKho();

        BigInteger tienTK = new BigInteger(khoTien.getTienTKNganHang());
        BigInteger tienMat = new BigInteger(khoTien.getTienMat());
        BigInteger soTien = new BigInteger(soTienTru);

        boolean isSufficient = tienMat.subtract(soTien).compareTo(BigInteger.ZERO) <= 0;
        if (isSufficient) {
            return false;
        } else {
            khoTien.setTienMat(tienMat.toString());

            tienTK = tienTK.subtract(soTien);
            khoTien.setTienTKNganHang(tienTK.toString());

            if (khoTienDAO.updateMoney(khoTien)) {
                return true;
            }

            return false;
        }
    }

    public boolean themTienTKTrongKho(String soTienThem) {
        layTienTuKho();

        BigInteger tienTK = new BigInteger(khoTien.getTienTKNganHang());
        BigInteger tienMat = new BigInteger(khoTien.getTienMat());
        BigInteger soTien = new BigInteger(soTienThem);

        boolean isSufficient = tienMat.subtract(soTien).compareTo(BigInteger.ZERO) <= 0;
        if (isSufficient) {
            return false;
        } else {
            khoTien.setTienMat(tienMat.toString());

            tienTK = tienTK.add(soTien);
            khoTien.setTienTKNganHang(tienTK.toString());

            if (khoTienDAO.updateMoney(khoTien)) {
                return true;
            }

            return false;
        }
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

    private int themGiaoDich(GiaoDichDTO giaoDich) {
        try {
            return giaoDichDAO.insert(giaoDich);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}
