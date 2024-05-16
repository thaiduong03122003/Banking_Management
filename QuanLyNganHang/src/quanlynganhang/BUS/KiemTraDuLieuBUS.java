package quanlynganhang.BUS;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.DAO.KyHanGuiTKDAO;
import quanlynganhang.DAO.TaiKhoanKHDAO;
import quanlynganhang.DAO.TietKiemDAO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.KyHanGuiTKDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.TietKiemDTO;

public class KiemTraDuLieuBUS {

    private final TietKiemDAO tietKiemDAO = new TietKiemDAO();
    private final FormatDate fDate = new FormatDate();
    private final TaiKhoanKHDAO taiKhoanKHDAO = new TaiKhoanKHDAO();
    private final KyHanGuiTKDAO kyHanGuiTKDAO = new KyHanGuiTKDAO();
    public KiemTraDuLieuBUS() {
    }
    private final GiaoDichBUS giaoDichBUS = new GiaoDichBUS();
    private TaiKhoanNVDTO taiKhoanNV;

    public KiemTraDuLieuBUS(TaiKhoanNVDTO taiKhoanNV) {
        this.taiKhoanNV = taiKhoanNV;
    }

    private GiaoDichDTO thongTinGD(int maTKKH, String soTien, String noiDungGD) {
        GiaoDichDTO giaoDich = new GiaoDichDTO();
        giaoDich.setMaTaiKhoanKH(maTKKH);
        giaoDich.setNgayGiaoDich(fDate.getToday());
        giaoDich.setSoTien(soTien);
        giaoDich.setNoiDungGiaoDich(noiDungGD);
        giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaTKNV());
        giaoDich.setMaLoaiGiaoDich(5);
        giaoDich.setMaTrangThai(4);

