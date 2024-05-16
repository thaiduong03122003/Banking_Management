package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.KyHanGuiTKDTO;
import quanlynganhang.DTO.ThoiHanVayDTO;

public class ThoiHanVayDAO {
    public List<ThoiHanVayDTO> selectAll(int thoiHanVay) throws Exception {
        String sql = "SELECT * FROM tbl_thoi_han_vay WHERE loai_hinh_vay = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, thoiHanVay);
            
            List<ThoiHanVayDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ThoiHanVayDTO thoiHan = new ThoiHanVayDTO();
                    thoiHan.setMaThoiHan(rs.getInt("ma_thoi_han"));
                    thoiHan.setSoThoiHan(rs.getInt("so_thoi_han"));
                    thoiHan.setLaiSuat(rs.getDouble("lai_suat"));

                    list.add(thoiHan);
                }
                return list;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public ThoiHanVayDTO selectThoiHanById(int maThoiHan) throws Exception {
        String sql = "SELECT * FROM tbl_thoi_han_vay WHERE ma_thoi_han = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maThoiHan);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ThoiHanVayDTO thoiHan = new ThoiHanVayDTO();
                    thoiHan.setMaThoiHan(rs.getInt("ma_thoi_han"));
                    thoiHan.setSoThoiHan(rs.getInt("so_thoi_han"));
                    thoiHan.setLaiSuat(rs.getDouble("lai_suat"));

                    return thoiHan;
                }
            }
            return null;
        }
    }
}
