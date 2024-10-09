package quanlynganhang.BUS;

import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DAO.ThongKeDAO;

public class ThongKeBUS {

    private final ThongKeDAO thongKeDAO = new ThongKeDAO();

    public ThongKeBUS() {
    }

    public String tongTien() {
        return thongKeDAO.tongTien();
    }

    public int tongKH() {
        return thongKeDAO.tongKhachHang();
    }

    public int tongNV() {
        return thongKeDAO.tongNhanVien();
    }

    public int tongTKKH() {
        return thongKeDAO.tongTaiKhoanKH();
    }

    public int tongTKNV() {
        return thongKeDAO.tongTaiKhoanNV();
    }

    public int tongThe() {
        return thongKeDAO.tongThe();
    }

    public int tongGiaoDich() {
        return thongKeDAO.tongGiaoDich();
    }

    public int tongLuotRut() {
        return thongKeDAO.tongLuotRutTien();
    }

    public int tongLuotNap() {
        return thongKeDAO.tongLuotNapTien();
    }

    public String maxTien() {
        return thongKeDAO.soTienLonNhat();
    }

    public int tongGTK() {
        return thongKeDAO.tongGuiTK();
    }

    public int tongVV() {
        return thongKeDAO.tongVayVon();
    }

    public String tongTienTrongKho() {
        return thongKeDAO.tongTienTrongKho();
    }

    public String tongTienRut() {
        return thongKeDAO.tongTienRut();
    }

    public String tongTienNap() {
        return thongKeDAO.tongTienNap();
    }

    public String tongtienVV() {
        return thongKeDAO.tongTienVayVon();
    }
}
