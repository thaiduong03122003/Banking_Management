package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.MaHoaMatKhauBUS;
import quanlynganhang.BUS.NganHangBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.FormatNumber;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class FormChuyenLienNganHang extends javax.swing.JPanel {

    private TaiKhoanNVDTO taiKhoanNV;
    private FormatDate fDate;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private KhachHangBUS khachHangBUS;
    private GiaoDichBUS giaoDichBUS;
    private TaiKhoanKHDTO taiKhoanKHNhan, taiKhoanKHGui;
    private NganHangBUS nganHangBUS;
    private Integer maNganHang;
    private BigInteger soDu;
    private BigInteger minSoTienGD;
    private BigInteger maxSoTienGD;

    public FormChuyenLienNganHang(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) {
        this.taiKhoanNV = taiKhoanNV;
        fDate = new FormatDate();
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        khachHangBUS = new KhachHangBUS();
        giaoDichBUS = new GiaoDichBUS();
        nganHangBUS = new NganHangBUS();
        minSoTienGD = new BigInteger("10000");
        maxSoTienGD = new BigInteger("1000000000");
        maNganHang = 0;

        initComponents();
        initCustomUI();
        loadNganHang();
    }

    private void initCustomUI() {
        jPCustomerInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccountSend.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccountReceive.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPSoDu.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTKNguoiNhan.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPBank.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPSoTienChuyen.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPNguonTien.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPNoiDungChuyen.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPFooterCus.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCusNameSend.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccNumSend.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccTypeSend.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCusNameReceive.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccNumReceive.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccTypeReceive.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");

        pwfSoDu.putClientProperty(FlatClientProperties.STYLE, ""
            + "showRevealButton:true;");

        txtSTKGui.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtTenTKGui.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtSTKNhan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtTenTKNhan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtStkNguoiNhan.setEnabled(false);

    }

    private void loadNganHang() {
        Map<Integer, String> map = new HashMap<>();
        map = nganHangBUS.convertListNganHangToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn ngân hàng-");

        for (String tenNganHang : map.values()) {
            model.addElement(tenNganHang);
        }

        cbxNganHang.setModel(model);
    }

    public void dienTKNguoiGui(int maTaiKhoanKH) {
        TaiKhoanKHDTO taiKhoanKH = taiKhoanKHBUS.getTaiKhoanKHById(maTaiKhoanKH);

        if (taiKhoanKH != null) {
            if (taiKhoanKH.getMaTrangThai() != 6 && (taiKhoanKH.getMaLoaiTaiKhoan() != 1 || taiKhoanKH.getMaLoaiTaiKhoan() != 2)) {
                MessageBox.showErrorMessage(null, "Không thể sử dụng tài khoản này!");
            } else {
                this.taiKhoanKHGui = taiKhoanKH;
                this.soDu = new BigInteger(taiKhoanKH.getSoDu());

                lbHoTenKHGui.setText(taiKhoanKH.getTenKhachHang());
                lbMaTKKHGui.setText("" + taiKhoanKH.getMaTKKH());
                txtSTKGui.setText(taiKhoanKH.getSoTaiKhoan());
                txtTenTKGui.setText(InputValidation.catNganString(taiKhoanKH.getTenTaiKhoan()));
                pwfSoDu.setText(FormatNumber.convertNumToVND(new BigInteger(taiKhoanKH.getSoDu())));
            }
        } else {
            MessageBox.showErrorMessage(null, "Không tìm thấy tài khoản người gửi!");
        }
    }

    private void dienTKNguoiNhan(String soTaiKhoan) {
        TaiKhoanKHDTO taiKhoanKHNhan = taiKhoanKHBUS.getTaiKhoanKHBySTK(soTaiKhoan, maNganHang);

        if (taiKhoanKHNhan != null) {
            if (taiKhoanKHNhan.getMaTrangThai() != 6 && (taiKhoanKHNhan.getMaLoaiTaiKhoan() != 1 || taiKhoanKHNhan.getMaLoaiTaiKhoan() != 2)) {
                MessageBox.showErrorMessage(null, "Không thể giao dịch với tài khoản này!");
            } else {
                this.taiKhoanKHNhan = taiKhoanKHNhan;
                lbHoTenKHNhan.setText(taiKhoanKHNhan.getTenKhachHang());
                lbMaTKKHNhan.setText("" + taiKhoanKHNhan.getMaTKKH());
                txtSTKNhan.setText(taiKhoanKHNhan.getSoTaiKhoan());
                txtTenTKNhan.setText(InputValidation.catNganString(taiKhoanKHNhan.getTenTaiKhoan()));
            }
        } else {
            MessageBox.showErrorMessage(null, "Không tìm thấy tài khoản người nhận!");
            txtSTKNhan.requestFocus();
        }
    }

    private void chuyenTienLienNH() {
        BigInteger soTien;
        StringBuilder error = new StringBuilder();
        error.append("");

        GiaoDichDTO giaoDich = new GiaoDichDTO();

        if (txtSoTienChuyen.getText().isEmpty()) {
            error.append("Vui lòng nhập đầy đủ thông tin");
        } else {
            if (InputValidation.kiemTraSoTien(txtSoTienChuyen.getText().trim().replace(",", ""))) {
                soTien = new BigInteger(txtSoTienChuyen.getText().trim().replace(",", ""));
                if (soTien.compareTo(maxSoTienGD) > 0 || soTien.compareTo(minSoTienGD) <= 0) {
                    error.append("\nSố tiền giao dịch nằm trong khoảng 10.000 VND và 1 tỷ VND cho một lần giao dịch!");
                }

                if (soTien.compareTo(soDu) > 0) {
                    error.append("\nSố dư không đủ để thực hiện giao dịch!");
                }
            } else {
                error.append("\nVui lòng nhập đúng số tiền giao dịch!");
            }
        }

        if (txtSTKNhan.getText().trim().isEmpty() || txtStkNguoiNhan.getText().trim().isEmpty()) {
            error.append("Vui lòng nhập số tài khoản nhận");
        }

        if (error.isEmpty()) {

            if (taiKhoanKHGui.getBiXoa() == 1) {
                if (MessageBox.showConfirmMessage(null, "Chủ sở hữu tài khoản gửi tiền này đã bị xóa khỏi hệ thống, xác nhận vẫn chuyển khoản?") == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            giaoDich.setMaTaiKhoanKH(Integer.parseInt(lbMaTKKHGui.getText()));
            giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaTKNV());
            giaoDich.setTenKhachHang(lbHoTenKHGui.getText());
            giaoDich.setTenNhanVien(taiKhoanNV.getTenNhanVien());
            giaoDich.setMaLoaiGiaoDich(2);

            if (txtNoiDung.getText().isEmpty()) {
                giaoDich.setNoiDungGiaoDich(lbHoTenKHGui.getText() + " chuyển tiền đến cho " + lbHoTenKHNhan.getText());
            } else {
                giaoDich.setNoiDungGiaoDich(txtNoiDung.getText());
            }

            giaoDich.setNgayGiaoDich(fDate.getToday());
            giaoDich.setSoTien(txtSoTienChuyen.getText().trim().replace(",", ""));

            boolean isTienTK = rdbTienTK.isSelected();

            if (giaoDichBUS.chuyenTien(giaoDich, isTienTK, taiKhoanKHNhan, maNganHang)) {
                MessageBox.showInformationMessage(null, "", "Chuyển tiền thành công!");

                dienTKNguoiGui(taiKhoanKHGui.getMaTKKH());
            } else {
                MessageBox.showErrorMessage(null, "Chuyển tiền thất bại!");
            }
        } else {
            MessageBox.showErrorMessage(null, "Lỗi: " + error);
        }
    }

    private void onCodeTextChanged() {
        String currency = txtSoTienChuyen.getText().trim().replace(",", "");

        if (InputValidation.kiemTraSoTien(currency)) {
            txtSoTienChuyen.setText(FormatNumber.convertNumToVND(new BigInteger(currency.trim())));
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
        btnGroupNguonTien = new javax.swing.ButtonGroup();
        jPCustomerInfo = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPSoDu = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pwfSoDu = new javax.swing.JPasswordField();
        jPSoTienChuyen = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtSoTienChuyen = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPFooterCus = new javax.swing.JPanel();
        btnChuyenTien = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnChonTKKH = new javax.swing.JButton();
        jPNoiDungChuyen = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtNoiDung = new javax.swing.JTextField();
        jPTKNguoiNhan = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtStkNguoiNhan = new javax.swing.JTextField();
        jPNguonTien = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        rdbTienTK = new javax.swing.JRadioButton();
        rdbTienMat = new javax.swing.JRadioButton();
        jPBank = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        cbxNganHang = new javax.swing.JComboBox<>();
        jPAccountSend = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPCusNameSend = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbHoTenKHGui = new javax.swing.JLabel();
        jPAccNumSend = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSTKGui = new javax.swing.JTextField();
        jPAccTypeSend = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenTKGui = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lbMaTKKHGui = new javax.swing.JLabel();
        jPAccountReceive = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jPCusNameReceive = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        lbHoTenKHNhan = new javax.swing.JLabel();
        jPAccNumReceive = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        txtSTKNhan = new javax.swing.JTextField();
        jPAccTypeReceive = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        txtTenTKNhan = new javax.swing.JTextField();
        lbTitleMa = new javax.swing.JLabel();
        lbMaTKKHNhan = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1132, 511));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setText("Thông tin thực hiện giao dịch");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setIcon(new FlatSVGIcon("quanlynganhang/icon/thunhap_label.svg")
        );
        jLabel3.setText("Số dư hiện có trong tài khoản");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("VND");

        pwfSoDu.setEditable(false);

        javax.swing.GroupLayout jPSoDuLayout = new javax.swing.GroupLayout(jPSoDu);
        jPSoDu.setLayout(jPSoDuLayout);
        jPSoDuLayout.setHorizontalGroup(
            jPSoDuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoDuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSoDuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPSoDuLayout.createSequentialGroup()
                        .addComponent(pwfSoDu, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPSoDuLayout.setVerticalGroup(
            jPSoDuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoDuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPSoDuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pwfSoDu, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addGroup(jPSoDuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setIcon(new FlatSVGIcon("quanlynganhang/icon/money_label.svg")
        );
        jLabel4.setText("Số tiền muốn chuyển");

        txtSoTienChuyen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoTienChuyenFocusLost(evt);
            }
        });
        txtSoTienChuyen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoTienChuyenKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setText("VND");

        javax.swing.GroupLayout jPSoTienChuyenLayout = new javax.swing.GroupLayout(jPSoTienChuyen);
        jPSoTienChuyen.setLayout(jPSoTienChuyenLayout);
        jPSoTienChuyenLayout.setHorizontalGroup(
            jPSoTienChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoTienChuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSoTienChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPSoTienChuyenLayout.createSequentialGroup()
                        .addComponent(txtSoTienChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPSoTienChuyenLayout.setVerticalGroup(
            jPSoTienChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoTienChuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSoTienChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(jPSoTienChuyenLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSoTienChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnChuyenTien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChuyenTien.setText("Chuyển tiền");
        btnChuyenTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuyenTienActionPerformed(evt);
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
                .addGap(18, 18, 18)
                .addComponent(btnChuyenTien, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPFooterCusLayout.setVerticalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFooterCusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(btnChuyenTien))
                .addContainerGap())
        );

        btnChonTKKH.setText("Chọn tài khoản người gửi");
        btnChonTKKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonTKKHActionPerformed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel13.setIcon(new FlatSVGIcon("quanlynganhang/icon/comment_label.svg")
        );
        jLabel13.setText("Nội dung chuyển tiền");

        txtNoiDung.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNoiDungFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPNoiDungChuyenLayout = new javax.swing.GroupLayout(jPNoiDungChuyen);
        jPNoiDungChuyen.setLayout(jPNoiDungChuyenLayout);
        jPNoiDungChuyenLayout.setHorizontalGroup(
            jPNoiDungChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNoiDungChuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPNoiDungChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPNoiDungChuyenLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNoiDung))
                .addContainerGap())
        );
        jPNoiDungChuyenLayout.setVerticalGroup(
            jPNoiDungChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNoiDungChuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel7.setText("Số tài khoản người nhận");

        txtStkNguoiNhan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtStkNguoiNhanFocusLost(evt);
            }
        });
        txtStkNguoiNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtStkNguoiNhanMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPTKNguoiNhanLayout = new javax.swing.GroupLayout(jPTKNguoiNhan);
        jPTKNguoiNhan.setLayout(jPTKNguoiNhanLayout);
        jPTKNguoiNhanLayout.setHorizontalGroup(
            jPTKNguoiNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKNguoiNhanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTKNguoiNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtStkNguoiNhan)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                .addContainerGap(96, Short.MAX_VALUE))
        );
        jPTKNguoiNhanLayout.setVerticalGroup(
            jPTKNguoiNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKNguoiNhanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtStkNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setIcon(new FlatSVGIcon("quanlynganhang/icon/nguontien_label.svg")
        );
        jLabel8.setText("Nguồn tiền");

        btnGroupNguonTien.add(rdbTienTK);
        rdbTienTK.setSelected(true);
        rdbTienTK.setText("Tiền tài khoản");
        rdbTienTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbTienTKActionPerformed(evt);
            }
        });

        btnGroupNguonTien.add(rdbTienMat);
        rdbTienMat.setText("Tiền mặt");

        javax.swing.GroupLayout jPNguonTienLayout = new javax.swing.GroupLayout(jPNguonTien);
        jPNguonTien.setLayout(jPNguonTienLayout);
        jPNguonTienLayout.setHorizontalGroup(
            jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNguonTienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPNguonTienLayout.createSequentialGroup()
                        .addComponent(rdbTienTK)
                        .addGap(18, 18, 18)
                        .addComponent(rdbTienMat)))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        jPNguonTienLayout.setVerticalGroup(
            jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNguonTienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbTienTK)
                    .addComponent(rdbTienMat))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel14.setIcon(new FlatSVGIcon("quanlynganhang/icon/bank_label.svg")
        );
        jLabel14.setText("Tên ngân hàng thụ hưởng");

        cbxNganHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn ngân hàng-" }));
        cbxNganHang.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxNganHangItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPBankLayout = new javax.swing.GroupLayout(jPBank);
        jPBank.setLayout(jPBankLayout);
        jPBankLayout.setHorizontalGroup(
            jPBankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBankLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPBankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPBankLayout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(cbxNganHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPBankLayout.setVerticalGroup(
            jPBankLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPBankLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxNganHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPCustomerInfoLayout = new javax.swing.GroupLayout(jPCustomerInfo);
        jPCustomerInfo.setLayout(jPCustomerInfoLayout);
        jPCustomerInfoLayout.setHorizontalGroup(
            jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPBank, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPNoiDungChuyen, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPFooterCus, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPSoTienChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPNguonTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 326, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPSoDu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                                .addGap(0, 138, Short.MAX_VALUE)
                                .addComponent(btnChonTKKH))
                            .addComponent(jPTKNguoiNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPSoDu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPTKNguoiNhan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                                .addComponent(jPSoTienChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPNoiDungChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(92, 92, 92)
                                .addComponent(jPFooterCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPNguonTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPBank, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin tài khoản người gửi");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Họ tên khách hàng: ");

        lbHoTenKHGui.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbHoTenKHGui.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPCusNameSendLayout = new javax.swing.GroupLayout(jPCusNameSend);
        jPCusNameSend.setLayout(jPCusNameSendLayout);
        jPCusNameSendLayout.setHorizontalGroup(
            jPCusNameSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameSendLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbHoTenKHGui, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPCusNameSendLayout.setVerticalGroup(
            jPCusNameSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameSendLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCusNameSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbHoTenKHGui))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel11.setText("Số tài khoản");

        txtSTKGui.setEditable(false);
        txtSTKGui.setEnabled(false);

        javax.swing.GroupLayout jPAccNumSendLayout = new javax.swing.GroupLayout(jPAccNumSend);
        jPAccNumSend.setLayout(jPAccNumSendLayout);
        jPAccNumSendLayout.setHorizontalGroup(
            jPAccNumSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumSendLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNumSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSTKGui, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPAccNumSendLayout.setVerticalGroup(
            jPAccNumSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumSendLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSTKGui, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg")
        );
        jLabel12.setText("Tên tài khoản");

        txtTenTKGui.setEditable(false);
        txtTenTKGui.setEnabled(false);

        javax.swing.GroupLayout jPAccTypeSendLayout = new javax.swing.GroupLayout(jPAccTypeSend);
        jPAccTypeSend.setLayout(jPAccTypeSendLayout);
        jPAccTypeSendLayout.setHorizontalGroup(
            jPAccTypeSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeSendLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccTypeSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                    .addComponent(txtTenTKGui))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPAccTypeSendLayout.setVerticalGroup(
            jPAccTypeSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeSendLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTKGui, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel18.setText("Mã tài khoản: ");

        lbMaTKKHGui.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaTKKHGui.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPAccountSendLayout = new javax.swing.GroupLayout(jPAccountSend);
        jPAccountSend.setLayout(jPAccountSendLayout);
        jPAccountSendLayout.setHorizontalGroup(
            jPAccountSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountSendLayout.createSequentialGroup()
                .addGroup(jPAccountSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccountSendLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbMaTKKHGui, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPAccountSendLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPAccountSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPAccountSendLayout.createSequentialGroup()
                                .addComponent(jPAccNumSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPAccTypeSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPCusNameSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPAccountSendLayout.setVerticalGroup(
            jPAccountSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountSendLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPCusNameSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccountSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lbMaTKKHGui))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccountSendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPAccNumSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPAccTypeSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Thông tin tài khoản người nhận");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel28.setText("Họ tên khách hàng: ");

        lbHoTenKHNhan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbHoTenKHNhan.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPCusNameReceiveLayout = new javax.swing.GroupLayout(jPCusNameReceive);
        jPCusNameReceive.setLayout(jPCusNameReceiveLayout);
        jPCusNameReceiveLayout.setHorizontalGroup(
            jPCusNameReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameReceiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbHoTenKHNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPCusNameReceiveLayout.setVerticalGroup(
            jPCusNameReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameReceiveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCusNameReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(lbHoTenKHNhan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel31.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel31.setText("Số tài khoản");

        txtSTKNhan.setEditable(false);
        txtSTKNhan.setEnabled(false);

        javax.swing.GroupLayout jPAccNumReceiveLayout = new javax.swing.GroupLayout(jPAccNumReceive);
        jPAccNumReceive.setLayout(jPAccNumReceiveLayout);
        jPAccNumReceiveLayout.setHorizontalGroup(
            jPAccNumReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumReceiveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNumReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSTKNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPAccNumReceiveLayout.setVerticalGroup(
            jPAccNumReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumReceiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSTKNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel32.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg")
        );
        jLabel32.setText("Tên tài khoản");

        txtTenTKNhan.setEditable(false);
        txtTenTKNhan.setEnabled(false);

        javax.swing.GroupLayout jPAccTypeReceiveLayout = new javax.swing.GroupLayout(jPAccTypeReceive);
        jPAccTypeReceive.setLayout(jPAccTypeReceiveLayout);
        jPAccTypeReceiveLayout.setHorizontalGroup(
            jPAccTypeReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeReceiveLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccTypeReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccTypeReceiveLayout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 7, Short.MAX_VALUE))
                    .addComponent(txtTenTKNhan))
                .addContainerGap())
        );
        jPAccTypeReceiveLayout.setVerticalGroup(
            jPAccTypeReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeReceiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTKNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbTitleMa.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lbTitleMa.setText("Mã tài khoản: ");

        lbMaTKKHNhan.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaTKKHNhan.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPAccountReceiveLayout = new javax.swing.GroupLayout(jPAccountReceive);
        jPAccountReceive.setLayout(jPAccountReceiveLayout);
        jPAccountReceiveLayout.setHorizontalGroup(
            jPAccountReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountReceiveLayout.createSequentialGroup()
                .addGroup(jPAccountReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccountReceiveLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPAccountReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPAccountReceiveLayout.createSequentialGroup()
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPCusNameReceive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPAccountReceiveLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lbTitleMa, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbMaTKKHNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPAccountReceiveLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPAccNumReceive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPAccTypeReceive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPAccountReceiveLayout.setVerticalGroup(
            jPAccountReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountReceiveLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPCusNameReceive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccountReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitleMa)
                    .addComponent(lbMaTKKHNhan))
                .addGap(6, 6, 6)
                .addGroup(jPAccountReceiveLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPAccNumReceive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPAccTypeReceive, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPCustomerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPAccountSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPAccountReceive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPCustomerInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPAccountSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPAccountReceive, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtSoTienChuyenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoTienChuyenFocusLost

    }//GEN-LAST:event_txtSoTienChuyenFocusLost

    private void txtNoiDungFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNoiDungFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoiDungFocusLost

    private void txtStkNguoiNhanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtStkNguoiNhanFocusLost
        if (!txtStkNguoiNhan.getText().isEmpty()) {
            dienTKNguoiNhan(txtStkNguoiNhan.getText());
        }
    }//GEN-LAST:event_txtStkNguoiNhanFocusLost

    private void rdbTienTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbTienTKActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbTienTKActionPerformed

    private void cbxNganHangItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxNganHangItemStateChanged
        String tenNganHang = (String) cbxNganHang.getSelectedItem();
        if (tenNganHang.equals("-Chọn ngân hàng-")) {
            txtStkNguoiNhan.setEnabled(false);
            maNganHang = 0;
        } else {
            txtStkNguoiNhan.setEnabled(true);
            txtStkNguoiNhan.requestFocus();
            maNganHang = nganHangBUS.getIdFromTenNganHang(tenNganHang);

            if (maNganHang == null) {
                MessageBox.showErrorMessage(null, "Lấy id của ngân hàng thất bại!");
            }
        }
    }//GEN-LAST:event_cbxNganHangItemStateChanged

    private void btnChonTKKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonTKKHActionPerformed
        JDialogTableChonItem chonTKKH = new JDialogTableChonItem(null, true, this, "Chọn tài khoản khách hàng", "DSTKKH", true);
        chonTKKH.setResizable(false);
        chonTKKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonTKKH.setVisible(true);
    }//GEN-LAST:event_btnChonTKKHActionPerformed

    private void btnChuyenTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuyenTienActionPerformed
        if (lbMaTKKHNhan.getText().equals("(Chưa có)") || lbMaTKKHGui.getText().equals("(Chưa có)") || lbMaTKKHGui.getText().equals(lbMaTKKHNhan.getText())) {
            MessageBox.showErrorMessage(null, "Thông tin không phù hợp! Vui lòng nhập chính xác và đầy đủ thông tin");
        } else {
            chuyenTienLienNH();
        }
    }//GEN-LAST:event_btnChuyenTienActionPerformed

    private void txtStkNguoiNhanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtStkNguoiNhanMouseClicked
        if (cbxNganHang.getSelectedIndex() == 1) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn ngân hàng thụ hưởng!");
        }
    }//GEN-LAST:event_txtStkNguoiNhanMouseClicked

    private void txtSoTienChuyenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoTienChuyenKeyReleased
        if (txtSoTienChuyen.getText().trim().isEmpty()) {
            return;
        }

        onCodeTextChanged();
    }//GEN-LAST:event_txtSoTienChuyenKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonTKKH;
    private javax.swing.JButton btnChuyenTien;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.ButtonGroup btnGroupNguonTien;
    private javax.swing.JComboBox<String> cbxNganHang;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPAccNumReceive;
    private javax.swing.JPanel jPAccNumSend;
    private javax.swing.JPanel jPAccTypeReceive;
    private javax.swing.JPanel jPAccTypeSend;
    private javax.swing.JPanel jPAccountReceive;
    private javax.swing.JPanel jPAccountSend;
    private javax.swing.JPanel jPBank;
    private javax.swing.JPanel jPCusNameReceive;
    private javax.swing.JPanel jPCusNameSend;
    private javax.swing.JPanel jPCustomerInfo;
    private javax.swing.JPanel jPFooterCus;
    private javax.swing.JPanel jPNguonTien;
    private javax.swing.JPanel jPNoiDungChuyen;
    private javax.swing.JPanel jPSoDu;
    private javax.swing.JPanel jPSoTienChuyen;
    private javax.swing.JPanel jPTKNguoiNhan;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lbHoTenKHGui;
    private javax.swing.JLabel lbHoTenKHNhan;
    private javax.swing.JLabel lbMaTKKHGui;
    private javax.swing.JLabel lbMaTKKHNhan;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTitleMa;
    private javax.swing.JPasswordField pwfSoDu;
    private javax.swing.JRadioButton rdbTienMat;
    private javax.swing.JRadioButton rdbTienTK;
    private javax.swing.JTextField txtNoiDung;
    private javax.swing.JTextField txtSTKGui;
    private javax.swing.JTextField txtSTKNhan;
    private javax.swing.JTextField txtSoTienChuyen;
    private javax.swing.JTextField txtStkNguoiNhan;
    private javax.swing.JTextField txtTenTKGui;
    private javax.swing.JTextField txtTenTKNhan;
    // End of variables declaration//GEN-END:variables
}
