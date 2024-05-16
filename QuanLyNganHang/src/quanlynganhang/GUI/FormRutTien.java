/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.math.BigInteger;
import javax.swing.JOptionPane;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.KiemTraDuLieuBUS;
import quanlynganhang.BUS.MaHoaMatKhauBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TietKiemBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.TietKiemDTO;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;

/**
 *
 * @author THAI
 */
public class FormRutTien extends javax.swing.JPanel {

    private TaiKhoanNVDTO taiKhoanNV;
    private FormatDate fDate;
    private TaiKhoanKHBUS taiKhoanBUS;
    private KhachHangBUS khachHangBUS;
    private GiaoDichBUS giaoDichBUS;
    private TietKiemBUS tietKiemBUS;
    private BigInteger soDu;
    private BigInteger minSoTienGD;
    private BigInteger maxSoTienGD;
    private KiemTraDuLieuBUS kiemTraDuLieuBUS;
    private TaiKhoanKHDTO taiKhoanKH;

    public FormRutTien(TaiKhoanNVDTO taiKhoanNV) {
        this.taiKhoanNV = taiKhoanNV;
        fDate = new FormatDate();
        taiKhoanBUS = new TaiKhoanKHBUS();
        khachHangBUS = new KhachHangBUS();
        giaoDichBUS = new GiaoDichBUS();
        tietKiemBUS = new TietKiemBUS();
        kiemTraDuLieuBUS = new KiemTraDuLieuBUS(taiKhoanNV);
        minSoTienGD = new BigInteger("10000");
        maxSoTienGD = new BigInteger("1000000000");

        initComponents();
        initCustomUI();
    }

    private void initCustomUI() {
        jPCustomerInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccountInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPSoDu.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPSoTienRut.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPPINCode.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
//        jPThongTinGD.putClientProperty(FlatClientProperties.STYLE, ""
//            + "background:$BodyPanel.background;");
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

        pwfSoDu.putClientProperty(FlatClientProperties.STYLE, ""
            + "showRevealButton:true;");

        txtSoTaiKhoan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtTenTaiKhoan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtCCCD.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtLoaiTaiKhoan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtNgaySinh.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtDiaChi.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtSDT.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");

        lbMaNV.setText("" + taiKhoanNV.getMaNhanVien());
        lbTenNV.setText("" + taiKhoanNV.getTenNhanVien());
    }

    private String tinhTongTienRut(TaiKhoanKHDTO taiKhoanRut) {
        TietKiemDTO tietKiem = tietKiemBUS.getTietKiemByMaTKKH(taiKhoanRut.getMaTKKH());
        if (tietKiem != null) {

            return kiemTraDuLieuBUS.soTienGTKUocTinh(tietKiem);
        } else {
            MessageBox.showErrorMessage(null, "Không tìn thấy thông tin gửi tiết kiệm!");
        }
        return "0";
    }

    private String soTienLai(String soTien, TietKiemDTO tietKiem, boolean isTruocHan) {
        return kiemTraDuLieuBUS.soTienLaiNhan(soTien, tietKiem, fDate.getToday(), isTruocHan);
    }

