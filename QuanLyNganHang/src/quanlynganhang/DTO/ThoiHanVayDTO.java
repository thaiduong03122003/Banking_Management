package quanlynganhang.DTO;

public class ThoiHanVayDTO {

    private int maThoiHan, soThoiHan, loaiHinhVay;
    private double laiSuat;

    public ThoiHanVayDTO() {
    }

    public ThoiHanVayDTO(int maThoiHan, int soThoiHan, int loaiHinhVay, double laiSuat) {
        this.maThoiHan = maThoiHan;
        this.soThoiHan = soThoiHan;
        this.loaiHinhVay = loaiHinhVay;
        this.laiSuat = laiSuat;
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

    public int getLoaiHinhVay() {
        return loaiHinhVay;
    }

    public void setLoaiHinhVay(int loaiHinhVay) {
        this.loaiHinhVay = loaiHinhVay;
    }

    public double getLaiSuat() {
        return laiSuat;
    }

    public void setLaiSuat(double laiSuat) {
        this.laiSuat = laiSuat;
    }
    
    
}
