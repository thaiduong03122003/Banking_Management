package quanlynganhang.DTO;

public class KyHanGuiTKDTO {
    private int maKyHan, soKyHan;
    private double laiSuat;

    public KyHanGuiTKDTO() {
    }

    public KyHanGuiTKDTO(int maKyHan, int soKyHan, double laiSuat) {
        this.maKyHan = maKyHan;
        this.soKyHan = soKyHan;
        this.laiSuat = laiSuat;
    }

    public int getMaKyHan() {
        return maKyHan;
    }

    public void setMaKyHan(int maKyHan) {
        this.maKyHan = maKyHan;
    }

    public int getSoKyHan() {
        return soKyHan;
    }

    public void setSoKyHan(int soKyHan) {
        this.soKyHan = soKyHan;
    }

    public double getLaiSuat() {
        return laiSuat;
    }

    public void setLaiSuat(double laiSuat) {
        this.laiSuat = laiSuat;
    }

    
}
