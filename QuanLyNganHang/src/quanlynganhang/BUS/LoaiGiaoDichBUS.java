package quanlynganhang.BUS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import quanlynganhang.DAO.LoaiGiaoDichDAO;
import quanlynganhang.DTO.LoaiGiaoDichDTO;

public class LoaiGiaoDichBUS {
    private final LoaiGiaoDichDAO loaiGiaoDichDAO = new LoaiGiaoDichDAO();
    
    public LoaiGiaoDichBUS() {
    }
    
    public List<LoaiGiaoDichDTO> getDSLoaiGD() {
        try {
            return loaiGiaoDichDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map<Integer, String> convertListLoaiGDToMap() {
        List<LoaiGiaoDichDTO> list = getDSLoaiGD();
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (LoaiGiaoDichDTO loaiGD : list) {
            map.put(loaiGD.getMaLoaiGiaoDich(), loaiGD.getTenLoaiGiaoDich());
        }

        return map;
    }

    public Integer getIdFromTenLoaiGD(String tenLoaiGD) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListLoaiGDToMap();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenLoaiGD)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
