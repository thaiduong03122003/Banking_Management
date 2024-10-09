package quanlynganhang.GUI.adminUI;

import quanlynganhang.GUI.*;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.BUS.TaiKhoanNVBUS;
import quanlynganhang.BUS.TrangThaiBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JFrameBoLocDSTKNV extends javax.swing.JFrame {

    private FormatDate fDate;
    private FormDSTaiKhoanNV formDSTaiKhoanNV;
    private TaiKhoanNVBUS taiKhoanNVBUS;
    private NhanVienBUS nhanVienBUS;
    private TrangThaiBUS trangThaiBUS;
    private Integer maNhanVien, maTrangThai;
    private int maTinhTrang;
    private boolean isFiltered;
    private final String danhMuc = "Account and Card";

    public JFrameBoLocDSTKNV(FormDSTaiKhoanNV formDSTaiKhoanNV) {
        this.formDSTaiKhoanNV = formDSTaiKhoanNV;
        fDate = new FormatDate();
        taiKhoanNVBUS = new TaiKhoanNVBUS();
        nhanVienBUS = new NhanVienBUS();
        trangThaiBUS = new TrangThaiBUS();
        maNhanVien = 0;
        isFiltered = false;

        initComponents();
        customUI();
        loadNhanVien();
        loadTrangThai();
    }

    private void customUI() {
        txtNgayBatDau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Bắt đầu");
        txtNgayKetThuc.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Kết thúc");

        txtNgayBatDau.setVisible(false);
        txtNgayKetThuc.setVisible(false);

        cbxNV.setEnabled(false);
        cbxTrangThai.setEnabled(false);
        cbxTinhTrang.setEnabled(false);
    }

    private void loadNhanVien() {
        try {
            List<NhanVienDTO> listNV = nhanVienBUS.getDSNhanVien(0);

            for (NhanVienDTO nhanVien : listNV) {
                cbxNV.addItem(nhanVien.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTrangThai() {
        Map<Integer, String> map = new HashMap<>();
        map = trangThaiBUS.convertListTrangThaiToMap(danhMuc);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn trạng thái-");

        for (String tenTrangThai : map.values()) {
            model.addElement(tenTrangThai);
        }

        cbxTrangThai.setModel(model);
    }

    private void checkFilterStatus() {
        if (rdbAllNgayTao.isSelected() && rdbAllNV.isSelected() && rdbAllTrangThai.isSelected() && rdbAllTinhTrang.isSelected()) {
            isFiltered = false;
        } else {
            isFiltered = true;
        }
    }

    private List<TaiKhoanNVDTO> chonTieuChiLoc() {
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

        int idNhanVien;
        if (rdbAllNV.isSelected()) {
            idNhanVien = 0;
        } else {
            if (cbxNV.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên!");
                return null;
            } else {
                idNhanVien = maNhanVien;
            }
        }

        int idTrangThai;
        if (rdbAllTrangThai.isSelected()) {
            idTrangThai = 0;
        } else {
            if (cbxTrangThai.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn trạng thái tài khoản!");
                return null;
            } else {
                idTrangThai = maTrangThai;
            }
        }

        int idTinhTrang;
        if (rdbAllTinhTrang.isSelected()) {
            idTinhTrang = 0;
        } else {
            if (cbxTinhTrang.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn tình trạng đăng nhập!");
                return null;
            } else {
                idTinhTrang = maTinhTrang;
            }
        }

        return taiKhoanNVBUS.locTaiKhoanNV(dateFrom, dateTo, idNhanVien, idTrangThai, idTinhTrang);
    }

    public List<TaiKhoanNVDTO> listNVBoLoc() {
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
        btnGroupTinhTrang = new javax.swing.ButtonGroup();
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
        rdbAllNV = new javax.swing.JRadioButton();
        rdbChonNV = new javax.swing.JRadioButton();
        cbxNV = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        btnLocTKNV = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        rdbAllTrangThai = new javax.swing.JRadioButton();
        rdbChonTrangThai = new javax.swing.JRadioButton();
        cbxTrangThai = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        rdbAllTinhTrang = new javax.swing.JRadioButton();
        rdbChonTinhTrang = new javax.swing.JRadioButton();
        cbxTinhTrang = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Bộ lọc danh sách");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Ngày tạo");

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
        jLabel4.setText("Mã nhân viên");

        btnGroupLoaiTK.add(rdbAllNV);
        rdbAllNV.setSelected(true);
        rdbAllNV.setText("Tất cả");
        rdbAllNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllNVActionPerformed(evt);
            }
        });

        btnGroupLoaiTK.add(rdbChonNV);
        rdbChonNV.setText("Tùy chỉnh");
        rdbChonNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonNVActionPerformed(evt);
            }
        });

        cbxNV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn nhân viên" }));
        cbxNV.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxNVItemStateChanged(evt);
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
                            .addComponent(cbxNV, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
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
                .addComponent(rdbAllNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonNV)
                .addGap(18, 18, 18)
                .addComponent(cbxNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        btnLocTKNV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLocTKNV.setText("Lọc");
        btnLocTKNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocTKNVActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Trạng thái");

        btnGroupTrangThai.add(rdbAllTrangThai);
        rdbAllTrangThai.setSelected(true);
        rdbAllTrangThai.setText("Tất cả");
        rdbAllTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllTrangThaiActionPerformed(evt);
            }
        });

        btnGroupTrangThai.add(rdbChonTrangThai);
        rdbChonTrangThai.setText("Tùy chỉnh");
        rdbChonTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonTrangThaiActionPerformed(evt);
            }
        });

        cbxTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn trạng thái", "Item 2", "Item 3", "Item 4" }));
        cbxTrangThai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTrangThaiItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllTrangThai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonTrangThai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllTrangThai)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonTrangThai)
                .addGap(18, 18, 18)
                .addComponent(cbxTrangThai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel8)
                .addGap(13, 13, 13))
        );

        btnReset.setText("Đặt lại");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Tình trạng đăng nhập");

        btnGroupTinhTrang.add(rdbAllTinhTrang);
        rdbAllTinhTrang.setSelected(true);
        rdbAllTinhTrang.setText("Tất cả");
        rdbAllTinhTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllTinhTrangActionPerformed(evt);
            }
        });

        btnGroupTinhTrang.add(rdbChonTinhTrang);
        rdbChonTinhTrang.setText("Tùy chỉnh");
        rdbChonTinhTrang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonTinhTrangActionPerformed(evt);
            }
        });

        cbxTinhTrang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn tình trạng", "<html><p style='color:rgb(0, 255, 13);'>Đang đăng nhập</p></html>", "Đã đăng xuất", "<html><p style='color:rgb(255, 0, 0);'>Chưa từng đăng nhập</p></html>" }));
        cbxTinhTrang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTinhTrangItemStateChanged(evt);
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
                            .addComponent(cbxTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllTinhTrang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonTinhTrang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
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
                .addComponent(rdbAllTinhTrang)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonTinhTrang)
                .addGap(18, 18, 18)
                .addComponent(cbxTinhTrang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel10)
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(197, 197, 197)
                        .addComponent(btnClose)
                        .addGap(18, 18, 18)
                        .addComponent(btnReset)
                        .addGap(18, 18, 18)
                        .addComponent(btnLocTKNV)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnReset)
                    .addComponent(btnLocTKNV))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            txtNgayBatDau.setVisible(false);
            txtNgayKetThuc.setVisible(false);
            revalidate();
        }
    }//GEN-LAST:event_rdbAllNgayTaoActionPerformed

    private void rdbAllNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllNVActionPerformed
        if (rdbAllNV.isSelected()) {
            cbxNV.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllNVActionPerformed

    private void rdbChonNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonNVActionPerformed
        if (rdbChonNV.isSelected()) {
            cbxNV.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonNVActionPerformed

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        this.dispose();
    }//GEN-LAST:event_btnCloseMouseClicked

    private void btnLocTKNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocTKNVActionPerformed
        List<TaiKhoanNVDTO> listLocTKNV = chonTieuChiLoc();

        if (listLocTKNV != null) {
            checkFilterStatus();

            if (listLocTKNV.isEmpty()) {
                MessageBox.showErrorMessage(null, "Không có tài khoản nhân viên nào phù hợp!");
                return;
            }

            formDSTaiKhoanNV.loadDSTaiKhoanNV(isFiltered, false, listLocTKNV);
            this.dispose();
        }
    }//GEN-LAST:event_btnLocTKNVActionPerformed

    private void rdbAllTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllTrangThaiActionPerformed
        if (rdbAllTrangThai.isSelected()) {
            cbxTrangThai.setSelectedIndex(0);
            cbxTrangThai.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllTrangThaiActionPerformed

    private void rdbChonTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonTrangThaiActionPerformed
        if (rdbChonTrangThai.isSelected()) {
            cbxTrangThai.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonTrangThaiActionPerformed

    private void cbxNVItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxNVItemStateChanged
        String nhanVien = (String) cbxNV.getSelectedItem();
        if (nhanVien.equals("-Chọn nhân viên-")) {
            maNhanVien = 0;
        } else {
            String[] parts = nhanVien.split(" - ");
            String maNhanVienString = parts[0];
            maNhanVien = Integer.parseInt(maNhanVienString);
        }
    }//GEN-LAST:event_cbxNVItemStateChanged

    private void cbxTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTrangThaiItemStateChanged
        String tenTrangThai = (String) cbxTrangThai.getSelectedItem();
        if (tenTrangThai.equals("-Chọn trạng thái-")) {
            maTrangThai = 0;
        } else {
            maTrangThai = trangThaiBUS.getIdFromTenTrangThai(tenTrangThai, danhMuc);

            if (maTrangThai == null) {
                MessageBox.showErrorMessage(null, "Lấy id của trạng thái thất bại!");
            }
        }
    }//GEN-LAST:event_cbxTrangThaiItemStateChanged

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        rdbAllNgayTao.setSelected(true);
        rdbAllNV.setSelected(true);
        rdbAllTrangThai.setSelected(true);
        rdbAllTinhTrang.setSelected(true);
        cbxNV.setSelectedIndex(0);
        cbxNV.setEnabled(false);
        cbxTrangThai.setSelectedIndex(0);
        cbxTrangThai.setEnabled(false);
        cbxTinhTrang.setSelectedIndex(0);
        cbxTinhTrang.setEnabled(false);
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtNgayBatDau.setVisible(false);
        txtNgayKetThuc.setVisible(false);

        btnLocTKNVActionPerformed(null);
    }//GEN-LAST:event_btnResetActionPerformed

    private void cbxTinhTrangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTinhTrangItemStateChanged
        maTinhTrang = cbxTinhTrang.getSelectedIndex();
    }//GEN-LAST:event_cbxTinhTrangItemStateChanged

    private void rdbChonTinhTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonTinhTrangActionPerformed
        if (rdbChonTinhTrang.isSelected()) {
            cbxTinhTrang.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonTinhTrangActionPerformed

    private void rdbAllTinhTrangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllTinhTrangActionPerformed
        if (rdbAllTinhTrang.isSelected()) {
            cbxTinhTrang.setSelectedIndex(0);
            cbxTinhTrang.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllTinhTrangActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("quanlynganhang.GUI.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup();
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.ButtonGroup btnGroupLoaiTK;
    private javax.swing.ButtonGroup btnGroupNgayTao;
    private javax.swing.ButtonGroup btnGroupTinhTrang;
    private javax.swing.ButtonGroup btnGroupTrangThai;
    private javax.swing.JButton btnLocTKNV;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbxNV;
    private javax.swing.JComboBox<String> cbxTinhTrang;
    private javax.swing.JComboBox<String> cbxTrangThai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JRadioButton rdbAllNV;
    private javax.swing.JRadioButton rdbAllNgayTao;
    private javax.swing.JRadioButton rdbAllTinhTrang;
    private javax.swing.JRadioButton rdbAllTrangThai;
    private javax.swing.JRadioButton rdbChonNV;
    private javax.swing.JRadioButton rdbChonNgayTao;
    private javax.swing.JRadioButton rdbChonTinhTrang;
    private javax.swing.JRadioButton rdbChonTrangThai;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    // End of variables declaration//GEN-END:variables
}
