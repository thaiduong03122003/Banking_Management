package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.BUS.KiemTraDuLieuBUS;
import quanlynganhang.BUS.MaHoaMatKhauBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TraKhoanVayBUS;
import quanlynganhang.BUS.VayVonBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.TraKhoanVayDTO;
import quanlynganhang.DTO.VayVonDTO;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;

public class FormTraKhoanVay extends javax.swing.JPanel {

    private int maTaiKhoanKHVay, maKhachHang, maVayVon;
    private TaiKhoanKHDTO taiKhoanKHVay, taiKhoanKHNguon;
    private TaiKhoanNVDTO taiKhoanNV;
    private VayVonDTO vayVonDTO;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private KiemTraDuLieuBUS kiemTraBUS;
    private VayVonBUS vayVonBUS;
    private TraKhoanVayBUS traVayBUS;
    private Integer maTaiKhoanKHNguon;
    private int maTraKhoanVay, kyVay;
    private String duNo, soTienDaTra, tongTien, tienVay, tienLai, tienPhat, soTienConThieu;
    private FormatDate fDate;

    public FormTraKhoanVay(TaiKhoanNVDTO taiKhoanNV) {
        this.taiKhoanNV = taiKhoanNV;
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        vayVonBUS = new VayVonBUS();
        traVayBUS = new TraKhoanVayBUS();
        kiemTraBUS = new KiemTraDuLieuBUS();
        fDate = new FormatDate();
        maTaiKhoanKHNguon = 0;
        maTaiKhoanKHVay = 0;
        kyVay = 0;
        duNo = "";

        initComponents();
        initCustomUI();

        resetThongTinKhoanVay(false);

        rdbTienMatActionPerformed(null);
    }

    private void loadDanhSachKyTraNo(boolean isSelected) {
        DefaultTableModel model = (DefaultTableModel) jTableDSKyTra.getModel();
        model.setRowCount(0);
        String[] title = {"Mã kỳ", "Kỳ", "Thời gian", "Số tiền đã trả", "Ngày trả", "Trạng thái"};
        Object[][] dataModel;
        if (!isSelected) {
            dataModel = new Object[0][6];
        } else {
            try {
                dataModel = traVayBUS.doiSangObjectTraKhoanVay(maVayVon);
            } catch (Exception ex) {
                ex.printStackTrace();
                dataModel = new Object[0][6];
            }
        }

        model.setDataVector(dataModel, title);

        TableColumn idColumn = jTableDSKyTra.getColumnModel().getColumn(0);
        jTableDSKyTra.getColumnModel().removeColumn(idColumn);
        jTableDSKyTra.setDefaultEditor(Object.class, null);
    }

    public void getMaTaiKhoanVay(int maTaiKhoanKH) {
        maTaiKhoanKHVay = maTaiKhoanKH;

        try {
            loadThongTinTKVay();
        } catch (Exception ex) {
            System.out.println("Lỗi tài khoản vay vốn");
            ex.printStackTrace();
        }
    }

