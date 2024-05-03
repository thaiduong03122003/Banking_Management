package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.LoaiTaiKhoanDTO;

public class LoaiTaiKhoanDAO {
    public List<LoaiTaiKhoanDTO> selectAll() throws Exception {
        String sql = "SELECT * FROM tbl_loai_tai_khoan";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<LoaiTaiKhoanDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LoaiTaiKhoanDTO loaiTaiKhoan = new LoaiTaiKhoanDTO();
                    loaiTaiKhoan.setMaLoaiTaiKhoan(rs.getInt("ma_loai_tai_khoan"));
                    loaiTaiKhoan.setTenLoaiTaiKhoan(rs.getString("ten_loai_tai_khoan"));

                    list.add(loaiTaiKhoan);
                }
                return list;
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
