package client;

import static javafx.util.Duration.seconds;
import javax.swing.JLabel;
import labLib.Star;

public class MoveThread extends Thread{
    public int wasY;
    public int xc;
    public int canvX;
    public int canvY;
    public Star str;
    public MoveThread (Star st) {
        str = st;
    }
    @Override
    public void run () {
        JLabel label = Client.vect.get(str);
        Canvas canv = null;
        //Canvas label = Client.vect.get(str);
        wasY = label.getY();
        canvY = wasY +5;
        xc = label.getX();
        canvX = xc +5;
        for (Canvas can :Client.canvs) {
            if (can.associated.equals(label)) {
                canv = can;
                break;
            }
                             
        }
        int it = wasY+1;
        int waitTime = 50;
        for (int i=0; i<20; i++) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException exc) {}
            canv.setLocation(canvX, it+5);
            label.setLocation(xc, it);
            //scanv.setLocation(xc, it);
            it++;
        }
        int loc = label.getY()-1;
        for (int i=0;i<20;i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException exc) {}
            label.setLocation(xc, loc);
            canv.setLocation(canvX, loc+5);
            loc--;
        }
        Client.filter.setEnabled(true);
        Client.add.setEnabled(true);
        Client.disc.setEnabled(true);
        Client.canc.setVisible(false);
        Client.reqBomb.setVisible(true);
                
    }
}
