package quanlynganhang.BUS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import quanlynganhang.DAO.NganHangDAO;
import quanlynganhang.DTO.NganHangDTO;
import quanlynganhang.DTO.TrangThaiDTO;

public class NganHangBUS {
    private final NganHangDAO nganHangDAO = new NganHangDAO();
    
    public NganHangBUS() {
    }

    public List<NganHangDTO> getDSNganHang() {
        try {
            return nganHangDAO.select();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<Integer, String> convertListNganHangToMap() {
        List<NganHangDTO> list = getDSNganHang();
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (NganHangDTO nganHang : list) {
            map.put(nganHang.getMaNganHang(), nganHang.getTenVietTat() + " - " + nganHang.getTenDayDu());
        }

        return map;
    }

    public Integer getIdFromTenNganHang(String tenNganHang) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListNganHangToMap();

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenNganHang)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
