package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import quanlynganhang.BUS.GiaoDichBUS;
import quanlynganhang.BUS.KhachHangBUS;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.BUS.TaiKhoanKHBUS;
import quanlynganhang.BUS.TaiKhoanNVBUS;
import quanlynganhang.DTO.KhachHangDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanKHDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.adminUI.FormPhanQuyen;
import quanlynganhang.GUI.adminUI.JFrameChiTietTKNV;
import quanlynganhang.GUI.adminUI.JFrameThemTKNV2;
import quanlynganhang.GUI.model.message.MessageBox;
import quanlynganhang.GUI.FormMoTheTinDung;
import quanlynganhang.GUI.adminUI.FormThongKe;
import quanlynganhang.GUI.model.textfield.SearchOptinEvent;
import quanlynganhang.GUI.model.textfield.SearchOption;

public class JDialogTableChonItem extends javax.swing.JDialog {

    private String loaiDanhSach;
    private NhanVienBUS nhanVienBUS;
    private KhachHangBUS khachHangBUS;
    private TaiKhoanNVBUS taiKhoanNVBUS;
    private TaiKhoanKHBUS taiKhoanKHBUS;
    private GiaoDichBUS giaoDichBUS;
    private JFrameThemTKNV2 themTKNV2;
    private FormPhanQuyen phanQuyen;
    private FormMoTaiKhoan moTaiKhoan;
    private FormNapTien napTien;
    private FormRutTien rutTien;
    private FormChuyenCungNganHang chuyenCungNganHang;
    private FormChuyenLienNganHang chuyenLienNganHang;
    private FormMoTKTietKiem formMoTKTietKiem;
    private FormChoVayVon formChoVayVon;
    private FormMoTheGhiNo formMoTheGhiNo;
    private FormMoTheTinDung formMoTheTinDung;
    private JFrameBoLocDSThe jFrameBoLocDSThe;
    private JFrameBoLocGD jFrameBoLocGD;
    private FormTraKhoanVay formTraKhoanVay;
    private FormTongQuan formTongQuan;
    private boolean isSearched;

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, JFrameThemTKNV2 themTKNV2, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.themTKNV2 = themTKNV2;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormThongKe fromThongKe, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.phanQuyen = phanQuyen;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormPhanQuyen phanQuyen, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.phanQuyen = phanQuyen;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormMoTaiKhoan moTaiKhoan, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.moTaiKhoan = moTaiKhoan;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormNapTien napTien, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.napTien = napTien;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormRutTien rutTien, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.rutTien = rutTien;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormChuyenCungNganHang chuyenCungNganHang, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.chuyenCungNganHang = chuyenCungNganHang;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormChuyenLienNganHang chuyenLienNganHang, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.chuyenLienNganHang = chuyenLienNganHang;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormMoTKTietKiem formMoTKTietKiem, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.formMoTKTietKiem = formMoTKTietKiem;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormChoVayVon formChoVayVon, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.formChoVayVon = formChoVayVon;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormMoTheGhiNo formMoTheGhiNo, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.formMoTheGhiNo = formMoTheGhiNo;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, JFrameBoLocDSThe jFrameBoLocDSThe, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.jFrameBoLocDSThe = jFrameBoLocDSThe;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormMoTheTinDung formMoTheTinDung, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.formMoTheTinDung = formMoTheTinDung;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, JFrameBoLocGD jFrameBoLocGD, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.jFrameBoLocGD = jFrameBoLocGD;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
        initSelectButton(canSelect);
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormTraKhoanVay formTraKhoanVay, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.formTraKhoanVay = formTraKhoanVay;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    public JDialogTableChonItem(java.awt.Frame parent, boolean modal, FormTongQuan formTongQuan, String title, String loaiDanhSach, boolean canSelect) {
        super(parent, modal);
        this.formTongQuan = formTongQuan;
        this.loaiDanhSach = loaiDanhSach;
        this.setTitle(title);

        initComponents();
        initSelectButton(canSelect);

        switchTable();
    }

