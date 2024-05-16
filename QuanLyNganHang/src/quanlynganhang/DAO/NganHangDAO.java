package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.NganHangDTO;

public class NganHangDAO {
    public List<NganHangDTO> select() throws Exception {
        String sql = "SELECT * FROM tbl_ngan_hang WHERE ma_ngan_hang != ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 1);

            List<NganHangDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NganHangDTO nganHang = new NganHangDTO();
                    nganHang.setMaNganHang(rs.getInt("ma_ngan_hang"));
                    nganHang.setTenVietTat(rs.getString("ten_viet_tat"));
                    nganHang.setTenDayDu(rs.getString("ten_day_du"));

                    list.add(nganHang);
                }
                return list;
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
