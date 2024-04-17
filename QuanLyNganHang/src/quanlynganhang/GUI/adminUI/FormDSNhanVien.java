package quanlynganhang.GUI.adminUI;

import quanlynganhang.GUI.*;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;

public class FormDSNhanVien extends javax.swing.JPanel {

    private JFrameBoLocDSNV boloc;
    private JFrameChiTietNV formChiTiet;
    private NhanVienBUS nhanVienBUS;
    private int biXoa;

    public FormDSNhanVien() {
        nhanVienBUS = new NhanVienBUS();
        initComponents();
        txtSearchNV.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / họ tên / mã căn cước của nhân viên cần tìm...");
        biXoa = 0;
        loadDSNhanVien(biXoa);
    }

    public void loadDSNhanVien(int biXoa) {
        DefaultTableModel model = (DefaultTableModel) jTableDSNV.getModel();
        model.setRowCount(0);

        Object[][] dataModel = nhanVienBUS.doiSangObjectNhanVien(biXoa);

        String[] title = {"Mã nhân viên", "Họ đệm", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Mã Căn cước công dân", "Chức vụ"};
        model.setDataVector(dataModel, title);

        jTableDSNV.setDefaultEditor(Object.class, null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popupDSNV = new javax.swing.JPopupMenu();
        ppmChiTiet = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        ppmSua = new javax.swing.JMenuItem();
        ppmXoa = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtSearchNV = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnBoLoc = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnThemNV = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        btnDoiBang = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDSNV = new javax.swing.JTable();

        ppmChiTiet.setText("Xem chi tiết");
        ppmChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmChiTietActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmChiTiet);
        popupDSNV.add(jSeparator2);

        ppmSua.setText("Sửa");
        ppmSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmSuaActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmSua);

        ppmXoa.setForeground(new java.awt.Color(255, 0, 51));
        ppmXoa.setText("Xóa");
        ppmXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmXoaActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmXoa);

        setPreferredSize(new java.awt.Dimension(1132, 511));
        setLayout(new java.awt.BorderLayout());

        jButton5.setIcon(new FlatSVGIcon("quanlynganhang/icon/search_btn.svg"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtSearchNV, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearchNV, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnBoLoc.setIcon(new FlatSVGIcon("quanlynganhang/icon/filter_btn.svg")
        );
        btnBoLoc.setText("Bộ lọc");
        btnBoLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoLocActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Sắp xếp theo-", "Mã nhân viên tăng dần", "Mã nhân viên giảm dần", "Tên theo thứ tự A -> Z", "Tên theo thứ tự Z -> A" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(btnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBoLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jComboBox1))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jButton1.setIcon(new FlatSVGIcon("quanlynganhang/icon/xuat_excel_btn.svg"));
        jButton1.setText("Xuất file");

        jButton2.setIcon(new FlatSVGIcon("quanlynganhang/icon/nhap_excel_btn.svg")
        );
        jButton2.setText("Nhập file");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThemNV.setText("Thêm NV");
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });

        jButton4.setText("Thêm tài khoản");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton7.setText("Thêm chức vụ");

