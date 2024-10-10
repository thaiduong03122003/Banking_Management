package quanlynganhang.BUS;

import quanlynganhang.DAO.ChucVuDAO;
import quanlynganhang.DAO.NhanVienDAO;
import quanlynganhang.DAO.TaiKhoanNVDAO;
import quanlynganhang.DAO.TinhTrangDangNhapDAO;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;

public class DangNhapBUS {

    private final TaiKhoanNVDAO taiKhoanNVDAO = new TaiKhoanNVDAO();
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private final ChucVuDAO chucVuDAO = new ChucVuDAO();
    private final TinhTrangDangNhapDAO tinhTrangDangNhapDAO = new TinhTrangDangNhapDAO();

    public TaiKhoanNVDTO kiemTraDangNhap(String tenDangNhap, String matKhau) {
        TaiKhoanNVDTO taiKhoanNV = taiKhoanNVDAO.selectByUserName(tenDangNhap);

        if (MaHoaMatKhauBUS.checkPassword(taiKhoanNV.getMatKhau(), matKhau)) {
            return taiKhoanNV;
        } else {
            return null;
        }
    }

    public int kiemTraChucVu(TaiKhoanNVDTO taiKhoanNV) {
        NhanVienDTO nhanVien = nhanVienDAO.selectById(taiKhoanNV.getMaNhanVien(), 0);
        if (nhanVien != null && nhanVien.getMaChucVu() != 1) {
            ChucVuDTO chucVu = chucVuDAO.selectById(nhanVien.getMaChucVu(), 0);
            return chucVu.getIsAdmin();
        } else {
            return 2;
        }
    }

    public int dangNhap(int maTaiKhoanNV) {
        return tinhTrangDangNhapDAO.insert(maTaiKhoanNV, true);
    }

    public boolean dangXuat(int maDangNhap) {
        return tinhTrangDangNhapDAO.loginAccount(maDangNhap, false);
    }
}