    private void initCustomUI() {
        jPCustomerInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccountInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPNguonTien.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTKNguon.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPSoTienTra.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPDuNo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPPINCode.putClientProperty(FlatClientProperties.STYLE, ""
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
        jPAddress.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        txtLaiPhat.putClientProperty(FlatClientProperties.STYLE, ""
            + "foreground:$BodyPanel.foreground;");

        pwfMaPINNV.putClientProperty(FlatClientProperties.STYLE, ""
            + "showRevealButton:true;");

        txtSTK.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtTenTK.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtTienPhaiTra.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtTienVay.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtTienLai.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtLaiPhat.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");

        lbMaNV.setText("" + taiKhoanNV.getMaNhanVien());
        lbTenNV.setText("" + taiKhoanNV.getTenNhanVien());
    }

    private void loadThongTinTKVay() {
        try {
            TaiKhoanKHDTO taiKhoanKH = taiKhoanKHBUS.getTaiKhoanKHById(maTaiKhoanKHVay);

            if (taiKhoanKH != null) {
                taiKhoanKHVay = taiKhoanKH;
                maKhachHang = taiKhoanKH.getMaKhachHang();

                loadTKNguon();

                lbHoTenKH.setText(taiKhoanKH.getTenKhachHang());
                lbMaTK.setText("" + taiKhoanKH.getMaTKKH());
                txtSTK.setText(taiKhoanKH.getSoTaiKhoan());
                txtTenTK.setText(taiKhoanKH.getTenTaiKhoan());

                VayVonDTO vayVon = vayVonBUS.getByMaTKKH(taiKhoanKHVay.getMaTKKH());

                if (vayVon != null) {
                    maVayVon = vayVon.getMaVayVon();

                    duNo = vayVon.getDuNoGoc();
                    txtDuNo.setValue(new BigDecimal(duNo));

                    System.out.println("Du no goc: " + vayVon.getDuNoGoc());

                    lbTenKH.setText(taiKhoanKH.getTenKhachHang());

                    loadDanhSachKyTraNo(true);
                } else {
                    MessageBox.showErrorMessage(null, "Tài khoản này đã bị đóng!");
                }

            } else {
                MessageBox.showErrorMessage(null, "Không tìm thấy thông tin tài khoản vay!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTKNguon() {
        Map<Integer, String> map = new HashMap<>();
        map = taiKhoanKHBUS.convertListTKNguonToMap(taiKhoanKHVay.getMaKhachHang());

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn tài khoản nguồn-");

        if (map != null) {
            for (String tenNganHang : map.values()) {
                model.addElement(tenNganHang);
            }
        }

        cbxTKNguon.setModel(model);
    }

    private TraKhoanVayDTO getKyTraKhoanVayById(int maKyTraNo) {
        TraKhoanVayDTO traVay = traVayBUS.getTraKhoanVayById(maKyTraNo);

        return traVay != null ? traVay : null;
    }

    private void kiemTraThanhToanKhoanNo() {

        String maPIN = String.valueOf(pwfMaPINNV.getPassword());
        if (maTraKhoanVay == 0) {
            MessageBox.showErrorMessage(null, "Không tìm thấy khoản vay!");
            return;
        }

        if (!traVayBUS.kiemTraKyVayTruoc(maVayVon, kyVay)) {
            MessageBox.showErrorMessage(null, "Vui lòng thanh toán các khoản vay kỳ trước!");
            return;
        }

//        if (!MaHoaMatKhauBUS.checkPassword(taiKhoanNV.getMaPIN(), maPIN)) {
//            MessageBox.showErrorMessage(null, "Mã PIN không đúng!");
//            return;
//        }

        BigDecimal soTienTra = new BigDecimal((txtSoTienTra.getValue()).toString());

        if (!InputValidation.kiemTraSoTien(soTienTra.toString()) || !InputValidation.kiemTraSoTien(tongTien) || !InputValidation.kiemTraSoTien(tienVay) || !InputValidation.kiemTraSoTien(tienLai) || !InputValidation.kiemTraSoTien(tienPhat)) {
            MessageBox.showErrorMessage(null, "Số tiền không hợp lệ");
            return;
        }

        if (InputValidation.kiemTraSoTien(soTienTra.toString())) {

            System.out.println("Dang kiem tra so tien");

            BigDecimal duNoGoc, tongTien, sotienDaTra, tienVay;

            duNoGoc = new BigDecimal(duNo);
            tongTien = new BigDecimal(this.tongTien);
            sotienDaTra = new BigDecimal(this.soTienDaTra);
            tienVay = new BigDecimal(this.tienVay);

            if (soTienTra.compareTo(tongTien) == 1) {
                MessageBox.showErrorMessage(null, "Bạn không thể trả nhiều hơn số tiền cần trả!");
                return;
            } else {
                BigDecimal phanTramTra = soTienTra.divide(tongTien, 1, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));

                System.out.println("Phan tram tra: " + phanTramTra.toString() + "%");

                if (sotienDaTra.compareTo(new BigDecimal("0")) == 0 && phanTramTra.compareTo(new BigDecimal("60")) == -1) {
                    MessageBox.showErrorMessage(null, "Vui lòng thanh toán ít nhất 60% số tiền cần trả!");
                    return;
                }

                kiemTraGiaoDich(duNoGoc, soTienTra, sotienDaTra, tongTien, tienVay);
            }
        }
    }

    private void kiemTraGiaoDich(BigDecimal duNo, BigDecimal soTienTra, BigDecimal soTienDaTra, BigDecimal tongTien, BigDecimal tienVay) {
        GiaoDichBUS giaoDichBUS = new GiaoDichBUS();
        GiaoDichDTO giaoDich = new GiaoDichDTO();
        boolean isTKNguon;
        if (rdbTienMat.isSelected()) {
            isTKNguon = false;
        } else if (rdbTienTaiKhoan.isSelected()) {
            if (maTaiKhoanKHNguon == 0) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản nguồn tiền!");
                return;
            }

            isTKNguon = true;
            giaoDich.setMaTaiKhoanKH(maTaiKhoanKHNguon);
        } else {
            MessageBox.showErrorMessage(null, "Vui lòng chọn nguồn tiền!");
            return;
        }

        giaoDich.setSoTien(soTienTra.toString());
        giaoDich.setMaTaiKhoanKH(maTaiKhoanKHVay);
        giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaTKNV());
        giaoDich.setMaLoaiGiaoDich(6);
        giaoDich.setNgayGiaoDich(fDate.getToday());
        giaoDich.setNoiDungGiaoDich("Khách hàng " + maKhachHang + " trả khoản vay kỳ " + kyVay);
        giaoDich.setMaTrangThai(4);

        String thongBao = giaoDichBUS.thanhToanKhoanVay(giaoDich, isTKNguon);

        if (thongBao.equals("1")) {
            if (capNhatKyThanhToan(soTienTra, soTienDaTra, tongTien) && capNhatDuNo(duNo, soTienTra, tongTien, tienVay)) {
                MessageBox.showInformationMessage(null, "", "Thanh toán khoản vay thành công!");
                resetThongTinKhoanVay(true);
            } else {
                MessageBox.showErrorMessage(null, "Cập nhật dư nợ thất bại");
            }
        } else {
            MessageBox.showErrorMessage(null, "Lỗi: " + thongBao);
        }
    }

    private boolean capNhatKyThanhToan(BigDecimal soTienTra, BigDecimal soTienDaTra, BigDecimal tongTien) {
        int maTrangThai;
        BigDecimal soTienConThieu;
        if (soTienTra.compareTo(tongTien) == 0) {
            maTrangThai = 9;
            soTienConThieu = new BigDecimal("0");
        } else {
            maTrangThai = 14;

            BigDecimal lai, phat, vay;

            lai = new BigDecimal(tienLai);
            phat = new BigDecimal(tienPhat);
            vay = new BigDecimal(tienVay);

            soTienConThieu = soTienTra.subtract(phat);
            soTienConThieu = soTienConThieu.subtract(lai);
            soTienConThieu = vay.subtract(soTienConThieu);

            this.soTienConThieu = soTienConThieu.toString();
            if (!(soTienConThieu.compareTo(BigDecimal.ZERO) == 1)) {
                soTienConThieu = BigDecimal.ZERO;
            }
        }

        soTienTra = soTienTra.add(soTienDaTra);

        return traVayBUS.updateKhoanVayDaTra(maTraKhoanVay, soTienTra.toString(), soTienConThieu.toString(), fDate.getToday(), maTrangThai);
    }

    private boolean capNhatDuNo(BigDecimal duNo, BigDecimal soTienTra, BigDecimal tongSoTienCanTra, BigDecimal soTienVay) {

        BigDecimal duNoMoi;
        int maTrangThai;

        if (soTienTra.compareTo(tongSoTienCanTra) == -1) {
            BigDecimal soTienThieu = new BigDecimal(soTienConThieu);
            duNoMoi = soTienVay.subtract(soTienThieu);
            duNoMoi = duNo.subtract(duNoMoi);
        } else {
            duNoMoi = tongSoTienCanTra.add(soTienVay);
            duNoMoi = duNoMoi.subtract(soTienTra);
            duNoMoi = duNo.subtract(duNoMoi);
        }

        if (duNoMoi.compareTo(BigDecimal.ZERO) == 0) {
            maTrangThai = 9;
        } else {
            maTrangThai = 14;
        }

        return vayVonBUS.updateDuNo(maVayVon, duNoMoi.toString(), maTrangThai);
    }

    private void capNhatCacKyVay() {
        VayVonDTO vayVon = vayVonBUS.getById(maVayVon);

        if (vayVon != null) {

            kiemTraBUS.xuLyTaiKhoanVay(vayVon);
            kiemTraBUS.capNhatLaiTienLai(vayVon);
        }
    }

    private void resetThongTinKhoanVay(boolean isSelected) {
        maTraKhoanVay = 0;
        soTienDaTra = "";
        tongTien = "";
        tienVay = "";
        tienLai = "";
        tienPhat = "";

        if (isSelected) {
            loadThongTinTKVay();
            capNhatCacKyVay();
        }
        loadDanhSachKyTraNo(isSelected);

        txtSoTienTra.setText("");
        pwfMaPINNV.setText("");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGroupNguonTien = new javax.swing.ButtonGroup();
        jPCustomerInfo = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPNguonTien = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        rdbTienMat = new javax.swing.JRadioButton();
        rdbTienTaiKhoan = new javax.swing.JRadioButton();
        jPSoTienTra = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtSoTienTra = new javax.swing.JFormattedTextField();
        jPThongTinGD = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lbMaNV = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbTenNV = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lbTenKH = new javax.swing.JLabel();
        lbSoTienRut = new javax.swing.JLabel();
        lbLoaiGiaoDich = new javax.swing.JLabel();
        jPFooterCus = new javax.swing.JPanel();
        btnTraKhoanVay = new javax.swing.JButton();
        btnChonTKVay = new javax.swing.JButton();
        jPPINCode = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pwfMaPINNV = new javax.swing.JPasswordField();
        jPTKNguon = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cbxTKNguon = new javax.swing.JComboBox<>();
        jPDuNo = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtDuNo = new javax.swing.JFormattedTextField();
        jPAccountInfo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPCusNameInfo = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbHoTenKH = new javax.swing.JLabel();
        jPAccNum = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSTK = new javax.swing.JTextField();
        jPAccType = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenTK = new javax.swing.JTextField();
        jPIdCentizenCard = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTienPhaiTra = new javax.swing.JFormattedTextField();
        jPCusYOB = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtTienLai = new javax.swing.JFormattedTextField();
        jPAddress = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtLaiPhat = new javax.swing.JFormattedTextField();
        jPAccName = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtTienVay = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        lbMaTK = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSKyTra = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1132, 511));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setText("Thông tin thực hiện giao dịch");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setIcon(new FlatSVGIcon("quanlynganhang/icon/thunhap_label.svg")
        );
        jLabel3.setText("Nguồn tiền trả");

