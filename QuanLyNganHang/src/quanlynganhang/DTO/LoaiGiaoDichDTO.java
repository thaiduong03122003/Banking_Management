package quanlynganhang.DTO;

public class LoaiGiaoDichDTO {
    private int maLoaiGiaoDich;
    private String tenLoaiGiaoDich;

    public LoaiGiaoDichDTO() {
    }

    public LoaiGiaoDichDTO(int maLoaiGiaoDich, String tenLoaiGiaoDich) {
        this.maLoaiGiaoDich = maLoaiGiaoDich;
        this.tenLoaiGiaoDich = tenLoaiGiaoDich;
    }

    public int getMaLoaiGiaoDich() {
        return maLoaiGiaoDich;
    }

    public void setMaLoaiGiaoDich(int maLoaiGiaoDich) {
        this.maLoaiGiaoDich = maLoaiGiaoDich;
    }

    public String getTenLoaiGiaoDich() {
        return tenLoaiGiaoDich;
    }

    public void setTenLoaiGiaoDich(String tenLoaiGiaoDich) {
        this.tenLoaiGiaoDich = tenLoaiGiaoDich;
    }
    
    
}