    public void dienThongTinTKKH(int maTaiKhoanKH) {
        TaiKhoanKHDTO taiKhoanKH = taiKhoanBUS.getTaiKhoanKHById(maTaiKhoanKH);

        if (taiKhoanKH != null) {
            this.taiKhoanKH = taiKhoanKH;

            if (taiKhoanKH.getMaTrangThai() != 6 && taiKhoanKH.getMaLoaiTaiKhoan() == 4) {
                MessageBox.showErrorMessage(null, "Không thể sử dụng tài khoản này!");
            } else {
                System.out.println("ma loai tk: " + taiKhoanKH.getMaLoaiTaiKhoan());

                lbSoDu.setText("Số dư hiện có trong tài khoản");

                this.soDu = new BigInteger(taiKhoanKH.getSoDu());
                pwfSoDu.setText("" + taiKhoanKH.getSoDu());

                lbHoTenKH.setText(taiKhoanKH.getTenKhachHang());
                lbMaTKKH.setText("" + taiKhoanKH.getMaTKKH());
                txtSoTaiKhoan.setText(taiKhoanKH.getSoTaiKhoan());
                txtTenTaiKhoan.setText(taiKhoanKH.getTenTaiKhoan());
                txtLoaiTaiKhoan.setText(taiKhoanKH.getTenLoaiTaiKhoan());
                lbTenKhachHang.setText(taiKhoanKH.getTenKhachHang());

                KhachHangDTO khachHang = khachHangBUS.getKhachHangById(taiKhoanKH.getMaKhachHang(), 0);
                if (khachHang != null) {
                    txtCCCD.setText(khachHang.getCccd());
                    txtNgaySinh.setText(fDate.toString(khachHang.getNgaySinh()));
                    txtDiaChi.setText(khachHang.getDiaChi());
                    txtSDT.setText(khachHang.getSdt());

                    String gioiTinh = khachHang.getGioiTinh();
                    if (gioiTinh.equals("Nam")) {
                        rdbNam.setSelected(true);
                    } else if (gioiTinh.equals("Nữ")) {
                        rdbNu.setSelected(true);
                    } else {
                        rdbKhac.setSelected(true);
                    }
                }
            }
        } else {
            MessageBox.showErrorMessage(null, "Không tìm thấy tài khoản khách hàng!");
        }

    }

