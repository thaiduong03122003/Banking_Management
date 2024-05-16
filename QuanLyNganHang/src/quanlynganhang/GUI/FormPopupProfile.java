/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.adminUI.ApplicationAdmin;
import quanlynganhang.GUI.adminUI.JFrameChiTietTKNV;

/**
 *
 * @author THAI
 */
public class FormPopupProfile extends javax.swing.JPanel {

    private TaiKhoanNVDTO taiKhoanNV;
    private NhanVienBUS nhanVienBUS;
    private Application app;
    private ApplicationAdmin appAdmin;
    private int quyenSua, quyenXoa;

    public FormPopupProfile(TaiKhoanNVDTO taiKhoanNV, Application app, ApplicationAdmin appAdmin, int quyenSua, int quyenXoa) {
        this.taiKhoanNV = taiKhoanNV;
        this.app = app;
        this.appAdmin = appAdmin;
        this.quyenSua = quyenSua;
        this.quyenXoa = quyenXoa;
        nhanVienBUS = new NhanVienBUS();
        initComponents();
        jPPopupProfile.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        loadThongTin(taiKhoanNV.getMaNhanVien());
    }

    private void loadThongTin(int maTaiKhoanNV) {
        NhanVienDTO nhanVien = nhanVienBUS.getNhanVienById(maTaiKhoanNV, 0);

        if (nhanVien != null) {
            lbTenNV.setText(nhanVien.getHoDem() + " " + nhanVien.getTen());
            lbChucVu.setText(nhanVien.getTenChucVu());
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

        jPPopupProfile = new javax.swing.JPanel();
        pictureBox1 = new quanlynganhang.GUI.model.picturebox.PictureBox();
        btnThongTinCaNhan = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();
        lbTenNV = new javax.swing.JLabel();
        lbChucVu = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        pictureBox1.setImage(new javax.swing.ImageIcon(getClass().getResource("/quanlynganhang/image/man.png"))); // NOI18N

        btnThongTinCaNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnThongTinCaNhan.setText("Thông tin cá nhân");
        btnThongTinCaNhan.setBorderPainted(false);
        btnThongTinCaNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongTinCaNhanActionPerformed(evt);
            }
        });

        btnDangXuat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDangXuat.setForeground(new java.awt.Color(255, 0, 51));
        btnDangXuat.setText("Đăng xuất");
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        lbTenNV.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        lbTenNV.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTenNV.setText("Dương Nguyễn Nghĩa Thái");

        lbChucVu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbChucVu.setText("Giao dịch viên");

        javax.swing.GroupLayout jPPopupProfileLayout = new javax.swing.GroupLayout(jPPopupProfile);
        jPPopupProfile.setLayout(jPPopupProfileLayout);
        jPPopupProfileLayout.setHorizontalGroup(
            jPPopupProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnThongTinCaNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lbTenNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPopupProfileLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lbChucVu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
        );
        jPPopupProfileLayout.setVerticalGroup(
            jPPopupProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPopupProfileLayout.createSequentialGroup()
                .addComponent(pictureBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTenNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbChucVu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(btnThongTinCaNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPopupProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPopupProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        if (app != null) {
            app.dispose();
        } else {
            appAdmin.dispose();
        }

        JFrameDangNhap dangNhap = new JFrameDangNhap();
        dangNhap.setDefaultCloseOperation(JFrameDangNhap.DISPOSE_ON_CLOSE);
        dangNhap.setVisible(true);
    }//GEN-LAST:event_btnDangXuatActionPerformed

    private void btnThongTinCaNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongTinCaNhanActionPerformed
        JFrameChiTietTKNV formChiTiet = new JFrameChiTietTKNV(taiKhoanNV.getMaTKNV(), false, quyenSua, quyenXoa);
        formChiTiet.setDefaultCloseOperation(JFrameChiTietTKNV.DISPOSE_ON_CLOSE);
        formChiTiet.setVisible(true);
    }//GEN-LAST:event_btnThongTinCaNhanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnThongTinCaNhan;
    private javax.swing.JPanel jPPopupProfile;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbChucVu;
    private javax.swing.JLabel lbTenNV;
    private quanlynganhang.GUI.model.picturebox.PictureBox pictureBox1;
    // End of variables declaration//GEN-END:variables
}
