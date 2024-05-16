/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import quanlynganhang.BUS.XuLyAnhBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.DiaChiDTO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.adminUI.JDialogXemAnh;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;

/**
 *
 * @author THAI
 */
public class FormMoTaiKhoan extends javax.swing.JPanel {

    FormatDate fDate = new FormatDate();
    private TaiKhoanNVDTO taiKhoanNV;
    private KhachHangBUS khachHangBUS;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private GiaoDichBUS giaoDichBUS;
    private DiaChiBUS diaChiBUS;
    private Integer maTinhThanh, maQuanHuyen, maPhuongXa;
    private String fileName;
    private BigInteger minSoDu;
    private String soTienGD;
    private ChucVuDTO chucVu;
    private int quyenThem1, quyenThem2, quyenSua, quyenXoa;

    public FormMoTaiKhoan(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) {
        this.taiKhoanNV = taiKhoanNV;
        this.chucVu = chucVu;
        fDate = new FormatDate();
        khachHangBUS = new KhachHangBUS();
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        giaoDichBUS = new GiaoDichBUS();
        diaChiBUS = new DiaChiBUS();
        fileName = "no_image.png";
        minSoDu = new BigInteger("100000");

        initComponents();
        initCustomUI();
        thietLapChucVu();
        loadTinhThanh("");
    }

    private void thietLapChucVu() {
        quyenThem1 = ChiaQuyenBUS.splitQuyen(chucVu.getqLKhachHang(), 2);
        quyenThem2 = ChiaQuyenBUS.splitQuyen(chucVu.getqLTKKhachHang(), 2);
        quyenSua = ChiaQuyenBUS.splitQuyen(chucVu.getqLKhachHang(), 3);
        quyenXoa = ChiaQuyenBUS.splitQuyen(chucVu.getqLKhachHang(), 4);
    }

    private void initCustomUI() {
        jPCustomerInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPIdCardNum.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccountInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCusName.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPYOB.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPGender.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAddress.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPPhoneNumber.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPEmail.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPMatKhau.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPFooterCus.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPCusNameInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccNum.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccType.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPAccName.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPMoney.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");
        jPFooterAcc.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$BodyPanel.background;");

        txtTen.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên");
        txtHoDem.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Họ");
        txtCCCD.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập đủ 12 số");
        txtSoNha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Số nhà, đường");
        txtSDT.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập đủ 10-11 số");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập với định dạng ***@gmail.com");

        pwfMatKhau.putClientProperty(FlatClientProperties.STYLE, ""
            + "showRevealButton:true;");

        txtSoTaiKhoan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập đủ 12 số");
        txtTien.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tối thiểu là 100.000 VND");

        cbxQuanHuyen.setEnabled(false);
        cbxPhuongXa.setEnabled(false);
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

    private int themKhachHang() throws ParseException {
        StringBuilder error = new StringBuilder();
        error.append("");

        KhachHangDTO khachHang = new KhachHangDTO();

        if (InputValidation.kiemTraTen(txtHoDem.getText())) {
            khachHang.setHoDem(txtHoDem.getText());
        } else {
            error.append("\nHọ đệm không hợp lệ");
        }

        if (InputValidation.kiemTraTen(txtTen.getText())) {
            khachHang.setTen(txtTen.getText());
        } else {
            error.append("\nTên không hợp lệ");
        }

        if (InputValidation.kiemTraNgay(txtNgaySinh.getText())) {
            khachHang.setNgaySinh(fDate.toDate(txtNgaySinh.getText()));
            System.out.println("Ngay sinh: " + khachHang.getNgaySinh());
        } else {
            error.append("\nNgày sinh không hợp lệ");
        }

        if (InputValidation.kiemTraCCCD(txtCCCD.getText())) {
            khachHang.setCccd(txtCCCD.getText());
        } else {
            error.append("\nMã căn cước không hợp lệ");
        }

        if (InputValidation.kiemTraEmail(txtEmail.getText())) {
            khachHang.setEmail(txtEmail.getText());
        } else {
            error.append("\nEmail không hợp lệ");
        }

        if (InputValidation.kiemTraSDT(txtSDT.getText())) {
            khachHang.setSdt(txtSDT.getText());
        } else {
            error.append("\nSố điện thoại không hợp lệ");
        }

        if (!txtSoNha.getText().isEmpty()) {
            khachHang.setSoNha(txtSoNha.getText());
        } else {
            error.append("\nVui lòng nhập địa chỉ");
        }

        if (maTinhThanh == null || maQuanHuyen == null || maPhuongXa == null) {
            error.append("\nVui lòng nhập đầy đủ tỉnh, huyện, xã");
        } else {
            khachHang.setMaPhuongXa(maPhuongXa);
        }

        khachHang.setAnhDaiDien(fileName);

        if (rdbNam.isSelected()) {
            khachHang.setGioiTinh("Nam");
        } else if (rdbNu.isSelected()) {
            khachHang.setGioiTinh("Nữ");
        } else {
            khachHang.setGioiTinh("Khác");
        }

        if (error.isEmpty()) {
            return khachHangBUS.addKhachHang(khachHang, 0);
        } else {
            MessageBox.showErrorMessage(null, "Lỗi: " + error);
            return 0;
        }
    }

