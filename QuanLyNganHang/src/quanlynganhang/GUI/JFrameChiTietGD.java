package quanlynganhang.GUI;

import java.awt.CardLayout;
import java.math.BigInteger;
import javax.swing.SwingWorker;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.validation.FormatDate;
import quanlynganhang.BUS.validation.FormatNumber;
import quanlynganhang.DTO.ChuyenTienDTO;
import quanlynganhang.DTO.GiaoDichDTO;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.GUI.model.message.MessageBox;

public class JFrameChiTietGD extends javax.swing.JFrame {

    private CardLayout jPCLayoutInfo, jPCLayoutInfoGui, jPCLayoutInfoNhan;
    private GiaoDichBUS giaoDichBUS;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private StringBuilder ghiChu;
    private FormatDate fDate;

    public JFrameChiTietGD(int maGiaoDich) {
        giaoDichBUS = new GiaoDichBUS();
        taiKhoanKHBUS = new TaiKhoanKHBUS();
        fDate = new FormatDate();

        initComponents();

        jPCLayoutInfo = (CardLayout) (jPInfo.getLayout());
        jPCLayoutInfoGui = (CardLayout) (jPInfoGui.getLayout());
        jPCLayoutInfoNhan = (CardLayout) (jPInfoNhan.getLayout());
        jPCLayoutInfo.show(jPInfo, "jPLoading");

        initData(maGiaoDich);
    }

