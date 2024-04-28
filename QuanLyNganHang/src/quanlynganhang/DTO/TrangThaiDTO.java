package quanlynganhang.DTO;

public class TrangThaiDTO {
    private int maTrangThai;
    private String tenTrangThai, danhMuc;

    public TrangThaiDTO() {
    }

    public TrangThaiDTO(int maTrangThai, String tenTrangThai, String danhMuc) {
        this.maTrangThai = maTrangThai;
        this.tenTrangThai = tenTrangThai;
        this.danhMuc = danhMuc;
    }

    public int getMaTrangThai() {
        return maTrangThai;
    }

    public void setMaTrangThai(int maTrangThai) {
        this.maTrangThai = maTrangThai;
    }

    public String getTenTrangThai() {
        return tenTrangThai;
    }

    public void setTenTrangThai(String tenTrangThai) {
        this.tenTrangThai = tenTrangThai;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }
    
    
}
