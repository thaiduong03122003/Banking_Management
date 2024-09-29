package quanlynganhang.BUS;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.DAO.KyHanGuiTKDAO;
import quanlynganhang.DAO.TaiKhoanKHDAO;
import quanlynganhang.DAO.TietKiemDAO;
import quanlynganhang.DAO.TraKhoanVayDAO;
import quanlynganhang.DAO.VayVonDAO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.KyHanGuiTKDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.TietKiemDTO;
import quanlynganhang.DTO.TraKhoanVayDTO;
import quanlynganhang.DTO.VayVonDTO;

public class KiemTraDuLieuBUS {

    private final TietKiemDAO tietKiemDAO = new TietKiemDAO();
    private final FormatDate fDate = new FormatDate();
    private final TaiKhoanKHDAO taiKhoanKHDAO = new TaiKhoanKHDAO();
    private final KyHanGuiTKDAO kyHanGuiTKDAO = new KyHanGuiTKDAO();
    private final VayVonDAO vayVonDAO = new VayVonDAO();
    private final TraKhoanVayDAO traVayDAO = new TraKhoanVayDAO();
    private final VayVonBUS vayVonBUS = new VayVonBUS();
    private final TraKhoanVayBUS traVayBUS = new TraKhoanVayBUS();
    private Date toDay;

    public KiemTraDuLieuBUS() {
        toDay = fDate.getToday();
    }

    private final GiaoDichBUS giaoDichBUS = new GiaoDichBUS();
    private TaiKhoanNVDTO taiKhoanNV;