    private void initData(int maGiaoDich) {
        spLoading.setIndeterminate(true);
        jPCLayoutInfo.show(jPInfo, "jPLoading");
        
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                return loadInfoData(maGiaoDich);
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
                    spLoading.setIndeterminate(false);
                    jPCLayoutInfo.show(jPInfo, "jPShowInfo");
                }

            }
        };
        worker.execute();
    }

    private boolean loadInfoData(int maGiaoDich) {
        GiaoDichDTO giaoDich = giaoDichBUS.getGiaoDichById(maGiaoDich);

        if (giaoDich == null) {
            MessageBox.showErrorMessage(null, "Không tìm thấy thông tin giao dịch!");
            return false;
        }

        loadThongTinGD(giaoDich);

        loadThongTinKhachHang(maGiaoDich, giaoDich.getMaTaiKhoanKH(), giaoDich.getMaLoaiGiaoDich());

        return true;
    }

    private void loadThongTinGD(GiaoDichDTO giaoDich) {
        lbMaGD1.setText("" + giaoDich.getMaGiaoDich());
        lbSoTien1.setText(FormatNumber.convertNumToVND(new BigInteger(giaoDich.getSoTien())) + " VND");
        lbLoaiGD1.setText(giaoDich.getTenLoaiGiaoDich());
        lbNgayGD1.setText(fDate.toString(giaoDich.getNgayGiaoDich()));
        lbNoiDung1.setText(giaoDich.getNoiDungGiaoDich());
        lbTrangThai1.setText(giaoDich.getTenTrangThai());
        lbMaNV1.setText("" + giaoDich.getMaTaiKhoanNV());
        lbTenNV1.setText(giaoDich.getTenNhanVien());
    }

    private void loadThongTinKhachHang(int maGiaoDich, int maTaiKhoanKH, int maLoaiGiaoDich) {
        ghiChu = new StringBuilder();
        
        if (getTaiKhoanGui(maTaiKhoanKH)) {
            jPCLayoutInfoGui.show(jPInfoGui, "jPShowInfoGui");
        } else {
            jPCLayoutInfoGui.show(jPInfoGui, "jPNoInfoGui");
        }

        if (maLoaiGiaoDich != 5) {
            if (getTaiKhoanNhan(maGiaoDich)) {
                jPCLayoutInfoNhan.show(jPInfoNhan, "jPShowInfoNhan");
            } else {
                jPCLayoutInfoNhan.show(jPInfoNhan, "jPNoInfoNhan");
            }
        } else {
            jPCLayoutInfoNhan.show(jPInfoNhan, "jPNoInfoNhan");
        }
        
        if (!ghiChu.isEmpty()) {
            lbNote.setText("<html><p>Ghi chú: <span style='color:rgb(255, 0, 0);'>" + ghiChu.toString() + "</span></p></html>");
        }
    }

    private boolean getTaiKhoanGui(int maTaiKhoanKH) {
        TaiKhoanKHDTO taiKhoanKHGui = taiKhoanKHBUS.getTaiKhoanKHById(maTaiKhoanKH);

        if (taiKhoanKHGui == null) {
            return false;
        }

        txtSTKNguoiGui.setText(taiKhoanKHGui.getSoTaiKhoan());
        lbTKGui.setText(taiKhoanKHGui.getTenTaiKhoan());
        lbKHGui.setText(taiKhoanKHGui.getTenKhachHang());
        lbSDTGui.setText(taiKhoanKHGui.getSdt());
        lbEmailGui.setText(taiKhoanKHGui.getEmail());

        if (taiKhoanKHGui.getMaNganHang() != 1) {
            lbTenNganHangGui.setVisible(true);
            lbNganHangGui.setVisible(true);

            lbNganHangGui.setText(taiKhoanKHGui.getTenNganHang());
        } else {
            lbTenNganHangGui.setVisible(false);
            lbNganHangGui.setVisible(false);
        }
        
        if (taiKhoanKHGui.getMaTrangThai() == 1) {
            themGhiChu("TKKH gửi đã đóng");
            return true;
        }
        
        if (taiKhoanKHGui.getBiXoa() == 1) {
            themGhiChu("KH gửi đã xóa");
            return true;
        }

        return true;
    }

    private boolean getTaiKhoanNhan(int maGiaoDich) {
        ChuyenTienDTO chuyenTien = giaoDichBUS.getByIdGD(maGiaoDich);

        if (chuyenTien == null) {
            return false;
        }

        txtSTKNguoiNhan.setText(chuyenTien.getSoTaiKhoanNhan());
        lbTKNhan.setText(chuyenTien.getTenTaiKhoanNhan());
        lbKHNhan.setText(chuyenTien.getTenKhachHangNhan());
        lbSDTNhan.setText(chuyenTien.getSdt());
        lbEmailNhan.setText(chuyenTien.getEmail());

        if (chuyenTien.getMaNganHang() != 1) {
            lbTenNganHangNhan.setVisible(true);
            lbNganHangNhan.setVisible(true);

            lbNganHangNhan.setText(chuyenTien.getTenNganHang());
        } else {
            lbTenNganHangNhan.setVisible(false);
            lbNganHangNhan.setVisible(false);
        }
        
        if (chuyenTien.getBiDong()== 1) {
            themGhiChu("TKKH nhận đã đóng");
            return true;
        }
        
        if (chuyenTien.getBiXoa() == 1) {
            themGhiChu("KH nhận đã xóa");
            return true;
        }

        return true;
    }
    
    private void themGhiChu(String noiDung) {
        if (ghiChu.isEmpty()) {
            ghiChu.append(noiDung);
        } else {
            ghiChu.append(", " + noiDung);
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
        lbChiTietThongTin = new javax.swing.JLabel();
        jPInfo = new javax.swing.JPanel();
        jPShowInfo = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lbSoTien1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lbLoaiGD1 = new javax.swing.JLabel();
        lbNgayGD1 = new javax.swing.JLabel();
        lbTrangThai1 = new javax.swing.JLabel();
        lbMaNV1 = new javax.swing.JLabel();
        lbTenNV1 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lbMaGD1 = new javax.swing.JLabel();
        lbNote = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lbNoiDung1 = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel14 = new javax.swing.JPanel();
        jPInfoGui = new javax.swing.JPanel();
        jPNoInfoGui = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPShowInfoGui = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSTKNguoiGui = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbTKGui = new javax.swing.JLabel();
        lbKHGui = new javax.swing.JLabel();
        lbSDTGui = new javax.swing.JLabel();
        lbEmailGui = new javax.swing.JLabel();
        lbTenNganHangGui = new javax.swing.JLabel();
        lbNganHangGui = new javax.swing.JLabel();
        jPInfoNhan = new javax.swing.JPanel();
        jPNoInfoNhan = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPShowInfoNhan = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtSTKNguoiNhan = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lbTKNhan = new javax.swing.JLabel();
        lbKHNhan = new javax.swing.JLabel();
        lbSDTNhan = new javax.swing.JLabel();
        lbEmailNhan = new javax.swing.JLabel();
        lbTenNganHangNhan = new javax.swing.JLabel();
        lbNganHangNhan = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPLoading = new javax.swing.JPanel();
        spLoading = new quanlynganhang.GUI.model.spinner.SpinnerProgress();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbChiTietThongTin.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        lbChiTietThongTin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbChiTietThongTin.setText("Chi tiết thông tin giao dịch");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbChiTietThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbChiTietThongTin)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPInfo.setLayout(new java.awt.CardLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Số tiền giao dịch:");

        lbSoTien1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbSoTien1.setText("2,000,000,000 VND");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel19.setText("Loại giao dịch:");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel20.setText("Ngày giao dịch:");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel21.setText("Trạng thái:");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel22.setText("Nội dung:");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel23.setText("Mã nhân viên thực hiện:");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel24.setText("Tên nhân viên thực hiện:");

        lbLoaiGD1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbLoaiGD1.setText("Rút tiền");

        lbNgayGD1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbNgayGD1.setText("2,000,000,000 VND");

        lbTrangThai1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbTrangThai1.setText("2,000,000,000 VND");

        lbMaNV1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbMaNV1.setText("2,000,000,000 VND");

        lbTenNV1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbTenNV1.setText("2,000,000,000 VND");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel26.setText("Mã giao dịch:");

        lbMaGD1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbMaGD1.setText("1");

        lbNote.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbNote.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbNote.setText("");

        lbNoiDung1.setEditable(false);
        lbNoiDung1.setColumns(20);
        lbNoiDung1.setRows(5);
        jScrollPane1.setViewportView(lbNoiDung1);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbSoTien1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbLoaiGD1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNgayGD1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbMaGD1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbNote, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(43, 43, 43)
                                .addComponent(lbTrangThai1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                                .addComponent(lbMaNV1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbTenNV1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(lbMaGD1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lbSoTien1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lbLoaiGD1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lbNgayGD1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel22)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(lbTrangThai1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(lbMaNV1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lbTenNV1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbNote)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPInfoGui.setLayout(new java.awt.CardLayout());

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Không thể hiển thị thông tin tài khoản hệ thống");

        javax.swing.GroupLayout jPNoInfoGuiLayout = new javax.swing.GroupLayout(jPNoInfoGui);
        jPNoInfoGui.setLayout(jPNoInfoGuiLayout);
        jPNoInfoGuiLayout.setHorizontalGroup(
            jPNoInfoGuiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNoInfoGuiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPNoInfoGuiLayout.setVerticalGroup(
            jPNoInfoGuiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNoInfoGuiLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPInfoGui.add(jPNoInfoGui, "jPNoInfoGui");

        jPShowInfoGui.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("Số tài khoản người gửi:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Tên tài khoản người gửi:");

        txtSTKNguoiGui.setEditable(false);
        txtSTKNguoiGui.setText("Dương Nguyễn Nghĩa Thái");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Tên khách hàng gửi:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setText("Số điện thoại: ");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Email: ");

        lbTKGui.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTKGui.setText("jLabel28");

        lbKHGui.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbKHGui.setText("jLabel28");

        lbSDTGui.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbSDTGui.setText("jLabel28");

        lbEmailGui.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbEmailGui.setText("jLabel28");

        lbTenNganHangGui.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTenNganHangGui.setText("Ngân hàng thụ hưởng:");

        lbNganHangGui.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbNganHangGui.setText("jLabel28");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                    .addComponent(lbTenNganHangGui, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTKGui, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbKHGui, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtSTKNguoiGui, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .addComponent(lbEmailGui, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbSDTGui, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbNganHangGui, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtSTKNguoiGui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lbTKGui))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lbKHGui))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lbSDTGui))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lbEmailGui))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTenNganHangGui)
                    .addComponent(lbNganHangGui))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPShowInfoGui.add(jPanel17, java.awt.BorderLayout.CENTER);

        jPInfoGui.add(jPShowInfoGui, "jPShowInfoGui");

        jPInfoNhan.setLayout(new java.awt.CardLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Không thể hiển thị thông tin tài khoản hệ thống");

        javax.swing.GroupLayout jPNoInfoNhanLayout = new javax.swing.GroupLayout(jPNoInfoNhan);
        jPNoInfoNhan.setLayout(jPNoInfoNhanLayout);
        jPNoInfoNhanLayout.setHorizontalGroup(
            jPNoInfoNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNoInfoNhanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPNoInfoNhanLayout.setVerticalGroup(
            jPNoInfoNhanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPNoInfoNhanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPInfoNhan.add(jPNoInfoNhan, "jPNoInfoNhan");

        jPShowInfoNhan.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("Số tài khoản người nhận:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Tên tài khoản người nhận:");

        txtSTKNguoiNhan.setEditable(false);
        txtSTKNguoiNhan.setText("jTextField3");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setText("Tên khách hàng nhận:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setText("Số điện thoại: ");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel28.setText("Email: ");

        lbTKNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTKNhan.setText("jLabel28");

        lbKHNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbKHNhan.setText("jLabel28");

        lbSDTNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbSDTNhan.setText("jLabel28");

        lbEmailNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbEmailNhan.setText("jLabel28");

        lbTenNganHangNhan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTenNganHangNhan.setText("Ngân hàng thụ hưởng:");

        lbNganHangNhan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbNganHangNhan.setText("jLabel28");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSTKNguoiNhan, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(lbTKNhan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbKHNhan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbSDTNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbEmailNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(lbTenNganHangNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbNganHangNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2)))
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSTKNguoiNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbTKNhan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lbKHNhan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lbSDTNhan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(lbEmailNhan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbTenNganHangNhan)
                    .addComponent(lbNganHangNhan))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPShowInfoNhan.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPInfoNhan.add(jPShowInfoNhan, "jPShowInfoNhan");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPInfoGui, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPInfoNhan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPInfoGui, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPInfoNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPShowInfoLayout = new javax.swing.GroupLayout(jPShowInfo);
        jPShowInfo.setLayout(jPShowInfoLayout);
        jPShowInfoLayout.setHorizontalGroup(
            jPShowInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPShowInfoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPShowInfoLayout.setVerticalGroup(
            jPShowInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPShowInfoLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPInfo.add(jPShowInfo, "jPShowInfo");

        spLoading.setValue(50);
        spLoading.setIndeterminate(true);

        javax.swing.GroupLayout jPLoadingLayout = new javax.swing.GroupLayout(jPLoading);
        jPLoading.setLayout(jPLoadingLayout);
        jPLoadingLayout.setHorizontalGroup(
            jPLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPLoadingLayout.createSequentialGroup()
                .addContainerGap(357, Short.MAX_VALUE)
                .addComponent(spLoading, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(351, Short.MAX_VALUE))
        );
        jPLoadingLayout.setVerticalGroup(
            jPLoadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPLoadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spLoading, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPInfo.add(jPLoading, "jPLoading");

        getContentPane().add(jPInfo, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPInfo;
    private javax.swing.JPanel jPInfoGui;
    private javax.swing.JPanel jPInfoNhan;
    private javax.swing.JPanel jPLoading;
    private javax.swing.JPanel jPNoInfoGui;
    private javax.swing.JPanel jPNoInfoNhan;
    private javax.swing.JPanel jPShowInfo;
    private javax.swing.JPanel jPShowInfoGui;
    private javax.swing.JPanel jPShowInfoNhan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbChiTietThongTin;
    private javax.swing.JLabel lbEmailGui;
    private javax.swing.JLabel lbEmailNhan;
    private javax.swing.JLabel lbKHGui;
    private javax.swing.JLabel lbKHNhan;
    private javax.swing.JLabel lbLoaiGD1;
    private javax.swing.JLabel lbMaGD1;
    private javax.swing.JLabel lbMaNV1;
    private javax.swing.JLabel lbNganHangGui;
    private javax.swing.JLabel lbNganHangNhan;
    private javax.swing.JLabel lbNgayGD1;
    private javax.swing.JTextArea lbNoiDung1;
    private javax.swing.JLabel lbNote;
    private javax.swing.JLabel lbSDTGui;
    private javax.swing.JLabel lbSDTNhan;
    private javax.swing.JLabel lbSoTien1;
    private javax.swing.JLabel lbTKGui;
    private javax.swing.JLabel lbTKNhan;
    private javax.swing.JLabel lbTenNV1;
    private javax.swing.JLabel lbTenNganHangGui;
    private javax.swing.JLabel lbTenNganHangNhan;
    private javax.swing.JLabel lbTrangThai1;
    private quanlynganhang.GUI.model.spinner.SpinnerProgress spLoading;
    private javax.swing.JTextField txtSTKNguoiGui;
    private javax.swing.JTextField txtSTKNguoiNhan;
    // End of variables declaration//GEN-END:variables
}
