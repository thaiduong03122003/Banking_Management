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
import quanlynganhang.DTO.KhoiPhucMatKhauDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;

public class TaiKhoanNVDAO {

    public int insert(TaiKhoanNVDTO taiKhoanNV) {
        if (selectByUserName(taiKhoanNV.getTenDangNhap()) != null) {
            return 0;
        } else {
            String sql = "INSERT INTO tbl_tai_khoan_nhan_vien(ten_dang_nhap, mat_khau, ngay_tao_tk, ma_nhan_vien, ma_trang_thai)"
                + " VALUES (?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setString(1, taiKhoanNV.getTenDangNhap());
                pstmt.setString(2, taiKhoanNV.getMatKhau());

                java.sql.Date dateNgaySinh = new Date(taiKhoanNV.getNgayTaoTK().getTime());
                pstmt.setDate(3, dateNgaySinh);

                pstmt.setInt(4, taiKhoanNV.getMaNhanVien());
                pstmt.setInt(5, 7);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                int id = 0;

                if (rs.next()) {
                    id = rs.getInt(1);
                }

                return id;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    private boolean updateExecute(TaiKhoanNVDTO taiKhoanNV) {
        String sql = "UPDATE tbl_tai_khoan_nhan_vien SET ten_dang_nhap = ?, ma_trang_thai = ? WHERE ma_tk_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, taiKhoanNV.getTenDangNhap());
            pstmt.setInt(2, taiKhoanNV.getMaTrangThai());
            pstmt.setInt(3, taiKhoanNV.getMaTKNV());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(TaiKhoanNVDTO taiKhoanNV) {

        if (selectById(taiKhoanNV.getMaTKNV()).getTenDangNhap().equals(taiKhoanNV.getTenDangNhap())) {
            return updateExecute(taiKhoanNV);
        } else if (selectByUserName(taiKhoanNV.getTenDangNhap()) != null) {
            return false;
        } else {
            return updateExecute(taiKhoanNV);
        }

    }

    public boolean switchStatus(int maTaiKhoanNV, int maTrangThai) {
        String sql = "UPDATE tbl_tai_khoan_nhan_vien SET ma_trang_thai = ? WHERE ma_tk_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTrangThai);
            pstmt.setInt(2, maTaiKhoanNV);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<TaiKhoanNVDTO> selectAll() {
        String sql = "SELECT tknv.*, nv.ho_dem, nv.ten, tt.ten_trang_thai, dn.tinh_trang_dang_nhap FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT OUTER JOIN tbl_dang_nhap dn ON tknv.ma_tk_nhan_vien = dn.ma_tk_nhan_vien";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<TaiKhoanNVDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                    taiKhoanNV.setMaTKNV(rs.getInt("ma_tk_nhan_vien"));
                    taiKhoanNV.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    taiKhoanNV.setMatKhau(rs.getString("mat_khau"));
                    taiKhoanNV.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                    taiKhoanNV.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                    taiKhoanNV.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanNV.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    taiKhoanNV.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));

                    int tinhTrang = rs.getInt("dn.tinh_trang_dang_nhap");

                    if (rs.wasNull()) {
                        tinhTrang = 2;
                    }

                    taiKhoanNV.setTinhTrangDangNhap(tinhTrang);

                    list.add(taiKhoanNV);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TaiKhoanNVDTO selectById(int maTaiKhoanNV) {
        String sql = "SELECT tknv.*, nv.ho_dem, nv.ten, tt.ten_trang_thai, dn.tinh_trang_dang_nhap FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT OUTER JOIN tbl_dang_nhap dn ON tknv.ma_tk_nhan_vien = dn.ma_tk_nhan_vien"
            + " WHERE tknv.ma_tk_nhan_vien = ? ORDER BY tknv.ma_tk_nhan_vien DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maTaiKhoanNV);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanNVDTO taiKhoan = new TaiKhoanNVDTO();
                    taiKhoan.setMaTKNV(rs.getInt("tknv.ma_tk_nhan_vien"));
                    taiKhoan.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    taiKhoan.setMatKhau(rs.getString("mat_khau"));
                    taiKhoan.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                    taiKhoan.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                    taiKhoan.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                    taiKhoan.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoan.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                    int tinhTrang = rs.getInt("dn.tinh_trang_dang_nhap");

                    if (rs.wasNull()) {
                        tinhTrang = 2;
                    }

                    taiKhoan.setTinhTrangDangNhap(tinhTrang);

                    return taiKhoan;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TaiKhoanNVDTO selectByUserName(String tenTaiKhoan) {

        String sql = "SELECT tknv.*, nv.ho_dem, nv.ten, tt.ten_trang_thai, cv.* FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu"
            + " WHERE ten_dang_nhap = ? ORDER BY ma_tk_nhan_vien DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, tenTaiKhoan);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanNVDTO taiKhoan = new TaiKhoanNVDTO();
                    taiKhoan.setMaTKNV(rs.getInt("ma_tk_nhan_vien"));
                    taiKhoan.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    taiKhoan.setMatKhau(rs.getString("mat_khau"));
                    taiKhoan.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                    taiKhoan.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                    taiKhoan.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                    taiKhoan.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoan.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    taiKhoan.setMaChucVu(rs.getInt("cv.ma_chuc_vu"));
                    taiKhoan.setTenChucVu(rs.getString("cv.ten_chuc_vu"));
                    return taiKhoan;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhoiPhucMatKhauDTO getEmailFromUsername(String username) {
        String sql = "SELECT tknv.*, nv.* FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " WHERE tknv.ten_dang_nhap = ? ORDER BY ma_tk_nhan_vien DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KhoiPhucMatKhauDTO khoiPhuc = new KhoiPhucMatKhauDTO();
                    khoiPhuc.setMaTaiKhoan(rs.getInt("ma_tk_nhan_vien"));
                    khoiPhuc.setEmail(rs.getString("nv.email"));
                    khoiPhuc.setMaTrangThai(rs.getInt("ma_trang_thai"));

                    return khoiPhuc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TaiKhoanNVDTO> filter(java.sql.Date dateFrom, java.sql.Date dateTo, int maNhanVien, int maTrangThai, int maTinhTrang) {
        String sql = "SELECT tknv.*, nv.ho_dem, nv.ten, tt.ten_trang_thai, dn.tinh_trang_dang_nhap FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT OUTER JOIN tbl_dang_nhap dn ON tknv.ma_tk_nhan_vien = dn.ma_tk_nhan_vien WHERE tknv.ma_tk_nhan_vien != ?";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(0);

        if (dateFrom != null && dateTo != null) {
            conditionalClause.append(" AND tknv.ngay_tao_tk BETWEEN ? AND ?");
            params.add(dateFrom);
            params.add(dateTo);
        }

        if (dateFrom != null && dateTo == null) {
            conditionalClause.append(" AND tknv.ngay_tao_tk > ?");
            params.add(dateFrom);
        }

        if (dateFrom == null && dateTo != null) {
            conditionalClause.append(" AND tknv.ngay_tao_tk < ?");
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

        if (maTinhTrang == 1) {
            conditionalClause.append(" AND dn.tinh_trang_dang_nhap = ?");
            params.add(1);
        }

        if (maTinhTrang == 2) {
            conditionalClause.append(" AND dn.tinh_trang_dang_nhap = ?");
            params.add(0);
        }

        if (maTinhTrang == 3) {
            conditionalClause.append(" AND (dn.tinh_trang_dang_nhap IS NULL OR dn.tinh_trang_dang_nhap NOT IN (?, ?))");
            params.add(0);
            params.add(1);
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
                while (rs.next()) {

                    TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                    taiKhoanNV.setMaTKNV(rs.getInt("tknv.ma_tk_nhan_vien"));
                    taiKhoanNV.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                    taiKhoanNV.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                    taiKhoanNV.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    taiKhoanNV.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                    taiKhoanNV.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanNV.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                    int tinhTrang = rs.getInt("dn.tinh_trang_dang_nhap");

                    if (rs.wasNull()) {
                        tinhTrang = 2;
                    }

                    taiKhoanNV.setTinhTrangDangNhap(rs.getInt("dn.tinh_trang_dang_nhap"));

                    list.add(taiKhoanNV);
                }

                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<TaiKhoanNVDTO> searchByInputType(String typeName, String inputValue) {
        String sql = "SELECT tknv.*, nv.*, tt.ten_trang_thai, dn.tinh_trang_dang_nhap FROM tbl_tai_khoan_nhan_vien tknv"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT OUTER JOIN tbl_dang_nhap dn ON tknv.ma_tk_nhan_vien = dn.ma_tk_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON tknv.ma_trang_thai = tt.ma_trang_thai WHERE tknv.ma_tk_nhan_vien != ?";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(0);

        if (typeName.equals("name")) {
            conditionalClause.append(" AND (nv.ho_dem LIKE ? OR nv.ten LIKE ?)");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("username")) {
            conditionalClause.append(" AND tknv.ten_dang_nhap LIKE ?");
            params.add("%" + inputValue + "%");
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
                while (rs.next()) {

                    TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                    taiKhoanNV.setMaTKNV(rs.getInt("tknv.ma_tk_nhan_vien"));
                    taiKhoanNV.setMaNhanVien(rs.getInt("ma_nhan_vien"));
                    taiKhoanNV.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                    taiKhoanNV.setTenDangNhap(rs.getString("ten_dang_nhap"));
                    taiKhoanNV.setNgayTaoTK(rs.getDate("ngay_tao_tk"));
                    taiKhoanNV.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanNV.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                    int tinhTrang = rs.getInt("dn.tinh_trang_dang_nhap");

                    if (rs.wasNull()) {
                        tinhTrang = 2;
                    }

                    taiKhoanNV.setTinhTrangDangNhap(tinhTrang);

                    list.add(taiKhoanNV);
                }

                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
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
}
