package quanlynganhang.GUI;

import quanlynganhang.GUI.adminUI.*;
import quanlynganhang.GUI.*;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TaiKhoanNVBUS;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;

public class FormDSTaiKhoanKH extends javax.swing.JPanel {

    private JFrameBoLocDSTKKH boloc;
    private JFrameChiTietTKKH formChiTiet;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private boolean isFiltered;
    private List<TaiKhoanKHDTO> listLocTaiKhoanKH, currentList;

    public FormDSTaiKhoanKH() throws Exception {
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        listLocTaiKhoanKH = new ArrayList<>();
        initComponents();
        txtSearchTaiKhoanKH.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nhập mã tài khoản/ mã khách hàng cần tìm...");
        loadDSTaiKhoanKH(false, null);
        jTableDSTaiKhoanKH.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDSTaiKhoanKH(boolean isFiltered, List<TaiKhoanKHDTO> list) throws Exception {
        this.isFiltered = isFiltered;
        listLocTaiKhoanKH = list;
        DefaultTableModel model = (DefaultTableModel) jTableDSTaiKhoanKH.getModel();
        model.setRowCount(0);

        Object[][] dataModel = isFiltered ? taiKhoanKHBUS.doiSangObjectTaiKhoanKH(isFiltered, list) : taiKhoanKHBUS.doiSangObjectTaiKhoanKH(isFiltered, null);
        currentList = isFiltered ? list : taiKhoanKHBUS.getDSTaiKhoanKH();
        String[] title = {"Mã tài khoản", "Số tài khoản", "Tên tài khoản", "Tên khách hàng", "Ngày tạo", "Loại tài khoản", "Trạng thái tài khoản"};
        model.setDataVector(dataModel, title);

        jTableDSTaiKhoanKH.setDefaultEditor(Object.class, null);
    }

    private void sapXep(int columnIndex, boolean isAscending) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTableDSTaiKhoanKH.getModel());
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

    private List<TaiKhoanKHDTO> nhapExcel(File file) {

        try {
            return taiKhoanKHBUS.nhapExcel(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
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

        popupDSNV = new javax.swing.JPopupMenu();
        ppmChiTiet = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        ppmSua = new javax.swing.JMenuItem();
        ppmXoa = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtSearchTaiKhoanKH = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnBoLoc = new javax.swing.JButton();
        cbxSapXep = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnXuatFile = new javax.swing.JButton();
        btnNhapFile = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnReload = new javax.swing.JButton();
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

        ppmXoa.setForeground(new java.awt.Color(255, 0, 51));
        ppmXoa.setText("Đóng tài khoản");
        ppmXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppmXoaActionPerformed(evt);
            }
        });
        popupDSNV.add(ppmXoa);

        setPreferredSize(new java.awt.Dimension(1132, 511));
        setLayout(new java.awt.BorderLayout());

        txtSearchTaiKhoanKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchTaiKhoanKHKeyReleased(evt);
            }
        });

        jButton5.setIcon(new FlatSVGIcon("quanlynganhang/icon/search_btn.svg"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtSearchTaiKhoanKH, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearchTaiKhoanKH, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnBoLoc.setIcon(new FlatSVGIcon("quanlynganhang/icon/filter_btn.svg")
        );
        btnBoLoc.setText("Bộ lọc");
        btnBoLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBoLocActionPerformed(evt);
            }
        });

        cbxSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Sắp xếp theo-", "Mã tài khoản tăng dần", "Mã tài khoản giảm dần", "Ngày tạo tăng dần", "Ngày tạo giảm dần" }));
        cbxSapXep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSapXepItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(139, 139, 139)
                .addComponent(cbxSapXep, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addComponent(btnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnBoLoc, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(cbxSapXep))
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

        btnXuatFile.setIcon(new FlatSVGIcon("quanlynganhang/icon/xuat_excel_btn.svg"));
        btnXuatFile.setText("Xuất file");
        btnXuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatFileActionPerformed(evt);
            }
        });

        btnNhapFile.setIcon(new FlatSVGIcon("quanlynganhang/icon/nhap_excel_btn.svg")
        );
        btnNhapFile.setText("Nhập file");
        btnNhapFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNhapFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNhapFile, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnReload.setText("Tải lại DS");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(203, Short.MAX_VALUE)
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(jPanel2, java.awt.BorderLayout.LINE_END);

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
        }

        boloc.setExtendedState(JFrameBoLocDSTKKH.NORMAL);
        boloc.setVisible(true);
    }//GEN-LAST:event_btnBoLocActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        try {
            if (boloc != null) {
                listLocTaiKhoanKH = boloc.listKHBoLoc();
                if (listLocTaiKhoanKH == null) {
                    MessageBox.showErrorMessage(null, "Không có tài khoản khách hàng nào!");
                    boloc = null;
                    loadDSTaiKhoanKH(false, null);
                } else {
                    loadDSTaiKhoanKH(isFiltered, listLocTaiKhoanKH);
                }
            } else {
                loadDSTaiKhoanKH(false, null);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnReloadActionPerformed

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
                        formChiTiet = new JFrameChiTietTKKH(maTaiKhoanKH, false);
                        formChiTiet.setDefaultCloseOperation(JFrameChiTietTKNV.DISPOSE_ON_CLOSE);
                        formChiTiet.setVisible(true);

                        formChiTiet.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    loadDSTaiKhoanKH(isFiltered, listLocTaiKhoanKH);
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

                    JFrameChiTietTKKH formSua = new JFrameChiTietTKKH(maTaiKhoanKH, true);
                    formSua.setDefaultCloseOperation(JFrameChiTietTKKH.DISPOSE_ON_CLOSE);
                    formSua.setVisible(true);

                    formSua.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            try {
                                loadDSTaiKhoanKH(isFiltered, listLocTaiKhoanKH);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                }
            }
        }
    }//GEN-LAST:event_ppmSuaActionPerformed

    private void ppmXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppmXoaActionPerformed
