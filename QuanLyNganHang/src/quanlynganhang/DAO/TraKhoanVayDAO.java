package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.TraKhoanVayDTO;

public class TraKhoanVayDAO {

    public int insert(TraKhoanVayDTO traVay) throws Exception {

        String sql = "INSERT INTO tbl_ky_tra_no(ma_vay_von, ky_tra_no, thoi_gian, so_tien_da_tra, tien_con_thieu, tien_no_goc, tien_lai, tien_phat, ma_trang_thai)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

            pstmt.setInt(1, traVay.getMaVayVon());
            pstmt.setInt(2, traVay.getKyTraNo());
            
            java.sql.Date thoiGian = new Date(traVay.getThoiGian().getTime());
            pstmt.setDate(3, thoiGian);
            
            pstmt.setString(4, traVay.getSoTienDaTra());
            pstmt.setString(5, traVay.getTienConThieu());
            pstmt.setString(6, traVay.getTienNoGoc());
            pstmt.setString(7, traVay.getTienLai());
            pstmt.setString(8, traVay.getTienPhat());
            pstmt.setInt(9, traVay.getMaTrangThai());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                traVay.setMaKyTraNo(rs.getInt(1));
            }

            return traVay.getMaKyTraNo();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<TraKhoanVayDTO> selectAll()throws Exception {
        String sql = "SELECT tkv.*, vv.* FROM tbl_ky_tra_no tkv"
            + " LEFT JOIN tbl_dich_vu_vay_von vv ON tkv.ma_vay_von = vv.ma_vay_von WHERE vv.ma_trang_thai != ? ORDER BY ma_ky_tra_no DESC";
        
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 8);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                List<TraKhoanVayDTO> listTKV = new ArrayList<>();
                while (rs.next()) {
                    TraKhoanVayDTO traVay = new TraKhoanVayDTO();
                    traVay.setMaKyTraNo(rs.getInt("ma_ky_tra_no"));
                    traVay.setMaVayVon(rs.getInt("ma_vay_von"));
                    traVay.setKyTraNo(rs.getInt("ky_tra_no"));
                    traVay.setThoiGian(rs.getDate("thoi_gian"));
                    traVay.setSoTienDaTra(rs.getString("so_tien_da_tra"));
                    traVay.setTienConThieu(rs.getString("tien_con_thieu"));
                    traVay.setTienNoGoc(rs.getString("tien_no_goc"));
                    traVay.setTienLai(rs.getString("tien_lai"));
                    traVay.setTienPhat(rs.getString("tien_phat"));
                    traVay.setNgayTraNo(rs.getDate("ngay_tra_no"));
                    traVay.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    listTKV.add(traVay);
                }

                return listTKV;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    public List<TraKhoanVayDTO> selectByMaVayVon(int maVayVon) throws Exception {
        String sql = "SELECT tkv.*, vv.*, tt.* FROM tbl_ky_tra_no tkv"
            + " LEFT JOIN tbl_dich_vu_vay_von vv ON tkv.ma_vay_von = vv.ma_vay_von"
            + " LEFT JOIN tbl_trang_thai tt ON tkv.ma_trang_thai = tt.ma_trang_thai"
            + " WHERE vv.ma_vay_von = ? ORDER BY ma_ky_tra_no DESC";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maVayVon);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<TraKhoanVayDTO> listTKV = new ArrayList<>();
                while (rs.next()) {
                    TraKhoanVayDTO traVay = new TraKhoanVayDTO();
                    traVay.setMaKyTraNo(rs.getInt("ma_ky_tra_no"));
                    traVay.setMaVayVon(rs.getInt("ma_vay_von"));
                    traVay.setKyTraNo(rs.getInt("ky_tra_no"));
                    traVay.setThoiGian(rs.getDate("thoi_gian"));
                    traVay.setSoTienDaTra(rs.getString("so_tien_da_tra"));
                    traVay.setTienConThieu(rs.getString("tien_con_thieu"));
                    traVay.setTienNoGoc(rs.getString("tien_no_goc"));
                    traVay.setTienLai(rs.getString("tien_lai"));
                    traVay.setTienPhat(rs.getString("tien_phat"));
                    traVay.setNgayTraNo(rs.getDate("ngay_tra_no"));
                    traVay.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    traVay.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    listTKV.add(traVay);
                }

