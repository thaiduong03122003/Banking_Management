package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import quanlynganhang.DTO.ChuyenTienDTO;

public class ChuyenTienDAO {

    public boolean insert(ChuyenTienDTO chuyenTien) throws Exception {

        String sql = "INSERT INTO tbl_dich_vu_chuyen_tien(ma_tk_nguoi_nhan, ma_ngan_hang, ma_giao_dich)"
            + " VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

            pstmt.setInt(1, chuyenTien.getMaTKNguoiNhan());
            pstmt.setInt(2, chuyenTien.getMaNganHang());
            pstmt.setInt(3, chuyenTien.getMaGiaoDich());

            return pstmt.executeUpdate() > 0;
        }
    }

    public ChuyenTienDTO selectByIdGD(int maGiaoDich) {
        String sql = "SELECT ct.*, tkkh.*, kh.*, nh.* FROM tbl_dich_vu_chuyen_tien ct"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON ct.ma_tk_nguoi_nhan = tkkh.ma_tk_khach_hang"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_ngan_hang nh ON ct.ma_ngan_hang = nh.ma_ngan_hang"
            + " WHERE ct.ma_giao_dich = ? LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maGiaoDich);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ChuyenTienDTO chuyenTien = new ChuyenTienDTO();

                    chuyenTien.setMaChuyenTien(rs.getInt("ma_chuyen_tien"));
                    chuyenTien.setMaTKNguoiNhan(rs.getInt("ct.ma_tk_nguoi_nhan"));
                    chuyenTien.setTenTaiKhoanNhan(rs.getString("tkkh.ten_tai_khoan"));
                    chuyenTien.setSoTaiKhoanNhan(rs.getString("tkkh.so_tai_khoan"));
                    chuyenTien.setMaKhachHang(rs.getInt("kh.ma_khach_hang"));
                    chuyenTien.setTenKhachHangNhan(rs.getString("kh.ho_dem") + rs.getString("kh.ten"));
                    chuyenTien.setMaNganHang(rs.getInt("ct.ma_ngan_hang"));
                    chuyenTien.setBiXoa(rs.getInt("kh.bi_xoa"));
                    chuyenTien.setBiDong(rs.getInt("tkkh.ma_trang_thai"));
                    chuyenTien.setEmail(rs.getString("kh.email"));
                    chuyenTien.setSdt(rs.getString("kh.so_dien_thoai"));
                    chuyenTien.setTenNganHang(rs.getString("nh.ten_viet_tat"));

                    return chuyenTien;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
