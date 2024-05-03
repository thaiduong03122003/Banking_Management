package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import quanlynganhang.DTO.KhoaTaiKhoanDTO;

public class KhoaTaiKhoanDAO {

    public boolean insert(KhoaTaiKhoanDTO khoaTaiKhoan) throws Exception {

        String sql;
        String loaiTaiKhoan = khoaTaiKhoan.getLoaiTaiKhoan();
        System.out.println("Loai tai khoan: " + loaiTaiKhoan);
        if (loaiTaiKhoan.equals("TKNV")) {
            sql = "INSERT INTO tbl_khoa_tai_khoan(ma_tk_nhan_vien, ly_do_khoa, ngay_mo_khoa, loai_tai_khoan, mo_khoa)"
            + " VALUES (?, ?, ?, ?, ?)";
        } else if (loaiTaiKhoan.equals("TKKH")) {
            sql = "INSERT INTO tbl_khoa_tai_khoan(ma_tk_khach_hang, ly_do_khoa, ngay_mo_khoa, loai_tai_khoan, mo_khoa)"
            + " VALUES (?, ?, ?, ?, ?)";
        } else {
            sql = "INSERT INTO tbl_khoa_tai_khoan(ma_tk_khach_hang, ly_do_khoa, ngay_mo_khoa, loai_tai_khoan, mo_khoa)"
            + " VALUES (?, ?, ?, ?, ?)";
        }
        

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

            if (loaiTaiKhoan.equals("TKNV")) {
                pstmt.setInt(1, khoaTaiKhoan.getMaTaiKhoanNV());
            } else if (loaiTaiKhoan.equals("TKKH")) {
                pstmt.setInt(1, khoaTaiKhoan.getMaTaiKhoanKH());
            } else {
                
            }
            
            pstmt.setString(2, khoaTaiKhoan.getLyDoKhoa());

            java.sql.Date dateNgayMoKhoa = new Date(khoaTaiKhoan.getNgayMoKhoa().getTime());
            pstmt.setDate(3, dateNgayMoKhoa);

            pstmt.setString(4, khoaTaiKhoan.getLoaiTaiKhoan());
            pstmt.setInt(5, 0);

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean unlockNV(int maKhoaTaiKhoan) throws Exception {
        String sql = "UPDATE tbl_khoa_tai_khoan ktk LEFT JOIN tbl_tai_khoan_nhan_vien tknv ON ktk.ma_tk_nhan_vien = tknv.ma_tk_nhan_vien SET ktk.mo_khoa = ?, tknv.ma_trang_thai = ? WHERE ma_khoa_tai_khoan = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            pstmt.setInt(2, 6);
            pstmt.setInt(3, maKhoaTaiKhoan);

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean unlockKH(int maKhoaTaiKhoan) throws Exception {
        String sql = "UPDATE tbl_khoa_tai_khoan ktk LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON ktk.ma_tk_khach_hang = tkkh.ma_tk_khach_hang SET ktk.mo_khoa = ?, tkkh.ma_trang_thai = ? WHERE ma_khoa_tai_khoan = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            pstmt.setInt(2, 6);
            pstmt.setInt(3, maKhoaTaiKhoan);

            return pstmt.executeUpdate() > 0;
        }
    }

}