        return giaoDich;
    }

    public void chayKiemTraTinhTrangGTK() {
        try {
            System.out.println("Dang kiem tra tinh trang!");

            List<TietKiemDTO> listTK = tietKiemDAO.selectAll();
            TaiKhoanKHDTO taiKhoanKH;
            if (listTK != null) {
                for (TietKiemDTO tietKiemItem : listTK) {

                    Date ktNgayNhanLai = tietKiemItem.getNgayNhanLai();

                    System.out.println("Gia tri ban dau: " + ktNgayNhanLai.getTime());
                    
                    while (ktNgayNhanLai.getTime() < fDate.getToday().getTime()) {
                        System.out.println("Kiem tra ngay nhan lai thanh cong!");

                        taiKhoanKH = taiKhoanKHDAO.selectById(tietKiemItem.getMaTaiKhoanTK());
                        TietKiemDTO tietKiem = tietKiemDAO.selectById(tietKiemItem.getMaGuiTK());

                        //KHÔNG GIA HẠN
                        if (tietKiem.getHinhThucGiaHan().equals("Không gia hạn")) {
                            String soTienLai = soTienLai(taiKhoanKH, tietKiem, fDate.getToday(), false);
                            BigInteger soDu = new BigInteger(taiKhoanKH.getSoDu());
                            BigInteger tienLai = new BigInteger(soTienLai);

                            if (tietKiem.getHinhThucNhanLai().equals("Chuyển về tài khoản nguồn") && tietKiem.getMaTaiKhoanNguonTien() != 0) {
                                soDu = soDu.add(tienLai);

                                if (giaoDichBUS.truTienTKTrongKho(soTienLai)) {
                                    System.out.println("So du moi: " + soDu);
                                    taiKhoanKHDAO.updateMoney(tietKiem.getMaTaiKhoanTK(), soDu.toString());

                                    TaiKhoanKHDTO taiKhoanNguon = taiKhoanKHDAO.selectById(tietKiem.getMaTaiKhoanNguonTien());

                                    GiaoDichDTO giaoDich = thongTinGD(taiKhoanKH.getMaTKKH(), soDu.toString(), "Chuyển tiền tiết kiệm về tài khoản " + taiKhoanNguon.getSoTaiKhoan());

                                    giaoDichBUS.chuyenTien(giaoDich, true, taiKhoanNguon, 1);
                                }

                            } else if (tietKiem.getHinhThucNhanLai().equals("Chuyển về tài khoản tiết kiệm")) {
                                GiaoDichDTO giaoDich = thongTinGD(taiKhoanKH.getMaTKKH(), soTienLai, "Chuyển tiền lãi tiết kiệm về tài khoản " + taiKhoanKH.getSoTaiKhoan());
                                giaoDichBUS.chuyenTienLaiTKVeTK(giaoDich);
                            }

                            tietKiemDAO.updateTrangThai(tietKiem.getMaGuiTK(), 11);

                            break;
                            //GIA HẠN GỐC
                        } else if (tietKiem.getHinhThucGiaHan().equals("Gia hạn với số tiền gốc")) {
                            String soTienLai = soTienLai(taiKhoanKH, tietKiem, fDate.getToday(), false);
                            BigInteger soDu = new BigInteger(taiKhoanKH.getSoDu());
                            BigInteger tienLai = new BigInteger(soTienLai);

                            if (tietKiem.getHinhThucNhanLai().equals("Chuyển về tài khoản nguồn") && tietKiem.getMaTaiKhoanNguonTien() != 0) {

                                if (giaoDichBUS.truTienTKTrongKho(soTienLai)) {

                                    TaiKhoanKHDTO taiKhoanNguon = taiKhoanKHDAO.selectById(tietKiem.getMaTaiKhoanNguonTien());

                                    GiaoDichDTO giaoDich = thongTinGD(taiKhoanKH.getMaTKKH(), tienLai.toString(), "Chuyển tiền lãi tiết kiệm về tài khoản " + taiKhoanNguon.getSoTaiKhoan());

                                    giaoDichBUS.chuyenTien(giaoDich, true, taiKhoanNguon, 1);
                                }

                            } else if (tietKiem.getHinhThucNhanLai().equals("Chuyển về tài khoản tiết kiệm")) {
                                GiaoDichDTO giaoDich = thongTinGD(taiKhoanKH.getMaTKKH(), soTienLai, "Chuyển tiền lãi tiết kiệm về tài khoản " + taiKhoanKH.getSoTaiKhoan());
                                giaoDichBUS.chuyenTienLaiTKVeTK(giaoDich);
                            }

                            Date newNgayNhanLai = fDate.addMonth(tietKiem.getNgayNhanLai(), tietKiem.getSoKyHan());

                            ktNgayNhanLai = newNgayNhanLai;

                            Date newNgayMoTK = fDate.addMonth(tietKiem.getNgayMoTK(), tietKiem.getSoKyHan());

                            tietKiemDAO.updateTietKiem(tietKiem.getMaGuiTK(), newNgayMoTK, newNgayNhanLai, tietKiem.getSoTienGoc());
                            //GIA HẠN CẢ GỐC VÀ LÃI
                        } else {
                            System.out.println("Dang xu ly gia han!");

                            String soTienLai = soTienLai(taiKhoanKH, tietKiem, fDate.getToday(), false);
                            BigInteger soDu = new BigInteger(taiKhoanKH.getSoDu());
                            BigInteger tienLai = new BigInteger(soTienLai);

                            if (tietKiem.getHinhThucNhanLai().equals("Chuyển về tài khoản tiết kiệm")) {

                                System.out.println("Dang thuc hien chuyen tien ve tktk");

                                GiaoDichDTO giaoDich = thongTinGD(taiKhoanKH.getMaTKKH(), soTienLai, "Chuyển tiền lãi tiết kiệm về tài khoản " + taiKhoanKH.getSoTaiKhoan());
                                if (giaoDichBUS.chuyenTienLaiTKVeTK(giaoDich)) {
                                    System.out.println("Giao dich thanh cong!");
                                } else {
                                    System.out.println("Giao dich that bai!");
                                }
                            }

                            Date newNgayNhanLai = fDate.addMonth(tietKiem.getNgayNhanLai(), tietKiem.getSoKyHan());

                            ktNgayNhanLai = newNgayNhanLai;
                            
                            System.out.println("Ngay nhan lai moi: " + ktNgayNhanLai);

                            Date newNgayMoTK = fDate.addMonth(tietKiem.getNgayMoTK(), tietKiem.getSoKyHan());
                            
                            System.out.println("Ngay mo gui tiet kiem moi: " + newNgayMoTK);

                            String newSoTienGoc = (soDu.add(tienLai)).toString();
                            
                            System.out.println("So tien gui tiet kiem moi: " + newSoTienGoc);

                            tietKiemDAO.updateTietKiem(tietKiem.getMaGuiTK(), newNgayMoTK, newNgayNhanLai, newSoTienGoc);
                        }
                    }
                }
            } else {
                System.out.println("Danh sach kiem tra rong!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private String soTienLai(TaiKhoanKHDTO taiKhoanKH, TietKiemDTO tietKiem, Date homNay, boolean isTruocHan) {
        return soTienLaiNhan(tietKiem.getSoTienGoc(), tietKiem, homNay, isTruocHan);
    }
    
    public String soTienLaiNhan(String soTien, TietKiemDTO tietKiem, Date homNay, boolean isTruocHan) {
        BigInteger soTienTinhLai = new BigInteger(soTien);
        BigInteger soNgay, laiSuat;

        if (isTruocHan) {
            KyHanGuiTKDTO kyHan = new KyHanGuiTKDTO();
            try {
                kyHan = kyHanGuiTKDAO.selectKyHanById(1);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            
            soNgay = new BigInteger(String.valueOf(fDate.tinhSoNgay(homNay, tietKiem.getNgayMoTK())));
            laiSuat = new BigInteger(String.valueOf((int) (kyHan.getLaiSuat() * 10)));
            
        } else {
            soNgay = new BigInteger(String.valueOf(fDate.tinhSoNgay(tietKiem.getNgayNhanLai(), tietKiem.getNgayMoTK())));
            laiSuat = new BigInteger(String.valueOf((int) (tietKiem.getLaiSuat() * 10)));
        }

        System.out.println("So ngay: " + soNgay);
        BigInteger nam = new BigInteger("365");

        BigInteger lai = new BigInteger("1000");

        BigInteger result = soTienTinhLai.multiply(laiSuat);

        result = result.divide(lai);
        result = result.multiply(soNgay);
        result = result.divide(nam);

        return result.toString();
    }

    public String soTienGTKUocTinh(TietKiemDTO tietKiem) {
        Date homNay = fDate.getToday();
        try {
            TaiKhoanKHDTO taiKhoanKH = taiKhoanKHDAO.selectById(tietKiem.getMaTaiKhoanTK());

            if (tietKiem != null && taiKhoanKH != null) {
                BigInteger soDu = new BigInteger(taiKhoanKH.getSoDu());
                BigInteger tienLai;

                if (taiKhoanKH.getSoDu().equals("0")) {
                    return "0";
                }
                if (homNay.equals(taiKhoanKH.getNgayTao())) {
                    return taiKhoanKH.getSoDu();
                } else if (tietKiem.getMaTrangThai() == 10) {
                    tienLai = new BigInteger(soTienLai(taiKhoanKH, tietKiem, homNay, true));
                    return (soDu.add(tienLai)).toString();
                } else {
                    
                    tienLai = new BigInteger(soTienLai(taiKhoanKH, tietKiem, homNay, false));
                    return (soDu.add(tienLai)).toString();
                }
            } else {
                return "0";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
    }
}
