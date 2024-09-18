package quanlynganhang.DAO;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.VayVonDTO;

public class VayVonDAO {

    public int insert(VayVonDTO vayVon) throws Exception {
        String sql = "INSERT INTO tbl_dich_vu_vay_von(so_tien_vay, ma_thoi_han, ngay_het_thoi_han, ngay_tra_no, tai_san_dam_bao, anh_minh_chung, ma_tk_khach_hang, du_no_goc, ma_trang_thai)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
            pstmt.setString(1, vayVon.getSoTienVay());
            pstmt.setInt(2, vayVon.getMaThoiHan());

            java.sql.Date dateHetThoiHan = new Date(vayVon.getNgayHetThoiHan().getTime());
            pstmt.setDate(3, dateHetThoiHan);

            java.sql.Date dateTraNo = new Date(vayVon.getNgayTraNo().getTime());
            pstmt.setDate(4, dateTraNo);

            pstmt.setInt(5, vayVon.getTaiSanDamBao());
            pstmt.setString(6, vayVon.getAnhMinhChung());
            pstmt.setInt(7, vayVon.getMaTaiKhoanKH());
            pstmt.setString(8, vayVon.getDuNoGoc());
            pstmt.setInt(9, vayVon.getMaTrangThai());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                vayVon.setMaVayVon(rs.getInt(1));
            }

