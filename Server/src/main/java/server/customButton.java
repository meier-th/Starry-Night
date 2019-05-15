package server;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
public class customButton extends JButton{
    private final Color hoverBackgroundColor = Color.ORANGE;
    private final Color pressedBackgroundColor = Color.GRAY;
    public customButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        this.setFocusPainted(false);
    }
    @Override
    protected void paintComponent(Graphics gr) {
        if (getModel().isPressed()) {
                gr.setColor(pressedBackgroundColor);
            } else if (getModel().isRollover()) {
                gr.setColor(hoverBackgroundColor);
            } else {
                gr.setColor(getBackground());
            }
            gr.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(gr);
    }
    
}
