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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.adminUI.JFrameBoLocDSNV;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.message.MessageBox;
import quanlynganhang.GUI.model.textfield.SearchOptinEvent;
import quanlynganhang.GUI.model.textfield.SearchOption;

public class FormDSGiaoDich extends javax.swing.JPanel {

    private TaiKhoanNVDTO taiKhoanNV;
    private JFrameChiTietGD formChiTiet;
    private JFrameBoLocGD boloc;
    private GiaoDichBUS giaoDichBUS;
    private boolean isFiltered, isSearched;
    private List<GiaoDichDTO> listLocGiaoDich, currentList;

    public FormDSGiaoDich(TaiKhoanNVDTO taiKhoanNV, ChucVuDTO chucVu) {
        this.taiKhoanNV = taiKhoanNV;
        giaoDichBUS = new GiaoDichBUS();
        listLocGiaoDich = new ArrayList<>();
        initComponents();

        txtSearchData.addEventOptionSelected(new SearchOptinEvent() {
            @Override
            public void optionSelected(SearchOption option, int index) {
                txtSearchData.setHint("Tìm kiếm theo " + option.getName() + "...");
            }
        });

        txtSearchData.addOption(new SearchOption("họ tên khách hàng hoặc tên tài khoản", new FlatSVGIcon("quanlynganhang/icon/searchData_name.svg")));
        txtSearchData.addOption(new SearchOption("số tài khoản", new FlatSVGIcon("quanlynganhang/icon/searchData_cccd.svg")));
        txtSearchData.setSelectedIndex(0);
        
        loadDSGiaoDich(false, false, null);
        jTableDSGD.getTableHeader().setReorderingAllowed(false);
    }

    public void loadDSGiaoDich(boolean isFiltered, boolean isSearched, List<GiaoDichDTO> list) {
        this.isFiltered = isFiltered;
        this.isSearched = isSearched;
        listLocGiaoDich = list;
        DefaultTableModel model = (DefaultTableModel) jTableDSGD.getModel();
        model.setRowCount(0);

        Object[][] dataModel = isFiltered || isSearched ? giaoDichBUS.doiSangObjectGiaoDich(isFiltered, isSearched, list) : giaoDichBUS.doiSangObjectGiaoDich(isFiltered, isSearched, null);
        currentList = isFiltered | isSearched ? list : giaoDichBUS.getDSGiaoDich();
        String[] title = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Trạng thái"};
        model.setDataVector(dataModel, title);

        jTableDSGD.setDefaultEditor(Object.class, null);
    }
    
