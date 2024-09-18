package quanlynganhang.DTO;

import java.util.Date;

public class TraKhoanVayDTO {
    private int maKyTraNo, maVayVon, kyTraNo, maTrangThai;
    private String soTienDaTra, tienConThieu, tienNoGoc, tienLai, tienPhat, tenTrangThai;
    private Date thoiGian, ngayTraNo;

    public TraKhoanVayDTO() {
    }

    public int getMaKyTraNo() {
        return maKyTraNo;
    }

    public void setMaKyTraNo(int maKyTraNo) {
        this.maKyTraNo = maKyTraNo;
    }

    public int getMaVayVon() {
        return maVayVon;
    }

    public void setMaVayVon(int maVayVon) {
        this.maVayVon = maVayVon;
    }

    public int getKyTraNo() {
        return kyTraNo;
    }

    public void setKyTraNo(int kyTraNo) {
        this.kyTraNo = kyTraNo;
    }

    public int getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(int maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    public String getSoTienDaTra() {
        return soTienDaTra;
    }

    public void setSoTienDaTra(String soTienDaTra) {
        this.soTienDaTra = soTienDaTra;
    }

    public String getTienNoGoc() {
        return tienNoGoc;
    }

    public void setTienNoGoc(String tienNoGoc) {
        this.tienNoGoc = tienNoGoc;
    }

    public String getTienLai() {
        return tienLai;
    }

    public void setTienLai(String tienLai) {
        this.tienLai = tienLai;
    }

    public String getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(String tienPhat) {
        this.tienPhat = tienPhat;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }
    
    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Date getNgayTraNo() {
        return ngayTraNo;
    }

    public void setNgayTraNo(Date ngayTraNo) {
        this.ngayTraNo = ngayTraNo;
    }

    public String getTienConThieu() {
        return tienConThieu;
    }

    public void setTienConThieu(String tienConThieu) {
        this.tienConThieu = tienConThieu;
    }

    
}