    private void resetForm() {
        if (!btnXemAnh.getText().equals("Chưa có ảnh!")) {
            if (lbMaKH.getText().equals("(Chưa có)")) {
                btnThemAnh.setText("Xóa ảnh");
                btnThemAnhActionPerformed(null);
            } else {
                btnXemAnh.setText("(Chưa có)");
                btnThemAnh.setEnabled(true);
            }
        }

        txtHoDem.setText("");
        txtTen.setText("");
        txtCCCD.setText("");
        txtNgaySinh.setText("");
        rdbNam.setSelected(true);
        cbxTinhThanh.setSelectedIndex(0);

        txtSoNha.setText("");
        txtSDT.setText("");
        txtEmail.setText("");

        lbMaKH.setText("(Chưa có)");
        txtSoTaiKhoan.setText("");
        cbxLoaiTaiKhoan.setSelectedIndex(0);
        txtTenTaiKhoan.setText("");
        pwfMatKhau.setText("");
        txtTien.setText("");

        txtHoDem.setEnabled(true);
        txtTen.setEnabled(true);
        txtCCCD.setEnabled(true);
        txtNgaySinh.setEnabled(true);
        rdbNam.setEnabled(true);
        rdbNu.setEnabled(true);
        rdbKhac.setEnabled(true);
        cbxTinhThanh.setEnabled(true);
        cbxQuanHuyen.setEnabled(true);
        cbxPhuongXa.setEnabled(true);
        txtSoNha.setEnabled(true);
        txtEmail.setEnabled(true);
        txtSDT.setEnabled(true);
        btnThemAnh.setEnabled(true);
        btnLuuKH.setEnabled(true);

        txtSoTaiKhoan.setEnabled(false);
        cbxLoaiTaiKhoan.setEnabled(false);
        txtTenTaiKhoan.setEnabled(false);
        pwfMatKhau.setEnabled(false);
        txtTien.setEnabled(false);
        btnTaoTK.setEnabled(false);

        cbxQuanHuyen.setEnabled(false);
        cbxPhuongXa.setEnabled(false);
    }

