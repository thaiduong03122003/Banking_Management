package quanlynganhang.BUS;

import java.util.List;
import quanlynganhang.DAO.KhoaTaiKhoanDAO;
import quanlynganhang.DTO.KhoaTaiKhoanDTO;

public class KhoaTaiKhoanBUS {

    private final KhoaTaiKhoanDAO khoaTaiKhoanDAO = new KhoaTaiKhoanDAO();

    public boolean addKhoaTaiKhoan(KhoaTaiKhoanDTO khoaTaiKhoan) {
        return khoaTaiKhoanDAO.insert(khoaTaiKhoan);
    }
    
    public List<KhoaTaiKhoanDTO> selectAllLocked() {
        return khoaTaiKhoanDAO.selectAllLocked();
    }
    
    public KhoaTaiKhoanDTO selectByIdTK(int maTaiKhoan, String loaiTaiKhoan) {
        return khoaTaiKhoanDAO.selectLockedByIdTK(maTaiKhoan, loaiTaiKhoan);
    }
    
    public boolean unlock(int maKhoaTK) {
        return khoaTaiKhoanDAO.unlock(maKhoaTK);
    }
}
