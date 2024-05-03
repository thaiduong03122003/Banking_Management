/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.DTO;

import java.util.Date;

/**
 *
 * @author THAI
 */
public class KhachHangDTO {
    private int maKH, maPhuongXa, noXau, biXoa;
    private String hoDem, ten, gioiTinh, soNha, email, sdt, cccd, anhDaiDien, diaChi;
    private Date ngaySinh;
    
    public KhachHangDTO() {
    }

    public KhachHangDTO(int maKH, int maPhuongXa, int noXau, int biXoa, String hoDem, String ten, String gioiTinh, String soNha, String email, String sdt, String cccd, String anhDaiDien, String diaChi, Date ngaySinh) {
        this.maKH = maKH;
        this.maPhuongXa = maPhuongXa;
        this.noXau = noXau;
        this.biXoa = biXoa;
        this.hoDem = hoDem;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.soNha = soNha;
        this.email = email;
        this.sdt = sdt;
        this.cccd = cccd;
        this.anhDaiDien = anhDaiDien;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }

    public int getMaPhuongXa() {
        return maPhuongXa;
    }

    public void setMaPhuongXa(int maPhuongXa) {
        this.maPhuongXa = maPhuongXa;
    }

    public int getNoXau() {
        return noXau;
    }

    public void setNoXau(int noXau) {
        this.noXau = noXau;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    @Override
    public String toString() {
        return maKH + " - " + hoDem + " " + ten;
    }
}
