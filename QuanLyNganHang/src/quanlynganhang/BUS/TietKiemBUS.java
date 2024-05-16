package quanlynganhang.BUS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DAO.KyHanGuiTKDAO;
import quanlynganhang.DAO.TietKiemDAO;
import quanlynganhang.DTO.KyHanGuiTKDTO;
import quanlynganhang.DTO.NganHangDTO;
import quanlynganhang.DTO.TietKiemDTO;

public class TietKiemBUS {
    private final KyHanGuiTKDAO kyHanGuiTKDAO = new KyHanGuiTKDAO();
    private final TietKiemDAO tietKiemDAO = new TietKiemDAO();
    
    public List<KyHanGuiTKDTO> getDSKyHan() {
        try {
            return kyHanGuiTKDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map<Integer, String> convertListKyHanToMap() {
        List<KyHanGuiTKDTO> list = getDSKyHan();
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (KyHanGuiTKDTO kyHan : list) {
            map.put(kyHan.getMaKyHan(), kyHan.getSoKyHan()+ " tháng (" + kyHan.getLaiSuat() + "%/năm)");
        }

        return map;
    }
    
    public Integer getIdFromTenKyHan(String tenKyHan) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListKyHanToMap();
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenKyHan)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public int addGuiTietKiem(TietKiemDTO tietKiem) {
        try {
            return tietKiemDAO.insert(tietKiem);
        } catch (Exception ex) {
            return 0;
        }
    }
    
    public KyHanGuiTKDTO getKyHanById(int maKyHan) {
        try {
            return kyHanGuiTKDAO.selectKyHanById(maKyHan);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public TietKiemDTO getTietKiemByMaTKKH(int maTaiKhoanKH) {
        try {
            return tietKiemDAO.selectByIdTKKH(maTaiKhoanKH);
        } catch (Exception ex) {
            return null;
        }
    }
    
    public boolean updateSoTienGoc(int maGuiTK, String soTienGoc) {
        try {
            return tietKiemDAO.updateSoTienGoc(maGuiTK, soTienGoc);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean updateTrangThai(int maGuiTK) {
        try {
            return tietKiemDAO.updateTrangThai(maGuiTK, 11);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
