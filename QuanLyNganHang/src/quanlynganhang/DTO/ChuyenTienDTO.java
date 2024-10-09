package quanlynganhang.DTO;

public class ChuyenTienDTO {
    private int maChuyenTien, maTKNguoiNhan, maNganHang, maGiaoDich, maKhachHang, biXoa, biDong;
    private String soTaiKhoanNhan, tenTaiKhoanNhan, tenKhachHangNhan, email, sdt, tenNganHang;

    public ChuyenTienDTO() {
    }

    public ChuyenTienDTO(int maChuyenTien, int maTKNguoiNhan, int maNganHang, int maGiaoDich) {
        this.maChuyenTien = maChuyenTien;
        this.maTKNguoiNhan = maTKNguoiNhan;
        this.maNganHang = maNganHang;
        this.maGiaoDich = maGiaoDich;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }
    
    public int getBiDong() {
        return biDong;
    }

    public void setBiDong(int biDong) {
        this.biDong = biDong;
    }
    
    public int getBiXoa() {
        return biXoa;
    }

    public void setBiXoa(int biXoa) {
        this.biXoa = biXoa;
    }
    
    public String getSoTaiKhoanNhan() {
        return soTaiKhoanNhan;
    }

    public void setSoTaiKhoanNhan(String soTaiKhoanNhan) {
        this.soTaiKhoanNhan = soTaiKhoanNhan;
    }
    
    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenTaiKhoanNhan() {
        return tenTaiKhoanNhan;
    }

    public void setTenTaiKhoanNhan(String tenTaiKhoanNhan) {
        this.tenTaiKhoanNhan = tenTaiKhoanNhan;
    }

    public String getTenKhachHangNhan() {
        return tenKhachHangNhan;
    }

    public void setTenKhachHangNhan(String tenKhachHangNhan) {
        this.tenKhachHangNhan = tenKhachHangNhan;
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
