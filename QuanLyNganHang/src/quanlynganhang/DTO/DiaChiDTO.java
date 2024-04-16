/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.DTO;


public class DiaChiDTO {
    private int maPhuongXa, maQuanHuyen, maTinhThanh;
    private String tenPhuongXa, tenQuanHuyen, tenTinhThanh;

    public DiaChiDTO() {
    }

    public DiaChiDTO(int maPhuongXa, int maQuanHuyen, int maTinhThanh, String tenPhuongXa, String tenQuanHuyen, String tenTinhThanh) {
        this.maPhuongXa = maPhuongXa;
        this.maQuanHuyen = maQuanHuyen;
        this.maTinhThanh = maTinhThanh;
        this.tenPhuongXa = tenPhuongXa;
        this.tenQuanHuyen = tenQuanHuyen;
        this.tenTinhThanh = tenTinhThanh;
    }

    public int getMaPhuongXa() {
        return maPhuongXa;
    }

    public void setMaPhuongXa(int maPhuongXa) {
        this.maPhuongXa = maPhuongXa;
    }

    public int getMaQuanHuyen() {
        return maQuanHuyen;
    }

    public void setMaQuanHuyen(int maQuanHuyen) {
        this.maQuanHuyen = maQuanHuyen;
    }

    public int getMaTinhThanh() {
        return maTinhThanh;
    }

    public void setMaTinhThanh(int maTinhThanh) {
        this.maTinhThanh = maTinhThanh;
    }

    public String getTenPhuongXa() {
        return tenPhuongXa;
    }

    public void setTenPhuongXa(String tenPhuongXa) {
        this.tenPhuongXa = tenPhuongXa;
    }

    public String getTenQuanHuyen() {
        return tenQuanHuyen;
    }

    public void setTenQuanHuyen(String tenQuanHuyen) {
        this.tenQuanHuyen = tenQuanHuyen;
    }

    public String getTenTinhThanh() {
        return tenTinhThanh;
    }

    public void setTenTinhThanh(String tenTinhThanh) {
        this.tenTinhThanh = tenTinhThanh;
    }
    
    
}
