package quanlynganhang.GUI.adminUI;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import quanlynganhang.BUS.ChiaQuyenBUS;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.model.message.MessageBox;
import quanlynganhang.GUI.model.textfield.SearchOptinEvent;
import quanlynganhang.GUI.model.textfield.SearchOption;

public class FormDSNhanVien extends javax.swing.JPanel {

    private JFrameBoLocDSNV boloc;
    private JFrameChiTietNV formChiTiet;
    private TaiKhoanNVDTO taiKhoanNV;
    private NhanVienBUS nhanVienBUS;
    private int quyenThem1, quyenThem2, quyenSua, quyenXoa, biXoa;
    private boolean isFiltered, isSearched;
    private List<NhanVienDTO> listLocNV, currentList;
    private ChucVuDTO chucVu;

    public FormDSNhanVien(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) throws Exception {
        this.chucVu = chucVu;
        this.taiKhoanNV = taiKhoanNV;
        nhanVienBUS = new NhanVienBUS();
        listLocNV = new ArrayList<>();
        initComponents();
        thietLapChucVu();

        txtSearchData.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearchData.setHint("Tìm kiếm theo " + option.getName() + "...");
            }
        });

        txtSearchData.addOption(new SearchOption("họ tên nhân viên", new FlatSVGIcon("quanlynganhang/icon/searchData_name.svg")));
        txtSearchData.addOption(new SearchOption("số điện thoại", new FlatSVGIcon("quanlynganhang/icon/searchData_phoneNum.svg")));
        txtSearchData.addOption(new SearchOption("email", new FlatSVGIcon("quanlynganhang/icon/searchData_email.svg")));
        txtSearchData.addOption(new SearchOption("mã căn cước", new FlatSVGIcon("quanlynganhang/icon/searchData_cccd.svg")));
        txtSearchData.setSelectedIndex(0);

        biXoa = 0;
        loadDSNhanVien(biXoa, false, false, null);
        jTableDSNV.getTableHeader().setReorderingAllowed(false);
    }

    private void thietLapChucVu() {
        quyenThem1 = ChiaQuyenBUS.splitQuyen(chucVu.getqLNhanVien(), 2);
        quyenThem2 = ChiaQuyenBUS.splitQuyen(chucVu.getqLTKNhanVien(), 2);
        quyenSua = ChiaQuyenBUS.splitQuyen(chucVu.getqLNhanVien(), 3);
        quyenXoa = ChiaQuyenBUS.splitQuyen(chucVu.getqLNhanVien(), 4);
    }

    public void loadDSNhanVien(int biXoa, boolean isFiltered, boolean isSearched, List<NhanVienDTO> list) {
        this.isFiltered = isFiltered;
        this.isSearched = isSearched;
        listLocNV = list;
        DefaultTableModel model = (DefaultTableModel) jTableDSNV.getModel();
        model.setRowCount(0);

        Object[][] dataModel = (isFiltered || isSearched) ? nhanVienBUS.doiSangObjectNhanVien(biXoa, isFiltered, isSearched, list) : nhanVienBUS.doiSangObjectNhanVien(biXoa, isFiltered, isSearched, null);
        currentList = (isFiltered || isSearched) ? list : nhanVienBUS.getDSNhanVien(biXoa);
        String[] title = {"Mã nhân viên", "Họ đệm", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Mã Căn cước công dân", "Chức vụ"};
        model.setDataVector(dataModel, title);

        jTableDSNV.setDefaultEditor(Object.class, null);
    }

    private void sapXep(int columnIndex, boolean isAscending) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTableDSNV.getModel());
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

        jTableDSNV.setRowSorter(sorter);

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
                nhanVienBUS.xuatExcel(saveFile, taiKhoanNV.getTenNhanVien(), currentList);

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

    private void themNhanVien() {
        if (quyenThem1 == 1) {
            JDialogThemNV themNV = new JDialogThemNV(null, true);
            themNV.setDefaultCloseOperation(JDialogThemNV.DISPOSE_ON_CLOSE);
            themNV.setVisible(true);

            loadDSNhanVien(0, false, false, null);
        } else {
            ChiaQuyenBUS.showError();
            return;
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
                typeName = "phoneNumber";
                break;
            case 2:
                typeName = "email";
                break;
            case 3:
                typeName = "cccd";
                break;
            default:
                return false;
        }

        List<NhanVienDTO> listNV = nhanVienBUS.timKiemTheoLoai(biXoa, typeName, txtSearchData.getText().trim());

        if (listNV != null && !listNV.isEmpty()) {
            loadDSNhanVien(biXoa, isFiltered, true, listNV);
            return true;
        }

        return false;
    }

    private void disableForm(boolean isEnable) {
        txtSearchData.setEnabled(isEnable);
        btnSearchData.setEnabled(isEnable);
        btnReload.setEnabled(isEnable);
        jTableDSNV.setEnabled(isEnable);
        btnThemNV.setEnabled(isEnable);
        cbxSapXep.setEnabled(isEnable);
        btnBoLoc.setEnabled(isEnable);
        btnXuatFile.setEnabled(isEnable);
        btnDoiBang.setEnabled(isEnable);
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
        ppmTaoTK = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        ppmSua = new javax.swing.JMenuItem();
        ppmXoa = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtSearchData = new quanlynganhang.GUI.model.textfield.TextFieldSearchOption();
        btnSearchData = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnBoLoc = new javax.swing.JButton();
        cbxSapXep = new javax.swing.JComboBox<>();
        btnReload = new javax.swing.JButton();
        btnXuatFile = new javax.swing.JButton();
        btnDoiBang = new javax.swing.JButton();
        btnThemNV = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDSNV = new javax.swing.JTable();

        ppmChiTiet.setText("Xem chi tiết");
        ppmChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmChiTietActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmChiTiet);

        ppmTaoTK.setText("Tạo tài khoản");
        ppmTaoTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmTaoTKActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmTaoTK);
        popupDSNV.add(jSeparator2);

        ppmSua.setText("Sửa");
        ppmSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmSuaActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmSua);

        ppmXoa.setForeground(new java.awt.Color(255, 0, 51));
        ppmXoa.setText("Xóa");
        ppmXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmXoaActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmXoa);

        setPreferredSize(new java.awt.Dimension(1132, 511));
        setLayout(new java.awt.BorderLayout());

        txtSearchData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchDataKeyReleased(evt);
            }
        });

        btnSearchData.setIcon(new FlatSVGIcon("quanlynganhang/icon/search_btn.svg"));
        btnSearchData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchDataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtSearchData, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        cbxSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Sắp xếp theo-", "<html><p style=\"color:rgb(255, 0, 0);\">Mã nhân viên tăng dần</p></html>", "Mã nhân viên giảm dần", "<html><p style=\"color:rgb(255, 0, 0);\">Tên theo thứ tự A -> Z</p></html>", "Tên theo thứ tự Z -> A", "<html><p style=\"color:rgb(255, 0, 0);\">Ngày sinh tăng dần</p></html>", "Ngày sinh giảm dần" }));
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

        btnDoiBang.setText("DSNV bị xóa");
        btnDoiBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiBangActionPerformed(evt);
            }
        });

        btnThemNV.setText("Thêm NV");
        btnThemNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThemNV, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDoiBang, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBoLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBoLoc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDoiBang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbxSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btnThemNV, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
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

        jTableDSNV.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableDSNV.setComponentPopupMenu(popupDSNV);
        jScrollPane2.setViewportView(jTableDSNV);

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
            boloc = new JFrameBoLocDSNV(this, biXoa);
            boloc.setResizable(false);
            boloc.setDefaultCloseOperation(JFrameBoLocDSNV.DISPOSE_ON_CLOSE);

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
        boloc.setExtendedState(JFrameBoLocDSNV.NORMAL);
        boloc.setVisible(true);
    }//GEN-LAST:event_btnBoLocActionPerformed

    private void btnDoiBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiBangActionPerformed
        if (biXoa == 0) {
            biXoa = 1;
            ppmXoa.setVisible(false);

            loadDSNhanVien(biXoa, false, false, null);

            ppmSua.setText("Khôi phục");
            btnDoiBang.setText("DSNV hiện tại");
        } else {
            biXoa = 0;
            ppmXoa.setVisible(true);

            loadDSNhanVien(biXoa, false, false, null);

            ppmSua.setText("Sửa");
            btnDoiBang.setText("DSNV bị xóa");
        }
    }//GEN-LAST:event_btnDoiBangActionPerformed

    private void ppmChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmChiTietActionPerformed
        if (formChiTiet == null) {
            int selectedRow = jTableDSNV.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên trước khi xem!");
                return;
            } else {

                Object idObj = jTableDSNV.getValueAt(selectedRow, 0);
                if (idObj != null) {
                    int maNhanVien = Integer.parseInt(idObj.toString());
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien = nhanVienBUS.getNhanVienById(maNhanVien, biXoa);
                    if (nhanVien == null) {
                        MessageBox.showErrorMessage(null, "Mã nhân viên không tồn tại!");
                        return;
                    } else {
                        formChiTiet = new JFrameChiTietNV(nhanVien, false, quyenSua, quyenXoa);
                        formChiTiet.setDefaultCloseOperation(JFrameChiTietNV.DISPOSE_ON_CLOSE);
                        formChiTiet.setVisible(true);

                        formChiTiet.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                loadDSNhanVien(biXoa, isFiltered, isSearched, listLocNV);

                                formChiTiet = null;
                            }
                        });
                    }
                }
            }
        } else {
            formChiTiet.setExtendedState(JFrameChiTietNV.NORMAL);
            formChiTiet.setVisible(true);
        }
    }//GEN-LAST:event_ppmChiTietActionPerformed

    private void ppmSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmSuaActionPerformed
        if (quyenSua == 1) {
            int selectedRow = jTableDSNV.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên trước khi sửa!");
                return;

            } else {
                Object idObj = jTableDSNV.getValueAt(selectedRow, 0);
                if (idObj != null) {
                    int maNhanVien = Integer.parseInt(idObj.toString());
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien = nhanVienBUS.getNhanVienById(maNhanVien, biXoa);
                    if (nhanVien == null) {
                        MessageBox.showErrorMessage(null, "Mã nhân viên không tồn tại!");
                        return;
                    } else {

                        if (biXoa == 1) {
                            if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn khôi phục nhân viên này?") == JOptionPane.YES_OPTION) {
                                if (nhanVienBUS.restoreNhanVien(maNhanVien)) {
                                    MessageBox.showInformationMessage(null, "", "Đã khôi phục lại nhân viên!");

                                    loadDSNhanVien(biXoa, isFiltered, isSearched, listLocNV);
                                } else {
                                    MessageBox.showErrorMessage(null, "Khôi phục nhân viên thất bại");
                                }
                            } else {
                                return;
                            }

                        } else {
                            JFrameChiTietNV formSua = new JFrameChiTietNV(nhanVien, true, quyenSua, quyenXoa);
                            formSua.setDefaultCloseOperation(JFrameChiTietNV.DISPOSE_ON_CLOSE);
                            formSua.setVisible(true);

                            formSua.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosed(WindowEvent e) {
                                    loadDSNhanVien(biXoa, isFiltered, isSearched, listLocNV);
                                }
                            });
                        }
                    }
                }
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_ppmSuaActionPerformed

    private void ppmXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmXoaActionPerformed
        if (quyenXoa == 1) {
            int selectedRow = jTableDSNV.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên trước khi xóa!");
                return;
            } else {
                if (biXoa == 1) {
                    MessageBox.showErrorMessage(null, "Tính năng đang cập nhật");
                    return;
                } else {
                    Object idObj = jTableDSNV.getValueAt(selectedRow, 0);
                    if (idObj != null) {
                        if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn xóa nhân viên này?") == JOptionPane.YES_OPTION) {
                            int maNhanVien = Integer.parseInt(idObj.toString());
                            boolean isDelete = nhanVienBUS.deleteNhanVien(maNhanVien);
                            if (isDelete == false) {
                                MessageBox.showErrorMessage(null, "Xóa nhân viên thất bại!");
                                return;
                            } else {
                                MessageBox.showInformationMessage(null, "", "Xóa nhân viên thành công");
                                loadDSNhanVien(biXoa, isFiltered, isSearched, listLocNV);
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_ppmXoaActionPerformed

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
                    sapXep(2, true);
                    break;
                case 4:
                    sapXep(2, false);
                    break;
                case 5:
                    sapXep(4, true);
                    break;
                case 6:
                    sapXep(4, false);
                    break;
                default:
                    jTableDSNV.setRowSorter(null);
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

    private void ppmTaoTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmTaoTKActionPerformed
        if (quyenThem2 == 1) {
            int selectedRow = jTableDSNV.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn nhân viên trước khi thêm tài khoản!");
                return;

            } else {
                Object idObj = jTableDSNV.getValueAt(selectedRow, 0);
                if (idObj != null) {
                    int maNhanVien = Integer.parseInt(idObj.toString());
                    NhanVienDTO nhanVien = new NhanVienDTO();
                    nhanVien = nhanVienBUS.getNhanVienById(maNhanVien, biXoa);
                    if (nhanVien == null) {
                        MessageBox.showErrorMessage(null, "Mã nhân viên không tồn tại!");
                        return;
                    } else {

                        if (biXoa == 1) {
                            MessageBox.showErrorMessage(null, "Không thể thêm tài khoản cho nhân viên bị xóa!");
                            return;

                        } else {
                            JDialogThemTKNV themTK = new JDialogThemTKNV(null, true, maNhanVien);
                            themTK.setDefaultCloseOperation(JFrameChiTietNV.DISPOSE_ON_CLOSE);
                            themTK.setVisible(true);
                        }
                    }
                }
            }
        } else {
            ChiaQuyenBUS.showError();
            return;
        }
    }//GEN-LAST:event_ppmTaoTKActionPerformed

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
        try {
            if (cbxSapXep.getSelectedIndex() != 0) {
                cbxSapXep.setSelectedIndex(0);
            }

            if (boloc != null) {
                listLocNV = boloc.listNVBoLoc();
                if (listLocNV == null) {
                    MessageBox.showErrorMessage(null, "Không có nhân viên nào!");
                    boloc = null;
                    loadDSNhanVien(biXoa, false, false, null);
                } else {
                    loadDSNhanVien(biXoa, isFiltered, false, listLocNV);
                }
            } else {
                loadDSNhanVien(biXoa, false, false, null);
            }

            txtSearchData.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReloadActionPerformed

    private void btnThemNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNVActionPerformed
        if (quyenThem1 != 1) {
            ChiaQuyenBUS.showError();
            return;
        }
        
        themNhanVien();
    }//GEN-LAST:event_btnThemNVActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBoLoc;
    private javax.swing.JButton btnDoiBang;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSearchData;
    private javax.swing.JButton btnThemNV;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cbxSapXep;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable jTableDSNV;
    private javax.swing.JPopupMenu popupDSNV;
    private javax.swing.JMenuItem ppmChiTiet;
    private javax.swing.JMenuItem ppmSua;
    private javax.swing.JMenuItem ppmTaoTK;
    private javax.swing.JMenuItem ppmXoa;
    private quanlynganhang.GUI.model.textfield.TextFieldSearchOption txtSearchData;
    // End of variables declaration//GEN-END:variables
}
