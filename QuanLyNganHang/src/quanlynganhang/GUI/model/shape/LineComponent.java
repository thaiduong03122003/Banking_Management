/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlynganhang.GUI.model.shape;

import java.awt.*;
import javax.swing.JPanel;

/**
 *
 * @author THAI
 */
public class LineComponent extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setStroke(new BasicStroke(2));

        g2d.setColor(Color.GRAY);

        g2d.drawLine(0, 17, 80, 17);
    }
}
