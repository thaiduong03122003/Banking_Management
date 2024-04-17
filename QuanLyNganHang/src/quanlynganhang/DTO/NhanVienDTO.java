package quanlynganhang.DTO;

import java.util.Date;

public class NhanVienDTO {
    private int maNV, maPhuongXa, maChucVu, biXoa;
    private String hoDem, ten, gioiTinh, soNha, email, sdt, cccd, anhDaiDien, tenChucVu;
    private Date ngaySinh, ngayVaoLam;
    
    public NhanVienDTO() {
    }

    public NhanVienDTO(int maNV, int maPhuongXa, int maChucVu, int biXoa, String hoDem, String ten, String gioiTinh, String soNha, String email, String sdt, String cccd, String anhDaiDien, String tenChucVu, Date ngaySinh, Date ngayVaoLam) {
        this.maNV = maNV;
        this.maPhuongXa = maPhuongXa;
        this.maChucVu = maChucVu;
        this.biXoa = biXoa;
        this.hoDem = hoDem;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.soNha = soNha;
        this.email = email;
        this.sdt = sdt;
        this.cccd = cccd;
        this.anhDaiDien = anhDaiDien;
        this.tenChucVu = tenChucVu;
        this.ngaySinh = ngaySinh;
        this.ngayVaoLam = ngayVaoLam;
    }

    

    public int getMaNV() {
        return maNV;
    }

    public void setMaNV(int maNV) {
        this.maNV = maNV;
    }

    public int getMaPhuongXa() {
        return maPhuongXa;
    }

    public void setMaPhuongXa(int maPhuongXa) {
        this.maPhuongXa = maPhuongXa;
    }

    public int getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(int maChucVu) {
        this.maChucVu = maChucVu;
    }

    public int getBiXoa() {
        return biXoa;
    }

    public void setBiXoa(int biXoa) {
        this.biXoa = biXoa;
    }

    public String getHoDem() {
        return hoDem;
    }

    public void setHoDem(String hoDem) {
        this.hoDem = hoDem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
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

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getTenChucVu() {
        return tenChucVu;
    }

    public void setTenChucVu(String tenChucVu) {
        this.tenChucVu = tenChucVu;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }
    
}