    private void rutTien() {
        boolean isSuccess = true;
        BigInteger soTien;
        StringBuilder error = new StringBuilder();
        error.append("");

        GiaoDichDTO giaoDich = new GiaoDichDTO();

        String maPIN = String.valueOf(pwfMaPINNV.getPassword());
        if (txtSoTienRut.getText().isEmpty() || maPIN.isEmpty()) {
            error.append("Vui lòng nhập đầy đủ thông tin");
        } else {
            try {
                soTien = new BigInteger(txtSoTienRut.getText());
                if (soTien.compareTo(maxSoTienGD) > 0 || soTien.compareTo(minSoTienGD) <= 0) {
                    error.append("\nSố tiền giao dịch nằm trong khoảng 10.000 VND và 1 tỷ VND cho một lần giao dịch!");
                }

                if (soTien.compareTo(soDu) > 0) {
                    error.append("\nSố dư không đủ để thực hiện giao dịch!");
                }
            } catch (NumberFormatException ne) {
                error.append("\nVui lòng nhập đúng số tiền giao dịch!");
            }

            if (!MaHoaMatKhauBUS.checkPassword(taiKhoanNV.getMaPIN(), maPIN)) {
                error.append("\nSai mã PIN");
            }
        }

        if (error.isEmpty()) {
            if (taiKhoanKH.getMaLoaiTaiKhoan() == 3) {
                String soTienLai = txtSoTienRut.getText();
                TietKiemDTO tietKiem = tietKiemBUS.getTietKiemByMaTKKH(taiKhoanKH.getMaTKKH());
                if (tietKiem != null) {
                    if (tietKiem.getMaTrangThai() == 10) {

                        boolean isTruocHan = true;
                        soTienLai = soTienLai(soTienLai, tietKiem, isTruocHan);

                        giaoDich.setMaTaiKhoanKH(taiKhoanKH.getMaTKKH());
                        giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaNhanVien());
                        giaoDich.setMaLoaiGiaoDich(5);
                        giaoDich.setNoiDungGiaoDich("Chuyển tiền lãi không kỳ hạn về số tài khoản " + taiKhoanKH.getSoTaiKhoan());
                        giaoDich.setNgayGiaoDich(fDate.getToday());
                        giaoDich.setSoTien(soTienLai);

                        if (giaoDichBUS.chuyenTienLaiTKVeTK(giaoDich)) {
                            if ((soDu.toString()).equals(txtSoTienRut.getText())) {
                                System.out.println("Rut het tien trong tai khoan!");

                                isSuccess = tietKiemBUS.updateTrangThai(tietKiem.getMaGuiTK());
                            } else {
                                System.out.println("Rut mot phan trong tai khoan!");

                                BigInteger tienGuiTKGoc = new BigInteger(tietKiem.getSoTienGoc());
                                BigInteger soTienRutBI = new BigInteger(txtSoTienRut.getText());

                                tienGuiTKGoc = tienGuiTKGoc.subtract(soTienRutBI);

                                isSuccess = tietKiemBUS.updateSoTienGoc(tietKiem.getMaGuiTK(), tienGuiTKGoc.toString());
                            }
                        } else {
                            isSuccess = false;
                        }

                    }
                }

                BigInteger tienRut = new BigInteger(txtSoTienRut.getText());
                BigInteger tienLai = new BigInteger(soTienLai);
                tienRut = tienRut.add(tienLai);

                giaoDich.setSoTien(tienRut.toString());
            } else {
                giaoDich.setSoTien(txtSoTienRut.getText());
            }

            if (isSuccess) {

                giaoDich.setMaTaiKhoanKH(taiKhoanKH.getMaTKKH());
                giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaNhanVien());
                giaoDich.setTenKhachHang(lbHoTenKH.getText());
                giaoDich.setTenNhanVien(taiKhoanNV.getTenNhanVien());
                giaoDich.setMaLoaiGiaoDich(3);
                giaoDich.setNoiDungGiaoDich("Rút tiền khỏi tài khoản cho khách hàng " + lbHoTenKH.getText());
                giaoDich.setNgayGiaoDich(fDate.getToday());

                if (giaoDichBUS.rutTien(giaoDich)) {
                    MessageBox.showInformationMessage(null, "", "Rút tiền thành công!");
                } else {
                    MessageBox.showErrorMessage(null, "Rút tiền thất bại!");
                }
            } else {
                MessageBox.showErrorMessage(null, "Lỗi hệ thống, không thể rút tiền!");
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

        btnGroupGender = new javax.swing.ButtonGroup();
        jPCustomerInfo = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPSoDu = new javax.swing.JPanel();
        lbSoDu = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pwfSoDu = new javax.swing.JPasswordField();
        jPSoTienRut = new javax.swing.JPanel();
        lbtienRut = new javax.swing.JLabel();
        txtSoTienRut = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPThongTinGD = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbMaNV = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbTenNV = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lbTenKhachHang = new javax.swing.JLabel();
        lbSoTienRut = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPFooterCus = new javax.swing.JPanel();
        btnRutTien = new javax.swing.JButton();
        btnChonTKKH = new javax.swing.JButton();
        jPPINCode = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pwfMaPINNV = new javax.swing.JPasswordField();
        jPAccountInfo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPCusNameInfo = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbHoTenKH = new javax.swing.JLabel();
        jPAccNum = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSoTaiKhoan = new javax.swing.JTextField();
        jPAccType = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenTaiKhoan = new javax.swing.JTextField();
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
        txtLoaiTaiKhoan = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        lbMaTKKH = new javax.swing.JLabel();
        jPPhoneNum = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jPGender = new javax.swing.JPanel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        rdbKhac = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1132, 511));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setText("Thông tin thực hiện giao dịch");

        lbSoDu.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbSoDu.setIcon(new FlatSVGIcon("quanlynganhang/icon/thunhap_label.svg")
        );
        lbSoDu.setText("Số dư hiện có trong tài khoản");

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
                        .addComponent(pwfSoDu, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbSoDu, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        jPSoDuLayout.setVerticalGroup(
            jPSoDuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoDuLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbSoDu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPSoDuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pwfSoDu, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addGroup(jPSoDuLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        lbtienRut.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbtienRut.setIcon(new FlatSVGIcon("quanlynganhang/icon/money_label.svg")
        );
        lbtienRut.setText("Số tiền muốn rút");

        txtSoTienRut.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoTienRutFocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setText("VND");

        javax.swing.GroupLayout jPSoTienRutLayout = new javax.swing.GroupLayout(jPSoTienRut);
        jPSoTienRut.setLayout(jPSoTienRutLayout);
        jPSoTienRutLayout.setHorizontalGroup(
            jPSoTienRutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoTienRutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSoTienRutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbtienRut, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPSoTienRutLayout.createSequentialGroup()
                        .addComponent(txtSoTienRut, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(121, Short.MAX_VALUE))
        );
        jPSoTienRutLayout.setVerticalGroup(
            jPSoTienRutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoTienRutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbtienRut)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPSoTienRutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtSoTienRut, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Mã giao dịch viên:");

        lbMaNV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaNV.setText(".");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Tên giao dịch viên:");

        lbTenNV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbTenNV.setText(".");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel24.setText("Tên khách hàng:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel25.setText("Số tiền giao dịch:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel26.setText("Loại giao dịch:");

        lbTenKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbTenKhachHang.setText(".");

        lbSoTienRut.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbSoTienRut.setText("(Chưa có)");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel29.setText("Rút tiền khỏi tài khoản");

        javax.swing.GroupLayout jPThongTinGDLayout = new javax.swing.GroupLayout(jPThongTinGD);
        jPThongTinGD.setLayout(jPThongTinGDLayout);
        jPThongTinGDLayout.setHorizontalGroup(
            jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThongTinGDLayout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbSoTienRut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTenKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTenNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbMaNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPThongTinGDLayout.setVerticalGroup(
            jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThongTinGDLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbMaNV))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTenNV)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lbTenKhachHang))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lbSoTienRut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel29))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        btnRutTien.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRutTien.setText("Rút tiền");
        btnRutTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRutTienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPFooterCusLayout = new javax.swing.GroupLayout(jPFooterCus);
        jPFooterCus.setLayout(jPFooterCusLayout);
        jPFooterCusLayout.setHorizontalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterCusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRutTien, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPFooterCusLayout.setVerticalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFooterCusLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(btnRutTien)
                .addContainerGap())
        );

        btnChonTKKH.setText("Chọn tài khoản khách hàng");
        btnChonTKKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonTKKHActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel15.setIcon(new FlatSVGIcon("quanlynganhang/icon/pin_code_label.svg")
        );
        jLabel15.setText("Mã PIN của nhân viên thực hiện giao dịch");

        javax.swing.GroupLayout jPPINCodeLayout = new javax.swing.GroupLayout(jPPINCode);
        jPPINCode.setLayout(jPPINCodeLayout);
        jPPINCodeLayout.setHorizontalGroup(
            jPPINCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPINCodeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPINCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pwfMaPINNV, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(97, Short.MAX_VALUE))
        );
        jPPINCodeLayout.setVerticalGroup(
            jPPINCodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPINCodeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pwfMaPINNV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addComponent(btnChonTKKH, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPSoTienRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPSoDu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPPINCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPThongTinGD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPCustomerInfoLayout.setVerticalGroup(
            jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTitle)
                    .addComponent(btnChonTKKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPSoDu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPSoTienRut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPPINCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPThongTinGD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPFooterCus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin tài khoản liên kết");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Họ tên khách hàng: ");

        lbHoTenKH.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbHoTenKH.setText("(Chưa chọn)");

        javax.swing.GroupLayout jPCusNameInfoLayout = new javax.swing.GroupLayout(jPCusNameInfo);
        jPCusNameInfo.setLayout(jPCusNameInfoLayout);
        jPCusNameInfoLayout.setHorizontalGroup(
            jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbHoTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPCusNameInfoLayout.setVerticalGroup(
            jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbHoTenKH))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel11.setText("Số tài khoản khách hàng");

        txtSoTaiKhoan.setEditable(false);
        txtSoTaiKhoan.setEnabled(false);

        javax.swing.GroupLayout jPAccNumLayout = new javax.swing.GroupLayout(jPAccNum);
        jPAccNum.setLayout(jPAccNumLayout);
        jPAccNumLayout.setHorizontalGroup(
            jPAccNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPAccNumLayout.setVerticalGroup(
            jPAccNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg")
        );
        jLabel12.setText("Tên tài khoản");

        txtTenTaiKhoan.setEditable(false);
        txtTenTaiKhoan.setEnabled(false);

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
                    .addComponent(txtTenTaiKhoan))
                .addContainerGap())
        );
        jPAccTypeLayout.setVerticalGroup(
            jPAccTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        txtLoaiTaiKhoan.setEditable(false);
        txtLoaiTaiKhoan.setEnabled(false);

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
                    .addComponent(txtLoaiTaiKhoan))
                .addContainerGap())
        );
        jPAccNameLayout.setVerticalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLoaiTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel18.setText("Mã tài khoản: ");

        lbMaTKKH.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaTKKH.setText("(Chưa chọn)");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel20.setIcon(new FlatSVGIcon("quanlynganhang/icon/phone_label.svg")
        );
        jLabel20.setText("Số điện thoại");

        txtSDT.setEditable(false);
        txtSDT.setEnabled(false);

        javax.swing.GroupLayout jPPhoneNumLayout = new javax.swing.GroupLayout(jPPhoneNum);
        jPPhoneNum.setLayout(jPPhoneNumLayout);
        jPPhoneNumLayout.setHorizontalGroup(
            jPPhoneNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhoneNumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPhoneNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSDT)
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
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(lbMaTKKH, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(lbMaTKKH))
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

    private void txtSoTienRutFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoTienRutFocusLost
        lbSoTienRut.setText("" + txtSoTienRut.getText() + " VND");
    }//GEN-LAST:event_txtSoTienRutFocusLost

    private void btnChonTKKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonTKKHActionPerformed
        JDialogTableChonItem chonTKKH = new JDialogTableChonItem(null, true, this, "Chọn tài khoản khách hàng", "DSTKKH");
        chonTKKH.setResizable(false);
        chonTKKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonTKKH.setVisible(true);
    }//GEN-LAST:event_btnChonTKKHActionPerformed

    private void btnRutTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutTienActionPerformed
        if (lbMaTKKH.getText().equals("(Chưa chọn)")) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản khách hàng!");
        } else {
            if (MessageBox.showConfirmMessage(null, "Xác nhận rút tiền từ số tài khoản " + taiKhoanKH.getSoTaiKhoan() + "?") == JOptionPane.YES_OPTION) {
                rutTien();
            } else {
                return;
            }
        }
    }//GEN-LAST:event_btnRutTienActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonTKKH;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.JButton btnRutTien;
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
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPAccName;
    private javax.swing.JPanel jPAccNum;
    private javax.swing.JPanel jPAccType;
    private javax.swing.JPanel jPAccountInfo;
    private javax.swing.JPanel jPAddress;
    private javax.swing.JPanel jPCusNameInfo;
    private javax.swing.JPanel jPCusYOB;
    private javax.swing.JPanel jPCustomerInfo;
    private javax.swing.JPanel jPFooterCus;
    private javax.swing.JPanel jPGender;
    private javax.swing.JPanel jPIdCentizenCard;
    private javax.swing.JPanel jPPINCode;
    private javax.swing.JPanel jPPhoneNum;
    private javax.swing.JPanel jPSoDu;
    private javax.swing.JPanel jPSoTienRut;
    private javax.swing.JPanel jPThongTinGD;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbHoTenKH;
    private javax.swing.JLabel lbMaNV;
    private javax.swing.JLabel lbMaTKKH;
    private javax.swing.JLabel lbSoDu;
    private javax.swing.JLabel lbSoTienRut;
    private javax.swing.JLabel lbTenKhachHang;
    private javax.swing.JLabel lbTenNV;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbtienRut;
    private javax.swing.JPasswordField pwfMaPINNV;
    private javax.swing.JPasswordField pwfSoDu;
    private javax.swing.JRadioButton rdbKhac;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtLoaiTaiKhoan;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoTaiKhoan;
    private javax.swing.JTextField txtSoTienRut;
    private javax.swing.JTextField txtTenTaiKhoan;
    // End of variables declaration//GEN-END:variables
}
