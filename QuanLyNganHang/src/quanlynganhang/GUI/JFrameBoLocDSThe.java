package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import quanlynganhang.BUS.TheATMBUS;
import quanlynganhang.BUS.TrangThaiBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.TheATMDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JFrameBoLocDSThe extends javax.swing.JFrame {

    private FormatDate fDate;
    private TheATMBUS theATMBUS;
    private TrangThaiBUS trangThaiBUS;
    private int maKhachHang;
    private FormDSThe formDSThe;
    private boolean isFiltered;
    private Integer maLoaiThe, maTrangThai;
    private final String danhMuc = "Account and Card";

    public JFrameBoLocDSThe(FormDSThe formDSThe) {
        this.formDSThe = formDSThe;
        fDate = new FormatDate();
        theATMBUS = new TheATMBUS();
        trangThaiBUS = new TrangThaiBUS();
        isFiltered = false;
        maLoaiThe = 0;
        maTrangThai = 0;

        initComponents();
        customUI();
        loadLoaiThe();
        loadTrangThai();
    }

    public void dienIdKH(int maKhachHang) {
        this.maKhachHang = maKhachHang;
        lbIdKH.setText("Id: " + maKhachHang);
    }

    private void customUI() {
        txtNgayBatDau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Bắt đầu");
        txtNgayKetThuc.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Kết thúc");

        txtNgayBatDau.setVisible(false);
        txtNgayKetThuc.setVisible(false);
        lbIdKH.setVisible(false);
        btnChonKH.setVisible(false);
        cbxLoaiThe.setEnabled(false);
        cbxTrangThai.setEnabled(false);

    }

    private void loadLoaiThe() {
        Map<Integer, String> map = new HashMap<>();
        map = theATMBUS.convertListLoaiTheATMToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn loại thẻ");

        for (String tenLoaiTaiKhoan : map.values()) {
            model.addElement(tenLoaiTaiKhoan);
        }

        cbxLoaiThe.setModel(model);
    }

    private void loadTrangThai() {
        Map<Integer, String> map = new HashMap<>();
        map = trangThaiBUS.convertListTrangThaiToMap(danhMuc);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn trạng thái");

        for (String tenTrangThai : map.values()) {
            model.addElement(tenTrangThai);
        }

        cbxTrangThai.setModel(model);
    }

    private void checkFilterStatus() {
        if (rdbAllNgayTao.isSelected() && rdbAllKH.isSelected() && rdbAllLoaiThe.isSelected() && rdbAllTrangThai.isSelected()) {
            isFiltered = false;
        } else {
            isFiltered = true;
        }
    }

    private List<TheATMDTO> chonTieuChiLoc() {
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

        int maKH;
        if (rdbAllKH.isSelected()) {
            maKH = 0;
        } else {
            if (lbIdKH.getText().equals("(Chưa chọn)")) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn khách hàng!");
                maKH = 0;
            } else {
                maKH = maKhachHang;
            }
        }

        int idLoaiThe;
        if (rdbAllLoaiThe.isSelected()) {
            idLoaiThe = 0;
        } else {
            if (cbxLoaiThe.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn loại thẻ!");
                return null;
            } else {
                idLoaiThe = maLoaiThe;
            }
        }

        int idTrangThai;
        if (rdbAllTrangThai.isSelected()) {
            idTrangThai = 0;
        } else {
            if (cbxTrangThai.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn trạng thái thẻ!");
                return null;
            } else {
                idTrangThai = maTrangThai;
            }
        }

        return theATMBUS.locThe(dateFrom, dateTo, maKH, idLoaiThe, idTrangThai);
    }

    public List<TheATMDTO> listTheBoLoc() {
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

        btnGroupLoaiThe = new javax.swing.ButtonGroup();
        btnGroupTrangThai = new javax.swing.ButtonGroup();
        btnGroupNgayTao = new javax.swing.ButtonGroup();
        btnGroupKH = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        rdbAllLoaiThe = new javax.swing.JRadioButton();
        rdbChonLoaiThe = new javax.swing.JRadioButton();
        cbxLoaiThe = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnLoc = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        rdbAllTrangThai = new javax.swing.JRadioButton();
        rdbChonTrangThai = new javax.swing.JRadioButton();
        cbxTrangThai = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtNgayBatDau = new javax.swing.JTextField();
        txtNgayKetThuc = new javax.swing.JTextField();
        rdbAllNgayTao = new javax.swing.JRadioButton();
        rdbChonNgayTao = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        rdbAllKH = new javax.swing.JRadioButton();
        rdbChonKH = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        btnChonKH = new javax.swing.JButton();
        lbIdKH = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Bộ lọc danh sách");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Loại thẻ");

        btnGroupLoaiThe.add(rdbAllLoaiThe);
        rdbAllLoaiThe.setSelected(true);
        rdbAllLoaiThe.setText("Tất cả");
        rdbAllLoaiThe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllLoaiTheActionPerformed(evt);
            }
        });

        btnGroupLoaiThe.add(rdbChonLoaiThe);
        rdbChonLoaiThe.setText("Tùy chỉnh");
        rdbChonLoaiThe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonLoaiTheActionPerformed(evt);
            }
        });

        cbxLoaiThe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại thẻ" }));
        cbxLoaiThe.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxLoaiTheItemStateChanged(evt);
            }
        });
        cbxLoaiThe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxLoaiTheActionPerformed(evt);
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
                            .addComponent(cbxLoaiThe, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllLoaiThe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonLoaiThe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
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
                .addComponent(rdbAllLoaiThe)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonLoaiThe)
                .addGap(18, 18, 18)
                .addComponent(cbxLoaiThe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel6)
                .addGap(13, 13, 13))
        );

        jButton1.setText("Hủy");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbAllNgayTao)
                            .addComponent(rdbChonNgayTao))
                        .addGap(69, 69, 69))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Khách hàng");

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

        btnChonKH.setText("Chọn");
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        lbIdKH.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                            .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbIdKH, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonKH)
                .addGap(18, 18, 18)
                .addComponent(lbIdKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChonKH)
                .addGap(37, 37, 37)
                .addComponent(jLabel9)
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
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(182, 182, 182)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReset)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLoc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLoc)
                        .addComponent(btnReset)))
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

    private void rdbAllLoaiTheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllLoaiTheActionPerformed
        if (rdbAllLoaiThe.isSelected()) {
            cbxLoaiThe.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllLoaiTheActionPerformed

    private void rdbChonLoaiTheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonLoaiTheActionPerformed
        if (rdbChonLoaiThe.isSelected()) {
            cbxLoaiThe.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonLoaiTheActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        List<TheATMDTO> listLocThe = chonTieuChiLoc();
        
        if (listLocThe != null) {
            checkFilterStatus();

            if (listLocThe.isEmpty()) {
                MessageBox.showErrorMessage(null, "Không có thông tin thẻ nào phù hợp!");
                return;
            }
            
            formDSThe.loadDSThe(isFiltered, false, chonTieuChiLoc());
            this.dispose();
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void rdbAllTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllTrangThaiActionPerformed
        if (rdbAllTrangThai.isSelected()) {
            cbxTrangThai.setVisible(false);
        }
    }//GEN-LAST:event_rdbAllTrangThaiActionPerformed

    private void rdbChonTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonTrangThaiActionPerformed
        if (rdbChonTrangThai.isSelected()) {
            cbxTrangThai.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonTrangThaiActionPerformed

    private void txtNgayBatDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayBatDauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayBatDauActionPerformed

    private void rdbAllNgayTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllNgayTaoActionPerformed
        if (rdbAllNgayTao.isSelected()) {
            txtNgayBatDau.setText("");
            txtNgayKetThuc.setText("");
            txtNgayBatDau.setVisible(false);
            txtNgayKetThuc.setVisible(false);
            revalidate();
        }
    }//GEN-LAST:event_rdbAllNgayTaoActionPerformed

    private void rdbChonNgayTaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonNgayTaoActionPerformed
        if (rdbChonNgayTao.isSelected()) {
            txtNgayBatDau.setVisible(true);
            txtNgayKetThuc.setVisible(true);
            revalidate();
        }
    }//GEN-LAST:event_rdbChonNgayTaoActionPerformed

    private void rdbAllKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllKHActionPerformed
        if (rdbAllKH.isSelected()) {
            btnChonKH.setVisible(false);
            lbIdKH.setText("(Chưa chọn)");
            lbIdKH.setVisible(false);
        }
    }//GEN-LAST:event_rdbAllKHActionPerformed

    private void rdbChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonKHActionPerformed
        if (rdbChonKH.isSelected()) {
            btnChonKH.setVisible(true);
            lbIdKH.setVisible(true);
        }
    }//GEN-LAST:event_rdbChonKHActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        JDialogTableChonItem chonKH = new JDialogTableChonItem(null, true, this, "Chọn khách hàng", "DSKH", true);
        chonKH.setResizable(false);
        chonKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonKH.setVisible(true);
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void cbxLoaiTheItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxLoaiTheItemStateChanged
        String tenLoaiThe = (String) cbxLoaiThe.getSelectedItem();
        if (tenLoaiThe.equals("Chọn loại thẻ")) {
            maLoaiThe = 0;
        } else {
            maLoaiThe = theATMBUS.getIdFromTenLoaiThe(tenLoaiThe);

            if (maLoaiThe == null) {
                MessageBox.showErrorMessage(null, "Lấy id của loại thẻ thất bại!");
            }
        }
    }//GEN-LAST:event_cbxLoaiTheItemStateChanged

    private void cbxTrangThaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTrangThaiItemStateChanged
        String tenTrangThai = (String) cbxTrangThai.getSelectedItem();
        if (tenTrangThai.equals("Chọn trạng thái")) {
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
        rdbAllKH.setSelected(true);
        rdbAllLoaiThe.setSelected(true);
        rdbAllTrangThai.setSelected(true);
        txtNgayBatDau.setText("");
        txtNgayKetThuc.setText("");
        txtNgayBatDau.setVisible(false);
        txtNgayKetThuc.setVisible(false);
        lbIdKH.setText("(Chưa chọn)");
        cbxLoaiThe.setSelectedIndex(0);
        cbxTrangThai.setSelectedIndex(0);
        cbxLoaiThe.setEnabled(false);
        cbxTrangThai.setEnabled(false);

        btnLocActionPerformed(null);
    }//GEN-LAST:event_btnResetActionPerformed

    private void cbxLoaiTheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxLoaiTheActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxLoaiTheActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonKH;
    private javax.swing.ButtonGroup btnGroupKH;
    private javax.swing.ButtonGroup btnGroupLoaiThe;
    private javax.swing.ButtonGroup btnGroupNgayTao;
    private javax.swing.ButtonGroup btnGroupTrangThai;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbxLoaiThe;
    private javax.swing.JComboBox<String> cbxTrangThai;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lbIdKH;
    private javax.swing.JRadioButton rdbAllKH;
    private javax.swing.JRadioButton rdbAllLoaiThe;
    private javax.swing.JRadioButton rdbAllNgayTao;
    private javax.swing.JRadioButton rdbAllTrangThai;
    private javax.swing.JRadioButton rdbChonKH;
    private javax.swing.JRadioButton rdbChonLoaiThe;
    private javax.swing.JRadioButton rdbChonNgayTao;
    private javax.swing.JRadioButton rdbChonTrangThai;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    // End of variables declaration//GEN-END:variables
}
