package quanlynganhang.DTO;

import java.util.Date;

public class TaiKhoanNVDTO {
    private int maTKNV, maNhanVien, maTrangThai;
    private String tenDangNhap, matKhau, maPIN, tenTrangThai, tenNhanVien;
    private Date ngayTaoTK;

    public TaiKhoanNVDTO() {
    }

    public TaiKhoanNVDTO(int maTKNV, int maNhanVien, int maTrangThai, String tenDangNhap, String matKhau, String maPIN, String tenTrangThai, String tenNhanVien, Date ngayTaoTK) {
        this.maTKNV = maTKNV;
        this.maNhanVien = maNhanVien;
        this.maTrangThai = maTrangThai;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maPIN = maPIN;
        this.tenTrangThai = tenTrangThai;
        this.tenNhanVien = tenNhanVien;
        this.ngayTaoTK = ngayTaoTK;
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

    public String getMaPIN() {
        return maPIN;
    }

    public void setMaPIN(String maPIN) {
        this.maPIN = maPIN;
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

    public Date getNgayTaoTK() {
        return ngayTaoTK;
    }

    public void setNgayTaoTK(Date ngayTaoTK) {
        this.ngayTaoTK = ngayTaoTK;
    }

    
}
