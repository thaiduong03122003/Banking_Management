package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import quanlynganhang.BUS.ChiaQuyenBUS;
import quanlynganhang.BUS.DiaChiBUS;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.BUS.XuLyAnhBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.DiaChiDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JFrameChiTietKH extends javax.swing.JFrame {

    private DiaChiBUS diaChiBUS;
    private KhachHangBUS khachHangBUS;
    private Integer maTinhThanh, maQuanHuyen, maPhuongXa;
    private KhachHangDTO khachHangDTO;
    private String fileName, newFileName;
    private int quyenSua, quyenXoa;

    public JFrameChiTietKH(KhachHangDTO khachHang, boolean isEdit, int quyenSua, int quyenXoa) {
        diaChiBUS = new DiaChiBUS();
        khachHangBUS = new KhachHangBUS();
        khachHangDTO = new KhachHangDTO();
        khachHangDTO = khachHang;
        newFileName = "";
        this.quyenSua = quyenSua;
        this.quyenXoa = quyenXoa;

        initComponents();
        dienThongTin(khachHang);
        editInfo(isEdit);
    }

    private void editInfo(boolean isEdit) {
        if (isEdit) {
            btnSuaThongTinActionPerformed(null);
            lbChiTietThongTin.setText("Sửa thông tin khách hàng");
        } else {
            doiTrangThaiNhap(isEdit);
        }
    }

    private void doiTrangThaiNhap(boolean isEnabel) {
        txtHoDem.setEnabled(isEnabel);
        txtTen.setEnabled(isEnabel);
        txtNgaySinh.setEnabled(isEnabel);
        rdbNam.setEnabled(isEnabel);
        rdbNu.setEnabled(isEnabel);
        rdbKhac.setEnabled(isEnabel);
        cbxTinhThanh.setEnabled(isEnabel);
        cbxQuanHuyen.setEnabled(isEnabel);
        cbxPhuongXa.setEnabled(isEnabel);
        txtSoNha.setEnabled(isEnabel);
        txtEmail.setEnabled(isEnabel);
        txtSdt.setEnabled(isEnabel);
        txtCCCD.setEnabled(isEnabel);
        btnChonAnh.setEnabled(isEnabel);
        btnCapNhat.setEnabled(isEnabel);
    }

    private void loadTinhThanh(String selectedName) {
        int selectedIndex = 0;
        int index = 1;
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListTinhThanhToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn tỉnh thành-");

        for (String tenTinhThanh : map.values()) {
            model.addElement(tenTinhThanh);
            if (tenTinhThanh.equals(selectedName)) {
                selectedIndex = index;
            }
            index++;
        }

        cbxTinhThanh.setModel(model);
        cbxTinhThanh.setSelectedIndex(selectedIndex);
    }

    private void loadQuanHuyen(int maTinhThanh, String selectedName) {
        int selectedIndex = 0;
        int index = 1;
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListQuanHuyenToMap(maTinhThanh);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn quận huyện-");

        for (String tenQuanHuyen : map.values()) {
            model.addElement(tenQuanHuyen);
            if (tenQuanHuyen.equals(selectedName)) {
                selectedIndex = index;
            }
            index++;
        }

        cbxQuanHuyen.setModel(model);
        cbxQuanHuyen.setSelectedIndex(selectedIndex);
    }

    private void loadPhuongXa(int maQuanHuyen, String selectedName) {
        int selectedIndex = 0;
        int index = 1;
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListPhuongXaToMap(maQuanHuyen);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn phường xã-");

        for (String tenPhuongXa : map.values()) {
            model.addElement(tenPhuongXa);
            if (tenPhuongXa.equals(selectedName)) {
                selectedIndex = index;
            }
            index++;
        }

        cbxPhuongXa.setModel(model);
        cbxPhuongXa.setSelectedIndex(selectedIndex);
    }

    private void loadAnh(String tenFileAnh) {
        String imagePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "quanlynganhang" + File.separator + "image" + File.separator + "data_image" + File.separator + tenFileAnh;
        ImageIcon icon = new ImageIcon(imagePath);
        ptbAnh.setImage(icon);
        repaint();
        revalidate();
    }

    private void dienThongTin(KhachHangDTO khachHang) {
        FormatDate fDate = new FormatDate();

        txtMaKH.setText("" + khachHang.getMaKH());
        txtHoDem.setText(khachHang.getHoDem());
        txtTen.setText(khachHang.getTen());
        txtSoNha.setText(khachHang.getSoNha());
        txtEmail.setText(khachHang.getEmail());
        txtSdt.setText(khachHang.getSdt());
        txtCCCD.setText(khachHang.getCccd());

        if (khachHang.getNgaySinh() == null) {
            txtNgaySinh.setText("");
        } else {
            txtNgaySinh.setText(fDate.toString(khachHang.getNgaySinh()));
        }

        String gioiTinh = khachHang.getGioiTinh();
        if (gioiTinh.equals("Nam")) {
            rdbNam.setSelected(true);
        } else if (gioiTinh.equals("Nữ")) {
            rdbNu.setSelected(true);
        } else {
            rdbKhac.setSelected(true);
        }

        if (khachHang.getNoXau() == 0) {
            rdbKhongNo.setSelected(true);
            btnXoaNo.setVisible(false);
        } else {
            rdbCoNo.setSelected(true);
            btnXoaNo.setVisible(true);
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
            loadAnh(fileName);
        } else {
            fileName = "no_image.png";
            loadAnh(fileName);
        }

    }

    private boolean capNhatKhachHang() throws ParseException {
        StringBuilder error = new StringBuilder();
        FormatDate fDate = new FormatDate();
        error.append("");

        KhachHangDTO khachHang = new KhachHangDTO();

        int maKhachHang = Integer.parseInt(txtMaKH.getText());
        khachHang.setMaKH(maKhachHang);

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

        if (InputValidation.kiemTraSDT(txtSdt.getText())) {
            khachHang.setSdt(txtSdt.getText());
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

        if (rdbNam.isSelected()) {
            khachHang.setGioiTinh("Nam");
        } else if (rdbNu.isSelected()) {
            khachHang.setGioiTinh("Nữ");
        } else {
            khachHang.setGioiTinh("Khác");
        }

        if (rdbKhongNo.isSelected()) {
            khachHang.setNoXau(0);
        } else {
            khachHang.setNoXau(1);
        }

        if (newFileName.isEmpty()) {
            if (fileName == null) {
                khachHang.setAnhDaiDien("no_image.png");
            } else {
                khachHang.setAnhDaiDien(fileName);
            }

        } else {
            khachHang.setAnhDaiDien(newFileName);
        }

        if (error.isEmpty()) {
            if (khachHangBUS.updateKhachHang(khachHang, 0)) {
                khachHangDTO = khachHang;
                return true;
            }
            return false;
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
        btnGroupNoXau = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lbChiTietThongTin = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtHoDem = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        rdbKhac = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtSoNha = new javax.swing.JTextField();
        cbxTinhThanh = new javax.swing.JComboBox<>();
        cbxQuanHuyen = new javax.swing.JComboBox<>();
        cbxPhuongXa = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        rdbKhongNo = new javax.swing.JRadioButton();
        rdbCoNo = new javax.swing.JRadioButton();
        btnXoaNo = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnChonAnh = new javax.swing.JButton();
        btnSuaThongTin = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        ptbAnh = new quanlynganhang.GUI.model.picturebox.PictureBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbChiTietThongTin.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lbChiTietThongTin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbChiTietThongTin.setText("Chi tiết thông tin khách hàng");

        jLabel2.setText("Mã khách hàng:");

        txtMaKH.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(33, 33, 33)
                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Họ tên khách hàng:");

        txtHoDem.setEnabled(false);

        txtTen.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHoDem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHoDem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Ngày sinh:");

        txtNgaySinh.setEnabled(false);
        txtNgaySinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNgaySinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(60, 60, 60)
                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setText("Giới tính:");

        btnGroupGender.add(rdbNam);
        rdbNam.setSelected(true);
        rdbNam.setText("Nam");

        btnGroupGender.add(rdbNu);
        rdbNu.setText("Nữ");

        btnGroupGender.add(rdbKhac);
        rdbKhac.setText("Khác");
        rdbKhac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbKhacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(68, 68, 68)
                .addComponent(rdbNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbNu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbKhac)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(rdbNam)
                    .addComponent(rdbNu)
                    .addComponent(rdbKhac))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jLabel6.setText("Địa chỉ:");

        txtSoNha.setEnabled(false);
        txtSoNha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoNhaActionPerformed(evt);
            }
        });

        cbxTinhThanh.setEnabled(false);
        cbxTinhThanh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTinhThanhItemStateChanged(evt);
            }
        });

        cbxQuanHuyen.setEnabled(false);
        cbxQuanHuyen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxQuanHuyenItemStateChanged(evt);
            }
        });

        cbxPhuongXa.setEnabled(false);
        cbxPhuongXa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPhuongXaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(cbxTinhThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtSoNha))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbxTinhThanh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoNha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Email:");

        txtEmail.setEnabled(false);
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64)
                .addComponent(txtEmail)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText("Số điện thoại:");

        txtSdt.setEnabled(false);
        txtSdt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSdtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setText("Mã CCCD:");

        txtCCCD.setEnabled(false);
        txtCCCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCCCDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel10.setText("Nợ xấu:");

        btnGroupNoXau.add(rdbKhongNo);
        rdbKhongNo.setSelected(true);
        rdbKhongNo.setText("Không");
        rdbKhongNo.setEnabled(false);

        btnGroupNoXau.add(rdbCoNo);
        rdbCoNo.setText("Có");
        rdbCoNo.setEnabled(false);

        btnXoaNo.setText("Xóa nợ");
        btnXoaNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbKhongNo, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdbCoNo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnXoaNo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(rdbKhongNo)
                    .addComponent(rdbCoNo)
                    .addComponent(btnXoaNo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnChonAnh.setText("Cập nhật ảnh đại diện");
        btnChonAnh.setEnabled(false);
        btnChonAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonAnhActionPerformed(evt);
            }
        });

        btnSuaThongTin.setText("Sửa thông tin");
        btnSuaThongTin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaThongTinActionPerformed(evt);
            }
        });

        btnCapNhat.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCapNhat.setText("Cập nhật");
        btnCapNhat.setEnabled(false);
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 0, 51));
        jButton4.setText("Xóa");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChonAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 48, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSuaThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCapNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnChonAnh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSuaThongTin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCapNhat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addGap(18, 18, 18))
        );

        ptbAnh.setImage(new javax.swing.ImageIcon(getClass().getResource("/quanlynganhang/image/man.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbChiTietThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ptbAnh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbChiTietThongTin)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ptbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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

    private void txtNgaySinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNgaySinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNgaySinhActionPerformed

    private void rdbKhacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbKhacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbKhacActionPerformed

    private void txtSoNhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoNhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoNhaActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtSdtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSdtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSdtActionPerformed

    private void txtCCCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCCCDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCCCDActionPerformed

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

    private void btnSuaThongTinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaThongTinActionPerformed
        if (quyenSua == 1) {
            if (btnSuaThongTin.getText().equals("Sửa thông tin")) {
                doiTrangThaiNhap(true);
                btnSuaThongTin.setText("Hủy sửa");

            } else {
                dienThongTin(khachHangDTO);
                doiTrangThaiNhap(false);
                loadAnh(fileName);
                btnChonAnh.setText("Cập nhật ảnh đại diện");
                btnSuaThongTin.setText("Sửa thông tin");
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_btnSuaThongTinActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn cập nhật thông tin?") == JOptionPane.YES_OPTION) {
            try {
                if (capNhatKhachHang()) {
                    MessageBox.showInformationMessage(null, "", "Cập nhật thông tin khách hàng thành công!");
                    btnSuaThongTinActionPerformed(null);
                } else {
                    MessageBox.showErrorMessage(null, "Cập nhật thông tin khách hàng thất bại!");
                }
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } else {
            return;
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnChonAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonAnhActionPerformed
        if (btnChonAnh.getText().equals("Cập nhật ảnh đại diện")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn ảnh");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                newFileName = XuLyAnhBUS.saveImage(selectedFile);
                loadAnh(newFileName);
            } else {
                return;
            }

            btnChonAnh.setText("Xóa ảnh");

        } else {
            if (XuLyAnhBUS.deleteImage(newFileName)) {
                loadAnh(fileName);
                btnChonAnh.setText("Cập nhật ảnh đại diện");
            } else {
                MessageBox.showErrorMessage(null, "Xóa ảnh thất bại!");
            }
        }
    }//GEN-LAST:event_btnChonAnhActionPerformed

    private void btnXoaNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNoActionPerformed
        if (quyenXoa == 1) {
            if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn xóa nợ cho khách hàng này?") == JOptionPane.YES_OPTION) {
                if (khachHangBUS.xoaNoXau(khachHangDTO.getMaKH())) {
                    MessageBox.showInformationMessage(null, "", "Xóa nợ thành công!");
                    khachHangDTO = khachHangBUS.getKhachHangById(khachHangDTO.getMaKH(), 0);
                    dienThongTin(khachHangDTO);
                    doiTrangThaiNhap(false);
                }
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_btnXoaNoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatRobotoFont.install();
        FlatLaf.registerCustomDefaultsSource("quanlynganhang.GUI.themes");
        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
        FlatMacLightLaf.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnChonAnh;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.ButtonGroup btnGroupNoXau;
    private javax.swing.JButton btnSuaThongTin;
    private javax.swing.JButton btnXoaNo;
    private javax.swing.JComboBox<String> cbxPhuongXa;
    private javax.swing.JComboBox<String> cbxQuanHuyen;
    private javax.swing.JComboBox<String> cbxTinhThanh;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
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
    private javax.swing.JLabel lbChiTietThongTin;
    private quanlynganhang.GUI.model.picturebox.PictureBox ptbAnh;
    private javax.swing.JRadioButton rdbCoNo;
    private javax.swing.JRadioButton rdbKhac;
    private javax.swing.JRadioButton rdbKhongNo;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoDem;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSoNha;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
