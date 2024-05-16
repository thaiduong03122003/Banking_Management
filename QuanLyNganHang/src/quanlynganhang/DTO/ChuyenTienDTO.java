package quanlynganhang.DTO;

public class ChuyenTienDTO {
    private int maChuyenTien, maTKNguoiNhan, maNganHang, maGiaoDich;

    public ChuyenTienDTO() {
    }

    public ChuyenTienDTO(int maChuyenTien, int maTKNguoiNhan, int maNganHang, int maGiaoDich) {
        this.maChuyenTien = maChuyenTien;
        this.maTKNguoiNhan = maTKNguoiNhan;
        this.maNganHang = maNganHang;
        this.maGiaoDich = maGiaoDich;
    }

    public int getMaChuyenTien() {
        return maChuyenTien;
    }

    public void setMaChuyenTien(int maChuyenTien) {
        this.maChuyenTien = maChuyenTien;
    }

    public int getMaTKNguoiNhan() {
        return maTKNguoiNhan;
    }

    public void setMaTKNguoiNhan(int maTKNguoiNhan) {
        this.maTKNguoiNhan = maTKNguoiNhan;
    }

    public int getMaNganHang() {
        return maNganHang;
    }

    public void setMaNganHang(int maNganHang) {
        this.maNganHang = maNganHang;
    }

    public int getMaGiaoDich() {
        return maGiaoDich;
    }

    public void setMaGiaoDich(int maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }
    
    
}
