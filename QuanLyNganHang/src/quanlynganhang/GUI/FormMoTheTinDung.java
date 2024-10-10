package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.MaHoaMatKhauBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TheATMBUS;
import quanlynganhang.BUS.VayVonBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.FormatNumber;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.TheATMDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class FormMoTheTinDung extends javax.swing.JPanel {

    private TaiKhoanKHBUS taiKhoanKHBUS;
    private KhachHangBUS khachHangBUS;
    private TheATMBUS theATMBUS;
    private TaiKhoanNVDTO taiKhoanNV;
    private TaiKhoanKHDTO taiKhoanKH;
    private FormatDate fDate;
    private int loaiThe;
    private boolean isAutoGenerate;
    private final BigInteger MAX_HAN_MUC = new BigInteger("500000000");
    private final BigInteger MIN_HAN_MUC = new BigInteger("1000000");

    public FormMoTheTinDung(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) {
        this.taiKhoanNV = taiKhoanNV;
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        khachHangBUS = new KhachHangBUS();
        theATMBUS = new TheATMBUS();
        fDate = new FormatDate();
        loaiThe = 3;

        initComponents();
        initCustomUI();
    }

    private void initCustomUI() {
        jPCustomerInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccountInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCardNum.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCardName.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPPINCode.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCardType.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPHanMucSD.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPThuNhap.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPPhysicalCard.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPRankCard.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPFooterCus.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCusNameInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccNum.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccType.putClientProperty(FlatClientProperties.STYLE, ""
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

        pwfMaPIN.putClientProperty(FlatClientProperties.STYLE, ""
            + "showRevealButton:true;");

        txtSoThe.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập đủ 12 số");
        pwfMaPIN.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập đủ 6 số");
        txtSTK.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtTenTK.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtCCCD.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtLoaiTK.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtNgaySinh.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtDiaChi.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtSdt.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        jPRankCard.setVisible(false);
    }

    public void dienThongTinTKKH(int maTaiKhoanKH) {
        TaiKhoanKHDTO taiKhoanKH = taiKhoanKHBUS.getTaiKhoanKHById(maTaiKhoanKH);

        if (taiKhoanKH != null) {
            if (taiKhoanKH.getMaLoaiTaiKhoan() != 1 && taiKhoanKH.getMaLoaiTaiKhoan() != 2) {
                MessageBox.showErrorMessage(null, "Không thể tạo thẻ cho tài khoản này!");
            } else if (taiKhoanKH.getMaTrangThai() != 6) {
                MessageBox.showErrorMessage(null, "Vui lòng kích hoạt tài khoản trước khi sử dụng!");
            } else {
                this.taiKhoanKH = taiKhoanKH;
                txtTenThe.setText(taiKhoanKH.getTenTaiKhoan());

                KhachHangDTO khachHang = khachHangBUS.getKhachHangById(taiKhoanKH.getMaKhachHang(), 0);

                if (khachHang != null) {
                    lbHotenKH.setText(taiKhoanKH.getTenKhachHang());
                    lbMaTK.setText("" + taiKhoanKH.getMaTKKH());
                    txtSTK.setText(taiKhoanKH.getSoTaiKhoan());
                    txtTenTK.setText(taiKhoanKH.getTenTaiKhoan());
                    txtCCCD.setText(khachHang.getCccd());
                    txtNgaySinh.setText(fDate.toString(khachHang.getNgaySinh()));
                    txtLoaiTK.setText(taiKhoanKH.getTenLoaiTaiKhoan());
                    txtDiaChi.setText(khachHang.getDiaChi());
                    txtSdt.setText(khachHang.getSdt());

                    if (khachHang.getGioiTinh().equals("Nam")) {
                        rdbNam.setSelected(true);
                    } else if (khachHang.getGioiTinh().equals("Nữ")) {
                        rdbNu.setSelected(true);
                    } else {
                        rdbKhac.setSelected(true);
                    }
                } else {
                    MessageBox.showErrorMessage(null, "Không tìm thấy thông tin khách hàng!");
                }
            }
        }
    }

    private void taoThe() {
        StringBuilder error = new StringBuilder();
        error.append("");
        String maPIN = String.valueOf(pwfMaPIN.getPassword());
        String hanMuc = "";
        String thuNhap = "";
        TheATMDTO theATM = new TheATMDTO();

        if ((txtSoThe.getText().isEmpty() && !isAutoGenerate) || txtTenThe.getText().trim().isEmpty() || maPIN.trim().isEmpty() || txtHanMucSD.getText().trim().isEmpty() || txtThuNhap.getText().trim().isEmpty()) {
            error.append("\nVui lòng điền đầy đủ thông tin!");
        } else {

            if (!isAutoGenerate) {
                if (InputValidation.kiemTraCCCD(txtSoThe.getText())) {
                    theATM.setSoThe(txtSoThe.getText().trim());
                } else {
                    error.append("\nMã số thẻ không hợp lệ");
                }
            } else {
                String soTheMoi = theATMBUS.taoSoTheTuDong();

                if (soTheMoi.equals("")) {
                    error.append("\nMã số thẻ bị lỗi");
                } else {
                    txtSoThe.setText(soTheMoi);
                    theATM.setSoThe(soTheMoi);
                }
            }

            if (!InputValidation.kiemTraMaPIN(maPIN)) {
                error.append("\nMã PIN không hợp lệ");
            }

            String soTienHanMuc = txtHanMucSD.getText().trim().replace(",", "");
            if (!InputValidation.kiemTraSoTien(soTienHanMuc)) {
                error.append("\nSố tiền hạn mức không hợp lệ");
            } else {
                BigInteger tienHanMuc = new BigInteger(soTienHanMuc);
                if (tienHanMuc.compareTo(MIN_HAN_MUC) < 0 || tienHanMuc.compareTo(MAX_HAN_MUC) > 0) {
                    error.append("\nSố tiền hạm mức nằm trong khoảng 1 triệu VND và 500 triệu VND cho chủ thẻ!");
                } else {
                    hanMuc = soTienHanMuc;
                }
            }

            if (!InputValidation.kiemTraMaPIN(maPIN)) {
                error.append("\nMã PIN không hợp lệ");
            }

            if (!InputValidation.kiemTraTen(txtTenThe.getText())) {
                error.append("\nTên in trên thẻ không hợp lệ");
            }
            
            String soTienThuNhap = txtThuNhap.getText().trim().replace(",", "");
            if (!InputValidation.kiemTraSoTien(soTienThuNhap)) {
                error.append("\nSố tiền hạn mức không hợp lệ");
            } else {
                thuNhap = soTienThuNhap;
            }
        }

        if (!error.isEmpty()) {
            MessageBox.showErrorMessage(null, "Lỗi: " + error);
            return;
        }

        if (!(new VayVonBUS().kiemTraDieuKienVay(thuNhap, hanMuc, 7))) {
            MessageBox.showErrorMessage(null, "Thu nhập của người này không đủ điều kiện để vay!");
            return;
        }
        
        if (MessageBox.showConfirmMessage(null, "Xác nhận tạo thẻ cho số tài khoản " + taiKhoanKH.getSoTaiKhoan() + "?") == JOptionPane.NO_OPTION) {
            return;
        }

        theATM.setSoThe(txtSoThe.getText());
        theATM.setTenThe(txtTenThe.getText());
        theATM.setMaPIN(MaHoaMatKhauBUS.encryptPassword(maPIN));
        theATM.setNgayTao(fDate.getToday());
        theATM.setThoiHanThe(fDate.addMonth(60));
        theATM.setMaTaiKhoanKH(taiKhoanKH.getMaTKKH());
        theATM.setMaLoaiThe(loaiThe);

        if (theATMBUS.addTheATM(theATM) != 0) {
            MessageBox.showInformationMessage(null, "", "Tạo thẻ tín dụng thành công!");
        } else {
            MessageBox.showErrorMessage(null, "Số thẻ đã tồn tại, vui lòng kiểm tra lại!");
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

        btnGroupCardType = new javax.swing.ButtonGroup();
        btnGroupGender = new javax.swing.ButtonGroup();
        btnGroupPhysicalCard = new javax.swing.ButtonGroup();
        btnGroupRankCard = new javax.swing.ButtonGroup();
        jPCustomerInfo = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPCardNum = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtSoThe = new javax.swing.JTextField();
        btnTaoMa = new javax.swing.JButton();
        jPPINCode = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pwfMaPIN = new javax.swing.JPasswordField();
        jPCardType = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rdbNoiDia = new javax.swing.JRadioButton();
        rdbQuocTe = new javax.swing.JRadioButton();
        jPHanMucSD = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtHanMucSD = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPPhysicalCard = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jPRankCard = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jPFooterCus = new javax.swing.JPanel();
        btnTaoThe = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnChonKH = new javax.swing.JButton();
        jPCardName = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtTenThe = new javax.swing.JTextField();
        jPThuNhap = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtThuNhap = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPAccountInfo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPCusNameInfo = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbHotenKH = new javax.swing.JLabel();
        jPAccNum = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSTK = new javax.swing.JTextField();
        jPAccType = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenTK = new javax.swing.JTextField();
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
        txtLoaiTK = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lbMaTK = new javax.swing.JLabel();
        jPPhoneNum = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jPGender = new javax.swing.JPanel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        rdbKhac = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1132, 511));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setText("Thông tin thẻ tín dụng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setIcon(new FlatSVGIcon("quanlynganhang/icon/customer_label.svg")
        );
        jLabel3.setText("Mã thẻ");

        btnTaoMa.setText("Tạo mã");
        btnTaoMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoMaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPCardNumLayout = new javax.swing.GroupLayout(jPCardNum);
        jPCardNum.setLayout(jPCardNumLayout);
        jPCardNumLayout.setHorizontalGroup(
            jPCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCardNumLayout.createSequentialGroup()
                        .addComponent(txtSoThe, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTaoMa, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                    .addGroup(jPCardNumLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPCardNumLayout.setVerticalGroup(
            jPCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnTaoMa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSoThe, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setIcon(new FlatSVGIcon("quanlynganhang/icon/pin_code_label.svg")
        );
        jLabel4.setText("Mã PIN");

        javax.swing.GroupLayout jPPINCodeLayout = new javax.swing.GroupLayout(jPPINCode);
        jPPINCode.setLayout(jPPINCodeLayout);
        jPPINCodeLayout.setHorizontalGroup(
            jPPINCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPINCodeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPINCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pwfMaPIN, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPPINCodeLayout.setVerticalGroup(
            jPPINCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPINCodeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pwfMaPIN, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setIcon(new FlatSVGIcon("quanlynganhang/icon/gender_label.svg")
        );
        jLabel5.setText("Phạm vi sử dụng");

        btnGroupCardType.add(rdbNoiDia);
        rdbNoiDia.setSelected(true);
        rdbNoiDia.setText("Nội địa");
        rdbNoiDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbNoiDiaActionPerformed(evt);
            }
        });

        btnGroupCardType.add(rdbQuocTe);
        rdbQuocTe.setText("Quốc tế");
        rdbQuocTe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbQuocTeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPCardTypeLayout = new javax.swing.GroupLayout(jPCardType);
        jPCardType.setLayout(jPCardTypeLayout);
        jPCardTypeLayout.setHorizontalGroup(
            jPCardTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbNoiDia)
                    .addComponent(rdbQuocTe, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPCardTypeLayout.setVerticalGroup(
            jPCardTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(8, 8, 8)
                .addComponent(rdbNoiDia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdbQuocTe)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setIcon(new FlatSVGIcon("quanlynganhang/icon/money_label.svg")
        );
        jLabel6.setText("Hạn mức tín dụng mong muốn");

        txtHanMucSD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtHanMucSDKeyReleased(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("VND");

        javax.swing.GroupLayout jPHanMucSDLayout = new javax.swing.GroupLayout(jPHanMucSD);
        jPHanMucSD.setLayout(jPHanMucSDLayout);
        jPHanMucSDLayout.setHorizontalGroup(
            jPHanMucSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHanMucSDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPHanMucSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPHanMucSDLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPHanMucSDLayout.createSequentialGroup()
                        .addComponent(txtHanMucSD, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(22, 22, 22))))
        );
        jPHanMucSDLayout.setVerticalGroup(
            jPHanMucSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHanMucSDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPHanMucSDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(txtHanMucSD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setIcon(new FlatSVGIcon("quanlynganhang/icon/physical_card_label.svg")
        );
        jLabel7.setText("In thẻ vật lý");

        btnGroupPhysicalCard.add(jRadioButton6);
        jRadioButton6.setSelected(true);
        jRadioButton6.setText("Có in thẻ");

        btnGroupPhysicalCard.add(jRadioButton7);
        jRadioButton7.setText("Không in thẻ");

        javax.swing.GroupLayout jPPhysicalCardLayout = new javax.swing.GroupLayout(jPPhysicalCard);
        jPPhysicalCard.setLayout(jPPhysicalCardLayout);
        jPPhysicalCardLayout.setHorizontalGroup(
            jPPhysicalCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhysicalCardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPhysicalCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPPhysicalCardLayout.createSequentialGroup()
                        .addComponent(jRadioButton6)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton7)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPPhysicalCardLayout.setVerticalGroup(
            jPPhysicalCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhysicalCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPPhysicalCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton7))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setIcon(new FlatSVGIcon("quanlynganhang/icon/rank_card_label.svg")
        );
        jLabel8.setText("Hạng thẻ ");

        btnGroupRankCard.add(jRadioButton8);
        jRadioButton8.setSelected(true);
        jRadioButton8.setText("Thẻ Plus");

        btnGroupRankCard.add(jRadioButton9);
        jRadioButton9.setText("Thẻ Gold");

        btnGroupRankCard.add(jRadioButton10);
        jRadioButton10.setText("Thẻ Platium");

        javax.swing.GroupLayout jPRankCardLayout = new javax.swing.GroupLayout(jPRankCard);
        jPRankCard.setLayout(jPRankCardLayout);
        jPRankCardLayout.setHorizontalGroup(
            jPRankCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRankCardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPRankCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPRankCardLayout.createSequentialGroup()
                        .addComponent(jRadioButton8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioButton10)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPRankCardLayout.setVerticalGroup(
            jPRankCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRankCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPRankCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton8)
                    .addComponent(jRadioButton9)
                    .addComponent(jRadioButton10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTaoThe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoThe.setText("Tạo thẻ");
        btnTaoThe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoTheActionPerformed(evt);
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
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnTaoThe, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPFooterCusLayout.setVerticalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFooterCusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(btnTaoThe))
                .addContainerGap())
        );

        btnChonKH.setText("Chọn tài khoản khách hàng");
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel15.setIcon(new FlatSVGIcon("quanlynganhang/icon/id_bankcard_label.svg")
        );
        jLabel15.setText("Tên in trên thẻ");

        javax.swing.GroupLayout jPCardNameLayout = new javax.swing.GroupLayout(jPCardName);
        jPCardName.setLayout(jPCardNameLayout);
        jPCardNameLayout.setHorizontalGroup(
            jPCardNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenThe)
                    .addGroup(jPCardNameLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPCardNameLayout.setVerticalGroup(
            jPCardNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenThe, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel25.setIcon(new FlatSVGIcon("quanlynganhang/icon/thunhap_label.svg")
        );
        jLabel25.setText("Thu nhập trung bình");

        txtThuNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtThuNhapKeyReleased(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel26.setText("VND");

        javax.swing.GroupLayout jPThuNhapLayout = new javax.swing.GroupLayout(jPThuNhap);
        jPThuNhap.setLayout(jPThuNhapLayout);
        jPThuNhapLayout.setHorizontalGroup(
            jPThuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPThuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPThuNhapLayout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(56, Short.MAX_VALUE))
                    .addGroup(jPThuNhapLayout.createSequentialGroup()
                        .addComponent(txtThuNhap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26)
                        .addGap(26, 26, 26))))
        );
        jPThuNhapLayout.setVerticalGroup(
            jPThuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPThuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel26)
                    .addComponent(txtThuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                        .addComponent(jPPINCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPCardType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPCardNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPCardName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                            .addComponent(jPPhysicalCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPRankCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                            .addComponent(jPHanMucSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jPThuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPCustomerInfoLayout.setVerticalGroup(
            jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(lbTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnChonKH))
                .addGap(6, 6, 6)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPCardNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPCardName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPPINCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPCardType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPHanMucSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPThuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPRankCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPPhysicalCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPFooterCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin tài khoản liên kết");

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

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel11.setText("Số tài khoản khách hàng");

        txtSTK.setEditable(false);
        txtSTK.setEnabled(false);

        javax.swing.GroupLayout jPAccNumLayout = new javax.swing.GroupLayout(jPAccNum);
        jPAccNum.setLayout(jPAccNumLayout);
        jPAccNumLayout.setHorizontalGroup(
            jPAccNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSTK, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPAccNumLayout.setVerticalGroup(
            jPAccNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg")
        );
        jLabel12.setText("Tên tài khoản");

        txtTenTK.setEditable(false);
        txtTenTK.setEnabled(false);

        javax.swing.GroupLayout jPAccTypeLayout = new javax.swing.GroupLayout(jPAccType);
        jPAccType.setLayout(jPAccTypeLayout);
        jPAccTypeLayout.setHorizontalGroup(
            jPAccTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccTypeLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTenTK))
                .addContainerGap())
        );
        jPAccTypeLayout.setVerticalGroup(
            jPAccTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel17.setText("Loại tài khoản");

        txtLoaiTK.setEditable(false);
        txtLoaiTK.setEnabled(false);

        javax.swing.GroupLayout jPAccNameLayout = new javax.swing.GroupLayout(jPAccName);
        jPAccName.setLayout(jPAccNameLayout);
        jPAccNameLayout.setHorizontalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccNameLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtLoaiTK))
                .addContainerGap())
        );
        jPAccNameLayout.setVerticalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLoaiTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel18.setText("Mã tài khoản: ");

        lbMaTK.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaTK.setText("(Chưa chọn)");

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
                .addContainerGap(26, Short.MAX_VALUE))
        );

        btnGroupGender.add(rdbNam);
        rdbNam.setSelected(true);
        rdbNam.setText("Nam");

        btnGroupGender.add(rdbNu);
        rdbNu.setText("Nữ");

        btnGroupGender.add(rdbKhac);
        rdbKhac.setText("Khác");

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
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPAccountInfoLayout = new javax.swing.GroupLayout(jPAccountInfo);
        jPAccountInfo.setLayout(jPAccountInfoLayout);
        jPAccountInfoLayout.setHorizontalGroup(
            jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbMaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(jPAccNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPAccType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addComponent(jPCusYOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPCusNameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(lbMaTK))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPAccNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPAccType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPIdCentizenCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPAccName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPCusYOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPCustomerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPAccountInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPCustomerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPAccountInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void rdbNoiDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNoiDiaActionPerformed
        if (rdbNoiDia.isSelected()) {
            loaiThe = 3;
            jPRankCard.setVisible(false);
            revalidate();
        }
    }//GEN-LAST:event_rdbNoiDiaActionPerformed

    private void rdbQuocTeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbQuocTeActionPerformed
        if (rdbQuocTe.isSelected()) {
            loaiThe = 4;
            jPRankCard.setVisible(true);
            revalidate();
        }
    }//GEN-LAST:event_rdbQuocTeActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        JDialogTableChonItem chonTKKH = new JDialogTableChonItem(null, true, this, "Chọn tài khoản khách hàng", "DSTKKH", true);
        chonTKKH.setResizable(false);
        chonTKKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonTKKH.setVisible(true);
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void btnTaoTheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoTheActionPerformed
        if (lbMaTK.getText().equals("(Chưa chọn)")) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản khách hàng!");
        } else {
            taoThe();
        }
    }//GEN-LAST:event_btnTaoTheActionPerformed

    private void btnTaoMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoMaActionPerformed
        if (btnTaoMa.getText().equals("Tạo mã")) {
            txtSoThe.setEnabled(false);
            isAutoGenerate = true;
            btnTaoMa.setText("Hủy tạo");
        } else {
            txtSoThe.setEnabled(true);
            isAutoGenerate = false;
            btnTaoMa.setText("Tạo mã");
        }
    }//GEN-LAST:event_btnTaoMaActionPerformed

    private void txtHanMucSDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHanMucSDKeyReleased
        String currency = txtHanMucSD.getText().trim().replace(",", "");
        
        if (currency.isEmpty()) {
            return;
        }
        
        if (InputValidation.kiemTraSoTien(currency)) {
            txtHanMucSD.setText(FormatNumber.convertNumToVND(new BigInteger(currency.trim())));
        } else {
            MessageBox.showErrorMessage(null, "Định dạng nhập không đúng!");
        }
    }//GEN-LAST:event_txtHanMucSDKeyReleased

    private void txtThuNhapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtThuNhapKeyReleased
        String currency = txtThuNhap.getText().trim().replace(",", "");
        
        if (currency.isEmpty()) {
            return;
        }
        
        if (InputValidation.kiemTraSoTien(currency)) {
            txtThuNhap.setText(FormatNumber.convertNumToVND(new BigInteger(currency.trim())));
        } else {
            MessageBox.showErrorMessage(null, "Định dạng nhập không đúng!");
        }
    }//GEN-LAST:event_txtThuNhapKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonKH;
    private javax.swing.ButtonGroup btnGroupCardType;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.ButtonGroup btnGroupPhysicalCard;
    private javax.swing.ButtonGroup btnGroupRankCard;
    private javax.swing.JButton btnTaoMa;
    private javax.swing.JButton btnTaoThe;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPAccName;
    private javax.swing.JPanel jPAccNum;
    private javax.swing.JPanel jPAccType;
    private javax.swing.JPanel jPAccountInfo;
    private javax.swing.JPanel jPAddress;
    private javax.swing.JPanel jPCardName;
    private javax.swing.JPanel jPCardNum;
    private javax.swing.JPanel jPCardType;
    private javax.swing.JPanel jPCusNameInfo;
    private javax.swing.JPanel jPCusYOB;
    private javax.swing.JPanel jPCustomerInfo;
    private javax.swing.JPanel jPFooterCus;
    private javax.swing.JPanel jPGender;
    private javax.swing.JPanel jPHanMucSD;
    private javax.swing.JPanel jPIdCentizenCard;
    private javax.swing.JPanel jPPINCode;
    private javax.swing.JPanel jPPhoneNum;
    private javax.swing.JPanel jPPhysicalCard;
    private javax.swing.JPanel jPRankCard;
    private javax.swing.JPanel jPThuNhap;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbHotenKH;
    private javax.swing.JLabel lbMaTK;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPasswordField pwfMaPIN;
    private javax.swing.JRadioButton rdbKhac;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNoiDia;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JRadioButton rdbQuocTe;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtHanMucSD;
    private javax.swing.JTextField txtLoaiTK;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSTK;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSoThe;
    private javax.swing.JTextField txtTenTK;
    private javax.swing.JTextField txtTenThe;
    private javax.swing.JTextField txtThuNhap;
    // End of variables declaration//GEN-END:variables
}
