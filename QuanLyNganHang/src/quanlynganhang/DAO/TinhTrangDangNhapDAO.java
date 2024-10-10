package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TinhTrangDangNhapDAO {

    public int insert(int maTaiKhoanNV, boolean isLogin) {
        int id = selectByAccountId(maTaiKhoanNV);
        if (id != 0) {
            
            loginAccount(id, isLogin);
            return id;
        } else {
            String sql = "INSERT INTO tbl_dang_nhap(ma_tk_nhan_vien, tinh_trang_dang_nhap)"
                + " VALUES (?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setInt(1, maTaiKhoanNV);
                pstmt.setInt(2, isLogin ? 1 : 0);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    id = rs.getInt(1);
                }

                return id;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public int selectByAccountId(int maTaiKhoanNV) {
        String sql = "SELECT * FROM tbl_dang_nhap WHERE ma_tk_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maTaiKhoanNV);

            try (ResultSet rs = pstmt.executeQuery()) {
                int id = 0;
                if (rs.next()) {
                    id = rs.getInt("ma_dang_nhap");
                }
                
                return id;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean loginAccount(int maDangNhap, boolean isLogin) {
        String sql = "UPDATE tbl_dang_nhap SET tinh_trang_dang_nhap = ? WHERE ma_dang_nhap = ?";
        int login = isLogin ? 1 : 0;

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, login);
            pstmt.setInt(2, maDangNhap);
            

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
