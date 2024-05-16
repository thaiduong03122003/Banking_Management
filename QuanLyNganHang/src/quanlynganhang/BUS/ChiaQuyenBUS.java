package quanlynganhang.BUS;

import quanlynganhang.GUI.model.message.MessageBox;

public class ChiaQuyenBUS {
    
    public static int splitQuyen(String quyen, int index) {
        String[] parts = quyen.split("-");
        return Integer.parseInt(parts[index]);
    }
    
    public static void showError() {
        MessageBox.showErrorMessage(null, "Bạn không có quyền thực hiện!");
    }
}
