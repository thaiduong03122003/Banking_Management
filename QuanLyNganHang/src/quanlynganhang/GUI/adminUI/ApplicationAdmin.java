package quanlynganhang.GUI.adminUI;

import quanlynganhang.GUI.*;
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
import quanlynganhang.GUI.MainForm;
import quanlynganhang.GUI.model.glasspanepopup.GlassPanePopup;
import quanlynganhang.GUI.model.menubar.Menu;

public class ApplicationAdmin extends javax.swing.JFrame {

    private static ApplicationAdmin app;
    private final MainForm mainForm;
    private DieuHuongMenuBUS menuBUS;
    private Menu menu;
    private boolean isAdmin = true;

    public ApplicationAdmin() {
        initComponents();
        setLocationRelativeTo(null);
        menu = new Menu(isAdmin);
        menuBUS = new DieuHuongMenuBUS(menu, isAdmin);
        mainForm = new MainForm(menu, menuBUS);
        setContentPane(mainForm);
    }

    public static void showForm(Component component, String titleName) {
        component.applyComponentOrientation(app.getComponentOrientation());
        app.mainForm.showForm(component, titleName);
    }

    public static void setSelectedMenu(int index, int subIndex) {
        app.menuBUS.setSelectedMenu(index, subIndex);
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

        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("quanlynganhang.GUI.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup();

        java.awt.EventQueue.invokeLater(() -> {
            FlatSVGIcon favicon = new FlatSVGIcon("quanlynganhang/icon/favicon.svg");
            app = new ApplicationAdmin();
            app.setIconImage(favicon.getImage());
            app.setTitle("Quản lý ngân hàng - Quản trị viên");
            GlassPanePopup.install(app);
            setSelectedMenu(0, 0);
            app.setVisible(true);
            app.setResizable(true);
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
