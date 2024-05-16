package quanlynganhang.DTO;

import java.util.Date;


public class TheATMDTO {
    private int maThe, maLoaiThe, maTrangThai, maTaiKhoanKH, maKhachHang;
    private String soThe, tenThe, maPIN, tenTrangThai, hoTenKH, tenLoaiThe;
    private Date thoiHanThe, ngayTao;

    public TheATMDTO() {
    }

    public TheATMDTO(String tenThe, int maKhachHang, int maThe, int maLoaiThe, int maTrangThai, int maTaiKhoanKH, String soThe, String maPIN, String tenTrangThai, String hoTenKH, String tenLoaiThe, Date thoiHanThe, Date ngayTao) {
        this.tenThe = tenThe;
        this.maKhachHang = maKhachHang;
        this.maThe = maThe;
        this.maLoaiThe = maLoaiThe;
        this.maTrangThai = maTrangThai;
        this.maTaiKhoanKH = maTaiKhoanKH;
        this.soThe = soThe;
        this.maPIN = maPIN;
        this.tenTrangThai = tenTrangThai;
        this.hoTenKH = hoTenKH;
        this.tenLoaiThe = tenLoaiThe;
        this.thoiHanThe = thoiHanThe;
        this.ngayTao = ngayTao;
    }

    public int getMaThe() {
        return maThe;
    }

    public void setMaThe(int maThe) {
        this.maThe = maThe;
    }

    public int getMaLoaiThe() {
        return maLoaiThe;
    }

    public void setMaLoaiThe(int maLoaiThe) {
        this.maLoaiThe = maLoaiThe;
    }

    public int getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(int maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    public int getMaTaiKhoanKH() {
        return maTaiKhoanKH;
    }

    public void setMaTaiKhoanKH(int maTaiKhoanKH) {
        this.maTaiKhoanKH = maTaiKhoanKH;
    }

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
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

    public String getHoTenKH() {
        return hoTenKH;
    }

    public void setHoTenKH(String hoTenKH) {
        this.hoTenKH = hoTenKH;
    }

    public String getTenLoaiThe() {
        return tenLoaiThe;
    }

    public void setTenLoaiThe(String tenLoaiThe) {
        this.tenLoaiThe = tenLoaiThe;
    }

    public Date getThoiHanThe() {
        return thoiHanThe;
    }

    public void setThoiHanThe(Date thoiHanThe) {
        this.thoiHanThe = thoiHanThe;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenThe() {
        return tenThe;
    }

    public void setTenThe(String tenThe) {
        this.tenThe = tenThe;
    }
    
    
}
