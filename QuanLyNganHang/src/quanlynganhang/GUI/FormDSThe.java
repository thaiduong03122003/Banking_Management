package quanlynganhang.GUI;

import quanlynganhang.GUI.*;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import quanlynganhang.BUS.ChiaQuyenBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TheATMBUS;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.DTO.TheATMDTO;
import quanlynganhang.GUI.JFrameBoLocDSThe;
import quanlynganhang.GUI.adminUI.JDialogDoiMatKhau;
import quanlynganhang.GUI.adminUI.JFrameChiTietTKNV;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;
import quanlynganhang.GUI.model.textfield.SearchOptinEvent;
import quanlynganhang.GUI.model.textfield.SearchOption;

public class FormDSThe extends javax.swing.JPanel {

    private JFrameBoLocDSThe boloc;
    private JFrameChiTietTKKH formChiTiet;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private TheATMBUS theATMBUS;
    private boolean isFiltered, isSearched;
    private List<TheATMDTO> listLocThe, currentList;
    private TaiKhoanNVDTO taiKhoanNV;
    private int quyenThem, quyenSua, quyenXoa;
    private ChucVuDTO chucVu;

    public FormDSThe(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) {
        this.taiKhoanNV = taiKhoanNV;
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        theATMBUS = new TheATMBUS();
        listLocThe = new ArrayList<>();
        this.chucVu = chucVu;

        initComponents();
        thietLapChucVu();

        txtSearchData.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearchData.setHint("Tìm kiếm theo " + option.getName() + "...");
            }
        });

        txtSearchData.addOption(new SearchOption("tên khách hàng", new FlatSVGIcon("quanlynganhang/icon/searchData_name.svg")));
        txtSearchData.addOption(new SearchOption("số tài khoản liên kết", new FlatSVGIcon("quanlynganhang/icon/searchData_accNum.svg")));
        txtSearchData.addOption(new SearchOption("số thẻ", new FlatSVGIcon("quanlynganhang/icon/searchData_cccd.svg")));

        txtSearchData.setSelectedIndex(0);

        loadDSThe(false, false, null);
        jTableDSThe.getTableHeader().setReorderingAllowed(false);
    }

    private void thietLapChucVu() {
        quyenThem = ChiaQuyenBUS.splitQuyen(chucVu.getqLThe(), 2);
        quyenSua = ChiaQuyenBUS.splitQuyen(chucVu.getqLThe(), 3);
        quyenXoa = ChiaQuyenBUS.splitQuyen(chucVu.getqLThe(), 4);
    }

    public void loadDSThe(boolean isFiltered, boolean isSearched, List<TheATMDTO> list) {
        this.isFiltered = isFiltered;
        this.isSearched = isSearched;
        listLocThe = list;
        DefaultTableModel model = (DefaultTableModel) jTableDSThe.getModel();
        model.setRowCount(0);

        Object[][] dataModel = isFiltered || isSearched ? theATMBUS.doiSangObjectThe(isFiltered, isSearched, list) : theATMBUS.doiSangObjectThe(isFiltered, isSearched, null);
        currentList = isFiltered || isSearched ? list : theATMBUS.getDSThe();
        String[] title = {"Mã thẻ", "Số thẻ", "Tên khách hàng", "Ngày tạo", "Thời hạn thẻ", "Loại thẻ", "Trạng thái thẻ"};
        model.setDataVector(dataModel, title);

        jTableDSThe.setDefaultEditor(Object.class, null);
    }

    private void sapXep(int columnIndex, boolean isAscending) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTableDSThe.getModel());

        if (columnIndex == 0) {
            sorter.setComparator(columnIndex, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    Integer int1 = Integer.parseInt(o1.toString());
                    Integer int2 = Integer.parseInt(o2.toString());
                    return int1.compareTo(int2);
                }
            });
        }

        jTableDSThe.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(columnIndex, isAscending ? SortOrder.ASCENDING : SortOrder.DESCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private boolean xuatFile() {
        try {
            JFileChooser jFileChooser = new JFileChooser("Lưu file");
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                theATMBUS.xuatExcel(saveFile, taiKhoanNV.getTenNhanVien(), currentList);

                return true;
            } else {
                return false;
            }

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean searchData() {
        if (txtSearchData.getText().trim().isEmpty()) {
            MessageBox.showErrorMessage(null, "Vui lòng nhập thông tin muốn tìm!");
            return true;
        }

        int option = txtSearchData.getSelectedIndex();
        String typeName;
        switch (option) {
            case 0:
                typeName = "name";
                break;
            case 1:
                typeName = "accNum";
                break;
            case 2:
                typeName = "cardNum";
                break;
            default:
                return false;
        }

        List<TheATMDTO> listThe = theATMBUS.timKiemTheoLoai(typeName, txtSearchData.getText().trim());
        if (listThe != null && !listThe.isEmpty()) {
            loadDSThe(isFiltered, true, listThe);
            return true;
        }

        return false;
    }

    private void disableForm(boolean isEnable) {
        txtSearchData.setEnabled(isEnable);
        btnSearchData.setEnabled(isEnable);
        btnReload.setEnabled(isEnable);
        jTableDSThe.setEnabled(isEnable);
        cbxSapXep.setEnabled(isEnable);
        btnBoLoc.setEnabled(isEnable);
        btnXuatFile.setEnabled(isEnable);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mnuGiaHan = new javax.swing.JMenuItem();
        mnuDoiMaPIN = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuDoiTrangThai = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnSearchData = new javax.swing.JButton();
        txtSearchData = new quanlynganhang.GUI.model.textfield.TextFieldSearchOption();
        jPanel5 = new javax.swing.JPanel();
        btnBoLoc = new javax.swing.JButton();
        cbxSapXep = new javax.swing.JComboBox<>();
        btnReload = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSThe = new javax.swing.JTable();

        mnuGiaHan.setText("Gia hạn thẻ");
        mnuGiaHan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGiaHanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnuGiaHan);

        mnuDoiMaPIN.setText("Đổi mã PIN");
        mnuDoiMaPIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDoiMaPINActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnuDoiMaPIN);
        jPopupMenu1.add(jSeparator2);

        mnuDoiTrangThai.setText("Đổi trạng thái");
        mnuDoiTrangThai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDoiTrangThaiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(mnuDoiTrangThai);

        setPreferredSize(new java.awt.Dimension(1132, 511));
        setLayout(new java.awt.BorderLayout());

        btnSearchData.setIcon(new FlatSVGIcon("quanlynganhang/icon/search_btn.svg"));
        btnSearchData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchDataActionPerformed(evt);
            }
        });

        txtSearchData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchDataKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSearchData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        btnBoLoc.setIcon(new FlatSVGIcon("quanlynganhang/icon/filter_btn.svg")
        );
        btnBoLoc.setText("Bộ lọc");
        btnBoLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoLocActionPerformed(evt);
            }
        });

        cbxSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Sắp xếp theo-", "<html><p style=\"color:rgb(255, 0, 0);\">Mã thẻ tăng dần</p></html>", "Mã thẻ giảm dần", "<html><p style=\"color:rgb(255, 0, 0);\">Ngày tạo tăng dần</p></html>", "Ngày tạo giảm dần", "<html><p style=\"color:rgb(255, 0, 0);\">Ngày hết hạn tăng dần</p></html>", "Ngày hết hạn giảm dần", "<html><p style=\"color:rgb(255, 0, 0);\">Tên theo thứ tự A -> Z</p></html>", "Tên theo thứ tự Z -> A" }));
        cbxSapXep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSapXepItemStateChanged(evt);
            }
        });

        btnReload.setIcon(new FlatSVGIcon("quanlynganhang/icon/reload_btn.svg"));
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        btnXuatFile.setIcon(new FlatSVGIcon("quanlynganhang/icon/xuat_excel_btn.svg"));
        btnXuatFile.setText("Xuất file");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxSapXep, 0, 236, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBoLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                        .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
                    .addComponent(cbxSapXep)
                    .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel8.setLayout(new java.awt.BorderLayout());

        jTableDSThe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã thẻ", "Số thẻ", "Số dư", "Thời hạn thẻ", "Mã tài khoản liên kết", "Loại thẻ", "Nhân viên thực hiện", "Trạng thái"
            }
        ));
        jTableDSThe.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(jTableDSThe);

        jPanel8.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBoLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoLocActionPerformed
        if (boloc == null) {
            boloc = new JFrameBoLocDSThe(this);
            boloc.setResizable(false);
            boloc.setDefaultCloseOperation(JFrameBoLocDSThe.DISPOSE_ON_CLOSE);

            boloc.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        disableForm(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        disableForm(false);
        boloc.setExtendedState(JFrameBoLocDSTKKH.NORMAL);
        boloc.setVisible(true);
    }//GEN-LAST:event_btnBoLocActionPerformed

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        if (xuatFile()) {
            MessageBox.showInformationMessage(null, "Xuất file", "Xuất file thành công!");
        } else {
            MessageBox.showErrorMessage(null, "Xuất file thất bại!");
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void mnuGiaHanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGiaHanActionPerformed
        int selectedRow = jTableDSThe.getSelectedRow();
        if (selectedRow == -1) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn thẻ trước khi gia hạn!");
            return;
        } else {

            Object idObj = jTableDSThe.getValueAt(selectedRow, 0);
            if (idObj != null) {
                int maThe = Integer.parseInt(idObj.toString());

                TheATMDTO theATM = theATMBUS.getTheById(maThe);

                if (theATM == null) {
                    MessageBox.showErrorMessage(null, "Không tìm thấy thông tin thẻ");
                    return;
                }

                JDialogGiaHanThe jDialogGiaHanThe = new JDialogGiaHanThe(null, true, theATM);
                jDialogGiaHanThe.setDefaultCloseOperation(JDialogGiaHanThe.DISPOSE_ON_CLOSE);
                jDialogGiaHanThe.setVisible(true);

                jDialogGiaHanThe.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            loadDSThe(isFiltered, isSearched, currentList);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        formChiTiet = null;
                    }
                });

            }
        }
    }//GEN-LAST:event_mnuGiaHanActionPerformed

    private void mnuDoiMaPINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDoiMaPINActionPerformed
        int selectedRow = jTableDSThe.getSelectedRow();
        if (selectedRow == -1) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn thẻ trước khi đổi mã PIN!");
            return;
        } else {

            Object idObj = jTableDSThe.getValueAt(selectedRow, 0);
            if (idObj != null) {
                int maThe = Integer.parseInt(idObj.toString());

                JDialogDoiMatKhauKH doiMaPIN = new JDialogDoiMatKhauKH(null, true, false, 0, maThe);
                doiMaPIN.setDefaultCloseOperation(JDialogDoiMatKhauKH.DISPOSE_ON_CLOSE);
                doiMaPIN.setVisible(true);

                doiMaPIN.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            loadDSThe(isFiltered, isSearched, currentList);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        formChiTiet = null;
                    }
                });

            }
        }
    }//GEN-LAST:event_mnuDoiMaPINActionPerformed

    private void mnuDoiTrangThaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDoiTrangThaiActionPerformed
        int selectedRow = jTableDSThe.getSelectedRow();
        if (selectedRow == -1) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn thẻ trước khi đổi trạng thái!");
            return;
        } else {

            Object idObj = jTableDSThe.getValueAt(selectedRow, 0);
            if (idObj != null) {
                int maThe = Integer.parseInt(idObj.toString());

                JDialogDoiTrangThaiThe jDoiTrangThaiThe = new JDialogDoiTrangThaiThe(null, true, maThe);
                jDoiTrangThaiThe.setDefaultCloseOperation(JDialogDoiTrangThaiThe.DISPOSE_ON_CLOSE);
                jDoiTrangThaiThe.setVisible(true);

                jDoiTrangThaiThe.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            loadDSThe(isFiltered, isSearched, currentList);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        formChiTiet = null;
                    }
                });

            }
        }
    }//GEN-LAST:event_mnuDoiTrangThaiActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged

    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void txtSearchDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchDataKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSearchDataActionPerformed(null);
        }
    }//GEN-LAST:event_txtSearchDataKeyReleased

    private void btnSearchDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchDataActionPerformed
        if (!searchData()) {
            MessageBox.showErrorMessage(null, "Không tìm thấy thông tin!");
        }
    }//GEN-LAST:event_btnSearchDataActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        if (cbxSapXep.getSelectedIndex() != 0) {
            cbxSapXep.setSelectedIndex(0);
        }

        if (boloc != null) {
            listLocThe = boloc.listTheBoLoc();
            if (listLocThe == null) {
                MessageBox.showErrorMessage(null, "Không có thông tin thẻ nào!");
                boloc = null;
                loadDSThe(false, false, null);
            } else {
                loadDSThe(isFiltered, false, listLocThe);
            }
        } else {
            loadDSThe(false, false, null);
        }

        txtSearchData.setText("");
    }//GEN-LAST:event_btnReloadActionPerformed

    private void cbxSapXepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSapXepItemStateChanged
        if (evt != null && evt.getStateChange() == ItemEvent.SELECTED) {
            int selectedIndex = cbxSapXep.getSelectedIndex();

            switch (selectedIndex) {
                case 1:
                    sapXep(0, true);
                    break;
                case 2:
                    sapXep(0, false);
                    break;
                case 3:
                    sapXep(5, true);
                    break;
                case 4:
                    sapXep(5, false);
                    break;
                case 5:
                    sapXep(5, true);
                    break;
                case 6:
                    sapXep(5, false);
                    break;
                case 7:
                    sapXep(4, true);
                    break;
                case 8:
                    sapXep(4, false);
                    break;
                default:
                    jTableDSThe.setRowSorter(null);
                    break;
            }
        }
    }//GEN-LAST:event_cbxSapXepItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBoLoc;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSearchData;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cbxSapXep;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable jTableDSThe;
    private javax.swing.JMenuItem mnuDoiMaPIN;
    private javax.swing.JMenuItem mnuDoiTrangThai;
    private javax.swing.JMenuItem mnuGiaHan;
    private quanlynganhang.GUI.model.textfield.TextFieldSearchOption txtSearchData;
    // End of variables declaration//GEN-END:variables
}
