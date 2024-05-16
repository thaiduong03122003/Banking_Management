package quanlynganhang.DTO;


public class NganHangDTO {
    private int maNganHang;
    private String tenVietTat, tenDayDu;

    public NganHangDTO() {
    }

    public NganHangDTO(int maNganHang, String tenVietTat, String tenDayDu) {
        this.maNganHang = maNganHang;
        this.tenVietTat = tenVietTat;
        this.tenDayDu = tenDayDu;
    }

    public int getMaNganHang() {
        return maNganHang;
    }

    public void setMaNganHang(int maNganHang) {
        this.maNganHang = maNganHang;
    }

    public String getTenVietTat() {
        return tenVietTat;
    }

    public void setTenVietTat(String tenVietTat) {
        this.tenVietTat = tenVietTat;
    }

    public String getTenDayDu() {
        return tenDayDu;
    }

    public void setTenDayDu(String tenDayDu) {
        this.tenDayDu = tenDayDu;
    }

    
}
