package quanlynganhang.BUS;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DAO.ChucVuDAO;
import quanlynganhang.DAO.NhanVienDAO;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.NhanVienDTO;

public class NhanVienBUS {

    private final ChucVuDAO chucVuDAO = new ChucVuDAO();
    private final NhanVienDAO nhanVienDAO = new NhanVienDAO();
    private NhanVienDTO nhanVien;

    public NhanVienBUS() {
    }

    public NhanVienBUS(NhanVienDTO nhanVien) {
        this.nhanVien = nhanVien;
    }

    private List<NhanVienDTO> getDSNhanVien(int biXoa) {
        try {
            return nhanVienDAO.selectAll(biXoa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[][] doiSangObjectNhanVien(int biXoa) {
        List<NhanVienDTO> list = getDSNhanVien(biXoa);

        Object[][] data = new Object[list.size()][10];
        int rowIndex = 0;
        for (NhanVienDTO nhanVien : list) {
            data[rowIndex][0] = nhanVien.getMaNV();
            data[rowIndex][1] = nhanVien.getHoDem();
            data[rowIndex][2] = nhanVien.getTen();
            data[rowIndex][3] = nhanVien.getGioiTinh();
            data[rowIndex][4] = nhanVien.getNgaySinh();
            data[rowIndex][5] = nhanVien.getMaPhuongXa();
            data[rowIndex][6] = nhanVien.getEmail();
            data[rowIndex][7] = nhanVien.getSdt();
            data[rowIndex][8] = nhanVien.getCccd();
            data[rowIndex][9] = nhanVien.getTenChucVu();
            
            rowIndex++;
        }
        return data;
    }
    
    public NhanVienDTO addNhanVien(NhanVienDTO nhanVien, int biXoa) {
        try {
            return nhanVienDAO.insert(nhanVien, biXoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public boolean updateNhanVien(NhanVienDTO nhanVien, int biXoa) {
        try {
            return nhanVienDAO.update(nhanVien, biXoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteNhanVien(int maNhanVien) {
        try {
            return nhanVienDAO.delete(maNhanVien);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public NhanVienDTO getNhanVienById(int maNhanVien, int biXoa) {
        try {
            return nhanVienDAO.selectById(maNhanVien, biXoa);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
