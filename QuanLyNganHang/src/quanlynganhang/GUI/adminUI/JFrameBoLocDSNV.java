/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import quanlynganhang.BUS.ChucVuBUS;
import quanlynganhang.BUS.DiaChiBUS;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.GUI.model.message.MessageBox;

/**
 *
 * @author THAI
 */
public class JFrameBoLocDSNV extends javax.swing.JFrame {

    private FormDSNhanVien formDSNhanVien;
    private DiaChiBUS diaChiBUS;
    private ChucVuBUS chucVuBUS;
    private NhanVienBUS nhanVienBUS;
    private Integer maTinhThanh, maQuanHuyen, maPhuongXa, maChucVu;
    private boolean isFiltered;

    /** Creates new form JFrameBoLocDS */
    public JFrameBoLocDSNV(FormDSNhanVien formDSNhanVien) {
        this.formDSNhanVien = formDSNhanVien;
        diaChiBUS = new DiaChiBUS();
        chucVuBUS = new ChucVuBUS();
        nhanVienBUS = new NhanVienBUS();
        maTinhThanh = 0;
        maQuanHuyen = 0;
        maPhuongXa = 0;
        maChucVu = 0;
        isFiltered = false;

        initComponents();
        customUI();
        loadTinhThanh();
        loadChucVu();
    }

    private void customUI() {
        txtNgayBatDau.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Bắt đầu");
        txtNgayKetThuc.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Kết thúc");

        txtNgayBatDau.setVisible(false);
        txtNgayKetThuc.setVisible(false);

        cbxTinh.setEnabled(false);
        cbxHuyen.setEnabled(false);
        cbxXa.setEnabled(false);

        cbxChucVu.setVisible(false);
    }

    private void loadTinhThanh() {
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListTinhThanhToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn thành phố/tỉnh");

        for (String tenTinhThanh : map.values()) {
            model.addElement(tenTinhThanh);
        }

        cbxTinh.setModel(model);
    }

    private void loadQuanHuyen(int maTinhThanh) {
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListQuanHuyenToMap(maTinhThanh);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn quận/huyện");

        for (String tenQuanHuyen : map.values()) {
            model.addElement(tenQuanHuyen);
        }

        cbxHuyen.setModel(model);
    }

    private void loadPhuongXa(int maQuanHuyen) {
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListPhuongXaToMap(maQuanHuyen);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn phường/xã");

        for (String tenPhuongXa : map.values()) {
            model.addElement(tenPhuongXa);
        }

        cbxXa.setModel(model);
    }

    private void loadChucVu() {
        Map<Integer, String> map = new HashMap<>();
        map = chucVuBUS.convertListChucVuToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Chọn chức vụ");

        for (String tenChucVu : map.values()) {
            model.addElement(tenChucVu);
        }

        cbxChucVu.setModel(model);
    }

    private void checkFilterStatus() {
        if (rdbAllGender.isSelected() && rdbAllNgaySinh.isSelected() && rdbAllDiaChi.isSelected() && rdbAllChucVu.isSelected()) {
            isFiltered = false;
        } else {
            isFiltered = true;
        }
    }

