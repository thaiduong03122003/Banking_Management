/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.LoaiTaiKhoanBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TrangThaiBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JFrameBoLocDSTKKH extends javax.swing.JFrame {

    private FormDSTaiKhoanKH formDSTaiKhoanKH;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private KhachHangBUS khachHangBUS;
    private LoaiTaiKhoanBUS loaiTaiKhoanBUS;
    private TrangThaiBUS trangThaiBUS;
    private Integer maKhachHang, maTrangThai, maLoaiTaiKhoan;
    private boolean isFiltered;
    private final String danhMuc = "Account and Card";

    public JFrameBoLocDSTKKH(FormDSTaiKhoanKH formDSTaiKhoanKH) {
        this.formDSTaiKhoanKH = formDSTaiKhoanKH;
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        khachHangBUS = new KhachHangBUS();
        loaiTaiKhoanBUS = new LoaiTaiKhoanBUS();
        trangThaiBUS = new TrangThaiBUS();
        maKhachHang = 0;
        maLoaiTaiKhoan = 0;
        maTrangThai = 0;
        isFiltered = false;

        initComponents();
        customUI();
        loadKhachHang();
        loadLoaiTaiKhoan();
        loadTrangThai();
    }

    private void customUI() {
        txtNgayBatDau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Bắt đầu");
        txtNgayKetThuc.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Kết thúc");

        txtNgayBatDau.setVisible(false);
        txtNgayKetThuc.setVisible(false);

        cbxKH.setEnabled(false);
        cbxTrangThai3.setEnabled(false);
        cbxLoaiTaiKhoan.setEnabled(false);
    }

    private void loadKhachHang() {
        try {
            List<KhachHangDTO> listKH = khachHangBUS.getDSKhachHang(0);

            for (KhachHangDTO khachHang : listKH) {
                cbxKH.addItem(khachHang.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLoaiTaiKhoan() {
        Map<Integer, String> map = new HashMap<>();
        map = loaiTaiKhoanBUS.convertListLoaiTaiKhoanToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn loại tài khoản-");

        for (String tenLoaiTaiKhoan : map.values()) {
            model.addElement(tenLoaiTaiKhoan);
        }

        cbxLoaiTaiKhoan.setModel(model);
    }

    private void loadTrangThai() {
        Map<Integer, String> map = new HashMap<>();
        map = trangThaiBUS.convertListTrangThaiToMap(danhMuc);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn trạng thái-");

        for (String tenTrangThai : map.values()) {
            model.addElement(tenTrangThai);
        }

        cbxTrangThai3.setModel(model);
    }

    private void checkFilterStatus() {
        if (rdbAllNgayTao.isSelected() && rdbAllKH.isSelected() && rdbAllLoaiTaiKhoan.isSelected() && rdbAllTrangThai1.isSelected()) {
            isFiltered = false;
        } else {
            isFiltered = true;
        }
    }

    private List<TaiKhoanKHDTO> chonTieuChiLoc() {
        FormatDate fDate = new FormatDate();

        Date dateFrom, dateTo;
        if (rdbAllNgayTao.isSelected()) {
            dateFrom = null;
            dateTo = null;
        } else {
            if (!txtNgayBatDau.getText().trim().isEmpty() && !txtNgayKetThuc.getText().trim().isEmpty() && InputValidation.kiemTraNgay(txtNgayBatDau.getText()) && InputValidation.kiemTraNgay(txtNgayKetThuc.getText())) {
                dateFrom = (Date) fDate.toDate(txtNgayBatDau.getText());
                dateTo = (Date) fDate.toDate(txtNgayKetThuc.getText());

                if (!InputValidation.kiemTraTrinhTuNhapNgay(dateFrom, dateTo)) {
                    MessageBox.showErrorMessage(null, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc!");
                    return null;
                }

            } else if (!txtNgayBatDau.getText().trim().isEmpty() && InputValidation.kiemTraNgay(txtNgayBatDau.getText().trim())) {
                dateFrom = (Date) fDate.toDate(txtNgayBatDau.getText());
                dateTo = null;
            } else if (!txtNgayKetThuc.getText().trim().isEmpty() && InputValidation.kiemTraNgay(txtNgayKetThuc.getText().trim())) {
                dateFrom = null;
                dateTo = (Date) fDate.toDate(txtNgayKetThuc.getText());
            } else {
                MessageBox.showErrorMessage(null, "Ngày nhập vào không hợp lệ!, vui lòng nhập đúng định dạng dd/MM/yyyy");
                return null;
            }
        }

        int idKhachHang;
        if (rdbAllKH.isSelected()) {
            idKhachHang = 0;
        } else {
            if (cbxKH.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn khách hàng!");
                return null;
            } else {
                idKhachHang = maKhachHang;
            }
        }

        int idTrangThai;
        if (rdbAllTrangThai1.isSelected()) {
            idTrangThai = 0;
        } else {
            if (cbxTrangThai3.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn trạng thái tài khoản!");
                return null;
            } else {
                idTrangThai = maTrangThai;
            }
        }

        int idLoaiTaiKhoan;
        if (rdbAllLoaiTaiKhoan.isSelected()) {
            idLoaiTaiKhoan = 0;
        } else {
            if (cbxLoaiTaiKhoan.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn loại tài khoản!");
                return null;
            } else {
                idLoaiTaiKhoan = maLoaiTaiKhoan;
            }
        }

        return taiKhoanKHBUS.locTaiKhoanKH(dateFrom, dateTo, idKhachHang, idLoaiTaiKhoan, idTrangThai);
    }

    public List<TaiKhoanKHDTO> listTKKHBoLoc() throws Exception {
        return chonTieuChiLoc();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupNgayTao = new javax.swing.ButtonGroup();
        btnGroupLoaiTK = new javax.swing.ButtonGroup();
        btnGroupTrangThai = new javax.swing.ButtonGroup();
        btnGroupKH = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtNgayBatDau = new javax.swing.JTextField();
        txtNgayKetThuc = new javax.swing.JTextField();
        rdbAllNgayTao = new javax.swing.JRadioButton();
        rdbChonNgayTao = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        rdbAllTrangThai1 = new javax.swing.JRadioButton();
        rdbChonTrangThai1 = new javax.swing.JRadioButton();
        cbxTrangThai3 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnLocTKKH = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        rdbAllKH = new javax.swing.JRadioButton();
        rdbChonKH = new javax.swing.JRadioButton();
        cbxKH = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        rdbAllLoaiTaiKhoan = new javax.swing.JRadioButton();
        rdbChonLoaiTaiKhoan = new javax.swing.JRadioButton();
        cbxLoaiTaiKhoan = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Bộ lọc danh sách");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Ngày tạo");

        txtNgayBatDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayBatDauActionPerformed(evt);
            }
        });

        btnGroupNgayTao.add(rdbAllNgayTao);
        rdbAllNgayTao.setSelected(true);
        rdbAllNgayTao.setText("Tất cả");
        rdbAllNgayTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllNgayTaoActionPerformed(evt);
            }
        });

        btnGroupNgayTao.add(rdbChonNgayTao);
        rdbChonNgayTao.setText("Tùy chỉnh");
        rdbChonNgayTao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonNgayTaoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addComponent(txtNgayKetThuc)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbAllNgayTao)
                            .addComponent(rdbChonNgayTao))
                        .addGap(20, 20, 20)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllNgayTao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonNgayTao)
                .addGap(18, 18, 18)
                .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Trạng thái");

        btnGroupLoaiTK.add(rdbAllTrangThai1);
        rdbAllTrangThai1.setSelected(true);
        rdbAllTrangThai1.setText("Tất cả");
        rdbAllTrangThai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllTrangThai1ActionPerformed(evt);
            }
        });

        btnGroupLoaiTK.add(rdbChonTrangThai1);
        rdbChonTrangThai1.setText("Tùy chỉnh");
        rdbChonTrangThai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonTrangThai1ActionPerformed(evt);
            }
        });

        cbxTrangThai3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn trạng thái-" }));
        cbxTrangThai3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTrangThai3ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxTrangThai3, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllTrangThai1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonTrangThai1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllTrangThai1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonTrangThai1)
                .addGap(18, 18, 18)
                .addComponent(cbxTrangThai3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel6)
                .addGap(13, 13, 13))
        );

        btnClose.setText("Hủy");
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnLocTKKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLocTKKH.setText("Lọc");
        btnLocTKKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocTKKHActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Khách hàng");

        btnGroupKH.add(rdbAllKH);
        rdbAllKH.setSelected(true);
        rdbAllKH.setText("Tất cả");
        rdbAllKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllKHActionPerformed(evt);
            }
        });

        btnGroupKH.add(rdbChonKH);
        rdbChonKH.setText("Tùy chỉnh");
        rdbChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonKHActionPerformed(evt);
            }
        });

        cbxKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn khách hàng-" }));
        cbxKH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxKHItemStateChanged(evt);
            }
        });
        cbxKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxKH, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonKH)
                .addGap(18, 18, 18)
                .addComponent(cbxKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel10)
                .addGap(13, 13, 13))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Loại tài khoản");

        btnGroupTrangThai.add(rdbAllLoaiTaiKhoan);
        rdbAllLoaiTaiKhoan.setSelected(true);
        rdbAllLoaiTaiKhoan.setText("Tất cả");
        rdbAllLoaiTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllLoaiTaiKhoanActionPerformed(evt);
            }
        });

        btnGroupTrangThai.add(rdbChonLoaiTaiKhoan);
        rdbChonLoaiTaiKhoan.setText("Tùy chỉnh");
        rdbChonLoaiTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonLoaiTaiKhoanActionPerformed(evt);
            }
        });

        cbxLoaiTaiKhoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn loại tài khoản-" }));
        cbxLoaiTaiKhoan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxLoaiTaiKhoanItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxLoaiTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllLoaiTaiKhoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonLoaiTaiKhoan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllLoaiTaiKhoan)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonLoaiTaiKhoan)
                .addGap(18, 18, 18)
                .addComponent(cbxLoaiTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel12)
                .addGap(13, 13, 13))
        );

        btnReset.setText("Đặt lại");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(245, 245, 245))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(btnClose)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addComponent(btnLocTKKH))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLocTKKH)
                            .addComponent(btnReset)
                            .addComponent(btnClose)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rdbChonNgayTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonNgayTaoActionPerformed
        if (rdbChonNgayTao.isSelected()) {
            txtNgayBatDau.setVisible(true);
            txtNgayKetThuc.setVisible(true);
            revalidate();
        }
    }//GEN-LAST:event_rdbChonNgayTaoActionPerformed

    private void rdbAllNgayTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllNgayTaoActionPerformed
        if (rdbAllNgayTao.isSelected()) {
            txtNgayBatDau.setText("");
            txtNgayKetThuc.setText("");
            txtNgayBatDau.setVisible(false);
            txtNgayKetThuc.setVisible(false);
            revalidate();
        }
    }//GEN-LAST:event_rdbAllNgayTaoActionPerformed

    private void rdbAllTrangThai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllTrangThai1ActionPerformed
        if (rdbAllTrangThai1.isSelected()) {
            cbxTrangThai3.setSelectedIndex(0);
            cbxTrangThai3.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllTrangThai1ActionPerformed

    private void rdbChonTrangThai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonTrangThai1ActionPerformed
        if (rdbChonTrangThai1.isSelected()) {
            cbxTrangThai3.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonTrangThai1ActionPerformed

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnLocTKKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocTKKHActionPerformed
        List<TaiKhoanKHDTO> listLocTKKH = chonTieuChiLoc();

        if (listLocTKKH != null) {
            checkFilterStatus();

            if (listLocTKKH.isEmpty()) {
                MessageBox.showErrorMessage(null, "Không có thông tin khách hàng nào phù hợp!");
                return;
            }

            formDSTaiKhoanKH.loadDSTaiKhoanKH(isFiltered, false, listLocTKKH);
            this.dispose();
        }
    }//GEN-LAST:event_btnLocTKKHActionPerformed

    private void rdbAllKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllKHActionPerformed
        if (rdbAllKH.isSelected()) {
            cbxKH.setSelectedIndex(0);
            cbxKH.setEnabled(false);
        } else {
            cbxKH.setEnabled(true);
        }
    }//GEN-LAST:event_rdbAllKHActionPerformed

    private void rdbChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonKHActionPerformed
        if (rdbChonKH.isSelected()) {
            cbxKH.setEnabled(true);
        } else {
            cbxKH.setEnabled(false);
        }
    }//GEN-LAST:event_rdbChonKHActionPerformed

    private void rdbAllLoaiTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllLoaiTaiKhoanActionPerformed
        if (rdbAllLoaiTaiKhoan.isSelected()) {
            cbxLoaiTaiKhoan.setSelectedIndex(0);
            cbxLoaiTaiKhoan.setEnabled(false);
        } else {
            cbxLoaiTaiKhoan.setEnabled(true);
        }
    }//GEN-LAST:event_rdbAllLoaiTaiKhoanActionPerformed

    private void rdbChonLoaiTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonLoaiTaiKhoanActionPerformed
        if (rdbChonLoaiTaiKhoan.isSelected()) {
            cbxLoaiTaiKhoan.setEnabled(true);
        } else {
            cbxLoaiTaiKhoan.setEnabled(false);
        }
    }//GEN-LAST:event_rdbChonLoaiTaiKhoanActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        rdbAllNgayTao.setSelected(true);
        rdbAllKH.setSelected(true);
        rdbAllLoaiTaiKhoan.setSelected(true);
        rdbAllTrangThai1.setSelected(true);
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtNgayBatDau.setVisible(false);
        txtNgayKetThuc.setVisible(false);
        cbxKH.setSelectedIndex(0);
        cbxLoaiTaiKhoan.setSelectedIndex(0);
        cbxTrangThai3.setSelectedIndex(0);
        cbxKH.setEnabled(false);
        cbxLoaiTaiKhoan.setEnabled(false);
        cbxTrangThai3.setEnabled(false);

        btnLocTKKHActionPerformed(null);
    }//GEN-LAST:event_btnResetActionPerformed

    private void cbxKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxKHActionPerformed

    }//GEN-LAST:event_cbxKHActionPerformed

    private void cbxTrangThai3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTrangThai3ItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String tenTrangThai = (String) cbxTrangThai3.getSelectedItem();
            if (tenTrangThai.equals("-Chọn trạng thái-")) {
                maTrangThai = 0;
            } else {
                maTrangThai = trangThaiBUS.getIdFromTenTrangThai(tenTrangThai, danhMuc);

                if (maTrangThai == null) {
                    MessageBox.showErrorMessage(null, "Lấy id của trạng thái thất bại!");
                }
            }
        }
    }//GEN-LAST:event_cbxTrangThai3ItemStateChanged

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void txtNgayBatDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayBatDauActionPerformed

    }//GEN-LAST:event_txtNgayBatDauActionPerformed

    private void cbxLoaiTaiKhoanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxLoaiTaiKhoanItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String tenLoaiTaiKhoan = (String) cbxLoaiTaiKhoan.getSelectedItem();
            if (tenLoaiTaiKhoan.equals("-Chọn loại tài khoản-")) {
                maLoaiTaiKhoan = 0;
            } else {
                maLoaiTaiKhoan = loaiTaiKhoanBUS.getIdFromTenLoaiTaiKhoan(tenLoaiTaiKhoan);

                if (maLoaiTaiKhoan == null) {
                    MessageBox.showErrorMessage(null, "Lấy id của loại tài khoản thất bại!");
                }
            }
        }
    }//GEN-LAST:event_cbxLoaiTaiKhoanItemStateChanged

    private void cbxKHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxKHItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String khachHang = (String) cbxKH.getSelectedItem();
            if (khachHang.equals("-Chọn khách hàng-")) {
                maKhachHang = 0;
            } else {
                String[] parts = khachHang.split(" - ");
                String maKhachHangString = parts[0];
                maKhachHang = Integer.parseInt(maKhachHangString);
            }
        }
    }//GEN-LAST:event_cbxKHItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.ButtonGroup btnGroupKH;
    private javax.swing.ButtonGroup btnGroupLoaiTK;
    private javax.swing.ButtonGroup btnGroupNgayTao;
    private javax.swing.ButtonGroup btnGroupTrangThai;
    private javax.swing.JButton btnLocTKKH;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbxKH;
    private javax.swing.JComboBox<String> cbxLoaiTaiKhoan;
    private javax.swing.JComboBox<String> cbxTrangThai3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JRadioButton rdbAllKH;
    private javax.swing.JRadioButton rdbAllLoaiTaiKhoan;
    private javax.swing.JRadioButton rdbAllNgayTao;
    private javax.swing.JRadioButton rdbAllTrangThai1;
    private javax.swing.JRadioButton rdbChonKH;
    private javax.swing.JRadioButton rdbChonLoaiTaiKhoan;
    private javax.swing.JRadioButton rdbChonNgayTao;
    private javax.swing.JRadioButton rdbChonTrangThai1;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    // End of variables declaration//GEN-END:variables
}
