package quanlynganhang.BUS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import quanlynganhang.DAO.DiaChiDAO;
import quanlynganhang.DTO.DiaChiDTO;

public class DiaChiBUS {

    private final DiaChiDAO diaChiDAO = new DiaChiDAO();

    private List<DiaChiDTO> getDSTinhThanh() {
        try {
            return diaChiDAO.selectAllTinhThanh();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<DiaChiDTO> getDSQuanHuyen(int maTinhThanh) {
        try {
            return diaChiDAO.selectAllQuanHuyenByIdTinhThanh(maTinhThanh);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<DiaChiDTO> getDSPhuongXa(int maQuanHuyen) {
        try {
            return diaChiDAO.selectAllPhuongXaByIdQuanHuyen(maQuanHuyen);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<Integer, String> convertListTinhThanhToMap() {
        List<DiaChiDTO> list = getDSTinhThanh();
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (DiaChiDTO diaChi : list) {
            map.put(diaChi.getMaTinhThanh(), diaChi.getTenTinhThanh());
        }

        return map;
    }

    public Map<Integer, String> convertListQuanHuyenToMap(int maTinhThanh) {
        List<DiaChiDTO> list = getDSQuanHuyen(maTinhThanh);
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (DiaChiDTO diaChi : list) {
            map.put(diaChi.getMaQuanHuyen(), diaChi.getTenQuanHuyen());
        }

        return map;
    }

    public Map<Integer, String> convertListPhuongXaToMap(int maQuanHuyen) {
        List<DiaChiDTO> list = getDSPhuongXa(maQuanHuyen);
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (DiaChiDTO diaChi : list) {
            map.put(diaChi.getMaPhuongXa(), diaChi.getTenPhuongXa());
        }

        return map;
    }

    public Integer getIdFromTenTinhThanh(String tenTinhThanh) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListTinhThanhToMap();
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenTinhThanh)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public Integer getIdFromTenQuanHuyen(String tenQuanHuyen, int maTinhThanh) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListQuanHuyenToMap(maTinhThanh);
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenQuanHuyen)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public Integer getIdFromTenPhuongXa(String tenPhuongXa, int maQuanHuyen) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListPhuongXaToMap(maQuanHuyen);
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenPhuongXa)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
