package quanlynganhang.DTO;

public class LoaiTheATMDTO {
    private int maLoaiThe;
    private String tenLoaiThe, moTa;

    public LoaiTheATMDTO() {
    }

    public LoaiTheATMDTO(int maLoaiThe, String tenLoaiThe, String moTa) {
        this.maLoaiThe = maLoaiThe;
        this.tenLoaiThe = tenLoaiThe;
        this.moTa = moTa;
    }

    public int getMaLoaiThe() {
        return maLoaiThe;
    }

    public void setMaLoaiThe(int maLoaiThe) {
        this.maLoaiThe = maLoaiThe;
    }

    public String getTenLoaiThe() {
        return tenLoaiThe;
    }

    public void setTenLoaiThe(String tenLoaiThe) {
        this.tenLoaiThe = tenLoaiThe;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    
    
}
