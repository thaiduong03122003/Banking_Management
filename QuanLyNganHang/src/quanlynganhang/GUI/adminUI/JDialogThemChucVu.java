package quanlynganhang.GUI.adminUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import quanlynganhang.BUS.ChucVuBUS;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JDialogThemChucVu extends javax.swing.JDialog {

    private ChucVuBUS chucVuBUS;

    public JDialogThemChucVu(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initCustomUI();
        chucVuBUS = new ChucVuBUS();
    }

    public JDialogThemChucVu(java.awt.Frame parent, boolean modal, ChucVuDTO chucVu) {
        super(parent, modal);
        initComponents();
        initCustomUI();
        chucVuBUS = new ChucVuBUS();

        dienThongTin(chucVu);
    }

    private void initCustomUI() {
        jPKhachHang.setVisible(false);
        jPNhanVien.setVisible(false);
        jPTKKhachHang.setVisible(false);
        jPTKNhanVien.setVisible(false);
        jPThe.setVisible(false);
        jPChoNhanVien.setVisible(true);
        jPPhanQuyen.setVisible(false);
        jPChucVu.setVisible(false);

        kiemTraCheckbox(false);
    }

    private void kiemTraCheckbox(boolean isAdmin) {
        if (isAdmin) {
            jPChoNhanVien.setVisible(false);
            chxGiaoDich.setSelected(false);
            chxGuiTietKiem.setSelected(false);
            chxVayVon.setSelected(false);
            chxVayTinDung.setSelected(false);
            jPPhanQuyen.setVisible(true);
            jPChucVu.setVisible(true);

            chxNhanVien.setEnabled(true);
            chxTKNhanVien.setEnabled(true);

            chxKHThem.setSelected(false);
            chxKHSua.setSelected(false);
            chxKHXoa.setSelected(false);

            chxNVThem.setSelected(false);
            chxNVSua.setSelected(false);
            chxNVXoa.setSelected(false);

            chxTKKHThem.setSelected(false);
            chxTKKHSua.setSelected(false);
            chxTKKHXoa.setSelected(false);

            chxTKNVThem.setSelected(false);
            chxTKNVSua.setSelected(false);
            chxTKNVXoa.setSelected(false);

            chxTheThem.setSelected(false);
            chxTheSua.setSelected(false);
            chxTheXoa.setSelected(false);

            chxKHThem.setEnabled(false);
            chxKHSua.setEnabled(false);
            chxKHXoa.setEnabled(false);

            chxTKKHThem.setEnabled(false);
            chxTKKHSua.setEnabled(false);
            chxTKKHXoa.setEnabled(false);

            chxTheThem.setEnabled(false);
            chxTheSua.setEnabled(false);
            chxTheXoa.setEnabled(false);

        } else {
            chxPhanQuyen.setSelected(false);
            chxThemChucVu.setSelected(false);
            jPChoNhanVien.setVisible(true);
            jPPhanQuyen.setVisible(false);
            jPChucVu.setVisible(false);

            chxNVThem.setSelected(false);
            chxNVSua.setSelected(false);
            chxNVXoa.setSelected(false);

            chxTKNVThem.setSelected(false);
            chxTKNVSua.setSelected(false);
            chxTKNVXoa.setSelected(false);

            chxNhanVien.setSelected(false);
            chxTKNhanVien.setSelected(false);

            chxNhanVien.setEnabled(false);
            chxTKNhanVien.setEnabled(false);

            chxKHThem.setEnabled(true);
            chxKHSua.setEnabled(true);
            chxKHXoa.setEnabled(true);

            chxTKKHThem.setEnabled(true);
            chxTKKHSua.setEnabled(true);
            chxTKKHXoa.setEnabled(true);

            chxTheThem.setEnabled(true);
            chxTheSua.setEnabled(true);
            chxTheXoa.setEnabled(true);
        }
    }

    private String layQuyenCheckBox(JCheckBox checkBox, JCheckBox them, JCheckBox sua, JCheckBox xoa) {
        StringBuilder result = new StringBuilder();

        if (checkBox.isSelected()) {
            result.append("1-1");

            result.append(them.isSelected() ? "-1" : "-0");
            result.append(sua.isSelected() ? "-1" : "-0");
            result.append(xoa.isSelected() ? "-1" : "-0");
        } else {
            result.append("0-0-0-0-0");
        }

        return result.toString();
    }

    private boolean kiemTraQuyen(int maChucVu, String tenChucVu, String moTa, boolean isUpdate) {
        ChucVuDTO chucVu = new ChucVuDTO();

        if (isUpdate) {
            chucVu.setMaChucVu(maChucVu);
        }
        chucVu.setTenChucVu(tenChucVu);
        chucVu.setMoTa(moTa);
        chucVu.setIsAdmin(rdbGiaoDienAdmin.isSelected() ? 1 : 0);
        chucVu.setqLThongKe(chxThongKe.isSelected() ? 1 : 0);
        chucVu.setqLGiaoDich(chxGiaoDich.isSelected() ? 1 : 0);
        chucVu.setqLGuiTietKiem(chxGuiTietKiem.isSelected() ? 1 : 0);
        chucVu.setqLVayVon(chxVayVon.isSelected() ? 1 : 0);
        chucVu.setqLVayTinDung(chxVayTinDung.isSelected() ? 1 : 0);
        chucVu.setPhanQuyen(chxPhanQuyen.isSelected() ? 1 : 0);
        chucVu.setThemChucVu(chxThemChucVu.isSelected() ? 1 : 0);

        chucVu.setqLKhachHang(layQuyenCheckBox(chxKhachHang, chxKHThem, chxKHSua, chxKHXoa));
        chucVu.setqLNhanVien(layQuyenCheckBox(chxNhanVien, chxNVThem, chxNVSua, chxNVXoa));
        chucVu.setqLTKKhachHang(layQuyenCheckBox(chxTKKhachHang, chxTKKHThem, chxTKKHSua, chxTKKHXoa));
        chucVu.setqLTKNhanVien(layQuyenCheckBox(chxTKNhanVien, chxTKNVThem, chxTKNVSua, chxTKNVXoa));
        chucVu.setqLThe(layQuyenCheckBox(chxThe, chxTheThem, chxTheSua, chxTheXoa));

        if (!isUpdate) {
            return chucVuBUS.addChucVu(chucVu, 0) != null ? true : false;
        } else {
            return chucVuBUS.updateChucVu(chucVu, 0);
        }

    }

    private void convertDataToCheckbox(String quyen, JCheckBox checkBox, JCheckBox xem, JCheckBox them, JCheckBox sua, JCheckBox xoa, JPanel jPInfo) {
        String[] parts = quyen.split("-");
        if (parts[0].equals("0")) {
            checkBox.setSelected(false);
        } else {
            checkBox.setSelected(true);
            jPInfo.setVisible(true);

            xem.setSelected(true);
            if (parts[2].equals("1")) {
                them.setSelected(true);
            } else {
                them.setSelected(false);
            }

            if (parts[3].equals("1")) {
                sua.setSelected(true); 
            } else {
                sua.setSelected(false);
            }

            if (parts[4].equals("1")) {
                xoa.setSelected(true); 
            } else {
                xoa.setSelected(false);
            }
        }
    }

    private void dienThongTin(ChucVuDTO chucVu) {
        lbTitle.setText("Sửa chức vụ");
        btnThemChucVu.setText("Cập nhật");

        txtMaChucVu.setText("" + chucVu.getMaChucVu());
        txtTenChucVu.setText(chucVu.getTenChucVu());
        txaMoTa.setText(chucVu.getMoTa());

        if (chucVu.getIsAdmin() == 0) {
            rdbGiaoDienNV.setSelected(true);
            rdbGiaoDienNVActionPerformed(null);
        } else {
            rdbGiaoDienAdmin.setSelected(true);
            rdbGiaoDienAdminActionPerformed(null);
        }

        if (chucVu.getqLThongKe() == 1) {
            chxThongKe.setSelected(true);
        } else {
            chxThongKe.setSelected(false);
        }

        if (chucVu.getqLGiaoDich() == 1) {
            chxGiaoDich.setSelected(true);
        } else {
            chxGiaoDich.setSelected(false);
        }

        if (chucVu.getqLGuiTietKiem() == 1) {
            chxGuiTietKiem.setSelected(true);
        } else {
            chxGuiTietKiem.setSelected(false);
        }

        if (chucVu.getqLVayVon() == 1) {
            chxVayVon.setSelected(true);
        } else {
            chxVayVon.setSelected(false);
        }

        if (chucVu.getqLVayTinDung() == 1) {
            chxVayTinDung.setSelected(true);
        } else {
            chxVayTinDung.setSelected(false);
        }
        
        if (chucVu.getPhanQuyen()== 1) {
            chxPhanQuyen.setSelected(true);
        } else {
            chxPhanQuyen.setSelected(false);
        }
        
        if (chucVu.getThemChucVu()== 1) {
            chxThemChucVu.setSelected(true);
        } else {
            chxThemChucVu.setSelected(false);
        }

        convertDataToCheckbox(chucVu.getqLKhachHang(), chxKhachHang, chxKHXem, chxKHThem, chxKHSua, chxKHXoa, jPKhachHang);
        convertDataToCheckbox(chucVu.getqLNhanVien(), chxNhanVien, chxNVXem, chxNVThem, chxNVSua, chxNVXoa, jPNhanVien);
        convertDataToCheckbox(chucVu.getqLTKKhachHang(), chxTKKhachHang, chxTKKHXem, chxTKKHThem, chxTKKHSua, chxTKKHXoa, jPTKKhachHang);
        convertDataToCheckbox(chucVu.getqLTKNhanVien(), chxTKNhanVien, chxTKNVXem, chxTKNVThem, chxTKNVSua, chxTKNVXoa, jPTKNhanVien);
        convertDataToCheckbox(chucVu.getqLThe(), chxThe, chxTheXem, chxTheThem, chxTheSua, chxTheXoa, jPThe);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupGiaoDien = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtMaChucVu = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTenChucVu = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaMoTa = new javax.swing.JTextArea();
        jPanel21 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnThemChucVu = new javax.swing.JButton();
        jPanel22 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        chxThongKe = new javax.swing.JCheckBox();
        jPKhachHang = new javax.swing.JPanel();
        chxKHXoa = new javax.swing.JCheckBox();
        chxKHSua = new javax.swing.JCheckBox();
        chxKHThem = new javax.swing.JCheckBox();
        chxKHXem = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        chxKhachHang = new javax.swing.JCheckBox();
        jPanel13 = new javax.swing.JPanel();
        chxNhanVien = new javax.swing.JCheckBox();
        jPNhanVien = new javax.swing.JPanel();
        chxNVXoa = new javax.swing.JCheckBox();
        chxNVSua = new javax.swing.JCheckBox();
        chxNVThem = new javax.swing.JCheckBox();
        chxNVXem = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        chxTKKhachHang = new javax.swing.JCheckBox();
        jPTKKhachHang = new javax.swing.JPanel();
        chxTKKHXoa = new javax.swing.JCheckBox();
        chxTKKHSua = new javax.swing.JCheckBox();
        chxTKKHThem = new javax.swing.JCheckBox();
        chxTKKHXem = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        chxTKNhanVien = new javax.swing.JCheckBox();
        jPTKNhanVien = new javax.swing.JPanel();
        chxTKNVXoa = new javax.swing.JCheckBox();
        chxTKNVSua = new javax.swing.JCheckBox();
        chxTKNVThem = new javax.swing.JCheckBox();
        chxTKNVXem = new javax.swing.JCheckBox();
        jPanel16 = new javax.swing.JPanel();
        chxThe = new javax.swing.JCheckBox();
        jPThe = new javax.swing.JPanel();
        chxTheXoa = new javax.swing.JCheckBox();
        chxTheSua = new javax.swing.JCheckBox();
        chxTheThem = new javax.swing.JCheckBox();
        chxTheXem = new javax.swing.JCheckBox();
        jPanel20 = new javax.swing.JPanel();
        rdbGiaoDienAdmin = new javax.swing.JRadioButton();
        rdbGiaoDienNV = new javax.swing.JRadioButton();
        jPPhanQuyen = new javax.swing.JPanel();
        chxPhanQuyen = new javax.swing.JCheckBox();
        jPChucVu = new javax.swing.JPanel();
        chxThemChucVu = new javax.swing.JCheckBox();
        jPChoNhanVien = new javax.swing.JPanel();
        chxGiaoDich = new javax.swing.JCheckBox();
        chxVayVon = new javax.swing.JCheckBox();
        chxVayTinDung = new javax.swing.JCheckBox();
        chxGuiTietKiem = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Thêm chức vụ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel19.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setIcon(new FlatSVGIcon("quanlynganhang/icon/id_bankcard_label.svg")
        );
        jLabel7.setText("Mã chức vụ");

        txtMaChucVu.setEditable(false);
        txtMaChucVu.setEnabled(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMaChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setIcon(new FlatSVGIcon("quanlynganhang/icon/customer_label.svg")
        );
        jLabel5.setText("Tên chức vụ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 207, Short.MAX_VALUE))
                    .addComponent(txtTenChucVu))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setIcon(new FlatSVGIcon("quanlynganhang/icon/description_label.svg")
        );
        jLabel6.setText("Mô tả");

        txaMoTa.setColumns(20);
        txaMoTa.setRows(5);
        jScrollPane1.setViewportView(txaMoTa);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 601, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel23Layout.createSequentialGroup()
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap()))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 178, Short.MAX_VALUE)
            .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel23Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel19.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel19, java.awt.BorderLayout.PAGE_START);

        jPanel21.setLayout(new java.awt.BorderLayout());

        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        btnThemChucVu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemChucVu.setText("Thêm chức vụ");
        btnThemChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemChucVuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(204, Short.MAX_VALUE)
                .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnThemChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(182, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuy)
                    .addComponent(btnThemChucVu))
                .addContainerGap())
        );

        jPanel21.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel21, java.awt.BorderLayout.PAGE_END);

        jPanel18.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        chxThongKe.setText("Thống kê");
        chxThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxThongKeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxThongKe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxKHXoa.setText("Xóa");
        chxKHXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxKHXoaActionPerformed(evt);
            }
        });

        chxKHSua.setText("Sửa");
        chxKHSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxKHSuaActionPerformed(evt);
            }
        });

        chxKHThem.setText("Thêm");
        chxKHThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxKHThemActionPerformed(evt);
            }
        });

        chxKHXem.setText("Xem");
        chxKHXem.setEnabled(false);
        chxKHXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxKHXemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPKhachHangLayout = new javax.swing.GroupLayout(jPKhachHang);
        jPKhachHang.setLayout(jPKhachHangLayout);
        jPKhachHangLayout.setHorizontalGroup(
            jPKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPKhachHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chxKHXem, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxKHThem, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxKHSua, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxKHXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPKhachHangLayout.setVerticalGroup(
            jPKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chxKHXoa)
                    .addComponent(chxKHSua)
                    .addComponent(chxKHThem)
                    .addComponent(chxKHXem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxKhachHang.setText("Khách hàng");
        chxKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxKhachHang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxNhanVien.setText("Nhân viên");
        chxNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxNhanVien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxNVXoa.setText("Xóa");
        chxNVXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxNVXoaActionPerformed(evt);
            }
        });

        chxNVSua.setText("Sửa");
        chxNVSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxNVSuaActionPerformed(evt);
            }
        });

        chxNVThem.setText("Thêm");
        chxNVThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxNVThemActionPerformed(evt);
            }
        });

        chxNVXem.setText("Xem");
        chxNVXem.setEnabled(false);
        chxNVXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxNVXemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPNhanVienLayout = new javax.swing.GroupLayout(jPNhanVien);
        jPNhanVien.setLayout(jPNhanVienLayout);
        jPNhanVienLayout.setHorizontalGroup(
            jPNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNhanVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chxNVXem, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxNVThem, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxNVSua, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxNVXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPNhanVienLayout.setVerticalGroup(
            jPNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chxNVXoa)
                    .addComponent(chxNVSua)
                    .addComponent(chxNVThem)
                    .addComponent(chxNVXem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxTKKhachHang.setText("Tài khoản khách hàng");
        chxTKKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxTKKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxTKKhachHang)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxTKKHXoa.setText("Xóa");
        chxTKKHXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKKHXoaActionPerformed(evt);
            }
        });

        chxTKKHSua.setText("Sửa");
        chxTKKHSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKKHSuaActionPerformed(evt);
            }
        });

        chxTKKHThem.setText("Thêm");
        chxTKKHThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKKHThemActionPerformed(evt);
            }
        });

        chxTKKHXem.setText("Xem");
        chxTKKHXem.setEnabled(false);
        chxTKKHXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKKHXemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPTKKhachHangLayout = new javax.swing.GroupLayout(jPTKKhachHang);
        jPTKKhachHang.setLayout(jPTKKhachHangLayout);
        jPTKKhachHangLayout.setHorizontalGroup(
            jPTKKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKKhachHangLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chxTKKHXem, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTKKHThem, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTKKHSua, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTKKHXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPTKKhachHangLayout.setVerticalGroup(
            jPTKKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTKKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chxTKKHXoa)
                    .addComponent(chxTKKHSua)
                    .addComponent(chxTKKHThem)
                    .addComponent(chxTKKHXem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxTKNhanVien.setText("Tài khoản nhân viên");
        chxTKNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxTKNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxTKNhanVien)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxTKNVXoa.setText("Xóa");
        chxTKNVXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKNVXoaActionPerformed(evt);
            }
        });

        chxTKNVSua.setText("Sửa");
        chxTKNVSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKNVSuaActionPerformed(evt);
            }
        });

        chxTKNVThem.setText("Thêm");
        chxTKNVThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKNVThemActionPerformed(evt);
            }
        });

        chxTKNVXem.setText("Xem");
        chxTKNVXem.setEnabled(false);
        chxTKNVXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTKNVXemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPTKNhanVienLayout = new javax.swing.GroupLayout(jPTKNhanVien);
        jPTKNhanVien.setLayout(jPTKNhanVienLayout);
        jPTKNhanVienLayout.setHorizontalGroup(
            jPTKNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKNhanVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chxTKNVXem, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTKNVThem, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTKNVSua, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTKNVXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPTKNhanVienLayout.setVerticalGroup(
            jPTKNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTKNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chxTKNVXoa)
                    .addComponent(chxTKNVSua)
                    .addComponent(chxTKNVThem)
                    .addComponent(chxTKNVXem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxThe.setText("Thẻ");
        chxThe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTheActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxThe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxThe)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxTheXoa.setText("Xóa");
        chxTheXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTheXoaActionPerformed(evt);
            }
        });

        chxTheSua.setText("Sửa");
        chxTheSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTheSuaActionPerformed(evt);
            }
        });

        chxTheThem.setText("Thêm");
        chxTheThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTheThemActionPerformed(evt);
            }
        });

        chxTheXem.setText("Xem");
        chxTheXem.setEnabled(false);
        chxTheXem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxTheXemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPTheLayout = new javax.swing.GroupLayout(jPThe);
        jPThe.setLayout(jPTheLayout);
        jPTheLayout.setHorizontalGroup(
            jPTheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTheLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chxTheXem, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTheThem, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTheSua, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chxTheXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPTheLayout.setVerticalGroup(
            jPTheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTheLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTheLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chxTheXoa)
                    .addComponent(chxTheSua)
                    .addComponent(chxTheThem)
                    .addComponent(chxTheXem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGroupGiaoDien.add(rdbGiaoDienAdmin);
        rdbGiaoDienAdmin.setText("Sử dụng giao diện của quản trị viên");
        rdbGiaoDienAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbGiaoDienAdminActionPerformed(evt);
            }
        });

        btnGroupGiaoDien.add(rdbGiaoDienNV);
        rdbGiaoDienNV.setSelected(true);
        rdbGiaoDienNV.setText("Sử dụng giao diện của nhân viên");
        rdbGiaoDienNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbGiaoDienNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdbGiaoDienNV, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdbGiaoDienAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbGiaoDienAdmin)
                    .addComponent(rdbGiaoDienNV))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxPhanQuyen.setText("Phân quyền cho nhân viên");
        chxPhanQuyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxPhanQuyenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPPhanQuyenLayout = new javax.swing.GroupLayout(jPPhanQuyen);
        jPPhanQuyen.setLayout(jPPhanQuyenLayout);
        jPPhanQuyenLayout.setHorizontalGroup(
            jPPhanQuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhanQuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxPhanQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(373, 373, 373))
        );
        jPPhanQuyenLayout.setVerticalGroup(
            jPPhanQuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhanQuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxPhanQuyen)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxThemChucVu.setText("Thêm chức vụ");

        javax.swing.GroupLayout jPChucVuLayout = new javax.swing.GroupLayout(jPChucVu);
        jPChucVu.setLayout(jPChucVuLayout);
        jPChucVuLayout.setHorizontalGroup(
            jPChucVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPChucVuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxThemChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(438, 438, 438))
        );
        jPChucVuLayout.setVerticalGroup(
            jPChucVuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPChucVuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chxThemChucVu)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        chxGiaoDich.setText("Giao dịch");
        chxGiaoDich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxGiaoDichActionPerformed(evt);
            }
        });

        chxVayVon.setText("Vay vốn");

        chxVayTinDung.setText("Vay tín dụng");

        chxGuiTietKiem.setText("Gửi tiết kiệm");

        javax.swing.GroupLayout jPChoNhanVienLayout = new javax.swing.GroupLayout(jPChoNhanVien);
        jPChoNhanVien.setLayout(jPChoNhanVienLayout);
        jPChoNhanVienLayout.setHorizontalGroup(
            jPChoNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPChoNhanVienLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(chxGiaoDich, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chxGuiTietKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chxVayVon, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chxVayTinDung, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPChoNhanVienLayout.setVerticalGroup(
            jPChoNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPChoNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPChoNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chxGiaoDich)
                    .addComponent(chxVayVon)
                    .addComponent(chxVayTinDung)
                    .addComponent(chxGuiTietKiem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPPhanQuyen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPChucVu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel26Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPTKKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPTKNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPThe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel26Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jPChoNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPChoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPTKKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPTKNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPThe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPPhanQuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel26);

        jPanel18.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.add(jPanel22, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void chxGiaoDichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxGiaoDichActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxGiaoDichActionPerformed

    private void chxPhanQuyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxPhanQuyenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxPhanQuyenActionPerformed

    private void rdbGiaoDienNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbGiaoDienNVActionPerformed
        if (rdbGiaoDienNV.isSelected()) {
            kiemTraCheckbox(false);
        }
    }//GEN-LAST:event_rdbGiaoDienNVActionPerformed

    private void rdbGiaoDienAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbGiaoDienAdminActionPerformed
        if (rdbGiaoDienAdmin.isSelected()) {
            kiemTraCheckbox(true);
        }
    }//GEN-LAST:event_rdbGiaoDienAdminActionPerformed

    private void chxTheXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTheXemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTheXemActionPerformed

    private void chxTheThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTheThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTheThemActionPerformed

    private void chxTheSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTheSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTheSuaActionPerformed

    private void chxTheXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTheXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTheXoaActionPerformed

    private void chxTheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTheActionPerformed
        if (chxThe.isSelected()) {
            jPThe.setVisible(true);
            chxTheXem.setSelected(true);
        } else {
            chxTheXem.setSelected(false);
            chxTheThem.setSelected(false);
            chxTheSua.setSelected(false);
            chxTheXoa.setSelected(false);
            jPThe.setVisible(false);
        }
    }//GEN-LAST:event_chxTheActionPerformed

    private void chxTKNVXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKNVXemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTKNVXemActionPerformed

    private void chxTKNVThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKNVThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTKNVThemActionPerformed

    private void chxTKNVSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKNVSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTKNVSuaActionPerformed

    private void chxTKNVXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKNVXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTKNVXoaActionPerformed

    private void chxTKNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKNhanVienActionPerformed
        if (chxTKNhanVien.isSelected()) {
            jPTKNhanVien.setVisible(true);
            chxTKNVXem.setSelected(true);
        } else {
            chxTKNVXem.setSelected(false);
            chxTKNVThem.setSelected(false);
            chxTKNVSua.setSelected(false);
            chxTKNVXoa.setSelected(false);
            jPTKNhanVien.setVisible(false);
        }
    }//GEN-LAST:event_chxTKNhanVienActionPerformed

    private void chxTKKHXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKKHXemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTKKHXemActionPerformed

    private void chxTKKHThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKKHThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTKKHThemActionPerformed

    private void chxTKKHSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKKHSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTKKHSuaActionPerformed

    private void chxTKKHXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKKHXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxTKKHXoaActionPerformed

    private void chxTKKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxTKKhachHangActionPerformed
        if (chxTKKhachHang.isSelected()) {
            jPTKKhachHang.setVisible(true);
            chxTKKHXem.setSelected(true);
        } else {
            chxTKKHXem.setSelected(false);
            chxTKKHThem.setSelected(false);
            chxTKKHSua.setSelected(false);
            chxTKKHXoa.setSelected(false);
            jPTKKhachHang.setVisible(false);
        }
    }//GEN-LAST:event_chxTKKhachHangActionPerformed

    private void chxNVXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxNVXemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxNVXemActionPerformed

    private void chxNVThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxNVThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxNVThemActionPerformed

    private void chxNVSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxNVSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxNVSuaActionPerformed

    private void chxNVXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxNVXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxNVXoaActionPerformed

    private void chxNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxNhanVienActionPerformed
        if (chxNhanVien.isSelected()) {
            jPNhanVien.setVisible(true);
            chxNVXem.setSelected(true);
        } else {
            chxNVXem.setSelected(false);
            chxNVThem.setSelected(false);
            chxNVSua.setSelected(false);
            chxNVXoa.setSelected(false);
            jPNhanVien.setVisible(false);
        }
    }//GEN-LAST:event_chxNhanVienActionPerformed

    private void chxKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxKhachHangActionPerformed
        if (chxKhachHang.isSelected()) {
            jPKhachHang.setVisible(true);
            chxKHXem.setSelected(true);
        } else {
            chxKHXem.setSelected(false);
            chxKHThem.setSelected(false);
            chxKHSua.setSelected(false);
            chxKHXoa.setSelected(false);
            jPKhachHang.setVisible(false);
        }
    }//GEN-LAST:event_chxKhachHangActionPerformed

    private void chxKHXemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxKHXemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxKHXemActionPerformed

    private void chxKHThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxKHThemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxKHThemActionPerformed

    private void chxKHSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxKHSuaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxKHSuaActionPerformed

    private void chxKHXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxKHXoaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxKHXoaActionPerformed

    private void chxThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxThongKeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chxThongKeActionPerformed

    private void btnThemChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemChucVuActionPerformed
        String tenChucVu = txtTenChucVu.getText();
        String moTa = txaMoTa.getText();
        int maChucVu = 0;
        if (InputValidation.kiemTraTen(tenChucVu)) {
            if (btnThemChucVu.getText().equals("Cập nhật")) {
                if (MessageBox.showConfirmMessage(null, "Bạn có muốn sửa chức vụ này?") == JOptionPane.YES_OPTION) {
                    maChucVu = Integer.parseInt(txtMaChucVu.getText());

                    if (kiemTraQuyen(maChucVu, tenChucVu, moTa, true)) {
                        MessageBox.showInformationMessage(null, "", "Cập nhật chức vụ thành công");
                        btnHuyActionPerformed(null);
                    } else {
                        MessageBox.showErrorMessage(null, "Chức vụ này đã tồn tại!");
                    }
                } else {
                    return;
                }

            } else {
                if (kiemTraQuyen(maChucVu, tenChucVu, moTa, false)) {
                    MessageBox.showInformationMessage(null, "", "Thêm chức vụ mới thành công");
                    btnHuyActionPerformed(null);
                } else {
                    MessageBox.showErrorMessage(null, "Tên chức vụ đã tồn tại!");
                }
            }

        } else {
            MessageBox.showErrorMessage(null, "Tên chức vụ không hợp lệ!");
        }
    }//GEN-LAST:event_btnThemChucVuActionPerformed

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
            java.util.logging.Logger.getLogger(JDialogThemChucVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogThemChucVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogThemChucVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogThemChucVu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogThemChucVu dialog = new JDialogThemChucVu(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupGiaoDien;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnThemChucVu;
    private javax.swing.JCheckBox chxGiaoDich;
    private javax.swing.JCheckBox chxGuiTietKiem;
    private javax.swing.JCheckBox chxKHSua;
    private javax.swing.JCheckBox chxKHThem;
    private javax.swing.JCheckBox chxKHXem;
    private javax.swing.JCheckBox chxKHXoa;
    private javax.swing.JCheckBox chxKhachHang;
    private javax.swing.JCheckBox chxNVSua;
    private javax.swing.JCheckBox chxNVThem;
    private javax.swing.JCheckBox chxNVXem;
    private javax.swing.JCheckBox chxNVXoa;
    private javax.swing.JCheckBox chxNhanVien;
    private javax.swing.JCheckBox chxPhanQuyen;
    private javax.swing.JCheckBox chxTKKHSua;
    private javax.swing.JCheckBox chxTKKHThem;
    private javax.swing.JCheckBox chxTKKHXem;
    private javax.swing.JCheckBox chxTKKHXoa;
    private javax.swing.JCheckBox chxTKKhachHang;
    private javax.swing.JCheckBox chxTKNVSua;
    private javax.swing.JCheckBox chxTKNVThem;
    private javax.swing.JCheckBox chxTKNVXem;
    private javax.swing.JCheckBox chxTKNVXoa;
    private javax.swing.JCheckBox chxTKNhanVien;
    private javax.swing.JCheckBox chxThe;
    private javax.swing.JCheckBox chxTheSua;
    private javax.swing.JCheckBox chxTheThem;
    private javax.swing.JCheckBox chxTheXem;
    private javax.swing.JCheckBox chxTheXoa;
    private javax.swing.JCheckBox chxThemChucVu;
    private javax.swing.JCheckBox chxThongKe;
    private javax.swing.JCheckBox chxVayTinDung;
    private javax.swing.JCheckBox chxVayVon;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPChoNhanVien;
    private javax.swing.JPanel jPChucVu;
    private javax.swing.JPanel jPKhachHang;
    private javax.swing.JPanel jPNhanVien;
    private javax.swing.JPanel jPPhanQuyen;
    private javax.swing.JPanel jPTKKhachHang;
    private javax.swing.JPanel jPTKNhanVien;
    private javax.swing.JPanel jPThe;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JRadioButton rdbGiaoDienAdmin;
    private javax.swing.JRadioButton rdbGiaoDienNV;
    private javax.swing.JTextArea txaMoTa;
    private javax.swing.JTextField txtMaChucVu;
    private javax.swing.JTextField txtTenChucVu;
    // End of variables declaration//GEN-END:variables
}
