package quanlynganhang.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.util.LocaleID;
import quanlynganhang.DTO.TheATMDTO;

public class TheATMDAO {
    public int insert(TheATMDTO theATM) throws Exception {
        if (selectByCardNum(theATM.getSoThe()) != null) {
            return 0;
        } else {
            String sql = "INSERT INTO tbl_the_atm(so_the, ten_the, ngay_tao, thoi_han_the, ma_PIN, ma_tk_khach_hang, ma_loai_the, ma_trang_thai)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {

                pstmt.setString(1, theATM.getSoThe());
                pstmt.setString(2, theATM.getTenThe());
                
                java.sql.Date dateTao = new Date(theATM.getNgayTao().getTime());
                pstmt.setDate(3, dateTao);
                
                java.sql.Date dateThoiHan = new Date(theATM.getThoiHanThe().getTime());
                pstmt.setDate(4, dateThoiHan);
                
                pstmt.setString(5, theATM.getMaPIN());
                pstmt.setInt(6, theATM.getMaTaiKhoanKH());

                pstmt.setInt(7, theATM.getMaLoaiThe());
                pstmt.setInt(8, 7);

                pstmt.executeUpdate();

                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    theATM.setMaThe(rs.getInt(1));
                }

                return theATM.getMaThe();
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

    private boolean updateExecute(TheATMDTO theATM) throws Exception {
        String sql = "UPDATE tbl_the_atm SET so_the = ? WHERE ma_the = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, theATM.getSoThe());
            pstmt.setInt(2, theATM.getMaThe());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean update(TheATMDTO theATM) throws Exception {

        if (selectById(theATM.getMaThe()).getSoThe().equals(theATM.getSoThe())) {
            return updateExecute(theATM);
        } else if (selectByCardNum(theATM.getSoThe()) != null) {
            return false;
        } else {
            return updateExecute(theATM);
        }

    }

    public boolean switchStatus(int maThe, int maTrangThai) {
        String sql = "UPDATE tbl_the_atm SET ma_trang_thai = ? WHERE ma_the = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maTrangThai);
            pstmt.setInt(2, maThe);

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TheATMDTO> selectAll() throws Exception {
        String sql = "SELECT the.*, tkkh.*, kh.*, loaithe.*, tt.* FROM tbl_the_atm the"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON the.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_loai_the_atm loaithe ON the.ma_loai_the = loaithe.ma_loai_the"
            + " LEFT JOIN tbl_trang_thai tt ON the.ma_trang_thai = tt.ma_trang_thai";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            List<TheATMDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TheATMDTO theATM = new TheATMDTO();
                    theATM.setMaThe(rs.getInt("ma_the"));
                    theATM.setSoThe(rs.getString("so_the"));
                    theATM.setTenThe(rs.getString("ten_the"));
                    theATM.setMaKhachHang(rs.getInt("kh.ma_khach_hang"));
                    theATM.setHoTenKH(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                    theATM.setMaTaiKhoanKH(rs.getInt("the.ma_tk_khach_hang"));
                    theATM.setNgayTao(rs.getDate("the.ngay_tao"));
                    theATM.setThoiHanThe(rs.getDate("the.thoi_han_the"));
                    theATM.setMaPIN(rs.getString("the.ma_PIN"));
                    theATM.setMaLoaiThe(rs.getInt("the.ma_loai_the"));
                    theATM.setTenLoaiThe(rs.getString("loaithe.ten_loai_the"));
                    theATM.setMaTrangThai(rs.getInt("the.ma_trang_thai"));
                    theATM.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    
                    list.add(theATM);
                }
            }
            return list;
        }
    }

    public TheATMDTO selectById(int maThe) throws Exception {
        String sql = "SELECT the.*, tkkh.*, kh.*, loaithe.*, tt.* FROM tbl_the_atm the"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON the.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_loai_the_atm loaithe ON the.ma_loai_the = loaithe.ma_loai_the"
            + " LEFT JOIN tbl_trang_thai tt ON the.ma_trang_thai = tt.ma_trang_thai"
            + " WHERE the.ma_the = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, maThe);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TheATMDTO theATM = new TheATMDTO();
                    
                    theATM.setMaThe(rs.getInt("ma_the"));
                    theATM.setSoThe(rs.getString("so_the"));
                    theATM.setTenThe(rs.getString("ten_the"));
                    theATM.setMaKhachHang(rs.getInt("kh.ma_khach_hang"));
                    theATM.setMaTaiKhoanKH(rs.getInt("the.ma_tk_khach_hang"));
                    theATM.setNgayTao(rs.getDate("the.ngay_tao"));
                    theATM.setThoiHanThe(rs.getDate("the.thoi_han_the"));
                    theATM.setMaPIN(rs.getString("the.ma_PIN"));
                    theATM.setMaLoaiThe(rs.getInt("the.ma_loai_the"));
                    theATM.setTenLoaiThe(rs.getString("loaithe.ten_loai_the"));
                    theATM.setMaTrangThai(rs.getInt("the.ma_trang_thai"));
                    theATM.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    
                    return theATM;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<TheATMDTO> selectByMaKH(int maKhachHang) throws Exception {
        String sql = "SELECT the.*, tkkh.*, kh.* FROM tbl_the_atm the"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON the.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_loai_the_atm loaithe ON the.ma_loai_the = loaithe.ma_loai_the"
            + " LEFT JOIN tbl_trang_thai tt ON the.ma_trang_thai = tt.ma_trang_thai"
            + " WHERE kh.ma_khach_hang = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setInt(1, maKhachHang);

            List<TheATMDTO> list = new ArrayList<>();

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TheATMDTO theATM = new TheATMDTO();
                    
                    theATM.setMaThe(rs.getInt("ma_the"));
                    theATM.setSoThe(rs.getString("so_the"));
                    theATM.setTenThe(rs.getString("ten_the"));
                    theATM.setMaKhachHang(rs.getInt("kh.ma_khach_hang"));
                    theATM.setHoTenKH(rs.getString("kh.hodem") + " " + rs.getString("kh.ten"));
                    theATM.setMaTaiKhoanKH(rs.getInt("the.ma_tk_khach_hang"));
                    theATM.setNgayTao(rs.getDate("the.ngay_tao"));
                    theATM.setThoiHanThe(rs.getDate("the.thoi_han_the"));
                    theATM.setMaPIN(rs.getString("the.ma_PIN"));
                    theATM.setMaLoaiThe(rs.getInt("the.ma_loai_the"));
                    theATM.setTenLoaiThe(rs.getString("loaithe.ten_loai_the"));
                    theATM.setMaTrangThai(rs.getInt("the.ma_trang_thai"));
                    theATM.setTenTrangThai(rs.getString("tt.ten_trang_thai"));
                    
                    list.add(theATM);
                }
            }
            return list;
        }
    }

    public TheATMDTO selectByCardNum(String soThe) throws Exception {
        String sql = "SELECT * FROM tbl_the_atm WHERE so_the = ?";
        
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setString(1, soThe);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    TheATMDTO theATM = new TheATMDTO();
                    
                    theATM.setMaThe(rs.getInt("ma_the"));
                    
                    return theATM;
                }
            }
            
            return null;
        }
    }

    public List<TheATMDTO> filter(java.sql.Date dateFrom, java.sql.Date dateTo, int maKhachHang, int maLoaiThe, int maTrangThai) throws Exception {
        String sql = "SELECT the.*, tkkh.*, kh.*, loaithe.*, tt.* FROM tbl_the_atm the"
            + " LEFT JOIN tbl_tai_khoan_khach_hang tkkh ON the.ma_tk_khach_hang = tkkh.ma_tk_khach_hang"
            + " LEFT JOIN tbl_khach_hang kh ON tkkh.ma_khach_hang = kh.ma_khach_hang"
            + " LEFT JOIN tbl_loai_the_atm loaithe ON the.ma_loai_the = loaithe.ma_loai_the"
            + " LEFT JOIN tbl_trang_thai tt ON the.ma_trang_thai = tt.ma_trang_thai"
            + " WHERE the.ma_the != ?";

        StringBuilder conditionalClause = new StringBuilder();
        List<Object> params = new ArrayList<>();
        params.add(0);

        if (dateFrom != null && dateTo != null) {
            conditionalClause.append(" AND the.ngay_tao BETWEEN ? AND ?");
            params.add(dateFrom);
            params.add(dateTo);
        }

        if (maKhachHang != 0) {
            conditionalClause.append(" AND kh.ma_khach_hang = ?");
            params.add(maKhachHang);
        }

        if (maLoaiThe != 0) {
            conditionalClause.append(" AND the.ma_loai_the = ?");
            params.add(maLoaiThe);
        }

        if (maTrangThai != 0) {
            conditionalClause.append(" AND the.ma_trang_thai = ?");
            params.add(maTrangThai);
        }

        if (conditionalClause.length() > 0) {
            sql += conditionalClause.toString();
            System.out.println("Cau sql: " + sql);
        }

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            for (int i = 1; i <= params.size(); i++) {
                pstmt.setObject(i, params.get(i - 1));
            }

            List<TheATMDTO> list = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    list = null;
                } else {
                    do {
                        TheATMDTO theATM = new TheATMDTO();
                    
                        theATM.setMaThe(rs.getInt("ma_the"));
                        theATM.setSoThe(rs.getString("so_the"));
                        theATM.setTenThe(rs.getString("ten_the"));
                        theATM.setMaKhachHang(rs.getInt("kh.ma_khach_hang"));
                        theATM.setHoTenKH(rs.getString("kh.ho_dem") + " " + rs.getString("kh.ten"));
                        theATM.setMaTaiKhoanKH(rs.getInt("the.ma_tk_khach_hang"));
                        theATM.setNgayTao(rs.getDate("the.ngay_tao"));
                        theATM.setThoiHanThe(rs.getDate("the.thoi_han_the"));
                        theATM.setMaPIN(rs.getString("the.ma_PIN"));
                        theATM.setMaLoaiThe(rs.getInt("the.ma_loai_the"));
                        theATM.setTenLoaiThe(rs.getString("loaithe.ten_loai_the"));
                        theATM.setMaTrangThai(rs.getInt("the.ma_trang_thai"));
                        theATM.setTenTrangThai(rs.getString("tt.ten_trang_thai"));

                        list.add(theATM);
                    } while (rs.next());
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                list = null;
            }
            return list;
        }
    }

    public boolean changePIN(TheATMDTO theATM) throws Exception {
        String sql = "UPDATE tbl_the_atm SET ma_PIN = ? WHERE ma_the = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            pstmt.setString(1, theATM.getMaPIN());
            pstmt.setInt(2, theATM.getMaThe());

            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean giaHanThe(TheATMDTO theATM) throws Exception {
        String sql = "UPDATE tbl_the_atm SET thoi_han_the = ? WHERE ma_the = ?";

        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            java.sql.Date dateThoiHan = new Date(theATM.getThoiHanThe().getTime());
            pstmt.setDate(1, dateThoiHan);
                
            pstmt.setInt(2, theATM.getMaThe());

            return pstmt.executeUpdate() > 0;
        }
    }
}
