/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package quanlynganhang.GUI.model.headerbar;

import javax.swing.JPanel;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import quanlynganhang.BUS.ChiaQuyenBUS;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.Application;
import quanlynganhang.GUI.FormPopupProfile;
import quanlynganhang.GUI.JFrameThongBao;
import quanlynganhang.GUI.adminUI.ApplicationAdmin;
import quanlynganhang.GUI.model.glasspanepopup.DefaultOption;
import quanlynganhang.GUI.model.glasspanepopup.GlassPanePopup;
import quanlynganhang.GUI.model.menubar.PopupSubMenu;
import quanlynganhang.GUI.model.message.MessageBox;

public class HeaderBar extends javax.swing.JPanel {

    private Application app;
    private ApplicationAdmin appAdmin;
    private TaiKhoanNVDTO taiKhoanNV;
    private JPopupMenu suggestionPopup; // Popup lưu trữ ngoài phương thức
    private JList<String> suggestionList; // JList lưu trữ ngoài phương thức
    private ArrayList<String> suggestions; // Danh sách gợi ý tĩnh hoặc động
    private int maDangNhap, quyenSua, quyenXoa;
    private boolean isAdmin;

    public HeaderBar(boolean isAdmin, int maDangNhap, TaiKhoanNVDTO taiKhoanNV, Application app, ApplicationAdmin appAdmin, int quyenSua, int quyenXoa) {
        this.isAdmin = isAdmin;
        this.app = app;
        this.maDangNhap = maDangNhap;
        this.appAdmin = appAdmin;
        this.taiKhoanNV = taiKhoanNV;
        this.quyenSua = quyenSua;
        this.quyenXoa = quyenXoa;
        initComponents();

        putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jPHeaderBar.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jpWelcome.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jpSearch.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");
        jpProfile.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$HeaderBar.background;");

        txtSearch.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tìm kiếm trong menu");
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_ICON, new FlatSVGIcon("quanlynganhang/icon/search_btn.svg"));
        txtSearch.setVisible(false);
        // Khởi tạo danh sách gợi ý và popup
        suggestions = new ArrayList<>();
        suggestions.add("Trang chủ");
        suggestions.add("Thống kê");
        suggestions.add("Danh sách khách hàng");
        suggestions.add("Danh sách thẻ ngân hàng");
        suggestions.add("Danh sách tài khoản KH");
        suggestions.add("Danh sách giao dịch");
        suggestions.add("Mở tài khoản ngân hàng");
        suggestions.add("Mở Thẻ ghi nợ");
        suggestions.add("Mở thẻ tín dụng");
        suggestions.add("Nạp tiền vào tài khoản");
        suggestions.add("Rút tiền khỏi tài khoản");
        suggestions.add("Chuyển tiền cùng ngân hàng");
        suggestions.add("Chuyển tiền liên ngân hàng");
        suggestions.add("Cho vay");
        suggestions.add("Trả khoản vay");
        suggestions.add("Danh sách nhân viên");
        suggestions.add("Danh sách tài khoản NV");
        suggestions.add("Thêm chức vụ");
        suggestions.add("Phân quyền");

        suggestionPopup = new JPopupMenu();
        suggestionList = new JList<>();
        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(suggestionList);
        scrollPane.setPreferredSize(new Dimension(200, 100));
        suggestionPopup.add(scrollPane);

