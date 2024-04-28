package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.TrangThaiDTO;

public class TrangThaiDAO {
    public List<TrangThaiDTO> selectAll(String danhMuc) throws Exception {
        String sql = "SELECT * FROM tbl_trang_thai WHERE danh_muc = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, danhMuc);

            List<TrangThaiDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TrangThaiDTO trangThai = new TrangThaiDTO();
                    trangThai.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    trangThai.setTenTrangThai(rs.getString("ten_trang_thai"));
                    trangThai.setDanhMuc(rs.getString("danh_muc"));

                    list.add(trangThai);
                }
                return list;
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
