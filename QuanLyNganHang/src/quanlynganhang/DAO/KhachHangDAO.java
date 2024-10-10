package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.KhachHangDTO;

public class KhachHangDAO {

    public int insert(KhachHangDTO khachHang, int biXoa) {
        if (selectByCCCD(khachHang.getCccd(), biXoa) != null) {
            return 0;
        } else {
            String sql = "INSERT INTO tbl_khach_hang(ho_dem, ten, gioi_tinh, ngay_sinh, ma_phuong_xa, so_nha, email, so_dien_thoai, cccd, anh_dai_dien, no_xau, bi_xoa)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setString(1, khachHang.getHoDem());
                pstmt.setString(2, khachHang.getTen());
                pstmt.setString(3, khachHang.getGioiTinh());

                java.sql.Date dateNgaySinh = new Date(khachHang.getNgaySinh().getTime());
                pstmt.setDate(4, dateNgaySinh);

                pstmt.setInt(5, khachHang.getMaPhuongXa());
                pstmt.setString(6, khachHang.getSoNha());
                pstmt.setString(7, khachHang.getEmail());
                pstmt.setString(8, khachHang.getSdt());
                pstmt.setString(9, khachHang.getCccd());
                pstmt.setString(10, khachHang.getAnhDaiDien());
                pstmt.setInt(11, 0);
                pstmt.setInt(12, 0);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    khachHang.setMaKH(rs.getInt(1));
                }

                return khachHang.getMaKH();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }

    private boolean updateExecute(KhachHangDTO khachHang) {
        String sql = "UPDATE tbl_khach_hang SET ho_dem = ?, ten = ?, gioi_tinh = ?, ngay_sinh = ?, ma_phuong_xa = ?, so_nha = ?, email = ?, so_dien_thoai = ?, cccd = ?, anh_dai_dien = ? WHERE ma_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, khachHang.getHoDem());
            pstmt.setString(2, khachHang.getTen());
            pstmt.setString(3, khachHang.getGioiTinh());

            java.sql.Date dateNgaySinh = new Date(khachHang.getNgaySinh().getTime());
            pstmt.setDate(4, dateNgaySinh);

            pstmt.setInt(5, khachHang.getMaPhuongXa());
            pstmt.setString(6, khachHang.getSoNha());
            pstmt.setString(7, khachHang.getEmail());
            pstmt.setString(8, khachHang.getSdt());
            pstmt.setString(9, khachHang.getCccd());
            pstmt.setString(10, khachHang.getAnhDaiDien());
            pstmt.setInt(11, khachHang.getMaKH());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(KhachHangDTO khachHang, int biXoa) {

        if (selectById(khachHang.getMaKH(), biXoa).getCccd().equals(khachHang.getCccd())) {
            return updateExecute(khachHang);
        } else if (selectByCCCD(khachHang.getCccd(), biXoa) != null) {
            return false;
        } else {
            return updateExecute(khachHang);
        }

    }

    public boolean delete(int maKhachHang) {
        String sql = "UPDATE tbl_khach_hang SET bi_xoa = ? WHERE ma_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            pstmt.setInt(2, maKhachHang);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean restore(int maKhachHang) {
        String sql = "UPDATE tbl_khach_hang SET bi_xoa = ? WHERE ma_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 0);
            pstmt.setInt(2, maKhachHang);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHangDTO> selectAll(int biXoa) {
        String sql = "SELECT kh.*, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_khach_hang kh "
            + "LEFT JOIN tbl_phuong_xa wa ON kh.ma_phuong_xa = wa.wardId "
            + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
            + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
            + " WHERE kh.bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, biXoa);

            List<KhachHangDTO> list = new ArrayList<>();
            String diaChi = "";

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setMaKH(rs.getInt("ma_khach_hang"));
                    khachHang.setHoDem(rs.getString("ho_dem"));
                    khachHang.setTen(rs.getString("ten"));
                    khachHang.setGioiTinh(rs.getString("gioi_tinh"));
                    khachHang.setNgaySinh(rs.getDate("ngay_sinh"));
                    khachHang.setMaPhuongXa(rs.getInt("ma_phuong_xa"));
                    khachHang.setSoNha(rs.getString("so_nha"));
                    khachHang.setEmail(rs.getString("email"));
                    khachHang.setSdt(rs.getString("so_dien_thoai"));
                    khachHang.setCccd(rs.getString("cccd"));
                    khachHang.setAnhDaiDien(rs.getString("anh_dai_dien"));
                    khachHang.setNoXau(rs.getInt("no_xau"));

                    diaChi = rs.getString("so_nha") + ", " + rs.getString("wa.wardName") + ", " + rs.getString("dt.districtName") + ", " + rs.getString("pr.provinceName");

                    khachHang.setDiaChi(diaChi);

                    khachHang.setBiXoa(biXoa);

                    list.add(khachHang);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhachHangDTO selectById(int maKhachHang, int biXoa) {
        String diaChi = "";
        String sql;
        if (biXoa == 2) {
            sql = "SELECT kh.*, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_khach_hang kh "
                + "LEFT JOIN tbl_phuong_xa wa ON kh.ma_phuong_xa = wa.wardId "
                + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
                + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
                + " WHERE ma_khach_hang = ?";
        } else {
            sql = "SELECT kh.*, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_khach_hang kh "
                + "LEFT JOIN tbl_phuong_xa wa ON kh.ma_phuong_xa = wa.wardId "
                + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
                + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
                + " WHERE ma_khach_hang = ? AND bi_xoa = ?";
        }
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            if (biXoa == 2) {
                pstmt.setInt(1, maKhachHang);
            } else {
                pstmt.setInt(1, maKhachHang);
                pstmt.setInt(2, biXoa);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setMaKH(rs.getInt("kh.ma_khach_hang"));
                    khachHang.setHoDem(rs.getString("ho_dem"));
                    khachHang.setTen(rs.getString("ten"));
                    khachHang.setGioiTinh(rs.getString("gioi_tinh"));
                    khachHang.setNgaySinh(rs.getDate("ngay_sinh"));
                    khachHang.setMaPhuongXa(rs.getInt("ma_phuong_xa"));
                    khachHang.setSoNha(rs.getString("so_nha"));
                    khachHang.setEmail(rs.getString("email"));
                    khachHang.setSdt(rs.getString("so_dien_thoai"));
                    khachHang.setCccd(rs.getString("cccd"));
                    khachHang.setAnhDaiDien(rs.getString("anh_dai_dien"));

                    diaChi = rs.getString("so_nha") + ", " + rs.getString("wa.wardName") + ", " + rs.getString("dt.districtName") + ", " + rs.getString("pr.provinceName");

                    khachHang.setDiaChi(diaChi);
                    khachHang.setNoXau(rs.getInt("no_xau"));
                    khachHang.setBiXoa(rs.getInt("bi_xoa"));

                    return khachHang;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public KhachHangDTO selectByCCCD(String cccd, int biXoa) {
        String sql = "SELECT * FROM tbl_khach_hang WHERE cccd = ? AND bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, cccd);
            pstmt.setInt(2, biXoa);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setMaKH(rs.getInt("ma_khach_hang"));
                    khachHang.setBiXoa(biXoa);

                    return khachHang;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<KhachHangDTO> filter(int biXoa, String gender, java.sql.Date dateFrom, java.sql.Date dateTo, int provinceId, int districtId, int wardId, int noXau) {
        String sql = "SELECT kh.*, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_khach_hang kh "
            + "LEFT JOIN tbl_phuong_xa wa ON kh.ma_phuong_xa = wa.wardId "
            + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
            + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
            + "WHERE bi_xoa = ? ";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(biXoa);

        if (!gender.equals("All")) {
            conditionalClause.append(" AND kh.gioi_tinh = ?");
            params.add(gender);
        }

        if (dateFrom != null && dateTo != null) {
            conditionalClause.append(" AND kh.ngay_sinh BETWEEN ? AND ?");
            params.add(dateFrom);
            params.add(dateTo);
        }

        if (dateFrom != null && dateTo == null) {
            conditionalClause.append(" AND kh.ngay_sinh > ?");
            params.add(dateFrom);
        }

        if (dateFrom == null && dateTo != null) {
            conditionalClause.append(" AND kh.ngay_sinh < ?");
            params.add(dateTo);
        }

        if (provinceId != 0) {
            conditionalClause.append(" AND pr.provinceId = ?");
            params.add(provinceId);
        }

        if (districtId != 0) {
            conditionalClause.append(" AND dt.districtId = ?");
            params.add(districtId);
        }

        if (wardId != 0) {
            conditionalClause.append(" AND wa.wardId = ?");
            params.add(wardId);
        }

        if (noXau != 2) {
            conditionalClause.append(" AND no_xau = ?");
            params.add(noXau);
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }

            List<KhachHangDTO> list = new ArrayList<>();
            String diaChi = "";
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setMaKH(rs.getInt("ma_khach_hang"));
                    khachHang.setHoDem(rs.getString("ho_dem"));
                    khachHang.setTen(rs.getString("ten"));
                    khachHang.setGioiTinh(rs.getString("gioi_tinh"));
                    khachHang.setNgaySinh(rs.getDate("ngay_sinh"));
                    khachHang.setMaPhuongXa(rs.getInt("ma_phuong_xa"));
                    khachHang.setSoNha(rs.getString("so_nha"));
                    khachHang.setEmail(rs.getString("email"));
                    khachHang.setSdt(rs.getString("so_dien_thoai"));
                    khachHang.setCccd(rs.getString("cccd"));
                    khachHang.setAnhDaiDien(rs.getString("anh_dai_dien"));
                    khachHang.setNoXau(rs.getInt("no_xau"));

                    diaChi = rs.getString("so_nha") + ", " + rs.getString("wa.wardName") + ", " + rs.getString("dt.districtName") + ", " + rs.getString("pr.provinceName");

                    khachHang.setDiaChi(diaChi);
                    khachHang.setBiXoa(rs.getInt("bi_xoa"));

                    list.add(khachHang);
                }

                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean xoaNoXau(int maKhachHang) {
        String sql = "UPDATE tbl_khach_hang SET no_xau = ? WHERE ma_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 0);
            pstmt.setInt(2, maKhachHang);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KhachHangDTO> searchByInputType(int biXoa, String typeName, String inputValue) {
        String sql = "SELECT kh.*, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_khach_hang kh "
            + "LEFT JOIN tbl_phuong_xa wa ON kh.ma_phuong_xa = wa.wardId "
            + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
            + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
            + "WHERE bi_xoa = ? ";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(biXoa);

        if (typeName.equals("name")) {
            conditionalClause.append(" AND (kh.ho_dem LIKE ? OR kh.ten LIKE ? OR CONCAT(kh.ho_dem, ' ', kh.ten) LIKE ?)");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("phoneNumber")) {
            conditionalClause.append(" AND kh.so_dien_thoai LIKE ?");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("email")) {
            conditionalClause.append(" AND kh.email LIKE ?");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("cccd")) {
            conditionalClause.append(" AND kh.cccd LIKE ?");
            params.add("%" + inputValue + "%");
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }

            List<KhachHangDTO> list = new ArrayList<>();
            String diaChi = "";
            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    KhachHangDTO khachHang = new KhachHangDTO();
                    khachHang.setMaKH(rs.getInt("ma_khach_hang"));
                    khachHang.setHoDem(rs.getString("ho_dem"));
                    khachHang.setTen(rs.getString("ten"));
                    khachHang.setGioiTinh(rs.getString("gioi_tinh"));
                    khachHang.setNgaySinh(rs.getDate("ngay_sinh"));
                    khachHang.setMaPhuongXa(rs.getInt("ma_phuong_xa"));
                    khachHang.setSoNha(rs.getString("so_nha"));
                    khachHang.setEmail(rs.getString("email"));
                    khachHang.setSdt(rs.getString("so_dien_thoai"));
                    khachHang.setCccd(rs.getString("cccd"));
                    khachHang.setAnhDaiDien(rs.getString("anh_dai_dien"));
                    khachHang.setNoXau(rs.getInt("no_xau"));

                    diaChi = rs.getString("so_nha") + ", " + rs.getString("wa.wardName") + ", " + rs.getString("dt.districtName") + ", " + rs.getString("pr.provinceName");

                    khachHang.setDiaChi(diaChi);
                    khachHang.setBiXoa(rs.getInt("bi_xoa"));

                    list.add(khachHang);
                }
                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