//        int selectedRow = jTableDSTaiKhoanKH.getSelectedRow();
//        if (selectedRow == -1) {
//            MessageBox.showErrorMessage(null, "Vui lòng chọn khách hàng trước khi xóa!");
//            return;
//        } else {
//            if (biXoa == 1) {
//                MessageBox.showErrorMessage(null, "Tính năng đang cập nhật");
//                return;
//            } else {
//                Object idObj = jTableDSTaiKhoanKH.getValueAt(selectedRow, 0);
//                if (idObj != null) {
//                    if (MessageBox.showConfirmMessage(this, "Bạn có chắc chắn muốn xóa nhân viên này?") == JOptionPane.YES_OPTION) {
//                        int maNhanVien = Integer.parseInt(idObj.toString());
//                        boolean isDelete = nhanVienBUS.deleteNhanVien(maNhanVien);
//                        if (isDelete == false) {
//                            MessageBox.showErrorMessage(null, "Xóa nhân viên thất bại!");
//                            return;
//                        } else {
//                            MessageBox.showInformationMessage(null, "", "Xóa nhân viên thành công");
//
//                            try {
//                                loadDSNhanVien(biXoa, isFiltered, listLocNV);
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                    } else {
//                        return;
//                    }
//                }
//            }
//        }
    }//GEN-LAST:event_ppmXoaActionPerformed

    private void txtSearchTaiKhoanKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchTaiKhoanKHKeyReleased
        DefaultTableModel obj = (DefaultTableModel) jTableDSTaiKhoanKH.getModel();
        TableRowSorter<DefaultTableModel> obj1 = new TableRowSorter<>(obj);
        jTableDSTaiKhoanKH.setRowSorter(obj1);

        int[] searchColumns = {0, 1, 2, 3};

        RowFilter<DefaultTableModel, Object> rowFilter = RowFilter.regexFilter(txtSearchTaiKhoanKH.getText(), searchColumns);

        obj1.setRowFilter(rowFilter);
    }//GEN-LAST:event_txtSearchTaiKhoanKHKeyReleased

    private void cbxSapXepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxSapXepItemStateChanged
        String selectedSortOption = (String) cbxSapXep.getSelectedItem();

        switch (selectedSortOption) {
            case "Mã tài khoản tăng dần":
                sapXep(0, true);
                break;
            case "Mã tài khoản giảm dần":
                sapXep(0, false);
                break;
            case "Ngày tạo tăng dần":
                sapXep(4, true);
                break;
            case "Ngày tạo giảm dần":
                sapXep(4, false);
                break;
            default:
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTableDSTaiKhoanKH.getModel());
                jTableDSTaiKhoanKH.setRowSorter(null);
                break;
        }
    }//GEN-LAST:event_cbxSapXepItemStateChanged

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        if (xuatFile()) {
            MessageBox.showInformationMessage(null, "Xuất file", "Xuất file thành công!");
        } else {
            MessageBox.showErrorMessage(null, "Xuất file thất bại!");
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed

    private void btnNhapFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapFileActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xlsx", "xlsm", "xltx", "xlsb", "xltm", "xla", "xlam", "xll", "xlw");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            List<TaiKhoanKHDTO> listTaiKhoanKH = nhapExcel(selectedFile);
            if (listTaiKhoanKH != null) {
                MessageBox.showInformationMessage(null, "Nhập file", "Lấy dữ liệu thành công!");
                try {
                    loadDSTaiKhoanKH(true, listTaiKhoanKH);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                MessageBox.showErrorMessage(null, "Lấy dữ liệu nhập thất bại!");
            }
        } else {
            return;
        }

    }//GEN-LAST:event_btnNhapFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBoLoc;
    private javax.swing.JButton btnNhapFile;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cbxSapXep;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable jTableDSTaiKhoanKH;
    private javax.swing.JPopupMenu popupDSNV;
    private javax.swing.JMenuItem ppmChiTiet;
    private javax.swing.JMenuItem ppmSua;
    private javax.swing.JMenuItem ppmXoa;
    private javax.swing.JTextField txtSearchTaiKhoanKH;
    // End of variables declaration//GEN-END:variables
}
