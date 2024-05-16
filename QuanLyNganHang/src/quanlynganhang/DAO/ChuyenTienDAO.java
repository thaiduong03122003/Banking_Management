package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import quanlynganhang.DTO.ChuyenTienDTO;

public class ChuyenTienDAO {

    public boolean insert(ChuyenTienDTO chuyenTien) throws Exception {

        String sql = "INSERT INTO tbl_dich_vu_chuyen_tien(ma_tk_nguoi_nhan, ma_ngan_hang, ma_giao_dich)"
            + " VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

            pstmt.setInt(1, chuyenTien.getMaTKNguoiNhan());
            pstmt.setInt(2, chuyenTien.getMaNganHang());
            pstmt.setInt(3, chuyenTien.getMaGiaoDich());

            return pstmt.executeUpdate() > 0;
        }
    }
}
