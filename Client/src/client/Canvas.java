package client; 

import java.awt.Color; 
import javax.swing.JLabel; 
import org.jdesktop.swingx.*; 
import org.jdesktop.swingx.border.DropShadowBorder; 
public class Canvas extends JXPanel{ 
public final JLabel associated;
public Canvas(Color clr, int shine, JLabel s){
    this.associated = s;
DropShadowBorder shadow = new DropShadowBorder(); 
shadow.setShadowSize(24); 
shadow.setShadowOpacity(shine/5000f); 
shadow.setShadowColor(clr); 
shadow.setShowLeftShadow(true); 
shadow.setShowRightShadow(true); 
shadow.setShowBottomShadow(true); 
shadow.setShowTopShadow(true); 
this.setBackground(new Color(0,0,0));  
this.setBorder(shadow); 
this.setSize(30, 30);
this.repaint();
} 
}
