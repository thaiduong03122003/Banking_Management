package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.JOptionPane;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.MaHoaMatKhauBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TheATMBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.TheATMDTO;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;

public class FormMoTheGhiNo extends javax.swing.JPanel {

    private TaiKhoanKHBUS taiKhoanKHBUS;
    private KhachHangBUS khachHangBUS;
    private TheATMBUS theATMBUS;
    private TaiKhoanNVDTO taiKhoanNV;
    private TaiKhoanKHDTO taiKhoanKH;
    private FormatDate fDate;
    private int loaiThe;
    private boolean isAutoGenerate;

    public FormMoTheGhiNo(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) {
        this.taiKhoanNV = taiKhoanNV;
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        khachHangBUS = new KhachHangBUS();
        theATMBUS = new TheATMBUS();
        fDate = new FormatDate();
        loaiThe = 1;

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
        jPYOB.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCardType.putClientProperty(FlatClientProperties.STYLE, ""
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
        TheATMDTO theATM = new TheATMDTO();

        if (txtTenThe.getText().isEmpty() || (txtSoThe.getText().isEmpty() && !isAutoGenerate) || maPIN.isEmpty()) {
            error.append("\nVui lòng điền đầu đủ thông tin!");
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

            if (!InputValidation.kiemTraTen(txtTenThe.getText())) {
                error.append("\nTên in trên thẻ không hợp lệ");
            }

            if (!InputValidation.kiemTraMaPIN(maPIN)) {
                error.append("\nMã PIN không hợp lệ");
            }
        }

        if (error.isEmpty()) {

            theATM.setSoThe(txtSoThe.getText().trim());
            theATM.setTenThe(txtTenThe.getText().trim());
            theATM.setMaPIN(MaHoaMatKhauBUS.encryptPassword(maPIN));
            theATM.setNgayTao(fDate.getToday());
            theATM.setThoiHanThe(fDate.addMonth(60));
            theATM.setMaTaiKhoanKH(taiKhoanKH.getMaTKKH());
            theATM.setMaLoaiThe(loaiThe);

            if (theATMBUS.addTheATM(theATM) != 0) {
                MessageBox.showInformationMessage(null, "", "Tạo thẻ ghi nợ thành công!");
            } else {
                MessageBox.showErrorMessage(null, "Tạo thẻ ghi nợ thất bại!");
            }
        } else {
            MessageBox.showErrorMessage(null, "Lỗi: " + error);
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
        jPYOB = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        pwfMaPIN = new javax.swing.JPasswordField();
        jPCardType = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rdbNoiDia = new javax.swing.JRadioButton();
        rdbQuocTe = new javax.swing.JRadioButton();
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
        btnChonTKKH = new javax.swing.JButton();
        jPCardName = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtTenThe = new javax.swing.JTextField();
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
        lbTitle.setText("Thông tin thẻ ghi nợ");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setIcon(new FlatSVGIcon("quanlynganhang/icon/id_bankcard_label.svg")
        );
        jLabel3.setText("Mã số thẻ");

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
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPCardNumLayout.createSequentialGroup()
                        .addComponent(txtSoThe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTaoMa, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        javax.swing.GroupLayout jPYOBLayout = new javax.swing.GroupLayout(jPYOB);
        jPYOB.setLayout(jPYOBLayout);
        jPYOBLayout.setHorizontalGroup(
            jPYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPYOBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pwfMaPIN, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPYOBLayout.setVerticalGroup(
            jPYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPYOBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pwfMaPIN, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(36, Short.MAX_VALUE))
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
                    .addComponent(jRadioButton6)
                    .addComponent(jRadioButton7))
                .addContainerGap(111, Short.MAX_VALUE))
        );
        jPPhysicalCardLayout.setVerticalGroup(
            jPPhysicalCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhysicalCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton7)
                .addContainerGap(28, Short.MAX_VALUE))
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
                    .addComponent(jRadioButton8)
                    .addComponent(jRadioButton9)
                    .addComponent(jRadioButton10))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPRankCardLayout.setVerticalGroup(
            jPRankCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPRankCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton10)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        btnTaoThe.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoThe.setText("Tạo thẻ");
        btnTaoThe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoTheActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPFooterCusLayout = new javax.swing.GroupLayout(jPFooterCus);
        jPFooterCus.setLayout(jPFooterCusLayout);
        jPFooterCusLayout.setHorizontalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterCusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTaoThe, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPFooterCusLayout.setVerticalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterCusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTaoThe)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        btnChonTKKH.setText("Chọn tài khoản khách hàng");
        btnChonTKKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonTKKHActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel15.setIcon(new FlatSVGIcon("quanlynganhang/icon/customer_label.svg")
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
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 54, Short.MAX_VALUE)))
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

        javax.swing.GroupLayout jPCustomerInfoLayout = new javax.swing.GroupLayout(jPCustomerInfo);
        jPCustomerInfo.setLayout(jPCustomerInfoLayout);
        jPCustomerInfoLayout.setHorizontalGroup(
            jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPFooterCus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPYOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPCardType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                                .addComponent(jPPhysicalCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPRankCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 58, Short.MAX_VALUE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPCardNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnChonTKKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPCardName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPCustomerInfoLayout.setVerticalGroup(
            jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitle)
                    .addComponent(btnChonTKKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPCardNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPCardType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPYOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jPCardName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPPhysicalCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(98, 98, 98)
                        .addComponent(jPFooterCus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPRankCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                        .addGap(0, 70, Short.MAX_VALUE))
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
                .addComponent(jPCustomerInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPAccountInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void rdbNoiDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbNoiDiaActionPerformed
        if (rdbNoiDia.isSelected()) {
            loaiThe = 1;
            jPRankCard.setVisible(false);
            revalidate();
        }
    }//GEN-LAST:event_rdbNoiDiaActionPerformed

    private void rdbQuocTeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbQuocTeActionPerformed
        if (rdbQuocTe.isSelected()) {
            loaiThe = 2;
            jPRankCard.setVisible(true);
            revalidate();
        }
    }//GEN-LAST:event_rdbQuocTeActionPerformed

    private void btnChonTKKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonTKKHActionPerformed
        JDialogTableChonItem chonTKKH = new JDialogTableChonItem(null, true, this, "Chọn tài khoản khách hàng", "DSTKKH", true);
        chonTKKH.setResizable(false);
        chonTKKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonTKKH.setVisible(true);
    }//GEN-LAST:event_btnChonTKKHActionPerformed

    private void btnTaoTheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoTheActionPerformed
        if (lbMaTK.getText().equals("(Chưa chọn)")) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản khách hàng!");
        } else {
            if (MessageBox.showConfirmMessage(null, "Xác nhận tạo thẻ cho số tài khoản " + taiKhoanKH.getSoTaiKhoan() + "?") == JOptionPane.YES_OPTION) {
                taoThe();
            } else {
                return;
            }
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonTKKH;
    private javax.swing.ButtonGroup btnGroupCardType;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.ButtonGroup btnGroupPhysicalCard;
    private javax.swing.ButtonGroup btnGroupRankCard;
    private javax.swing.JButton btnTaoMa;
    private javax.swing.JButton btnTaoThe;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JPanel jPIdCentizenCard;
    private javax.swing.JPanel jPPhoneNum;
    private javax.swing.JPanel jPPhysicalCard;
    private javax.swing.JPanel jPRankCard;
    private javax.swing.JPanel jPYOB;
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
    private javax.swing.JTextField txtLoaiTK;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSTK;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSoThe;
    private javax.swing.JTextField txtTenTK;
    private javax.swing.JTextField txtTenThe;
    // End of variables declaration//GEN-END:variables
}
