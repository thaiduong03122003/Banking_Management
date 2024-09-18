package quanlynganhang.BUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.DAO.TraKhoanVayDAO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.TraKhoanVayDTO;

public class TraKhoanVayBUS {

    private final TraKhoanVayDAO traVayDAO = new TraKhoanVayDAO();
    private TraKhoanVayDTO traVay;
    private FormatDate fDate = new FormatDate();

    public TraKhoanVayBUS() {
    }

    public List<TraKhoanVayDTO> getDSTraKhoanVay() {
        try {
            return traVayDAO.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TraKhoanVayDTO> getDSByMaVayVon(int maVayVon) {
        try {
            return traVayDAO.selectByMaVayVon(maVayVon);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TraKhoanVayDTO getTraKhoanVayById(int maKyTraNo) {
        try {
            return traVayDAO.selectById(maKyTraNo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object[][] doiSangObjectTraKhoanVay(int maVayVon) throws Exception {
        List<TraKhoanVayDTO> list = getDSByMaVayVon(maVayVon);

        if (list != null) {
            Object[][] data = new Object[list.size()][10];
            int rowIndex = 0;
            for (TraKhoanVayDTO traVay : list) {
                data[rowIndex][0] = traVay.getMaKyTraNo();
                data[rowIndex][1] = traVay.getKyTraNo();
                data[rowIndex][2] = traVay.getThoiGian() != null ? fDate.toString(traVay.getThoiGian()) : "Chưa có";
                data[rowIndex][3] = traVay.getSoTienDaTra();
                data[rowIndex][4] = traVay.getNgayTraNo() != null ? fDate.toString(traVay.getNgayTraNo()) : "Chưa có";
                data[rowIndex][5] = traVay.getTenTrangThai();

                rowIndex++;
            }
            return data;
        } else {
            return null;
        }
    }

    public int addTraKhoanNo(TraKhoanVayDTO traVay) {
        try {
            return traVayDAO.insert(traVay);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public boolean updateKhoanVayDaTra(int maKyHanVay, String soTienDaTra, String soTienConThieu, Date thoiGianTra, int maTrangThai) {
        try {
            return traVayDAO.updateKhoanVayDaTra(maKyHanVay, soTienDaTra, soTienConThieu, thoiGianTra, maTrangThai);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateTrangThai(int maKyTraNo, int maTrangThai) {
        try {
            return traVayDAO.updateTrangThai(maKyTraNo, maTrangThai);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateTienLai(int maKyTraNo, String tienLai) {
        try {
            return traVayDAO.updateTienLai(maKyTraNo, tienLai);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateTienPhat(int maKyTraNo, String tienPhat) {
        try {
            return traVayDAO.updateTienPhat(maKyTraNo, tienPhat);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean kiemTraKyVayTruoc(int maVayVon, int kyVay) {
        try {
            List<TraKhoanVayDTO> listTraVay = traVayDAO.selectDSKyVayTruocDo(maVayVon, kyVay);

            System.out.println("Ky vay: " + kyVay + ", ma vay von: " + maVayVon);
            
            if (listTraVay == null) {
                System.out.println("Danh sach null!");
                return true;
            }
            
            if (listTraVay.isEmpty()) {
                System.out.println("Danh sach trong!");
                return true;    
            }

            for (TraKhoanVayDTO traVay : listTraVay) {
                if (traVay.getMaTrangThai() != 9) {
                    return false;
                }
            }
            
            System.out.println("Khong tim thya trang thai!");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
