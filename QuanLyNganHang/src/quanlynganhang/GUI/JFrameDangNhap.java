package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.awt.event.KeyEvent;
import javax.swing.UIManager;
import quanlynganhang.BUS.DangNhapBUS;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.adminUI.ApplicationAdmin;
import quanlynganhang.GUI.model.glasspanepopup.GlassPanePopup;
import quanlynganhang.GUI.model.message.MessageBox;

public class JFrameDangNhap extends javax.swing.JFrame {

    private DangNhapBUS dangNhapBUS;

    public JFrameDangNhap() {
        dangNhapBUS = new DangNhapBUS();
        initComponents();
        customUI();
    }

    private void customUI() {
        txtTenDangNhap.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg"));
        pwfMatKhau.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("quanlynganhang/icon/password_label.svg"));
        pwfMatKhau.putClientProperty(FlatClientProperties.STYLE, ""
            + "showRevealButton:true;");

        txtTenDangNhap.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên đăng nhập");
        pwfMatKhau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");

    }

    private void dangNhap() {
        String matKhau = String.valueOf(pwfMatKhau.getPassword());
        if (txtTenDangNhap.getText().isEmpty() || matKhau.isEmpty()) {
            MessageBox.showErrorMessage(null, "Vui lòng nhập đầy đủ thông tin!");
        } else {
            TaiKhoanNVDTO taiKhoanNV = dangNhapBUS.kiemTraDangNhap(txtTenDangNhap.getText(), matKhau);
            if (taiKhoanNV != null) {
                int isAdmin = dangNhapBUS.kiemTraChucVu(taiKhoanNV);

                FlatSVGIcon favicon = new FlatSVGIcon("quanlynganhang/icon/favicon.svg");
                if (isAdmin == 0) {

                    if (taiKhoanNV.getMaTrangThai() == 6) {
                        this.dispose();
                        Application app = new Application(taiKhoanNV);

                        app.setIconImage(favicon.getImage());
                        app.setTitle("Quản lý ngân hàng");
                        GlassPanePopup.install(app);
                        app.setSelectedMenu(0, 0);
                        app.setVisible(true);
                        app.setResizable(false);
                        app.setDefaultCloseOperation(Application.DISPOSE_ON_CLOSE);
                    } else {
                        MessageBox.showErrorMessage(null, "Vui lòng kích hoạt tài khoản");
                    }

                } else if (isAdmin == 1) {
                    if (taiKhoanNV.getMaTrangThai() == 6) {
                        this.dispose();
                        ApplicationAdmin app = new ApplicationAdmin(taiKhoanNV);

                        app.setIconImage(favicon.getImage());
                        app.setTitle("Quản lý ngân hàng - Quản trị viên");
                        GlassPanePopup.install(app);
                        app.setSelectedMenu(0, 0);
                        app.setVisible(true);
                        app.setResizable(true);
                        app.setDefaultCloseOperation(ApplicationAdmin.DISPOSE_ON_CLOSE);
                    } else {
                        MessageBox.showErrorMessage(null, "Vui lòng kích hoạt tài khoản");
                    }
                } else {
                    MessageBox.showErrorMessage(null, "Có vẻ bạn chưa được phân quyền, vui lòng liên hệ đến quản trị viên để kích hoạt!");
                }
            } else {
                MessageBox.showErrorMessage(null, "Tên đăng nhập hoặc mật khẩu không chính xác!");
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnDangNhap = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtTenDangNhap = new javax.swing.JTextField();
        pwfMatKhau = new javax.swing.JPasswordField();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ĐĂNG NHẬP VÀO HỆ THỐNG");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        btnDangNhap.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnDangNhap.setText("Đăng nhập");
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });
        btnDangNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnDangNhapKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnDangNhapKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setLayout(new java.awt.BorderLayout());

        txtTenDangNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenDangNhap.setText("nhainhai123");
        txtTenDangNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenDangNhapKeyReleased(evt);
            }
        });

        pwfMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pwfMatKhau.setText("Nhai@123456");
        pwfMatKhau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pwfMatKhauKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenDangNhap)
                    .addComponent(pwfMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pwfMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );

        jPanel3.add(jPanel8, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
        dangNhap();
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void btnDangNhapKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDangNhapKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDangNhapKeyTyped

    private void btnDangNhapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDangNhapKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dangNhap();
        }
    }//GEN-LAST:event_btnDangNhapKeyReleased

    private void pwfMatKhauKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pwfMatKhauKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            dangNhap();
        }
    }//GEN-LAST:event_pwfMatKhauKeyReleased

    private void txtTenDangNhapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenDangNhapKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            pwfMatKhau.requestFocus();
        }
    }//GEN-LAST:event_txtTenDangNhapKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("quanlynganhang.GUI.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FlatSVGIcon favicon = new FlatSVGIcon("quanlynganhang/icon/favicon.svg");
                JFrameDangNhap login = new JFrameDangNhap();
                login.setIconImage(favicon.getImage());
                login.setTitle("Đăng nhập");
                login.setVisible(true);
                login.setResizable(false);
                login.setDefaultCloseOperation(JFrameDangNhap.DISPOSE_ON_CLOSE);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPasswordField pwfMatKhau;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
