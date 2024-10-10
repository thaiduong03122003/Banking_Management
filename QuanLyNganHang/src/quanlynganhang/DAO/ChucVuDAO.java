package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.ChucVuDTO;

public class ChucVuDAO {

    public ChucVuDTO insert(ChucVuDTO chucVu, int biXoa) {
        if (selectByName(chucVu.getTenChucVu(), biXoa) != null) {
            return null;
        } else {
            String sql = "INSERT INTO tbl_chuc_vu(ten_chuc_vu, mo_ta, admin, thong_ke, khach_hang, nhan_vien, tk_khach_hang, tk_nhan_vien, the, giao_dich, gui_tiet_kiem, vay_von, phan_quyen, chuc_vu, bi_xoa)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setString(1, chucVu.getTenChucVu());
                pstmt.setString(2, chucVu.getMoTa());
                pstmt.setInt(3, chucVu.getIsAdmin());
                pstmt.setInt(4, chucVu.getqLThongKe());
                pstmt.setString(5, chucVu.getqLKhachHang());
                pstmt.setString(6, chucVu.getqLNhanVien());
                pstmt.setString(7, chucVu.getqLTKKhachHang());
                pstmt.setString(8, chucVu.getqLTKNhanVien());
                pstmt.setString(9, chucVu.getqLThe());
                pstmt.setInt(10, chucVu.getqLGiaoDich());
                pstmt.setInt(11, chucVu.getqLGuiTietKiem());
                pstmt.setInt(12, chucVu.getqLVayVon());
                pstmt.setInt(13, chucVu.getPhanQuyen());
                pstmt.setString(14, chucVu.getqlChucVu());
                pstmt.setInt(15, 0);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    chucVu.setMaChucVu(rs.getInt(1));
                }

                return chucVu;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private boolean updateExecute(ChucVuDTO chucVu, int biXoa) {
        String sql = "UPDATE tbl_chuc_vu SET ten_chuc_vu = ?, mo_ta = ?, admin = ?, thong_ke = ?, khach_hang = ?, nhan_vien = ?, tk_khach_hang = ?, tk_nhan_vien = ?, the = ?, giao_dich =?, gui_tiet_kiem = ?, vay_von = ?, phan_quyen = ?, chuc_vu = ?, bi_xoa = ? WHERE ma_chuc_vu = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, chucVu.getTenChucVu());
            pstmt.setString(2, chucVu.getMoTa());
            pstmt.setInt(3, chucVu.getIsAdmin());
            pstmt.setInt(4, chucVu.getqLThongKe());
            pstmt.setString(5, chucVu.getqLKhachHang());
            pstmt.setString(6, chucVu.getqLNhanVien());
            pstmt.setString(7, chucVu.getqLTKKhachHang());
            pstmt.setString(8, chucVu.getqLTKNhanVien());
            pstmt.setString(9, chucVu.getqLThe());
            pstmt.setInt(10, chucVu.getqLGiaoDich());
            pstmt.setInt(11, chucVu.getqLGuiTietKiem());
            pstmt.setInt(12, chucVu.getqLVayVon());
            pstmt.setInt(13, chucVu.getPhanQuyen());
            pstmt.setString(14, chucVu.getqlChucVu());
            pstmt.setInt(15, biXoa);
            pstmt.setInt(16, chucVu.getMaChucVu());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(ChucVuDTO chucVu, int biXoa) {

        if (selectById(chucVu.getMaChucVu(), biXoa).getTenChucVu().equals(chucVu.getTenChucVu())) {
            return updateExecute(chucVu, biXoa);
        } else if (selectByName(chucVu.getTenChucVu(), biXoa) != null) {
            return false;
        } else {
            return updateExecute(chucVu, biXoa);
        }

    }

    public boolean delete(int maChucVu) {
        String sql = "UPDATE tbl_chuc_vu SET bi_xoa = ? WHERE ma_chuc_vu = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, 1);
            pstmt.setInt(2, maChucVu);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ChucVuDTO> selectAll(int biXoa) {
        String sql = "SELECT * FROM tbl_chuc_vu WHERE bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, biXoa);

            List<ChucVuDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ChucVuDTO chucVu = new ChucVuDTO();
                    chucVu.setMaChucVu(rs.getInt("ma_chuc_vu"));
                    chucVu.setTenChucVu(rs.getString("ten_chuc_vu"));
                    chucVu.setMoTa(rs.getString("mo_ta"));
                    chucVu.setIsAdmin(rs.getInt("admin"));
                    chucVu.setqLThongKe(rs.getInt("thong_ke"));
                    chucVu.setqLKhachHang(rs.getString("khach_hang"));
                    chucVu.setqLNhanVien(rs.getString("nhan_vien"));
                    chucVu.setqLTKKhachHang(rs.getString("tk_khach_hang"));
                    chucVu.setqLTKNhanVien(rs.getString("tk_nhan_vien"));
                    chucVu.setqLThe(rs.getString("the"));
                    chucVu.setqLGiaoDich(rs.getInt("giao_dich"));
                    chucVu.setqLGuiTietKiem(rs.getInt("gui_tiet_kiem"));
                    chucVu.setqLVayVon(rs.getInt("vay_von"));
                    chucVu.setPhanQuyen(rs.getInt("phan_quyen"));
                    chucVu.setqlChucVu(rs.getString("chuc_vu"));
                    chucVu.setBiXoa(biXoa);

                    list.add(chucVu);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChucVuDTO selectById(int maChucVu, int biXoa) {
        String sql = "SELECT * FROM tbl_chuc_vu WHERE ma_chuc_vu = ? AND bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maChucVu);
            pstmt.setInt(2, biXoa);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ChucVuDTO chucVu = new ChucVuDTO();
                    chucVu.setMaChucVu(rs.getInt("ma_chuc_vu"));
                    chucVu.setTenChucVu(rs.getString("ten_chuc_vu"));
                    chucVu.setMoTa(rs.getString("mo_ta"));
                    chucVu.setIsAdmin(rs.getInt("admin"));
                    chucVu.setqLThongKe(rs.getInt("thong_ke"));
                    chucVu.setqLKhachHang(rs.getString("khach_hang"));
                    chucVu.setqLNhanVien(rs.getString("nhan_vien"));
                    chucVu.setqLTKKhachHang(rs.getString("tk_khach_hang"));
                    chucVu.setqLTKNhanVien(rs.getString("tk_nhan_vien"));
                    chucVu.setqLThe(rs.getString("the"));
                    chucVu.setqLGiaoDich(rs.getInt("giao_dich"));
                    chucVu.setqLGuiTietKiem(rs.getInt("gui_tiet_kiem"));
                    chucVu.setqLVayVon(rs.getInt("vay_von"));
                    chucVu.setPhanQuyen(rs.getInt("phan_quyen"));
                    chucVu.setqlChucVu(rs.getString("chuc_vu"));
                    chucVu.setBiXoa(biXoa);

                    return chucVu;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ChucVuDTO selectByName(String tenChucVu, int biXoa) {
        String sql = "SELECT * FROM tbl_chuc_vu WHERE ten_chuc_vu = ? AND bi_xoa = ? ORDER BY ma_chuc_vu DESC LIMIT 1";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, tenChucVu);
            pstmt.setInt(2, biXoa);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ChucVuDTO chucVu = new ChucVuDTO();
                    chucVu.setMaChucVu(rs.getInt("ma_chuc_vu"));
                    chucVu.setTenChucVu(rs.getString("ten_chuc_vu"));
                    chucVu.setMoTa(rs.getString("mo_ta"));
                    chucVu.setIsAdmin(rs.getInt("admin"));
                    chucVu.setqLThongKe(rs.getInt("thong_ke"));
                    chucVu.setqLKhachHang(rs.getString("khach_hang"));
                    chucVu.setqLNhanVien(rs.getString("nhan_vien"));
                    chucVu.setqLTKKhachHang(rs.getString("tk_khach_hang"));
                    chucVu.setqLTKNhanVien(rs.getString("tk_nhan_vien"));
                    chucVu.setqLThe(rs.getString("the"));
                    chucVu.setqLGiaoDich(rs.getInt("giao_dich"));
                    chucVu.setqLGuiTietKiem(rs.getInt("gui_tiet_kiem"));
                    chucVu.setqLVayVon(rs.getInt("vay_von"));
                    chucVu.setPhanQuyen(rs.getInt("phan_quyen"));
                    chucVu.setqlChucVu(rs.getString("chuc_vu"));
                    chucVu.setBiXoa(biXoa);

                    return chucVu;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChucVuDTO> getNORole() {
        String sql = "SELECT cv.ma_chuc_vu, cv.ten_chuc_vu, COUNT(nv.ma_chuc_vu) AS so_chuc_vu FROM tbl_chuc_vu cv"
            + " LEFT JOIN tbl_nhan_vien nv ON cv.ma_chuc_vu = nv.ma_chuc_vu"
            + " WHERE cv.bi_xoa = ?"
            + " GROUP BY cv.ma_chuc_vu, cv.ten_chuc_vu";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 0);

            List<ChucVuDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ChucVuDTO chucVu = new ChucVuDTO();
                    chucVu.setMaChucVu(rs.getInt("cv.ma_chuc_vu"));
                    chucVu.setTenChucVu(rs.getString("cv.ten_chuc_vu"));
                    chucVu.setSoChucVu(rs.getInt("so_chuc_vu"));
                    chucVu.setBiXoa(0);

                    list.add(chucVu);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ChucVuDTO> searchByInputType(String inputValue, int biXoa) {
        String sql = "SELECT * FROM tbl_chuc_vu WHERE ten_chuc_vu LIKE ? AND bi_xoa = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, "%" + inputValue + "%");
            pstmt.setInt(2, biXoa);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<ChucVuDTO> list = new ArrayList<>();
                while (rs.next()) {
                    ChucVuDTO chucVu = new ChucVuDTO();
                    chucVu.setMaChucVu(rs.getInt("ma_chuc_vu"));
                    chucVu.setTenChucVu(rs.getString("ten_chuc_vu"));
                    chucVu.setMoTa(rs.getString("mo_ta"));
                    chucVu.setIsAdmin(rs.getInt("admin"));
                    chucVu.setqLThongKe(rs.getInt("thong_ke"));
                    chucVu.setqLKhachHang(rs.getString("khach_hang"));
                    chucVu.setqLNhanVien(rs.getString("nhan_vien"));
                    chucVu.setqLTKKhachHang(rs.getString("tk_khach_hang"));
                    chucVu.setqLTKNhanVien(rs.getString("tk_nhan_vien"));
                    chucVu.setqLThe(rs.getString("the"));
                    chucVu.setqLGiaoDich(rs.getInt("giao_dich"));
                    chucVu.setqLGuiTietKiem(rs.getInt("gui_tiet_kiem"));
                    chucVu.setqLVayVon(rs.getInt("vay_von"));
                    chucVu.setPhanQuyen(rs.getInt("phan_quyen"));
                    chucVu.setqlChucVu(rs.getString("chuc_vu"));
                    chucVu.setBiXoa(rs.getInt("bi_xoa"));

                    list.add(chucVu);
                }
                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