        setEventInputSearch();
    }

    public void setEventInputSearch() {
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (txtSearch.getText().length() == 0) {
                        return;
                    }
                    searchByText(txtSearch.getText().trim());
                }
            }
        });
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onTextChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onTextChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onTextChange();
            }

            public void onTextChange() {
                String value = txtSearch.getText().trim();

                // Nếu không có văn bản nào trong txtSearch, ẩn popup
                if (value.isEmpty()) {
                    suggestionPopup.setVisible(false);
                    return;
                }

                // Lọc các gợi ý dựa trên văn bản nhập vào
                ArrayList<String> filteredSuggestions = new ArrayList<>();
                for (String suggestion : suggestions) {
                    if (suggestion.toLowerCase().contains(value.toLowerCase())) {
                        filteredSuggestions.add(suggestion);
                    }
                }

                // Nếu không có gợi ý phù hợp, ẩn popup
                if (filteredSuggestions.isEmpty()) {
                    suggestionPopup.setVisible(false);
                    return;
                }

                // Cập nhật JList với gợi ý mới
                suggestionList.setListData(filteredSuggestions.toArray(new String[0]));

                // Hiển thị popup ngay dưới JTextField
                suggestionPopup.setLocation(txtSearch.getLocationOnScreen().x, txtSearch.getLocationOnScreen().y + txtSearch.getHeight());
                suggestionPopup.setVisible(true);

                // Xử lý sự kiện khi người dùng chọn gợi ý
                suggestionList.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) {
                            String selectedValue = suggestionList.getSelectedValue();
                            if (selectedValue != null) {
                                txtSearch.setText(selectedValue);
                                suggestionPopup.setVisible(false);
                                searchByText(txtSearch.getText().trim());
                            }

                        }
                    }
                });
            }
        });

        // Đảm bảo khi người dùng click vào một khu vực khác, popup sẽ ẩn đi
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                suggestionPopup.setVisible(false);
            }
        });
    }

    private void searchByText(String text) {

        String temp = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

        if (!isAdmin) {
            switch (pattern.matcher(temp).replaceAll("").toLowerCase()) {

                case "trang chu":
                    Application.instanceMenu.setSelectedMenu(0, 1);
                    break;
                case "thong ke":
                    Application.instanceMenu.setSelectedMenu(1, 1);
                    break;
                case "danh sach khach hang":
                    Application.instanceMenu.setSelectedMenu(2, 1);
                    break;
                case "danh sach nhan vien":
                    ChiaQuyenBUS.showError();
                    break;
                case "danh sach tai khoan nv":
                    ChiaQuyenBUS.showError();
                    break;
                case "them chuc vu":
                    ChiaQuyenBUS.showError();
                    break;
                case "phan quyen":
                    ChiaQuyenBUS.showError();
                    break;
                case "danh sach the ngan hang":
                    Application.instanceMenu.setSelectedMenu(4, 1);
                    break;
                case "danh sach tai khoan kh":
                    Application.instanceMenu.setSelectedMenu(3, 1);
                    break;
                case "danh sach giao dich":
                    Application.instanceMenu.setSelectedMenu(5, 1);
                    break;
                case "mo tai khoan ngan hang":
                    Application.instanceMenu.setSelectedMenu(6, 1);
                    break;
                case "mo the ghi no":
                    Application.instanceMenu.setSelectedMenu(7, 1);
                    break;
                case "mo the tin dung":
                    Application.instanceMenu.setSelectedMenu(7, 2);
                    break;
                case "nap tien vao tai khoan":
                    Application.instanceMenu.setSelectedMenu(8, 1);
                    break;
                case "rut tien khoi tai khoan":
                    Application.instanceMenu.setSelectedMenu(8, 2);
                    break;
                case "chuyen tien cung ngan hang":
                    Application.instanceMenu.setSelectedMenu(8, 3);
                    break;
                case "chuyen tien lien ngan hang":
                    Application.instanceMenu.setSelectedMenu(8, 4);
                    break;
                case "cho vay":
                    Application.instanceMenu.setSelectedMenu(10, 1);
                    break;
                case "tra khoan vay":
                    Application.instanceMenu.setSelectedMenu(10, 2);
                    break;
                case "gui tiet kiem":
                    Application.instanceMenu.setSelectedMenu(9, 1);
                    break;
                default:
                    JOptionPane.showMessageDialog(
                        null,
                        "<html>" + "Không tìm thấy kết quả nào cho: " + text + "</html>",
                        "Kết quả tìm kiếm",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    break;
            }

        } else {

            switch (pattern.matcher(temp).replaceAll("").toLowerCase()) {

                case "trang chu":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(0, 1);
                    break;
                case "thong ke":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(1, 1);
                    break;
                case "danh sach khach hang":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(2, 1);
                    break;
                case "danh sach nhan vien":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(3, 1);
                    break;
                case "danh sach tai khoan nv":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(5, 1);
                    break;
                case "them chuc vu":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(9, 1);
                    break;
                case "phan quyen":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(8, 1);
                    break;
                case "danh sach the ngan hang":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(6, 1);
                    break;
                case "danh sach tai khoan kh":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(4, 1);
                    break;
                case "danh sach giao dich":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(7, 1);
                    break;
                case "mo tai khoan ngan hang":
                    ApplicationAdmin.instanceMenu.setSelectedMenu(6, 1);
                    break;
                case "mo the ghi no":
                    ChiaQuyenBUS.showError();
                    break;
                case "mo the tin dung":
                    ChiaQuyenBUS.showError();
                    break;
                case "nap tien vao tai khoan":
                    ChiaQuyenBUS.showError();
                    break;
                case "rut tien khoi tai khoan":
                    ChiaQuyenBUS.showError();
                    break;
                case "chuyen tien cung ngan hang":
                    ChiaQuyenBUS.showError();
                    break;
                case "chuyen tien lien ngan hang":
                    ChiaQuyenBUS.showError();
                    break;
                case "cho vay":
                    ChiaQuyenBUS.showError();
                    break;
                case "tra khoan vay":
                    ChiaQuyenBUS.showError();
                    break;
                case "gui tiet kiem":
                    ChiaQuyenBUS.showError();
                    break;
                default:
                    JOptionPane.showMessageDialog(
                        null,
                        "<html>" + "Không tìm thấy kết quả nào cho: " + text + "</html>",
                        "Kết quả tìm kiếm",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    break;
            }
        }
        txtSearch.setText("");
    }

    public void setTitleName(String name) {
        lbPanelName.setText(name);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPHeaderBar = new javax.swing.JPanel();
        jpWelcome = new javax.swing.JPanel();
        lbPanelName = new javax.swing.JLabel();
        jpSearch = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        jpProfile = new javax.swing.JPanel();
        btnProfile = new javax.swing.JButton();
        btnShowSearch = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setPreferredSize(new java.awt.Dimension(1017, 74));
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                formAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        setLayout(new java.awt.BorderLayout());

        lbPanelName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbPanelName.setForeground(new java.awt.Color(255, 255, 255));
        lbPanelName.setText("Trang chủ");

        javax.swing.GroupLayout jpWelcomeLayout = new javax.swing.GroupLayout(jpWelcome);
        jpWelcome.setLayout(jpWelcomeLayout);
        jpWelcomeLayout.setHorizontalGroup(
            jpWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpWelcomeLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(lbPanelName, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jpWelcomeLayout.setVerticalGroup(
            jpWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpWelcomeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lbPanelName)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        txtSearch.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txtSearchInputMethodTextChanged(evt);
            }
        });

        javax.swing.GroupLayout jpSearchLayout = new javax.swing.GroupLayout(jpSearch);
        jpSearch.setLayout(jpSearchLayout);
        jpSearchLayout.setHorizontalGroup(
            jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );
        jpSearchLayout.setVerticalGroup(
            jpSearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpSearchLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnProfile.setContentAreaFilled(false);
        btnProfile.setOpaque(false);
        btnProfile.setBorderPainted(false);
        btnProfile.setIcon(new FlatSVGIcon("quanlynganhang/icon/profile.svg")
        );
        btnProfile.setToolTipText("Thông tin người dùng");
        btnProfile.setBorderPainted(false);
        btnProfile.setContentAreaFilled(false);
        btnProfile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProfile.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProfileMouseClicked(evt);
            }
        });

        btnShowSearch.setIcon(new FlatSVGIcon("quanlynganhang/icon/show_search_btn.svg")
        );
        btnShowSearch.setToolTipText("Tìm kiếm");
        btnShowSearch.setBorderPainted(false);
        btnShowSearch.setContentAreaFilled(false);
        btnShowSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnShowSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnShowSearchMouseClicked(evt);
            }
        });
        btnShowSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpProfileLayout = new javax.swing.GroupLayout(jpProfile);
        jpProfile.setLayout(jpProfileLayout);
        jpProfileLayout.setHorizontalGroup(
            jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpProfileLayout.createSequentialGroup()
                .addContainerGap(192, Short.MAX_VALUE)
                .addComponent(btnShowSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jpProfileLayout.setVerticalGroup(
            jpProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpProfileLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnShowSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(btnProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPHeaderBarLayout = new javax.swing.GroupLayout(jPHeaderBar);
        jPHeaderBar.setLayout(jPHeaderBarLayout);
        jPHeaderBarLayout.setHorizontalGroup(
            jPHeaderBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHeaderBarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpWelcome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jpProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPHeaderBarLayout.setVerticalGroup(
            jPHeaderBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPHeaderBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPHeaderBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpWelcome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPHeaderBar, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void formAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_formAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_formAncestorAdded

    private void btnProfileMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProfileMouseClicked
        GlassPanePopup.showPopup(new FormPopupProfile(maDangNhap, taiKhoanNV, app, appAdmin, quyenSua, quyenXoa));
    }//GEN-LAST:event_btnProfileMouseClicked

    private void btnShowSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowSearchActionPerformed

    }//GEN-LAST:event_btnShowSearchActionPerformed

    private void btnShowSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnShowSearchMouseClicked
        if (!txtSearch.isVisible()) {
            txtSearch.setVisible(true);
            revalidate();
        } else {
            txtSearch.setVisible(false);
            revalidate();
        }
    }//GEN-LAST:event_btnShowSearchMouseClicked

    private void txtSearchInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txtSearchInputMethodTextChanged

    }//GEN-LAST:event_txtSearchInputMethodTextChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProfile;
    private javax.swing.JButton btnShowSearch;
    private javax.swing.JPanel jPHeaderBar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel jpProfile;
    private javax.swing.JPanel jpSearch;
    private javax.swing.JPanel jpWelcome;
    private javax.swing.JLabel lbPanelName;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