    public KiemTraDuLieuBUS(TaiKhoanNVDTO taiKhoanNV) {
        this.taiKhoanNV = taiKhoanNV;
        toDay = fDate.getToday();
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

    public void chayKiemTraTinhTrangVayVon() {
        System.out.println("Dang kiem tra tinh trang vay von");

        List<VayVonDTO> listVayVon = vayVonBUS.getDSVayVon();
        if (listVayVon != null && !listVayVon.isEmpty()) {
            for (VayVonDTO vayVon : listVayVon) {
                xuLyTaiKhoanVay(vayVon);
            }
        } else {
            System.out.println("Chua co khoan vay nao can thanh toan!");
        }
    }

    public void xuLyTaiKhoanVay(VayVonDTO vayVon) {
        int soNgay = fDate.tinhSoNgay(toDay, vayVon.getNgayTraNo());

        System.out.println("So ngay vay: " + soNgay);
        if (soNgay >= 1) {

            List<TraKhoanVayDTO> listTraVay = traVayBUS.getDSByMaVayVon(vayVon.getMaVayVon());
            int soThang = fDate.tinhSoThang(toDay, vayVon.getNgayTraNo());

            System.out.println("So thang: " + soThang);

            if (soThang > vayVon.getSoThoiHan()) {
                soThang = vayVon.getSoThoiHan();
            }

            int kyVay;

            if (listTraVay != null && !listTraVay.isEmpty()) {
                TraKhoanVayDTO traVay = listTraVay.get(0);

                if (traVay.getKyTraNo() < soThang) {
                    kyVay = traVay.getKyTraNo() + 1;
                    capNhatKyTraVay(vayVon, kyVay, soThang);
                }
            } else {
                kyVay = 1;
                capNhatKyTraVay(vayVon, kyVay, soThang);
            }
        }
        capNhatLaiTienLai(vayVon);
        xuLyTienPhat(vayVon);
    }

    private void capNhatKyTraVay(VayVonDTO vayVon, int kyVay, int soThang) {
        TraKhoanVayDTO traVay;
        int kyTraVay = kyVay;
        for (int i = 0; i <= soThang; i++) {
            traVay = new TraKhoanVayDTO();
            traVay.setMaVayVon(vayVon.getMaVayVon());
            traVay.setKyTraNo(kyTraVay);
            traVay.setThoiGian(fDate.addMonth(vayVon.getNgayTraNo(), i));

            System.out.println("Thoi gian: " + traVay.getThoiGian());

            traVay.setSoTienDaTra("0");
            traVay.setTienConThieu("0");
            traVay.setTienNoGoc(tinhTienVayTheoKy(vayVon.getSoTienVay(), vayVon.getSoThoiHan()));
            traVay.setTienLai("0");
            traVay.setTienPhat("0");
            traVay.setMaTrangThai(8);

            traVayBUS.addTraKhoanNo(traVay);
            if (fDate.tinhSoThang(fDate.addMonth(vayVon.getNgayTraNo(), i), vayVon.getNgayHetThoiHan()) > 0) {
                System.out.println("Da den gioi han!");
                return;
            }

            vayVonBUS.updateNgayTraNo(vayVon.getMaVayVon(), fDate.addMonth(vayVon.getNgayTraNo(), i));
            kyTraVay++;
        }
    }

    public void capNhatLaiTienLai(VayVonDTO vayVon) {
        List<TraKhoanVayDTO> listTraVay = traVayBUS.getDSByMaVayVon(vayVon.getMaVayVon());

        if (listTraVay != null && !listTraVay.isEmpty()) {
            for (TraKhoanVayDTO traVay : listTraVay) {
                if (traVay.getMaTrangThai() == 14 || traVay.getMaTrangThai() == 15 || traVay.getMaTrangThai() == 9) {
                    continue;
                }

                String tienLai = tinhTienLaiVay(vayVon.getDuNoGoc(), vayVon.getLaiSuat(), vayVon.getSoThoiHan());

                traVayBUS.updateTienLai(traVay.getMaKyTraNo(), tienLai);
            }
        }
    }

    private String tinhTienVayTheoKy(String soTien, int soThang) {
        BigInteger soTienVay = new BigInteger(soTien);
        BigInteger tienGocMoiKy = soTienVay.divide(BigInteger.valueOf(soThang));

        return tienGocMoiKy.toString();
    }

    //TINH THEO CONG THUC DU NO GIAM DAN
    private String tinhTienLaiVay(String duNo, double laiSuat, int soThang) {

        BigInteger duNoConLai = new BigInteger(duNo);
        BigInteger laiSuatVay, phanTramLai, soThangVay, result;

        laiSuatVay = new BigInteger(String.valueOf((int) (laiSuat * 10)));
        phanTramLai = new BigInteger("1000");
        soThangVay = new BigInteger(String.valueOf(soThang));

        result = duNoConLai.multiply(laiSuatVay);
        result = result.divide(phanTramLai);
        result = result.divide(soThangVay);

        return result.toString();
    }


    private void xuLyTienPhat(VayVonDTO vayVon) {
        System.out.println("Dang xu ly tien phat");

        List<TraKhoanVayDTO> listTraVay = traVayBUS.getDSByMaVayVon(vayVon.getMaVayVon());

        if (listTraVay != null && !listTraVay.isEmpty()) {
            kiemTraTienPhat(listTraVay, vayVon);
        }
    }

    private void kiemTraTienPhat(List<TraKhoanVayDTO> listTraVay, VayVonDTO vayVon) {
        int soThang;
        for (TraKhoanVayDTO traVay : listTraVay) {
            if (traVay.getMaTrangThai() == 9) {
                continue;
            }

            soThang = fDate.tinhSoThang(toDay, traVay.getThoiGian());

            if (soThang >= 1) {
                System.out.println("Dang cap nhat trang thai cho ma " + traVay.getMaKyTraNo());

                if (traVay.getMaTrangThai() == 14) {
                    System.out.println("Phat hien co khoan vay chua tra du!");

                    if (traVayBUS.updateTrangThai(traVay.getMaKyTraNo(), 15)) {
                        System.out.println("Kiem tra lai phat thanh cong cho ma " + traVay.getMaKyTraNo());
                        tinhTienPhat(traVay.getMaKyTraNo(), vayVon);
                    } else {
                        System.out.println("Kiem tra lai phat that cua ma " + traVay.getMaKyTraNo() + " that bai!");
                    }
                } else if (traVay.getMaTrangThai() == 8) {
                    if (traVayBUS.updateTrangThai(traVay.getMaKyTraNo(), 13)) {
                        System.out.println("Kiem tra lai phat thanh cong cho ma " + traVay.getMaKyTraNo());
                        tinhTienPhat(traVay.getMaKyTraNo(), vayVon);
                    } else {
                        System.out.println("Kiem tra lai phat that cua ma " + traVay.getMaKyTraNo() + " that bai!");
                    }
                }
            }
        }
    }

    private void tinhTienPhat(int maKyTraNo, VayVonDTO vayVon) {
        System.out.println("Dang tinh tien phat");

        TraKhoanVayDTO traVay = traVayBUS.getTraKhoanVayById(maKyTraNo);

        if (traVay != null && (traVay.getMaTrangThai() == 13 || traVay.getMaTrangThai() == 15)) {
            tinhTienPhat(traVay, vayVon);
        }
    }

    private void tinhTienPhat(TraKhoanVayDTO traVay, VayVonDTO vayVon) {
        BigInteger soTienNoGoc, soTienDaTra, soTienLai, tongSoTienTreHan, laiTreHan, phanTramLai, kyHanVay, soThangTreHan, result;
        double laiPhat = vayVon.getLaiSuat() * 150;
        int soThang;

        laiTreHan = new BigInteger(String.valueOf((int) (laiPhat * 10)));
        phanTramLai = new BigInteger("100000");
        soTienNoGoc = new BigInteger(traVay.getTienNoGoc());
        soTienLai = new BigInteger(traVay.getTienLai());
        tongSoTienTreHan = soTienNoGoc.add(soTienLai);

        //CAP NHAT SO TIEN PHAT NEU TRA CHUA DU
        if (traVay.getMaTrangThai() == 15) {
            soTienDaTra = new BigInteger(traVay.getSoTienDaTra());
            tongSoTienTreHan = tongSoTienTreHan.subtract(soTienDaTra);
        }

        kyHanVay = new BigInteger(String.valueOf(vayVon.getSoThoiHan()));
        soThang = fDate.tinhSoThang(toDay, fDate.addMonth(traVay.getThoiGian(), 1));

        if (soThang < 0) {
            return;
        }

        soThangTreHan = new BigInteger(String.valueOf(soThang));

        result = tongSoTienTreHan.multiply(laiTreHan);
        result = result.divide(phanTramLai);
        result = result.divide(kyHanVay);
        result = result.multiply(soThangTreHan);

        if (result == null) {
            return;
        }

        if (traVayBUS.updateTienPhat(traVay.getMaKyTraNo(), result.toString())) {
            System.out.println("Cap nhat lai phat thanh cong cho ma " + traVay.getMaKyTraNo());
        } else {
            System.out.println("Cap nhat lai phat that cua ma " + traVay.getMaKyTraNo() + " that bai!");
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