                return listTKV;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public TraKhoanVayDTO selectById(int maKyTraNo) throws Exception {
        String sql = "SELECT * FROM tbl_ky_tra_no WHERE ma_ky_tra_no = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maKyTraNo);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TraKhoanVayDTO traVay = new TraKhoanVayDTO();
                    traVay.setMaKyTraNo(rs.getInt("ma_ky_tra_no"));
                    traVay.setMaVayVon(rs.getInt("ma_vay_von"));
                    traVay.setKyTraNo(rs.getInt("ky_tra_no"));
                    traVay.setThoiGian(rs.getDate("thoi_gian"));
                    traVay.setSoTienDaTra(rs.getString("so_tien_da_tra"));
                    traVay.setTienConThieu(rs.getString("tien_con_thieu"));
                    traVay.setTienNoGoc(rs.getString("tien_no_goc"));
                    traVay.setTienLai(rs.getString("tien_lai"));
                    traVay.setTienPhat(rs.getString("tien_phat"));
                    traVay.setNgayTraNo(rs.getDate("ngay_tra_no"));
                    traVay.setMaTrangThai(rs.getInt("ma_trang_thai"));

                    return traVay;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateKhoanVayDaTra(int maKyTraNo, String soTienDaTra, String soTienConThieu, java.util.Date ngayTraNo, int maTrangThai) throws Exception {
        String sql = "UPDATE tbl_ky_tra_no SET so_tien_da_tra = ?, tien_con_thieu = ?, ngay_tra_no = ?, ma_trang_thai = ? WHERE ma_ky_tra_no = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, soTienDaTra);
            pstmt.setString(2, soTienConThieu);
            
            java.sql.Date dateTraNo = new Date(ngayTraNo.getTime());
            pstmt.setDate(3, dateTraNo);
            pstmt.setInt(4, maTrangThai);
            pstmt.setInt(5, maKyTraNo);

            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateTienLai(int maKyTraNo, String tienLai) throws Exception {
        String sql = "UPDATE tbl_ky_tra_no SET tien_lai = ? WHERE ma_ky_tra_no = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, tienLai);
            pstmt.setInt(2, maKyTraNo);

            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateTienPhat(int maKyTraNo, String tienPhat) throws Exception {
        String sql = "UPDATE tbl_ky_tra_no SET tien_phat = ? WHERE ma_ky_tra_no = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, tienPhat);
            pstmt.setInt(2, maKyTraNo);

            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean updateTrangThai(int maKyTraNo, int maTrangThai) throws Exception {
        String sql = "UPDATE tbl_ky_tra_no SET ma_trang_thai = ? WHERE ma_ky_tra_no = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTrangThai);
            pstmt.setInt(2, maKyTraNo);

            return pstmt.executeUpdate() > 0;
        }
    }
    
    public List<TraKhoanVayDTO> selectDSKyVayTruocDo(int maVayVon, int kyVay) throws Exception {
        String sql = "SELECT * FROM tbl_ky_tra_no WHERE ma_vay_von = ? ORDER BY ma_ky_tra_no ASC LIMIT ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maVayVon);
            pstmt.setInt(2, kyVay - 1);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<TraKhoanVayDTO> listTKV = new ArrayList<>();
                while (rs.next()) {
                    TraKhoanVayDTO traVay = new TraKhoanVayDTO();
                    traVay.setMaKyTraNo(rs.getInt("ma_ky_tra_no"));
                    traVay.setMaVayVon(rs.getInt("ma_vay_von"));
                    traVay.setKyTraNo(rs.getInt("ky_tra_no"));
                    traVay.setThoiGian(rs.getDate("thoi_gian"));
                    traVay.setSoTienDaTra(rs.getString("so_tien_da_tra"));
                    traVay.setTienConThieu(rs.getString("tien_con_thieu"));
                    traVay.setTienNoGoc(rs.getString("tien_no_goc"));
                    traVay.setTienLai(rs.getString("tien_lai"));
                    traVay.setTienPhat(rs.getString("tien_phat"));
                    traVay.setNgayTraNo(rs.getDate("ngay_tra_no"));
                    traVay.setMaTrangThai(rs.getInt("ma_trang_thai"));
                    listTKV.add(traVay);
                }
                
                return listTKV;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
