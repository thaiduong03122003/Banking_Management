package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.event.ItemEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.BUS.LoaiGiaoDichBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TaiKhoanNVBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.TheATMDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JFrameBoLocGD extends javax.swing.JFrame {

    private GiaoDichBUS giaoDichBUS;
    private LoaiGiaoDichBUS loaiGiaoDichBUS;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private int maKhachHang, maNhanVien;
    private Integer maTaiKhoanKH, maTaiKhoanNV, maLoaiGiaoDich;
    private boolean isFiltered;
    private FormDSGiaoDich formDSGiaoDich;
    private FormatDate fDate;
    
    public JFrameBoLocGD(FormDSGiaoDich formDSGiaoDich) {
        this.formDSGiaoDich = formDSGiaoDich;
        giaoDichBUS = new GiaoDichBUS();
        loaiGiaoDichBUS = new LoaiGiaoDichBUS();
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        isFiltered = false;
        fDate = new FormatDate();
        
        initComponents();
        loadLoaiGiaoDich();
        rdbAllNgayGDActionPerformed(null);
        rdbAllKHActionPerformed(null);
        rdbAllNVActionPerformed(null);
        rdbAllTKKHActionPerformed(null);
        rdbAllLoaiGDActionPerformed(null);
        
        txtNgayBatDau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Bắt đầu");
        txtNgayKetThuc.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Kết thúc");
    }
    
    public void dienIdKH(int maKhachHang) {
        this.maKhachHang = maKhachHang;
        lbMaKH.setText("Id: " + maKhachHang);
        loadTKKH();
        
        rdbAllTKKH.setEnabled(true);
        rdbChonTKKH.setEnabled(true);
    }
    
    public void dienIdNV(int maNhanVien) {
        this.maNhanVien = maNhanVien;
        lbMaNV.setText("Id: " + maNhanVien);
    }
    
    private void loadTKKH() {
        Map<Integer, String> map = new HashMap<>();
        map = taiKhoanKHBUS.convertListTKNguonToMap(maKhachHang);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn tài khoản");

        for (String tenTaiKhoanKH : map.values()) {
            model.addElement(tenTaiKhoanKH);
        }

        cbxTKKH.setModel(model);
    }
    
    private void loadLoaiGiaoDich() {
        Map<Integer, String> map = new HashMap<>();
        map = loaiGiaoDichBUS.convertListLoaiGDToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn loại giao dịch");

        for (String tenLoaiGD : map.values()) {
            model.addElement(tenLoaiGD);
        }

        cbxLoaiGD.setModel(model);
    }
    
    private void checkFilterStatus() {
        if (rdbAllNgayGD.isSelected() && rdbAllKH.isSelected() && rdbAllNV.isSelected() && rdbAllTKKH.isSelected() && rdbAllLoaiGD.isSelected()) {
            isFiltered = false;
        } else {
            isFiltered = true;
        }
    }
    
    private List<GiaoDichDTO> chonTieuChiLoc() throws ParseException, Exception {
        

        Date dateFrom, dateTo;
        if (rdbAllNgayGD.isSelected()) {
            dateFrom = null;
            dateTo = null;
        } else {
            if (!InputValidation.kiemTraNgay(txtNgayBatDau.getText()) || !InputValidation.kiemTraNgay(txtNgayKetThuc.getText())) {
                MessageBox.showErrorMessage(null, "Ngày nhập vào không hợp lệ!, vui lòng nhập đúng định dạng dd/MM/yyyy");
                return null;
            } else {
                dateFrom = (Date) fDate.toDate(txtNgayBatDau.getText());
                dateTo = (Date) fDate.toDate(txtNgayKetThuc.getText());
                
                if (!InputValidation.kiemTraTrinhTuNhapNgay(dateFrom, dateTo)) {
                    MessageBox.showErrorMessage(null, "Ngày bắt đầu phải nhỏ hơn ngày kết thúc!");
                    return null;
                }
            }
        }
        
        int maKH;
        if (rdbAllKH.isSelected()) {
            maKH = 0;
        } else {
            if (lbMaKH.getText().equals("(Chưa có)")) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn khách hàng!");
                maKH = 0;
            } else {
                maKH = maKhachHang;
            }
        }
        
        int maNV;
        if (rdbAllNV.isSelected()) {
            maNV = 0;
        } else {
            if (lbMaNV.getText().equals("(Chưa có)")) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên!");
                maNV = 0;
            } else {
                maNV = maNhanVien;
            }
        }
        
        int maTKKH;
        if (rdbAllTKKH.isSelected()) {
            maTKKH = 0;
        } else {
            if (cbxTKKH.getSelectedIndex() == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản khách hàng!");
                maTKKH = 0;
            } else {
                maTKKH = maTaiKhoanKH;
            }
        }

        int idLoaiGD;
        if (rdbAllLoaiGD.isSelected()) {
            idLoaiGD = 0;
        } else {
            idLoaiGD = maLoaiGiaoDich;
        }

        return giaoDichBUS.locGiaoDich(dateFrom, dateTo, maKH, maNV, maTKKH, idLoaiGD);
    }
    
    public List<GiaoDichDTO> listGDBoLoc() throws Exception {
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

        btnGroupNgayGD = new javax.swing.ButtonGroup();
        btnGroupKH = new javax.swing.ButtonGroup();
        btnGroupNV = new javax.swing.ButtonGroup();
        btnGroupTKKH = new javax.swing.ButtonGroup();
        btnGroupLoaiGD = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnLoc = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtNgayBatDau = new javax.swing.JTextField();
        txtNgayKetThuc = new javax.swing.JTextField();
        rdbAllNgayGD = new javax.swing.JRadioButton();
        rdbChonNgayGD = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        rdbAllKH = new javax.swing.JRadioButton();
        rdbChonKH = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        btnChonKH = new javax.swing.JButton();
        lbMaKH = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        rdbAllLoaiGD = new javax.swing.JRadioButton();
        rdbChonLoaiGD = new javax.swing.JRadioButton();
        cbxLoaiGD = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        rdbAllTKKH = new javax.swing.JRadioButton();
        rdbChonTKKH = new javax.swing.JRadioButton();
        cbxTKKH = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        rdbAllNV = new javax.swing.JRadioButton();
        rdbChonNV = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        btnChonNV = new javax.swing.JButton();
        lbMaNV = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Bộ lọc danh sách giao dịch");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
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

        btnReset.setText("Đặt lại");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnLoc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLoc.setText("Lọc");
        btnLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(245, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(242, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnReset)
                    .addComponent(btnLoc))
                .addContainerGap())
        );

        jPanel2.add(jPanel12, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Ngày giao dịch");

        txtNgayBatDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgayBatDauActionPerformed(evt);
            }
        });

        btnGroupNgayGD.add(rdbAllNgayGD);
        rdbAllNgayGD.setSelected(true);
        rdbAllNgayGD.setText("Tất cả");
        rdbAllNgayGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllNgayGDActionPerformed(evt);
            }
        });

        btnGroupNgayGD.add(rdbChonNgayGD);
        rdbChonNgayGD.setText("Tùy chỉnh");
        rdbChonNgayGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonNgayGDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbAllNgayGD)
                            .addComponent(rdbChonNgayGD))
                        .addGap(69, 69, 69))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllNgayGD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonNgayGD)
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

        lbMaKH.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                            .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonKH)
                .addGap(18, 18, 18)
                .addComponent(lbMaKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChonKH)
                .addGap(37, 37, 37)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Loại giao dịch");

        btnGroupLoaiGD.add(rdbAllLoaiGD);
        rdbAllLoaiGD.setSelected(true);
        rdbAllLoaiGD.setText("Tất cả");
        rdbAllLoaiGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllLoaiGDActionPerformed(evt);
            }
        });

        btnGroupLoaiGD.add(rdbChonLoaiGD);
        rdbChonLoaiGD.setText("Tùy chỉnh");
        rdbChonLoaiGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonLoaiGDActionPerformed(evt);
            }
        });

        cbxLoaiGD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn loại giao dịch" }));
        cbxLoaiGD.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxLoaiGDItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxLoaiGD, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllLoaiGD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonLoaiGD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllLoaiGD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonLoaiGD)
                .addGap(18, 18, 18)
                .addComponent(cbxLoaiGD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel6)
                .addGap(13, 13, 13))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Tài khoản khách hàng");

        btnGroupTKKH.add(rdbAllTKKH);
        rdbAllTKKH.setSelected(true);
        rdbAllTKKH.setText("Tất cả");
        rdbAllTKKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllTKKHActionPerformed(evt);
            }
        });

        btnGroupTKKH.add(rdbChonTKKH);
        rdbChonTKKH.setText("Tùy chỉnh");
        rdbChonTKKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonTKKHActionPerformed(evt);
            }
        });

        cbxTKKH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn tài khoản" }));
        cbxTKKH.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTKKHItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxTKKH, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllTKKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonTKKH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllTKKH)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonTKKH)
                .addGap(18, 18, 18)
                .addComponent(cbxTKKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(jLabel8)
                .addGap(13, 13, 13))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Nhân viên");

        btnGroupNV.add(rdbAllNV);
        rdbAllNV.setSelected(true);
        rdbAllNV.setText("Tất cả");
        rdbAllNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllNVActionPerformed(evt);
            }
        });

        btnGroupNV.add(rdbChonNV);
        rdbChonNV.setText("Tùy chỉnh");
        rdbChonNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonNVActionPerformed(evt);
            }
        });

        btnChonNV.setText("Chọn");
        btnChonNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonNVActionPerformed(evt);
            }
        });

        lbMaNV.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                            .addComponent(btnChonNV, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonNV)
                .addGap(18, 18, 18)
                .addComponent(lbMaNV)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChonNV)
                .addGap(37, 37, 37)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNgayBatDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgayBatDauActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgayBatDauActionPerformed

    private void rdbAllNgayGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllNgayGDActionPerformed
        if (rdbAllNgayGD.isSelected()) {
            txtNgayBatDau.setText("");
            txtNgayKetThuc.setText("");
            txtNgayBatDau.setVisible(false);
            txtNgayKetThuc.setVisible(false);
            revalidate();
        }
    }//GEN-LAST:event_rdbAllNgayGDActionPerformed

    private void rdbChonNgayGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonNgayGDActionPerformed
        if (rdbChonNgayGD.isSelected()) {
            txtNgayBatDau.setVisible(true);
            txtNgayKetThuc.setVisible(true);
            revalidate();
        }
    }//GEN-LAST:event_rdbChonNgayGDActionPerformed

    private void rdbAllKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllKHActionPerformed
        if(rdbAllKH.isSelected()) {
            maKhachHang = 0;
            btnChonKH.setVisible(false);
            lbMaKH.setText("(Chưa chọn)");
            lbMaKH.setVisible(false);
            
            rdbAllTKKH.setSelected(true);
            rdbAllTKKH.setEnabled(false);
            rdbChonTKKH.setEnabled(false);
            cbxTKKH.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllKHActionPerformed

    private void rdbChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonKHActionPerformed
        if(rdbChonKH.isSelected()) {
            btnChonKH.setVisible(true);
            lbMaKH.setVisible(true);
        }
    }//GEN-LAST:event_rdbChonKHActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        JDialogTableChonItem chonKH = new JDialogTableChonItem(null, true, this, "Chọn khách hàng", "DSKH");
        chonKH.setResizable(false);
        chonKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonKH.setVisible(true);
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void rdbAllLoaiGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllLoaiGDActionPerformed
        if (rdbAllLoaiGD.isSelected()) {
            cbxLoaiGD.setSelectedIndex(0);
            cbxLoaiGD.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllLoaiGDActionPerformed

    private void rdbChonLoaiGDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonLoaiGDActionPerformed
        if (rdbChonLoaiGD.isSelected()) {
            cbxLoaiGD.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonLoaiGDActionPerformed

    private void cbxLoaiGDItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxLoaiGDItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String tenLoaiGD = (String) cbxLoaiGD.getSelectedItem();
            if (tenLoaiGD.equals("Chọn loại giao dịch")) {
                maLoaiGiaoDich = 0;
            } else {
                maLoaiGiaoDich = loaiGiaoDichBUS.getIdFromTenLoaiGD(tenLoaiGD);

                if (maTaiKhoanKH == null) {
                    MessageBox.showErrorMessage(null, "Lấy id của loại giao dịch thất bại!");
                    return;
                }
            }
        }
    }//GEN-LAST:event_cbxLoaiGDItemStateChanged

    private void rdbAllTKKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllTKKHActionPerformed
        if (rdbAllTKKH.isSelected()) {
            maTaiKhoanKH = 0;
            cbxTKKH.setSelectedIndex(0);
            cbxTKKH.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllTKKHActionPerformed

    private void rdbChonTKKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonTKKHActionPerformed
        if (rdbChonTKKH.isSelected()) {
            cbxTKKH.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonTKKHActionPerformed

    private void cbxTKKHItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTKKHItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String tenTaiKhoan = (String) cbxTKKH.getSelectedItem();
            if (tenTaiKhoan.equals("Chọn tài khoản")) {
                maTaiKhoanKH = 0;
            } else {
                maTaiKhoanKH = taiKhoanKHBUS.getIdFromSTKNguon(tenTaiKhoan, maKhachHang);

                if (maTaiKhoanKH == null) {
                    MessageBox.showErrorMessage(null, "Lấy id của tài khoản khách hàng thất bại!");
                    return;
                }
            }
        }
    }//GEN-LAST:event_cbxTKKHItemStateChanged

    private void rdbAllNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllNVActionPerformed
        if(rdbAllNV.isSelected()) {
            maNhanVien = 0;
            btnChonNV.setVisible(false);
            lbMaNV.setText("(Chưa chọn)");
            lbMaNV.setVisible(false);
        }
    }//GEN-LAST:event_rdbAllNVActionPerformed

    private void rdbChonNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonNVActionPerformed
        if(rdbChonNV.isSelected()) {
            btnChonNV.setVisible(true);
            lbMaNV.setVisible(true);
        }
    }//GEN-LAST:event_rdbChonNVActionPerformed

    private void btnChonNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonNVActionPerformed
        JDialogTableChonItem chonKH = new JDialogTableChonItem(null, true, this, "Chọn nhân viên", "DSNV");
        chonKH.setResizable(false);
        chonKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonKH.setVisible(true);
    }//GEN-LAST:event_btnChonNVActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        try {
            if (chonTieuChiLoc() != null) {
                checkFilterStatus();

                formDSGiaoDich.loadDSGiaoDich(isFiltered, chonTieuChiLoc());
                this.dispose();
            } else {
                MessageBox.showInformationMessage(null, "", "Không có thông tin giao dịch nào phù hợp");
                return;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        rdbAllNgayGD.setSelected(true);
        rdbAllKH.setSelected(true);
        rdbAllNV.setSelected(true);
        rdbAllTKKH.setSelected(true);
        rdbAllLoaiGD.setSelected(true);
        
        rdbAllNgayGDActionPerformed(null);
        rdbAllKHActionPerformed(null);
        rdbAllNVActionPerformed(null);
        rdbAllTKKHActionPerformed(null);
        rdbAllLoaiGDActionPerformed(null);
        
        btnLocActionPerformed(null);
    }//GEN-LAST:event_btnResetActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameBoLocGD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameBoLocGD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameBoLocGD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameBoLocGD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonKH;
    private javax.swing.JButton btnChonNV;
    private javax.swing.ButtonGroup btnGroupKH;
    private javax.swing.ButtonGroup btnGroupLoaiGD;
    private javax.swing.ButtonGroup btnGroupNV;
    private javax.swing.ButtonGroup btnGroupNgayGD;
    private javax.swing.ButtonGroup btnGroupTKKH;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbxLoaiGD;
    private javax.swing.JComboBox<String> cbxTKKH;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JLabel lbMaKH;
    private javax.swing.JLabel lbMaNV;
    private javax.swing.JRadioButton rdbAllKH;
    private javax.swing.JRadioButton rdbAllLoaiGD;
    private javax.swing.JRadioButton rdbAllNV;
    private javax.swing.JRadioButton rdbAllNgayGD;
    private javax.swing.JRadioButton rdbAllTKKH;
    private javax.swing.JRadioButton rdbChonKH;
    private javax.swing.JRadioButton rdbChonLoaiGD;
    private javax.swing.JRadioButton rdbChonNV;
    private javax.swing.JRadioButton rdbChonNgayGD;
    private javax.swing.JRadioButton rdbChonTKKH;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    // End of variables declaration//GEN-END:variables
}
