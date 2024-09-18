package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.TheATMDTO;

public class GiaoDichDAO {

    public int insert(GiaoDichDTO giaoDich) throws Exception {

        if (giaoDich.getMaTaiKhoanKH() == 0) {
            String sql = "INSERT INTO tbl_giao_dich(ma_tk_nhan_vien, so_tien, ngay_giao_dich, noi_dung_giao_dich, ma_loai_giao_dich, ma_trang_thai)"
                + " VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setInt(1, giaoDich.getMaTaiKhoanNV());
                pstmt.setString(2, giaoDich.getSoTien());

                java.sql.Date ngayGiaoDich = new Date(giaoDich.getNgayGiaoDich().getTime());
                pstmt.setDate(3, ngayGiaoDich);

                pstmt.setString(4, giaoDich.getNoiDungGiaoDich());
                pstmt.setInt(5, giaoDich.getMaLoaiGiaoDich());
                pstmt.setInt(6, 4);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    giaoDich.setMaGiaoDich(rs.getInt(1));
                }

                return giaoDich.getMaGiaoDich();
            }
        } else {
            String sql = "INSERT INTO tbl_giao_dich(ma_tk_khach_hang, ma_tk_nhan_vien, so_tien, ngay_giao_dich, noi_dung_giao_dich, ma_loai_giao_dich, ma_trang_thai)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setInt(1, giaoDich.getMaTaiKhoanKH());
                pstmt.setInt(2, giaoDich.getMaTaiKhoanNV());
                pstmt.setString(3, giaoDich.getSoTien());

                java.sql.Date ngayGiaoDich = new Date(giaoDich.getNgayGiaoDich().getTime());
                pstmt.setDate(4, ngayGiaoDich);

                pstmt.setString(5, giaoDich.getNoiDungGiaoDich());
                pstmt.setInt(6, giaoDich.getMaLoaiGiaoDich());
                pstmt.setInt(7, 4);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    giaoDich.setMaGiaoDich(rs.getInt(1));
                }

                return giaoDich.getMaGiaoDich();
            }
        }
    }
    //==================17/9
      public List<GiaoDichDTO> getMaxGiaoDich() throws Exception {
        String sql = "SELECT gd.*, lgd.*, tkkh.so_tai_khoan, kh.ho_dem, kh.ten, nv.ho_dem, nv.ten, tt.*,Max(so_tien) FROM tbl_giao_dich gd"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON gd.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_tai_khoan_nhan_vien tknv ON gd.ma_tk_nhan_vien = tknv.ma_tk_nhan_vien"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON gd.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_giao_dich lgd ON gd.ma_loai_giao_dich = lgd.ma_loai_giao_dich";
            

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<GiaoDichDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    GiaoDichDTO giaoDich = new GiaoDichDTO();
                    giaoDich.setMaGiaoDich(rs.getInt("ma_giao_dich"));
                    giaoDich.setMaTaiKhoanKH(rs.getInt("gd.ma_tk_khach_hang"));
                    giaoDich.setNgayGiaoDich(rs.getDate("ngay_giao_dich"));
                    giaoDich.setSoTien(rs.getString("so_tien"));
                    giaoDich.setMaTaiKhoanNV(rs.getInt("gd.ma_tk_nhan_vien"));
                    giaoDich.setMaLoaiGiaoDich(rs.getInt("gd.ma_loai_giao_dich"));
                    giaoDich.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                    giaoDich.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                    giaoDich.setTenLoaiGiaoDich(rs.getString("lgd.ten_loai_giao_dich"));
                    giaoDich.setMaTrangThai(rs.getInt("gd.ma_trang_thai"));
                    giaoDich.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    giaoDich.setSoTaiKhoan(rs.getString("tkkh.so_tai_khoan"));

                    list.add(giaoDich);
                     return list;
                }
            }
            return list;
        }
    }
    //===================17/9

    public List<GiaoDichDTO> selectAll() throws Exception {
        String sql = "SELECT gd.*, lgd.*, tkkh.so_tai_khoan, kh.ho_dem, kh.ten, nv.ho_dem, nv.ten, tt.* FROM tbl_giao_dich gd"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON gd.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_tai_khoan_nhan_vien tknv ON gd.ma_tk_nhan_vien = tknv.ma_tk_nhan_vien"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON gd.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_giao_dich lgd ON gd.ma_loai_giao_dich = lgd.ma_loai_giao_dich";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<GiaoDichDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    GiaoDichDTO giaoDich = new GiaoDichDTO();
                    giaoDich.setMaGiaoDich(rs.getInt("ma_giao_dich"));
                    giaoDich.setMaTaiKhoanKH(rs.getInt("gd.ma_tk_khach_hang"));
                    giaoDich.setNgayGiaoDich(rs.getDate("ngay_giao_dich"));
                    giaoDich.setSoTien(rs.getString("so_tien"));
                    giaoDich.setMaTaiKhoanNV(rs.getInt("gd.ma_tk_nhan_vien"));
                    giaoDich.setMaLoaiGiaoDich(rs.getInt("gd.ma_loai_giao_dich"));
                    giaoDich.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                    giaoDich.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                    giaoDich.setTenLoaiGiaoDich(rs.getString("lgd.ten_loai_giao_dich"));
                    giaoDich.setMaTrangThai(rs.getInt("gd.ma_trang_thai"));
                    giaoDich.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    giaoDich.setSoTaiKhoan(rs.getString("tkkh.so_tai_khoan"));

                    list.add(giaoDich);
                }
            }
            return list;
        }
    }
    
    public List<GiaoDichDTO> filter(java.sql.Date dateFrom, java.sql.Date dateTo, int maKhachHang, int maNhanVien, int maTaiKhoanKH, int maLoaiGiaoDich) throws Exception {
        String sql = "SELECT gd.*, lgd.*, tkkh.so_tai_khoan, kh.ho_dem, kh.ten, nv.ho_dem, nv.ten, tt.* FROM tbl_giao_dich gd"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON gd.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_tai_khoan_nhan_vien tknv ON gd.ma_tk_nhan_vien = tknv.ma_tk_nhan_vien"
            + " LEFT JOIN tbl_nhan_vien nv ON tknv.ma_nhan_vien = nv.ma_nhan_vien"
            + " LEFT JOIN tbl_trang_thai tt ON gd.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_giao_dich lgd ON gd.ma_loai_giao_dich = lgd.ma_loai_giao_dich"
            + " WHERE ma_giao_dich != ?";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(0);

        if (dateFrom != null && dateTo != null) {
            conditionalClause.append(" AND gd.ngay_giao_dich BETWEEN ? AND ?");
            params.add(dateFrom);
            params.add(dateTo);
        }

        if (maKhachHang != 0) {
            conditionalClause.append(" AND kh.ma_khach_hang = ?");
            params.add(maKhachHang);
        }
        
        if (maNhanVien != 0) {
            conditionalClause.append(" AND nv.ma_nhan_vien = ?");
            params.add(maNhanVien);
        }
        
        if (maTaiKhoanKH != 0) {
            conditionalClause.append(" AND gd.ma_tk_khach_hang = ?");
            params.add(maTaiKhoanKH);
        }

        if (maLoaiGiaoDich != 0) {
            conditionalClause.append(" AND gd.ma_loai_giao_dich = ?");
            params.add(maLoaiGiaoDich);
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }

            List<GiaoDichDTO> list = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    list = null;
                } else {
                    do {
                        GiaoDichDTO giaoDich = new GiaoDichDTO();
                        giaoDich.setMaGiaoDich(rs.getInt("ma_giao_dich"));
                        giaoDich.setMaTaiKhoanKH(rs.getInt("gd.ma_tk_khach_hang"));
                        giaoDich.setNgayGiaoDich(rs.getDate("ngay_giao_dich"));
                        giaoDich.setSoTien(rs.getString("so_tien"));
                        giaoDich.setMaTaiKhoanNV(rs.getInt("gd.ma_tk_nhan_vien"));
                        giaoDich.setMaLoaiGiaoDich(rs.getInt("gd.ma_loai_giao_dich"));
                        giaoDich.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                        giaoDich.setTenNhanVien(rs.getString("nv.ho_dem") + " " + rs.getString("nv.ten"));
                        giaoDich.setTenLoaiGiaoDich(rs.getString("lgd.ten_loai_giao_dich"));
                        giaoDich.setMaTrangThai(rs.getInt("gd.ma_trang_thai"));
                        giaoDich.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                        giaoDich.setSoTaiKhoan(rs.getString("tkkh.so_tai_khoan"));

                        list.add(giaoDich);
                    } while (rs.next());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                list = null;
            }
            return list;
        }
    }
}
