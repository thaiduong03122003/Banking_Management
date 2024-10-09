package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import quanlynganhang.BUS.MaHoaMatKhauBUS;
import quanlynganhang.BUS.SendMailBUS;
import quanlynganhang.BUS.TaiKhoanNVBUS;
import quanlynganhang.BUS.validation.InputValidation;
import quanlynganhang.DTO.KhoiPhucMatKhauDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JFrameQuenMK extends javax.swing.JFrame {

    private int currentPanel, countdown, countdownTimer;
    private CardLayout quenMKCLayout;
    private Timer timer;
    private String tenTaiKhoan;
    private TaiKhoanNVBUS taiKhoanNVBUS;
    private SendMailBUS sendMailBUS;
    private int code, maTKNV;
    private List<Integer> expiredCode;

    public JFrameQuenMK() {
        initComponents();
        customUI();

        taiKhoanNVBUS = new TaiKhoanNVBUS();
        sendMailBUS = new SendMailBUS();
        currentPanel = 1;
        countdownTimer = 60;
        expiredCode = new ArrayList<>();
        quenMKCLayout = (CardLayout) (jPanelCardLayout.getLayout());
        loadingAnimation(false);
        quenMKCLayout.show(jPanelCardLayout, "jPanelNhapTK");
        
    }

    private void customUI() {
        txtTenDangNhap.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("quanlynganhang/icon/account_name_label.svg"));
        txtTenDangNhap.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên đăng nhập");
        txtMaXacNhan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng nhập mã xác nhận");
        pwdNewPass1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng nhập mật khẩu");
        pwdNewPass2.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Vui lòng nhập lại mật khẩu");
        pwdNewPass1.putClientProperty(FlatClientProperties.STYLE, ""
            + "showRevealButton:true;");
        pwdNewPass2.putClientProperty(FlatClientProperties.STYLE, ""
            + "showRevealButton:true;");

        btnNext.setEnabled(false);
    }

    private boolean isUsernameExist(String username) {
        KhoiPhucMatKhauDTO khoiPhuc = taiKhoanNVBUS.getEmailByUsername(username);

        if (khoiPhuc == null) {
            MessageBox.showErrorMessage(null, "Tài khoản không tồn tại!");
            return false;
        }

        if (!InputValidation.kiemTraEmail(khoiPhuc.getEmail())) {
            MessageBox.showErrorMessage(null, "Tài khoản này chưa thiết lập email khôi phục!");
        }

        if (khoiPhuc.getMaTrangThai() != 6 && khoiPhuc.getMaTrangThai() != 7) {
            MessageBox.showErrorMessage(null, "không thể lấy lại mật khẩu của tài khoản này!");
            return false;
        }

        return sendCodeToEmail(khoiPhuc);
    }

    private void startCountdown() {
    countdown = countdownTimer;
    
    if (timer != null && timer.isRunning()) {
        timer.stop();
    }

    btnGuiMa.setEnabled(false);
    btnGuiMa.setText(countdown + "s");
    
    timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            countdown--;
            btnGuiMa.setText(countdown + "s");

            if (countdown < 0) {
                timer.stop();
                expiredCode.add(code);
                code = 0;
                btnGuiMa.setText("Gửi lại mã");
                btnGuiMa.setEnabled(true);
            }
        }
    });
    timer.start();
}
    
    private void loadingAnimation(boolean isActive) {
        spLoading.setIndeterminate(isActive);
        spLoading.setVisible(isActive);
    }

    private boolean sendCodeToEmail(KhoiPhucMatKhauDTO khoiPhuc) {
        code = sendMailBUS.taoMaXacNhan();
        checkCode(code);

        if (!sendMailBUS.guiMaXacNhan(khoiPhuc.getEmail(), code, "khôi phục mật khẩu")) {
            MessageBox.showErrorMessage(null, "Chức năng đã bị lỗi, gửi mã xác nhận thất bại!");
            return false;
        }
        maTKNV = khoiPhuc.getMaTaiKhoan();
        jLabelEmail.setText(InputValidation.anEmail(khoiPhuc.getEmail()));
        return true;
    }

    private void checkCode(int code) {
        if (expiredCode != null && !expiredCode.isEmpty() && expiredCode.contains(code)) {
            expiredCode.remove(code);
        }
    }

    private void onTextChanged() {
        if (tenTaiKhoan != null) {
            btnBuoc2_1.setEnabled(!txtTenDangNhap.getText().trim().isEmpty() && tenTaiKhoan.equals(txtTenDangNhap.getText()));
        }

        btnNext.setEnabled(!txtTenDangNhap.getText().trim().isEmpty());
    }

    private void onCodeTextChanged() {
        btnNext.setEnabled(txtMaXacNhan.getText().trim().length() == 6);
    }

    private void onPassTextChanged() {
        if (pwdNewPass1.getPassword().length == 0 || pwdNewPass2.getPassword().length == 0) {
            btnNext.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
        }
    }

    private void handleCardNhapTen() {
        btnNext.setEnabled(false);
        btnNext.setText("Đang xử lý...");
        loadingAnimation(true);
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                if (txtTenDangNhap.getText().trim().contains(" ")) {
                    MessageBox.showErrorMessage(null, "Tên đang nhập không hợp lệ!");
                    return false;
                } else {
                    tenTaiKhoan = txtTenDangNhap.getText().trim();

                    return isUsernameExist(tenTaiKhoan);
                }
            }

            @Override
            protected void done() {
                Boolean success;
                try {
                    success = get();
                } catch (Exception ex) {
                    success = null;
                    ex.printStackTrace();
                }
                if (success) {
                    currentPanel = 2;
                    quenMKCLayout.show(jPanelCardLayout, "jPanelXacThuc");

                    startCountdown();
                }
                loadingAnimation(false);
                btnNext.setEnabled(true);
                btnNext.setText("Tiếp");
            }
        };
        worker.execute();
    }

    private void handleCardXacThuc() {
        btnNext.setEnabled(false);
        if (txtMaXacNhan.getText().trim().isEmpty()) {
            MessageBox.showErrorMessage(null, "Vui lòng nhập mã xác nhận!");
            return;
        }
        if (expiredCode.contains(Integer.parseInt(txtMaXacNhan.getText().trim()))) {
            MessageBox.showErrorMessage(null, "Mã xác nhận này đã hết hạn!");
        } else {
            if (!InputValidation.kiemTraMaXacNhan(txtMaXacNhan.getText().trim()) || !txtMaXacNhan.getText().trim().equals(String.valueOf(code))) {
                MessageBox.showErrorMessage(null, "Mã xác nhận không đúng");
            } else {
                currentPanel = 3;
                quenMKCLayout.show(jPanelCardLayout, "jPanelDoiMK");
                btnNext.setText("Hoàn tất");
                btnNext.setEnabled(false);
            }
        }
    }

    private void handleCardDoiMatKhau() {
        btnNext.setEnabled(false);
        btnNext.setText("Đang xử lý...");
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                String password1 = String.valueOf(pwdNewPass1.getPassword());
                String password2 = String.valueOf(pwdNewPass2.getPassword());
                TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                taiKhoanNV.setMaTKNV(maTKNV);

                if (!password1.equals(password2)) {
                    MessageBox.showErrorMessage(null, "Mật khẩu không trùng khớp!");
                    return false;
                }

                if (password1.contains(" ") || password2.contains(" ")) {
                    MessageBox.showErrorMessage(null, "Không được chứa kí tự khoảng trắng!");
                    return false;
                }
                if (InputValidation.kiemTraMatKhau(password1)) {
                    taiKhoanNV.setMatKhau(MaHoaMatKhauBUS.encryptPassword(password1));
                    return taiKhoanNVBUS.doiMatKhau(taiKhoanNV);
                } else {
                    MessageBox.showErrorMessage(null, "Mật khẩu phải có độ dài từ 6-12 ký tự, chứa cả ký tự hoa, thường và ký tự đặc biệt");
                    return false;
                }
            }

            @Override
            protected void done() {
                Boolean success;
                try {
                    success = get();
                } catch (Exception ex) {
                    success = null;
                    ex.printStackTrace();
                }
                if (success) {
                    currentPanel = 1;
                    MessageBox.showInformationMessage(null, "", "Đổi mật khẩu thành công, bạn sẽ quay lại trang đăng nhập");
                    btnHuyMouseClicked(null);
                }
                btnNext.setEnabled(true);
                btnNext.setText("Hoàn tất");
            }
        };
        worker.execute();
    }

    private void confirmExit(boolean isExitNow) {
        if (isExitNow || MessageBox.showConfirmMessage(null, "Bạn có thể sẽ phải thực hiện lại việc khôi phục mật khẩu từ đầu, vẫn thoát chứ?") == JOptionPane.YES_OPTION) {
            this.dispose();

            JFrameDangNhap dangNhap = new JFrameDangNhap();
            FlatSVGIcon favicon = new FlatSVGIcon("quanlynganhang/icon/favicon.svg");
            dangNhap.setIconImage(favicon.getImage());
            dangNhap.setTitle("Đăng nhập");
            dangNhap.setVisible(true);
            dangNhap.setResizable(false);
            dangNhap.setDefaultCloseOperation(JFrameDangNhap.DISPOSE_ON_CLOSE);
            dangNhap.setVisible(true);
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

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnNext = new javax.swing.JButton();
        btnHuy = new javax.swing.JLabel();
        jPanelCardLayout = new javax.swing.JPanel();
        jPanelNhapTK = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnBuoc2_1 = new javax.swing.JButton();
        lineComponent1 = new quanlynganhang.GUI.model.shape.LineComponent();
        lineComponent2 = new quanlynganhang.GUI.model.shape.LineComponent();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        spLoading = new quanlynganhang.GUI.model.spinner.SpinnerProgress();
        jPanelXacThuc = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        btnBuoc1_2 = new javax.swing.JButton();
        btnBuoc3 = new javax.swing.JButton();
        btnBuoc2_2 = new javax.swing.JButton();
        lineComponent3 = new quanlynganhang.GUI.model.shape.LineComponent();
        lineComponent4 = new quanlynganhang.GUI.model.shape.LineComponent();
        jPanel16 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabelEmail = new javax.swing.JLabel();
        txtMaXacNhan = new javax.swing.JTextField();
        btnGuiMa = new javax.swing.JButton();
        jPanelDoiMK = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        lineComponent5 = new quanlynganhang.GUI.model.shape.LineComponent();
        lineComponent6 = new quanlynganhang.GUI.model.shape.LineComponent();
        jPanel21 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        pwdNewPass1 = new javax.swing.JPasswordField();
        pwdNewPass2 = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("QUÊN MẬT KHẨU");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        btnNext.setText("Tiếp");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnHuy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHuy.setForeground(new java.awt.Color(255, 0, 51));
        btnHuy.setText("Hủy");
        btnHuy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuyMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHuy)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 97, Short.MAX_VALUE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(124, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnHuy)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.add(jPanel10, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanelCardLayout.setLayout(new java.awt.CardLayout());

        jPanelNhapTK.setLayout(new java.awt.BorderLayout());

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());

        jButton4.setBackground(new java.awt.Color(51, 153, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("1");

        jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton5.setText("3");
        jButton5.setEnabled(false);

        btnBuoc2_1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuoc2_1.setText("2");
        btnBuoc2_1.setEnabled(false);
        btnBuoc2_1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuoc2_1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lineComponent1Layout = new javax.swing.GroupLayout(lineComponent1);
        lineComponent1.setLayout(lineComponent1Layout);
        lineComponent1Layout.setHorizontalGroup(
            lineComponent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        lineComponent1Layout.setVerticalGroup(
            lineComponent1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout lineComponent2Layout = new javax.swing.GroupLayout(lineComponent2);
        lineComponent2.setLayout(lineComponent2Layout);
        lineComponent2Layout.setHorizontalGroup(
            lineComponent2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        lineComponent2Layout.setVerticalGroup(
            lineComponent2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineComponent1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuoc2_1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineComponent2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5)
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lineComponent2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lineComponent1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnBuoc2_1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("Nhập tài khoản");

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Xác minh bảo mật");

        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Thiết lập mật khẩu");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addGap(26, 26, 26)
                .addComponent(jLabel4)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Vui lòng nhập tài khoản cần tìm lại mật khẩu");

        txtTenDangNhap.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtTenDangNhapInputMethodTextChanged(evt);
            }
        });
        txtTenDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDangNhapActionPerformed(evt);
            }
        });
        txtTenDangNhap.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextChanged();
            }
        });

        jPanel25.setLayout(new java.awt.BorderLayout());

        spLoading.setValue(50);
        spLoading.setIndeterminate(true);
        jPanel25.add(spLoading, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTenDangNhap)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel5.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanelNhapTK.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanelCardLayout.add(jPanelNhapTK, "jPanelNhapTK");

        jPanelXacThuc.setLayout(new java.awt.BorderLayout());

        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel14.setLayout(new java.awt.BorderLayout());

        btnBuoc1_2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuoc1_2.setForeground(new java.awt.Color(153, 153, 153));
        btnBuoc1_2.setText("1");
        btnBuoc1_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuoc1_2ActionPerformed(evt);
            }
        });

        btnBuoc3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuoc3.setText("3");
        btnBuoc3.setEnabled(false);

        btnBuoc2_2.setBackground(new java.awt.Color(0, 153, 255));
        btnBuoc2_2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnBuoc2_2.setForeground(new java.awt.Color(255, 255, 255));
        btnBuoc2_2.setText("2");

        javax.swing.GroupLayout lineComponent3Layout = new javax.swing.GroupLayout(lineComponent3);
        lineComponent3.setLayout(lineComponent3Layout);
        lineComponent3Layout.setHorizontalGroup(
            lineComponent3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        lineComponent3Layout.setVerticalGroup(
            lineComponent3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout lineComponent4Layout = new javax.swing.GroupLayout(lineComponent4);
        lineComponent4.setLayout(lineComponent4Layout);
        lineComponent4Layout.setHorizontalGroup(
            lineComponent4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        lineComponent4Layout.setVerticalGroup(
            lineComponent4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(btnBuoc1_2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineComponent3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuoc2_2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineComponent4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuoc3)
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lineComponent4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lineComponent3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuoc3)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnBuoc1_2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnBuoc2_2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel15, java.awt.BorderLayout.PAGE_START);

        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Nhập tài khoản");

        jLabel7.setForeground(new java.awt.Color(0, 153, 255));
        jLabel7.setText("Xác minh bảo mật");

        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Thiết lập mật khẩu");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(29, 29, 29)
                .addComponent(jLabel7)
                .addGap(26, 26, 26)
                .addComponent(jLabel8)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel13, java.awt.BorderLayout.PAGE_START);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new FlatSVGIcon("quanlynganhang/icon/mail_khoi_phuc.svg")
        );

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Chúng tôi vừa gửi cho bạn mã xác nhận email để xác minh danh tính");

        jLabelEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabelEmail.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelEmail.setText("example@gmail.com");

        btnGuiMa.setText("Gửi lại mã");
        btnGuiMa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiMaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                    .addComponent(jLabelEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtMaXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuiMa, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnGuiMa, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(txtMaXacNhan))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        txtMaXacNhan.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onCodeTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onCodeTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onCodeTextChanged();
            }

        });

        jPanel12.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPanelXacThuc.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanelCardLayout.add(jPanelXacThuc, "jPanelXacThuc");

        jPanelDoiMK.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel19.setLayout(new java.awt.BorderLayout());

        jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("1");
        jButton10.setEnabled(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(51, 153, 255));
        jButton11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton11.setForeground(new java.awt.Color(255, 255, 255));
        jButton11.setText("3");

        jButton12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton12.setText("2");
        jButton12.setEnabled(false);

        javax.swing.GroupLayout lineComponent5Layout = new javax.swing.GroupLayout(lineComponent5);
        lineComponent5.setLayout(lineComponent5Layout);
        lineComponent5Layout.setHorizontalGroup(
            lineComponent5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        lineComponent5Layout.setVerticalGroup(
            lineComponent5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout lineComponent6Layout = new javax.swing.GroupLayout(lineComponent6);
        lineComponent6.setLayout(lineComponent6Layout);
        lineComponent6Layout.setHorizontalGroup(
            lineComponent6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 83, Short.MAX_VALUE)
        );
        lineComponent6Layout.setVerticalGroup(
            lineComponent6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(105, Short.MAX_VALUE)
                .addComponent(jButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineComponent5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lineComponent6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton11)
                .addContainerGap(105, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lineComponent6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lineComponent5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton11)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton10, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton12)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel20, java.awt.BorderLayout.PAGE_START);

        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("Nhập tài khoản");

        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Xác minh bảo mật");

        jLabel14.setForeground(new java.awt.Color(51, 153, 255));
        jLabel14.setText("Thiết lập mật khẩu");

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(29, 29, 29)
                .addComponent(jLabel13)
                .addGap(26, 26, 26)
                .addComponent(jLabel14)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel21, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel18, java.awt.BorderLayout.PAGE_START);

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Vui lòng thiết lập mật khẩu tương đối mạnh");

        jLabel15.setIcon(new FlatSVGIcon("quanlynganhang/icon/change_password.svg")
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pwdNewPass2)
                    .addComponent(pwdNewPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pwdNewPass1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pwdNewPass2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        pwdNewPass1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onPassTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onPassTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onPassTextChanged();
            }

        });
        pwdNewPass2.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onPassTextChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onPassTextChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onPassTextChanged();
            }

        });

        jPanel3.add(jPanel22, java.awt.BorderLayout.CENTER);

        jPanelDoiMK.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanelCardLayout.add(jPanelDoiMK, "jPanelDoiMK");

        getContentPane().add(jPanelCardLayout, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyMouseClicked
        confirmExit(currentPanel == 1);
    }//GEN-LAST:event_btnHuyMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        try {
            switch (currentPanel) {
                case 1:
                    handleCardNhapTen();
                    break;
                case 2:
                    handleCardXacThuc();
                    break;
                case 3:
                    handleCardDoiMatKhau();
                    break;
                default:
                    currentPanel = 1;
                    quenMKCLayout.show(jPanelCardLayout, "jPanelNhapTK");
                    btnNext.setText("Tiếp");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnGuiMaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiMaActionPerformed
        startCountdown();
    }//GEN-LAST:event_btnGuiMaActionPerformed

    private void btnBuoc1_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuoc1_2ActionPerformed
        currentPanel = 1;
        quenMKCLayout.show(jPanelCardLayout, "jPanelNhapTK");
        btnBuoc2_1.setEnabled(true);
        btnNext.setEnabled(true);
    }//GEN-LAST:event_btnBuoc1_2ActionPerformed

    private void txtTenDangNhapInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtTenDangNhapInputMethodTextChanged

    }//GEN-LAST:event_txtTenDangNhapInputMethodTextChanged

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void btnBuoc2_1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuoc2_1ActionPerformed
        currentPanel = 2;
        quenMKCLayout.show(jPanelCardLayout, "jPanelXacThuc");
        btnNext.setEnabled(false);
    }//GEN-LAST:event_btnBuoc2_1ActionPerformed

    private void txtTenDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDangNhapActionPerformed

    }//GEN-LAST:event_txtTenDangNhapActionPerformed

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
            java.util.logging.Logger.getLogger(JFrameQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameQuenMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameQuenMK().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuoc1_2;
    private javax.swing.JButton btnBuoc2_1;
    private javax.swing.JButton btnBuoc2_2;
    private javax.swing.JButton btnBuoc3;
    private javax.swing.JButton btnGuiMa;
    private javax.swing.JLabel btnHuy;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelCardLayout;
    private javax.swing.JPanel jPanelDoiMK;
    private javax.swing.JPanel jPanelNhapTK;
    private javax.swing.JPanel jPanelXacThuc;
    private quanlynganhang.GUI.model.shape.LineComponent lineComponent1;
    private quanlynganhang.GUI.model.shape.LineComponent lineComponent2;
    private quanlynganhang.GUI.model.shape.LineComponent lineComponent3;
    private quanlynganhang.GUI.model.shape.LineComponent lineComponent4;
    private quanlynganhang.GUI.model.shape.LineComponent lineComponent5;
    private quanlynganhang.GUI.model.shape.LineComponent lineComponent6;
    private javax.swing.JPasswordField pwdNewPass1;
    private javax.swing.JPasswordField pwdNewPass2;
    private quanlynganhang.GUI.model.spinner.SpinnerProgress spLoading;
    private javax.swing.JTextField txtMaXacNhan;
    private javax.swing.JTextField txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
