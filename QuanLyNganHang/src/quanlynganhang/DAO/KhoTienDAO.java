package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DTO.KhoTienDTO;

public class KhoTienDAO {
    public KhoTienDTO selectMoney() throws Exception {
        String sql = "SELECT * FROM tbl_kho_tien LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KhoTienDTO khoTien = new KhoTienDTO();
                    khoTien.setMaKhoTien(rs.getInt("ma_kho_tien"));
                    khoTien.setTienTKNganHang(rs.getString("tien_tai_khoan"));
                    khoTien.setTienMat(rs.getString("tien_mat"));
                    
                    return khoTien;
                }
            }
            return null;
        }
    }
    
    public boolean updateMoney(KhoTienDTO khoTien) {
        String sql = "UPDATE tbl_kho_tien SET tien_tai_khoan = ?, tien_mat = ? WHERE ma_kho_tien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, khoTien.getTienTKNganHang());
            pstmt.setString(2, khoTien.getTienMat());
            pstmt.setInt(3, 1);

            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
