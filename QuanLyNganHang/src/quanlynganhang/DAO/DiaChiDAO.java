package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.DiaChiDTO;
import quanlynganhang.DTO.NhanVienDTO;

public class DiaChiDAO {

    public List<DiaChiDTO> selectAllTinhThanh() throws Exception {
        String sql = "SELECT * FROM tbl_tinh_thanh";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<DiaChiDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DiaChiDTO diaChi = new DiaChiDTO();
                    
                    diaChi.setMaTinhThanh(rs.getInt("provinceId"));
                    diaChi.setTenTinhThanh(rs.getString("provinceName"));

                    list.add(diaChi);
                }
            }
            return list;
        }
    }

    public List<DiaChiDTO> selectAllQuanHuyenByIdTinhThanh(int maTinhThanh) throws Exception {
        String sql = "SELECT * FROM tbl_quan_huyen WHERE provinceId = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTinhThanh);

            List<DiaChiDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DiaChiDTO diaChi = new DiaChiDTO();
                    
                    diaChi.setMaQuanHuyen(rs.getInt("districtId"));
                    diaChi.setTenQuanHuyen(rs.getString("districtName"));
                    diaChi.setMaTinhThanh(rs.getInt("provinceId"));
                    
                    list.add(diaChi);
                }
            }
            return list;
        }
    }
    
    public List<DiaChiDTO> selectAllPhuongXaByIdQuanHuyen(int maQuanHuyen) throws Exception {
        String sql = "SELECT * FROM tbl_phuong_xa WHERE districtId = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maQuanHuyen);

            List<DiaChiDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DiaChiDTO diaChi = new DiaChiDTO();
                    
                    diaChi.setMaPhuongXa(rs.getInt("wardId"));
                    diaChi.setTenPhuongXa(rs.getString("wardName"));
                    diaChi.setMaQuanHuyen(rs.getInt("districtId"));
                    
                    list.add(diaChi);
                }
            }
            return list;
        }
    }

    public DiaChiDTO getDiaChiByIdPhuongXa(int maPhuongXa) throws Exception {
        String sql = "SELECT px.*, qh.districtId, qh.districtName, tt.provinceId, tt.provinceName FROM tbl_phuong_xa px LEFT JOIN tbl_quan_huyen qh ON px.districtId = qh.districtId LEFT JOIN tbl_tinh_thanh tt ON qh.provinceId = tt.provinceId WHERE px.wardId = ? LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maPhuongXa);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    DiaChiDTO diaChi = new DiaChiDTO();
                    
                    diaChi.setMaPhuongXa(rs.getInt("px.wardId"));
                    diaChi.setTenPhuongXa(rs.getString("px.wardName"));
                    diaChi.setMaQuanHuyen(rs.getInt("qh.districtId"));
                    diaChi.setTenQuanHuyen(rs.getString("qh.districtName"));
                    diaChi.setMaTinhThanh(rs.getInt("tt.provinceId"));
                    diaChi.setTenTinhThanh(rs.getString("tt.provinceName"));

                    return diaChi;
                }
            }
            return null;
        }
    }
}
