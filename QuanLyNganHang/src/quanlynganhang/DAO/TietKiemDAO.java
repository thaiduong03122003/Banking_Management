package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.TietKiemDTO;

public class TietKiemDAO {

    public int insert(TietKiemDTO tietKiem) throws Exception {

        if (tietKiem.getMaTaiKhoanNguonTien() == 0) {
            String sql = "INSERT INTO tbl_dich_vu_gui_tiet_kiem(ma_ky_han, hinh_thuc_gia_han, hinh_thuc_nhan_lai, ma_tk_khach_hang, ngay_mo_tk, ngay_nhan_lai, so_tien_goc, ma_trang_thai)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setInt(1, tietKiem.getMaKyHan());
                pstmt.setString(2, tietKiem.getHinhThucGiaHan());
                pstmt.setString(3, tietKiem.getHinhThucNhanLai());
                pstmt.setInt(4, tietKiem.getMaTaiKhoanTK());
                
                java.sql.Date dateMoTK = new Date(tietKiem.getNgayMoTK().getTime());
                pstmt.setDate(5, dateMoTK);

                java.sql.Date dateNhanLai = new Date(tietKiem.getNgayNhanLai().getTime());
                pstmt.setDate(6, dateNhanLai);

                pstmt.setString(7, tietKiem.getSoTienGoc());
                pstmt.setInt(8, tietKiem.getMaTrangThai());

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    tietKiem.setMaGuiTK(rs.getInt(1));
                }

                return tietKiem.getMaGuiTK();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        } else {
            String sql = "INSERT INTO tbl_dich_vu_gui_tiet_kiem(ma_ky_han, hinh_thuc_gia_han, hinh_thuc_nhan_lai, ma_tk_khach_hang, ma_tk_nguon_tien, ngay_mo_tk, ngay_nhan_lai, so_tien_goc, ma_trang_thai)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setInt(1, tietKiem.getMaKyHan());
                pstmt.setString(2, tietKiem.getHinhThucGiaHan());
                pstmt.setString(3, tietKiem.getHinhThucNhanLai());
                pstmt.setInt(4, tietKiem.getMaTaiKhoanTK());
                pstmt.setInt(5, tietKiem.getMaTaiKhoanNguonTien());
                
                java.sql.Date dateMoTK = new Date(tietKiem.getNgayMoTK().getTime());
                pstmt.setDate(6, dateMoTK);

                java.sql.Date dateNgaySinh = new Date(tietKiem.getNgayNhanLai().getTime());
                pstmt.setDate(7, dateNgaySinh);

                pstmt.setString(8, tietKiem.getSoTienGoc());
                pstmt.setInt(9, tietKiem.getMaTrangThai());
                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    tietKiem.setMaGuiTK(rs.getInt(1));
                }

