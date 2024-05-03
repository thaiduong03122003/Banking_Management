package quanlynganhang.BUS;

import quanlynganhang.DAO.KhoaTaiKhoanDAO;
import quanlynganhang.DTO.KhoaTaiKhoanDTO;

public class KhoaTaiKhoanBUS {
    private final KhoaTaiKhoanDAO khoaTaiKhoanDAO = new KhoaTaiKhoanDAO();
    
    public boolean addKhoaTaiKhoan(KhoaTaiKhoanDTO khoaTaiKhoan) {
        try {
            return khoaTaiKhoanDAO.insert(khoaTaiKhoan);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
