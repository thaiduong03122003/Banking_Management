package quanlynganhang.DTO;

import java.util.Date;

public class TaiKhoanNVDTO {
    private int maTKNV, maNhanVien, maTrangThai, maChucVu, tinhTrangDangNhap;
    private String tenDangNhap, matKhau, tenTrangThai, tenNhanVien, tenChucVu;
    private Date ngayTaoTK;

    public TaiKhoanNVDTO() {
    }

    public int getTinhTrangDangNhap() {
        return tinhTrangDangNhap;
    }

    public void setTinhTrangDangNhap(int tinhTrangDangNhap) {
        this.tinhTrangDangNhap = tinhTrangDangNhap;
    }
    
    public int getMaTKNV() {
        return maTKNV;
    }

    public void setMaTKNV(int maTKNV) {
        this.maTKNV = maTKNV;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public int getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(int maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    public int getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(int maChucVu) {
        this.maChucVu = maChucVu;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public Date getNgayTaoTK() {
        return ngayTaoTK;
    }

    public void setNgayTaoTK(Date ngayTaoTK) {
        this.ngayTaoTK = ngayTaoTK;
    }

    
}
