package quanlynganhang.BUS;

import quanlynganhang.GUI.Application;
import quanlynganhang.GUI.FormDSKhachHang;
import quanlynganhang.GUI.FormThongKe;
import quanlynganhang.GUI.FormTongQuan;
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
                Application.showForm(new FormTrangChu());
            } else if (index == 1) {
                Application.showForm(new FormTongQuan());
            } else if (index == 2) {
                Application.showForm(new FormDSKhachHang());
            } else if (index == 3) {
                if (subIndex == 1) {
                    //Application.showForm(new FormThongKe());
                } else if (subIndex == 2) {
                    //Application.showForm(new FormRead());
                } else {
                    action.cancel();
                }
            } else if (index == 9) {
                //Application.logout();
            } else {
                action.cancel();
            }
        });
    }

    public void setSelectedMenu(int index, int subIndex) {
        menu.setSelectedMenu(index, subIndex);
    }
}
