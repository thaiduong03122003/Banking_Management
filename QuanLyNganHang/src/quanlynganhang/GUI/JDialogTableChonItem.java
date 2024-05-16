package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TaiKhoanNVBUS;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.adminUI.FormPhanQuyen;
import quanlynganhang.GUI.adminUI.JFrameChiTietTKNV;
import quanlynganhang.GUI.adminUI.JFrameThemTKNV2;
import quanlynganhang.GUI.model.message.MessageBox;
import quanlynganhang.GUI.FormMoTheTinDung;

public class JDialogTableChonItem extends javax.swing.JDialog {

    private String loaiDanhSach;
    private NhanVienBUS nhanVienBUS;
    private KhachHangBUS khachHangBUS;
    private TaiKhoanNVBUS taiKhoanNVBUS;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private JFrameThemTKNV2 themTKNV2;
    private FormPhanQuyen phanQuyen;
    private FormMoTaiKhoan moTaiKhoan;
    private FormNapTien napTien;
    private FormRutTien rutTien;
    private FormChuyenCungNganHang chuyenCungNganHang;
    private FormChuyenLienNganHang chuyenLienNganHang;
    private FormMoTKTietKiem formMoTKTietKiem;
    private FormChoVayVon formChoVayVon;
    private FormMoTheGhiNo formMoTheGhiNo;
    private FormMoTheTinDung formMoTheTinDung;
    private JFrameBoLocDSThe jFrameBoLocDSThe;
    private JFrameBoLocGD jFrameBoLocGD;
    private FormTraKhoanVay formTraKhoanVay;
    private FormTongQuan formTongQuan;

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, JFrameThemTKNV2 themTKNV2, String title, String loaiDanhSach) {
        super(parent, modal);
        this.themTKNV2 = themTKNV2;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormPhanQuyen phanQuyen, String title, String loaiDanhSach) {
        super(parent, modal);
        this.phanQuyen = phanQuyen;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormMoTaiKhoan moTaiKhoan, String title, String loaiDanhSach) {
        super(parent, modal);
        this.moTaiKhoan = moTaiKhoan;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormNapTien napTien, String title, String loaiDanhSach) {
        super(parent, modal);
        this.napTien = napTien;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormRutTien rutTien, String title, String loaiDanhSach) {
        super(parent, modal);
        this.rutTien = rutTien;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormChuyenCungNganHang chuyenCungNganHang, String title, String loaiDanhSach) {
        super(parent, modal);
        this.chuyenCungNganHang = chuyenCungNganHang;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormChuyenLienNganHang chuyenLienNganHang, String title, String loaiDanhSach) {
        super(parent, modal);
        this.chuyenLienNganHang = chuyenLienNganHang;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }
    
    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormMoTKTietKiem formMoTKTietKiem, String title, String loaiDanhSach) {
        super(parent, modal);
        this.formMoTKTietKiem = formMoTKTietKiem;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }
    
    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormChoVayVon formChoVayVon, String title, String loaiDanhSach) {
        super(parent, modal);
        this.formChoVayVon = formChoVayVon;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }
    
    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormMoTheGhiNo formMoTheGhiNo, String title, String loaiDanhSach) {
        super(parent, modal);
        this.formMoTheGhiNo = formMoTheGhiNo;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }
    
    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, JFrameBoLocDSThe jFrameBoLocDSThe, String title, String loaiDanhSach) {
        super(parent, modal);
        this.jFrameBoLocDSThe = jFrameBoLocDSThe;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }
    
    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormMoTheTinDung formMoTheTinDung, String title, String loaiDanhSach) {
        super(parent, modal);
        this.formMoTheTinDung = formMoTheTinDung;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }
    
    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, JFrameBoLocGD jFrameBoLocGD, String title, String loaiDanhSach) {
        super(parent, modal);
        this.jFrameBoLocGD = jFrameBoLocGD;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }
    
    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormTraKhoanVay formTraKhoanVay, String title, String loaiDanhSach) {
        super(parent, modal);
        this.formTraKhoanVay = formTraKhoanVay;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }
    
    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormTongQuan formTongQuan, String title, String loaiDanhSach) {
        super(parent, modal);
        this.formTongQuan = formTongQuan;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã / tên cần tìm...");

        switchTable();
    }

    public void loadDSNhanVien() throws Exception {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = nhanVienBUS.doiSangObjectNhanVien(0, false, null);
        String[] title = {"Mã nhân viên", "Họ đệm", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Mã Căn cước công dân", "Chức vụ"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSKhachHang() throws Exception {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = khachHangBUS.doiSangObjectKhachHang(0, false, null);
        String[] title = {"Mã khách hàng", "Họ đệm", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Mã Căn cước công dân", "Nợ xấu"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSTaiKhoanNV() throws Exception {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = taiKhoanNVBUS.doiSangObjectTaiKhoanNV(false, null);
        String[] title = {"Mã tài khoản", "Họ tên nhân viên", "Tên đăng nhập", "Ngày tạo", "Trạng thái tài khoản"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSTaiKhoanKH() throws Exception {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = taiKhoanKHBUS.doiSangObjectTaiKhoanKH(false, null);
        String[] title = {"Mã tài khoản", "Số tài khoản", "Tên tài khoản", "Tên khách hàng", "Ngày tạo", "Loại tài khoản", "Trạng thái tài khoản"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }
    
    public void loadDSTaiKhoanVay() throws Exception {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = taiKhoanKHBUS.doiSangObjectTaiKhoanVay();
        String[] title = {"Mã tài khoản", "Số tài khoản", "Tên tài khoản", "Tên khách hàng", "Ngày tạo", "Loại tài khoản", "Trạng thái tài khoản"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void switchTable() {
        try {
            if (loaiDanhSach.equals("DSNV")) {
                nhanVienBUS = new NhanVienBUS();
                loadDSNhanVien();
            } else if (loaiDanhSach.equals("DSTKNV")) {
                taiKhoanNVBUS = new TaiKhoanNVBUS();
                loadDSTaiKhoanNV();
            } else if (loaiDanhSach.equals("DSKH")) {
                khachHangBUS = new KhachHangBUS();
                loadDSKhachHang();
            } else if (loaiDanhSach.equals("DSTKKH")) {
                taiKhoanKHBUS = new TaiKhoanKHBUS();
                loadDSTaiKhoanKH();
            } else if (loaiDanhSach.equals("DSTKV")) {
                taiKhoanKHBUS = new TaiKhoanKHBUS();
                loadDSTaiKhoanVay();
            } else {
                MessageBox.showErrorMessage(null, "Không tìm thấy danh sách được chọn!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showErrorMessage(null, "Load danh sách thất bại!");
        }
    }

    public void guiMaTaiKhoanKH(int maTaiKhoanKH) {
        if (napTien != null) {
            napTien.dienThongTinTKKH(maTaiKhoanKH);
            this.dispose();
        } else if (rutTien != null) {
            rutTien.dienThongTinTKKH(maTaiKhoanKH);
            this.dispose();
        } else if (chuyenCungNganHang != null) {
            chuyenCungNganHang.dienTKNguoiGui(maTaiKhoanKH);
            this.dispose();
        } else if (chuyenLienNganHang != null) {
            chuyenLienNganHang.dienTKNguoiGui(maTaiKhoanKH);
            this.dispose();
        } else if (formMoTheGhiNo != null) {
            formMoTheGhiNo.dienThongTinTKKH(maTaiKhoanKH);
            this.dispose();
        } else if (formMoTheTinDung != null) {
            formMoTheTinDung.dienThongTinTKKH(maTaiKhoanKH);
            this.dispose();
        } else if (formTongQuan != null) {
            MessageBox.showErrorMessage(null, "Bạn không thể xem chi tiết!");
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
        jPanel4 = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnChon = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDS = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(476, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Hủy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnChon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChon.setText("Chọn");
        btnChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(333, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btnChon)
                .addContainerGap(317, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnChon))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jTableDS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTableDS);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonActionPerformed
        int selectedRow = jTableDS.getSelectedRow();
        if (selectedRow == -1) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn dữ liệu!");
            return;
        } else {

            Object idObj = jTableDS.getValueAt(selectedRow, 0);
            if (idObj != null) {
                int id = Integer.parseInt(idObj.toString());

                if (loaiDanhSach.equals("DSNV")) {

                    if (themTKNV2 != null) {
                        themTKNV2.dienThongTinNV(id);
                    } else if (phanQuyen != null) {
                        phanQuyen.dienThongTin(id);
                    } else if (jFrameBoLocGD != null){
                        jFrameBoLocGD.dienIdNV(id);
                    }
                    this.dispose();

                } else if (loaiDanhSach.equals("DSTKNV")) {
                    TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                    taiKhoanNV = taiKhoanNVBUS.getTaiKhoanNVById(id);
                    if (taiKhoanNV == null) {
                        MessageBox.showErrorMessage(null, "Mã tài khoản nhân viên không tồn tại!");
                        return;
                    } else {
//                        themTKNV2.dienThongTinNV(nhanVien);
                    }

                } else if (loaiDanhSach.equals("DSKH")) {

                    if (moTaiKhoan != null) {
                        moTaiKhoan.dienThongTinKH(id);
                    } else if (formMoTKTietKiem != null) {
                        formMoTKTietKiem.dienThongTinKH(id);
                    } else if (formChoVayVon != null) {
                        formChoVayVon.dienThongTinKH(id);
                    } else if(jFrameBoLocDSThe != null) {
                        jFrameBoLocDSThe.dienIdKH(id);
                    } else if (jFrameBoLocGD != null) {
                        jFrameBoLocGD.dienIdKH(id);
                    } else if (formTongQuan != null) {
                        MessageBox.showErrorMessage(null, "Bạn không thể xem chi tiết!");
                    }
                    this.dispose();

                } else if (loaiDanhSach.equals("DSTKKH")) {
                    JDialogXacNhanChon xacNhanChon = new JDialogXacNhanChon(null, true, this, id);
                    xacNhanChon.setDefaultCloseOperation(JDialogXacNhanChon.DISPOSE_ON_CLOSE);
                    xacNhanChon.setVisible(true);
                } else if (loaiDanhSach.equals("DSTKV")){
                    if (formTraKhoanVay != null) {
                        formTraKhoanVay.getMaTaiKhoanVay(id);
                        this.dispose();
                    }
                }

            }
        }
    }//GEN-LAST:event_btnChonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DefaultTableModel obj = (DefaultTableModel) jTableDS.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        jTableDS.setRowSorter(obj1);

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter(txtSearch.getText());

        obj1.setRowFilter(rowFilter);
    }//GEN-LAST:event_txtSearchKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogTableChonItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogTableChonItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogTableChonItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogTableChonItem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChon;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDS;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
