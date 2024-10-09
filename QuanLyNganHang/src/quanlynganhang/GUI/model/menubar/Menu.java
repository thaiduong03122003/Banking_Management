package quanlynganhang.GUI.model.menubar;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import quanlynganhang.BUS.LightDarkMode;

public class Menu extends JPanel {

    boolean isAdmin;
    private String menuItems[][];
   
    private void setContentMenu() {
        if (isAdmin) {
            menuItems = new String[][] {
                {"~CHÍNH~"},
                {"Trang chủ"},
                {"Thống kê biến động"},
                {"~THÔNG TIN~"},
                {"Danh sách khách hàng"},
                {"Danh sách nhân viên"},
                {"Danh sách tài khoản KH"},
                {"Danh sách tài khoản NV"},
                {"Danh sách thẻ ngân hàng"},
                {"Danh sách giao dịch"},
                {"~QUYỀN HẠN~"},
                {"Phân quyền cho nhân viên"},
                {"Thêm chức vụ"}
            };
        } else {
            menuItems = new String[][] {
                {"~CHÍNH~"},
                {"Trang chủ"},
                {"Thống kê"},
                {"~THÔNG TIN~"},
                {"Danh sách khách hàng"},
                {"Danh sách tài khoản KH"},
                {"Danh sách thẻ ngân hàng"},
                {"Danh sách giao dịch"},
                {"~DỊCH VỤ PHÁT HÀNH~"},
                {"Mở tài khoản ngân hàng"},
                {"Mở thẻ ngân hàng", "Thẻ ghi nợ", "Thẻ tín dụng"},
                {"~DỊCH VỤ KHÁC~"},
                {"Chuyển tiền", "Nạp tiền vào tài khoản", "Rút tiền khỏi tài khoản", "Chuyển cùng ngân hàng", "Chuyển liên ngân hàng"},
                {"Gửi tiết kiệm"},
                {"Vay vốn", "Cho vay vốn", "Trả khoản vay"},
                // {"Thanh toán tín dụng"}
            };
        }
    }
    public boolean isMenuFull() {
        return menuFull;
    }