    public void dienThongTinKH(int maKhachHang) {
        KhachHangDTO khachHang = khachHangBUS.getKhachHangById(maKhachHang, 0);

        if (khachHang != null) {
            lbMaKH.setText("" + khachHang.getMaKH());
            txtHoDem.setText(khachHang.getHoDem());
            txtTen.setText(khachHang.getTen());
            txtCCCD.setText(khachHang.getCccd());
            txtNgaySinh.setText(fDate.toString(khachHang.getNgaySinh()));
            txtSoNha.setText(khachHang.getSoNha());
            txtEmail.setText(khachHang.getEmail());
            txtSDT.setText(khachHang.getSdt());

            String gioiTinh = khachHang.getGioiTinh();
            if (gioiTinh.equals("Nam")) {
                rdbNam.setSelected(true);
            } else if (gioiTinh.equals("Nữ")) {
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

            txtHoDem.setEnabled(false);
            txtTen.setEnabled(false);
            txtCCCD.setEnabled(false);
            txtNgaySinh.setEnabled(false);
            rdbNam.setEnabled(false);
            rdbNu.setEnabled(false);
            rdbKhac.setEnabled(false);
            cbxTinhThanh.setEnabled(false);
            cbxQuanHuyen.setEnabled(false);
            cbxPhuongXa.setEnabled(false);
            txtSoNha.setEnabled(false);
            txtEmail.setEnabled(false);
            txtSDT.setEnabled(false);
            btnThemAnh.setEnabled(false);
            btnLuuKH.setEnabled(false);

            txtSoTaiKhoan.setEnabled(true);
            cbxLoaiTaiKhoan.setEnabled(true);
            txtTenTaiKhoan.setEnabled(true);
            pwfMatKhau.setEnabled(true);
            txtTien.setEnabled(true);
            btnTaoTK.setEnabled(true);
        }
    }

    private boolean themTaiKhoanKH() {
        StringBuilder error = new StringBuilder();
        error.append("");
        TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();

        taiKhoanKH.setMaKhachHang(Integer.parseInt(lbMaKH.getText()));

        if (InputValidation.kiemTraCCCD(txtSoTaiKhoan.getText())) {
            taiKhoanKH.setSoTaiKhoan(txtSoTaiKhoan.getText());
        } else {
            error.append("\nSố tài khoản không hợp lệ");
        }

        taiKhoanKH.setTenTaiKhoan(txtTenTaiKhoan.getText());

        if (cbxLoaiTaiKhoan.getSelectedIndex() == 0) {
            taiKhoanKH.setMaLoaiTaiKhoan(1);
        } else {
            taiKhoanKH.setMaLoaiTaiKhoan(2);
        }

        String password = String.valueOf(pwfMatKhau.getPassword());
        if (InputValidation.kiemTraMatKhau(password)) {
            taiKhoanKH.setMatKhau(MaHoaMatKhauBUS.encryptPassword(password));
        } else {
            error.append("\nMật khẩu phải có độ dài từ 6-12 ký tự, chứa cả ký tự hoa, thường và ký tự đặc biệt");
        }

        try {
            BigInteger soTien = new BigInteger(txtTien.getText());

            if (soTien.compareTo(minSoDu) >= 0) {
                soTienGD = txtTien.getText();
                taiKhoanKH.setSoDu("0");
            } else {
                error.append("\nSố tiền phải lớn hơn hoặc bằng 100.000 VND");
            }
        } catch (NumberFormatException pe) {
            error.append("\nSố tiền nhập không đúng");
        }

        taiKhoanKH.setNgayTao(fDate.getToday());

        if (error.isEmpty()) {
            int maTKKH = taiKhoanKHBUS.addTaiKhoanKH(taiKhoanKH);

            if (maTKKH != 0) {
                GiaoDichDTO giaoDich = new GiaoDichDTO();

                giaoDich.setMaTaiKhoanKH(maTKKH);
                giaoDich.setMaTaiKhoanNV(taiKhoanNV.getMaTKNV());
                giaoDich.setNgayGiaoDich(fDate.getToday());
                giaoDich.setNoiDungGiaoDich("Chuyển tiền vào tài khoản lần đầu cho tài khoản " + txtSoTaiKhoan.getText());
                giaoDich.setMaLoaiGiaoDich(4);
                giaoDich.setSoTien(soTienGD);
                giaoDich.setMaTrangThai(4);

                return giaoDichBUS.napTien(giaoDich);

            } else {
                return false;
            }
        } else {
            MessageBox.showErrorMessage(null, "Lỗi: " + error);
            return false;
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
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPCusName = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtHoDem = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jPYOB = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jPGender = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        rdbKhac = new javax.swing.JRadioButton();
        jPAddress = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtSoNha = new javax.swing.JTextField();
        cbxTinhThanh = new javax.swing.JComboBox<>();
        cbxQuanHuyen = new javax.swing.JComboBox<>();
        cbxPhuongXa = new javax.swing.JComboBox<>();
        jPPhoneNumber = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jPEmail = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPFooterCus = new javax.swing.JPanel();
        btnThemAnh = new javax.swing.JButton();
        btnLuuKH = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnXemAnh = new javax.swing.JButton();
        jPIdCardNum = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jPAccountInfo = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPCusNameInfo = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lbMaKH = new javax.swing.JLabel();
        jPAccNum = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtSoTaiKhoan = new javax.swing.JTextField();
        jPAccType = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        cbxLoaiTaiKhoan = new javax.swing.JComboBox<>();
        jPAccName = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        jPMoney = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtTien = new javax.swing.JTextField();
        lbMoneyType = new javax.swing.JLabel();
        jPFooterAcc = new javax.swing.JPanel();
        btnTaoTK = new javax.swing.JButton();
        btnChonKH = new javax.swing.JButton();
        jPMatKhau = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        pwfMatKhau = new javax.swing.JPasswordField();

        setPreferredSize(new java.awt.Dimension(1132, 511));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Thông tin khách hàng");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setIcon(new FlatSVGIcon("quanlynganhang/icon/customer_label.svg")
        );
        jLabel3.setText("Họ tên khách hàng");

        javax.swing.GroupLayout jPCusNameLayout = new javax.swing.GroupLayout(jPCusName);
        jPCusName.setLayout(jPCusNameLayout);
        jPCusNameLayout.setHorizontalGroup(
            jPCusNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCusNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPCusNameLayout.createSequentialGroup()
                        .addComponent(txtHoDem, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPCusNameLayout.setVerticalGroup(
            jPCusNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCusNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(txtHoDem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setIcon(new FlatSVGIcon("quanlynganhang/icon/yearofbirth_label.svg")
        );
        jLabel4.setText("Ngày sinh");

        javax.swing.GroupLayout jPYOBLayout = new javax.swing.GroupLayout(jPYOB);
        jPYOB.setLayout(jPYOBLayout);
        jPYOBLayout.setHorizontalGroup(
            jPYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPYOBLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPYOBLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtNgaySinh))
                .addContainerGap())
        );
        jPYOBLayout.setVerticalGroup(
            jPYOBLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPYOBLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setIcon(new FlatSVGIcon("quanlynganhang/icon/gender_label.svg")
        );
        jLabel5.setText("Giới tính");

        btnGroupGender.add(rdbNam);
        rdbNam.setSelected(true);
        rdbNam.setText("Nam");

        btnGroupGender.add(rdbNu);
        rdbNu.setText("Nữ");

        btnGroupGender.add(rdbKhac);
        rdbKhac.setText("Khác");

        javax.swing.GroupLayout jPGenderLayout = new javax.swing.GroupLayout(jPGender);
        jPGender.setLayout(jPGenderLayout);
        jPGenderLayout.setHorizontalGroup(
            jPGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPGenderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPGenderLayout.createSequentialGroup()
                        .addComponent(rdbNam)
                        .addGap(18, 18, 18)
                        .addComponent(rdbNu)
                        .addGap(18, 18, 18)
                        .addComponent(rdbKhac)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPGenderLayout.setVerticalGroup(
            jPGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPGenderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPGenderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbNam)
                    .addComponent(rdbNu)
                    .addComponent(rdbKhac))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setIcon(new FlatSVGIcon("quanlynganhang/icon/address_label.svg")
        );
        jLabel6.setText("Địa chỉ");

        cbxTinhThanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn tỉnh thành-" }));
        cbxTinhThanh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTinhThanhItemStateChanged(evt);
            }
        });

        cbxQuanHuyen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn quận huyện-" }));
        cbxQuanHuyen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxQuanHuyenItemStateChanged(evt);
            }
        });

        cbxPhuongXa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn phường xã-" }));
        cbxPhuongXa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPhuongXaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPAddressLayout = new javax.swing.GroupLayout(jPAddress);
        jPAddress.setLayout(jPAddressLayout);
        jPAddressLayout.setHorizontalGroup(
            jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPAddressLayout.createSequentialGroup()
                        .addComponent(cbxTinhThanh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPAddressLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtSoNha))
                .addContainerGap())
        );
        jPAddressLayout.setVerticalGroup(
            jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAddressLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPAddressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxPhuongXa, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(cbxQuanHuyen)
                    .addComponent(cbxTinhThanh, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoNha, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setIcon(new FlatSVGIcon("quanlynganhang/icon/phone_label.svg")
        );
        jLabel7.setText("Số điện thoại");

        javax.swing.GroupLayout jPPhoneNumberLayout = new javax.swing.GroupLayout(jPPhoneNumber);
        jPPhoneNumber.setLayout(jPPhoneNumberLayout);
        jPPhoneNumberLayout.setHorizontalGroup(
            jPPhoneNumberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhoneNumberLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPhoneNumberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(txtSDT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPPhoneNumberLayout.setVerticalGroup(
            jPPhoneNumberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPhoneNumberLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setIcon(new FlatSVGIcon("quanlynganhang/icon/email_label.svg")
        );
        jLabel8.setText("Email");

        javax.swing.GroupLayout jPEmailLayout = new javax.swing.GroupLayout(jPEmail);
        jPEmail.setLayout(jPEmailLayout);
        jPEmailLayout.setHorizontalGroup(
            jPEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPEmailLayout.setVerticalGroup(
            jPEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnThemAnh.setIcon(new FlatSVGIcon("quanlynganhang/icon/image_btn.svg")
        );
        btnThemAnh.setText("Lấy ảnh");
        btnThemAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemAnhActionPerformed(evt);
            }
        });

        btnLuuKH.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLuuKH.setText("Kiểm tra và lưu");
        btnLuuKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuuKHActionPerformed(evt);
            }
        });

        btnReset.setText("Đặt lại");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnXemAnh.setText("Chưa có ảnh!");
        btnXemAnh.setToolTipText("Xem ảnh");
        btnXemAnh.setBorderPainted(false);
        btnXemAnh.setContentAreaFilled(false);
        btnXemAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXemAnh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnXemAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPFooterCusLayout = new javax.swing.GroupLayout(jPFooterCus);
        jPFooterCus.setLayout(jPFooterCusLayout);
        jPFooterCusLayout.setHorizontalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterCusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThemAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXemAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReset)
                .addGap(18, 18, 18)
                .addComponent(btnLuuKH, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPFooterCusLayout.setVerticalGroup(
            jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterCusLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPFooterCusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThemAnh)
                    .addComponent(btnLuuKH)
                    .addComponent(btnReset)
                    .addComponent(btnXemAnh))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel18.setIcon(new FlatSVGIcon("quanlynganhang/icon/id_card_label.svg")
        );
        jLabel18.setText("Mã căn cước công dân");

        javax.swing.GroupLayout jPIdCardNumLayout = new javax.swing.GroupLayout(jPIdCardNum);
        jPIdCardNum.setLayout(jPIdCardNumLayout);
        jPIdCardNumLayout.setHorizontalGroup(
            jPIdCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPIdCardNumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPIdCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCCCD)
                    .addGroup(jPIdCardNumLayout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPIdCardNumLayout.setVerticalGroup(
            jPIdCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPIdCardNumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCCCD, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
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
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPYOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPGender, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPCusName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPIdCardNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                        .addComponent(jPPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPCustomerInfoLayout.setVerticalGroup(
            jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCustomerInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPIdCardNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPCusName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPYOB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(jPAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPCustomerInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jPFooterCus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thông tin tài khoản khách hàng");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Mã khách hàng: ");

        lbMaKH.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lbMaKH.setText("(Chưa có)");

        javax.swing.GroupLayout jPCusNameInfoLayout = new javax.swing.GroupLayout(jPCusNameInfo);
        jPCusNameInfo.setLayout(jPCusNameInfoLayout);
        jPCusNameInfoLayout.setHorizontalGroup(
            jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPCusNameInfoLayout.setVerticalGroup(
            jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCusNameInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCusNameInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbMaKH))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_num_label.svg")
        );
        jLabel11.setText("Số tài khoản khách hàng");

        txtSoTaiKhoan.setEnabled(false);

        javax.swing.GroupLayout jPAccNumLayout = new javax.swing.GroupLayout(jPAccNum);
        jPAccNum.setLayout(jPAccNumLayout);
        jPAccNumLayout.setHorizontalGroup(
            jPAccNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoTaiKhoan)
                    .addGroup(jPAccNumLayout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 115, Short.MAX_VALUE)))
                .addContainerGap())
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
        jLabel12.setText("Loại tài khoản");

        cbxLoaiTaiKhoan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tài khoản cá nhân", "Tài khoản doanh nghiệp" }));
        cbxLoaiTaiKhoan.setEnabled(false);

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
                    .addComponent(cbxLoaiTaiKhoan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPAccTypeLayout.setVerticalGroup(
            jPAccTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxLoaiTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel13.setIcon(new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg")
        );
        jLabel13.setText("Tên tài khoản");

        txtTenTaiKhoan.setEnabled(false);

        javax.swing.GroupLayout jPAccNameLayout = new javax.swing.GroupLayout(jPAccName);
        jPAccName.setLayout(jPAccNameLayout);
        jPAccNameLayout.setHorizontalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenTaiKhoan)
                    .addGroup(jPAccNameLayout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 311, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPAccNameLayout.setVerticalGroup(
            jPAccNameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccNameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel14.setIcon(new FlatSVGIcon("quanlynganhang/icon/money_label.svg")
        );
        jLabel14.setText("Số tiền ban đầu");

        txtTien.setEnabled(false);
        txtTien.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTienFocusLost(evt);
            }
        });

        lbMoneyType.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbMoneyType.setText("VND");

        javax.swing.GroupLayout jPMoneyLayout = new javax.swing.GroupLayout(jPMoney);
        jPMoney.setLayout(jPMoneyLayout);
        jPMoneyLayout.setHorizontalGroup(
            jPMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMoneyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPMoneyLayout.createSequentialGroup()
                        .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbMoneyType, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPMoneyLayout.setVerticalGroup(
            jPMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMoneyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbMoneyType)
                    .addGroup(jPMoneyLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnTaoTK.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTaoTK.setText("Tạo tài khoản");
        btnTaoTK.setEnabled(false);
        btnTaoTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoTKActionPerformed(evt);
            }
        });

        btnChonKH.setText("Chọn khách hàng");
        btnChonKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPFooterAccLayout = new javax.swing.GroupLayout(jPFooterAcc);
        jPFooterAcc.setLayout(jPFooterAccLayout);
        jPFooterAccLayout.setHorizontalGroup(
            jPFooterAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPFooterAccLayout.createSequentialGroup()
                .addGap(250, 250, 250)
                .addComponent(btnChonKH, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTaoTK, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPFooterAccLayout.setVerticalGroup(
            jPFooterAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPFooterAccLayout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(jPFooterAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTaoTK)
                    .addComponent(btnChonKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel15.setIcon(new FlatSVGIcon("quanlynganhang/icon/password_label.svg")
        );
        jLabel15.setText("Mật khẩu");

        pwfMatKhau.setEnabled(false);

        javax.swing.GroupLayout jPMatKhauLayout = new javax.swing.GroupLayout(jPMatKhau);
        jPMatKhau.setLayout(jPMatKhauLayout);
        jPMatKhauLayout.setHorizontalGroup(
            jPMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMatKhauLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pwfMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPMatKhauLayout.setVerticalGroup(
            jPMatKhauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPMatKhauLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pwfMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPAccountInfoLayout = new javax.swing.GroupLayout(jPAccountInfo);
        jPAccountInfo.setLayout(jPAccountInfoLayout);
        jPAccountInfoLayout.setHorizontalGroup(
            jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPAccountInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPFooterAcc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addComponent(jPAccNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jPAccType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPCusNameInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPMoney, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPAccountInfoLayout.createSequentialGroup()
                        .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPAccName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(12, 12, 12)
                .addGroup(jPAccountInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPAccType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPAccNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPAccName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPFooterAcc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn đặt lại?") == JOptionPane.YES_OPTION) {
            resetForm();
        } else {
            return;
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtTienFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTienFocusLost
        // Hàm xử lý dịnh dạng tiền tệ
    }//GEN-LAST:event_txtTienFocusLost

    private void cbxTinhThanhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTinhThanhItemStateChanged
        String tenTinhThanh = (String) cbxTinhThanh.getSelectedItem();
        if (tenTinhThanh.equals("-Chọn tỉnh thành-")) {
            cbxQuanHuyen.setSelectedIndex(0);
            cbxPhuongXa.setSelectedIndex(0);

            cbxQuanHuyen.setEnabled(false);
            cbxPhuongXa.setEnabled(false);
            txtSoNha.setEnabled(false);
            maTinhThanh = null;
        } else {
            maTinhThanh = diaChiBUS.getIdFromTenTinhThanh(tenTinhThanh);

            if (maTinhThanh != null) {
                loadQuanHuyen(maTinhThanh.intValue(), "");
                cbxQuanHuyen.setEnabled(true);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của tỉnh thành thất bại!");
            }
        }
    }//GEN-LAST:event_cbxTinhThanhItemStateChanged

    private void cbxQuanHuyenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxQuanHuyenItemStateChanged
        if (maTinhThanh == null) {
            return;
        }

        String tenQuanHuyen = (String) cbxQuanHuyen.getSelectedItem();
        if (tenQuanHuyen.equals("-Chọn quận huyện-")) {
            cbxPhuongXa.setSelectedIndex(0);

            cbxPhuongXa.setEnabled(false);
            txtSoNha.setEnabled(false);
            maQuanHuyen = null;
        } else {
            maQuanHuyen = diaChiBUS.getIdFromTenQuanHuyen(tenQuanHuyen, maTinhThanh);

            if (maQuanHuyen != null) {
                loadPhuongXa(maQuanHuyen.intValue(), "");
                cbxPhuongXa.setEnabled(true);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của quận huyện thất bại!");
            }
        }
    }//GEN-LAST:event_cbxQuanHuyenItemStateChanged

    private void cbxPhuongXaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPhuongXaItemStateChanged
        if (maQuanHuyen == null) {
            return;
        }

        String tenPhuongXa = (String) cbxPhuongXa.getSelectedItem();
        if (tenPhuongXa.equals("-Chọn phường xã-")) {
            txtSoNha.setEnabled(false);
            maPhuongXa = null;
        } else {
            maPhuongXa = diaChiBUS.getIdFromTenPhuongXa(tenPhuongXa, maQuanHuyen);

            if (maPhuongXa != null) {
                txtSoNha.setEnabled(true);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của phường xã thất bại!");
            }
        }
    }//GEN-LAST:event_cbxPhuongXaItemStateChanged

    private void btnThemAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemAnhActionPerformed
        if (btnThemAnh.getText().equals("Lấy ảnh")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                fileName = XuLyAnhBUS.saveImage(selectedFile);
                btnXemAnh.setText(fileName);
            } else {
                return;
            }

            btnThemAnh.setText("Xóa ảnh");
        } else {
            if (XuLyAnhBUS.deleteImage(fileName)) {
                btnThemAnh.setText("Lấy ảnh");
                btnXemAnh.setText("(Chưa có)");
            } else {
                MessageBox.showErrorMessage(null, "Xóa ảnh thất bại!");
            }
        }
    }//GEN-LAST:event_btnThemAnhActionPerformed

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

    private void btnLuuKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuuKHActionPerformed
        if (quyenThem1 == 1) {
            try {
                int maKhachHang = themKhachHang();
                if (maKhachHang != 0) {
                    MessageBox.showInformationMessage(null, "", "Thêm khách hàng thành công");
                    dienThongTinKH(maKhachHang);
                } else {
                    MessageBox.showInformationMessage(null, "", "Thêm khách hàng thất bại!");
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_btnLuuKHActionPerformed

    private void btnChonKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonKHActionPerformed
        JDialogTableChonItem chonKH = new JDialogTableChonItem(null, true, this, "Chọn khách hàng", "DSKH");
        chonKH.setResizable(false);
        chonKH.setDefaultCloseOperation(JDialogTableChonItem.DISPOSE_ON_CLOSE);
        chonKH.setVisible(true);
    }//GEN-LAST:event_btnChonKHActionPerformed

    private void btnTaoTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoTKActionPerformed
        if (quyenThem2 == 1) {
            if (MessageBox.showConfirmMessage(this, "Xác nhận tạo tài khoản?") == JOptionPane.YES_OPTION) {
                if (themTaiKhoanKH()) {
                    MessageBox.showInformationMessage(null, "", "Tạo tài khoản khách hàng thành công!");
                } else {
                    MessageBox.showErrorMessage(null, "Tạo tài khoản khách hàng thất bại!");
                }
            } else {
                return;
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_btnTaoTKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChonKH;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.JButton btnLuuKH;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnTaoTK;
    private javax.swing.JButton btnThemAnh;
    private javax.swing.JButton btnXemAnh;
    private javax.swing.JComboBox<String> cbxLoaiTaiKhoan;
    private javax.swing.JComboBox<String> cbxPhuongXa;
    private javax.swing.JComboBox<String> cbxQuanHuyen;
    private javax.swing.JComboBox<String> cbxTinhThanh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPCusName;
    private javax.swing.JPanel jPCusNameInfo;
    private javax.swing.JPanel jPCustomerInfo;
    private javax.swing.JPanel jPEmail;
    private javax.swing.JPanel jPFooterAcc;
    private javax.swing.JPanel jPFooterCus;
    private javax.swing.JPanel jPGender;
    private javax.swing.JPanel jPIdCardNum;
    private javax.swing.JPanel jPMatKhau;
    private javax.swing.JPanel jPMoney;
    private javax.swing.JPanel jPPhoneNumber;
    private javax.swing.JPanel jPYOB;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbMaKH;
    private javax.swing.JLabel lbMoneyType;
    private javax.swing.JPasswordField pwfMatKhau;
    private javax.swing.JRadioButton rdbKhac;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoDem;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoNha;
    private javax.swing.JTextField txtSoTaiKhoan;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenTaiKhoan;
    private javax.swing.JTextField txtTien;
    // End of variables declaration//GEN-END:variables
}
