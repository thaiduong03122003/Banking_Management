/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.TaiKhoanNVDTO;

/**
 *
 * @author THAI
 */
public class TaiKhoanNVDAO {

    public boolean insert(TaiKhoanNVDTO taiKhoanNV) throws Exception {
        if (selectByUserName(taiKhoanNV.getTenDangNhap()) != null) {
            return false;
        } else {
            String sql = "INSERT INTO tbl_tai_khoan_nhan_vien(ten_dang_nhap, mat_khau, ma_PIN_dang_nhap, ngay_tao_tk, ma_nhan_vien, ma_trang_thai)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setString(1, taiKhoanNV.getTenDangNhap());
                pstmt.setString(2, taiKhoanNV.getMatKhau());
                pstmt.setString(3, taiKhoanNV.getMaPIN());

                java.sql.Date dateNgaySinh = new Date(taiKhoanNV.getNgayTaoTK().getTime());
                pstmt.setDate(4, dateNgaySinh);

                pstmt.setInt(5, taiKhoanNV.getMaNhanVien());
                pstmt.setInt(6, 7);

                return pstmt.executeUpdate() > 0;
            }
        }
    }

    private boolean updateExecute(TaiKhoanNVDTO taiKhoanNV) throws Exception {
        String sql = "UPDATE tbl_tai_khoan_nhan_vien SET ten_dang_nhap = ?, ma_trang_thai = ? WHERE ma_tk_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, taiKhoanNV.getTenDangNhap());
            pstmt.setInt(2, taiKhoanNV.getMaTrangThai());
            pstmt.setInt(3, taiKhoanNV.getMaNhanVien());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean update(TaiKhoanNVDTO taiKhoanNV) throws Exception {

        if (selectById(taiKhoanNV.getMaNhanVien()).getTenDangNhap().equals(taiKhoanNV.getTenDangNhap())) {
            return updateExecute(taiKhoanNV);
        } else if (selectByUserName(taiKhoanNV.getTenDangNhap()) != null) {
            return false;
        } else {
            return updateExecute(taiKhoanNV);
        }

    }

    public boolean switchStatus(int maTaiKhoanNV, int maTrangThai) throws Exception {
        String sql = "UPDATE tbl_tai_khoan_nhan_vien SET ma_trang_thai = ? WHERE ma_tk_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTrangThai);
            pstmt.setInt(2, maTaiKhoanNV);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<TaiKhoanNVDTO> selectAll() throws Exception {
        String sql = "SELECT tknv.*, nv.ho_dem, nv.ten, tt.ten_trang_thai FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<TaiKhoanNVDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                    taiKhoanNV.setMaTKNV(rs.getInt("ma_tk_nhan_vien"));
                    taiKhoanNV.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    taiKhoanNV.setMatKhau(rs.getString("mat_khau"));
                    taiKhoanNV.setMaPIN(rs.getString("ma_PIN_dang_nhap"));
                    taiKhoanNV.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                    taiKhoanNV.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                    taiKhoanNV.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanNV.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    taiKhoanNV.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));

                    list.add(taiKhoanNV);
                }
            }
            return list;
        }
    }

    public TaiKhoanNVDTO selectById(int maTaiKhoanNV) throws Exception {
        String sql = "SELECT tknv.*, nv.ho_dem, nv.ten, tt.ten_trang_thai FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai"
            + " WHERE ma_tk_nhan_vien = ? ORDER BY ma_tk_nhan_vien DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maTaiKhoanNV);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanNVDTO taiKhoan = new TaiKhoanNVDTO();
                    taiKhoan.setMaTKNV(rs.getInt("ma_tk_nhan_vien"));
                    taiKhoan.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    taiKhoan.setMatKhau(rs.getString("mat_khau"));
                    taiKhoan.setMaPIN(rs.getString("ma_PIN_dang_nhap"));
                    taiKhoan.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                    taiKhoan.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                    taiKhoan.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                    taiKhoan.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoan.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                    return taiKhoan;
                }
            }
            return null;
        }
    }

    public TaiKhoanNVDTO selectByUserName(String tenTaiKhoan) throws Exception {

        String sql = "SELECT tknv.*, nv.ho_dem, nv.ten, tt.ten_trang_thai FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai"
            + " WHERE ten_dang_nhap = ? ORDER BY ma_tk_nhan_vien DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, tenTaiKhoan);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanNVDTO taiKhoan = new TaiKhoanNVDTO();
                    taiKhoan.setMaTKNV(rs.getInt("ma_tk_nhan_vien"));
                    taiKhoan.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    taiKhoan.setMatKhau(rs.getString("mat_khau"));
                    taiKhoan.setMaPIN(rs.getString("ma_PIN_dang_nhap"));
                    taiKhoan.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                    taiKhoan.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                    taiKhoan.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                    taiKhoan.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoan.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                    return taiKhoan;
                }
            }
            return null;
        }
    }

    public List<TaiKhoanNVDTO> filter(java.sql.Date dateFrom, java.sql.Date dateTo, int maNhanVien, int maTrangThai) throws Exception {
        String sql = "SELECT tknv.*, nv.ho_dem, nv.ten, tt.ten_trang_thai FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai WHERE tknv.ma_tk_nhan_vien != ?";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(0);

        if (dateFrom != null && dateTo != null) {
            conditionalClause.append(" AND tknv.ngay_tao_tk BETWEEN ? AND ?");
            params.add(dateFrom);
            params.add(dateTo);
        }

        if (maNhanVien != 0) {
            conditionalClause.append(" AND tknv.ma_nhan_vien = ?");
            params.add(maNhanVien);
        }

        if (maTrangThai != 0) {
            conditionalClause.append(" AND tknv.ma_trang_thai = ?");
            params.add(maTrangThai);
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }

            List<TaiKhoanNVDTO> list = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    list = null;
                } else {
                    do {
                        TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                        taiKhoanNV.setMaTKNV(rs.getInt("ma_tk_nhan_vien"));
                        taiKhoanNV.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                        taiKhoanNV.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                        taiKhoanNV.setTenDangNhap(rs.getString("ten_dang_nhap"));
                        taiKhoanNV.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                        taiKhoanNV.setMaTrangThai(rs.getInt("ma_trang_thai"));
                        taiKhoanNV.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                        list.add(taiKhoanNV);
                    } while (rs.next());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                list = null;
            }
            return list;
        }
    }

    public boolean changePassword(TaiKhoanNVDTO taiKhoanNV) {
        String sql = "UPDATE tbl_tai_khoan_nhan_vien SET mat_khau = ? WHERE ma_tk_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, taiKhoanNV.getMatKhau());
            pstmt.setInt(2, taiKhoanNV.getMaTKNV());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePINCode(TaiKhoanNVDTO taiKhoanNV) throws Exception {
        String sql = "UPDATE tbl_tai_khoan_nhan_vien SET ma_PIN_dang_nhap = ? WHERE ma_tk_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, taiKhoanNV.getMaPIN());
            pstmt.setInt(2, taiKhoanNV.getMaTKNV());
            return pstmt.executeUpdate() > 0;
        }
    }
}
