package quanlynganhang.DTO;

public class LoaiTaiKhoanDTO {
    private int maLoaiTaiKhoan;
    private String tenLoaiTaiKhoan;

    public LoaiTaiKhoanDTO() {
    }

    public LoaiTaiKhoanDTO(int maLoaiTaiKhoan, String tenLoaiTaiKhoan) {
        this.maLoaiTaiKhoan = maLoaiTaiKhoan;
        this.tenLoaiTaiKhoan = tenLoaiTaiKhoan;
    }

    public int getMaLoaiTaiKhoan() {
        return maLoaiTaiKhoan;
    }

    public void setMaLoaiTaiKhoan(int maLoaiTaiKhoan) {
        this.maLoaiTaiKhoan = maLoaiTaiKhoan;
    }

    public String getTenLoaiTaiKhoan() {
        return tenLoaiTaiKhoan;
    }

    public void setTenLoaiTaiKhoan(String tenLoaiTaiKhoan) {
        this.tenLoaiTaiKhoan = tenLoaiTaiKhoan;
    }
    
    
}