    //LOAD DANH SÁCH
    public void loadDSRutTien() {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = giaoDichBUS.doiSangObjectGiaoDichTK(false, null, 3);
        String[] title = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Tên trạng thái"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSNapTien() {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = giaoDichBUS.doiSangObjectGiaoDichTK(false, null, 4);
        String[] title = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Tên trạng thái"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSGuiTietKiem() {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = giaoDichBUS.doiSangObjectGiaoDichTK(false, null, 5);
        String[] title = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Tên trạng thái"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }
    
    public void loadDSGDVayVon() {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = giaoDichBUS.doiSangObjectGiaoDichTK(false, null, 7);
        String[] title = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Tên trạng thái"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSGiaoDich() {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = giaoDichBUS.doiSangObjectGiaoDichTK(false, giaoDichBUS.getDSGiaoDich(), 0);
        String[] title = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Tên trạng thái"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSMaxGiiaoDich() throws Exception {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = giaoDichBUS.doiSangObjectMaxGiaoDich();
        String[] title = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Tên trạng thái"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSVayVon() throws Exception {
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = giaoDichBUS.doiSangObjectGiaoDichTK(false, null, 7);
        String[] title = {"Mã giao dịch", "Số tài khoản", "Tên khách hàng", "Số tiền", "Ngày giao dịch", "Loại giao dịch", "Tên nhân viên", "Tên trạng thái"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSNhanVien(boolean isSearched, List<NhanVienDTO> list) {
        this.isSearched = isSearched;
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = isSearched ? nhanVienBUS.doiSangObjectNhanVien(0, false, isSearched, list) : nhanVienBUS.doiSangObjectNhanVien(0, false, isSearched, null);
        String[] title = {"Mã nhân viên", "Họ đệm", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Mã Căn cước công dân", "Chức vụ"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSKhachHang(boolean isSearched, List<KhachHangDTO> list) {
        this.isSearched = isSearched;
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = isSearched ? khachHangBUS.doiSangObjectKhachHang(0, false, isSearched, list) : khachHangBUS.doiSangObjectKhachHang(0, false, isSearched, null);

        String[] title = {"Mã khách hàng", "Họ đệm", "Tên", "Giới tính", "Ngày sinh", "Địa chỉ", "Email", "Số điện thoại", "Mã Căn cước công dân", "Nợ xấu"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSTaiKhoanNV(boolean isSearched, List<TaiKhoanNVDTO> list) {
        this.isSearched = isSearched;
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = isSearched ? taiKhoanNVBUS.doiSangObjectTaiKhoanNV(false, isSearched, list) : taiKhoanNVBUS.doiSangObjectTaiKhoanNV(false, isSearched, null);
        String[] title = {"Mã tài khoản", "Họ tên nhân viên", "Tên đăng nhập", "Ngày tạo", "Trạng thái tài khoản", "Trạng thái hoạt động"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSTaiKhoanKH(boolean isSearched, List<TaiKhoanKHDTO> list) {
        this.isSearched = isSearched;
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = isSearched ? taiKhoanKHBUS.doiSangObjectTaiKhoanKH(false, isSearched, list, 0) : taiKhoanKHBUS.doiSangObjectTaiKhoanKH(false, isSearched, null, 0);
        String[] title = {"Mã tài khoản", "Số tài khoản", "Tên tài khoản", "Tên khách hàng", "Số dư", "Ngày tạo", "Loại tài khoản", "Trạng thái tài khoản"};

        model.setDataVector(dataModel, title);
        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void loadDSTaiKhoanVay(boolean isSearched, List<TaiKhoanKHDTO> list) {
        this.isSearched = isSearched;
        DefaultTableModel model = (DefaultTableModel) jTableDS.getModel();
        model.setRowCount(0);

        Object[][] dataModel = isSearched ? taiKhoanKHBUS.doiSangObjectTaiKhoanKH(false, isSearched, list, 4) : taiKhoanKHBUS.doiSangObjectTaiKhoanKH(false, isSearched, null, 4);
        String[] title = {"Mã tài khoản", "Số tài khoản", "Tên tài khoản", "Tên khách hàng", "Số dư trong tài khoản vay", "Ngày tạo khoản vay", "Loại tài khoản", "Trạng thái tài khoản"};
        model.setDataVector(dataModel, title);

        jTableDS.setDefaultEditor(Object.class, null);
    }

    public void switchTable() {
        try {
            if (loaiDanhSach.equals("DSNV")) {
                nhanVienBUS = new NhanVienBUS();

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

                loadDSNhanVien(false, null);

            } else if (loaiDanhSach.equals("DSTKNV")) {
                taiKhoanNVBUS = new TaiKhoanNVBUS();

                txtSearchData.addEventOptionSelected(new SearchOptinEvent() {
                    @Override
                    public void optionSelected(SearchOption option, int index) {
                        txtSearchData.setHint("Tìm kiếm theo " + option.getName() + "...");
                    }
                });

                txtSearchData.addOption(new SearchOption("họ tên nhân viên", new FlatSVGIcon("quanlynganhang/icon/searchData_name.svg")));
                txtSearchData.addOption(new SearchOption("tên đăng nhập", new FlatSVGIcon("quanlynganhang/icon/searchData_accNum.svg")));
                txtSearchData.setSelectedIndex(0);

                loadDSTaiKhoanNV(false, null);

            } else if (loaiDanhSach.equals("DSKH")) {
                khachHangBUS = new KhachHangBUS();

                txtSearchData.addEventOptionSelected(new SearchOptinEvent() {
                    @Override
                    public void optionSelected(SearchOption option, int index) {
                        txtSearchData.setHint("Tìm kiếm theo " + option.getName() + "...");
                    }
                });

                txtSearchData.addOption(new SearchOption("họ tên khách hàng", new FlatSVGIcon("quanlynganhang/icon/searchData_name.svg")));
                txtSearchData.addOption(new SearchOption("số điện thoại", new FlatSVGIcon("quanlynganhang/icon/searchData_phoneNum.svg")));
                txtSearchData.addOption(new SearchOption("email", new FlatSVGIcon("quanlynganhang/icon/searchData_email.svg")));
                txtSearchData.addOption(new SearchOption("mã căn cước", new FlatSVGIcon("quanlynganhang/icon/searchData_cccd.svg")));
                txtSearchData.setSelectedIndex(0);

                loadDSKhachHang(false, null);

            } else if (loaiDanhSach.equals("DSTKKH")) {
                taiKhoanKHBUS = new TaiKhoanKHBUS();

                txtSearchData.addEventOptionSelected(new SearchOptinEvent() {
                    @Override
                    public void optionSelected(SearchOption option, int index) {
                        txtSearchData.setHint("Tìm kiếm theo " + option.getName() + "...");
                    }
                });

                txtSearchData.addOption(new SearchOption("họ tên khách hàng hoặc tên tài khoản", new FlatSVGIcon("quanlynganhang/icon/searchData_name.svg")));
                txtSearchData.addOption(new SearchOption("số tài khoản", new FlatSVGIcon("quanlynganhang/icon/searchData_cccd.svg")));
                txtSearchData.setSelectedIndex(0);

                loadDSTaiKhoanKH(false, null);

            } else if (loaiDanhSach.equals("DSTKV")) {
                taiKhoanKHBUS = new TaiKhoanKHBUS();

                txtSearchData.addEventOptionSelected(new SearchOptinEvent() {
                    @Override
                    public void optionSelected(SearchOption option, int index) {
                        txtSearchData.setHint("Tìm kiếm theo " + option.getName() + "...");
                    }
                });

                txtSearchData.addOption(new SearchOption("họ tên khách hàng hoặc tên tài khoản", new FlatSVGIcon("quanlynganhang/icon/searchData_name.svg")));
                txtSearchData.addOption(new SearchOption("số tài khoản", new FlatSVGIcon("quanlynganhang/icon/searchData_cccd.svg")));
                txtSearchData.setSelectedIndex(0);

                loadDSTaiKhoanVay(false, null);

            } else if (loaiDanhSach.equals("DSRT")) {
                disableControl();
                giaoDichBUS = new GiaoDichBUS();
                loadDSRutTien();
            } else if (loaiDanhSach.equals("DSNT")) {
                disableControl();
                giaoDichBUS = new GiaoDichBUS();
                loadDSNapTien();
            } else if (loaiDanhSach.equals("DSGTK")) {
                disableControl();
                giaoDichBUS = new GiaoDichBUS();
                loadDSGuiTietKiem();
                } else if (loaiDanhSach.equals("DSGDVV")) {
                disableControl();
                giaoDichBUS = new GiaoDichBUS();
                loadDSGDVayVon();
            } else if (loaiDanhSach.equals("DSGD")) {
                disableControl();
                giaoDichBUS = new GiaoDichBUS();
                loadDSGiaoDich();
            } else if (loaiDanhSach.equals("DSMGD")) {
                disableControl();
                giaoDichBUS = new GiaoDichBUS();
                loadDSMaxGiiaoDich();
            } else if (loaiDanhSach.equals("DSVV")) {
                disableControl();
                giaoDichBUS = new GiaoDichBUS();
                loadDSVayVon();
            } else {
                MessageBox.showErrorMessage(null, "Không tìm thấy danh sách được chọn!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MessageBox.showErrorMessage(null, "load danh sách thất bại!");
        }
    }

    public void guiMaTaiKhoanKH(int maTaiKhoanKH) {
        if (napTien != null) {
            napTien.dienThongTinTKKH(maTaiKhoanKH);
            this.dispose();
        } else if (rutTien != null) {
            rutTien.dienThongTinTKKH(maTaiKhoanKH);
            this.dispose();
        } else if (chuyenCungNganHang != null) {
            chuyenCungNganHang.dienTKNguoiGui(maTaiKhoanKH);
            this.dispose();
        } else if (chuyenLienNganHang != null) {
            chuyenLienNganHang.dienTKNguoiGui(maTaiKhoanKH);
            this.dispose();
        } else if (formMoTheGhiNo != null) {
            formMoTheGhiNo.dienThongTinTKKH(maTaiKhoanKH);
            this.dispose();
        } else if (formMoTheTinDung != null) {
            formMoTheTinDung.dienThongTinTKKH(maTaiKhoanKH);
            this.dispose();
        } else if (formTongQuan != null) {
            MessageBox.showErrorMessage(null, "Bạn không thể xem chi tiết!");
        }
    }

    private void initSelectButton(boolean canSelect) {
        if (canSelect) {
            btnChon.setVisible(true);
        } else {
            btnChon.setVisible(false);
        }
    }

    private void searchData() {
        if (loaiDanhSach.equals("DSNV")) {
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
                    return;
            }

            List<NhanVienDTO> listNV = nhanVienBUS.timKiemTheoLoai(0, typeName, txtSearchData.getText().trim());

            if (listNV != null && !listNV.isEmpty()) {
                loadDSNhanVien(true, listNV);
                return;
            }
            showError();

        } else if (loaiDanhSach.equals("DSTKNV")) {
            int option = txtSearchData.getSelectedIndex();
            String typeName = option == 0 ? "name" : "username";

            List<TaiKhoanNVDTO> listTKNV = taiKhoanNVBUS.timKiemTheoLoai(typeName, txtSearchData.getText().trim());
            if (listTKNV != null && !listTKNV.isEmpty()) {
                loadDSTaiKhoanNV(true, listTKNV);
                return;
            }
            showError();

        } else if (loaiDanhSach.equals("DSKH")) {
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
                    return;
            }

            List<KhachHangDTO> listKH = khachHangBUS.timKiemTheoLoai(0, typeName, txtSearchData.getText().trim());

            if (listKH != null && !listKH.isEmpty()) {
                loadDSKhachHang(true, listKH);
                return;
            }
            showError();

        } else if (loaiDanhSach.equals("DSTKKH")) {
            int option = txtSearchData.getSelectedIndex();
            String typeName = option == 0 ? "name" : "accountNum";

            List<TaiKhoanKHDTO> listTKKH = taiKhoanKHBUS.timKiemTheoLoai(typeName, txtSearchData.getText().trim());
            if (listTKKH != null && !listTKKH.isEmpty()) {
                loadDSTaiKhoanKH(true, listTKKH);
                return;
            }
            showError();
        } else if (loaiDanhSach.equals("DSTKV")) {
            int option = txtSearchData.getSelectedIndex();
            String typeName = option == 0 ? "name" : "accountNum";

            List<TaiKhoanKHDTO> listTKKH = taiKhoanKHBUS.timKiemTheoLoai(typeName, txtSearchData.getText().trim());
            if (listTKKH != null && !listTKKH.isEmpty()) {
                loadDSTaiKhoanKH(true, listTKKH);
                return;
            }
            showError();
        }
    }

    private void showError() {
        MessageBox.showErrorMessage(null, "Không tìm thấy thông tin nào!");
    }

    private void resetTable() {
        if (loaiDanhSach.equals("DSNV")) {
            loadDSNhanVien(false, null);

        } else if (loaiDanhSach.equals("DSTKNV")) {
            loadDSTaiKhoanNV(false, null);

        } else if (loaiDanhSach.equals("DSKH")) {
            loadDSKhachHang(false, null);

        } else if (loaiDanhSach.equals("DSTKKH")) {
            loadDSTaiKhoanKH(false, null);
        }
    }

    private void disableControl() {
        txtSearchData.setVisible(false);
        btnSearch.setVisible(false);
        btnReset.setVisible(false);
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
        txtSearchData = new quanlynganhang.GUI.model.textfield.TextFieldSearchOption();
        btnSearch = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnChon = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDS = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        txtSearchData.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchDataKeyReleased(evt);
            }
        });

        btnSearch.setIcon(new FlatSVGIcon("quanlynganhang/icon/search_btn.svg"));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnReset.setIcon(new FlatSVGIcon("quanlynganhang/icon/reload_btn.svg"));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSearchData, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 370, Short.MAX_VALUE)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtSearchData, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jButton1.setText("Hủy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnChon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnChon.setText("Chọn");
        btnChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(333, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(btnChon)
                .addContainerGap(317, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnChon))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jTableDS.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableDS);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonActionPerformed
        int selectedRow = jTableDS.getSelectedRow();
        if (selectedRow == -1) {
            MessageBox.showErrorMessage(null, "Vui lòng chọn dữ liệu!");
            return;
        } else {

            Object idObj = jTableDS.getValueAt(selectedRow, 0);
            if (idObj != null) {
                int id = Integer.parseInt(idObj.toString());

                if (loaiDanhSach.equals("DSNV")) {

                    if (themTKNV2 != null) {
                        themTKNV2.dienThongTinNV(id);
                    } else if (phanQuyen != null) {
                        phanQuyen.dienThongTin(id);
                    } else if (jFrameBoLocGD != null) {
                        jFrameBoLocGD.dienIdNV(id);
                    }
                    this.dispose();

                } else if (loaiDanhSach.equals("DSTKNV")) {
                    TaiKhoanNVDTO taiKhoanNV = new TaiKhoanNVDTO();
                    taiKhoanNV = taiKhoanNVBUS.getTaiKhoanNVById(id);
                    if (taiKhoanNV == null) {
                        MessageBox.showErrorMessage(null, "Mã tài khoản nhân viên không tồn tại!");
                        return;
                    } else {
//                        themTKNV2.dienThongTinNV(nhanVien);
                    }

                } else if (loaiDanhSach.equals("DSKH")) {

                    if (moTaiKhoan != null) {
                        moTaiKhoan.dienThongTinKH(id);
                    } else if (formMoTKTietKiem != null) {
                        formMoTKTietKiem.dienThongTinKH(id);
                    } else if (formChoVayVon != null) {
                        formChoVayVon.dienThongTinKH(id);
                    } else if (jFrameBoLocDSThe != null) {
                        jFrameBoLocDSThe.dienIdKH(id);
                    } else if (jFrameBoLocGD != null) {
                        jFrameBoLocGD.dienIdKH(id);
                    } else if (formTongQuan != null) {
                        MessageBox.showErrorMessage(null, "Bạn không thể xem chi tiết!");
                    }
                    this.dispose();

                } else if (loaiDanhSach.equals("DSTKKH")) {
                    JDialogXacNhanChon xacNhanChon = new JDialogXacNhanChon(null, true, this, id);
                    xacNhanChon.setDefaultCloseOperation(JDialogXacNhanChon.DISPOSE_ON_CLOSE);
                    xacNhanChon.setVisible(true);
                } else if (loaiDanhSach.equals("DSTKV")) {
                    if (formTraKhoanVay != null) {
                        formTraKhoanVay.getMaTaiKhoanVay(id);
                        this.dispose();
                    }
                }

            }
        }
    }//GEN-LAST:event_btnChonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        if (txtSearchData.getText().trim().isEmpty()) {
            MessageBox.showErrorMessage(null, "Vui lòng nhập thông tin cần tìm!");
            return;
        }
        searchData();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        resetTable();
        txtSearchData.setText("");
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtSearchDataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchDataKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            btnSearchActionPerformed(null);
        }
    }//GEN-LAST:event_txtSearchDataKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChon;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDS;
    private quanlynganhang.GUI.model.textfield.TextFieldSearchOption txtSearchData;
    // End of variables declaration//GEN-END:variables
}
