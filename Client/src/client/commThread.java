package client;

import com.google.gson.Gson;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import labLib.Star;

public class commThread extends Thread{
    @Override
    public void run() {
        while (true) {
            try {
                byte[]forMess = new byte[1024];
                Client.ios.read(forMess);
                String message = new String(forMess);
                message = message.trim();
                //System.out.println(message);
                if (message.equals("banned")) {
                    JOptionPane pane = new JOptionPane("You are banned, LOL");
                    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    JDialog dial = pane.createDialog(Client.frame, "Banned");
                    dial.setVisible(true);
                    System.exit(0);
                }
                
                else if (message.equals("star")) {
                    Client.oos.write("a".getBytes());
                    Client.oos.flush();
                    byte[] get = new byte[1024];
                    Client.ios.read(get);
                    ByteArrayInputStream bis = new ByteArrayInputStream(get);
                    ObjectInputStream oInStr = new ObjectInputStream(bis);
                    Object obj = oInStr.readObject();
                    Star str = (Star) obj;
                    
                    ImageIcon createdStar;
                    if (str.getColour().equals(Color.WHITE)) 
            createdStar = new ImageIcon("Resources/wc.jpg");
        else if (str.getColour().equals(Color.ORANGE)) 
            createdStar = new ImageIcon("Resources/oc.jpg");
        else if (str.getColour().equals(Color.YELLOW))
            createdStar = new ImageIcon("Resources/yc.jpg");
        else
            createdStar = new ImageIcon("Resources/rc.jpg");
                    JLabel move = new JLabel(createdStar);
                    move.setSize(new Dimension(40,40));
                    move.setBackground(new Color(0,0,0));
                    Client.space.add(move);
                    Insets insets = Client.space.getInsets();
                    move.setLocation(str.getCoordinates()[0]-20+insets.left+450, str.getCoordinates()[1]-20+insets.top+360);
                    
                    String line1 = "name: "+str.toString()+", shine: "+String.valueOf(str.getShine());
                    String line2 = "Visible from Earth: "+String.valueOf(str.visibleFromEarth());
                    String line3 = "Visible from Moon: "+String.valueOf(str.visibleFromMoon());
                    String line4 = "Coordinates: X:"+String.valueOf(str.getCoordinates()[0])+", Y:"+String.valueOf(str.getCoordinates()[1]);
                    move.setToolTipText("<html> "+line1+"<br>"+line2+"<br>"+line3+"<br>"+line4+"</html>");
                    Canvas canv = new Canvas(str.getColour(),str.getShine(),move);
                    canv.setLocation(str.getCoordinates()[0]-15+insets.left+450, str.getCoordinates()[1]-15+insets.top+360);
                    Client.canvs.add(canv);
                    Client.space.add(canv);
                    //Client.vect.put(str, move);
                    Client.vect.put(str, move);
                } else if (message.equals("collection")) {
                    Client.oos.write("a".getBytes());
                    Client.oos.flush();
                    byte[]size = new byte[16];
                    Client.ios.read(size);
                    Client.oos.write("a".getBytes());
                    Client.oos.flush();
                    int number = Integer.parseInt(new String(size).trim());
                    for (int i=0; i<number; i++) {
                        byte[] get = new byte[1024];
                        Client.ios.read(get);
                        Client.oos.write("a".getBytes());
                        Client.oos.flush();
                        ByteArrayInputStream bis = new ByteArrayInputStream(get);
                        ObjectInputStream oInStr = new ObjectInputStream(bis);
                        Object obj = oInStr.readObject();
                        Star str = (Star) obj;
                        
                        ImageIcon createdStar;
                        if (str.getColour().equals(Color.WHITE)) 
                            createdStar = new ImageIcon("Resources/wc.jpg");
                        else if (str.getColour().equals(Color.ORANGE)) 
                            createdStar = new ImageIcon("Resources/oc.jpg");
                        else if (str.getColour().equals(Color.YELLOW))
                            createdStar = new ImageIcon("Resources/yc.jpg");
                        else
                            createdStar = new ImageIcon("Resources/rc.jpg");
                        JLabel move = new JLabel(createdStar);
                        move.setSize(new Dimension(40,40));
                        move.setBackground(new Color(0,0,0));
                        Client.space.add(move);
                        Insets insets = Client.space.getInsets();
                        String line1 = "name: "+str.toString()+", shine: "+String.valueOf(str.getShine());
                    String line2 = "Visible from Earth: "+String.valueOf(str.visibleFromEarth());
                    String line3 = "Visible from Moon: "+String.valueOf(str.visibleFromMoon());
                    String line4 = "Coordinates: X:"+String.valueOf(str.getCoordinates()[0])+", Y:"+String.valueOf(str.getCoordinates()[1]);
                    move.setToolTipText("<html> "+line1+"<br>"+line2+"<br>"+line3+"<br>"+line4+"</html>");
                        Canvas canv = new Canvas(str.getColour(),str.getShine(),move);
                        Client.canvs.add(canv);
                        //canv.setSize(40,40);
                        move.setLocation(str.getCoordinates()[0]-20+insets.left+450, str.getCoordinates()[1]-20+insets.top+360);
                        canv.setLocation(str.getCoordinates()[0]-15+insets.left+450, str.getCoordinates()[1]-15+insets.top+360);
                        //Client.vect.put(str, move);
                        Client.space.add(canv);
                        Client.vect.put(str, move);
                    }
                    
                }
                
                else if (message.equals("delete")) {
                    Client.oos.write("which one?".getBytes());
                    Client.oos.flush();
                    
                    byte[]get = new byte[1024];
                    Client.ios.read(get);
                    ByteArrayInputStream bis = new ByteArrayInputStream(get);
                    ObjectInputStream oInStr = new ObjectInputStream(bis);
                    Object obj = oInStr.readObject();
                    Star str = (Star) obj;
                    Star rem = null;
                    //System.out.println(str==null);
                    for (Star st :Client.vect.keySet()) {
                        if (st.toString().equals(str.toString())&&(st.getCoordinates()[0]==str.getCoordinates()[0])&&(st.getCoordinates()[1]==str.getCoordinates()[1])) 
                            rem = st;
                    }
                    for (Canvas can :Client.canvs) {
                            if (can.associated.equals(Client.vect.get(rem))) {
                             Client.space.remove(can);
                             Client.canvs.remove(can);
                             break;
                            }
                        }
                    Client.space.remove(Client.vect.get(rem));
                            Client.vect.remove(rem);
                            Client.space.repaint();
                 }
                else if (message.equals("clear")) {
                    Client.vect.clear();
                    Client.canvs.clear();
                    Client.space.removeAll();
                    Client.space.repaint();
                }
                else if (!message.equals("") && message.charAt(0)=='{') {
                    String oldSt = message.substring(0, message.indexOf("}!{")+1);
                    String newSt = message.substring(oldSt.length()+1, message.length());
                    //System.out.println(oldSt);
                    //System.out.println(newSt);
                    Gson gson = new Gson();
                    Star old = gson.fromJson(oldSt, labLib.Star.class);
                    Star news = gson.fromJson(newSt, labLib.Star.class);
                    //System.out.println(old==null);
                    //System.out.println(news==null);
                    Star selected = null;
                    //System.out.println(Client.vect.size());
                    for (Star st : Client.vect.keySet()) {
                        //System.out.println(st.toString());
                        if (st.toString().equals(old.toString())&&(st.getCoordinates()[0]==old.getCoordinates()[0])&&(st.getCoordinates()[1]==old.getCoordinates()[1])) {
                            selected = st;
                            break;
                            //System.out.println("found!");
                        }
                    }
                        Client.space.remove(Client.vect.get(selected));
                        for (Canvas can :Client.canvs) {
                            if (can.associated.equals(Client.vect.get(selected))) {
                             Client.space.remove(can);
                             Client.canvs.remove(can);
                             break;
                            }
                        }
                        
                        Client.vect.remove(selected);
                        Client.space.repaint();
                        ImageIcon newIc;
                        if (news.getColour().equals(Color.WHITE)) 
                            newIc = new ImageIcon("Resources/wc.jpg");
                        else if (news.getColour().equals(Color.ORANGE)) 
                            newIc = new ImageIcon("Resources/oc.png");
                        else if (news.getColour().equals(Color.YELLOW))
                            newIc = new ImageIcon("Resources/yc.jpg");
                        else
                            newIc = new ImageIcon("Resources/rc.png");
                        JLabel move = new JLabel(newIc);
                        move.setSize(new Dimension(40,40));
                        move.setBackground(new Color(0,0,0));
                        Insets insets = Client.space.getInsets();
                        move.setLocation(news.getCoordinates()[0]-20+insets.left+450, news.getCoordinates()[1]-20+insets.top+360);
                        Canvas canv = new Canvas(news.getColour(),news.getShine(), move);
                        Client.canvs.add(canv);
                        Client.vect.put(news, move);
                        canv.setPreferredSize(new Dimension(40,40));
                        canv.setLocation(news.getCoordinates()[0]-15+insets.left+450, news.getCoordinates()[1]-15+insets.top+360);
                        //Client.vect.put(news, canv);
                        String line1 = "name: "+news.toString()+", shine: "+String.valueOf(news.getShine());
                    String line2 = "Visible from Earth: "+String.valueOf(news.visibleFromEarth());
                    String line3 = "Visible from Moon: "+String.valueOf(news.visibleFromMoon());
                    String line4 = "Coordinates: X:"+String.valueOf(news.getCoordinates()[0])+", Y:"+String.valueOf(news.getCoordinates()[1]);
                    move.setToolTipText("<html> "+line1+"<br>"+line2+"<br>"+line3+"<br>"+line4+"</html>");
                        
                        Client.space.add(canv);
                        Client.space.add(move);
                        Client.space.repaint();
               
                }
                else if (message.equals("add")) {
                    Client.oos.write("add?".getBytes());
                    Client.oos.flush();
                    byte[] get = new byte[1024];
                    Client.ios.read(get);
                    ByteArrayInputStream bis = new ByteArrayInputStream(get);
                    ObjectInputStream oInStr = new ObjectInputStream(bis);
                    Object obj = oInStr.readObject();
                    Star str = (Star) obj;
                    
                    ImageIcon createdStar;
                    if (str.getColour().equals(Color.WHITE)) 
            createdStar = new ImageIcon("Resources/wc.jpg");
        else if (str.getColour().equals(Color.ORANGE)) 
            createdStar = new ImageIcon("Resources/oc.jpg");
        else if (str.getColour().equals(Color.YELLOW))
            createdStar = new ImageIcon("Resources/yc.jpg");
        else
            createdStar = new ImageIcon("Resources/rc.jpg");
                    JLabel move = new JLabel(createdStar);
                    move.setSize(new Dimension(40,40));
                    move.setBackground(new Color(0,0,0));
                    Client.space.add(move);
                    Insets insets = Client.space.getInsets();
                    move.setLocation(str.getCoordinates()[0]-20+insets.left+450, str.getCoordinates()[1]-20+insets.top+360);
                    String line1 = "name: "+str.toString()+", shine: "+String.valueOf(str.getShine());
                    String line2 = "Visible from Earth: "+String.valueOf(str.visibleFromEarth());
                    String line3 = "Visible from Moon: "+String.valueOf(str.visibleFromMoon());
                    String line4 = "Coordinates: X:"+String.valueOf(str.getCoordinates()[0])+", Y:"+String.valueOf(str.getCoordinates()[1]);
                    move.setToolTipText("<html> "+line1+"<br>"+line2+"<br>"+line3+"<br>"+line4+"</html>");
                    Canvas canv = new Canvas(str.getColour(),str.getShine(),move);
                    Client.canvs.add(canv);
                    canv.setLocation(str.getCoordinates()[0]-15+insets.left+450, str.getCoordinates()[1]-15+insets.top+360);
                    Client.vect.put(str, move);
                    Client.space.add(canv);
                    //Client.vect.put(str, move);
                }
                    
            } catch (IOException|ClassNotFoundException exc) {}
        }
    }
}
