package quanlynganhang.DTO;

import java.util.Date;

public class TietKiemDTO {
    private int maGuiTK, maKyHan, maGiaoDich, maTaiKhoanTK, maTaiKhoanNguonTien, maTrangThai, soKyHan;
    private String hinhThucGiaHan, hinhThucNhanLai, soTienGoc;
    private Date ngayMoTK, ngayNhanLai;
    private double laiSuat;
    
    public TietKiemDTO() {
    }

    public TietKiemDTO(int maGuiTK, int maKyHan, int maGiaoDich, int maTaiKhoanTK, int maTaiKhoanNguonTien, int maTrangThai, int soKyHan, String hinhThucGiaHan, String hinhThucNhanLai, String soTienGoc, Date ngayMoTK, Date ngayNhanLai, double laiSuat) {
        this.maGuiTK = maGuiTK;
        this.maKyHan = maKyHan;
        this.maGiaoDich = maGiaoDich;
        this.maTaiKhoanTK = maTaiKhoanTK;
        this.maTaiKhoanNguonTien = maTaiKhoanNguonTien;
        this.maTrangThai = maTrangThai;
        this.soKyHan = soKyHan;
        this.hinhThucGiaHan = hinhThucGiaHan;
        this.hinhThucNhanLai = hinhThucNhanLai;
        this.soTienGoc = soTienGoc;
        this.ngayMoTK = ngayMoTK;
        this.ngayNhanLai = ngayNhanLai;
        this.laiSuat = laiSuat;
    }

    public int getMaGuiTK() {
        return maGuiTK;
    }

    public void setMaGuiTK(int maGuiTK) {
        this.maGuiTK = maGuiTK;
    }

    public int getMaKyHan() {
        return maKyHan;
    }

    public void setMaKyHan(int maKyHan) {
        this.maKyHan = maKyHan;
    }

    public int getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(int maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public int getMaTaiKhoanTK() {
        return maTaiKhoanTK;
    }

    public void setMaTaiKhoanTK(int maTaiKhoanTK) {
        this.maTaiKhoanTK = maTaiKhoanTK;
    }

    public int getMaTaiKhoanNguonTien() {
        return maTaiKhoanNguonTien;
    }

    public void setMaTaiKhoanNguonTien(int maTaiKhoanNguonTien) {
        this.maTaiKhoanNguonTien = maTaiKhoanNguonTien;
    }

    public int getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(int maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    public int getSoKyHan() {
        return soKyHan;
    }

    public void setSoKyHan(int soKyHan) {
        this.soKyHan = soKyHan;
    }

    public String getHinhThucGiaHan() {
        return hinhThucGiaHan;
    }

    public void setHinhThucGiaHan(String hinhThucGiaHan) {
        this.hinhThucGiaHan = hinhThucGiaHan;
    }

    public String getHinhThucNhanLai() {
        return hinhThucNhanLai;
    }

    public void setHinhThucNhanLai(String hinhThucNhanLai) {
        this.hinhThucNhanLai = hinhThucNhanLai;
    }

    public String getSoTienGoc() {
        return soTienGoc;
    }

    public void setSoTienGoc(String soTienGoc) {
        this.soTienGoc = soTienGoc;
    }

    public Date getNgayMoTK() {
        return ngayMoTK;
    }

    public void setNgayMoTK(Date ngayMoTK) {
        this.ngayMoTK = ngayMoTK;
    }

    public Date getNgayNhanLai() {
        return ngayNhanLai;
    }

    public void setNgayNhanLai(Date ngayNhanLai) {
        this.ngayNhanLai = ngayNhanLai;
    }

    public double getLaiSuat() {
        return laiSuat;
    }

    public void setLaiSuat(double laiSuat) {
        this.laiSuat = laiSuat;
    }

    
}
