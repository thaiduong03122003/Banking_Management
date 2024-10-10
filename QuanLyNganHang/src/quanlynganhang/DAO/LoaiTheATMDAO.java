package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.LoaiTheATMDTO;
import quanlynganhang.DTO.TrangThaiDTO;

public class LoaiTheATMDAO {

    public List<LoaiTheATMDTO> selectAll() {
        String sql = "SELECT * FROM tbl_loai_the_atm";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<LoaiTheATMDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LoaiTheATMDTO loaiThe = new LoaiTheATMDTO();
                    loaiThe.setMaLoaiThe(rs.getInt("ma_loai_the"));
                    loaiThe.setTenLoaiThe(rs.getString("ten_loai_the"));
                    loaiThe.setMoTa(rs.getString("mo_ta"));

                    list.add(loaiThe);
                }
                return list;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
