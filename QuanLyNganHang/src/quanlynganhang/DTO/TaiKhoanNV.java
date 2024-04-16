package quanlynganhang.DTO;

public class TaiKhoanNV {
    private int maTKNV;
    private String tenDangNhap;
    private String matKhau;
    private String maPIN;
    private NhanVienDTO nhanVien;
    private int maTrangThai;

    public TaiKhoanNV() {
    }

    public TaiKhoanNV(int maTKNV, String tenDangNhap, String matKhau, String maPIN, NhanVienDTO nhanVien, int maTrangThai) {
        this.maTKNV = maTKNV;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.maPIN = maPIN;
        this.nhanVien = nhanVien;
        this.maTrangThai = maTrangThai;
    }

    public int getMaTKNV() {
        return maTKNV;
    }

    public void setMaTKNV(int maTKNV) {
        this.maTKNV = maTKNV;
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

    public NhanVienDTO getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVienDTO nhanVien) {
        this.nhanVien = nhanVien;
    }

    public int getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(int maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    
    
    
}
