package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import quanlynganhang.BUS.DieuHuongMenuBUS;
import quanlynganhang.BUS.KiemTraDuLieuBUS;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.MainForm;
import quanlynganhang.GUI.model.glasspanepopup.GlassPanePopup;
import quanlynganhang.GUI.model.menubar.Menu;

public class Application extends javax.swing.JFrame {

    private final MainForm mainForm;
    private DieuHuongMenuBUS menuBUS;
    private KiemTraDuLieuBUS kiemTraDuLieuBUS;
    private TaiKhoanNVDTO taiKhoanNV;
    private Menu menu;
    private boolean isAdmin = false;
    public static  Menu instanceMenu;
    public Application(TaiKhoanNVDTO taiKhoanNV) {
       
        kiemTraDuLieuBUS = new KiemTraDuLieuBUS(taiKhoanNV);
        this.taiKhoanNV = taiKhoanNV;
        initComponents();
        setLocationRelativeTo(null);
        menu = new Menu(isAdmin);
         instanceMenu = menu;
        menuBUS = new DieuHuongMenuBUS(menu, isAdmin, this, null, taiKhoanNV);
        mainForm = new MainForm(menu, menuBUS, taiKhoanNV, this, null);
        setContentPane(mainForm);
        
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
        setPreferredSize(new java.awt.Dimension(1220, 650));

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

        System.out.println("Nothing!");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
