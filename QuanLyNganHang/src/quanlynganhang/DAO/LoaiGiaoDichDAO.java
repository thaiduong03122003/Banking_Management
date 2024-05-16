package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.LoaiGiaoDichDTO;

public class LoaiGiaoDichDAO {
    public List<LoaiGiaoDichDTO> selectAll() throws Exception {
        String sql = "SELECT * FROM tbl_loai_giao_dich";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<LoaiGiaoDichDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LoaiGiaoDichDTO loaiGD = new LoaiGiaoDichDTO();
                    loaiGD.setMaLoaiGiaoDich(rs.getInt("ma_loai_giao_dich"));
                    loaiGD.setTenLoaiGiaoDich(rs.getString("ten_loai_giao_dich"));

                    list.add(loaiGD);
                }
                return list;
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