    public void setMenuFull(boolean menuFull) {
        this.menuFull = menuFull;
        if (menuFull) {
            header.setText(headerName);
            descriptionInfo.setText(descriptionName);
            header.setHorizontalAlignment(getComponentOrientation().isLeftToRight() ? JLabel.LEFT : JLabel.RIGHT);
        } else {
            header.setText("");
            descriptionInfo.setText("");
            header.setHorizontalAlignment(JLabel.CENTER);
        }
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                ((MenuItem) com).setFull(menuFull);
            }
        }
        lightDarkMode.setMenuFull(menuFull);
    }

    private final List<MenuEvent> events = new ArrayList<>();
    private boolean menuFull = true;
    private final String headerName = "           DANH MỤC";
    private final String descriptionName = "";

    protected final boolean hideMenuTitleOnMinimum = true;
    protected final int menuTitleLeftInset = 5;
    protected final int menuTitleVgap = 5;
    protected final int menuMaxWidth = 250;
    protected final int menuMinWidth = 60;
    protected final int headerFullHgap = 5;

    public Menu(boolean isAdmin) {
        this.isAdmin = isAdmin;
         
        setContentMenu();
        init();
        setInfoMenuHeader();
        System.out.println("Gia tri cua isAdmin: " + this.isAdmin);
    }
 
    private void init() {
        setLayout(new MenuLayout());
        putClientProperty(FlatClientProperties.STYLE, ""
            + "border:20,2,2,2;"
            + "background:$Menu.background;"
            + "arc:10");
        header = new JLabel(headerName);
        descriptionInfo = new JLabel();
        header.putClientProperty(FlatClientProperties.STYLE, ""
            + "font:$Menu.header.font;"
            + "foreground:$Menu.foreground;");
        descriptionInfo.putClientProperty(FlatClientProperties.STYLE, ""
            + "font:$Menu.descriptionInfo.font;"
            + "foreground:$Menu.descriptionInfo.foreground;"
            + "border: 0, 48, 0, 0;");

        //  Menu
        scroll = new JScrollPane();
        panelMenu = new JPanel(new MenuItemLayout(this));
        panelMenu.putClientProperty(FlatClientProperties.STYLE, ""
            + "border:5,5,5,5;"
            + "background:$Menu.background");

        scroll.setViewportView(panelMenu);
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
            + "border:null");
        JScrollBar vscroll = scroll.getVerticalScrollBar();
        vscroll.setUnitIncrement(10);
        vscroll.putClientProperty(FlatClientProperties.STYLE, ""
            + "width:$Menu.scroll.width;"
            + "trackInsets:$Menu.scroll.trackInsets;"
            + "thumbInsets:$Menu.scroll.thumbInsets;"
            + "background:$Menu.ScrollBar.background;"
            + "thumb:$Menu.ScrollBar.thumb");
        createMenu();
        lightDarkMode = new LightDarkMode();
        add(header);
        add(descriptionInfo);
        add(scroll);
        add(lightDarkMode);
    }
    
    private void setInfoMenuHeader() {
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();

        if (hour >= 0 && hour < 12) {
            header.setIcon(new FlatSVGIcon("quanlynganhang/icon/morning.svg"));
        } else if (hour >= 12 && hour < 18) {
            header.setIcon(new FlatSVGIcon("quanlynganhang/icon/afternoon.svg"));
        } else {
            header.setIcon(new FlatSVGIcon("quanlynganhang/icon/night.svg"));
        }
    }

    private void createMenu() {
        int index = 0;
        for (int i = 0; i < menuItems.length; i++) {
            String menuName = menuItems[i][0];
            if (menuName.startsWith("~") && menuName.endsWith("~")) {
                panelMenu.add(createTitle(menuName));
            } else {
                MenuItem menuItem = new MenuItem(this, menuItems[i], index++, events, isAdmin);
                panelMenu.add(menuItem);
            }
        }
    }

    private JLabel createTitle(String title) {
        String menuName = title.substring(1, title.length() - 1);
        JLabel lbTitle = new JLabel(menuName);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
            + "font:$Menu.label.font;"
            + "foreground:$Menu.title.foreground");
        return lbTitle;
    }

    public void setSelectedMenu(int index, int subIndex) {
        runEvent(index, subIndex);
         setSelected(index, subIndex); 
    }

    public void setSelected(int index, int subIndex) {
        int size = panelMenu.getComponentCount();
        for (int i = 0; i < size; i++) {
            Component com = panelMenu.getComponent(i);
            if (com instanceof MenuItem) {
                MenuItem item = (MenuItem) com;
                if (item.getMenuIndex() == index) {
                    item.setSelectedIndex(subIndex);
                } else {
                    item.setSelectedIndex(-1);
                }
            }
        }
    }

    protected void runEvent(int index, int subIndex) {
        MenuAction menuAction = new MenuAction();
        for (MenuEvent event : events) {
            event.menuSelected(index, subIndex, menuAction);
        }
        if (!menuAction.isCancel()) {
            setSelected(index, subIndex);
        }
         
    }

    public void addMenuEvent(MenuEvent event) {
        events.add(event);
    }

    public void hideMenuItem() {
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                ((MenuItem) com).hideMenuItem();
            }
        }
        revalidate();
    }

    public boolean isHideMenuTitleOnMinimum() {
        return hideMenuTitleOnMinimum;
    }

    public int getMenuTitleLeftInset() {
        return menuTitleLeftInset;
    }

    public int getMenuTitleVgap() {
        return menuTitleVgap;
    }

    public int getMenuMaxWidth() {
        return menuMaxWidth;
    }

    public int getMenuMinWidth() {
        return menuMinWidth;
    }

    private JLabel header;
    private JLabel descriptionInfo;
    private JScrollPane scroll;
    private JPanel panelMenu;
    private LightDarkMode lightDarkMode;

    private class MenuLayout implements LayoutManager {

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
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int gap = UIScale.scale(5);
                int sheaderFullHgap = UIScale.scale(headerFullHgap);
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int iconWidth = width;
                int iconHeight = header.getPreferredSize().height;
                int hgap = menuFull ? sheaderFullHgap : 0;

                header.setBounds(x + hgap, y, iconWidth - (hgap * 2), iconHeight);

                int descriptionInfoWidth = iconWidth - (hgap * 2);
                int descriptionInfoHeight = descriptionInfo.getPreferredSize().height;
                int descriptionInfoX = x + hgap;
                int descriptionInfoY = y + iconHeight + gap;
                descriptionInfo.setBounds(descriptionInfoX, descriptionInfoY, descriptionInfoWidth, descriptionInfoHeight);

                int ldgap = UIScale.scale(10);
                int ldWidth = width - ldgap * 2;
                int ldHeight = lightDarkMode.getPreferredSize().height;
                int ldx = x + ldgap;
                int ldy = y + height - ldHeight - ldgap;

                int menux = x;
                int menuy = y + iconHeight + descriptionInfoHeight + gap;
                int menuWidth = width;
                int menuHeight = height - (iconHeight + gap) - (ldHeight + ldgap * 2) - (descriptionInfoHeight + gap);
                scroll.setBounds(menux, menuy, menuWidth, menuHeight);

                lightDarkMode.setBounds(ldx, ldy, ldWidth, ldHeight);
            }
        }
    }
}