            return vayVon.getMaVayVon();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean switchStatus(int maVayVon, int maTrangThai) throws Exception {
        String sql = "UPDATE tbl_dich_vu_vay_von SET ma_trang_thai = ? WHERE ma_vay_von = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTrangThai);
            pstmt.setInt(2, maVayVon);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<VayVonDTO> selectAll() throws Exception {
        String sql = "SELECT vv.*, thoihan.*, tkkh.* FROM tbl_dich_vu_vay_von vv"
            + " LEFT JOIN tbl_thoi_han_vay thoihan ON vv.ma_thoi_han = thoihan.ma_thoi_han"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON vv.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " WHERE vv.ma_trang_thai != ? ORDER BY ma_vay_von DESC";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 9);

            List<VayVonDTO> listVayVon = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    VayVonDTO vayVon = new VayVonDTO();
                    vayVon.setMaVayVon(rs.getInt("ma_vay_von"));
                    vayVon.setLaiSuat(rs.getDouble("thoihan.lai_suat"));
                    vayVon.setSoThoiHan(rs.getInt("thoihan.so_thoi_han"));
                    vayVon.setSoTienVay(rs.getString("so_tien_vay"));
                    vayVon.setMaThoiHan(rs.getInt("vv.ma_thoi_han"));
                    vayVon.setNgayHetThoiHan(rs.getDate("ngay_het_thoi_han"));
                    vayVon.setNgayTraNo(rs.getDate("ngay_tra_no"));
                    vayVon.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    vayVon.setTaiSanDamBao(rs.getInt("tai_san_dam_bao"));
                    vayVon.setAnhMinhChung(rs.getString("anh_minh_chung"));
                    vayVon.setDuNoGoc(rs.getString("du_no_goc"));
                    vayVon.setMaTaiKhoanKH(rs.getInt("vv.ma_tk_khach_hang"));

                    listVayVon.add(vayVon);
                }
            }
            return listVayVon;
        }
    }
    
    public VayVonDTO selectById(int maVayVon) throws Exception {
        String sql = "SELECT vv.*, thoihan.*, tkkh.* FROM tbl_dich_vu_vay_von vv"
            + " LEFT JOIN tbl_thoi_han_vay thoihan ON vv.ma_thoi_han = thoihan.ma_thoi_han"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON vv.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " WHERE vv.ma_vay_von = ? AND tkkh.ma_trang_thai = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maVayVon);
            pstmt.setInt(2, 6);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    VayVonDTO vayVon = new VayVonDTO();
                    vayVon.setMaVayVon(rs.getInt("ma_vay_von"));
                    vayVon.setLaiSuat(rs.getDouble("thoihan.lai_suat"));
                    vayVon.setSoThoiHan(rs.getInt("thoihan.so_thoi_han"));
                    vayVon.setSoTienVay(rs.getString("so_tien_vay"));
                    vayVon.setMaThoiHan(rs.getInt("vv.ma_thoi_han"));
                    vayVon.setNgayHetThoiHan(rs.getDate("ngay_het_thoi_han"));
                    vayVon.setNgayTraNo(rs.getDate("ngay_tra_no"));
                    vayVon.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    vayVon.setTaiSanDamBao(rs.getInt("tai_san_dam_bao"));
                    vayVon.setAnhMinhChung(rs.getString("anh_minh_chung"));
                    vayVon.setDuNoGoc(rs.getString("du_no_goc"));
                    vayVon.setMaTaiKhoanKH(rs.getInt("ma_tk_khach_hang"));

                    return vayVon;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<VayVonDTO> selectByMaKH(int maKhachHang) throws Exception {
        String sql = "SELECT vv.*, thoihan.*, kh.* FROM tbl_dich_vu_vay_von vv"
            + " LEFT JOIN tbl_thoi_han_vay thoihan ON vv.ma_thoi_han = thoihan.ma_thoi_han"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON vv.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " WHERE tkkh.ma_khach_hang = ? ORDER BY ma_vay_von DESC";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maKhachHang);

            List<VayVonDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    VayVonDTO vayVon = new VayVonDTO();
                    vayVon.setMaVayVon(rs.getInt("ma_vay_von"));
                    vayVon.setLaiSuat(rs.getDouble("thoihan.lai_suat"));
                    vayVon.setSoThoiHan(rs.getInt("thoihan.so_thoi_han"));
                    vayVon.setSoTienVay(rs.getString("so_tien_vay"));
                    vayVon.setMaThoiHan(rs.getInt("vv.ma_thoi_han"));
                    vayVon.setNgayHetThoiHan(rs.getDate("ngay_het_thoi_han"));
                    vayVon.setNgayTraNo(rs.getDate("ngay_tra_no"));
                    vayVon.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    vayVon.setTaiSanDamBao(rs.getInt("tai_san_dam_bao"));
                    vayVon.setAnhMinhChung(rs.getString("anh_minh_chung"));
                    vayVon.setDuNoGoc(rs.getString("du_no_goc"));
                    vayVon.setMaTaiKhoanKH(rs.getInt("ma_tk_khach_hang"));

                    list.add(vayVon);
                }
            }
            return list;
        }
    }

    public VayVonDTO selectByMaTKKH(int maTKKH) throws Exception {
        String sql = "SELECT vv.*, thoihan.*, tkkh.* FROM tbl_dich_vu_vay_von vv"
            + " LEFT JOIN tbl_thoi_han_vay thoihan ON vv.ma_thoi_han = thoihan.ma_thoi_han"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON vv.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " WHERE tkkh.ma_tk_khach_hang = ? AND tkkh.ma_trang_thai = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maTKKH);
            pstmt.setInt(2, 6);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    VayVonDTO vayVon = new VayVonDTO();
                    vayVon.setMaVayVon(rs.getInt("ma_vay_von"));
                    vayVon.setLaiSuat(rs.getDouble("thoihan.lai_suat"));
                    vayVon.setSoThoiHan(rs.getInt("thoihan.so_thoi_han"));
                    vayVon.setSoTienVay(rs.getString("so_tien_vay"));
                    vayVon.setMaThoiHan(rs.getInt("vv.ma_thoi_han"));
                    vayVon.setNgayHetThoiHan(rs.getDate("ngay_het_thoi_han"));
                    vayVon.setNgayTraNo(rs.getDate("ngay_tra_no"));
                    vayVon.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    vayVon.setTaiSanDamBao(rs.getInt("tai_san_dam_bao"));
                    vayVon.setAnhMinhChung(rs.getString("anh_minh_chung"));
                    vayVon.setDuNoGoc(rs.getString("du_no_goc"));
                    vayVon.setMaTaiKhoanKH(rs.getInt("ma_tk_khach_hang"));

                    return vayVon;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean updateDuNo(int maVayVon, String duNo, int maTrangThai) throws Exception {
        String sql = "UPDATE tbl_dich_vu_vay_von SET du_no_goc = ?, ma_trang_thai = ? WHERE ma_vay_von = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, duNo);
            pstmt.setInt(2, maTrangThai);
            pstmt.setInt(3, maVayVon);

            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateNgayTraNo(int maVayVon, java.util.Date ngayTraNo) throws Exception {
        String sql = "UPDATE tbl_dich_vu_vay_von SET ngay_tra_no = ? WHERE ma_vay_von = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            java.sql.Date dateTraNo = new Date(ngayTraNo.getTime());
            pstmt.setDate(1, dateTraNo);
            pstmt.setInt(2, maVayVon);

            return pstmt.executeUpdate() > 0;
        }
    }
    
}
