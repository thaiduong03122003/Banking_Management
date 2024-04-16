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
                    diaChi.setMaTinhThanh(rs.getInt("districtId"));
                    
                    list.add(diaChi);
                }
            }
            return list;
        }
    }

//    public NhanVienDTO selectByCCCD(String cccd, int biXoa) throws Exception {
//        String sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu FROM tbl_nhan_vien nv LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu WHERE nv.cccd = ? AND nv.bi_xoa = ?";
//
//        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
//            pstmt.setString(1, cccd);
//            pstmt.setInt(2, biXoa);
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//                while (rs.next()) {
//                    NhanVienDTO nhanVien = new NhanVienDTO();
//                    nhanVien.setMaNV(rs.getInt("ma_nhan_vien"));
//                    nhanVien.setHoDem(rs.getString("ho_dem"));
//                    nhanVien.setTen(rs.getString("ten"));
//                    nhanVien.setGioiTinh(rs.getString("gioi_tinh"));
//                    nhanVien.setNgaySinh(rs.getDate("ngay_sinh"));
//                    nhanVien.setMaPhuongXa(rs.getInt("ma_phuong_xa"));
//                    nhanVien.setSoNha(rs.getString("so_nha"));
//                    nhanVien.setEmail(rs.getString("email"));
//                    nhanVien.setSdt(rs.getString("so_dien_thoai"));
//                    nhanVien.setCccd(rs.getString("cccd"));
//                    nhanVien.setAnhDaiDien(rs.getString("anh_dai_dien"));
//                    nhanVien.setMaChucVu(rs.getInt("cv.ma_chuc_vu"));
//                    nhanVien.setTenChucVu(rs.getString("cv.ten_chuc_vu"));
//                    nhanVien.setBiXoa(biXoa);
//
//                    return nhanVien;
//                }
//            }
//            return null;
//        }
//    }
}
