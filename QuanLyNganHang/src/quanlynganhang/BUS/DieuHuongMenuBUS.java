package quanlynganhang.BUS;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.Application;
import quanlynganhang.GUI.FormChoVayVon;
import quanlynganhang.GUI.FormChuyenCungNganHang;
import quanlynganhang.GUI.FormChuyenLienNganHang;
import quanlynganhang.GUI.FormDSGiaoDich;
import quanlynganhang.GUI.FormDSKhachHang;
import quanlynganhang.GUI.FormDSTaiKhoanKH;
import quanlynganhang.GUI.FormDSThe;
import quanlynganhang.GUI.FormMoTKTietKiem;
import quanlynganhang.GUI.FormMoTaiKhoan;
import quanlynganhang.GUI.FormMoTheGhiNo;
import quanlynganhang.GUI.FormMoTheTinDung;
import quanlynganhang.GUI.FormNapTien;
import quanlynganhang.GUI.FormRutTien;
import quanlynganhang.GUI.FormTongQuan;
import quanlynganhang.GUI.FormTraKhoanVay;
import quanlynganhang.GUI.FormTrangChu;
import quanlynganhang.GUI.adminUI.ApplicationAdmin;
import quanlynganhang.GUI.adminUI.FormDSNhanVien;
import quanlynganhang.GUI.adminUI.FormDSTaiKhoanNV;
import quanlynganhang.GUI.adminUI.FormPhanQuyen;
import quanlynganhang.GUI.adminUI.FormThemChucVu;
import quanlynganhang.GUI.adminUI.FormThongKe;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.menubar.MenuAction;
import quanlynganhang.GUI.model.message.MessageBox;

public class DieuHuongMenuBUS {

    private boolean isAdmin;
    private Menu menu;
    private ApplicationAdmin appAdmin;
    private TaiKhoanNVDTO taiKhoanNV;
    private Application app;
    private ChucVuBUS chucVuBUS;
    private NhanVienBUS nhanVienBUS;
    private ChucVuDTO chucVu;
   
    public DieuHuongMenuBUS(Menu menu, boolean isAdmin, Application app, ApplicationAdmin appAdmin, TaiKhoanNVDTO taiKhoanNV) {
        this.isAdmin = isAdmin;
        this.taiKhoanNV = taiKhoanNV;
        chucVuBUS = new ChucVuBUS();
        nhanVienBUS = new NhanVienBUS();
        if (isAdmin) {
            this.appAdmin = appAdmin;
        } else {
            this.app = app;
        }
        this.menu = menu;
        
    }
    

    private void showError() {
        MessageBox.showErrorMessage(null, "Bạn không có quyền để mở giao diện này!");
    }

    private int splitQuyen(String quyen) {
        String[] parts = quyen.split("-");
        return Integer.parseInt(parts[0]);
    }
    
    private int splitQuyen(String quyen, int index) {
        String[] parts = quyen.split("-");
        return Integer.parseInt(parts[index]);
    }

