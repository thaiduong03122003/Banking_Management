package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.TaiKhoanKHDTO;

public class TaiKhoanKHDAO {

    public boolean insert(TaiKhoanKHDTO taiKhoanKH) throws Exception {
        if (selectByAccountNum(taiKhoanKH.getSoTaiKhoan()) != null) {
            return false;
        } else {
            String sql = "INSERT INTO tbl_tai_khoan_khach_hang(so_tai_khoan, ten_tai_khoan, mat_khau, ngay_tao_tk, so_du, ma_ngan_hang, ma_loai_tai_khoan, ma_khach_hang, ma_trang_thai, bi_xoa)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setString(1, taiKhoanKH.getSoTaiKhoan());
                pstmt.setString(2, taiKhoanKH.getTenTaiKhoan());
                pstmt.setString(3, taiKhoanKH.getMatKhau());

                java.sql.Date dateNgaySinh = new Date(taiKhoanKH.getNgayTao().getTime());
                pstmt.setDate(4, dateNgaySinh);

                pstmt.setInt(5, taiKhoanKH.getSoDu());
                pstmt.setInt(6, 1);
                pstmt.setInt(7, taiKhoanKH.getMaLoaiTaiKhoan());
                pstmt.setInt(8, taiKhoanKH.getMaKhachHang());
                pstmt.setInt(9, 7);
                pstmt.setInt(10, 0);

                return pstmt.executeUpdate() > 0;
            }
        }
    }

    private boolean updateExecute(TaiKhoanKHDTO taiKhoanKH) throws Exception {
        String sql = "UPDATE tbl_tai_khoan_khach_hang SET so_tai_khoan = ?, ten_tai_khoan = ? WHERE ma_tk_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, taiKhoanKH.getSoTaiKhoan());
            pstmt.setString(2, taiKhoanKH.getTenTaiKhoan());
            pstmt.setInt(3, taiKhoanKH.getMaTKKH());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean update(TaiKhoanKHDTO taiKhoanKH) throws Exception {

        if (selectById(taiKhoanKH.getMaTKKH()).getSoTaiKhoan().equals(taiKhoanKH.getSoTaiKhoan())) {
            return updateExecute(taiKhoanKH);
        } else if (selectByAccountNum(taiKhoanKH.getSoTaiKhoan()) != null) {
            return false;
        } else {
            return updateExecute(taiKhoanKH);
        }

    }

    public boolean switchStatus(int maTaiKhoanNV, int maTrangThai) throws Exception {
        String sql = "UPDATE tbl_tai_khoan_khach_hang SET ma_trang_thai = ? WHERE ma_tk_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTrangThai);
            pstmt.setInt(2, maTaiKhoanNV);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<TaiKhoanKHDTO> selectAll() throws Exception {
        String sql = "SELECT tkkh.*, kh.ho_dem, kh.ten, tt.ten_trang_thai, ltk.ten_loai_tai_khoan FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " WHERE ma_ngan_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            
            List<TaiKhoanKHDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                    taiKhoanKH.setMaTKKH(rs.getInt("ma_tk_khach_hang"));
                    taiKhoanKH.setSoTaiKhoan(rs.getString("so_tai_khoan"));
                    taiKhoanKH.setTenTaiKhoan(rs.getString("ten_tai_khoan"));
                    taiKhoanKH.setSoDu(rs.getInt("so_du"));
                    taiKhoanKH.setNgayTao(rs.getDate("ngay_tao_tk"));
                    taiKhoanKH.setTenLoaiTaiKhoan(rs.getString("ltk.ten_loai_tai_khoan"));
                    taiKhoanKH.setMaKhachHang(rs.getInt("ma_khach_hang"));
                    taiKhoanKH.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanKH.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    taiKhoanKH.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));

                    list.add(taiKhoanKH);
                }
            }
            return list;
        }
    }

    public TaiKhoanKHDTO selectById(int maTaiKhoanKH) throws Exception {
        String sql = "SELECT tkkh.*, kh.ho_dem, kh.ten, tt.ten_trang_thai, ltk.ten_loai_tai_khoan FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " WHERE ma_tk_khach_hang = ? ORDER BY ma_tk_khach_hang DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maTaiKhoanKH);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                    taiKhoanKH.setMaTKKH(rs.getInt("ma_tk_khach_hang"));
                    taiKhoanKH.setSoTaiKhoan(rs.getString("so_tai_khoan"));
                    taiKhoanKH.setTenTaiKhoan(rs.getString("ten_tai_khoan"));
                    taiKhoanKH.setMatKhau(rs.getString("mat_khau"));
                    taiKhoanKH.setSoDu(rs.getInt("so_du"));
                    taiKhoanKH.setNgayTao(rs.getDate("ngay_tao_tk"));
                    taiKhoanKH.setTenLoaiTaiKhoan(rs.getString("ltk.ten_loai_tai_khoan"));
                    taiKhoanKH.setMaKhachHang(rs.getInt("ma_khach_hang"));
                    taiKhoanKH.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                    taiKhoanKH.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanKH.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    return taiKhoanKH;
                }
            }
            return null;
        }
    }

    public TaiKhoanKHDTO selectByAccountNum(String soTaiKhoan) throws Exception {
        String sql = "SELECT * FROM tbl_tai_khoan_khach_hang WHERE so_tai_khoan = ? ORDER BY ma_tk_khach_hang DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, soTaiKhoan);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanKHDTO taiKhoan = new TaiKhoanKHDTO();
                    taiKhoan.setMaTKKH(rs.getInt("ma_tk_khach_hang"));
                    taiKhoan.setTenTaiKhoan(rs.getString("ten_tai_khoan"));
                    taiKhoan.setMaKhachHang(rs.getInt("ma_khach_hang"));
                    taiKhoan.setMaTrangThai(rs.getInt("ma_trang_thai"));

                    return taiKhoan;
                }
            }
            return null;
        }
    }

    public List<TaiKhoanKHDTO> filter(java.sql.Date dateFrom, java.sql.Date dateTo, int maKhachHang, int maLoaiTaiKhoan, int maTrangThai) throws Exception {
        String sql = "SELECT tkkh.*, kh.ho_dem, kh.ten, tt.ten_trang_thai, ltk.ten_loai_tai_khoan FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai WHERE tkkh.ma_tk_khach_hang != ?";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(0);

        if (dateFrom != null && dateTo != null) {
            conditionalClause.append(" AND tkkh.ngay_tao_tk BETWEEN ? AND ?");
            params.add(dateFrom);
            params.add(dateTo);
        }

        if (maKhachHang != 0) {
            conditionalClause.append(" AND tkkh.ma_khach_hang = ?");
            params.add(maKhachHang);
        }
        
        if (maLoaiTaiKhoan != 0) {
            conditionalClause.append(" AND tkkh.ma_loai_tai_khoan = ?");
            params.add(maLoaiTaiKhoan);
        }

        if (maTrangThai != 0) {
            conditionalClause.append(" AND tkkh.ma_trang_thai = ?");
            params.add(maTrangThai);
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }

            List<TaiKhoanKHDTO> list = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    list = null;
                } else {
                    do {
                        TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                        taiKhoanKH.setMaTKKH(rs.getInt("ma_tk_khach_hang"));
                        taiKhoanKH.setTenTaiKhoan(rs.getString("ten_tai_khoan"));
                        taiKhoanKH.setMaLoaiTaiKhoan(rs.getInt("tkkh.ma_loai_tai_khoan"));
                        taiKhoanKH.setTenLoaiTaiKhoan(rs.getString("ltk.ten_loai_tai_khoan"));
                        taiKhoanKH.setMaKhachHang(rs.getInt("ma_khach_hang"));
                        taiKhoanKH.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                        taiKhoanKH.setSoTaiKhoan(rs.getString("so_tai_khoan"));
                        taiKhoanKH.setNgayTao(rs.getDate("ngay_tao_tk"));
                        taiKhoanKH.setMaTrangThai(rs.getInt("ma_trang_thai"));
                        taiKhoanKH.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                        list.add(taiKhoanKH);
                    } while (rs.next());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                list = null;
            }
            return list;
        }
    }

    public boolean changePassword(TaiKhoanKHDTO taiKhoanKH) throws Exception {
        String sql = "UPDATE tbl_tai_khoan_khach_hang SET mat_khau = ? WHERE ma_tk_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, taiKhoanKH.getMatKhau());
            pstmt.setInt(2, taiKhoanKH.getMaTKKH());

            return pstmt.executeUpdate() > 0;
        }
    }


}