        btnGroupNguonTien.add(rdbTienMat);
        rdbTienMat.setSelected(true);
        rdbTienMat.setText("Tiền mặt");
        rdbTienMat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbTienMatActionPerformed(evt);
            }
        });

        btnGroupNguonTien.add(rdbTienTaiKhoan);
        rdbTienTaiKhoan.setText("Từ tài khoản");
        rdbTienTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbTienTaiKhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPNguonTienLayout = new javax.swing.GroupLayout(jPNguonTien);
        jPNguonTien.setLayout(jPNguonTienLayout);
        jPNguonTienLayout.setHorizontalGroup(
            jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNguonTienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPNguonTienLayout.createSequentialGroup()
                        .addComponent(rdbTienMat, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addComponent(rdbTienTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPNguonTienLayout.setVerticalGroup(
            jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNguonTienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPNguonTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbTienMat)
                    .addComponent(rdbTienTaiKhoan))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setIcon(new FlatSVGIcon("quanlynganhang/icon/money_label.svg")
        );
        jLabel4.setText("Số tiền trả");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setText("VND");

        txtSoTienTra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###"))));
        txtSoTienTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoTienTraFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPSoTienTraLayout = new javax.swing.GroupLayout(jPSoTienTra);
        jPSoTienTra.setLayout(jPSoTienTraLayout);
        jPSoTienTraLayout.setHorizontalGroup(
            jPSoTienTraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoTienTraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSoTienTraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPSoTienTraLayout.createSequentialGroup()
                        .addComponent(txtSoTienTra, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPSoTienTraLayout.setVerticalGroup(
            jPSoTienTraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSoTienTraLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPSoTienTraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSoTienTra, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Mã giao dịch viên:");

        lbMaNV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaNV.setText("(Chưa chọn)");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Tên giao dịch viên:");

        lbTenNV.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbTenNV.setText("(Chưa chọn)");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel24.setText("Tên khách hàng trả:");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel25.setText("Số tiền giao dịch:");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel26.setText("Loại giao dịch:");

        lbTenKH.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbTenKH.setText("(Chưa chọn)");

        lbSoTienRut.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbSoTienRut.setText("0 VND");

        lbLoaiGiaoDich.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbLoaiGiaoDich.setText("Thanh toán khoản vay");

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
                    .addComponent(lbTenKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbTenNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbLoaiGiaoDich, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(lbTenKH))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(lbSoTienRut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPThongTinGDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lbLoaiGiaoDich))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        btnTraKhoanVay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTraKhoanVay.setText("Trả khoản vay");
        btnTraKhoanVay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraKhoanVayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPFooterCusLayout = new javax.swing.GroupLayout(jPFooterCus);
        jPFooterCus.setLayout(jPFooterCusLayout);
        jPFooterCusLayout.setHorizontalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFooterCusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTraKhoanVay, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPFooterCusLayout.setVerticalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFooterCusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTraKhoanVay)
                .addContainerGap())
        );

        btnChonTKVay.setText("Chọn tài khoản vay");
        btnChonTKVay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonTKVayActionPerformed(evt);
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
                    .addComponent(pwfMaPINNV, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setIcon(new FlatSVGIcon("quanlynganhang/icon/thunhap_label.svg")
        );
        jLabel5.setText("Tài khoản nguồn");

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
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxTKNguon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPTKNguonLayout.setVerticalGroup(
            jPTKNguonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTKNguonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTKNguon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setIcon(new FlatSVGIcon("quanlynganhang/icon/money_label.svg")
        );
        jLabel10.setText("Dư nợ gốc");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel19.setText("VND");

        txtDuNo.setEditable(false);
        txtDuNo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###"))));

        javax.swing.GroupLayout jPDuNoLayout = new javax.swing.GroupLayout(jPDuNo);
        jPDuNo.setLayout(jPDuNoLayout);
        jPDuNoLayout.setHorizontalGroup(
            jPDuNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDuNoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPDuNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPDuNoLayout.createSequentialGroup()
                        .addComponent(txtDuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPDuNoLayout.setVerticalGroup(
            jPDuNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPDuNoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPDuNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDuNo, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addGroup(jPDuNoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel19)))
                .addContainerGap())
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
                        .addComponent(btnChonTKVay, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPNguonTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPTKNguon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPPINCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                                .addComponent(jPSoTienTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPDuNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(btnChonTKVay))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPNguonTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPTKNguon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPSoTienTra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPDuNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPPINCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPThongTinGD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPFooterCus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin tài khoản vay vốn");

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
        jLabel11.setText("Số tài khoản vay vốn");

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
        jLabel12.setText("Tên tài khoản vay vốn");

        txtTenTK.setEditable(false);
        txtTenTK.setEnabled(false);

        javax.swing.GroupLayout jPAccTypeLayout = new javax.swing.GroupLayout(jPAccType);
        jPAccType.setLayout(jPAccTypeLayout);
        jPAccTypeLayout.setHorizontalGroup(
            jPAccTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenTK, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                    .addGroup(jPAccTypeLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
        jLabel13.setText("Tổng số tiền cần trả cho kỳ này");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setText("VND");

        txtTienPhaiTra.setEditable(false);
        txtTienPhaiTra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###"))));

        javax.swing.GroupLayout jPIdCentizenCardLayout = new javax.swing.GroupLayout(jPIdCentizenCard);
        jPIdCentizenCard.setLayout(jPIdCentizenCardLayout);
        jPIdCentizenCardLayout.setHorizontalGroup(
            jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPIdCentizenCardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPIdCentizenCardLayout.createSequentialGroup()
                        .addComponent(txtTienPhaiTra, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPIdCentizenCardLayout.setVerticalGroup(
            jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPIdCentizenCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTienPhaiTra, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addGroup(jPIdCentizenCardLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel14.setText("Số tiền lãi phải đóng kỳ này");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setText("VND");

        txtTienLai.setEditable(false);
        txtTienLai.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###"))));

        javax.swing.GroupLayout jPCusYOBLayout = new javax.swing.GroupLayout(jPCusYOB);
        jPCusYOB.setLayout(jPCusYOBLayout);
        jPCusYOBLayout.setHorizontalGroup(
            jPCusYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusYOBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCusYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPCusYOBLayout.createSequentialGroup()
                        .addComponent(txtTienLai, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPCusYOBLayout.setVerticalGroup(
            jPCusYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusYOBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCusYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTienLai)
                    .addGroup(jPCusYOBLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel21)))
                .addContainerGap())
        );

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 0, 0));
        jLabel16.setText("Số tiền lãi phạt");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("VND");

        txtLaiPhat.setEditable(false);
        txtLaiPhat.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###"))));

        javax.swing.GroupLayout jPAddressLayout = new javax.swing.GroupLayout(jPAddress);
        jPAddress.setLayout(jPAddressLayout);
        jPAddressLayout.setHorizontalGroup(
            jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAddressLayout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPAddressLayout.createSequentialGroup()
                        .addComponent(txtLaiPhat, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel28)
                        .addGap(0, 38, Short.MAX_VALUE))))
        );
        jPAddressLayout.setVerticalGroup(
            jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28)
                    .addGroup(jPAddressLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtLaiPhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel17.setText("Số tiền vay phải đóng kỳ này");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setText("VND");

        txtTienVay.setEditable(false);
        txtTienVay.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###"))));

        javax.swing.GroupLayout jPAccNameLayout = new javax.swing.GroupLayout(jPAccName);
        jPAccName.setLayout(jPAccNameLayout);
        jPAccNameLayout.setHorizontalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccNameLayout.createSequentialGroup()
                        .addComponent(txtTienVay, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPAccNameLayout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPAccNameLayout.setVerticalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTienVay, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addGroup(jPAccNameLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel20)))
                .addContainerGap())
        );

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel18.setText("Mã tài khoản: ");

        lbMaTK.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaTK.setText("(Chưa chọn)");

        jTableDSKyTra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã trả vay", "Kỳ", "Thời gian", "Số tiền phải trả", "Ngày trả", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTableDSKyTra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDSKyTraMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableDSKyTra);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPAccountInfoLayout = new javax.swing.GroupLayout(jPAccountInfo);
        jPAccountInfo.setLayout(jPAccountInfoLayout);
        jPAccountInfoLayout.setHorizontalGroup(
            jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPAccountInfoLayout.createSequentialGroup()
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPAccountInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                                .addComponent(jPAccNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPAccType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPCusNameInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPAccountInfoLayout.createSequentialGroup()
                                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPIdCentizenCard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPCusYOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPAccName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPAccountInfoLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbMaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(jPAccNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPAccType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPAccName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPIdCentizenCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPCusYOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnChonTKVayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonTKVayActionPerformed
        JDialogTableChonItem chonTKKH = new JDialogTableChonItem(null, true, this, "Chọn tài khoản vay", "DSTKV");
        chonTKKH.setResizable(false);
        chonTKKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonTKKH.setVisible(true);
    }//GEN-LAST:event_btnChonTKVayActionPerformed

    private void cbxTKNguonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTKNguonItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String tenTKNguon = (String) cbxTKNguon.getSelectedItem();
            if (tenTKNguon.equals("-Chọn tài khoản nguồn-")) {
                maTaiKhoanKHNguon = 0;
            } else {
                maTaiKhoanKHNguon = taiKhoanKHBUS.getIdFromSTKNguon(tenTKNguon, maKhachHang);

                if (maTaiKhoanKHNguon == null) {
                    MessageBox.showErrorMessage(null, "Lấy id của tài khoản nguồn thất bại!");
                    return;
                }
            }
        }
    }//GEN-LAST:event_cbxTKNguonItemStateChanged

    private void jTableDSKyTraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDSKyTraMouseClicked
        if (evt.getClickCount() == 2) {
            int selectedRow = jTableDSKyTra.getSelectedRow();

            if (selectedRow >= 0) {
                DefaultTableModel model = (DefaultTableModel) jTableDSKyTra.getModel();
                Object idObj = model.getValueAt(selectedRow, 0);
                Object kyVayObj = model.getValueAt(selectedRow, 1);
                int maKyTraNo = Integer.parseInt(idObj.toString());
                kyVay = Integer.parseInt(kyVayObj.toString());

                TraKhoanVayDTO traVay = getKyTraKhoanVayById(maKyTraNo);
                if (traVay != null) {
                    if (traVay.getMaTrangThai() == 9) {
                        MessageBox.showErrorMessage(null, "Khoản vay này đã thanh toán xong!");
                        return;
                    }

                    maTraKhoanVay = maKyTraNo;

                    if (kyVay != 0) {
                        lbLoaiGiaoDich.setText("Thanh toán khoản vay kỳ " + kyVay);
                    } else {
                        lbLoaiGiaoDich.setText("Thanh toán khoản vay");
                    }

                    BigInteger tienNoGoc, tienLai, tienPhat, tongTien;
                    tienNoGoc = new BigInteger(traVay.getTienNoGoc());
                    tienLai = new BigInteger(traVay.getTienLai());
                    tienPhat = new BigInteger(traVay.getTienPhat());

                    if (traVay.getMaTrangThai() == 15 || traVay.getMaTrangThai() == 14) {
                        System.out.println("Khoan vay nay dang tra thieu!");

                        tienNoGoc = new BigInteger(traVay.getTienConThieu());
                        tienLai = new BigInteger("0");
                        tongTien = tienNoGoc.add(tienPhat);
                    } else {
                        tongTien = tienPhat.add(tienNoGoc.add(tienLai));
                    }

                    soTienDaTra = traVay.getSoTienDaTra();
                    this.tienLai = tienLai.toString();
                    this.tienPhat = tienPhat.toString();
                    tienVay = tienNoGoc.toString();
                    this.tongTien = tongTien.toString();

                    txtTienPhaiTra.setValue(new BigDecimal(this.tongTien));
                    txtTienVay.setValue(new BigDecimal(tienVay));
                    txtTienLai.setValue(new BigDecimal(this.tienLai));
                    txtLaiPhat.setValue(new BigDecimal(this.tienPhat));
                } else {
                    MessageBox.showErrorMessage(null, "Không tìm thấy khoản vay!");
                }
            }
        }
    }//GEN-LAST:event_jTableDSKyTraMouseClicked

    private void rdbTienMatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbTienMatActionPerformed
        if (rdbTienMat.isSelected()) {
            jPTKNguon.setVisible(false);
        } else {
            jPTKNguon.setVisible(true);
        }
    }//GEN-LAST:event_rdbTienMatActionPerformed

    private void rdbTienTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbTienTaiKhoanActionPerformed
        if (rdbTienTaiKhoan.isSelected()) {
            jPTKNguon.setVisible(true);
        } else {
            jPTKNguon.setVisible(false);
        }
    }//GEN-LAST:event_rdbTienTaiKhoanActionPerformed

    private void btnTraKhoanVayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraKhoanVayActionPerformed
        if (maTaiKhoanKHVay == 0) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản vay!");
        } else {
            kiemTraThanhToanKhoanNo();
        }
    }//GEN-LAST:event_btnTraKhoanVayActionPerformed

    private void txtSoTienTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoTienTraFocusLost
        lbSoTienRut.setText(txtSoTienTra.getText() + " VND");
    }//GEN-LAST:event_txtSoTienTraFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonTKVay;
    private javax.swing.ButtonGroup btnGroupNguonTien;
    private javax.swing.JButton btnTraKhoanVay;
    private javax.swing.JComboBox<String> cbxTKNguon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel28;
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
    private javax.swing.JPanel jPCusNameInfo;
    private javax.swing.JPanel jPCusYOB;
    private javax.swing.JPanel jPCustomerInfo;
    private javax.swing.JPanel jPDuNo;
    private javax.swing.JPanel jPFooterCus;
    private javax.swing.JPanel jPIdCentizenCard;
    private javax.swing.JPanel jPNguonTien;
    private javax.swing.JPanel jPPINCode;
    private javax.swing.JPanel jPSoTienTra;
    private javax.swing.JPanel jPTKNguon;
    private javax.swing.JPanel jPThongTinGD;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTableDSKyTra;
    private javax.swing.JLabel lbHoTenKH;
    private javax.swing.JLabel lbLoaiGiaoDich;
    private javax.swing.JLabel lbMaNV;
    private javax.swing.JLabel lbMaTK;
    private javax.swing.JLabel lbSoTienRut;
    private javax.swing.JLabel lbTenKH;
    private javax.swing.JLabel lbTenNV;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPasswordField pwfMaPINNV;
    private javax.swing.JRadioButton rdbTienMat;
    private javax.swing.JRadioButton rdbTienTaiKhoan;
    private javax.swing.JFormattedTextField txtDuNo;
    private javax.swing.JFormattedTextField txtLaiPhat;
    private javax.swing.JTextField txtSTK;
    private javax.swing.JFormattedTextField txtSoTienTra;
    private javax.swing.JTextField txtTenTK;
    private javax.swing.JFormattedTextField txtTienLai;
    private javax.swing.JFormattedTextField txtTienPhaiTra;
    private javax.swing.JFormattedTextField txtTienVay;
    // End of variables declaration//GEN-END:variables
}
