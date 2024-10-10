package quanlynganhang.GUI.adminUI;

import java.awt.Component;
import quanlynganhang.BUS.DieuHuongMenuBUS;
import quanlynganhang.BUS.KiemTraDuLieuBUS;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.MainForm;
import quanlynganhang.GUI.model.menubar.Menu;

public class ApplicationAdmin extends javax.swing.JFrame {

    private final MainForm mainForm;
    private DieuHuongMenuBUS menuBUS;
    private KiemTraDuLieuBUS kiemTraDuLieuBUS;
    private Menu menu;
    private boolean isAdmin = true;
    public static Menu instanceMenu;

    public ApplicationAdmin(TaiKhoanNVDTO taiKhoanNV, int maDangNhap) {
        kiemTraDuLieuBUS = new KiemTraDuLieuBUS(taiKhoanNV);
        initComponents();
        setLocationRelativeTo(null);
        menu = new Menu(isAdmin);
        instanceMenu = menu;
        menuBUS = new DieuHuongMenuBUS(menu, isAdmin, null, this, taiKhoanNV);
        mainForm = new MainForm(menu, menuBUS, taiKhoanNV, null, this, maDangNhap, isAdmin);
        setContentPane(mainForm);
        
        kiemTraDuLieuBUS.chayKiemTraKhoaTaiKhoan();
        kiemTraDuLieuBUS.chayKiemTraTinhTrangGTK();
        kiemTraDuLieuBUS.chayKiemTraTinhTrangVayVon();
    }

    public void showForm(Component component, String titleName) {
        component.applyComponentOrientation(this.getComponentOrientation());
        mainForm.showForm(component, titleName);
    }

    public void setSelectedMenu(int index, int subIndex) {
        menuBUS.setSelectedMenu(index, subIndex);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1220, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
