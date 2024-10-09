package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.KhoaTaiKhoanDTO;

public class KhoaTaiKhoanDAO {

    public boolean insert(KhoaTaiKhoanDTO khoaTaiKhoan) {
        String sql = "INSERT INTO tbl_khoa_tai_khoan(ma_tai_khoan , ly_do_khoa, ngay_mo_khoa, loai_tai_khoan, mo_khoa)"
            + " VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, khoaTaiKhoan.getMaTaiKhoan());

            pstmt.setString(2, khoaTaiKhoan.getLyDoKhoa());

            java.sql.Date dateNgayMoKhoa = new Date(khoaTaiKhoan.getNgayMoKhoa().getTime());
            pstmt.setDate(3, dateNgayMoKhoa);

            pstmt.setString(4, khoaTaiKhoan.getLoaiTaiKhoan());
            pstmt.setInt(5, 0);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unlock(int maKhoaTaiKhoan) {
        String sql = "UPDATE tbl_khoa_tai_khoan SET mo_khoa = ? WHERE ma_khoa_tk = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            pstmt.setInt(2, maKhoaTaiKhoan);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhoaTaiKhoanDTO> selectAllLocked() {
        String sql = "SELECT * FROM tbl_khoa_tai_khoan WHERE mo_khoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 0);

            List<KhoaTaiKhoanDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KhoaTaiKhoanDTO khoaTK = new KhoaTaiKhoanDTO();
                    khoaTK.setMaKhoaTK(rs.getInt("ma_khoa_tk"));
                    khoaTK.setMaTaiKhoan(rs.getInt("ma_tai_khoan"));
                    khoaTK.setLyDoKhoa(rs.getString("ly_do_khoa"));
                    khoaTK.setNgayMoKhoa(rs.getDate("ngay_mo_khoa"));
                    khoaTK.setLoaiTaiKhoan(rs.getString("loai_tai_khoan"));
                    khoaTK.setMoKhoa(rs.getInt("mo_khoa"));

                    list.add(khoaTK);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhoaTaiKhoanDTO selectLockedByIdTK(int maTaiKhoan, String loaiTaiKhoan) {
        String sql = "SELECT * FROM tbl_khoa_tai_khoan WHERE ma_tai_khoan = ? AND loai_tai_khoan = ? AND mo_khoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maTaiKhoan);
            pstmt.setString(2, loaiTaiKhoan);
            pstmt.setInt(3, 0);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KhoaTaiKhoanDTO khoaTK = new KhoaTaiKhoanDTO();
                    khoaTK.setMaKhoaTK(rs.getInt("ma_khoa_tk"));
                    khoaTK.setMaTaiKhoan(rs.getInt("ma_tai_khoan"));
                    khoaTK.setLyDoKhoa(rs.getString("ly_do_khoa"));
                    khoaTK.setNgayMoKhoa(rs.getDate("ngay_mo_khoa"));
                    khoaTK.setLoaiTaiKhoan(rs.getString("loai_tai_khoan"));
                    khoaTK.setMoKhoa(rs.getInt("mo_khoa"));

                    return khoaTK;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