                return tietKiem.getMaGuiTK();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    public List<TietKiemDTO> selectAll() throws Exception {
        String sql = "SELECT gtk.*, kyhan.* FROM tbl_dich_vu_gui_tiet_kiem gtk"
            + " LEFT JOIN tbl_ky_han_gui kyhan ON gtk.ma_ky_han = kyhan.ma_ky_han"
            + " WHERE ma_trang_thai = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 10);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<TietKiemDTO> listTK = new ArrayList<>();
                while (rs.next()) {
                    TietKiemDTO tietKiem = new TietKiemDTO();
                    tietKiem.setMaGuiTK(rs.getInt("ma_gui_tk"));
                    tietKiem.setMaKyHan(rs.getInt("ma_ky_han"));
                    tietKiem.setHinhThucGiaHan(rs.getString("hinh_thuc_gia_han"));
                    tietKiem.setHinhThucNhanLai(rs.getString("hinh_thuc_nhan_lai"));
                    tietKiem.setMaTaiKhoanTK(rs.getInt("ma_tk_khach_hang"));

                    Integer matTaiKhoanNguon = rs.getInt("ma_tk_nguon_tien");
                    if (matTaiKhoanNguon != null) {
                        tietKiem.setMaTaiKhoanNguonTien(matTaiKhoanNguon);
                    }

                    tietKiem.setNgayMoTK(rs.getDate("ngay_mo_tk"));
                    tietKiem.setNgayNhanLai(rs.getDate("ngay_nhan_lai"));
                    tietKiem.setSoTienGoc(rs.getString("so_tien_goc"));
                    tietKiem.setMaKyHan(rs.getInt("gtk.ma_ky_han"));
                    tietKiem.setSoKyHan(rs.getInt("kyhan.so_ky_han"));
                    tietKiem.setLaiSuat(rs.getDouble("kyhan.lai_suat"));
                    tietKiem.setMaTrangThai(rs.getInt("ma_trang_thai"));

                    listTK.add(tietKiem);
                }

                return listTK;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    
    public TietKiemDTO selectById(int maGuiTK) throws Exception {
        String sql = "SELECT gtk.*, kyhan.* FROM tbl_dich_vu_gui_tiet_kiem gtk"
            + " LEFT JOIN tbl_ky_han_gui kyhan ON gtk.ma_ky_han = kyhan.ma_ky_han"
            + " WHERE gtk.ma_gui_tk = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maGuiTK);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TietKiemDTO tietKiem = new TietKiemDTO();
                    tietKiem.setMaGuiTK(rs.getInt("ma_gui_tk"));
                    tietKiem.setMaTaiKhoanTK(rs.getInt("ma_tk_khach_hang"));
                    tietKiem.setMaTaiKhoanNguonTien(rs.getInt("ma_tk_nguon_tien"));
                    tietKiem.setMaKyHan(rs.getInt("ma_ky_han"));
                    tietKiem.setSoKyHan(rs.getInt("kyhan.so_ky_han"));
                    tietKiem.setLaiSuat(rs.getDouble("kyhan.lai_suat"));
                    tietKiem.setHinhThucGiaHan(rs.getString("hinh_thuc_gia_han"));
                    tietKiem.setHinhThucNhanLai(rs.getString("hinh_thuc_nhan_lai"));
                    tietKiem.setNgayMoTK(rs.getDate("ngay_mo_tk"));
                    tietKiem.setNgayNhanLai(rs.getDate("ngay_nhan_lai"));
                    tietKiem.setSoTienGoc(rs.getString("so_tien_goc"));
                    tietKiem.setMaTrangThai(rs.getInt("ma_trang_thai"));

                    return tietKiem;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TietKiemDTO selectByIdTKKH(int maTaiKhoanKH) throws Exception {
        String sql = "SELECT gtk.*, kyhan.* FROM tbl_dich_vu_gui_tiet_kiem gtk"
            + " LEFT JOIN tbl_ky_han_gui kyhan ON gtk.ma_ky_han = kyhan.ma_ky_han"
            + " WHERE gtk.ma_tk_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maTaiKhoanKH);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TietKiemDTO tietKiem = new TietKiemDTO();
                    tietKiem.setMaGuiTK(rs.getInt("ma_gui_tk"));
                    tietKiem.setMaTaiKhoanTK(rs.getInt("ma_tk_khach_hang"));
                    tietKiem.setMaTaiKhoanNguonTien(rs.getInt("ma_tk_nguon_tien"));
                    tietKiem.setMaKyHan(rs.getInt("ma_ky_han"));
                    tietKiem.setSoKyHan(rs.getInt("kyhan.so_ky_han"));
                    tietKiem.setLaiSuat(rs.getDouble("kyhan.lai_suat"));
                    tietKiem.setHinhThucGiaHan(rs.getString("hinh_thuc_gia_han"));
                    tietKiem.setHinhThucNhanLai(rs.getString("hinh_thuc_nhan_lai"));
                    tietKiem.setNgayMoTK(rs.getDate("ngay_mo_tk"));
                    tietKiem.setNgayNhanLai(rs.getDate("ngay_nhan_lai"));
                    tietKiem.setSoTienGoc(rs.getString("so_tien_goc"));
                    tietKiem.setMaTrangThai(rs.getInt("ma_trang_thai"));

                    return tietKiem;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateTietKiem(int maGuiTK, java.util.Date ngayGuiTK, java.util.Date ngayNhanLai, String soTienGoc) throws Exception {
        String sql = "UPDATE tbl_dich_vu_gui_tiet_kiem SET ngay_mo_tk = ?, ngay_nhan_lai = ?, so_tien_goc = ? WHERE ma_gui_tk = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            java.sql.Date dateMoTK = new Date(ngayGuiTK.getTime());
            pstmt.setDate(1, dateMoTK);
            
            java.sql.Date dateNhanLai = new Date(ngayNhanLai.getTime());
            pstmt.setDate(2, dateNhanLai);

            pstmt.setString(3, soTienGoc);
            pstmt.setInt(4, maGuiTK);

            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateSoTienGoc(int maGuiTK, String soTienGoc) throws Exception {
        String sql = "UPDATE tbl_dich_vu_gui_tiet_kiem SET so_tien_goc = ? WHERE ma_gui_tk = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, soTienGoc);
            pstmt.setInt(2, maGuiTK);

            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateTrangThai(int maGuiTK, int maTrangThai) throws Exception {
        String sql = "UPDATE tbl_dich_vu_gui_tiet_kiem SET ma_trang_thai = ? WHERE ma_gui_tk = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTrangThai);
            pstmt.setInt(2, maGuiTK);

            return pstmt.executeUpdate() > 0;
        }
    }
}
