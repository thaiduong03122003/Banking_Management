package quanlynganhang.BUS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import quanlynganhang.DAO.LoaiTaiKhoanDAO;
import quanlynganhang.DTO.LoaiTaiKhoanDTO;
import quanlynganhang.DTO.TrangThaiDTO;

public class LoaiTaiKhoanBUS {
    private final LoaiTaiKhoanDAO loaiTaiKhoanDAO = new LoaiTaiKhoanDAO();
    
    public LoaiTaiKhoanBUS() {
        
    }
    
    public List<LoaiTaiKhoanDTO> getDSLoaiTaiKhoan() {
        try {
            return loaiTaiKhoanDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map<Integer, String> convertListLoaiTaiKhoanToMap() {
        List<LoaiTaiKhoanDTO> list = getDSLoaiTaiKhoan();
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (LoaiTaiKhoanDTO loaiTaiKhoan : list) {
            map.put(loaiTaiKhoan.getMaLoaiTaiKhoan(), loaiTaiKhoan.getTenLoaiTaiKhoan());
        }

        return map;
    }
    
    public Integer getIdFromTenLoaiTaiKhoan(String tenLoaiTaiKhoan) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListLoaiTaiKhoanToMap();
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenLoaiTaiKhoan)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