    private boolean xuatFile() {
            JFileChooser jFileChooser = new JFileChooser("Lưu file");
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                return giaoDichBUS.xuatExcel(saveFile, taiKhoanNV.getTenNhanVien(), currentList);
            } else {
                return false;
            }
    }
    
    private void sapXep(int columnIndex, boolean isAscending) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTableDSGD.getModel());

        if (columnIndex == 0) {
            sorter.setComparator(columnIndex, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    Integer int1 = Integer.parseInt(o1.toString());
                    Integer int2 = Integer.parseInt(o2.toString());
                    return int1.compareTo(int2);
                }
            });
        } else if (columnIndex == 3) {
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

        jTableDSGD.setRowSorter(sorter);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(columnIndex, isAscending ? SortOrder.ASCENDING : SortOrder.DESCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }
    
    private boolean searchData() {
        if (txtSearchData.getText().trim().isEmpty()) {
            MessageBox.showErrorMessage(null, "Vui lòng nhập thông tin muốn tìm!");
            return true;
        }

        try {
            int option = txtSearchData.getSelectedIndex();
            String typeName = option == 0 ? "name" : "accountNum";

            List<GiaoDichDTO> listTKKH = giaoDichBUS.timKiemTheoLoai(typeName, txtSearchData.getText().trim());
            if (listTKKH != null && !listTKKH.isEmpty()) {
                loadDSGiaoDich(isFiltered, true, listTKKH);
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private void disableForm(boolean isEnable) {
        txtSearchData.setEnabled(isEnable);
        btnSearchData.setEnabled(isEnable);
        btnReload.setEnabled(isEnable);
        jTableDSGD.setEnabled(isEnable);
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

        jPPMGD = new javax.swing.JPopupMenu();
        jPPItemChiTiet = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtSearchData = new quanlynganhang.GUI.model.textfield.TextFieldSearchOption();
        btnSearchData = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnBoLoc = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        cbxSapXep = new javax.swing.JComboBox<>();
        btnXuatFile = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDSGD = new javax.swing.JTable();

        jPPItemChiTiet.setText("Xem chi tiết");
        jPPItemChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPPItemChiTietMouseClicked(evt);
            }
        });
        jPPItemChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPPItemChiTietActionPerformed(evt);
            }
        });
        jPPMGD.add(jPPItemChiTiet);

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
                .addComponent(txtSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtSearchData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btnReload.setIcon(new FlatSVGIcon("quanlynganhang/icon/reload_btn.svg"));
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        cbxSapXep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-Sắp xếp theo-", "<html><p style=\"color:rgb(255, 0, 0);\">Mã giao dịch tăng dần</p></html>", "Mã giao dịch giảm dần", "<html><p style=\"color:rgb(255, 0, 0);\">Số tiền tăng dần</p></html>", "Số tiền giảm dần", "<html><p style=\"color:rgb(255, 0, 0);\">Ngày giao dịch tăng dần</p></html>", "Ngày giao dịch giảm dần" }));
        cbxSapXep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxSapXepItemStateChanged(evt);
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
                    .addComponent(cbxSapXep)
                    .addComponent(btnReload, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBoLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXuatFile, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

        jTableDSGD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã giao dịch", "Số tiền", "Ngày giao dịch", "Nội dung", "Mã khách hàng", "Nhân viên thực hiện", "Loại giao dịch"
            }
        ));
        jTableDSGD.setComponentPopupMenu(jPPMGD);
        jScrollPane1.setViewportView(jTableDSGD);

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

    private void jPPItemChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPPItemChiTietActionPerformed
        if (formChiTiet == null) {
            int selectedRow = jTableDSGD.getSelectedRow();
            if (selectedRow == -1) {
                MessageBox.showErrorMessage(null, "Vui lòng chọn giao dịch muốn xem!");
                return;
            }

            Object idObj = jTableDSGD.getValueAt(selectedRow, 0);
            if (idObj != null) {
                int maGiaoDich = Integer.parseInt(idObj.toString());

                formChiTiet = new JFrameChiTietGD(maGiaoDich);
                formChiTiet.setDefaultCloseOperation(JFrameChiTietGD.DISPOSE_ON_CLOSE);
                formChiTiet.setResizable(false);
                formChiTiet.setVisible(true);

                formChiTiet.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        try {
                            loadDSGiaoDich(isFiltered, isSearched, listLocGiaoDich);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        formChiTiet = null;
                    }
                });
            }
        } else {
            formChiTiet.setExtendedState(JFrameChiTietGD.NORMAL);
            formChiTiet.setVisible(true);
        }
    }//GEN-LAST:event_jPPItemChiTietActionPerformed

    private void jPPItemChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPPItemChiTietMouseClicked

    }//GEN-LAST:event_jPPItemChiTietMouseClicked

    private void btnBoLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBoLocActionPerformed
        if (boloc == null) {
            boloc = new JFrameBoLocGD(this);
            boloc.setResizable(false);
            boloc.setDefaultCloseOperation(JFrameBoLocGD.DISPOSE_ON_CLOSE);

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
        boloc.setExtendedState(JFrameBoLocGD.NORMAL);
        boloc.setVisible(true);
    }//GEN-LAST:event_btnBoLocActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
        try {
            if (cbxSapXep.getSelectedIndex() != 0) {
                cbxSapXep.setSelectedIndex(0);
            }

            if (boloc != null) {
                listLocGiaoDich = boloc.listGDBoLoc();
                if (listLocGiaoDich == null) {
                    MessageBox.showErrorMessage(null, "Không có giao dịch nào!");
                    boloc = null;
                    loadDSGiaoDich(false, false, null);
                } else {
                    loadDSGiaoDich(isFiltered, false, listLocGiaoDich);
                }
            } else {
                loadDSGiaoDich(false, false, null);
            }

            txtSearchData.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
                sapXep(3, true);
                break;
                case 4:
                sapXep(3, false);
                break;
                case 5:
                sapXep(4, true);
                break;
                case 6:
                sapXep(4, false);
                break;
                default:
                jTableDSGD.setRowSorter(null);
                break;
            }
        }
    }//GEN-LAST:event_cbxSapXepItemStateChanged

    private void txtSearchDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchDataKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSearchDataActionPerformed(null);
        }
    }//GEN-LAST:event_txtSearchDataKeyReleased

    private void btnSearchDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchDataActionPerformed
        if (!searchData()) {
            MessageBox.showErrorMessage(null, "Không tìm thấy thông tin giao dịch!");
        }
    }//GEN-LAST:event_btnSearchDataActionPerformed

    private void btnXuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatFileActionPerformed
        if (xuatFile()) {
            MessageBox.showInformationMessage(null, "Xuất file", "Xuất file thành công!");
        } else {
            MessageBox.showErrorMessage(null, "Xuất file thất bại!");
        }
    }//GEN-LAST:event_btnXuatFileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBoLoc;
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnSearchData;
    private javax.swing.JButton btnXuatFile;
    private javax.swing.JComboBox<String> cbxSapXep;
    private javax.swing.JMenuItem jPPItemChiTiet;
    private javax.swing.JPopupMenu jPPMGD;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDSGD;
    private quanlynganhang.GUI.model.textfield.TextFieldSearchOption txtSearchData;
    // End of variables declaration//GEN-END:variables
}
