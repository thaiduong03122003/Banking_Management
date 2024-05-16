package quanlynganhang.BUS;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import quanlynganhang.DAO.ThoiHanVayDAO;
import quanlynganhang.DAO.VayVonDAO;
import quanlynganhang.DTO.KyHanGuiTKDTO;
import quanlynganhang.DTO.ThoiHanVayDTO;
import quanlynganhang.DTO.VayVonDTO;

public class VayVonBUS {
    private final VayVonDAO vayVonDAO = new VayVonDAO();
    private final ThoiHanVayDAO thoiHanDAO = new ThoiHanVayDAO();
    
    public List<ThoiHanVayDTO> getDSKyHan(int loaiHinhVay) {
        try {
            return thoiHanDAO.selectAll(loaiHinhVay);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Map<Integer, String> convertListThoiHanToMap(int loaiHinhVay) {
        List<ThoiHanVayDTO> list = getDSKyHan(loaiHinhVay);
        if (list == null) {
            return null;
        }

        Map<Integer, String> map = new HashMap<>();
        for (ThoiHanVayDTO thoiHan : list) {
            map.put(thoiHan.getMaThoiHan(), thoiHan.getSoThoiHan()+ " tháng (" + thoiHan.getLaiSuat() + "%/năm)");
        }

        return map;
    }
    
    public Integer getIdFromTenThoiHan(String tenThoiHan, int loaiHinhVay) {
        Map<Integer, String> map = new HashMap<>();
        map = convertListThoiHanToMap(loaiHinhVay);
        
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(tenThoiHan)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    public boolean kiemTraDieuKienVay(String soTienThuNhap, String soTienMuonVay, int maThoiHan) {
        ThoiHanVayDTO thoiHanVay = new ThoiHanVayDTO();
        try {
            thoiHanVay = thoiHanDAO.selectThoiHanById(maThoiHan);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        BigInteger soTienNo = new BigInteger(soTienNo(soTienMuonVay, thoiHanVay));
        BigInteger thuNhap = new BigInteger(soTienThuNhap);
        BigInteger tiLe = new BigInteger("100");
        
        System.out.println("Tong so tien phai tra moi thang: " + soTienNo.toString());
        
        soTienNo = soTienNo.multiply(tiLe);
        
        soTienNo = soTienNo.divide(thuNhap);
        
        System.out.println("Thu nhap: " + thuNhap.toString());
        System.out.println("Ti le khong the vay: " + soTienNo + "%");
        return Integer.parseInt(soTienNo.toString()) <= 70;
    }
    
    public String soTienNo(String soTienVay, ThoiHanVayDTO thoiHanVay) {
        BigInteger laiSuat = new BigInteger(String.valueOf((int) (thoiHanVay.getLaiSuat() * 10)));
        BigInteger lai = new BigInteger("1000");
        BigInteger soThangVay = new BigInteger(String.valueOf(thoiHanVay.getSoThoiHan()));
        BigInteger nam = new BigInteger("12");
        BigInteger tienVay = new BigInteger(soTienVay);
        
        BigInteger tienGocHangThang = tienVay.divide(soThangVay);
        
        BigInteger tienLaiHangThang = tienVay.multiply(laiSuat);
        
        tienLaiHangThang = tienLaiHangThang.divide(lai);
        tienLaiHangThang = tienLaiHangThang.divide(nam);
        
        System.out.println("Tien goc moi thang: " + tienGocHangThang.toString());
        System.out.println("Tien lai moi thang: " + tienLaiHangThang.toString());
        return (tienGocHangThang.add(tienLaiHangThang)).toString();
    }
    
    public ThoiHanVayDTO getThoiHanById(int maThoiHan) {
        try {
            return thoiHanDAO.selectThoiHanById(maThoiHan);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public int addVayVon(VayVonDTO vayVon) {
        try {
            return vayVonDAO.insert(vayVon);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
