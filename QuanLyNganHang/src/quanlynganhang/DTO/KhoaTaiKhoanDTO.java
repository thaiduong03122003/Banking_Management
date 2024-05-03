package quanlynganhang.DTO;

import java.util.Date;

public class KhoaTaiKhoanDTO {
    private int maKhoaTK, maTaiKhoanNV, maTaiKhoanKH, moKhoa;
    private String lyDoKhoa, loaiTaiKhoan;
    private Date ngayMoKhoa;

    public KhoaTaiKhoanDTO() {
    }

    public KhoaTaiKhoanDTO(int maKhoaTK, int maTaiKhoanNV, int maTaiKhoanKH, int moKhoa, String lyDoKhoa, String loaiTaiKhoan, Date ngayMoKhoa) {
        this.maKhoaTK = maKhoaTK;
        this.maTaiKhoanNV = maTaiKhoanNV;
        this.maTaiKhoanKH = maTaiKhoanKH;
        this.moKhoa = moKhoa;
        this.lyDoKhoa = lyDoKhoa;
        this.loaiTaiKhoan = loaiTaiKhoan;
        this.ngayMoKhoa = ngayMoKhoa;
    }

    public int getMaKhoaTK() {
        return maKhoaTK;
    }

    public void setMaKhoaTK(int maKhoaTK) {
        this.maKhoaTK = maKhoaTK;
    }

    public int getMaTaiKhoanNV() {
        return maTaiKhoanNV;
    }

    public void setMaTaiKhoanNV(int maTaiKhoanNV) {
        this.maTaiKhoanNV = maTaiKhoanNV;
    }

    public int getMaTaiKhoanKH() {
        return maTaiKhoanKH;
    }

    public void setMaTaiKhoanKH(int maTaiKhoanKH) {
        this.maTaiKhoanKH = maTaiKhoanKH;
    }

    public int getMoKhoa() {
        return moKhoa;
    }

    public void setMoKhoa(int moKhoa) {
        this.moKhoa = moKhoa;
    }

    public String getLyDoKhoa() {
        return lyDoKhoa;
    }

    public void setLyDoKhoa(String lyDoKhoa) {
        this.lyDoKhoa = lyDoKhoa;
    }

    public String getLoaiTaiKhoan() {
        return loaiTaiKhoan;
    }

    public void setLoaiTaiKhoan(String loaiTaiKhoan) {
        this.loaiTaiKhoan = loaiTaiKhoan;
    }

    public Date getNgayMoKhoa() {
        return ngayMoKhoa;
    }

    public void setNgayMoKhoa(Date ngayMoKhoa) {
        this.ngayMoKhoa = ngayMoKhoa;
    }
    
    
}
