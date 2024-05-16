package quanlynganhang.DTO;

import java.util.Date;

public class GiaoDichDTO {
    private int maGiaoDich, maTaiKhoanKH, maTaiKhoanNV, maLoaiGiaoDich, maTrangThai;
    private String tenKhachHang, tenNhanVien, noiDungGiaoDich, tenLoaiGiaoDich, tenTrangThai, soTien, soTaiKhoan;
    private Date ngayGiaoDich;

    public GiaoDichDTO() {
    }

    public GiaoDichDTO(String soTaiKhoan, int maGiaoDich, int maTaiKhoanKH, int maTaiKhoanNV, int maLoaiGiaoDich, int maTrangThai, String soTien, String tenKhachHang, String tenNhanVien, String noiDungGiaoDich, String tenLoaiGiaoDich, String tenTrangThai, Date ngayGiaoDich) {
        this.soTaiKhoan = soTaiKhoan;
        this.maGiaoDich = maGiaoDich;
        this.maTaiKhoanKH = maTaiKhoanKH;
        this.maTaiKhoanNV = maTaiKhoanNV;
        this.maLoaiGiaoDich = maLoaiGiaoDich;
        this.maTrangThai = maTrangThai;
        this.soTien = soTien;
        this.tenKhachHang = tenKhachHang;
        this.tenNhanVien = tenNhanVien;
        this.noiDungGiaoDich = noiDungGiaoDich;
        this.tenLoaiGiaoDich = tenLoaiGiaoDich;
        this.tenTrangThai = tenTrangThai;
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public int getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(int maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public int getMaTaiKhoanKH() {
        return maTaiKhoanKH;
    }

    public void setMaTaiKhoanKH(int maTaiKhoanKH) {
        this.maTaiKhoanKH = maTaiKhoanKH;
    }

    public int getMaTaiKhoanNV() {
        return maTaiKhoanNV;
    }

    public void setMaTaiKhoanNV(int maTaiKhoanNV) {
        this.maTaiKhoanNV = maTaiKhoanNV;
    }

    public int getMaLoaiGiaoDich() {
        return maLoaiGiaoDich;
    }

    public void setMaLoaiGiaoDich(int maLoaiGiaoDich) {
        this.maLoaiGiaoDich = maLoaiGiaoDich;
    }

    public int getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(int maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    public String getSoTien() {
        return soTien;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getNoiDungGiaoDich() {
        return noiDungGiaoDich;
    }

    public void setNoiDungGiaoDich(String noiDungGiaoDich) {
        this.noiDungGiaoDich = noiDungGiaoDich;
    }

    public String getTenLoaiGiaoDich() {
        return tenLoaiGiaoDich;
    }

    public void setTenLoaiGiaoDich(String tenLoaiGiaoDich) {
        this.tenLoaiGiaoDich = tenLoaiGiaoDich;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public Date getNgayGiaoDich() {
        return ngayGiaoDich;
    }

    public void setNgayGiaoDich(Date ngayGiaoDich) {
        this.ngayGiaoDich = ngayGiaoDich;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }
    
}
