package quanlynganhang.GUI.adminUI;

import com.formdev.flatlaf.FlatClientProperties;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import quanlynganhang.BUS.DangNhapBUS;
import quanlynganhang.BUS.KhoaTaiKhoanBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TaiKhoanNVBUS;
import quanlynganhang.BUS.TrangThaiBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.KhoaTaiKhoanDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JDiaLogDoiTrangThai extends javax.swing.JDialog {

    private TrangThaiBUS trangThaiBUS;
    private TaiKhoanNVBUS taiKhoanNVBUS;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private DangNhapBUS dangNhapBUS;
    private KhoaTaiKhoanBUS khoaTaiKhoanBUS;
    private String loaiMa, danhMuc;
    private int maTK;
    private String loaiTaiKhoan, tenTrangThai;
    private FormatDate fDate;
    private KhoaTaiKhoanDTO khoaTK;

    public JDiaLogDoiTrangThai(java.awt.Frame parent, boolean modal, String loaiMa, String danhMuc, int maTK, String tenTrangThai, String loaiTaiKhoan) {
        super(parent, modal);
        trangThaiBUS = new TrangThaiBUS();
        khoaTaiKhoanBUS = new KhoaTaiKhoanBUS();
        dangNhapBUS = new DangNhapBUS();
        this.danhMuc = danhMuc;
        this.loaiMa = loaiMa;
        this.maTK = maTK;
        this.tenTrangThai = tenTrangThai;
        this.loaiTaiKhoan = loaiTaiKhoan;
        fDate = new FormatDate();
        initComponents();
        customUI();
        txtMa.setText("" + maTK);
        doiLoaiTaiKhoan();
        loadTrangThai();
    }

    private void customUI() {
        txtKhoaDen.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "dd/MM/yyyy");
        lbMa.setText(loaiMa);
        jPKhoaDen.setVisible(false);
        jPLyDo.setVisible(false);
    }

    private void doiLoaiTaiKhoan() {
        if (loaiTaiKhoan.equals("TKNV")) {
            taiKhoanNVBUS = new TaiKhoanNVBUS();
        } else if (loaiTaiKhoan.equals("TKKH")) {
            taiKhoanKHBUS = new TaiKhoanKHBUS();
        } else {

        }
    }

    private void loadTrangThai() {
        int selectedIndex = 0;
        int index = 0;

        Map<Integer, String> map = new HashMap<>();
        map = trangThaiBUS.convertListTrangThaiToMap(danhMuc);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        for (String tenTrangThai : map.values()) {
            model.addElement(tenTrangThai);
            if (tenTrangThai.equals(this.tenTrangThai)) {
                selectedIndex = index;
            }
            index++;
        }

        cbxTrangThai.setModel(model);
        cbxTrangThai.setSelectedIndex(selectedIndex);

        if (this.tenTrangThai.equals("Locked")) {
            fillLockedStatus();
        }
    }

    private void fillLockedStatus() {
        khoaTK = khoaTaiKhoanBUS.selectByIdTK(maTK, loaiTaiKhoan);

        if (khoaTK == null) {
            return;
        }

        txtKhoaDen.setText(fDate.toString(khoaTK.getNgayMoKhoa()));
        txaLyDo.setText(khoaTK.getLyDoKhoa());
    }

    private boolean doiTrangThai() {

        String tenTrangThai = (String) cbxTrangThai.getSelectedItem();
        Integer maTrangthai = trangThaiBUS.getIdFromTenTrangThai(tenTrangThai, danhMuc);

        if (maTrangthai != null) {
            if (this.tenTrangThai.equals("Locked") && !tenTrangThai.equals("Locked")) {
                moKhoa();
            }

            if (loaiTaiKhoan.equals("TKNV")) {
                return taiKhoanNVBUS.doiTrangThai(maTK, maTrangthai.intValue());
            } else if (loaiTaiKhoan.equals("TKKH")) {
                return taiKhoanKHBUS.doiTrangThai(maTK, maTrangthai.intValue());
            } else {
                return false;
            }

        } else {
            MessageBox.showErrorMessage(null, "Lấy id của trạng thái thất bại!");
            return false;
        }
    }

    private boolean datNgayMoKhoa() {
        KhoaTaiKhoanDTO khoaTaiKhoan = new KhoaTaiKhoanDTO();
        khoaTaiKhoan.setMaTaiKhoan(maTK);

        String ngayKhoa = txtKhoaDen.getText();
        if (InputValidation.kiemTraNgay(ngayKhoa)) {
            if (InputValidation.kiemTraNgayKhoa(ngayKhoa)) {
                khoaTaiKhoan.setNgayMoKhoa(fDate.toDate(ngayKhoa));
                khoaTaiKhoan.setLyDoKhoa(txaLyDo.getText());
                khoaTaiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);

                return khoaTaiKhoanBUS.addKhoaTaiKhoan(khoaTaiKhoan);
            } else {
                MessageBox.showErrorMessage(null, "Ngày khóa phải lớn hơn ngày hiện tại!");
                return false;
            }
        } else {
            MessageBox.showErrorMessage(null, "Ngày nhập không hợp lệ!");
            return false;
        }
    }

    private void moKhoa() {
        if (khoaTK == null) {
            return;
        }

        khoaTaiKhoanBUS.unlock(khoaTK.getMaKhoaTK());
    }

    private void confirmDoiTT() {
        if (cbxTrangThai.getSelectedItem().equals("Locked")) {
            if (doiTrangThai() && datNgayMoKhoa()) {
                MessageBox.showInformationMessage(null, "", "Khóa tài khoản thành công!");
                this.dispose();
            }
        } else {
            if (doiTrangThai()) {
                MessageBox.showInformationMessage(null, "", "Thay đổi trạng thái tài khoản thành công!");
                this.dispose();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        btnDoi = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        lbMa = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbxTrangThai = new javax.swing.JComboBox<>();
        jPKhoaDen = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtKhoaDen = new javax.swing.JTextField();
        jPLyDo = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaLyDo = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đổi trạng thái");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        btnClose.setText("Hủy");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnDoi.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDoi.setText("Đổi");
        btnDoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addComponent(btnClose)
                .addGap(18, 18, 18)
                .addComponent(btnDoi)
                .addContainerGap(106, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnDoi))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        lbMa.setText("Mã tài khoản:");

        txtMa.setEnabled(false);
        txtMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMatxtNgayVaoLamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lbMa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbMa)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel16.setText("Trạng thái:");

        cbxTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTrangThaiItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cbxTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setText("Khóa đến:");

        txtKhoaDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKhoaDentxtNgayVaoLamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPKhoaDenLayout = new javax.swing.GroupLayout(jPKhoaDen);
        jPKhoaDen.setLayout(jPKhoaDenLayout);
        jPKhoaDenLayout.setHorizontalGroup(
            jPKhoaDenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPKhoaDenLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtKhoaDen, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPKhoaDenLayout.setVerticalGroup(
            jPKhoaDenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPKhoaDenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPKhoaDenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtKhoaDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel18.setText("Lý do khóa:");

        txaLyDo.setColumns(20);
        txaLyDo.setRows(5);
        jScrollPane1.setViewportView(txaLyDo);

        javax.swing.GroupLayout jPLyDoLayout = new javax.swing.GroupLayout(jPLyDo);
        jPLyDo.setLayout(jPLyDoLayout);
        jPLyDoLayout.setHorizontalGroup(
            jPLyDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPLyDoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );
        jPLyDoLayout.setVerticalGroup(
            jPLyDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPLyDoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPLyDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPKhoaDen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPLyDo, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPKhoaDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPLyDo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiActionPerformed
        if (loaiTaiKhoan.equals("TKNV")) {
            if (taiKhoanNVBUS.getTaiKhoanNVById(maTK).getTinhTrangDangNhap() == 1) {
                if (MessageBox.showConfirmMessage(this, "Tài khoản này đang hoạt động, bạn có chắc chắn muốn đổi?") == JOptionPane.YES_OPTION) {
                    confirmDoiTT();
                    return;
                } else {
                    return;
                }
            }
        }

        if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn đổi?") == JOptionPane.YES_OPTION) {
            confirmDoiTT();
        } else {
            return;
        }
    }//GEN-LAST:event_btnDoiActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void txtMatxtNgayVaoLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMatxtNgayVaoLamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMatxtNgayVaoLamActionPerformed

    private void cbxTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTrangThaiItemStateChanged
        String tenTrangThai = (String) cbxTrangThai.getSelectedItem();
        Integer maTrangThai = trangThaiBUS.getIdFromTenTrangThai(tenTrangThai, danhMuc);

        if (maTrangThai == null) {
            MessageBox.showErrorMessage(null, "Lấy id của trạng thái thất bại!");
        } else {
            if (maTrangThai == 2) {
                jPKhoaDen.setVisible(true);
                jPLyDo.setVisible(true);
            } else {
                txtKhoaDen.setText("");
                txaLyDo.setText("");
                jPKhoaDen.setVisible(false);
                jPLyDo.setVisible(false);
            }
        }
    }//GEN-LAST:event_cbxTrangThaiItemStateChanged

    private void txtKhoaDentxtNgayVaoLamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKhoaDentxtNgayVaoLamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKhoaDentxtNgayVaoLamActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnDoi;
    private javax.swing.JComboBox<String> cbxTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JPanel jPKhoaDen;
    private javax.swing.JPanel jPLyDo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbMa;
    private javax.swing.JTextArea txaLyDo;
    private javax.swing.JTextField txtKhoaDen;
    private javax.swing.JTextField txtMa;
    // End of variables declaration//GEN-END:variables
}
