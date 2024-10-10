package quanlynganhang.GUI;

import quanlynganhang.GUI.adminUI.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import quanlynganhang.BUS.ChiaQuyenBUS;
import quanlynganhang.BUS.KhoaTaiKhoanBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.KhoaTaiKhoanDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.model.message.MessageBox;
import quanlynganhang.GUI.model.textfield.SearchOptinEvent;
import quanlynganhang.GUI.model.textfield.SearchOption;

public class FormDSTaiKhoanKH extends javax.swing.JPanel {

    private JFrameBoLocDSTKKH boloc;
    private JFrameChiTietTKKH formChiTiet;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private KhoaTaiKhoanBUS khoaTaiKhoanBUS;
    private boolean isFiltered, isSearched;
    private List<TaiKhoanKHDTO> listLocTaiKhoanKH, currentList;
    private int quyenThem, quyenSua, quyenXoa;
    private ChucVuDTO chucVu;

    public FormDSTaiKhoanKH(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) throws Exception {
        this.chucVu = chucVu;
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        khoaTaiKhoanBUS = new KhoaTaiKhoanBUS();
        listLocTaiKhoanKH = new ArrayList<>();

        initComponents();
        thietLapChucVu();

        txtSearchData.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearchData.setHint("Tìm kiếm theo " + option.getName() + "...");
            }
        });

        txtSearchData.addOption(new SearchOption("họ tên khách hàng hoặc tên tài khoản", new FlatSVGIcon("quanlynganhang/icon/searchData_name.svg")));
        txtSearchData.addOption(new SearchOption("số tài khoản", new FlatSVGIcon("quanlynganhang/icon/searchData_cccd.svg")));

        txtSearchData.setSelectedIndex(0);

        loadDSTaiKhoanKH(false, false, null);
        jTableDSTaiKhoanKH.getTableHeader().setReorderingAllowed(false);
    }

    private void thietLapChucVu() {
        quyenThem = ChiaQuyenBUS.splitQuyen(chucVu.getqLTKKhachHang(), 2);
        quyenSua = ChiaQuyenBUS.splitQuyen(chucVu.getqLTKKhachHang(), 3);
        quyenXoa = ChiaQuyenBUS.splitQuyen(chucVu.getqLTKKhachHang(), 4);
    }

    public void loadDSTaiKhoanKH(boolean isFiltered, boolean isSearched, List<TaiKhoanKHDTO> list) {
        this.isFiltered = isFiltered;
        this.isSearched = isSearched;
        listLocTaiKhoanKH = list;
        DefaultTableModel model = (DefaultTableModel) jTableDSTaiKhoanKH.getModel();
        model.setRowCount(0);

        Object[][] dataModel = (isFiltered || isSearched) ? taiKhoanKHBUS.doiSangObjectTaiKhoanKH(isFiltered, isSearched, list, 0) : taiKhoanKHBUS.doiSangObjectTaiKhoanKH(isFiltered, isSearched, null, 0);
        currentList = (isFiltered || isSearched) ? list : taiKhoanKHBUS.getDSTaiKhoanKH(0);
        String[] title = {"Mã tài khoản", "Số tài khoản", "Tên tài khoản", "Tên khách hàng", "Số dư", "Ngày tạo", "Loại tài khoản", "Trạng thái tài khoản"};

        model.setDataVector(dataModel, title);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        jTableDSTaiKhoanKH.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        jTableDSTaiKhoanKH.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        jTableDSTaiKhoanKH.setDefaultEditor(Object.class, null);
    }

    private void sapXep(int columnIndex, boolean isAscending) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTableDSTaiKhoanKH.getModel());

        if (columnIndex == 0) {
            sorter.setComparator(columnIndex, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    Integer int1 = Integer.parseInt(o1.toString());
                    Integer int2 = Integer.parseInt(o2.toString());
                    return int1.compareTo(int2);
                }
            });
        } else if (columnIndex == 4) {
            sorter.setComparator(columnIndex, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    String num1 = o1.toString().split(" ")[0].replace(",", "");
                    String num2 = o2.toString().split(" ")[0].replace(",", "");

                    Integer int1 = Integer.parseInt(num1);
                    Integer int2 = Integer.parseInt(num2);

                    return int1.compareTo(int2);
                }
            });
        } else if (columnIndex == 5) {
            sorter.setComparator(columnIndex, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    try {
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate date1 = LocalDate.parse(o1.toString(), dateFormatter);
                        LocalDate date2 = LocalDate.parse(o2.toString(), dateFormatter);
                        return date1.compareTo(date2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return 0;
                    }
                }
            });
        }

        jTableDSTaiKhoanKH.setRowSorter(sorter);

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
                taiKhoanKHBUS.xuatExcel(saveFile, "Thái Dương", currentList);

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
        String typeName = option == 0 ? "name" : "accountNum";

        List<TaiKhoanKHDTO> listTKKH = taiKhoanKHBUS.timKiemTheoLoai(typeName, txtSearchData.getText().trim());
        if (listTKKH != null && !listTKKH.isEmpty()) {
            loadDSTaiKhoanKH(isFiltered, true, listTKKH);
            return true;
        }

        return false;

    }

    private void disableForm(boolean isEnable) {
        txtSearchData.setEnabled(isEnable);
        btnSearchData.setEnabled(isEnable);
        btnReload.setEnabled(isEnable);
        jTableDSTaiKhoanKH.setEnabled(isEnable);
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

        popupDSNV = new javax.swing.JPopupMenu();
        ppmChiTiet = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        ppmSua = new javax.swing.JMenuItem();
        ppmDongTK = new javax.swing.JMenuItem();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDSTaiKhoanKH = new javax.swing.JTable();

        ppmChiTiet.setText("Xem chi tiết");
        ppmChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmChiTietActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmChiTiet);
        popupDSNV.add(jSeparator2);

        ppmSua.setText("Sửa");
        ppmSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmSuaActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmSua);

        ppmDongTK.setForeground(new java.awt.Color(255, 0, 51));
        ppmDongTK.setText("Đóng tài khoản");
        ppmDongTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmDongTKActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmDongTK);

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

        cbxSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Sắp xếp theo-", "<html><p style=\"color:rgb(255, 0, 0);\">Mã tài khoản tăng dần</p></html>", "Mã tài khoản giảm dần", "<html><p style=\"color:rgb(255, 0, 0);\">Ngày tạo tăng dần</p></html>", "Ngày tạo giảm dần", "<html><p style=\"color:rgb(255, 0, 0);\">Số dư tăng dần</p></html>", "Số dư giảm dần" }));
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
                .addContainerGap()
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxSapXep, 0, 222, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxSapXep)))
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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

        jTableDSTaiKhoanKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nhân viên", "Họ đệm", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Mã CCCD", "Chức vụ"
            }
        ));
        jTableDSTaiKhoanKH.setComponentPopupMenu(popupDSNV);
        jScrollPane2.setViewportView(jTableDSTaiKhoanKH);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

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
            boloc = new JFrameBoLocDSTKKH(this);
            boloc.setResizable(false);
            boloc.setDefaultCloseOperation(JFrameBoLocDSTKKH.DISPOSE_ON_CLOSE);

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

    private void ppmChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmChiTietActionPerformed
        if (formChiTiet == null) {
            int selectedRow = jTableDSTaiKhoanKH.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản trước khi xem!");
                return;
            } else {

                Object idObj = jTableDSTaiKhoanKH.getValueAt(selectedRow, 0);
                if (idObj != null) {
                    int maTaiKhoanKH = Integer.parseInt(idObj.toString());
                    TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                    taiKhoanKH = taiKhoanKHBUS.getTaiKhoanKHById(maTaiKhoanKH);
                    if (taiKhoanKH == null) {
                        MessageBox.showErrorMessage(null, "Mã tài khoản khách hàng không tồn tại!");
                        return;
                    } else {
                        formChiTiet = new JFrameChiTietTKKH(maTaiKhoanKH, false, quyenSua, quyenXoa);
                        formChiTiet.setDefaultCloseOperation(JFrameChiTietTKKH.DISPOSE_ON_CLOSE);
                        formChiTiet.setVisible(true);

                        formChiTiet.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    loadDSTaiKhoanKH(isFiltered, isSearched, listLocTaiKhoanKH);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                formChiTiet = null;
                            }
                        });
                    }
                }
            }
        } else {
            formChiTiet.setExtendedState(JFrameChiTietTKKH.NORMAL);
            formChiTiet.setVisible(true);
        }
    }//GEN-LAST:event_ppmChiTietActionPerformed

    private void ppmSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmSuaActionPerformed
        if (quyenSua == 1) {
            int selectedRow = jTableDSTaiKhoanKH.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản khách hàng trước khi sửa!");
                return;

            } else {
                Object idObj = jTableDSTaiKhoanKH.getValueAt(selectedRow, 0);
                if (idObj != null) {
                    int maTaiKhoanKH = Integer.parseInt(idObj.toString());
                    TaiKhoanKHDTO taiKhoanKH = new TaiKhoanKHDTO();
                    taiKhoanKH = taiKhoanKHBUS.getTaiKhoanKHById(maTaiKhoanKH);
                    if (taiKhoanKH == null) {
                        MessageBox.showErrorMessage(null, "Mã tài khoản khách hàng không tồn tại!");
                        return;
                    } else {

                        JFrameChiTietTKKH formSua = new JFrameChiTietTKKH(maTaiKhoanKH, true, quyenSua, quyenXoa);
                        formSua.setDefaultCloseOperation(JFrameChiTietTKKH.DISPOSE_ON_CLOSE);
                        formSua.setVisible(true);

                        formSua.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    loadDSTaiKhoanKH(isFiltered, isSearched, listLocTaiKhoanKH);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });

                    }
                }
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_ppmSuaActionPerformed

    private void ppmDongTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmDongTKActionPerformed
        if (quyenXoa == 1) {
            int selectedRow = jTableDSTaiKhoanKH.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn tài khoản trước khi đóng!");
                return;
            } else {

                Object idObj = jTableDSTaiKhoanKH.getValueAt(selectedRow, 0);
                if (idObj != null) {
                    if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn đóng tài khoản này?") == JOptionPane.YES_OPTION) {
                        int maTaiKhoanKH = Integer.parseInt(idObj.toString());

                        KhoaTaiKhoanDTO khoaTK = khoaTaiKhoanBUS.selectByIdTK(maTaiKhoanKH, "TKKH");

                        if (khoaTK != null) {
                            khoaTaiKhoanBUS.unlock(khoaTK.getMaKhoaTK());
                        }

                        if (!taiKhoanKHBUS.doiTrangThai(maTaiKhoanKH, 1)) {
                            MessageBox.showErrorMessage(null, "Đóng tài khoản thất bại!");
                            return;
                        } else {
                            MessageBox.showInformationMessage(null, "", "Đóng tài khoản thành công");

                            try {
                                loadDSTaiKhoanKH(isFiltered, isSearched, listLocTaiKhoanKH);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        return;
                    }
                }
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_ppmDongTKActionPerformed

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
                    sapXep(4, true);
                    break;
                case 6:
                    sapXep(4, false);
                    break;
                default:
                    jTableDSTaiKhoanKH.setRowSorter(null);
                    break;
            }
        }
    }//GEN-LAST:event_cbxSapXepItemStateChanged

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        if (xuatFile()) {
            MessageBox.showInformationMessage(null, "Xuất file", "Xuất file thành công!");
        } else {
            MessageBox.showErrorMessage(null, "Xuất file thất bại!");
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

    }//GEN-LAST:event_jButton5ActionPerformed

    private void txtSearchDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchDataKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSearchDataActionPerformed(null);
        }
    }//GEN-LAST:event_txtSearchDataKeyReleased

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        try {
            if (cbxSapXep.getSelectedIndex() != 0) {
                cbxSapXep.setSelectedIndex(0);
            }

            if (boloc != null) {
                listLocTaiKhoanKH = boloc.listTKKHBoLoc();
                if (listLocTaiKhoanKH == null) {
                    MessageBox.showErrorMessage(null, "Không có tài khoản khách hàng nào!");
                    boloc = null;
                    loadDSTaiKhoanKH(false, false, null);
                } else {
                    loadDSTaiKhoanKH(isFiltered, false, listLocTaiKhoanKH);
                }
            } else {
                loadDSTaiKhoanKH(false, false, null);
            }

            txtSearchData.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnSearchDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchDataActionPerformed
        if (!searchData()) {
            MessageBox.showErrorMessage(null, "Không tìm thấy thông tin!");
        }
    }//GEN-LAST:event_btnSearchDataActionPerformed


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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable jTableDSTaiKhoanKH;
    private javax.swing.JPopupMenu popupDSNV;
    private javax.swing.JMenuItem ppmChiTiet;
    private javax.swing.JMenuItem ppmDongTK;
    private javax.swing.JMenuItem ppmSua;
    private quanlynganhang.GUI.model.textfield.TextFieldSearchOption txtSearchData;
    // End of variables declaration//GEN-END:variables
}
