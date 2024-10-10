package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DTO.TaiKhoanKHDTO;

public class TaiKhoanKHDAO {

    public int insert(TaiKhoanKHDTO taiKhoanKH) {
        if (selectByAccountNum(taiKhoanKH.getSoTaiKhoan(), 1) != null) {
            return 0;
        } else {
            String sql = "INSERT INTO tbl_tai_khoan_khach_hang(so_tai_khoan, ten_tai_khoan, mat_khau, ngay_tao_tk, so_du, ma_ngan_hang, ma_loai_tai_khoan, ma_khach_hang, ma_trang_thai)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setString(1, taiKhoanKH.getSoTaiKhoan());
                pstmt.setString(2, taiKhoanKH.getTenTaiKhoan());
                pstmt.setString(3, taiKhoanKH.getMatKhau());

                java.sql.Date dateNgaySinh = new Date(taiKhoanKH.getNgayTao().getTime());
                pstmt.setDate(4, dateNgaySinh);

                pstmt.setString(5, taiKhoanKH.getSoDu());
                pstmt.setInt(6, 1);
                pstmt.setInt(7, taiKhoanKH.getMaLoaiTaiKhoan());
                pstmt.setInt(8, taiKhoanKH.getMaKhachHang());
                
                if (taiKhoanKH.getMaLoaiTaiKhoan() == 3 || taiKhoanKH.getMaLoaiTaiKhoan() == 4) {
                    pstmt.setInt(9, 6);
                } else {
                    pstmt.setInt(9, 7);
                }

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    taiKhoanKH.setMaTKKH(rs.getInt(1));
                }

                return taiKhoanKH.getMaTKKH();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    private boolean updateExecute(TaiKhoanKHDTO taiKhoanKH) {
        String sql = "UPDATE tbl_tai_khoan_khach_hang SET so_tai_khoan = ?, ten_tai_khoan = ? WHERE ma_tk_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, taiKhoanKH.getSoTaiKhoan());
            pstmt.setString(2, taiKhoanKH.getTenTaiKhoan());
            pstmt.setInt(3, taiKhoanKH.getMaTKKH());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(TaiKhoanKHDTO taiKhoanKH) {

        if (selectById(taiKhoanKH.getMaTKKH()).getSoTaiKhoan().equals(taiKhoanKH.getSoTaiKhoan())) {
            return updateExecute(taiKhoanKH);
        } else if (selectByAccountNum(taiKhoanKH.getSoTaiKhoan(), 1) != null) {
            return false;
        } else {
            return updateExecute(taiKhoanKH);
        }

    }

    public boolean switchStatus(int maTaiKhoanNV, int maTrangThai) {
        String sql = "UPDATE tbl_tai_khoan_khach_hang SET ma_trang_thai = ? WHERE ma_tk_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTrangThai);
            pstmt.setInt(2, maTaiKhoanNV);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<TaiKhoanKHDTO> selectAll(int maLoaiTaiKhoan) {
        String baseSql = "SELECT tkkh.*, kh.ho_dem, kh.ten, tt.ten_trang_thai, ltk.ten_loai_tai_khoan FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " WHERE ma_ngan_hang = ?";

        String sql = (maLoaiTaiKhoan != 0) ? baseSql + " AND tkkh.ma_loai_tai_khoan = ?" : baseSql;

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);

            if (maLoaiTaiKhoan != 0) {
                pstmt.setInt(2, maLoaiTaiKhoan);
            }

            List<TaiKhoanKHDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                    taiKhoanKH.setMaTKKH(rs.getInt("ma_tk_khach_hang"));
                    taiKhoanKH.setSoTaiKhoan(rs.getString("so_tai_khoan"));
                    taiKhoanKH.setTenTaiKhoan(rs.getString("ten_tai_khoan"));
                    taiKhoanKH.setSoDu(rs.getString("so_du"));
                    taiKhoanKH.setNgayTao(rs.getDate("ngay_tao_tk"));
                    taiKhoanKH.setTenLoaiTaiKhoan(rs.getString("ltk.ten_loai_tai_khoan"));
                    taiKhoanKH.setMaKhachHang(rs.getInt("ma_khach_hang"));
                    taiKhoanKH.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanKH.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    taiKhoanKH.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));

                    list.add(taiKhoanKH);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TaiKhoanKHDTO selectById(int maTaiKhoanKH) {
        String sql = "SELECT tkkh.*, kh.*, tt.ten_trang_thai, ltk.ten_loai_tai_khoan, nh.* FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " LEFT JOIN tbl_ngan_hang nh ON tkkh.ma_ngan_hang = nh.ma_ngan_hang"
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
                    taiKhoanKH.setSoDu(rs.getString("so_du"));
                    taiKhoanKH.setNgayTao(rs.getDate("ngay_tao_tk"));
                    taiKhoanKH.setMaLoaiTaiKhoan(rs.getInt("tkkh.ma_loai_tai_khoan"));
                    taiKhoanKH.setTenLoaiTaiKhoan(rs.getString("ltk.ten_loai_tai_khoan"));
                    taiKhoanKH.setMaKhachHang(rs.getInt("ma_khach_hang"));
                    taiKhoanKH.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                    taiKhoanKH.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanKH.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    taiKhoanKH.setEmail(rs.getString("kh.email"));
                    taiKhoanKH.setSdt(rs.getString("kh.so_dien_thoai"));
                    taiKhoanKH.setBiXoa(rs.getInt("kh.bi_xoa"));
                    taiKhoanKH.setMaNganHang(rs.getInt("tkkh.ma_ngan_hang"));
                    taiKhoanKH.setTenNganHang(rs.getString("nh.ten_viet_tat"));

                    return taiKhoanKH;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getNewSTK() {
        String sql = "SELECT * FROM tbl_tai_khoan_khach_hang WHERE ma_ngan_hang = ? ORDER BY ma_tk_khach_hang DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 1);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String sotaiKhoan = (rs.getString("so_tai_khoan"));

                    return sotaiKhoan;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<TaiKhoanKHDTO> selectByMaKH(int maKhachHang) {
        String sql = "SELECT tkkh.*, kh.ho_dem, kh.ten, tt.ten_trang_thai, ltk.ten_loai_tai_khoan FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " WHERE tkkh.ma_khach_hang = ? AND tkkh.ma_trang_thai = ? AND (tkkh.ma_loai_tai_khoan = ? OR tkkh.ma_loai_tai_khoan = ?)";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maKhachHang);
            pstmt.setInt(2, 6);
            pstmt.setInt(3, 1);
            pstmt.setInt(4, 2);

            List<TaiKhoanKHDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                    taiKhoanKH.setMaTKKH(rs.getInt("ma_tk_khach_hang"));
                    taiKhoanKH.setSoTaiKhoan(rs.getString("so_tai_khoan"));
                    taiKhoanKH.setTenTaiKhoan(rs.getString("ten_tai_khoan"));
                    taiKhoanKH.setSoDu(rs.getString("so_du"));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TaiKhoanKHDTO selectByAccountNum(String soTaiKhoan, int maNganHang) {
        String sql = "SELECT tkkh.*, kh.ho_dem, kh.ten, tt.ten_trang_thai, ltk.ten_loai_tai_khoan FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " WHERE so_tai_khoan = ? AND ma_ngan_hang = ? ORDER BY ma_tk_khach_hang DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, soTaiKhoan);
            pstmt.setInt(2, maNganHang);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                    taiKhoanKH.setMaTKKH(rs.getInt("ma_tk_khach_hang"));
                    taiKhoanKH.setSoTaiKhoan(rs.getString("so_tai_khoan"));
                    taiKhoanKH.setTenTaiKhoan(rs.getString("ten_tai_khoan"));
                    taiKhoanKH.setMatKhau(rs.getString("mat_khau"));
                    taiKhoanKH.setSoDu(rs.getString("so_du"));
                    taiKhoanKH.setNgayTao(rs.getDate("ngay_tao_tk"));
                    taiKhoanKH.setTenLoaiTaiKhoan(rs.getString("ltk.ten_loai_tai_khoan"));
                    taiKhoanKH.setMaKhachHang(rs.getInt("ma_khach_hang"));
                    taiKhoanKH.setTenKhachHang(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                    taiKhoanKH.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    taiKhoanKH.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                    return taiKhoanKH;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TaiKhoanKHDTO> filter(java.sql.Date dateFrom, java.sql.Date dateTo, int maKhachHang, int maLoaiTaiKhoan, int maTrangThai) {
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

        if (dateFrom != null && dateTo == null) {
            conditionalClause.append(" AND tkkh.ngay_tao_tk > ?");
            params.add(dateFrom);
        }

        if (dateFrom == null && dateTo != null) {
            conditionalClause.append(" AND tkkh.ngay_tao_tk < ?");
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

            try (ResultSet rs = pstmt.executeQuery()) {
                List<TaiKhoanKHDTO> list = new ArrayList<>();

                while (rs.next()) {

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
                    taiKhoanKH.setSoDu(rs.getString("so_du"));

                    list.add(taiKhoanKH);
                }

                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TaiKhoanKHDTO> searchVVByInputType(String typeName, String inputValue) {
        String sql = "SELECT tkkh.*, kh.*, tt.ten_trang_thai, ltk.ten_loai_tai_khoan FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai"
            + " WHERE tkkh.ma_tk_khach_hang != ? AND tkkh.ma_loai_tai_khoan = ?";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(0);
        params.add(4);

        if (typeName.equals("name")) {
            conditionalClause.append(" AND (kh.ho_dem LIKE ? OR kh.ten LIKE ? OR CONCAT(kh.ho_dem, ' ', kh.ten) LIKE ? OR tkkh.ten_tai_khoan LIKE ?)");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("accountNum")) {
            conditionalClause.append(" AND tkkh.so_tai_khoan LIKE ?");
            params.add("%" + inputValue + "%");
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }
            List<TaiKhoanKHDTO> list = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
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
                    taiKhoanKH.setSoDu(rs.getString("so_du"));

                    list.add(taiKhoanKH);
                }

                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TaiKhoanKHDTO> searchByInputType(String typeName, String inputValue) {
        String sql = "SELECT tkkh.*, kh.*, tt.ten_trang_thai, ltk.ten_loai_tai_khoan FROM tbl_tai_khoan_khach_hang tkkh"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_loai_tai_khoan ltk ON tkkh.ma_loai_tai_khoan = ltk.ma_loai_tai_khoan"
            + " LEFT JOIN tbl_trang_thai tt ON tkkh.ma_trang_thai = tt.ma_trang_thai"
            + " WHERE tkkh.ma_tk_khach_hang != ?";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(0);
        
        if (typeName.equals("name")) {
            conditionalClause.append(" AND (kh.ho_dem LIKE ? OR kh.ten LIKE ? OR CONCAT(kh.ho_dem, ' ', kh.ten) LIKE ? OR tkkh.ten_tai_khoan LIKE ?)");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("accountNum")) {
            conditionalClause.append(" AND tkkh.so_tai_khoan LIKE ?");
            params.add("%" + inputValue + "%");
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }
            List<TaiKhoanKHDTO> list = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
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
                    taiKhoanKH.setSoDu(rs.getString("so_du"));

                    list.add(taiKhoanKH);
                }

                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean changePassword(TaiKhoanKHDTO taiKhoanKH) {
        String sql = "UPDATE tbl_tai_khoan_khach_hang SET mat_khau = ? WHERE ma_tk_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, taiKhoanKH.getMatKhau());
            pstmt.setInt(2, taiKhoanKH.getMaTKKH());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMoney(int mataiKhoanKH, String newSoDu) {
        String sql = "UPDATE tbl_tai_khoan_khach_hang SET so_du = ? WHERE ma_tk_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, newSoDu);

            pstmt.setInt(2, mataiKhoanKH);

            return pstmt.executeUpdate() > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