    private List<NhanVienDTO> chonTieuChiLoc() throws ParseException, Exception {
        FormatDate fDate = new FormatDate();

        String gender;
        if (rdbAllGender.isSelected()) {
            gender = "All";
        } else if (rdbNam.isSelected()) {
            gender = "Nam";
        } else if (rdbNu.isSelected()) {
            gender = "Nữ";
        } else {
            gender = "Khác";
        }

        Date dateFrom, dateTo;
        if (rdbAllNgaySinh.isSelected()) {
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

        int idTinh, idHuyen, idXa;
        if (rdbAllDiaChi.isSelected()) {
            idTinh = 0;
            idHuyen = 0;
            idXa = 0;
        } else {
            idTinh = maTinhThanh;
            idHuyen = maQuanHuyen;
            idXa = maPhuongXa;
        }

        int idChucVu;
        if (rdbAllChucVu.isSelected()) {
            idChucVu = 0;
        } else {
            idChucVu = maChucVu;
        }

        return nhanVienBUS.locNhanVien(gender, dateFrom, dateTo, idTinh, idHuyen, idXa, idChucVu);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupGender = new javax.swing.ButtonGroup();
        btnGroupNgaySinh = new javax.swing.ButtonGroup();
        btnGroupDiaChi = new javax.swing.ButtonGroup();
        btnGroupChucVu = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        rdbAllGender = new javax.swing.JRadioButton();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        rdbKhac = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtNgayBatDau = new javax.swing.JTextField();
        txtNgayKetThuc = new javax.swing.JTextField();
        rdbAllNgaySinh = new javax.swing.JRadioButton();
        rdbChonNgaySinh = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        rdbAllDiaChi = new javax.swing.JRadioButton();
        rdbChonDiaChi = new javax.swing.JRadioButton();
        cbxTinh = new javax.swing.JComboBox<>();
        cbxHuyen = new javax.swing.JComboBox<>();
        cbxXa = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        rdbAllChucVu = new javax.swing.JRadioButton();
        rdbChonChucVu = new javax.swing.JRadioButton();
        cbxChucVu = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        btnLoc = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel1.setText("Bộ lọc danh sách");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Giới tính");

        btnGroupGender.add(rdbAllGender);
        rdbAllGender.setSelected(true);
        rdbAllGender.setText("Tất cả");
        rdbAllGender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllGenderActionPerformed(evt);
            }
        });

        btnGroupGender.add(rdbNam);
        rdbNam.setText("Nam");

        btnGroupGender.add(rdbNu);
        rdbNu.setText("Nữ");

        btnGroupGender.add(rdbKhac);
        rdbKhac.setText("Khác");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbAllGender)
                            .addComponent(rdbNam)
                            .addComponent(rdbKhac)
                            .addComponent(rdbNu))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllGender)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbNu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbKhac)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("Ngày sinh");

        btnGroupNgaySinh.add(rdbAllNgaySinh);
        rdbAllNgaySinh.setSelected(true);
        rdbAllNgaySinh.setText("Tất cả");
        rdbAllNgaySinh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdbAllNgaySinhItemStateChanged(evt);
            }
        });
        rdbAllNgaySinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllNgaySinhActionPerformed(evt);
            }
        });

        btnGroupNgaySinh.add(rdbChonNgaySinh);
        rdbChonNgaySinh.setText("Tùy chỉnh");
        rdbChonNgaySinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonNgaySinhActionPerformed(evt);
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
                    .addComponent(txtNgayBatDau)
                    .addComponent(txtNgayKetThuc)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbAllNgaySinh)
                            .addComponent(rdbChonNgaySinh))
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
                .addComponent(rdbAllNgaySinh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonNgaySinh)
                .addGap(18, 18, 18)
                .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Địa chỉ");

        btnGroupDiaChi.add(rdbAllDiaChi);
        rdbAllDiaChi.setSelected(true);
        rdbAllDiaChi.setText("Tất cả");
        rdbAllDiaChi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdbAllDiaChiItemStateChanged(evt);
            }
        });
        rdbAllDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllDiaChiActionPerformed(evt);
            }
        });

        btnGroupDiaChi.add(rdbChonDiaChi);
        rdbChonDiaChi.setText("Tùy chỉnh");
        rdbChonDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonDiaChiActionPerformed(evt);
            }
        });

        cbxTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn thành phố/tỉnh" }));
        cbxTinh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTinhItemStateChanged(evt);
            }
        });

        cbxHuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn quận/huyện" }));
        cbxHuyen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxHuyenItemStateChanged(evt);
            }
        });

        cbxXa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn phường/xã" }));
        cbxXa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxXaItemStateChanged(evt);
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
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cbxXa, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxHuyen, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbxTinh, javax.swing.GroupLayout.Alignment.LEADING, 0, 157, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rdbAllDiaChi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdbChonDiaChi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)))
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
                .addComponent(rdbAllDiaChi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonDiaChi)
                .addGap(18, 18, 18)
                .addComponent(cbxTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxXa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jLabel6)
                .addGap(13, 13, 13))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Chức vụ");

        btnGroupChucVu.add(rdbAllChucVu);
        rdbAllChucVu.setSelected(true);
        rdbAllChucVu.setText("Tất cả");
        rdbAllChucVu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                rdbAllChucVuItemStateChanged(evt);
            }
        });
        rdbAllChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllChucVuActionPerformed(evt);
            }
        });

        btnGroupChucVu.add(rdbChonChucVu);
        rdbChonChucVu.setText("Tùy chỉnh");
        rdbChonChucVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbChonChucVuActionPerformed(evt);
            }
        });

        cbxChucVu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chọn chức vụ" }));
        cbxChucVu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxChucVuItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cbxChucVu, 0, 191, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdbAllChucVu)
                            .addComponent(rdbChonChucVu))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdbAllChucVu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbChonChucVu)
                .addGap(18, 18, 18)
                .addComponent(cbxChucVu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLoc)
                                .addGap(188, 188, 188)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnReset)
                    .addComponent(btnLoc))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rdbAllGenderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllGenderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbAllGenderActionPerformed

    private void rdbChonNgaySinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonNgaySinhActionPerformed
        if (rdbChonNgaySinh.isSelected()) {
            txtNgayBatDau.setVisible(true);
            txtNgayKetThuc.setVisible(true);
            revalidate();
        }
    }//GEN-LAST:event_rdbChonNgaySinhActionPerformed

    private void rdbAllNgaySinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllNgaySinhActionPerformed

    }//GEN-LAST:event_rdbAllNgaySinhActionPerformed

    private void rdbAllDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllDiaChiActionPerformed

    }//GEN-LAST:event_rdbAllDiaChiActionPerformed

    private void rdbChonDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonDiaChiActionPerformed
        if (rdbChonDiaChi.isSelected()) {
            cbxTinh.setEnabled(true);
        }
    }//GEN-LAST:event_rdbChonDiaChiActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

    private void rdbAllChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllChucVuActionPerformed

    }//GEN-LAST:event_rdbAllChucVuActionPerformed

    private void rdbChonChucVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbChonChucVuActionPerformed
        if (rdbChonChucVu.isSelected()) {
            cbxChucVu.setVisible(true);
        }
    }//GEN-LAST:event_rdbChonChucVuActionPerformed

    private void cbxHuyenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxHuyenItemStateChanged
        String tenQuanHuyen = (String) cbxHuyen.getSelectedItem();
        if (tenQuanHuyen.equals("Chọn quận/huyện")) {
            cbxXa.setSelectedIndex(0);

            cbxXa.setEnabled(false);
            maQuanHuyen = 0;
        } else {
            maQuanHuyen = diaChiBUS.getIdFromTenQuanHuyen(tenQuanHuyen, maTinhThanh);

            if (maQuanHuyen != 0) {
                loadPhuongXa(maQuanHuyen.intValue());
                cbxXa.setEnabled(true);
                cbxXa.setSelectedIndex(0);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của quận huyện thất bại!");
            }
        }
    }//GEN-LAST:event_cbxHuyenItemStateChanged

    private void cbxTinhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTinhItemStateChanged
        String tenTinhThanh = (String) cbxTinh.getSelectedItem();
        if (tenTinhThanh.equals("Chọn thành phố/tỉnh")) {
            cbxHuyen.setSelectedIndex(0);
            cbxXa.setSelectedIndex(0);

            cbxHuyen.setEnabled(false);
            cbxXa.setEnabled(false);
            maTinhThanh = 0;
        } else {
            maTinhThanh = diaChiBUS.getIdFromTenTinhThanh(tenTinhThanh);

            if (maTinhThanh != 0) {
                loadQuanHuyen(maTinhThanh.intValue());
                cbxHuyen.setEnabled(true);
                cbxXa.setEnabled(false);
                cbxHuyen.setSelectedIndex(0);
                cbxXa.setSelectedIndex(0);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của tỉnh thành thất bại!");
            }
        }
    }//GEN-LAST:event_cbxTinhItemStateChanged

    private void cbxXaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxXaItemStateChanged
        String tenPhuongXa = (String) cbxXa.getSelectedItem();
        if (tenPhuongXa.equals("Chọn phường/xã")) {
            maPhuongXa = 0;
        } else {
            maPhuongXa = diaChiBUS.getIdFromTenPhuongXa(tenPhuongXa, maQuanHuyen);

            if (maPhuongXa != 0) {
                System.out.println("Id xã: " + maPhuongXa + " - Id huyện: " + maQuanHuyen + " - Id tỉnh: " + maTinhThanh);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của phường xã thất bại!");
            }
        }
    }//GEN-LAST:event_cbxXaItemStateChanged

    private void cbxChucVuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxChucVuItemStateChanged
        String tenChucVu = (String) cbxChucVu.getSelectedItem();
        if (tenChucVu.equals("Chọn chức vụ")) {
            maChucVu = 0;
        } else {
            maChucVu = chucVuBUS.getIdFromTenChucVu(tenChucVu);

            if (maChucVu != 0) {
                System.out.println("Lay ma chuc vu thanh cong!");
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của chức vụ thất bại!");
            }
        }
    }//GEN-LAST:event_cbxChucVuItemStateChanged

    private void btnLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLocActionPerformed
        try {
            if (chonTieuChiLoc() != null) {
                checkFilterStatus();

                formDSNhanVien.loadDSNhanVien(0, isFiltered, chonTieuChiLoc());
                this.dispose();
            } else {
                MessageBox.showInformationMessage(null, "Lọc nhân viên", "Không có thông tin nhân viên nào phù hợp");
                return;
            }

        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnLocActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        rdbAllGender.setSelected(true);
        rdbAllNgaySinh.setSelected(true);
        rdbAllDiaChi.setSelected(true);
        rdbAllChucVu.setSelected(true);

        btnLocActionPerformed(null);
    }//GEN-LAST:event_btnResetActionPerformed

    private void rdbAllNgaySinhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdbAllNgaySinhItemStateChanged
        if (rdbAllNgaySinh.isSelected()) {
            txtNgayBatDau.setText("");
            txtNgayKetThuc.setText("");
            txtNgayBatDau.setVisible(false);
            txtNgayKetThuc.setVisible(false);
            revalidate();
        }
    }//GEN-LAST:event_rdbAllNgaySinhItemStateChanged

    private void rdbAllDiaChiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdbAllDiaChiItemStateChanged
        if (rdbAllDiaChi.isSelected()) {
            cbxTinh.setEnabled(false);
            cbxHuyen.setEnabled(false);
            cbxXa.setEnabled(false);
        }
    }//GEN-LAST:event_rdbAllDiaChiItemStateChanged

    private void rdbAllChucVuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_rdbAllChucVuItemStateChanged
        if (rdbAllChucVu.isSelected()) {
            cbxChucVu.setSelectedIndex(0);
            cbxChucVu.setVisible(false);
        }
    }//GEN-LAST:event_rdbAllChucVuItemStateChanged

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
                System.out.println("You should run in formDSNhanVien!");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGroupChucVu;
    private javax.swing.ButtonGroup btnGroupDiaChi;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.ButtonGroup btnGroupNgaySinh;
    private javax.swing.JButton btnLoc;
    private javax.swing.JButton btnReset;
    private javax.swing.JComboBox<String> cbxChucVu;
    private javax.swing.JComboBox<String> cbxHuyen;
    private javax.swing.JComboBox<String> cbxTinh;
    private javax.swing.JComboBox<String> cbxXa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JRadioButton rdbAllChucVu;
    private javax.swing.JRadioButton rdbAllDiaChi;
    private javax.swing.JRadioButton rdbAllGender;
    private javax.swing.JRadioButton rdbAllNgaySinh;
    private javax.swing.JRadioButton rdbChonChucVu;
    private javax.swing.JRadioButton rdbChonDiaChi;
    private javax.swing.JRadioButton rdbChonNgaySinh;
    private javax.swing.JRadioButton rdbKhac;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTextField txtNgayBatDau;
    private javax.swing.JTextField txtNgayKetThuc;
    // End of variables declaration//GEN-END:variables
}
