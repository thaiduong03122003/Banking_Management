package quanlynganhang.BUS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import quanlynganhang.DAO.TrangThaiDAO;
import quanlynganhang.DTO.TrangThaiDTO;

public class TrangThaiBUS {
    private final TrangThaiDAO trangThaiDAO = new TrangThaiDAO();
    
    public TrangThaiBUS() {
    }
    
    public List<TrangThaiDTO> getDSTrangThaiByDanhMuc(String danhMuc) {
        try {
            return trangThaiDAO.selectAll(danhMuc);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map<Integer, String> convertListTrangThaiToMap(String danhMuc) {
        List<TrangThaiDTO> list = getDSTrangThaiByDanhMuc(danhMuc);
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (TrangThaiDTO diaChi : list) {
            map.put(diaChi.getMaTrangThai(), diaChi.getTenTrangThai());
        }

        return map;
    }
    
    public Integer getIdFromTenTrangThai(String tenTrangThai, String danhMuc) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListTrangThaiToMap(danhMuc);
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenTrangThai)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
