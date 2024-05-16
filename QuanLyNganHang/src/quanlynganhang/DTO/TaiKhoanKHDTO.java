package quanlynganhang.DTO;

import java.util.Date;

public class TaiKhoanKHDTO {
    private int maTKKH, maNganHang, maLoaiTaiKhoan, maKhachHang, maTrangThai;
    private String soTaiKhoan, tenTaiKhoan, matKhau, soDu, tenNganHang, tenLoaiTaiKhoan, tenKhachHang, tenTrangThai;
    private Date ngayTao;

    public TaiKhoanKHDTO() {
    }

    public TaiKhoanKHDTO(int maTKKH, int maNganHang, int maLoaiTaiKhoan, int maKhachHang, int maTrangThai, String soTaiKhoan, String tenTaiKhoan, String matKhau, String soDu, String tenNganHang, String tenLoaiTaiKhoan, String tenKhachHang, String tenTrangThai, Date ngayTao) {
        this.maTKKH = maTKKH;
        this.maNganHang = maNganHang;
        this.maLoaiTaiKhoan = maLoaiTaiKhoan;
        this.maKhachHang = maKhachHang;
        this.maTrangThai = maTrangThai;
        this.soTaiKhoan = soTaiKhoan;
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.soDu = soDu;
        this.tenNganHang = tenNganHang;
        this.tenLoaiTaiKhoan = tenLoaiTaiKhoan;
        this.tenKhachHang = tenKhachHang;
        this.tenTrangThai = tenTrangThai;
        this.ngayTao = ngayTao;
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
