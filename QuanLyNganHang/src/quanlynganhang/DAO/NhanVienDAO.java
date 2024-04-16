package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.NhanVienDTO;

public class NhanVienDAO {

    public NhanVienDTO insert(NhanVienDTO nhanVien, int biXoa) throws Exception {
        if (selectByCCCD(nhanVien.getCccd(), biXoa) != null) {
            return null;
        } else {
            String sql = "INSERT INTO tbl_nhan_vien(ho_dem, ten, gioi_tinh, ngay_sinh, ma_phuong_xa, so_nha, email, so_dien_thoai, cccd, anh_dai_dien, ma_chuc_vu, bi_xoa)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setString(1, nhanVien.getHoDem());
                pstmt.setString(2, nhanVien.getTen());
                pstmt.setString(3, nhanVien.getGioiTinh());

                java.sql.Date dateNgaySinh = new Date(nhanVien.getNgaySinh().getTime());
                pstmt.setDate(4, dateNgaySinh);

                pstmt.setInt(5, nhanVien.getMaPhuongXa());
                pstmt.setString(6, nhanVien.getSoNha());
                pstmt.setString(7, nhanVien.getEmail());
                pstmt.setString(8, nhanVien.getSdt());
                pstmt.setString(9, nhanVien.getCccd());
                pstmt.setString(10, nhanVien.getAnhDaiDien());
                pstmt.setInt(11, 1);
                pstmt.setInt(12, 0);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    nhanVien.setMaNV(rs.getInt(1));
                }

                return nhanVien;
            }
        }

    }

    private boolean updateExecute(NhanVienDTO nhanVien, int biXoa) throws Exception {
        String sql = "UPDATE tbl_nhan_vien SET ho_dem = ?, ten = ?, gioi_tinh = ?, ngay_sinh = ?, ma_phuong_xa = ?, so_nha = ?, email = ?, so_dien_thoai = ?, cccd = ?, anh_dai_dien = ?, ma_chuc_vu = ? WHERE ma_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, nhanVien.getHoDem());
            pstmt.setString(2, nhanVien.getTen());
            pstmt.setString(3, nhanVien.getGioiTinh());

            java.sql.Date dateNgaySinh = new Date(nhanVien.getNgaySinh().getTime());
            pstmt.setDate(4, dateNgaySinh);

            pstmt.setInt(5, nhanVien.getMaPhuongXa());
            pstmt.setString(6, nhanVien.getSoNha());
            pstmt.setString(7, nhanVien.getEmail());
            pstmt.setString(8, nhanVien.getSdt());
            pstmt.setString(9, nhanVien.getCccd());
            pstmt.setString(10, nhanVien.getAnhDaiDien());
            pstmt.setInt(11, biXoa);
            pstmt.setInt(12, nhanVien.getMaNV());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean update(NhanVienDTO nhanVien, int biXoa) throws Exception {

        if (selectById(nhanVien.getMaNV(), biXoa).getCccd().equals(nhanVien.getCccd())) {
            return updateExecute(nhanVien, biXoa);
        } else if (selectByCCCD(nhanVien.getCccd(), biXoa) != null) {
            return false;
        } else {
            return updateExecute(nhanVien, biXoa);
        }

    }

    public boolean delete(int maNhanVien) throws Exception {
        String sql = "UPDATE tbl_nhan_vien SET bi_xoa = ? WHERE ma_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            pstmt.setInt(2, maNhanVien);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<NhanVienDTO> selectAll(int biXoa) throws Exception {
        String sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu FROM tbl_nhan_vien nv LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu WHERE nv.bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, biXoa);

            List<NhanVienDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien.setMaNV(rs.getInt("ma_nhan_vien"));
                    nhanVien.setHoDem(rs.getString("ho_dem"));
                    nhanVien.setTen(rs.getString("ten"));
                    nhanVien.setGioiTinh(rs.getString("gioi_tinh"));
                    nhanVien.setNgaySinh(rs.getDate("ngay_sinh"));
                    nhanVien.setMaPhuongXa(rs.getInt("ma_phuong_xa"));
                    nhanVien.setSoNha(rs.getString("so_nha"));
                    nhanVien.setEmail(rs.getString("email"));
                    nhanVien.setSdt(rs.getString("so_dien_thoai"));
                    nhanVien.setCccd(rs.getString("cccd"));
                    nhanVien.setAnhDaiDien(rs.getString("anh_dai_dien"));
                    nhanVien.setMaChucVu(rs.getInt("cv.ma_chuc_vu"));
                    nhanVien.setTenChucVu(rs.getString("cv.ten_chuc_vu"));
                    nhanVien.setBiXoa(biXoa);

                    list.add(nhanVien);
                }
            }
            return list;
        }
    }

    public NhanVienDTO selectById(int maNhanVien, int biXoa) throws Exception {
        String sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu FROM tbl_nhan_vien nv LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu WHERE nv.ma_nhan_vien = ? AND nv.bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maNhanVien);
            pstmt.setInt(2, biXoa);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien.setMaNV(rs.getInt("ma_nhan_vien"));
                    nhanVien.setHoDem(rs.getString("ho_dem"));
                    nhanVien.setTen(rs.getString("ten"));
                    nhanVien.setGioiTinh(rs.getString("gioi_tinh"));
                    nhanVien.setNgaySinh(rs.getDate("ngay_sinh"));
                    nhanVien.setMaPhuongXa(rs.getInt("ma_phuong_xa"));
                    nhanVien.setSoNha(rs.getString("so_nha"));
                    nhanVien.setEmail(rs.getString("email"));
                    nhanVien.setSdt(rs.getString("so_dien_thoai"));
                    nhanVien.setCccd(rs.getString("cccd"));
                    nhanVien.setAnhDaiDien(rs.getString("anh_dai_dien"));
                    nhanVien.setMaChucVu(rs.getInt("cv.ma_chuc_vu"));
                    nhanVien.setTenChucVu(rs.getString("cv.ten_chuc_vu"));
                    nhanVien.setBiXoa(biXoa);

                    return nhanVien;
                }
            }
            return null;
        }
    }

    public NhanVienDTO selectByCCCD(String cccd, int biXoa) throws Exception {
        String sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu FROM tbl_nhan_vien nv LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu WHERE nv.cccd = ? AND nv.bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, cccd);
            pstmt.setInt(2, biXoa);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien.setMaNV(rs.getInt("ma_nhan_vien"));
                    nhanVien.setHoDem(rs.getString("ho_dem"));
                    nhanVien.setTen(rs.getString("ten"));
                    nhanVien.setGioiTinh(rs.getString("gioi_tinh"));
                    nhanVien.setNgaySinh(rs.getDate("ngay_sinh"));
                    nhanVien.setMaPhuongXa(rs.getInt("ma_phuong_xa"));
                    nhanVien.setSoNha(rs.getString("so_nha"));
                    nhanVien.setEmail(rs.getString("email"));
                    nhanVien.setSdt(rs.getString("so_dien_thoai"));
                    nhanVien.setCccd(rs.getString("cccd"));
                    nhanVien.setAnhDaiDien(rs.getString("anh_dai_dien"));
                    nhanVien.setMaChucVu(rs.getInt("cv.ma_chuc_vu"));
                    nhanVien.setTenChucVu(rs.getString("cv.ten_chuc_vu"));
                    nhanVien.setBiXoa(biXoa);

                    return nhanVien;
                }
            }
            return null;
        }
    }
}
