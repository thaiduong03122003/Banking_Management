package quanlynganhang.BUS;

import quanlynganhang.GUI.Application;
import quanlynganhang.GUI.FormChoVayVon;
import quanlynganhang.GUI.FormChuyenCungNganHang;
import quanlynganhang.GUI.FormChuyenLienNganHang;
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
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.menubar.MenuAction;

public class DieuHuongMenuBUS {

    private Menu menu;

    public DieuHuongMenuBUS(Menu menu) {
        this.menu = menu;
    }

    public void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
            //Application.mainForm.showForm(new DefaultForm("Form : " + index + " " + subIndex));
            if (index == 0) {
                Application.showForm(new FormTrangChu(), "Trang chủ");
            } else if (index == 1) {
                Application.showForm(new FormTongQuan(), "Thống kê");
            } else if (index == 2) {
                Application.showForm(new FormDSKhachHang(), "Danh sách khách hàng");
            } else if (index == 3) {
                Application.showForm(new FormDSTaiKhoanKH(), "Danh sách tài khoản khách");
            } else if (index == 4) {
                Application.showForm(new FormDSThe(), "Danh sách thẻ");
            } else if (index == 6) {
                Application.showForm(new FormMoTaiKhoan(), "Mở tài khoản khách");
            } else if (index == 7) {
                if (subIndex == 1) {
                    Application.showForm(new FormMoTheGhiNo(), "Mở thẻ ghi nợ");
                } else if (subIndex == 2) {
                    Application.showForm(new FormMoTheTinDung(), "Mở thẻ tín dụng");
                } else {
                    action.cancel();
                }
            } else if (index == 8) {
                if (subIndex == 1) {
                    Application.showForm(new FormNapTien(), "Nạp tiền vào tài khoản");
                } else if (subIndex == 2) {
                    Application.showForm(new FormRutTien(), "Rút tiền khỏi tài khoản");
                } else if (subIndex == 3) {
                    Application.showForm(new FormChuyenCungNganHang(), "Chuyển tiền cùng ngân hàng");
                } else if (subIndex == 4) {
                    Application.showForm(new FormChuyenLienNganHang(), "Chuyển tiền liên ngân hàng");
                } else {
                    action.cancel();
                }
            } else if (index == 9) {
                Application.showForm(new FormMoTKTietKiem(), "Mở tiết kiệm");
            } else if (index == 10) {
                if (subIndex == 1) {
                    Application.showForm(new FormChoVayVon(), "Tạo khoản vay");
                } else if (subIndex == 2) {
                    Application.showForm(new FormTraKhoanVay(), "Trả khoản vay");
                } else {
                    action.cancel();
                }
            } else {
                action.cancel();
            }

        });
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }
}
