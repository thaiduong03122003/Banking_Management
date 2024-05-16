package quanlynganhang.BUS;

import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DAO.ChucVuDAO;
import quanlynganhang.DAO.NhanVienDAO;
import quanlynganhang.DAO.TaiKhoanNVDAO;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;

public class DangNhapBUS {

    private final TaiKhoanNVDAO taiKhoanNVDAO = new TaiKhoanNVDAO();
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private final ChucVuDAO chucVuDAO = new ChucVuDAO();

    public TaiKhoanNVDTO kiemTraDangNhap(String tenDangNhap, String matKhau) {
        try {
            TaiKhoanNVDTO taiKhoanNV = taiKhoanNVDAO.selectByUserName(tenDangNhap);

            if (MaHoaMatKhauBUS.checkPassword(taiKhoanNV.getMatKhau(), matKhau)) {
                return taiKhoanNV;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public int kiemTraChucVu(TaiKhoanNVDTO taiKhoanNV) {
        try {
            NhanVienDTO nhanVien = nhanVienDAO.selectById(taiKhoanNV.getMaNhanVien(), 0);
            if (nhanVien != null && nhanVien.getMaChucVu() != 1) {
                ChucVuDTO chucVu = chucVuDAO.selectById(nhanVien.getMaChucVu(), 0);
                return chucVu.getIsAdmin();
            } else {
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }
}
