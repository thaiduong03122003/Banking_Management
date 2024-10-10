/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.GUI;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import quanlynganhang.BUS.ChiaQuyenBUS;
import quanlynganhang.BUS.ChucVuBUS;
import quanlynganhang.BUS.DieuHuongMenuBUS;
import quanlynganhang.BUS.NhanVienBUS;
import quanlynganhang.DTO.ChucVuDTO;
import quanlynganhang.DTO.NhanVienDTO;
import quanlynganhang.DTO.TaiKhoanNVDTO;
import quanlynganhang.GUI.adminUI.ApplicationAdmin;
import quanlynganhang.GUI.model.menubar.Menu;
import quanlynganhang.GUI.model.headerbar.HeaderBar;

public class MainForm extends JLayeredPane {
    private Application app;
    private ApplicationAdmin appAdmin;
    private TaiKhoanNVDTO taiKhoanNV;
    private NhanVienBUS nhanVienBUS;
    private ChucVuBUS chucVuBUS;
    private int quyenSua;
    
    public MainForm(Menu menu, DieuHuongMenuBUS menuBUS, TaiKhoanNVDTO taiKhoanNV, Application app, ApplicationAdmin appAdmin, int maDangNhap, boolean isAdmin) {
        this.app = app;
        this.appAdmin = appAdmin;
        this.menu = menu;
        this.menuBUS = menuBUS;
        this.taiKhoanNV = taiKhoanNV;
        
        nhanVienBUS = new NhanVienBUS();
        chucVuBUS = new ChucVuBUS();
        
        init(maDangNhap, isAdmin);
    }

    private void init(int maDangNhap, boolean isAdmin) {
        initData();
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(new MainFormLayout());
        headerBar = new HeaderBar(isAdmin, maDangNhap, taiKhoanNV, app, appAdmin, quyenSua, 0);
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
            + "background:$Menu.button.background;"
            + "arc:999;"
            + "focusWidth:0;"
            + "borderWidth:0");
        menuButton.addActionListener((ActionEvent e) -> {
            setMenuFull(!menu.isMenuFull());
        });
        menuBUS.initMenuEvent();
        setLayer(menuButton, JLayeredPane.POPUP_LAYER);
        add(menuButton);
        add(menu);
        add(headerBar);
        add(panelBody);
        setMenuFull(false);
    }
    
    private void initData() {
        NhanVienDTO nhanVien = nhanVienBUS.getNhanVienById(taiKhoanNV.getMaNhanVien(), 0);

        ChucVuDTO chucVu = chucVuBUS.getChucVuById(nhanVien.getMaChucVu());
        
        quyenSua = ChiaQuyenBUS.splitQuyen(chucVu.getqLTKNhanVien(), 3);
    }

    @Override
    public void applyComponentOrientation(ComponentOrientation o) {
        super.applyComponentOrientation(o);
        initMenuArrowIcon();
    }

    private void initMenuArrowIcon() {
        if (menuButton == null) {
            menuButton = new JButton();
        }
        String icon = (getComponentOrientation().isLeftToRight()) ? "menu_left.svg" : "menu_right.svg";
        menuButton.setIcon(new FlatSVGIcon("quanlynganhang/icon/" + icon, 0.8f));
    }

    private void setMenuFull(boolean full) {
        String icon;
        if (getComponentOrientation().isLeftToRight()) {
            icon = (full) ? "menu_left.svg" : "menu_right.svg";
        } else {
            icon = (full) ? "menu_right.svg" : "menu_left.svg";
        }
        menuButton.setIcon(new FlatSVGIcon("quanlynganhang/icon/" + icon, 0.8f));
        menu.setMenuFull(full);
        revalidate();
    }

    public void hideMenu() {
        menu.hideMenuItem();
    }

    public void showForm(Component component, String titleName) {
        headerBar.setTitleName(titleName);
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public Menu menu;
    private HeaderBar headerBar;
    private JPanel panelBody;
    private JButton menuButton;
    private DieuHuongMenuBUS menuBUS;

    private class MainFormLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, 5);
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                boolean ltr = parent.getComponentOrientation().isLeftToRight();
                Insets insets = UIScale.scale(parent.getInsets());
                int x = insets.left;
                int y = insets.top;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
                int menuX = ltr ? x : x + width - menuWidth;
                menu.setBounds(menuX, y, menuWidth, height);
                int menuButtonWidth = menuButton.getPreferredSize().width;
                int menuButtonHeight = menuButton.getPreferredSize().height;
                int menubX;
                if (ltr) {
                    menubX = (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)));
                } else {
                    menubX = (int) (menuX - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));
                }
                menuButton.setBounds(menubX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);
                int gap = UIScale.scale(5);
                int headerBarX = x + menu.getMenuMinWidth() + gap * 2;
                int headerBarY = y;
                int headerWidth = width - menu.getMenuMinWidth() - gap * 3;
                int bodyWidth = width - menu.getMenuMinWidth() - gap * 2;
                int bodyHeight = height - headerBar.getPreferredSize().height - gap;
                int bodyX = x + menu.getMenuMinWidth() + gap;
                int bodyY = y + headerBar.getPreferredSize().height + gap;
                headerBar.setBounds(headerBarX, headerBarY, headerWidth, headerBar.getPreferredSize().height);
                panelBody.setBounds(bodyX, bodyY, bodyWidth, bodyHeight);
            }
        }
    }
}
