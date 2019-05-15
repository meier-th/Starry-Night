package client;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    Image background;
    int Width;
    int Height;
    public ImagePanel(Image image) {
    this.background = image;
    this.Width = image.getWidth(this)/2;
    this.Height = image.getHeight(this)/2;
}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = this.getParent().getWidth()/2 - Width;
        int y = this.getParent().getHeight()/2 - Height;
        g.drawImage(background,x,y,this);
    }
}
