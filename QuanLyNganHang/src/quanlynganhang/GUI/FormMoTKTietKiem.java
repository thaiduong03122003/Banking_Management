/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ItemEvent;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.MaHoaMatKhauBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TietKiemBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.FormatNumber;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.KyHanGuiTKDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.TietKiemDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class FormMoTKTietKiem extends javax.swing.JPanel {

    private KhachHangBUS khachHangBUS;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private GiaoDichBUS giaoDichBUS;
    private TietKiemBUS tietKiemBUS;
    private FormatDate fDate;
    private int maKhachHang;
    private Integer maTaiKhoanKH, maKyHan;
    private BigInteger soDu, soTien, minSoTienGD, maxSoTienGD;
    private TaiKhoanNVDTO taiKhoanNV;
    private TaiKhoanKHDTO taiKhoanNguon;
    private boolean isAutoGenerateSTK;

    public FormMoTKTietKiem(TaiKhoanNVDTO taiKhoanNV) {
        this.taiKhoanNV = taiKhoanNV;
        fDate = new FormatDate();
        khachHangBUS = new KhachHangBUS();
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        giaoDichBUS = new GiaoDichBUS();
        tietKiemBUS = new TietKiemBUS();
        minSoTienGD = new BigInteger("1000000");
        maxSoTienGD = new BigInteger("2000000000");
        maKhachHang = 0;
        maTaiKhoanKH = 0;
        maKyHan = 0;
        isAutoGenerateSTK = true;

        initComponents();
        initCustomUI();
        chxSTKTuDongActionPerformed(null);
        loadKyHan();
    }

    private void initCustomUI() {
        jPCustomerInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccountInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTKInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPNguonTien.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTKNguon.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTienTietKiem.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPKyHan.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPGiaHan.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPNhanLai.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPStkTietKiem.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTenTKTietKiem.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPFooterCus.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCusNameInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPIdCentizenCard.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccName.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCusYOB.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPGender.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAddress.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPPhoneNum.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");

        txtTienTietKiem.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tối thiểu 1.000.000");
        txtCCCD.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtNgaySinh.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtDiaChi.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtSdt.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");

        jPTKNguon.setVisible(false);
    }

    public void dienThongTinKH(int maKhachHang) {
        KhachHangDTO khachHang = khachHangBUS.getKhachHangById(maKhachHang, 0);
        if (khachHang != null) {
            this.maKhachHang = maKhachHang;
            lbHotenKH.setText(khachHang.getHoDem() + " " + khachHang.getTen());
            lbMaKH.setText("" + khachHang.getMaKH());
            txtCCCD.setText(khachHang.getCccd());
            txtNgaySinh.setText(fDate.toString(khachHang.getNgaySinh()));
            txtEmail.setText(khachHang.getEmail());
            txtSdt.setText(khachHang.getSdt());
            txtDiaChi.setText(khachHang.getDiaChi());

            if (khachHang.getGioiTinh().equals("Nam")) {
                rdbNam.setSelected(true);
            } else if (khachHang.getGioiTinh().equals("Nữ")) {
                rdbNu.setSelected(true);
            } else {
                rdbKhac.setSelected(true);
            }

            loadTKNguon();
        }
    }

    private void reSetThongTinTKNguon() {
        lbSTK.setText("(chưa chọn)");
        lbLoaiTK.setText("(chưa chọn)");
        lbTenTK.setText("(chưa chọn)");
    }

    private void loadTKNguon() {
        Map<Integer, String> map = new HashMap<>();
        map = taiKhoanKHBUS.convertListTKNguonToMap(maKhachHang);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn tài khoản nguồn-");

        if (map != null) {
            for (String tenNganHang : map.values()) {
                model.addElement(tenNganHang);
            }
        }

        cbxTKNguon.setModel(model);

        reSetThongTinTKNguon();
    }

    private void loadKyHan() {
        Map<Integer, String> map = new HashMap<>();
        map = tietKiemBUS.convertListKyHanToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn kỳ hạn-");

        for (String tenKyHan : map.values()) {
            model.addElement(tenKyHan);
        }

        cbxKyHan.setModel(model);
    }

    private void dienThongTinTKNguon(int maTaiKhoanKH) {
        TaiKhoanKHDTO taiKhoanKH = taiKhoanKHBUS.getTaiKhoanKHById(maTaiKhoanKH);

        if (taiKhoanKH != null) {
            taiKhoanNguon = taiKhoanKH;

            this.soDu = new BigInteger(taiKhoanKH.getSoDu());

            lbSTK.setText(taiKhoanKH.getSoTaiKhoan());
            lbLoaiTK.setText(InputValidation.catNganString(taiKhoanKH.getTenLoaiTaiKhoan()));
            lbTenTK.setText(taiKhoanKH.getTenTaiKhoan());

        }
    }

    private void taoTaiKhoanTK() {
        StringBuilder error = new StringBuilder();
        error.append("");

        boolean isTienTK = rdbTaiKhoan.isSelected();

        if (rdbTaiKhoan.isSelected() && maTaiKhoanKH == 0) {
            error.append("Vui lòng chọn tài khoản nguồn!");
        }

        if (txtTienTietKiem.getText().isEmpty() || cbxKyHan.getSelectedIndex() == 0 || cbxGiaHan.getSelectedIndex() == 0 || cbxNhanLai.getSelectedIndex() == 0 || (txtSTKTietKiem.getText().isEmpty() && !isAutoGenerateSTK) || txtTenTKTietKiem.getText().isEmpty()) {
            error.append("Vui lòng nhập đầu đủ thông tin");
        } else {
            if (InputValidation.kiemTraSoTien(txtTienTietKiem.getText().trim().replace(",", ""))) {
                soTien = new BigInteger(txtTienTietKiem.getText().trim().replace(",", ""));
                if (soTien.compareTo(maxSoTienGD) > 0 || soTien.compareTo(minSoTienGD) < 0) {
                    error.append("\nSố tiền tiết kiệm nằm trong khoảng 1 triệu VND và 2 tỷ VND cho một lần gửi!");
                }

                if (rdbTaiKhoan.isSelected() && cbxTKNguon.getSelectedIndex() != 0 && soTien.compareTo(soDu) > 0) {
                    error.append("\nSố dư không đủ để thực hiện giao dịch!");
                }
            } else {
                error.append("\nVui lòng nhập đúng số tiền giao dịch!");
            }
        }

        GiaoDichDTO giaoDich = new GiaoDichDTO();
        TaiKhoanKHDTO taiKhoanGTK = new TaiKhoanKHDTO();
        TietKiemDTO tietKiem = new TietKiemDTO();

        if (InputValidation.kiemTraTen(txtTenTKTietKiem.getText())) {
            taiKhoanGTK.setTenTaiKhoan(txtTenTKTietKiem.getText().trim());
        } else {
            error.append("\nTên tài khoản không hợp lệ");
        }

        if (isAutoGenerateSTK) {
            String soTaiKhoanMoi = taiKhoanKHBUS.taoSTKTuDong();

            if (soTaiKhoanMoi.equals("")) {
                error.append("\nSố tài khoản bị lỗi");
            } else {
                txtSTKTietKiem.setText(soTaiKhoanMoi);
                taiKhoanGTK.setSoTaiKhoan(soTaiKhoanMoi);
            }
        } else {
            if (InputValidation.kiemTraCCCD(txtSTKTietKiem.getText())) {
                taiKhoanGTK.setSoTaiKhoan(txtSTKTietKiem.getText());
            } else {
                error.append("\nSố tài khoản không hợp lệ");
            }
        }

        if (!error.isEmpty()) {
            MessageBox.showErrorMessage(null, "Lỗi: " + error);
            return;
        }

        if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn tạo tài khoản tiết kiệm cho khách hàng này?") == JOptionPane.NO_OPTION) {
            return;
        }

        taiKhoanGTK.setMaKhachHang(maKhachHang);
        taiKhoanGTK.setSoDu("0");
        taiKhoanGTK.setMatKhau(MaHoaMatKhauBUS.encryptPassword("123"));
        taiKhoanGTK.setMaLoaiTaiKhoan(3);
        taiKhoanGTK.setMaNganHang(1);
        taiKhoanGTK.setMaTrangThai(6);
        taiKhoanGTK.setNgayTao(fDate.getToday());

        int maTaiKhoanGTK = taiKhoanKHBUS.addTaiKhoanKH(taiKhoanGTK);

        if (isTienTK) {
            giaoDich.setMaTaiKhoanKH(taiKhoanNguon.getMaTKKH());
            tietKiem.setMaTaiKhoanNguonTien(taiKhoanNguon.getMaTKKH());
            giaoDich.setNgayGiaoDich(fDate.getToday());
            giaoDich.setSoTien(txtTienTietKiem.getText().trim().replace(",", ""));
            giaoDich.setNoiDungGiaoDich("Chuyển tiền để gửi tiết kiệm đến tài khoản " + taiKhoanGTK.getSoTaiKhoan());
            giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaTKNV());
            giaoDich.setMaLoaiGiaoDich(5);
            giaoDich.setMaTrangThai(4);
        } else {
            giaoDich.setMaTaiKhoanKH(maTaiKhoanGTK);
            tietKiem.setMaTaiKhoanNguonTien(0);
            giaoDich.setNgayGiaoDich(fDate.getToday());
            giaoDich.setSoTien(txtTienTietKiem.getText().trim().replace(",", ""));
            giaoDich.setNoiDungGiaoDich("Nhận tiền để gửi tiết kiệm từ nguồn tiền mặt");
            giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaTKNV());
            giaoDich.setMaLoaiGiaoDich(5);
            giaoDich.setMaTrangThai(4);
        }

        KyHanGuiTKDTO kyHanGui = tietKiemBUS.getKyHanById(maKyHan);
        if (kyHanGui != null) {
            tietKiem.setNgayNhanLai(fDate.addMonth(kyHanGui.getSoKyHan()));
            System.out.println("So ngay den nhan lai: " + fDate.toString(tietKiem.getNgayNhanLai()));
        } else {
            MessageBox.showErrorMessage(null, "Lấy thông tin kỳ hạn thất bại!");
        }

        tietKiem.setMaKyHan(maKyHan);
        tietKiem.setHinhThucGiaHan(cbxGiaHan.getSelectedItem().toString());
        tietKiem.setHinhThucNhanLai(cbxNhanLai.getSelectedItem().toString());
        tietKiem.setNgayMoTK(fDate.getToday());
        tietKiem.setSoTienGoc(txtTienTietKiem.getText().trim().replace(",", ""));
        tietKiem.setMaTrangThai(10);

        if (maTaiKhoanGTK == 0) {
            MessageBox.showErrorMessage(null, "Số tài khoản đã tồn tại trong hệ thống!");
            return;
        }

        tietKiem.setMaTaiKhoanTK(maTaiKhoanGTK);
        taiKhoanGTK.setMaTKKH(maTaiKhoanGTK);

        if (isTienTK) {
            if (giaoDichBUS.chuyenTien(giaoDich, isTienTK, taiKhoanGTK, 1) && tietKiemBUS.addGuiTietKiem(tietKiem) != 0) {
                MessageBox.showInformationMessage(null, "", "Tạo tài khoản gửi tiết kiệm thành công!");
            } else {
                MessageBox.showErrorMessage(null, "Gửi tiết kiệm thất bại");
            }
        } else {
            if (giaoDichBUS.napTien(giaoDich) && tietKiemBUS.addGuiTietKiem(tietKiem) != 0) {
                MessageBox.showInformationMessage(null, "", "Tạo tài khoản gửi tiết kiệm thành công!");
            } else {
                MessageBox.showErrorMessage(null, "Gửi tiết kiệm thất bại");
            }
        }
    }

    private void onCodeTextChanged() {
        String currency = txtTienTietKiem.getText().trim().replace(",", "");

        if (InputValidation.kiemTraSoTien(currency)) {
            txtTienTietKiem.setText(FormatNumber.convertNumToVND(new BigInteger(currency.trim())));
        } else {
            MessageBox.showErrorMessage(null, "Định dạng nhập không đúng!");
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

        btnGroupGender = new javax.swing.ButtonGroup();
        btnGroupMoney = new javax.swing.ButtonGroup();
        jPCustomerInfo = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPNguonTien = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        rdbTienMat = new javax.swing.JRadioButton();
        rdbTaiKhoan = new javax.swing.JRadioButton();
        jPTienTietKiem = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTienTietKiem = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPFooterCus = new javax.swing.JPanel();
        btnTaoTKTK = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnChonKH = new javax.swing.JButton();
        jPTKNguon = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cbxTKNguon = new javax.swing.JComboBox<>();
        jPKyHan = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cbxKyHan = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPGiaHan = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        cbxGiaHan = new javax.swing.JComboBox<>();
        jPNhanLai = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        cbxNhanLai = new javax.swing.JComboBox<>();
        jPStkTietKiem = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSTKTietKiem = new javax.swing.JTextField();
        chxSTKTuDong = new javax.swing.JCheckBox();
        jPTenTKTietKiem = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenTKTietKiem = new javax.swing.JTextField();
        jPAccountInfo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPCusNameInfo = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbHotenKH = new javax.swing.JLabel();
        jPIdCentizenCard = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jPCusYOB = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jPAddress = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jPAccName = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lbMaKH = new javax.swing.JLabel();
        jPPhoneNum = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jPGender = new javax.swing.JPanel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        rdbKhac = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        jPTKInfo = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel15 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lbSTK = new javax.swing.JLabel();
        lbTenTK = new javax.swing.JLabel();
        lbLoaiTK = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1132, 511));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setText("Thông tin tài khoản tiết kiện");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setIcon(new FlatSVGIcon("quanlynganhang/icon/thunhap_label.svg")
        );
        jLabel3.setText("Nguồn tiền");

        btnGroupMoney.add(rdbTienMat);
        rdbTienMat.setSelected(true);
        rdbTienMat.setText("Tiền mặt");
        rdbTienMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbTienMatActionPerformed(evt);
            }
        });

        btnGroupMoney.add(rdbTaiKhoan);
        rdbTaiKhoan.setText("Từ tài khoản");
        rdbTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbTaiKhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPNguonTienLayout = new javax.swing.GroupLayout(jPNguonTien);
        jPNguonTien.setLayout(jPNguonTienLayout);
        jPNguonTienLayout.setHorizontalGroup(
            jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNguonTienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbTienMat)
                    .addComponent(rdbTaiKhoan))
                .addContainerGap(57, Short.MAX_VALUE))
        );
        jPNguonTienLayout.setVerticalGroup(
            jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNguonTienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbTienMat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbTaiKhoan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setIcon(new FlatSVGIcon("quanlynganhang/icon/money_label.svg")
        );
        jLabel4.setText("Số tiền tiết kiệm");

        txtTienTietKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTienTietKiemKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("VND");

        javax.swing.GroupLayout jPTienTietKiemLayout = new javax.swing.GroupLayout(jPTienTietKiem);
        jPTienTietKiem.setLayout(jPTienTietKiemLayout);
        jPTienTietKiemLayout.setHorizontalGroup(
            jPTienTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTienTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTienTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPTienTietKiemLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPTienTietKiemLayout.createSequentialGroup()
                        .addComponent(txtTienTietKiem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(17, 17, 17))))
        );
        jPTienTietKiemLayout.setVerticalGroup(
            jPTienTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTienTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPTienTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTienTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btnTaoTKTK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoTKTK.setText("Tạo tài khoản tiết kiệm");
        btnTaoTKTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoTKTKActionPerformed(evt);
            }
        });

        jButton3.setText("Đặt lại");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPFooterCusLayout = new javax.swing.GroupLayout(jPFooterCus);
        jPFooterCus.setLayout(jPFooterCusLayout);
        jPFooterCusLayout.setHorizontalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterCusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTaoTKTK, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPFooterCusLayout.setVerticalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterCusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoTKTK)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnChonKH.setText("Chọn khách hàng");
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel6.setText("Tài khoản nguồn");

        cbxTKNguon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn tài khoản nguồn-" }));
        cbxTKNguon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTKNguonItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPTKNguonLayout = new javax.swing.GroupLayout(jPTKNguon);
        jPTKNguon.setLayout(jPTKNguonLayout);
        jPTKNguonLayout.setHorizontalGroup(
            jPTKNguonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKNguonLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTKNguonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxTKNguon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPTKNguonLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPTKNguonLayout.setVerticalGroup(
            jPTKNguonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKNguonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTKNguon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setIcon(new FlatSVGIcon("quanlynganhang/icon/date_create_label.svg")
        );
        jLabel5.setText("Kỳ hạn gửi");

        cbxKyHan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxKyHanItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPKyHanLayout = new javax.swing.GroupLayout(jPKyHan);
        jPKyHan.setLayout(jPKyHanLayout);
        jPKyHanLayout.setHorizontalGroup(
            jPKyHanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPKyHanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPKyHanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxKyHan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPKyHanLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 58, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPKyHanLayout.setVerticalGroup(
            jPKyHanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPKyHanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxKyHan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel27.setIcon(new FlatSVGIcon("quanlynganhang/icon/giahan_label.svg")
        );
        jLabel27.setText("Hình thức gia hạn");

        cbxGiaHan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn hình thức-", "Không gia hạn", "Gia hạn với số tiền gốc", "Gia hạn với cả gốc và lãi" }));
        cbxGiaHan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxGiaHanItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPGiaHanLayout = new javax.swing.GroupLayout(jPGiaHan);
        jPGiaHan.setLayout(jPGiaHanLayout);
        jPGiaHanLayout.setHorizontalGroup(
            jPGiaHanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPGiaHanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPGiaHanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxGiaHan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPGiaHanLayout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPGiaHanLayout.setVerticalGroup(
            jPGiaHanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPGiaHanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxGiaHan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel28.setIcon(new FlatSVGIcon("quanlynganhang/icon/nhan_lai_label.svg")
        );
        jLabel28.setText("Hình thức nhận lãi");

        cbxNhanLai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn hình thức-", "Chuyển về tài khoản tiết kiệm", "Chuyển về tài khoản nguồn" }));
        cbxNhanLai.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxNhanLaiItemStateChanged(evt);
            }
        });
        cbxNhanLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNhanLaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPNhanLaiLayout = new javax.swing.GroupLayout(jPNhanLai);
        jPNhanLai.setLayout(jPNhanLaiLayout);
        jPNhanLaiLayout.setHorizontalGroup(
            jPNhanLaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNhanLaiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPNhanLaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxNhanLai, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPNhanLaiLayout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 46, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPNhanLaiLayout.setVerticalGroup(
            jPNhanLaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNhanLaiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxNhanLai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel11.setText("Số tài khoản tiết kiệm");

        chxSTKTuDong.setSelected(true);
        chxSTKTuDong.setText("Tạo số tài khoản tự động");
        chxSTKTuDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chxSTKTuDongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPStkTietKiemLayout = new javax.swing.GroupLayout(jPStkTietKiem);
        jPStkTietKiem.setLayout(jPStkTietKiemLayout);
        jPStkTietKiemLayout.setHorizontalGroup(
            jPStkTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPStkTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPStkTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSTKTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chxSTKTuDong, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPStkTietKiemLayout.setVerticalGroup(
            jPStkTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPStkTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSTKTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chxSTKTuDong)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg")
        );
        jLabel12.setText("Tên tài khoản tiết kiệm");

        javax.swing.GroupLayout jPTenTKTietKiemLayout = new javax.swing.GroupLayout(jPTenTKTietKiem);
        jPTenTKTietKiem.setLayout(jPTenTKTietKiemLayout);
        jPTenTKTietKiemLayout.setHorizontalGroup(
            jPTenTKTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTenTKTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTenTKTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPTenTKTietKiemLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 17, Short.MAX_VALUE))
                    .addComponent(txtTenTKTietKiem))
                .addContainerGap())
        );
        jPTenTKTietKiemLayout.setVerticalGroup(
            jPTenTKTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTenTKTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTKTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPCustomerInfoLayout = new javax.swing.GroupLayout(jPCustomerInfo);
        jPCustomerInfo.setLayout(jPCustomerInfoLayout);
        jPCustomerInfoLayout.setHorizontalGroup(
            jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPFooterCus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPNguonTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPTKNguon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPGiaHan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPTienTietKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPKyHan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCustomerInfoLayout.createSequentialGroup()
                                .addComponent(jPNhanLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12))))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPStkTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(jPTenTKTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPCustomerInfoLayout.setVerticalGroup(
            jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitle)
                    .addComponent(btnChonKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPTKNguon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPNguonTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPKyHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPTienTietKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPGiaHan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPNhanLai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPTenTKTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPStkTietKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPFooterCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin khách hàng");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Họ tên khách hàng: ");

        lbHotenKH.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbHotenKH.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPCusNameInfoLayout = new javax.swing.GroupLayout(jPCusNameInfo);
        jPCusNameInfo.setLayout(jPCusNameInfoLayout);
        jPCusNameInfoLayout.setHorizontalGroup(
            jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbHotenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPCusNameInfoLayout.setVerticalGroup(
            jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbHotenKH))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel13.setIcon(new FlatSVGIcon("quanlynganhang/icon/id_card_label.svg")
        );
        jLabel13.setText("Mã căn cước công dân");

        txtCCCD.setEditable(false);
        txtCCCD.setEnabled(false);

        javax.swing.GroupLayout jPIdCentizenCardLayout = new javax.swing.GroupLayout(jPIdCentizenCard);
        jPIdCentizenCard.setLayout(jPIdCentizenCardLayout);
        jPIdCentizenCardLayout.setHorizontalGroup(
            jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPIdCentizenCardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPIdCentizenCardLayout.setVerticalGroup(
            jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPIdCentizenCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel14.setIcon(new FlatSVGIcon("quanlynganhang/icon/yearofbirth_label.svg")
        );
        jLabel14.setText("Ngày sinh");

        txtNgaySinh.setEditable(false);
        txtNgaySinh.setEnabled(false);

        javax.swing.GroupLayout jPCusYOBLayout = new javax.swing.GroupLayout(jPCusYOB);
        jPCusYOB.setLayout(jPCusYOBLayout);
        jPCusYOBLayout.setHorizontalGroup(
            jPCusYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusYOBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCusYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPCusYOBLayout.setVerticalGroup(
            jPCusYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusYOBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel16.setIcon(new FlatSVGIcon("quanlynganhang/icon/address_label.svg")
        );
        jLabel16.setText("Địa chỉ của khách hàng");

        txtDiaChi.setEditable(false);
        txtDiaChi.setEnabled(false);

        javax.swing.GroupLayout jPAddressLayout = new javax.swing.GroupLayout(jPAddress);
        jPAddress.setLayout(jPAddressLayout);
        jPAddressLayout.setHorizontalGroup(
            jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAddressLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtDiaChi))
                .addContainerGap())
        );
        jPAddressLayout.setVerticalGroup(
            jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel17.setText("Email");

        txtEmail.setEditable(false);
        txtEmail.setEnabled(false);

        javax.swing.GroupLayout jPAccNameLayout = new javax.swing.GroupLayout(jPAccName);
        jPAccName.setLayout(jPAccNameLayout);
        jPAccNameLayout.setHorizontalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccNameLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 94, Short.MAX_VALUE))
                    .addComponent(txtEmail))
                .addContainerGap())
        );
        jPAccNameLayout.setVerticalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel18.setText("Mã khách hàng: ");

        lbMaKH.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaKH.setText("(Chưa chọn)");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel20.setIcon(new FlatSVGIcon("quanlynganhang/icon/phone_label.svg")
        );
        jLabel20.setText("Số điện thoại");

        txtSdt.setEditable(false);
        txtSdt.setEnabled(false);

        javax.swing.GroupLayout jPPhoneNumLayout = new javax.swing.GroupLayout(jPPhoneNum);
        jPPhoneNum.setLayout(jPPhoneNumLayout);
        jPPhoneNumLayout.setHorizontalGroup(
            jPPhoneNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhoneNumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPhoneNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSdt)
                    .addGroup(jPPhoneNumLayout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 36, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPPhoneNumLayout.setVerticalGroup(
            jPPhoneNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhoneNumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGroupGender.add(rdbNam);
        rdbNam.setSelected(true);
        rdbNam.setText("Nam");
        rdbNam.setEnabled(false);

        btnGroupGender.add(rdbNu);
        rdbNu.setText("Nữ");
        rdbNu.setEnabled(false);

        btnGroupGender.add(rdbKhac);
        rdbKhac.setText("Khác");
        rdbKhac.setEnabled(false);

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel21.setIcon(new FlatSVGIcon("quanlynganhang/icon/gender_label.svg")
        );
        jLabel21.setText("Giới tính");

        javax.swing.GroupLayout jPGenderLayout = new javax.swing.GroupLayout(jPGender);
        jPGender.setLayout(jPGenderLayout);
        jPGenderLayout.setHorizontalGroup(
            jPGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPGenderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPGenderLayout.createSequentialGroup()
                        .addComponent(rdbNam)
                        .addGap(18, 18, 18)
                        .addComponent(rdbNu)
                        .addGap(18, 18, 18)
                        .addComponent(rdbKhac)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPGenderLayout.setVerticalGroup(
            jPGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPGenderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbNam)
                    .addComponent(rdbNu)
                    .addComponent(rdbKhac))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPAccountInfoLayout = new javax.swing.GroupLayout(jPAccountInfo);
        jPAccountInfo.setLayout(jPAccountInfoLayout);
        jPAccountInfoLayout.setHorizontalGroup(
            jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addComponent(jPIdCentizenCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPAccName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addComponent(jPCusYOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPCusNameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                                .addComponent(jPPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPAccountInfoLayout.setVerticalGroup(
            jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPCusNameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbMaKH))
                .addGap(18, 18, 18)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPIdCentizenCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPAccName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPCusYOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Thông tin tài khoản nguồn");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel15.setText("Số tài khoản:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel22.setText("Tên tài khoản:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel23.setText("Loại tài khoản:");

        lbSTK.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbSTK.setText("(chưa chọn)");

        lbTenTK.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbTenTK.setText("(chưa chọn)");

        lbLoaiTK.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbLoaiTK.setText("(chưa chọn)");

        javax.swing.GroupLayout jPTKInfoLayout = new javax.swing.GroupLayout(jPTKInfo);
        jPTKInfo.setLayout(jPTKInfoLayout);
        jPTKInfoLayout.setHorizontalGroup(
            jPTKInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTKInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPTKInfoLayout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbTenTK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPTKInfoLayout.createSequentialGroup()
                        .addGroup(jPTKInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPTKInfoLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbSTK, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbLoaiTK, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPTKInfoLayout.setVerticalGroup(
            jPTKInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPTKInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel23)
                    .addComponent(lbSTK)
                    .addComponent(lbLoaiTK))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPTKInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(lbTenTK))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPAccountInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPTKInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPCustomerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPAccountInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPTKInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void rdbTienMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbTienMatActionPerformed
        if (rdbTienMat.isSelected()) {
            jPTKNguon.setVisible(false);
        } else {
            jPTKNguon.setVisible(true);
        }

        if (cbxNhanLai.getSelectedIndex() == 2 && rdbTienMat.isSelected()) {
            MessageBox.showErrorMessage(null, "Không thể chọn hình thức này!");
            rdbTaiKhoan.setSelected(true);
            return;
        }
    }//GEN-LAST:event_rdbTienMatActionPerformed

    private void rdbTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbTaiKhoanActionPerformed
        if (rdbTaiKhoan.isSelected()) {
            jPTKNguon.setVisible(true);
        } else {
            jPTKNguon.setVisible(false);
        }
    }//GEN-LAST:event_rdbTaiKhoanActionPerformed

    private void cbxGiaHanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxGiaHanItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (cbxNhanLai.getSelectedIndex() == 2 && cbxGiaHan.getSelectedIndex() == 3) {
                MessageBox.showErrorMessage(null, "Bạn không thể gia hạn cả gốc lẫn lãi nếu tiền lãi chuyển về TK nguồn!");
                cbxGiaHan.setSelectedIndex(0);
                return;
            }
        }
    }//GEN-LAST:event_cbxGiaHanItemStateChanged

    private void btnTaoTKTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoTKTKActionPerformed
        if (lbHotenKH.getText().trim().equals("(Chưa chọn)")) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn khách hàng!");
            return;
        } else {
            taoTaiKhoanTK();
        }
    }//GEN-LAST:event_btnTaoTKTKActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        JDialogTableChonItem chonNV = new JDialogTableChonItem(null, true, this, "Chọn khách hàng", "DSKH", true);
        chonNV.setResizable(false);
        chonNV.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonNV.setVisible(true);
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void cbxTKNguonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTKNguonItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String tenTKNguon = (String) cbxTKNguon.getSelectedItem();
            if (tenTKNguon.equals("-Chọn tài khoản nguồn-")) {
                maTaiKhoanKH = 0;
                reSetThongTinTKNguon();
            } else {
                maTaiKhoanKH = taiKhoanKHBUS.getIdFromSTKNguon(tenTKNguon, maKhachHang);

                if (maTaiKhoanKH == null) {
                    MessageBox.showErrorMessage(null, "Lấy id của tài khoản nguồn thất bại!");
                    return;
                }

                dienThongTinTKNguon(maTaiKhoanKH);
            }
        }
    }//GEN-LAST:event_cbxTKNguonItemStateChanged

    private void cbxNhanLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNhanLaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxNhanLaiActionPerformed

    private void cbxNhanLaiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxNhanLaiItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (cbxNhanLai.getSelectedIndex() == 2 && cbxGiaHan.getSelectedIndex() == 3) {
                MessageBox.showErrorMessage(null, "Bạn không thể gia hạn cả gốc lẫn lãi nếu tiền lãi chuyển về TK nguồn!");
                cbxNhanLai.setSelectedIndex(0);
                return;
            }
        }

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (cbxNhanLai.getSelectedIndex() == 2 && rdbTienMat.isSelected()) {
                MessageBox.showErrorMessage(null, "Không thể chọn hình thức này!");
                cbxNhanLai.setSelectedIndex(0);
                return;
            }
        }

    }//GEN-LAST:event_cbxNhanLaiItemStateChanged

    private void cbxKyHanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxKyHanItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String tenKyHan = (String) cbxKyHan.getSelectedItem();
            if (tenKyHan.equals("-Chọn kỳ hạn-")) {
                maKyHan = 0;
            } else {
                maKyHan = tietKiemBUS.getIdFromTenKyHan(tenKyHan);

                if (maKyHan == null) {
                    MessageBox.showErrorMessage(null, "Lấy id của kỳ hạn thất bại!");
                    return;
                }
            }
        }
    }//GEN-LAST:event_cbxKyHanItemStateChanged

    private void chxSTKTuDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxSTKTuDongActionPerformed
        if (chxSTKTuDong.isSelected()) {
            txtSTKTietKiem.setText("");
            txtSTKTietKiem.setEnabled(false);
            isAutoGenerateSTK = true;
        } else {
            txtSTKTietKiem.setEnabled(true);
            isAutoGenerateSTK = false;
        }
    }//GEN-LAST:event_chxSTKTuDongActionPerformed

    private void txtTienTietKiemKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTienTietKiemKeyReleased
        if (txtTienTietKiem.getText().trim().isEmpty()) {
            return;
        }
        onCodeTextChanged();
    }//GEN-LAST:event_txtTienTietKiemKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonKH;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.ButtonGroup btnGroupMoney;
    private javax.swing.JButton btnTaoTKTK;
    private javax.swing.JComboBox<String> cbxGiaHan;
    private javax.swing.JComboBox<String> cbxKyHan;
    private javax.swing.JComboBox<String> cbxNhanLai;
    private javax.swing.JComboBox<String> cbxTKNguon;
    private javax.swing.JCheckBox chxSTKTuDong;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPAccName;
    private javax.swing.JPanel jPAccountInfo;
    private javax.swing.JPanel jPAddress;
    private javax.swing.JPanel jPCusNameInfo;
    private javax.swing.JPanel jPCusYOB;
    private javax.swing.JPanel jPCustomerInfo;
    private javax.swing.JPanel jPFooterCus;
    private javax.swing.JPanel jPGender;
    private javax.swing.JPanel jPGiaHan;
    private javax.swing.JPanel jPIdCentizenCard;
    private javax.swing.JPanel jPKyHan;
    private javax.swing.JPanel jPNguonTien;
    private javax.swing.JPanel jPNhanLai;
    private javax.swing.JPanel jPPhoneNum;
    private javax.swing.JPanel jPStkTietKiem;
    private javax.swing.JPanel jPTKInfo;
    private javax.swing.JPanel jPTKNguon;
    private javax.swing.JPanel jPTenTKTietKiem;
    private javax.swing.JPanel jPTienTietKiem;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbHotenKH;
    private javax.swing.JLabel lbLoaiTK;
    private javax.swing.JLabel lbMaKH;
    private javax.swing.JLabel lbSTK;
    private javax.swing.JLabel lbTenTK;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JRadioButton rdbKhac;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JRadioButton rdbTaiKhoan;
    private javax.swing.JRadioButton rdbTienMat;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSTKTietKiem;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTenTKTietKiem;
    private javax.swing.JTextField txtTienTietKiem;
    // End of variables declaration//GEN-END:variables
}
