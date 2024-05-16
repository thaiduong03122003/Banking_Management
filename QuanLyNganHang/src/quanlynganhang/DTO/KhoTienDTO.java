package quanlynganhang.DTO;

public class KhoTienDTO {
    private int maKhoTien;
    private String tienTKNganHang, tienMat;
    public KhoTienDTO() {
    }

    public KhoTienDTO(int maKhoTien, String tienTKNganHang, String tienMat) {
        this.maKhoTien = maKhoTien;
        this.tienTKNganHang = tienTKNganHang;
        this.tienMat = tienMat;
    }

    public int getMaKhoTien() {
        return maKhoTien;
    }

    public void setMaKhoTien(int maKhoTien) {
        this.maKhoTien = maKhoTien;
    }

    public String getTienTKNganHang() {
        return tienTKNganHang;
    }

    public void setTienTKNganHang(String tienTKNganHang) {
        this.tienTKNganHang = tienTKNganHang;
    }

    public String getTienMat() {
        return tienMat;
    }

    public void setTienMat(String tienMat) {
        this.tienMat = tienMat;
    }

}
