package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.NhanVienDTO;

public class NhanVienDAO {

    public NhanVienDTO insert(NhanVienDTO nhanVien, int biXoa) {
        if (selectByCCCD(nhanVien.getCccd(), biXoa) != null) {
            return null;
        } else {
            String sql = "INSERT INTO tbl_nhan_vien(ho_dem, ten, gioi_tinh, ngay_sinh, ma_phuong_xa, so_nha, email, so_dien_thoai, cccd, anh_dai_dien, ma_chuc_vu, ngay_vao_lam, bi_xoa)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

                java.sql.Date dateNgayVaoLam = new Date(nhanVien.getNgayVaoLam().getTime());
                pstmt.setDate(12, dateNgayVaoLam);

                pstmt.setInt(13, 0);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    nhanVien.setMaNV(rs.getInt(1));
                }

                return nhanVien;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private boolean updateExecute(NhanVienDTO nhanVien) {
        String sql = "UPDATE tbl_nhan_vien SET ho_dem = ?, ten = ?, gioi_tinh = ?, ngay_sinh = ?, ma_phuong_xa = ?, so_nha = ?, email = ?, so_dien_thoai = ?, cccd = ?, anh_dai_dien = ?, ngay_vao_lam = ? WHERE ma_nhan_vien = ?";

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

            java.sql.Date dateNgayVaoLam = new Date(nhanVien.getNgayVaoLam().getTime());
            pstmt.setDate(11, dateNgayVaoLam);

            pstmt.setInt(12, nhanVien.getMaNV());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(NhanVienDTO nhanVien, int biXoa) {

        if (selectById(nhanVien.getMaNV(), biXoa).getCccd().equals(nhanVien.getCccd())) {
            return updateExecute(nhanVien);
        } else if (selectByCCCD(nhanVien.getCccd(), biXoa) != null) {
            return false;
        } else {
            return updateExecute(nhanVien);
        }

    }

    public boolean delete(int maNhanVien) {
        String sql = "UPDATE tbl_nhan_vien SET bi_xoa = ? WHERE ma_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            pstmt.setInt(2, maNhanVien);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean restore(int maNhanVien) {
        String sql = "UPDATE tbl_nhan_vien SET bi_xoa = ? WHERE ma_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 0);
            pstmt.setInt(2, maNhanVien);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<NhanVienDTO> selectAll(int biXoa) {
        String sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_nhan_vien nv "
            + "LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu "
            + "LEFT JOIN tbl_phuong_xa wa ON nv.ma_phuong_xa = wa.wardId "
            + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
            + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
            + " WHERE nv.bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, biXoa);

            List<NhanVienDTO> list = new ArrayList<>();
            String diaChi = "";

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
                    nhanVien.setNgayVaoLam(rs.getDate("ngay_vao_lam"));

                    diaChi = rs.getString("so_nha") + ", " + rs.getString("wa.wardName") + ", " + rs.getString("dt.districtName") + ", " + rs.getString("pr.provinceName");

                    nhanVien.setDiaChi(diaChi);

                    nhanVien.setBiXoa(biXoa);

                    list.add(nhanVien);
                }
                
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NhanVienDTO selectById(int maNhanVien, int biXoa) {
        String diaChi = "";
        String sql;
        if (biXoa == 2) {
            sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_nhan_vien nv "
                + "LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu "
                + "LEFT JOIN tbl_phuong_xa wa ON nv.ma_phuong_xa = wa.wardId "
                + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
                + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
                + " WHERE ma_nhan_vien = ?";
        } else {
            sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_nhan_vien nv "
                + "LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu "
                + "LEFT JOIN tbl_phuong_xa wa ON nv.ma_phuong_xa = wa.wardId "
                + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
                + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
                + " WHERE nv.ma_nhan_vien = ? AND nv.bi_xoa = ?";
        }
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            if (biXoa == 2) {
                pstmt.setInt(1, maNhanVien);
            } else {
                pstmt.setInt(1, maNhanVien);
                pstmt.setInt(2, biXoa);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien.setMaNV(rs.getInt("nv.ma_nhan_vien"));
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
                    nhanVien.setNgayVaoLam(rs.getDate("ngay_vao_lam"));

                    diaChi = rs.getString("so_nha") + ", " + rs.getString("wa.wardName") + ", " + rs.getString("dt.districtName") + ", " + rs.getString("pr.provinceName");

                    nhanVien.setDiaChi(diaChi);

                    nhanVien.setBiXoa(rs.getInt("nv.bi_xoa"));

                    return nhanVien;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NhanVienDTO selectByCCCD(String cccd, int biXoa) {
        String sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu FROM tbl_nhan_vien nv LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu WHERE nv.cccd = ? AND nv.bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, cccd);
            pstmt.setInt(2, biXoa);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien.setMaNV(rs.getInt("ma_nhan_vien"));
                    nhanVien.setBiXoa(biXoa);

                    return nhanVien;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<NhanVienDTO> filter(int biXoa, String gender, java.sql.Date dateFrom, java.sql.Date dateTo, int provinceId, int districtId, int wardId, int roleId) {
        String sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_nhan_vien nv "
            + "LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu "
            + "LEFT JOIN tbl_phuong_xa wa ON nv.ma_phuong_xa = wa.wardId "
            + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
            + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
            + "WHERE nv.bi_xoa = ? ";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(biXoa);

        if (!gender.equals("All")) {
            conditionalClause.append(" AND nv.gioi_tinh = ?");
            params.add(gender);
        }

        if (dateFrom != null && dateTo != null) {
            conditionalClause.append(" AND nv.ngay_sinh BETWEEN ? AND ?");
            params.add(dateFrom);
            params.add(dateTo);
        }

        if (dateFrom != null && dateTo == null) {
            conditionalClause.append(" AND nv.ngay_sinh > ?");
            params.add(dateFrom);
        }

        if (dateFrom == null && dateTo != null) {
            conditionalClause.append(" AND nv.ngay_sinh < ?");
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

        if (roleId != 0) {
            conditionalClause.append(" AND nv.ma_chuc_vu = ?");
            params.add(roleId);
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }

            List<NhanVienDTO> list = new ArrayList<>();
            String diaChi = "";
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
                    nhanVien.setNgayVaoLam(rs.getDate("ngay_vao_lam"));

                    diaChi = rs.getString("so_nha") + ", " + rs.getString("wa.wardName") + ", " + rs.getString("dt.districtName") + ", " + rs.getString("pr.provinceName");

                    nhanVien.setDiaChi(diaChi);

                    nhanVien.setBiXoa(rs.getInt("bi_xoa"));
                    list.add(nhanVien);
                }
                return list;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<NhanVienDTO> searchByInputType(int biXoa, String typeName, String inputValue) {
        String sql = "SELECT nv.*, cv.ma_chuc_vu, cv.ten_chuc_vu, pr.provinceId, pr.provinceName, dt.districtId, dt.districtName, wa.wardId, wa.wardName FROM tbl_nhan_vien nv "
            + "LEFT JOIN tbl_chuc_vu cv ON nv.ma_chuc_vu = cv.ma_chuc_vu "
            + "LEFT JOIN tbl_phuong_xa wa ON nv.ma_phuong_xa = wa.wardId "
            + "LEFT JOIN tbl_quan_huyen dt ON wa.districtId = dt.districtId "
            + "LEFT JOIN tbl_tinh_thanh pr ON dt.provinceId = pr.provinceId "
            + "WHERE nv.bi_xoa = ? ";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(biXoa);

        if (typeName.equals("name")) {
            conditionalClause.append(" AND (nv.ho_dem LIKE ? OR nv.ten LIKE ? OR CONCAT(nv.ho_dem, ' ', nv.ten) LIKE ?)");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("phoneNumber")) {
            conditionalClause.append(" AND nv.so_dien_thoai LIKE ?");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("email")) {
            conditionalClause.append(" AND nv.email LIKE ?");
            params.add("%" + inputValue + "%");
        }

        if (typeName.equals("cccd")) {
            conditionalClause.append(" AND nv.cccd LIKE ?");
            params.add("%" + inputValue + "%");
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }

            List<NhanVienDTO> list = new ArrayList<>();
            String diaChi = "";
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
                    nhanVien.setNgayVaoLam(rs.getDate("ngay_vao_lam"));

                    diaChi = rs.getString("so_nha") + ", " + rs.getString("wa.wardName") + ", " + rs.getString("dt.districtName") + ", " + rs.getString("pr.provinceName");

                    nhanVien.setDiaChi(diaChi);

                    nhanVien.setBiXoa(rs.getInt("bi_xoa"));
                    list.add(nhanVien);
                }
                return list;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean changeRole(int maNhanVien, int maChucVu) {
        String sql = "UPDATE tbl_nhan_vien SET ma_chuc_vu = ? WHERE ma_nhan_vien = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maChucVu);
            pstmt.setInt(2, maNhanVien);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
