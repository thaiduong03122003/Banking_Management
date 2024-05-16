package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.KyHanGuiTKDTO;
import quanlynganhang.DTO.TrangThaiDTO;

public class KyHanGuiTKDAO {

    public List<KyHanGuiTKDTO> selectAll() throws Exception {
        String sql = "SELECT * FROM tbl_ky_han_gui WHERE ma_ky_han != ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            
            List<KyHanGuiTKDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KyHanGuiTKDTO kyHan = new KyHanGuiTKDTO();
                    kyHan.setMaKyHan(rs.getInt("ma_ky_han"));
                    kyHan.setSoKyHan(rs.getInt("so_ky_han"));
                    kyHan.setLaiSuat(rs.getDouble("lai_suat"));

                    list.add(kyHan);
                }
                return list;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public KyHanGuiTKDTO selectKyHanById(int maKyHan) throws Exception {
        String sql = "SELECT * FROM tbl_ky_han_gui WHERE ma_ky_han = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maKyHan);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KyHanGuiTKDTO kyHan = new KyHanGuiTKDTO();
                    kyHan.setMaKyHan(rs.getInt("ma_ky_han"));
                    kyHan.setSoKyHan(rs.getInt("so_ky_han"));
                    kyHan.setLaiSuat(rs.getDouble("lai_suat"));

                    return kyHan;
                }
            }
            return null;
        }
    }
}
