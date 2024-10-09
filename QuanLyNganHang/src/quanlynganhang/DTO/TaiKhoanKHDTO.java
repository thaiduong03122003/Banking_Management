package quanlynganhang.DTO;

import java.util.Date;

public class TaiKhoanKHDTO {
    private int maTKKH, maNganHang, maLoaiTaiKhoan, maKhachHang, maTrangThai, biXoa;
    private String soTaiKhoan, tenTaiKhoan, matKhau, soDu, tenNganHang, tenLoaiTaiKhoan, tenKhachHang, tenTrangThai, email, sdt;
    private Date ngayTao;

    public TaiKhoanKHDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
    
    public int getBiXoa() {
        return biXoa;
    }

    public void setBiXoa(int biXoa) {
        this.biXoa = biXoa;
    }
    
    public int getMaTKKH() {
        return maTKKH;
    }

    public void setMaTKKH(int maTKKH) {
        this.maTKKH = maTKKH;
    }

    public int getMaNganHang() {
        return maNganHang;
    }

    public void setMaNganHang(int maNganHang) {
        this.maNganHang = maNganHang;
    }

    public int getMaLoaiTaiKhoan() {
        return maLoaiTaiKhoan;
    }

    public void setMaLoaiTaiKhoan(int maLoaiTaiKhoan) {
        this.maLoaiTaiKhoan = maLoaiTaiKhoan;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(int maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSoDu() {
        return soDu;
    }

    public void setSoDu(String soDu) {
        this.soDu = soDu;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }

    public String getTenLoaiTaiKhoan() {
        return tenLoaiTaiKhoan;
    }

    public void setTenLoaiTaiKhoan(String tenLoaiTaiKhoan) {
        this.tenLoaiTaiKhoan = tenLoaiTaiKhoan;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

}
