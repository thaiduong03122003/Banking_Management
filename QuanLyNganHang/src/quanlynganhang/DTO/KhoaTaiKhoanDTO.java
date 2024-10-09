package quanlynganhang.DTO;

import java.util.Date;

public class KhoaTaiKhoanDTO {
    private int maKhoaTK, maTaiKhoan, moKhoa;
    private String lyDoKhoa, loaiTaiKhoan;
    private Date ngayMoKhoa;

    public KhoaTaiKhoanDTO() {
    }

    public int getMaKhoaTK() {
        return maKhoaTK;
    }

    public void setMaKhoaTK(int maKhoaTK) {
        this.maKhoaTK = maKhoaTK;
    }

    public int getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(int maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
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
