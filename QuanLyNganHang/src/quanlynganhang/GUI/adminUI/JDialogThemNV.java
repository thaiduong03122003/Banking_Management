package quanlynganhang.GUI.adminUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import quanlynganhang.BUS.DiaChiBUS;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.BUS.XuLyAnhBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.GUI.model.message.MessageBox;

/**
 *
 * @author THAI
 */
public class JDialogThemNV extends javax.swing.JDialog {

    private NhanVienBUS nhanVienBUS;
    private DiaChiBUS diaChiBUS;
    private Integer maTinhThanh, maQuanHuyen, maPhuongXa;
    private String fileName;

    public JDialogThemNV(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        nhanVienBUS = new NhanVienBUS();
        diaChiBUS = new DiaChiBUS();
        fileName = "";
        initComponents();
        initCustomUI();
        loadTinhThanh();
    }

    private void initCustomUI() {
        txtHo.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Họ và tên đệm");
        txtTen.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên");
        txtNgaySinh.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "dd/MM/yyyy");
        txtCCCD.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập đủ 12 số");
        txtEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "*****@gmail.com");
        txtSdt.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập đủ 10-11 số");
        txtSoNha.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập số nhà, đường");
        txtNgayVaoLam.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "dd/MM/yyyy");
        cbxQuanHuyen.setEnabled(false);
        cbxPhuongXa.setEnabled(false);
        txtSoNha.setEnabled(false);
    }

    private void loadTinhThanh() {
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListTinhThanhToMap();

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn tỉnh thành-");

        for (String tenTinhThanh : map.values()) {
            model.addElement(tenTinhThanh);
        }

        cbxTinhThanh.setModel(model);
    }

    private void loadQuanHuyen(int maTinhThanh) {
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListQuanHuyenToMap(maTinhThanh);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn quận huyện-");

        for (String tenQuanHuyen : map.values()) {
            model.addElement(tenQuanHuyen);
        }

        cbxQuanHuyen.setModel(model);
    }

    private void loadPhuongXa(int maQuanHuyen) {
        Map<Integer, String> map = new HashMap<>();
        map = diaChiBUS.convertListPhuongXaToMap(maQuanHuyen);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-Chọn phường xã-");

        for (String tenPhuongXa : map.values()) {
            model.addElement(tenPhuongXa);
        }

        cbxPhuongXa.setModel(model);
    }

    private boolean themNhanVien() throws ParseException {
        StringBuilder error = new StringBuilder();
        FormatDate fDate = new FormatDate();
        error.append("");

        NhanVienDTO nhanVien = new NhanVienDTO();

        if (InputValidation.kiemTraTen(txtHo.getText())) {
            nhanVien.setHoDem(txtHo.getText());
        } else {
            error.append("\nHọ đệm không hợp lệ");
        }

        if (InputValidation.kiemTraTen(txtTen.getText())) {
            nhanVien.setTen(txtTen.getText());
        } else {
            error.append("\nTên không hợp lệ");
        }

        if (InputValidation.kiemTraNgay(txtNgaySinh.getText())) {
            if (InputValidation.kiemTratuoi(txtNgaySinh.getText())) {
                nhanVien.setNgaySinh(fDate.toDate(txtNgaySinh.getText()));
            } else {
                error.append("\nKhông thể thêm nhân viên với độ tuổi này");
            }
        } else {
            error.append("\nNgày sinh không hợp lệ");
        }

        if (InputValidation.kiemTraCCCD(txtCCCD.getText())) {
            nhanVien.setCccd(txtCCCD.getText());
        } else {
            error.append("\nMã căn cước không hợp lệ");
        }

        if (InputValidation.kiemTraEmail(txtEmail.getText())) {
            nhanVien.setEmail(txtEmail.getText());
        } else {
            error.append("\nEmail không hợp lệ");
        }

        if (InputValidation.kiemTraSDT(txtSdt.getText())) {
            nhanVien.setSdt(txtSdt.getText());
        } else {
            error.append("\nSố điện thoại không hợp lệ");
        }

        if (!txtSoNha.getText().isEmpty()) {
            nhanVien.setSoNha(txtSoNha.getText());
        } else {
            error.append("\nVui lòng nhập địa chỉ");
        }

        if (maTinhThanh == null || maQuanHuyen == null || maPhuongXa == null) {
            error.append("\nVui lòng nhập đầy đủ tỉnh, huyện, xã");
        } else {
            nhanVien.setMaPhuongXa(maPhuongXa);
        }

        if (InputValidation.kiemTraNgay(txtNgayVaoLam.getText())) {
            nhanVien.setNgayVaoLam(fDate.toDate(txtNgayVaoLam.getText()));
        } else {
            error.append("\nNgày vào làm không hợp lệ");
        }
        
        if (btnXemAnh.getText().equals("(Chưa có)")) {
            error.append("\nVui lòng chọn ảnh đại diện");
        } else {
            nhanVien.setAnhDaiDien(fileName);
        }

        if (rdbNam.isSelected()) {
            nhanVien.setGioiTinh("Nam");
        } else if (rdbNu.isSelected()) {
            nhanVien.setGioiTinh("Nữ");
        } else {
            nhanVien.setGioiTinh("Khác");
        }

        if (error.isEmpty()) {
            return nhanVienBUS.addNhanVien(nhanVien, 0) != null ? true : false;
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnClose = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPCardNum = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtHo = new javax.swing.JTextField();
        txtTen = new javax.swing.JTextField();
        jPCardNum1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNgaySinh = new javax.swing.JTextField();
        jPCardNum2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        rdbNam = new javax.swing.JRadioButton();
        rdbNu = new javax.swing.JRadioButton();
        rdbKhac = new javax.swing.JRadioButton();
        jPCardNum3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCCCD = new javax.swing.JTextField();
        jPCardNum4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jPCardNum5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSdt = new javax.swing.JTextField();
        jPCardNum6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtSoNha = new javax.swing.JTextField();
        cbxTinhThanh = new javax.swing.JComboBox<>();
        cbxQuanHuyen = new javax.swing.JComboBox<>();
        cbxPhuongXa = new javax.swing.JComboBox<>();
        jPCardNum7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnThemAnh = new javax.swing.JButton();
        btnXemAnh = new javax.swing.JButton();
        jPCardNum8 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtNgayVaoLam = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Thêm nhân viên mới");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        btnClose.setText("Hủy");
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });

        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(274, 274, 274)
                .addComponent(btnClose)
                .addGap(18, 18, 18)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(280, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnClose)
                    .addComponent(btnThem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setIcon(new FlatSVGIcon("quanlynganhang/icon/customer_label.svg")
        );
        jLabel3.setText("Họ tên nhân viên");

        javax.swing.GroupLayout jPCardNumLayout = new javax.swing.GroupLayout(jPCardNum);
        jPCardNum.setLayout(jPCardNumLayout);
        jPCardNumLayout.setHorizontalGroup(
            jPCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNumLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCardNumLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(121, Short.MAX_VALUE))
                    .addGroup(jPCardNumLayout.createSequentialGroup()
                        .addComponent(txtHo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))))
        );
        jPCardNumLayout.setVerticalGroup(
            jPCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNumLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCardNumLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setIcon(new FlatSVGIcon("quanlynganhang/icon/yearofbirth_label.svg")
        );
        jLabel4.setText("Ngày sinh");

        javax.swing.GroupLayout jPCardNum1Layout = new javax.swing.GroupLayout(jPCardNum1);
        jPCardNum1.setLayout(jPCardNum1Layout);
        jPCardNum1Layout.setHorizontalGroup(
            jPCardNum1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNum1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPCardNum1Layout.setVerticalGroup(
            jPCardNum1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum1Layout.createSequentialGroup()
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

        javax.swing.GroupLayout jPCardNum2Layout = new javax.swing.GroupLayout(jPCardNum2);
        jPCardNum2.setLayout(jPCardNum2Layout);
        jPCardNum2Layout.setHorizontalGroup(
            jPCardNum2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNum2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPCardNum2Layout.createSequentialGroup()
                        .addComponent(rdbNam, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbNu, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rdbKhac, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPCardNum2Layout.setVerticalGroup(
            jPCardNum2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCardNum2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbNam)
                    .addComponent(rdbNu)
                    .addComponent(rdbKhac))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setIcon(new FlatSVGIcon("quanlynganhang/icon/id_card_label.svg")
        );
        jLabel6.setText("Mã căn cước công dân");

        javax.swing.GroupLayout jPCardNum3Layout = new javax.swing.GroupLayout(jPCardNum3);
        jPCardNum3.setLayout(jPCardNum3Layout);
        jPCardNum3Layout.setHorizontalGroup(
            jPCardNum3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNum3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPCardNum3Layout.setVerticalGroup(
            jPCardNum3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCCCD, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setIcon(new FlatSVGIcon("quanlynganhang/icon/email_label.svg")
        );
        jLabel7.setText("Email");

        javax.swing.GroupLayout jPCardNum4Layout = new javax.swing.GroupLayout(jPCardNum4);
        jPCardNum4.setLayout(jPCardNum4Layout);
        jPCardNum4Layout.setHorizontalGroup(
            jPCardNum4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNum4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPCardNum4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtEmail))
                .addContainerGap())
        );
        jPCardNum4Layout.setVerticalGroup(
            jPCardNum4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setIcon(new FlatSVGIcon("quanlynganhang/icon/phone_label.svg")
        );
        jLabel8.setText("Số điện thoại");

        javax.swing.GroupLayout jPCardNum5Layout = new javax.swing.GroupLayout(jPCardNum5);
        jPCardNum5.setLayout(jPCardNum5Layout);
        jPCardNum5Layout.setHorizontalGroup(
            jPCardNum5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPCardNum5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPCardNum5Layout.setVerticalGroup(
            jPCardNum5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setIcon(new FlatSVGIcon("quanlynganhang/icon/address_label.svg")
        );
        jLabel9.setText("Địa chỉ");

        cbxTinhThanh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Chọn tỉnh thành-" }));
        cbxTinhThanh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTinhThanhItemStateChanged(evt);
            }
        });
        cbxTinhThanh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTinhThanhActionPerformed(evt);
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

        javax.swing.GroupLayout jPCardNum6Layout = new javax.swing.GroupLayout(jPCardNum6);
        jPCardNum6.setLayout(jPCardNum6Layout);
        jPCardNum6Layout.setHorizontalGroup(
            jPCardNum6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNum6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPCardNum6Layout.createSequentialGroup()
                        .addComponent(cbxTinhThanh, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxQuanHuyen, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxPhuongXa, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtSoNha))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPCardNum6Layout.setVerticalGroup(
            jPCardNum6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPCardNum6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxPhuongXa, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(cbxQuanHuyen, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(cbxTinhThanh))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtSoNha, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new FlatSVGIcon("quanlynganhang/icon/image_btn.svg"));
        jLabel10.setText("Ảnh đại diện");

        btnThemAnh.setText("Lấy ảnh");
        btnThemAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemAnhActionPerformed(evt);
            }
        });

        btnXemAnh.setText("(Chưa có)");
        btnXemAnh.setBorderPainted(false);
        btnXemAnh.setContentAreaFilled(false);
        btnXemAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXemAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXemAnhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPCardNum7Layout = new javax.swing.GroupLayout(jPCardNum7);
        jPCardNum7.setLayout(jPCardNum7Layout);
        jPCardNum7Layout.setHorizontalGroup(
            jPCardNum7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNum7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCardNum7Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPCardNum7Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnThemAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPCardNum7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXemAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPCardNum7Layout.setVerticalGroup(
            jPCardNum7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThemAnh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXemAnh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setIcon(new FlatSVGIcon("quanlynganhang/icon/id_card_label.svg")
        );
        jLabel11.setText("Ngày vào làm");

        javax.swing.GroupLayout jPCardNum8Layout = new javax.swing.GroupLayout(jPCardNum8);
        jPCardNum8.setLayout(jPCardNum8Layout);
        jPCardNum8Layout.setHorizontalGroup(
            jPCardNum8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPCardNum8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPCardNum8Layout.setVerticalGroup(
            jPCardNum8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPCardNum8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNgayVaoLam, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPCardNum3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPCardNum4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPCardNum5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPCardNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPCardNum1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPCardNum2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPCardNum6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPCardNum7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jPCardNum8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPCardNum1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPCardNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPCardNum2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPCardNum3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPCardNum4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPCardNum5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPCardNum6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPCardNum7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPCardNum8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

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

    private void btnXemAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXemAnhActionPerformed
        String tenAnh = btnXemAnh.getText();
        if (tenAnh.equals("null") || tenAnh.equals("(Chưa có)")) {
            return;
        } else {
            JDialogXemAnh xemAnh = new JDialogXemAnh(null, rootPaneCheckingEnabled, tenAnh);
            xemAnh.setDefaultCloseOperation(JDialogXemAnh.DISPOSE_ON_CLOSE);
            xemAnh.setVisible(true);
        }
    }//GEN-LAST:event_btnXemAnhActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        if (fileName.equals("")) {
            this.dispose();
        } else {
            if (XuLyAnhBUS.deleteImage(fileName)) {
                this.dispose();
            } else {
                System.out.println("Delete image file error!");
            }
        }
        
    }//GEN-LAST:event_btnCloseActionPerformed

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
                loadQuanHuyen(maTinhThanh.intValue());
                cbxQuanHuyen.setEnabled(true);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của tỉnh thành thất bại!");
            }
        }
    }//GEN-LAST:event_cbxTinhThanhItemStateChanged

    private void cbxTinhThanhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTinhThanhActionPerformed

    }//GEN-LAST:event_cbxTinhThanhActionPerformed

    private void cbxQuanHuyenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxQuanHuyenItemStateChanged
        String tenQuanHuyen = (String) cbxQuanHuyen.getSelectedItem();
        if (tenQuanHuyen.equals("-Chọn quận huyện-")) {
            cbxPhuongXa.setSelectedIndex(0);

            cbxPhuongXa.setEnabled(false);
            txtSoNha.setEnabled(false);
            maQuanHuyen = null;
        } else {
            maQuanHuyen = diaChiBUS.getIdFromTenQuanHuyen(tenQuanHuyen, maTinhThanh);

            if (maQuanHuyen != null) {
                loadPhuongXa(maQuanHuyen.intValue());
                cbxPhuongXa.setEnabled(true);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của quận huyện thất bại!");
            }
        }
    }//GEN-LAST:event_cbxQuanHuyenItemStateChanged

    private void cbxPhuongXaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPhuongXaItemStateChanged
        String tenPhuongXa = (String) cbxPhuongXa.getSelectedItem();
        if (tenPhuongXa.equals("-Chọn phường xã-")) {
            txtSoNha.setEnabled(false);
            maPhuongXa = null;
        } else {
            maPhuongXa = diaChiBUS.getIdFromTenPhuongXa(tenPhuongXa, maQuanHuyen);

            if (maPhuongXa != null) {
                System.out.println("Id xã: " + maPhuongXa + " - Id huyện: " + maQuanHuyen + " - Id tỉnh: " + maTinhThanh);
                txtSoNha.setEnabled(true);
            } else {
                MessageBox.showErrorMessage(null, "Lấy id của phường xã thất bại!");
            }
        }
    }//GEN-LAST:event_cbxPhuongXaItemStateChanged

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        try {
            if (themNhanVien()) {
                MessageBox.showInformationMessage(null, "", "Thêm nhân viên mới thành công!");
                btnCloseActionPerformed(null);
            } else {
                MessageBox.showErrorMessage(null, "Thêm nhân viên thất bại!");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnThemActionPerformed

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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialogThemNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialogThemNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialogThemNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialogThemNV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialogThemNV dialog = new JDialogThemNV(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.ButtonGroup btnGroupGender;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemAnh;
    private javax.swing.JButton btnXemAnh;
    private javax.swing.JComboBox<String> cbxPhuongXa;
    private javax.swing.JComboBox<String> cbxQuanHuyen;
    private javax.swing.JComboBox<String> cbxTinhThanh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPCardNum;
    private javax.swing.JPanel jPCardNum1;
    private javax.swing.JPanel jPCardNum2;
    private javax.swing.JPanel jPCardNum3;
    private javax.swing.JPanel jPCardNum4;
    private javax.swing.JPanel jPCardNum5;
    private javax.swing.JPanel jPCardNum6;
    private javax.swing.JPanel jPCardNum7;
    private javax.swing.JPanel jPCardNum8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton rdbKhac;
    private javax.swing.JRadioButton rdbNam;
    private javax.swing.JRadioButton rdbNu;
    private javax.swing.JTextField txtCCCD;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHo;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtNgayVaoLam;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtSoNha;
    private javax.swing.JTextField txtTen;
    // End of variables declaration//GEN-END:variables
}
