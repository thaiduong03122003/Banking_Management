package quanlynganhang.DTO;

import java.util.Date;

public class VayVonDTO {
    private int maVayVon, maKhachHang, maTrangThai, maTaiKhoanKH, taiSanDamBao, maThoiHan, soThoiHan;
    private String soTienVay, anhMinhChung, duNoGoc;
    private Date ngayTraNo, ngayHetThoiHan;
    private double laiSuat;

    public VayVonDTO() {
    }

    public VayVonDTO(int maVayVon, int maKhachHang, int maTrangThai, int maTaiKhoanKH, int taiSanDamBao, int maThoiHan, int soThoiHan, String soTienVay, String anhMinhChung, String duNoGoc, Date ngayTraNo, Date ngayHetThoiHan, double laiSuat) {
        this.maVayVon = maVayVon;
        this.maKhachHang = maKhachHang;
        this.maTrangThai = maTrangThai;
        this.maTaiKhoanKH = maTaiKhoanKH;
        this.taiSanDamBao = taiSanDamBao;
        this.maThoiHan = maThoiHan;
        this.soThoiHan = soThoiHan;
        this.soTienVay = soTienVay;
        this.anhMinhChung = anhMinhChung;
        this.duNoGoc = duNoGoc;
        this.ngayTraNo = ngayTraNo;
        this.ngayHetThoiHan = ngayHetThoiHan;
        this.laiSuat = laiSuat;
    }

    public int getMaVayVon() {
        return maVayVon;
    }

    public void setMaVayVon(int maVayVon) {
        this.maVayVon = maVayVon;
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

    public int getMaTaiKhoanKH() {
        return maTaiKhoanKH;
    }

    public void setMaTaiKhoanKH(int maTaiKhoanKH) {
        this.maTaiKhoanKH = maTaiKhoanKH;
    }

    public int getTaiSanDamBao() {
        return taiSanDamBao;
    }

    public void setTaiSanDamBao(int taiSanDamBao) {
        this.taiSanDamBao = taiSanDamBao;
    }

    public int getMaThoiHan() {
        return maThoiHan;
    }

    public void setMaThoiHan(int maThoiHan) {
        this.maThoiHan = maThoiHan;
    }

    public int getSoThoiHan() {
        return soThoiHan;
    }

    public void setSoThoiHan(int soThoiHan) {
        this.soThoiHan = soThoiHan;
    }

    public String getSoTienVay() {
        return soTienVay;
    }

    public void setSoTienVay(String soTienVay) {
        this.soTienVay = soTienVay;
    }

    public String getAnhMinhChung() {
        return anhMinhChung;
    }

    public void setAnhMinhChung(String anhMinhChung) {
        this.anhMinhChung = anhMinhChung;
    }

    public String getDuNoGoc() {
        return duNoGoc;
    }

    public void setDuNoGoc(String duNoGoc) {
        this.duNoGoc = duNoGoc;
    }

    public Date getNgayTraNo() {
        return ngayTraNo;
    }

    public void setNgayTraNo(Date ngayTraNo) {
        this.ngayTraNo = ngayTraNo;
    }

    public Date getNgayHetThoiHan() {
        return ngayHetThoiHan;
    }

    public void setNgayHetThoiHan(Date ngayHetThoiHan) {
        this.ngayHetThoiHan = ngayHetThoiHan;
    }

    public double getLaiSuat() {
        return laiSuat;
    }

    public void setLaiSuat(double laiSuat) {
        this.laiSuat = laiSuat;
    }

    
}
