package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ItemEvent;
import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import quanlynganhang.BUS.ChiaQuyenBUS;
import quanlynganhang.BUS.DiaChiBUS;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.MaHoaMatKhauBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.VayVonBUS;
import quanlynganhang.BUS.XuLyAnhBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.DiaChiDTO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.ThoiHanVayDTO;
import quanlynganhang.DTO.VayVonDTO;
import quanlynganhang.GUI.adminUI.JDialogXemAnh;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FormChoVayVon extends javax.swing.JPanel {

    private KhachHangBUS khachHangBUS;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private DiaChiBUS diaChiBUS;
    private VayVonBUS vayVonBUS;
    private GiaoDichBUS giaoDichBUS;
    private TaiKhoanNVDTO taiKhoanNV;
    private KhachHangDTO khachHang;
    private FormatDate fDate;
    private Integer maTinhThanh, maQuanHuyen, maPhuongXa, maThoiHan;
    private String fileName, ngayTraNo;
    private int isVayTinChap;
    private BigInteger soTien, maxGD1, maxGD2, minGD;
    private boolean isAutoGenerateSTK;

    public FormChoVayVon(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) {
        this.taiKhoanNV = taiKhoanNV;
        khachHangBUS = new KhachHangBUS();
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        diaChiBUS = new DiaChiBUS();
        vayVonBUS = new VayVonBUS();
        giaoDichBUS = new GiaoDichBUS();
        fDate = new FormatDate();
        fileName = "no_image.png";
        maThoiHan = 0;
        isVayTinChap = 1;
        maxGD1 = new BigInteger("400000000");
        maxGD2 = new BigInteger("1000000000");
        minGD = new BigInteger("1000000");
        isAutoGenerateSTK = true;

        initComponents();
        initCustomUI();
        loadTinhThanh("");
        loadThoiHan();
    }

    private void initCustomUI() {
        jPCustomerInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccountInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPLoaiHinhVay.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPMinhChung.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTienVay.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPThoiHanVay.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPThuNhap.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPStkTietKiem.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPTenTKTietKiem.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPFooterCus.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPHoTenKH.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCusYOB.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPIdCentizenCard.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPGender.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAddress.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPPhoneNum.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPEmail.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPNgayTraNo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");

        txtTienVay.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tối thiểu 1.000.000");
        txtCCCD.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtNgaySinh.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtSdt.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "(Chưa chọn)");
        txtNgayTraNo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Bỏ trống nếu muốn trả từ tháng sau");

        jPMinhChung.setVisible(false);

    }

    private void loadTinhThanh(String selectedName) {
        int selectedIndex = 0;
        int index = 1;
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListTinhThanhToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn tỉnh thành-");
        if (!selectedName.isEmpty()) {
            for (String tenTinhThanh : map.values()) {
                model.addElement(tenTinhThanh);
                if (tenTinhThanh.equals(selectedName)) {
                    selectedIndex = index;
                }
                index++;
            }

            cbxTinhThanh.setModel(model);
            cbxTinhThanh.setSelectedIndex(selectedIndex);
        } else {
            for (String tenTinhThanh : map.values()) {
                model.addElement(tenTinhThanh);
            }

            cbxTinhThanh.setModel(model);
        }
    }

    private void loadQuanHuyen(int maTinhThanh, String selectedName) {
        int selectedIndex = 0;
        int index = 1;
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListQuanHuyenToMap(maTinhThanh);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn quận huyện-");

        if (!selectedName.isEmpty()) {
            for (String tenQuanHuyen : map.values()) {
                model.addElement(tenQuanHuyen);
                if (tenQuanHuyen.equals(selectedName)) {
                    selectedIndex = index;
                }
                index++;
            }

            cbxQuanHuyen.setModel(model);
            cbxQuanHuyen.setSelectedIndex(selectedIndex);
        } else {
            for (String tenQuanHuyen : map.values()) {
                model.addElement(tenQuanHuyen);
            }

            cbxQuanHuyen.setModel(model);
        }
    }

    private void loadPhuongXa(int maQuanHuyen, String selectedName) {
        int selectedIndex = 0;
        int index = 1;
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListPhuongXaToMap(maQuanHuyen);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn phường xã-");

        if (!selectedName.isEmpty()) {
            for (String tenPhuongXa : map.values()) {
                model.addElement(tenPhuongXa);
                if (tenPhuongXa.equals(selectedName)) {
                    selectedIndex = index;
                }
                index++;
            }

            cbxPhuongXa.setModel(model);
            cbxPhuongXa.setSelectedIndex(selectedIndex);
        } else {
            for (String tenPhuongXa : map.values()) {
                model.addElement(tenPhuongXa);
            }

            cbxPhuongXa.setModel(model);
        }
    }

    private void loadThoiHan() {
        Map<Integer, String> map = new HashMap<>();
        map = vayVonBUS.convertListThoiHanToMap(isVayTinChap);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn thời hạn-");

        for (String tenThoiHan : map.values()) {
            model.addElement(tenThoiHan);
        }

        cbxThoiHan.setModel(model);
    }

    public void dienThongTinKH(int maKhachHang) {
        KhachHangDTO khachHang = khachHangBUS.getKhachHangById(maKhachHang, 0);
        if (khachHang != null) {

            if (khachHang.getNoXau() != 1) {
                this.khachHang = khachHang;

                txtHoDem.setText(khachHang.getHoDem());
                txtTen.setText(khachHang.getTen());
                txtNgaySinh.setText(fDate.toString(khachHang.getNgaySinh()));
                txtCCCD.setText(khachHang.getCccd());
                txtSoNha.setText(khachHang.getSoNha());
                txtSdt.setText(khachHang.getSdt());
                txtEmail.setText(khachHang.getEmail());

                if (khachHang.getGioiTinh().equals("Nam")) {
                    rdbNam.setSelected(true);
                } else if (khachHang.getGioiTinh().equals("Nữ")) {
                    rdbNu.setSelected(true);
                } else {
                    rdbKhac.setSelected(true);
                }

                maPhuongXa = khachHang.getMaPhuongXa();

                DiaChiDTO diaChi = new DiaChiDTO();
                diaChi = diaChiBUS.layDiaChiTuIdPhuongXa(maPhuongXa);

                maQuanHuyen = diaChi.getMaQuanHuyen();
                maTinhThanh = diaChi.getMaTinhThanh();

                loadTinhThanh(diaChi.getTenTinhThanh());
                loadQuanHuyen(maTinhThanh, diaChi.getTenQuanHuyen());
                loadPhuongXa(maQuanHuyen, diaChi.getTenPhuongXa());

                fileName = khachHang.getAnhDaiDien();
                if (fileName != null) {
                    btnXemAnh.setText(fileName);
                } else {
                    btnXemAnh.setText("no_image.png");
                }

                cbxQuanHuyen.setEnabled(false);
                cbxPhuongXa.setEditable(false);
                txtSoNha.setEnabled(false);
            } else {
                MessageBox.showErrorMessage(null, "Người này không thể vay vì đang có nợ xấu!");
            }
        }
    }

    private void taoTaiKhoanVV() {
        if (txtTienVay.getText().isEmpty() || maThoiHan == 0 || txtThuNhap.getText().isEmpty() || (txtSTKVay.getText().isEmpty() && !isAutoGenerateSTK) || txtTenTKVay.getText().isEmpty()) {
            MessageBox.showErrorMessage(null, "Vui lòng điền đầy đủ thông tin!");
        } else {
            StringBuilder error = new StringBuilder();
            error.append("");

            try {
                soTien = new BigInteger(txtTienVay.getText());

                if (isVayTinChap == 1) {
                    if (soTien.compareTo(maxGD1) > 0 || soTien.compareTo(minGD) < 0) {
                        error.append("\nSố tiền vay nằm trong khoảng 1 triệu VND và 400 triệu VND cho một lần vay!");
                    }
                } else {
                    if (soTien.compareTo(maxGD2) > 0 || soTien.compareTo(minGD) < 0) {
                        error.append("\nSố tiền vay nằm trong khoảng 1 triệu VND và 1 tỷ VND cho một lần vay!");
                    }
                }
            } catch (NumberFormatException ne) {
                error.append("\nVui lòng nhập đúng số tiền!");
            }

            if (txtNgayTraNo.getText().isEmpty()) {
                ngayTraNo = "";
            } else {
                if (InputValidation.kiemTraNgay(txtNgayTraNo.getText())) {
                    ngayTraNo = txtNgayTraNo.getText();
                } else {
                    error.append("\nNgày trả nợ không hợp lệ, vui lòng nhập đúng định dạng dd/mm/yyyy");
                }
            }

            TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();

            if (isAutoGenerateSTK) {
                String soTaiKhoanMoi = taiKhoanKHBUS.taoSTKTuDong();

                if (soTaiKhoanMoi.equals("")) {
                    error.append("\nSố tài khoản bị lỗi");
                } else {
                    txtSTKVay.setText(soTaiKhoanMoi);
                    taiKhoanKH.setSoTaiKhoan(soTaiKhoanMoi);
                }
            } else {
                if (InputValidation.kiemTraCCCD(txtSTKVay.getText())) {
                    taiKhoanKH.setSoTaiKhoan(txtSTKVay.getText());
                } else {
                    error.append("\nSố tài khoản không hợp lệ");
                }
            }

            if (error.isEmpty()) {
                if (isVayTinChap == 1 && !vayVonBUS.kiemTraDieuKienVay(txtThuNhap.getText(), txtTienVay.getText(), maThoiHan)) {
                    MessageBox.showErrorMessage(null, "Thu nhập của người này không đủ điều kiện để vay!");
                } else {

                    taiKhoanKH.setMaKhachHang(khachHang.getMaKH());
                    taiKhoanKH.setNgayTao(fDate.getToday());
                    taiKhoanKH.setMaNganHang(1);
                    taiKhoanKH.setSoDu("0");
                    taiKhoanKH.setMaLoaiTaiKhoan(4);
                    taiKhoanKH.setMatKhau(MaHoaMatKhauBUS.encryptPassword("123"));
                    taiKhoanKH.setTenTaiKhoan(txtTenTKVay.getText());
                    taiKhoanKH.setMaTrangThai(6);

                    int maTKKH = taiKhoanKHBUS.addTaiKhoanKH(taiKhoanKH);

                    if (maTKKH != 0) {

                        ThoiHanVayDTO thoiHan = vayVonBUS.getThoiHanById(maThoiHan);

                        VayVonDTO vayVon = new VayVonDTO();

                        vayVon.setMaThoiHan(maThoiHan);
                        vayVon.setTaiSanDamBao(isVayTinChap);
                        vayVon.setAnhMinhChung(fileName);
                        vayVon.setMaTaiKhoanKH(maTKKH);
                        vayVon.setSoTienVay(txtTienVay.getText());
                        vayVon.setMaTrangThai(8);
                        try {
                            vayVon.setNgayTraNo(ngayTraNo == "" ? fDate.addMonth(1) : fDate.toDate(ngayTraNo));
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        vayVon.setNgayHetThoiHan(fDate.addMonth(thoiHan.getSoThoiHan()));
                        vayVon.setDuNoGoc(txtTienVay.getText());

                        if (vayVonBUS.addVayVon(vayVon) != 0) {

                            GiaoDichDTO giaoDich = new GiaoDichDTO();

                            giaoDich.setSoTien(txtTienVay.getText());
                            giaoDich.setMaTaiKhoanKH(maTKKH);
                            giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaTKNV());
                            giaoDich.setMaLoaiGiaoDich(7);
                            giaoDich.setNgayGiaoDich(fDate.getToday());
                            giaoDich.setNoiDungGiaoDich("Chuyển về vay vốn về số tài khoản " + taiKhoanKH.getSoTaiKhoan());
                            giaoDich.setMaTrangThai(4);

                            if (giaoDichBUS.chuyenTienLaiTKVeTK(giaoDich)) {
                                MessageBox.showInformationMessage(null, "", "Tạo tài khoản vay vốn thành công!");
                            } else {
                                MessageBox.showErrorMessage(null, "Chuyển tiền thất bại");
                            }
                        } else {
                            MessageBox.showErrorMessage(null, "Tạo khoản vay thất bại!");
                        }
                    } else {
                        MessageBox.showErrorMessage(null, "Tạo tài khoản vay thất bại!");
                    }
                }
            } else {
                MessageBox.showErrorMessage(null, "Lỗi: " + error);
            }
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
        btnDoiTuongVay = new javax.swing.ButtonGroup();
        btnGroupLoaiHinhVay = new javax.swing.ButtonGroup();
        jPCustomerInfo = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPTienVay = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtTienVay = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPFooterCus = new javax.swing.JPanel();
        btnTaoTKVay = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnChonKH = new javax.swing.JButton();
        jPThoiHanVay = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cbxThoiHan = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jPStkTietKiem = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSTKVay = new javax.swing.JTextField();
        chxSTKTuDong = new javax.swing.JCheckBox();
        jPTenTKTietKiem = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtTenTKVay = new javax.swing.JTextField();
        jPLoaiHinhVay = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        rdbVayKhongDB = new javax.swing.JRadioButton();
        rdbVayCoDB = new javax.swing.JRadioButton();
        jPMinhChung = new javax.swing.JPanel();
        btnThemAnh = new javax.swing.JButton();
        btnXemMinhChung = new javax.swing.JButton();
        jPThuNhap = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtThuNhap = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPNgayTraNo = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtNgayTraNo = new javax.swing.JTextField();
        jPAccountInfo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPIdCentizenCard = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jPCusYOB = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jPAddress = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbxTinhThanh = new javax.swing.JComboBox<>();
        cbxQuanHuyen = new javax.swing.JComboBox<>();
        cbxPhuongXa = new javax.swing.JComboBox<>();
        txtSoNha = new javax.swing.JTextField();
        jPPhoneNum = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jPGender = new javax.swing.JPanel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        rdbKhac = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        jPEmail = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPHoTenKH = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtHoDem = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        btnXemAnh = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1132, 511));

        lbTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle.setText("Thông tin tài khoản vay");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setIcon(new FlatSVGIcon("quanlynganhang/icon/money_label.svg")
        );
        jLabel4.setText("Số tiền đề nghị vay");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel1.setText("VND");

        javax.swing.GroupLayout jPTienVayLayout = new javax.swing.GroupLayout(jPTienVay);
        jPTienVay.setLayout(jPTienVayLayout);
        jPTienVayLayout.setHorizontalGroup(
            jPTienVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTienVayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTienVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPTienVayLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPTienVayLayout.createSequentialGroup()
                        .addComponent(txtTienVay)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addGap(17, 17, 17))))
        );
        jPTienVayLayout.setVerticalGroup(
            jPTienVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTienVayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPTienVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTienVay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        btnTaoTKVay.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoTKVay.setText("Tạo khoản vay");
        btnTaoTKVay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoTKVayActionPerformed(evt);
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
                .addComponent(btnTaoTKVay, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPFooterCusLayout.setVerticalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterCusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoTKVay)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnChonKH.setText("Chọn khách hàng");
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setIcon(new FlatSVGIcon("quanlynganhang/icon/date_create_label.svg")
        );
        jLabel5.setText("Thời hạn vay");

        cbxThoiHan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxThoiHanItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPThoiHanVayLayout = new javax.swing.GroupLayout(jPThoiHanVay);
        jPThoiHanVay.setLayout(jPThoiHanVayLayout);
        jPThoiHanVayLayout.setHorizontalGroup(
            jPThoiHanVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThoiHanVayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPThoiHanVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxThoiHan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPThoiHanVayLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 58, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPThoiHanVayLayout.setVerticalGroup(
            jPThoiHanVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThoiHanVayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxThoiHan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel11.setText("Số tài khoản vay vốn");

        txtSTKVay.setEnabled(false);

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
                    .addComponent(txtSTKVay, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chxSTKTuDong))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPStkTietKiemLayout.setVerticalGroup(
            jPStkTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPStkTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSTKVay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chxSTKTuDong)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg")
        );
        jLabel12.setText("Tên tài khoản vay vốn");

        javax.swing.GroupLayout jPTenTKTietKiemLayout = new javax.swing.GroupLayout(jPTenTKTietKiem);
        jPTenTKTietKiem.setLayout(jPTenTKTietKiemLayout);
        jPTenTKTietKiemLayout.setHorizontalGroup(
            jPTenTKTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTenTKTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPTenTKTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenTKVay)
                    .addGroup(jPTenTKTietKiemLayout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPTenTKTietKiemLayout.setVerticalGroup(
            jPTenTKTietKiemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPTenTKTietKiemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTKVay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setIcon(new FlatSVGIcon("quanlynganhang/icon/thunhap_label.svg")
        );
        jLabel8.setText("Loại hình vay vốn");

        btnGroupLoaiHinhVay.add(rdbVayKhongDB);
        rdbVayKhongDB.setSelected(true);
        rdbVayKhongDB.setText("Vay tín chấp");
        rdbVayKhongDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbVayKhongDBActionPerformed(evt);
            }
        });

        btnGroupLoaiHinhVay.add(rdbVayCoDB);
        rdbVayCoDB.setText("Vay thế chấp");
        rdbVayCoDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbVayCoDBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPLoaiHinhVayLayout = new javax.swing.GroupLayout(jPLoaiHinhVay);
        jPLoaiHinhVay.setLayout(jPLoaiHinhVayLayout);
        jPLoaiHinhVayLayout.setHorizontalGroup(
            jPLoaiHinhVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPLoaiHinhVayLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPLoaiHinhVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdbVayKhongDB, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdbVayCoDB, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22))
        );
        jPLoaiHinhVayLayout.setVerticalGroup(
            jPLoaiHinhVayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPLoaiHinhVayLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbVayKhongDB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbVayCoDB)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThemAnh.setText("Ảnh minh chứng");
        btnThemAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemAnhActionPerformed(evt);
            }
        });

        btnXemMinhChung.setText("(Chưa có)");
        btnXemMinhChung.setBorderPainted(false);
        btnXemMinhChung.setContentAreaFilled(false);
        btnXemMinhChung.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXemMinhChung.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemMinhChungActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPMinhChungLayout = new javax.swing.GroupLayout(jPMinhChung);
        jPMinhChung.setLayout(jPMinhChungLayout);
        jPMinhChungLayout.setHorizontalGroup(
            jPMinhChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMinhChungLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPMinhChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXemMinhChung, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPMinhChungLayout.setVerticalGroup(
            jPMinhChungLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMinhChungLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnThemAnh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXemMinhChung)
                .addContainerGap())
        );

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel17.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel17.setText("Thu nhập trung bình/tháng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("VND");

        javax.swing.GroupLayout jPThuNhapLayout = new javax.swing.GroupLayout(jPThuNhap);
        jPThuNhap.setLayout(jPThuNhapLayout);
        jPThuNhapLayout.setHorizontalGroup(
            jPThuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPThuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPThuNhapLayout.createSequentialGroup()
                        .addComponent(txtThuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPThuNhapLayout.setVerticalGroup(
            jPThuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPThuNhapLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPThuNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(txtThuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel18.setIcon(new FlatSVGIcon("quanlynganhang/icon/yearofbirth_label.svg")
        );
        jLabel18.setText("Ngày bắt đầu trả nợ");

        txtNgayTraNo.setToolTipText("");

        javax.swing.GroupLayout jPNgayTraNoLayout = new javax.swing.GroupLayout(jPNgayTraNo);
        jPNgayTraNo.setLayout(jPNgayTraNoLayout);
        jPNgayTraNoLayout.setHorizontalGroup(
            jPNgayTraNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNgayTraNoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPNgayTraNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPNgayTraNoLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNgayTraNo))
                .addContainerGap())
        );
        jPNgayTraNoLayout.setVerticalGroup(
            jPNgayTraNoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNgayTraNoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayTraNo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
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
                        .addGap(178, 178, 178)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPStkTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPTenTKTietKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPLoaiHinhVay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPMinhChung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCustomerInfoLayout.createSequentialGroup()
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPTienVay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPThuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                                .addComponent(jPThoiHanVay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 6, Short.MAX_VALUE))
                            .addComponent(jPNgayTraNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPLoaiHinhVay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPMinhChung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPThoiHanVay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPTienVay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPThuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPNgayTraNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPTenTKTietKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPStkTietKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jPFooterCus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin khách hàng");

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
                .addGroup(jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(txtCCCD))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPIdCentizenCardLayout.setVerticalGroup(
            jPIdCentizenCardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPIdCentizenCardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(35, Short.MAX_VALUE))
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

        cbxTinhThanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn tỉnh thành-" }));
        cbxTinhThanh.setEnabled(false);
        cbxTinhThanh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTinhThanhItemStateChanged(evt);
            }
        });

        cbxQuanHuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn quận huyện-" }));
        cbxQuanHuyen.setEnabled(false);
        cbxQuanHuyen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxQuanHuyenItemStateChanged(evt);
            }
        });

        cbxPhuongXa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn phường xã-" }));
        cbxPhuongXa.setEnabled(false);
        cbxPhuongXa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPhuongXaItemStateChanged(evt);
            }
        });

        txtSoNha.setEditable(false);
        txtSoNha.setEnabled(false);

        javax.swing.GroupLayout jPAddressLayout = new javax.swing.GroupLayout(jPAddress);
        jPAddress.setLayout(jPAddressLayout);
        jPAddressLayout.setHorizontalGroup(
            jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoNha)
                    .addGroup(jPAddressLayout.createSequentialGroup()
                        .addGroup(jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPAddressLayout.createSequentialGroup()
                                .addComponent(cbxTinhThanh, 0, 191, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbxQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPAddressLayout.setVerticalGroup(
            jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxPhuongXa, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(cbxTinhThanh)
                    .addComponent(cbxQuanHuyen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoNha, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
                .addGroup(jPPhoneNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSdt)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(27, Short.MAX_VALUE))
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

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel29.setIcon(new FlatSVGIcon("quanlynganhang/icon/email_label.svg")
        );
        jLabel29.setText("Email");

        txtEmail.setEditable(false);
        txtEmail.setEnabled(false);

        javax.swing.GroupLayout jPEmailLayout = new javax.swing.GroupLayout(jPEmail);
        jPEmail.setLayout(jPEmailLayout);
        jPEmailLayout.setHorizontalGroup(
            jPEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPEmailLayout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtEmail))
                .addContainerGap())
        );
        jPEmailLayout.setVerticalGroup(
            jPEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel15.setIcon(new FlatSVGIcon("quanlynganhang/icon/id_card_label.svg")
        );
        jLabel15.setText("Họ tên khách hàng");

        txtHoDem.setEditable(false);
        txtHoDem.setEnabled(false);

        txtTen.setEditable(false);
        txtTen.setEnabled(false);

        javax.swing.GroupLayout jPHoTenKHLayout = new javax.swing.GroupLayout(jPHoTenKH);
        jPHoTenKH.setLayout(jPHoTenKHLayout);
        jPHoTenKHLayout.setHorizontalGroup(
            jPHoTenKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHoTenKHLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPHoTenKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                    .addComponent(txtHoDem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTen)
                .addContainerGap())
        );
        jPHoTenKHLayout.setVerticalGroup(
            jPHoTenKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHoTenKHLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPHoTenKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHoDem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnXemAnh.setText("(Chưa có ảnh)");
        btnXemAnh.setBorderPainted(false);
        btnXemAnh.setContentAreaFilled(false);
        btnXemAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXemAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPAccountInfoLayout = new javax.swing.GroupLayout(jPAccountInfo);
        jPAccountInfo.setLayout(jPAccountInfoLayout);
        jPAccountInfoLayout.setHorizontalGroup(
            jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addComponent(jPPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                                .addComponent(jPIdCentizenCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnXemAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addComponent(jPHoTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPCusYOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPAccountInfoLayout.setVerticalGroup(
            jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addComponent(jPHoTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPIdCentizenCard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPCusYOB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPPhoneNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnXemAnh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void rdbVayKhongDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbVayKhongDBActionPerformed
        if (rdbVayKhongDB.isSelected()) {
            jPMinhChung.setVisible(false);
            isVayTinChap = 1;
            loadThoiHan();
        }
    }//GEN-LAST:event_rdbVayKhongDBActionPerformed

    private void rdbVayCoDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbVayCoDBActionPerformed
        if (rdbVayCoDB.isSelected()) {
            jPMinhChung.setVisible(true);
            isVayTinChap = 2;
            loadThoiHan();
        }
    }//GEN-LAST:event_rdbVayCoDBActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        JDialogTableChonItem chonKH = new JDialogTableChonItem(null, true, this, "Chọn khách hàng", "DSKH");
        chonKH.setResizable(false);
        chonKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonKH.setVisible(true);
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void btnXemAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemAnhActionPerformed
        String tenAnh = btnXemAnh.getText();
        if (tenAnh.equals("null") || tenAnh.equals("(Chưa có)")) {
            return;
        } else {
            JDialogXemAnh xemAnh = new JDialogXemAnh(null, true, tenAnh);
            xemAnh.setDefaultCloseOperation(JDialogXemAnh.DISPOSE_ON_CLOSE);
            xemAnh.setVisible(true);
        }
    }//GEN-LAST:event_btnXemAnhActionPerformed

    private void cbxTinhThanhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTinhThanhItemStateChanged

    }//GEN-LAST:event_cbxTinhThanhItemStateChanged

    private void cbxQuanHuyenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxQuanHuyenItemStateChanged

    }//GEN-LAST:event_cbxQuanHuyenItemStateChanged

    private void cbxPhuongXaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPhuongXaItemStateChanged

    }//GEN-LAST:event_cbxPhuongXaItemStateChanged

    private void cbxThoiHanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxThoiHanItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            String tenThoiHan = (String) cbxThoiHan.getSelectedItem();
            if (tenThoiHan.equals("-Chọn thời hạn-")) {
                maThoiHan = 0;
            } else {
                maThoiHan = vayVonBUS.getIdFromTenThoiHan(tenThoiHan, isVayTinChap);

                if (maThoiHan == null) {
                    MessageBox.showErrorMessage(null, "Lấy id của thời hạn thất bại!");
                    return;
                }
            }
        }
    }//GEN-LAST:event_cbxThoiHanItemStateChanged

    private void btnTaoTKVayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoTKVayActionPerformed
        if (txtHoDem.getText().isEmpty()) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn khách hàng!");
        } else {
            if (MessageBox.showConfirmMessage(null, "Xác nhận tạo tài khoản vay vốn cho khách hàng " + khachHang.getHoDem() + " " + khachHang.getTen() + "?") == JOptionPane.YES_OPTION) {
                taoTaiKhoanVV();
            } else {
                return;
            }
        }
    }//GEN-LAST:event_btnTaoTKVayActionPerformed

    private void btnThemAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemAnhActionPerformed
        if (btnThemAnh.getText().equals("Ảnh minh chứng")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileName = XuLyAnhBUS.saveImage(selectedFile);
                btnXemMinhChung.setText(fileName);
            } else {
                return;
            }

            btnThemAnh.setText("Xóa ảnh");
        } else {
            if (XuLyAnhBUS.deleteImage(fileName)) {
                btnThemAnh.setText("Ảnh minh chứng");
                btnXemMinhChung.setText("(Chưa có)");
            } else {
                MessageBox.showErrorMessage(null, "Xóa ảnh thất bại!");
            }
        }
    }//GEN-LAST:event_btnThemAnhActionPerformed

    private void btnXemMinhChungActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemMinhChungActionPerformed
        String tenAnh = btnXemMinhChung.getText();
        if (tenAnh.equals("null") || tenAnh.equals("(Chưa có)")) {
            return;
        } else {
            JDialogXemAnh xemAnh = new JDialogXemAnh(null, true, tenAnh);
            xemAnh.setDefaultCloseOperation(JDialogXemAnh.DISPOSE_ON_CLOSE);
            xemAnh.setVisible(true);
        }
    }//GEN-LAST:event_btnXemMinhChungActionPerformed

    private void chxSTKTuDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chxSTKTuDongActionPerformed
        if (chxSTKTuDong.isSelected()) {
            txtSTKVay.setText("");
            txtSTKVay.setEnabled(false);
            isAutoGenerateSTK = true;
        } else {
            txtSTKVay.setEnabled(true);
            isAutoGenerateSTK = false;
        }
    }//GEN-LAST:event_chxSTKTuDongActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonKH;
    private javax.swing.ButtonGroup btnDoiTuongVay;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.ButtonGroup btnGroupLoaiHinhVay;
    private javax.swing.JButton btnTaoTKVay;
    private javax.swing.JButton btnThemAnh;
    private javax.swing.JButton btnXemAnh;
    private javax.swing.JButton btnXemMinhChung;
    private javax.swing.JComboBox<String> cbxPhuongXa;
    private javax.swing.JComboBox<String> cbxQuanHuyen;
    private javax.swing.JComboBox<String> cbxThoiHan;
    private javax.swing.JComboBox<String> cbxTinhThanh;
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
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPAccountInfo;
    private javax.swing.JPanel jPAddress;
    private javax.swing.JPanel jPCusYOB;
    private javax.swing.JPanel jPCustomerInfo;
    private javax.swing.JPanel jPEmail;
    private javax.swing.JPanel jPFooterCus;
    private javax.swing.JPanel jPGender;
    private javax.swing.JPanel jPHoTenKH;
    private javax.swing.JPanel jPIdCentizenCard;
    private javax.swing.JPanel jPLoaiHinhVay;
    private javax.swing.JPanel jPMinhChung;
    private javax.swing.JPanel jPNgayTraNo;
    private javax.swing.JPanel jPPhoneNum;
    private javax.swing.JPanel jPStkTietKiem;
    private javax.swing.JPanel jPTenTKTietKiem;
    private javax.swing.JPanel jPThoiHanVay;
    private javax.swing.JPanel jPThuNhap;
    private javax.swing.JPanel jPTienVay;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JRadioButton rdbKhac;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JRadioButton rdbVayCoDB;
    private javax.swing.JRadioButton rdbVayKhongDB;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoDem;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtNgayTraNo;
    private javax.swing.JTextField txtSTKVay;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSoNha;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenTKVay;
    private javax.swing.JTextField txtThuNhap;
    private javax.swing.JTextField txtTienVay;
    // End of variables declaration//GEN-END:variables
}
