package quanlynganhang.BUS;

import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DAO.ThongKeDAO;

public class ThongKeBUS {
    private final ThongKeDAO thongKeDAO = new ThongKeDAO();
    
    public ThongKeBUS() {
        
    }
    
    public String tongTien() {
        try {
            return thongKeDAO.tongTien();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }
    
    public int tongKH() {
        try {
            return thongKeDAO.tongKhachHang();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int tongNV() {
        try {
            return thongKeDAO.tongNhanVien();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int tongTKKH() {
        try {
            return thongKeDAO.tongTaiKhoanKH();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int tongTKNV() {
        try {
            return thongKeDAO.tongTaiKhoanNV();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int tongThe() {
        try {
            return thongKeDAO.tongThe();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int tongGiaoDich() {
        try {
            return thongKeDAO.tongGiaoDich();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int tongLuotRut() {
        try {
            return thongKeDAO.tongLuotRutTien();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int tongLuotNap() {
        try {
            return thongKeDAO.tongLuotNapTien();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public String maxTien() {
        try {
            return thongKeDAO.soTienLonNhat();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
    }
    
    public int tongGTK() {
        try {
            return thongKeDAO.tongGuiTK();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int tongVV() {
        try {
            return thongKeDAO.tongVayVon();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    public String tongTienTrongKho() {
        try {
            return thongKeDAO.tongTienTrongKho();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
    }
    
    public String tongTienRut() {
        try {
            return thongKeDAO.tongTienRut();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
    }
    
    public String tongTienNap() {
        try {
            return thongKeDAO.tongTienNap();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0";
        }
    }
}
