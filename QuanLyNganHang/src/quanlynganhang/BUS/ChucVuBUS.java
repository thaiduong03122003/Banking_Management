package quanlynganhang.BUS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DAO.ChucVuDAO;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.NhanVienDTO;

public class ChucVuBUS {

    private final ChucVuDAO chucVuDAO = new ChucVuDAO();
    private NhanVienDTO nhanVien;

    public ChucVuBUS() {
    }

    public ChucVuBUS(NhanVienDTO nhanVien) {
        this.nhanVien = nhanVien;
    }

    private List<ChucVuDTO> getDSChucVu(int biXoa) {
        try {
            return chucVuDAO.selectAll(biXoa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[][] doiSangObjectChucVu(int biXoa) {
        List<ChucVuDTO> list = getDSChucVu(biXoa);

        Object[][] data = new Object[list.size()][14];
        int rowIndex = 0;
        for (ChucVuDTO chucVu : list) {
            data[rowIndex][0] = chucVu.getMaChucVu();
            data[rowIndex][1] = chucVu.getTenChucVu();
            data[rowIndex][2] = chucVu.getMoTa();
            data[rowIndex][3] = chucVu.getIsAdmin();
            data[rowIndex][4] = chucVu.getqLThongKe();
            data[rowIndex][5] = chucVu.getqLKhachHang();
            data[rowIndex][6] = chucVu.getqLNhanVien();
            data[rowIndex][7] = chucVu.getqLTKKhachHang();
            data[rowIndex][8] = chucVu.getqLTKNhanVien();
            data[rowIndex][9] = chucVu.getqLThe();
            data[rowIndex][10] = chucVu.getqLGiaoDich();
            data[rowIndex][11] = chucVu.getqLGuiTietKiem();
            data[rowIndex][12] = chucVu.getqLVayVon();
            data[rowIndex][13] = chucVu.getqLVayTinDung();
            rowIndex++;
        }
        return data;
    }
    
    public ChucVuDTO addChucVu(ChucVuDTO chucVu, int biXoa) {
        try {
            return chucVuDAO.insert(chucVu, biXoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public boolean updateChucVu(ChucVuDTO chucVu, int biXoa) {
        try {
            return chucVuDAO.update(chucVu, biXoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteChucVu(int maChucVu) {
        try {
            return chucVuDAO.delete(maChucVu);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public ChucVuDTO getChucVuById(int maChucVu) {
        try {
            return chucVuDAO.selectById(maChucVu, 0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Map<Integer, String> convertListChucVuToMap() {
        List<ChucVuDTO> list = getDSChucVu(0);
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (ChucVuDTO chucVu : list) {
            map.put(chucVu.getMaChucVu(), chucVu.getTenChucVu());
        }

        return map;
    }
    
    public Integer getIdFromTenChucVu(String tenChucVu) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListChucVuToMap();
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenChucVu)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private List<ChucVuDTO> getSoLuongChucVu() {
        try {
            return chucVuDAO.getNORole();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Object[][] doiSangObjectSoLuongChucVu() {
        List<ChucVuDTO> list = getSoLuongChucVu();

        Object[][] data = new Object[list.size()][3];
        int rowIndex = 0;
        for (ChucVuDTO chucVu : list) {
            data[rowIndex][0] = chucVu.getMaChucVu();
            data[rowIndex][1] = chucVu.getTenChucVu();
            data[rowIndex][2] = chucVu.getSoChucVu();
 
            rowIndex++;
        }
        return data;
    }
}