    public void initMenuEvent() {
        NhanVienDTO nhanVien = nhanVienBUS.getNhanVienById(taiKhoanNV.getMaNhanVien(), 0);

        chucVu = chucVuBUS.getChucVuById(nhanVien.getMaChucVu());

        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {

            if (isAdmin) {
                if (index == 0) {
                    appAdmin.showForm(new FormTrangChu(taiKhoanNV, chucVu), "Trang chủ");
                } else if (index == 1) {
                    if (chucVu.getqLThongKe() == 1) {
                        appAdmin.showForm(new FormThongKe(taiKhoanNV, chucVu), "Thống kê - Biến động");
                    } else {
                        showError();
                        return;
                    }

                } else if (index == 2) {
                    try {
                        if (splitQuyen(chucVu.getqLKhachHang()) == 1) {
                            appAdmin.showForm(new FormDSKhachHang(taiKhoanNV, chucVu), "Danh sách khách hàng");
                        } else {
                            showError();
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (index == 3) {
                    try {
                        if (splitQuyen(chucVu.getqLNhanVien()) == 1) {
                            appAdmin.showForm(new FormDSNhanVien(taiKhoanNV, chucVu), "Danh sách nhân viên");
                        } else {
                            showError();
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (index == 4) {
                    try {
                        if (splitQuyen(chucVu.getqLTKKhachHang()) == 1) {
                            appAdmin.showForm(new FormDSTaiKhoanKH(taiKhoanNV, chucVu), "Danh sách tài khoản khách hàng");
                        } else {
                            showError();
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (index == 5) {
                    try {
                        if (splitQuyen(chucVu.getqLTKNhanVien()) == 1) {
                            appAdmin.showForm(new FormDSTaiKhoanNV(taiKhoanNV, chucVu), "Danh sách tài khoản nhân viên");
                        } else {
                            showError();
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (index == 6) {
                    if (splitQuyen(chucVu.getqLThe()) == 1) {
                        appAdmin.showForm(new FormDSThe(taiKhoanNV, chucVu), "Danh sách thẻ");
                    } else {
                        showError();
                        return;
                    }
                } else if (index == 7) {
                    if (chucVu.getqLGiaoDich() == 1) {
                        appAdmin.showForm(new FormDSGiaoDich(taiKhoanNV, chucVu), "Danh sách giao dịch");
                    } else {
                        showError();
                        return;
                    }
                } else if (index == 8) {
                    if (chucVu.getPhanQuyen() == 1) {
                        appAdmin.showForm(new FormPhanQuyen(taiKhoanNV, chucVu), "Phân quyền");
                    } else {
                        showError();
                        return;
                    }
                } else if (index == 9) {
                    if (chucVu.getThemChucVu() == 1) {
                        appAdmin.showForm(new FormThemChucVu(taiKhoanNV, chucVu), "Thêm chức vụ");
                    } else {
                        showError();
                        return;
                    }
                } else {
                    action.cancel();
                }
            } else {
                if (index == 0) {
                    app.showForm(new FormTrangChu(taiKhoanNV, chucVu), "Trang chủ");
                } else if (index == 1) {
                    if (chucVu.getqLThongKe() == 1) {
                        app.showForm(new FormTongQuan(taiKhoanNV, chucVu), "Thống kê");
                    } else {
                        showError();
                        return;
                    }
                } else if (index == 2) {
                    try {
                        if (splitQuyen(chucVu.getqLKhachHang()) == 1) {
                            app.showForm(new FormDSKhachHang(taiKhoanNV, chucVu), "Danh sách khách hàng");
                        } else {
                            showError();
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (index == 3) {
                    try {
                        if (splitQuyen(chucVu.getqLTKKhachHang()) == 1) {
                            app.showForm(new FormDSTaiKhoanKH(taiKhoanNV, chucVu), "Danh sách tài khoản khách hàng");
                        } else {
                            showError();
                            return;
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (index == 4) {
                    if (splitQuyen(chucVu.getqLThe()) == 1) {
                        app.showForm(new FormDSThe(taiKhoanNV, chucVu), "Danh sách thẻ");
                    } else {
                        showError();
                        return;
                    }
                } else if (index == 5) {
                    if (chucVu.getqLGiaoDich() == 1) {
                        app.showForm(new FormDSGiaoDich(taiKhoanNV, chucVu), "Danh sách giao dịch");
                    } else {
                        showError();
                        return;
                    }
                } else if (index == 6) {
                    if (splitQuyen(chucVu.getqLKhachHang(), 2) == 1 || splitQuyen(chucVu.getqLTKKhachHang(), 2) == 1) {
                        app.showForm(new FormMoTaiKhoan(taiKhoanNV, chucVu), "Mở tài khoản khách hàng");
                    } else {
                        showError();
                        return;
                    }
                } else if (index == 7) {
                    if (subIndex == 1) {
                        if (splitQuyen(chucVu.getqLThe(), 2) == 1) {
                            app.showForm(new FormMoTheGhiNo(taiKhoanNV, chucVu), "Mở thẻ ghi nợ");
                        } else {
                            showError();
                            return;
                        }
                    } else if (subIndex == 2) {
                        if (splitQuyen(chucVu.getqLThe() , 2) == 1 && chucVu.getqLVayTinDung() == 1) {
                            app.showForm(new FormMoTheTinDung(taiKhoanNV, chucVu), "Mở thẻ tín dụng");
                        } else {
                            showError();
                            return;
                        }
                    } else {
                        action.cancel();
                    }
                } else if (index == 8) {
                    if (subIndex == 1) {
                        if (chucVu.getqLGiaoDich() == 1) {
                            app.showForm(new FormNapTien(taiKhoanNV, chucVu), "Nạp tiền vào tài khoản");
                        } else {
                            showError();
                            return;
                        }
                    } else if (subIndex == 2) {
                        if (chucVu.getqLGiaoDich() == 1) {
                            app.showForm(new FormRutTien(taiKhoanNV), "Rút tiền khỏi tài khoản");
                        } else {
                            showError();
                            return;
                        }
                    } else if (subIndex == 3) {
                        if (chucVu.getqLGiaoDich() == 1) {
                            app.showForm(new FormChuyenCungNganHang(taiKhoanNV, chucVu), "Chuyển tiền cùng ngân hàng");
                        } else {
                            showError();
                            return;
                        }
                    } else if (subIndex == 4) {
                        if (chucVu.getqLGiaoDich() == 1) {
                            app.showForm(new FormChuyenLienNganHang(taiKhoanNV, chucVu), "Chuyển tiền liên ngân hàng");
                        } else {
                            showError();
                            return;
                        }
                    } else {
                        action.cancel();
                    }
                } else if (index == 9) {
                    if (chucVu.getqLGuiTietKiem() == 1) {
                        app.showForm(new FormMoTKTietKiem(taiKhoanNV), "Mở tiết kiệm");
                    } else {
                        showError();
                        return;
                    }
                } else if (index == 10) {
                    if (subIndex == 1) {
                        if (chucVu.getqLVayVon() == 1) {
                            app.showForm(new FormChoVayVon(taiKhoanNV, chucVu), "Tạo khoản vay");
                        } else {
                            showError();
                            return;
                        }
                    } else if (subIndex == 2) {
                        if (chucVu.getqLVayVon() == 1) {
                            app.showForm(new FormTraKhoanVay(taiKhoanNV), "Trả khoản vay");
                        } else {
                            showError();
                            return;
                        }
                    } else {
                        action.cancel();
                    }
                } else {
                    action.cancel();
                }
            }

        });
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }
}