        jButton8.setText("Tải lại DS");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        btnDoiBang.setText("DSNV bị xóa");
        btnDoiBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiBangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDoiBang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDoiBang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.LINE_END);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jTableDSNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ đệm", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Mã CCCD", "Chức vụ"
            }
        ));
        jTableDSNV.setComponentPopupMenu(popupDSNV);
        jScrollPane2.setViewportView(jTableDSNV);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBoLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoLocActionPerformed
        if (boloc == null) {
            boloc = new JFrameBoLocDSNV();
            boloc.setResizable(false);
            boloc.setDefaultCloseOperation(JFrameBoLocDSNV.DISPOSE_ON_CLOSE);
        }

        boloc.setExtendedState(JFrameBoLocDSNV.NORMAL);
        boloc.setVisible(true);
    }//GEN-LAST:event_btnBoLocActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        JDialogThemNV themNV = new JDialogThemNV(null, true);
        themNV.setDefaultCloseOperation(JDialogThemNV.DISPOSE_ON_CLOSE);
        themNV.setVisible(true);
        loadDSNhanVien(0);
    }//GEN-LAST:event_btnThemNVActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JDialogThemTKNV themTKNV = new JDialogThemTKNV(null, true);
        themTKNV.setDefaultCloseOperation(JDialogThemTKNV.DISPOSE_ON_CLOSE);
        themTKNV.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnDoiBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiBangActionPerformed
        if (biXoa == 0) {
            biXoa = 1;
            loadDSNhanVien(biXoa);
            ppmSua.setText("Khôi phục");
            btnDoiBang.setText("DSNV hiện tại");
        } else {
            biXoa = 0;
            loadDSNhanVien(biXoa);
            ppmSua.setText("Sửa");
            btnDoiBang.setText("DSNV bị xóa");
        }
    }//GEN-LAST:event_btnDoiBangActionPerformed

    private void ppmChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmChiTietActionPerformed
        if (formChiTiet == null) {
            int selectedRow = jTableDSNV.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên trước khi xem!");
                return;
            } else {

                Object idObj = jTableDSNV.getValueAt(selectedRow, 0);
                if (idObj != null) {
                    int maNhanVien = Integer.parseInt(idObj.toString());
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien = nhanVienBUS.getNhanVienById(maNhanVien, biXoa);
                    if (nhanVien == null) {
                        MessageBox.showErrorMessage(null, "Mã nhân viên không tồn tại!");
                        return;
                    } else {
                        formChiTiet = new JFrameChiTietNV(nhanVien, false);
                        formChiTiet.setDefaultCloseOperation(JFrameChiTietNV.DISPOSE_ON_CLOSE);
                        formChiTiet.setVisible(true);

                        formChiTiet.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                loadDSNhanVien(biXoa);
                            }
                        });
                    }
                }
            }
        } else {
            formChiTiet.setExtendedState(JFrameChiTietNV.NORMAL);
            formChiTiet.setVisible(true);
        }
    }//GEN-LAST:event_ppmChiTietActionPerformed

    private void ppmSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmSuaActionPerformed
        int selectedRow = jTableDSNV.getSelectedRow();
        if (selectedRow == -1) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên trước khi sửa!");
            return;

        } else {
            Object idObj = jTableDSNV.getValueAt(selectedRow, 0);
            if (idObj != null) {
                int maNhanVien = Integer.parseInt(idObj.toString());
                NhanVienDTO nhanVien = new NhanVienDTO();
                nhanVien = nhanVienBUS.getNhanVienById(maNhanVien, biXoa);
                if (nhanVien == null) {
                    MessageBox.showErrorMessage(null, "Mã nhân viên không tồn tại!");
                    return;
                } else {

                    if (biXoa == 1) {
                        if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn khôi phục nhân viên này?") == JOptionPane.NO_OPTION) {
                            return;
                        }
                        if (nhanVienBUS.restoreNhanVien(maNhanVien)) {
                            MessageBox.showInformationMessage(null, "", "Đã khôi phục lại nhân viên!");
                            loadDSNhanVien(biXoa);
                        } else {
                            MessageBox.showErrorMessage(null, "Khôi phục nhân viên thất bại");
                        }
                    } else {
                        JFrameChiTietNV formSua = new JFrameChiTietNV(nhanVien, true);
                        formSua.setDefaultCloseOperation(JFrameChiTietNV.DISPOSE_ON_CLOSE);
                        formSua.setVisible(true);

                        formSua.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                loadDSNhanVien(biXoa);
                            }
                        });
                    }
                }
            }
        }
    }//GEN-LAST:event_ppmSuaActionPerformed

    private void ppmXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmXoaActionPerformed
        int selectedRow = jTableDSNV.getSelectedRow();
        if (selectedRow == -1) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên trước khi xóa!");
            return;
        } else {
            if (biXoa == 1) {
                MessageBox.showErrorMessage(null, "Tính năng đang cập nhật");
                return;
            } else {
                Object idObj = jTableDSNV.getValueAt(selectedRow, 0);
                if (idObj != null) {
                    if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn xóa nhân viên này?") == JOptionPane.NO_OPTION) {
                        return;
                    }

                    int maNhanVien = Integer.parseInt(idObj.toString());
                    boolean isDelete = nhanVienBUS.deleteNhanVien(maNhanVien);
                    if (isDelete == false) {
                        MessageBox.showErrorMessage(null, "Xóa nhân viên thất bại!");
                        return;
                    } else {
                        MessageBox.showInformationMessage(null, "", "Xóa nhân viên thành công");

                        loadDSNhanVien(biXoa);
                    }
                }
            }
        }
    }//GEN-LAST:event_ppmXoaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBoLoc;
    private javax.swing.JButton btnDoiBang;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable jTableDSNV;
    private javax.swing.JPopupMenu popupDSNV;
    private javax.swing.JMenuItem ppmChiTiet;
    private javax.swing.JMenuItem ppmSua;
    private javax.swing.JMenuItem ppmXoa;
    private javax.swing.JTextField txtSearchNV;
    // End of variables declaration//GEN-END:variables
}
