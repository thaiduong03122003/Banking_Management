/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.DAO;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import quanlynganhang.DTO.ChucVuDTO;

public class ThongKeDAO {

    public String tongTien() {
        String sql = "SELECT * FROM tbl_kho_tien WHERE ma_kho_tien = ?";
        String tongTienString = "";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 1);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String tienTKString = rs.getString("tien_tai_khoan");
                    String tienMatString = rs.getString("tien_mat");

                    BigInteger tienTK = BigInteger.valueOf(Long.parseLong(tienTKString));
                    BigInteger tienMat = BigInteger.valueOf(Long.parseLong(tienMatString));

                    BigInteger tongTien = tienTK.add(tienMat);
                    tongTienString = tongTien.toString();
                }
            }
            return tongTienString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public int tongKhachHang() {
        String sql = "SELECT COUNT(ma_khach_hang) AS tong_so_khach_hang FROM tbl_khach_hang WHERE bi_xoa = ?";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 0);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongKH = rs.getInt("tong_so_khach_hang");

                    return tongKH;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tongNhanVien() {
        String sql = "SELECT COUNT(ma_nhan_vien) AS tong_so_nhan_vien FROM tbl_nhan_vien WHERE bi_xoa = ?";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 0);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongNV = rs.getInt("tong_so_nhan_vien");

                    return tongNV;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tongTaiKhoanKH() {
        String sql = "SELECT COUNT(ma_tk_khach_hang) AS tong_so_tk_khach_hang FROM tbl_tai_khoan_khach_hang";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongTKKH = rs.getInt("tong_so_tk_khach_hang");

                    return tongTKKH;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tongTaiKhoanNV() {
        String sql = "SELECT COUNT(ma_tk_nhan_vien) AS tong_so_tk_nhan_vien FROM tbl_tai_khoan_nhan_vien";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongTKNV = rs.getInt("tong_so_tk_nhan_vien");

                    return tongTKNV;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tongThe() {
        String sql = "SELECT COUNT(ma_the) AS tong_so_the FROM tbl_the_atm";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongThe = rs.getInt("tong_so_the");

                    return tongThe;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tongGiaoDich() {
        String sql = "SELECT COUNT(ma_giao_dich) AS tong_giao_dich FROM tbl_giao_dich";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongGiaoDich = rs.getInt("tong_giao_dich");

                    return tongGiaoDich;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tongLuotRutTien() {
        String sql = "SELECT COUNT(ma_giao_dich) AS tong_luot_rut_tien FROM tbl_giao_dich WHERE ma_loai_giao_dich = ?";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 3);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongRutTien = rs.getInt("tong_luot_rut_tien");

                    return tongRutTien;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tongLuotNapTien() {
        String sql = "SELECT COUNT(ma_giao_dich) AS tong_luot_nap_tien FROM tbl_giao_dich WHERE ma_loai_giao_dich = ?";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 4);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongNapTien = rs.getInt("tong_luot_nap_tien");

                    return tongNapTien;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String soTienLonNhat() {
        String sql = "SELECT so_tien FROM tbl_giao_dich";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            BigInteger max = new BigInteger("0");
            BigInteger soTien;
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    soTien = new BigInteger(rs.getString("so_tien"));

                    if (max.compareTo(soTien) <= 0) {
                        max = soTien;
                    }

                }
                return max.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "0";
    }

    public int tongGuiTK() {
        String sql = "SELECT COUNT(ma_giao_dich) AS tong_gui_tiet_kiem FROM tbl_giao_dich WHERE ma_loai_giao_dich = ?";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 5);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongGTK = rs.getInt("tong_gui_tiet_kiem");

                    return tongGTK;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int tongVayVon() {
        String sql = "SELECT COUNT(ma_giao_dich) AS tong_vay_von FROM tbl_giao_dich WHERE ma_loai_giao_dich = 7";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int tongVV = rs.getInt("tong_vay_von");

                    return tongVV;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String tongTienTrongKho() {
        String sql = "SELECT * FROM tbl_kho_tien";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    BigInteger tienMat = new BigInteger(rs.getString("tien_mat"));
                    BigInteger tienTK = new BigInteger(rs.getString("tien_tai_khoan"));
                    return (tienMat.add(tienTK).toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public String tongTienRut() {
        String sql = "SELECT so_tien FROM tbl_giao_dich WHERE ma_loai_giao_dich = ?";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 3);

            BigInteger sum = new BigInteger("0");
            BigInteger soTien;
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    soTien = new BigInteger(rs.getString("so_tien"));

                    sum = sum.add(soTien);

                }
                return sum.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public String tongTienNap() {
        String sql = "SELECT so_tien FROM tbl_giao_dich WHERE ma_loai_giao_dich = ?";
        try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql);) {
            pstmt.setInt(1, 4);

            BigInteger sum = new BigInteger("0");
            BigInteger soTien;
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    soTien = new BigInteger(rs.getString("so_tien"));

                    sum = sum.add(soTien);

                }
                return sum.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public String tongTienVayVon() {
    String sql = "SELECT SUM(so_tien_vay) AS tong_tien_vay_von FROM tbl_dich_vu_vay_von";
    try (Connection con = DatabaseConnect.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {

        try (ResultSet rs = pstmt.executeQuery()) {
            String tongTienVV = "0";

            if (rs.next()) {
                BigDecimal sum = rs.getBigDecimal("tong_tien_vay_von");
                tongTienVV = (sum != null) ? sum.toPlainString() : "0";
            }
            return tongTienVV;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "0";
}

}
