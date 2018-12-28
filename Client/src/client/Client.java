//this one
package client;

import com.google.common.collect.HashBiMap;
import labLib.Star;
import java.io.*;
import java.net.*;
import java.awt.BorderLayout;
//import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
//import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;

public class Client {

    private static Star example;
    public static JFrame frame;
    private static JPanel left;
    private static JPanel cont;
    private static java.awt.Image backgroundImage;
    private static JPanel right;
    private static JLabel forPepe;
    private static ImageIcon normPepe;
    private static ImageIcon deadPepe;
    private static ImageIcon suicPepe;
    private static ImageIcon angryPepe;
    private static JLabel forSuicide;
    public static JPanel withBart = new JPanel();
    private static JButton login;
    private static JPanel addPanel;
    private static boolean pepeIsNorm;
    private static boolean pepeIsAlive;
    public static JPanel filt;
    private static final int port = 4988;
    public static BufferedOutputStream oos;
    public static BufferedInputStream ios;
    public static HashBiMap<Star, JLabel> vect = HashBiMap.create();
    //public static HashBiMap<Star, Canvas>vect = HashBiMap.create();
    private static ImageIcon createdStar;
    private static JPanel bombPanel;
    private static JPanel stimpanel;
    public static JButton canc;
    public static JButton reqBomb;
    private static JLabel state = new JLabel();
    private static boolean movable = false;
    public static JPanel space;
    private static int x;
    private static int y;
    private static ObjectOutputStream ststr;
    private static byte[] mock = new byte[10];
    public static JButton add;
    public static JButton disc;
    public static JButton filter;
    public static ArrayList <Canvas> canvs = new ArrayList<>();

    private static void initStart() {
        frame = new JFrame("Client application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(1300, 650));
        frame.setMaximumSize(new Dimension(1300, 650));
        frame.getContentPane().setLayout(new BorderLayout());
        JPanel topMock = new JPanel();
        topMock.setPreferredSize(new Dimension(1300, 150));
        topMock.setBackground(java.awt.Color.DARK_GRAY);
        JLabel greet = new JLabel();
        greet.setText("Welcome");
        greet.setFont(new Font("Comic Sans", 1, 40));
        JPanel leftMock = new JPanel();
        leftMock.setPreferredSize(new Dimension(500, 350));
        leftMock.setBackground(java.awt.Color.DARK_GRAY);
        JPanel central = new JPanel();
        central.setPreferredSize(new Dimension(300, 350));
        JPanel rightMock = new JPanel();
        JLabel name = new JLabel();
        name.setPreferredSize(new Dimension(170, 30));
        name.setText("Create username:");
        JTextField usnm = new JTextField();
        usnm.setPreferredSize(new Dimension(160, 30));
        usnm.setFont(new Font("Arial", 1, 20));
        rightMock.setPreferredSize(new Dimension(500, 350));
        rightMock.setBackground(java.awt.Color.DARK_GRAY);
        JPanel lowMock = new JPanel();
        lowMock.setPreferredSize(new Dimension(1300, 150));
        lowMock.setBackground(java.awt.Color.DARK_GRAY);
        login = new JButton("sign in");

        login.setFont(new Font("Arial", 1, 20));
        login.setPreferredSize(new Dimension(200, 50));
        login.setFocusPainted(false);
        login.addActionListener((ActionEvent event) -> {
            String usnmm = usnm.getText();
            if (usnmm != null && !usnmm.equals("")) {
                try {
                    oos.write(usnmm.getBytes());
                    oos.flush();

                    byte[] reply = new byte[40];
                    ios.read(reply);
                    String rep = new String(reply);

                    rep = rep.trim();
                    if (rep.equals("ac")) {
                        login.setVisible(false);
                        initializeObjects();
                    } else {
                        JOptionPane pane = new JOptionPane("This username is already used. Create another one!");
                        pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                        JDialog dial = pane.createDialog(frame, "Username is occupied");
                        dial.setVisible(true);
                    }
                } catch (IOException exc) {
                    System.out.println(exc.getMessage());
                }

            } else {
                JOptionPane pane = new JOptionPane("Create a username!");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Username not created");
                dial.setVisible(true);

            }
        });
        central.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        central.add(greet);
        central.add(name);
        central.add(usnm);
        central.add(login);
        frame.getContentPane().add(topMock, BorderLayout.NORTH);
        frame.getContentPane().add(leftMock, BorderLayout.WEST);
        frame.getContentPane().add(central, BorderLayout.CENTER);
        frame.getContentPane().add(rightMock, BorderLayout.EAST);
        frame.getContentPane().add(lowMock, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }

    private static void initializeObjects() {
        withBart.setBackground(java.awt.Color.PINK);
        withBart.setLayout(new BorderLayout());
        withBart.setPreferredSize(new Dimension(200, 400));
        withBart.setVisible(false);

        try {
            backgroundImage = javax.imageio.ImageIO.read(new File("Resources/background.jpeg"));
        } catch (IOException exc) {
        }

        space = new ImagePanel(backgroundImage);
        space.setPreferredSize(new Dimension(900, 650));
        space.setLayout(null);

        reqBomb = new JButton("<html>Move<br>Stars</html>");
        reqBomb.setFocusPainted(false);
        reqBomb.setFont(new Font("Arial", 1, 12));
        reqBomb.setPreferredSize(new Dimension(90, 30));
        reqBomb.addActionListener((ActionEvent event) -> {
            ArrayList<MoveThread> thr = new ArrayList<>();
            reqBomb.setVisible(false);
            disc.setEnabled(false);
            add.setEnabled(false);
            filter.setEnabled(false);
            canc = new JButton("cancel");
            canc.setPreferredSize(new Dimension(90, 30));
            bombPanel.add(canc);
            canc.addActionListener((ActionEvent ev) -> {
                
                for (MoveThread th :thr) {
                    vect.get(th.str).setLocation(th.xc, th.wasY);
                    for (Canvas canv: canvs){
                           if (canv.associated.equals(vect.get(th.str))) {
                               canv.setLocation(th.canvX, th.canvY);
                               break;
                           }}    
                    th.stop();
                }
                canc.setVisible(false);
                reqBomb.setVisible(true);
                disc.setEnabled(true);
                add.setEnabled(true);
                filter.setEnabled(true);
            });
            for (Star st : Client.vect.keySet()) {
                if (Client.vect.get(st).isVisible()) {
                    MoveThread mthrd = new MoveThread(st);
                    mthrd.start();
                    thr.add(mthrd);
                }
            }

        });
        try {
            /*byte[] forread = new byte[40];
            ios.read(forread);*/
            oos.write("a".getBytes());
            oos.flush();
        } catch (IOException exc) {
        }
        commThread thrd = new commThread();

        thrd.start();
        normPepe = new ImageIcon("Resources/Pepe1.jpg");
        forPepe = new JLabel(normPepe);
        pepeIsNorm = true;
        pepeIsAlive = true;
        suicPepe = new ImageIcon("Resources/Pepe_suic.jpg");
        deadPepe = new ImageIcon("Resources/pepe_dead.png");
        angryPepe = new ImageIcon("Resources/angry_pepe.jpg");
        forSuicide = new JLabel(suicPepe);

        forPepe.setPreferredSize(new Dimension(200, 220));
        forPepe.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pepeIsNorm) {
                    forPepe.setIcon(angryPepe);
                    left.setBackground(java.awt.Color.RED);
                    right.setBackground(java.awt.Color.RED);
                    filt.setBackground(java.awt.Color.RED);
                    addPanel.setBackground(java.awt.Color.RED);
                    
                } else {
                    forPepe.setIcon(normPepe);
                    left.setBackground(java.awt.Color.LIGHT_GRAY);
                    right.setBackground(java.awt.Color.LIGHT_GRAY);
                    filt.setBackground(java.awt.Color.LIGHT_GRAY);
                    addPanel.setBackground(java.awt.Color.LIGHT_GRAY);
                    
                }
                chngPepeState();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
        forSuicide.setPreferredSize(new Dimension(200, 220));

        forSuicide.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (pepeIsAlive) {
                    forSuicide.setIcon(deadPepe);
                    killPepe();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });

        cont = new JPanel(new BorderLayout());
        frame.setContentPane(cont);
        right = new JPanel();
        right.setPreferredSize(new Dimension(200, 720));
        right.setBackground(java.awt.Color.LIGHT_GRAY);
        right.setLayout(new BorderLayout());
        right.add(forSuicide, BorderLayout.SOUTH);
        left = new JPanel();
        left.setPreferredSize(new Dimension(200, 720));
        left.setBackground(java.awt.Color.LIGHT_GRAY);
        left.setLayout(new BorderLayout());
        left.add(forPepe, BorderLayout.SOUTH);

        initializeFilter();
        initializeAdd();

        cont.add(left, BorderLayout.WEST);
        cont.add(right, BorderLayout.EAST);
        cont.add(space, BorderLayout.CENTER);
        frame.pack();
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    oos.write("closed".getBytes());
                    oos.flush();
                } catch (IOException exc) {
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

    }

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            Socket sock = new Socket("localhost", port);
            ios = new BufferedInputStream(sock.getInputStream());
            oos = new BufferedOutputStream(sock.getOutputStream());
            initStart();
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private static void chngPepeState() {
        pepeIsNorm = !pepeIsNorm;
    }

    private static void killPepe() {
        pepeIsAlive = false;
    }

    private static void initializeFilter() {
        filt = new JPanel();
        filt.setBackground(java.awt.Color.LIGHT_GRAY);
        filt.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filt.setPreferredSize(new Dimension(200, 400));
        filter = new JButton("Filter");
        filter.setFocusPainted(false);
        filter.setPreferredSize(new Dimension(85, 30));

        JCheckBox tr = new JCheckBox("true");
        tr.setPreferredSize(new Dimension(85, 20));
        JCheckBox fls = new JCheckBox("false");
        fls.setPreferredSize(new Dimension(85, 20));
        tr.setBackground(java.awt.Color.LIGHT_GRAY);
        fls.setBackground(java.awt.Color.LIGHT_GRAY);
        JCheckBox tr2 = new JCheckBox("true");
        tr2.setPreferredSize(new Dimension(85, 20));
        JCheckBox fls2 = new JCheckBox("false");
        fls2.setPreferredSize(new Dimension(85, 20));
        tr2.setBackground(java.awt.Color.LIGHT_GRAY);
        fls2.setBackground(java.awt.Color.LIGHT_GRAY);
        JCheckBox white = new JCheckBox("white");
        JCheckBox yellow = new JCheckBox("yellow");
        JCheckBox orange = new JCheckBox("orange");
        JCheckBox red = new JCheckBox("red");
        white.setBackground(java.awt.Color.LIGHT_GRAY);
        white.setPreferredSize(new Dimension(85, 20));
        yellow.setBackground(java.awt.Color.LIGHT_GRAY);
        yellow.setPreferredSize(new Dimension(85, 20));
        orange.setBackground(java.awt.Color.LIGHT_GRAY);
        orange.setPreferredSize(new Dimension(85, 20));
        red.setBackground(java.awt.Color.LIGHT_GRAY);
        red.setPreferredSize(new Dimension(85, 20));

        JButton disc = new JButton("Reset");
        disc.setPreferredSize(new Dimension(85, 30));
        disc.setFocusPainted(false);
        JSpinner chmin = new JSpinner();
        Integer[] shine = new Integer[5001];
        for (int i = 0; i < 51; i++) {
            shine[i] = i * 100;
        }
        SpinnerListModel model = new SpinnerListModel(shine);
        chmin.setModel(model);
        ((DefaultEditor) chmin.getEditor()).getTextField().setEditable(false);
        chmin.setPreferredSize(new Dimension(150, 40));
        JSpinner chmax = new JSpinner();
        chmax.setModel(new SpinnerListModel(shine));
        ((DefaultEditor) chmax.getEditor()).getTextField().setFont(new Font("Arial", 1, 20));
        ((DefaultEditor) chmin.getEditor()).getTextField().setFont(new Font("Arial", 1, 20));
        ((DefaultEditor) chmax.getEditor()).getTextField().setEditable(false);
        chmax.setPreferredSize(new Dimension(150, 40));
        JLabel heading = new JLabel("Filter stars");
        JLabel eart = new JLabel("Visible from Earth");
        eart.setPreferredSize(new Dimension(200, 20));
        JLabel moon = new JLabel("Visible from Moon");
        moon.setPreferredSize(new Dimension(200, 20));
        JLabel shnmn = new JLabel("min Shine:");
        shnmn.setPreferredSize(new Dimension(200, 20));
        JLabel shnmx = new JLabel("max Shine:");
        shnmx.setPreferredSize(new Dimension(200, 20));
        JLabel clr = new JLabel("Colour");
        clr.setPreferredSize(new Dimension(200, 20));
        heading.setFont(new Font("Helvetica", Font.ITALIC, 30));
        eart.setFont(new Font("Helvetica", 1, 17));
        moon.setFont(new Font("Helvetica", 1, 17));
        shnmn.setFont(new Font("Helvetica", 1, 17));
        shnmx.setFont(new Font("Helvetica", 1, 17));
        clr.setFont(new Font("Helvetica", 1, 17));
        heading.setPreferredSize(new Dimension(200, 50));
        disc.addActionListener((ActionEvent event) -> {
            for (Star st : vect.keySet()) {
                vect.get(st).setVisible(true);
                for (Canvas canv: canvs){
                           if (canv.associated.equals(vect.get(st))) {
                               canv.setVisible(true);
                               break; 
                           }
            }
            }
            tr.setSelected(false);
            fls.setSelected(false);
            tr2.setSelected(false);
            fls2.setSelected(false);
            white.setSelected(false);
            yellow.setSelected(false);
            orange.setSelected(false);
            red.setSelected(false);
            chmin.setValue(0);
            chmax.setValue(0);
        });
        filter.addActionListener((ActionEvent event) -> {
            boolean earts = false;
            boolean eartn = false;
            boolean moons = false;
            boolean moonn = false;
            boolean whites = false;
            boolean yellows = false;
            boolean oranges = false;
            boolean reds = false;
            int minShine = 0;
            int maxShine = 0;
            boolean eartMatters = false;
            boolean moonMatters = false;
            boolean colourMatters = false;
            boolean shineMatters = false;
            if (tr.isSelected()) {
                earts = true;
            }
            if (fls.isSelected()) {
                eartn = true;
            }
            if (earts || eartn) {
                eartMatters = true;
            }
            if (tr2.isSelected()) {
                moons = true;
            }
            if (fls2.isSelected()) {
                moonn = true;
            }
            if (moons || moonn) {
                moonMatters = true;
            }
            if (white.isSelected()) {
                whites = true;
            }
            if (yellow.isSelected()) {
                yellows = true;
            }
            if (orange.isSelected()) {
                oranges = true;
            }
            if (red.isSelected()) {
                reds = true;
            }
            if (reds || oranges || yellows || whites) {
                colourMatters = true;
            }
            minShine = (Integer) chmin.getValue();
            maxShine = (Integer) chmax.getValue();
            if (minShine != 0 || maxShine != 0) {
                shineMatters = true;
            }
            for (Star st : vect.keySet()) {
                Canvas sss = null;
                for (Canvas canv: canvs){
                           if (canv.associated.equals(vect.get(st))) {
                               sss = canv;
                               break;
                           } 
                        }
                //System.out.println("iteration");
                vect.get(st).setVisible(true);
                sss.setVisible(true);
                if (eartMatters) {
                    if (st.visibleFromEarth() && !earts || !st.visibleFromEarth() && !eartn) {
                        vect.get(st).setVisible(false);
                        sss.setVisible(false);
                        
                    } 
                }
                if (moonMatters) {
                    if (st.visibleFromMoon() && !moons || !st.visibleFromMoon() && !moonn) {
                        vect.get(st).setVisible(false);
                        sss.setVisible(false);
                        
                    }
                }
                if (colourMatters) {
                    if (st.getColour().equals(java.awt.Color.WHITE) && !whites || st.getColour().equals(java.awt.Color.YELLOW) && !yellows || st.getColour().equals(java.awt.Color.ORANGE) && !oranges || st.getColour().equals(java.awt.Color.RED) && !reds) {
                        vect.get(st).setVisible(false);
                        sss.setVisible(false);
                        
                    }
                }
                if (shineMatters) {
                    if (st.getShine() > maxShine || st.getShine() < minShine) {
                        vect.get(st).setVisible(false);
                        sss.setVisible(false);
                        
                    }
                }
            }
        });
        filt.add(heading);
        filt.add(eart);
        filt.add(tr);
        filt.add(fls);
        filt.add(moon);
        filt.add(tr2);
        filt.add(fls2);
        filt.add(clr);
        filt.add(white);
        filt.add(yellow);
        filt.add(orange);
        filt.add(red);
        filt.add(shnmn);
        filt.add(chmin);
        filt.add(shnmx);
        filt.add(chmax);
        filt.add(disc);
        filt.add(filter);
        left.add(filt);
    }

    private static void initializeAdd() {
        addPanel = new JPanel();
        stimpanel = new JPanel();
        bombPanel = new JPanel();
        stimpanel.setPreferredSize(new Dimension(200, 40));
        stimpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bombPanel = new JPanel();
        bombPanel.setPreferredSize(new Dimension(200, 40));
        bombPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bombPanel.setBackground(java.awt.Color.LIGHT_GRAY);
        stimpanel.setBackground(java.awt.Color.LIGHT_GRAY);
        stimpanel.setVisible(false);
        JLabel bmb = new JLabel("Bomb state:");
        bmb.setPreferredSize(new Dimension(70, 40));
        bombPanel.add(bmb);
        bombPanel.add(state);
        bombPanel.add(reqBomb);
        addPanel.setBackground(java.awt.Color.LIGHT_GRAY);
        addPanel.setPreferredSize(new Dimension(200, 490));
        addPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel heading = new JLabel("Add a Star");
        heading.setFont(new Font("Helvetica", Font.ITALIC, 30));
        heading.setPreferredSize(new Dimension(200, 50));
        JLabel name = new JLabel("Name");
        name.setFont(new Font("Helvetica", 1, 17));
        name.setPreferredSize(new Dimension(200, 20));
        JLabel eart = new JLabel("Visible from Earth");
        eart.setFont(new Font("Helvetica", 1, 17));
        eart.setPreferredSize(new Dimension(200, 20));
        JLabel moon = new JLabel("Visible from Moon");
        moon.setFont(new Font("Helvetica", 1, 17));
        moon.setPreferredSize(new Dimension(200, 20));
        JLabel shn = new JLabel("Shine");
        shn.setFont(new Font("Helvetica", 1, 17));
        shn.setPreferredSize(new Dimension(200, 20));
        JLabel clr = new JLabel("Colour");
        clr.setFont(new Font("Helvetica", 1, 17));
        clr.setPreferredSize(new Dimension(200, 20));
        JTextField namein = new JTextField();
        namein.setPreferredSize(new Dimension(120, 30));
        namein.setFont(new Font("Arial", 1, 14));
        JTextField shinein = new JTextField();
        shinein.setPreferredSize(new Dimension(120, 30));
        shinein.setFont(new Font("Arial", 1, 14));

        ButtonGroup trfls = new ButtonGroup();
        ButtonGroup trfls2 = new ButtonGroup();
        ButtonGroup clrs = new ButtonGroup();
        JRadioButton tr = new JRadioButton();
        tr.setText("true");
        tr.setPreferredSize(new Dimension(85, 20));
        tr.setBackground(java.awt.Color.LIGHT_GRAY);
        JRadioButton fls = new JRadioButton();
        fls.setText("false");
        fls.setPreferredSize(new Dimension(85, 20));
        fls.setBackground(java.awt.Color.LIGHT_GRAY);
        trfls.add(tr);
        trfls.add(fls);
        JRadioButton tr2 = new JRadioButton();
        tr2.setText("true");
        tr2.setPreferredSize(new Dimension(85, 20));
        tr2.setBackground(java.awt.Color.LIGHT_GRAY);
        JRadioButton fls2 = new JRadioButton();
        fls2.setText("false");
        fls2.setPreferredSize(new Dimension(85, 20));
        fls2.setBackground(java.awt.Color.LIGHT_GRAY);
        trfls2.add(tr2);
        trfls2.add(fls2);
        JRadioButton white = new JRadioButton();
        white.setText("white");
        white.setPreferredSize(new Dimension(85, 20));
        white.setBackground(java.awt.Color.LIGHT_GRAY);
        JRadioButton orange = new JRadioButton();
        orange.setText("orange");
        orange.setPreferredSize(new Dimension(85, 20));
        orange.setBackground(java.awt.Color.LIGHT_GRAY);
        JRadioButton yellow = new JRadioButton();
        yellow.setText("yellow");
        yellow.setPreferredSize(new Dimension(85, 20));
        yellow.setBackground(java.awt.Color.LIGHT_GRAY);
        JRadioButton red = new JRadioButton();
        red.setText("red");
        red.setPreferredSize(new Dimension(85, 20));
        red.setBackground(java.awt.Color.LIGHT_GRAY);
        clrs.add(white);
        clrs.add(yellow);
        clrs.add(orange);
        clrs.add(red);
        add = new JButton("Add");
        add.setPreferredSize(new Dimension(85, 30));
        add.setFocusPainted(false);
        disc = new JButton("Reset");
        disc.setPreferredSize(new Dimension(85, 30));
        disc.setFocusPainted(false);
        disc.addActionListener((ActionEvent event) -> {
            namein.setText("");
            shinein.setText("");
            trfls.clearSelection();
            trfls2.clearSelection();
            clrs.clearSelection();
        });
        add.addActionListener((ActionEvent event) -> {
            try {
                int shineSt = Integer.parseInt(shinein.getText());
                if (shineSt > 5000 || shineSt < 0) {
                    JOptionPane pane = new JOptionPane("'Shine' value must lay between 0 and 5000.");
                    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    JDialog dial = pane.createDialog(frame, "Wrong input!");
                    dial.setVisible(true);
                    throw new NumberFormatException("name");
                }
                String nameSt = namein.getText();
                if (nameSt == null || nameSt.equals("")) {
                    JOptionPane pane = new JOptionPane("Enter name of a new Star.");
                    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    JDialog dial = pane.createDialog(frame, "Wrong input!");
                    dial.setVisible(true);
                    throw new NumberFormatException("name");
                }
                boolean visErth = tr.isSelected();
                boolean visMoon = tr2.isSelected();
                if (!tr.isSelected() && !fls.isSelected()) {
                    JOptionPane pane = new JOptionPane("Specify visibility from Earth!");
                    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    JDialog dial = pane.createDialog(frame, "Wrong input!");
                    dial.setVisible(true);
                    throw new NumberFormatException("name");
                }
                if (!tr2.isSelected() && !fls2.isSelected()) {
                    JOptionPane pane = new JOptionPane("Specify visibility from Moon!");
                    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    JDialog dial = pane.createDialog(frame, "Wrong input!");
                    dial.setVisible(true);
                    throw new NumberFormatException("name");
                }
                java.awt.Color clrSt;

                if (white.isSelected()) {
                    createdStar = new ImageIcon("Resources/whtSt.png");
                    clrSt = java.awt.Color.WHITE;
                } else if (orange.isSelected()) {
                    createdStar = new ImageIcon("Resources/orngSt.png");
                    clrSt = java.awt.Color.ORANGE;
                } else if (yellow.isSelected()) {
                    createdStar = new ImageIcon("Resources/yellSt.png");
                    clrSt = java.awt.Color.YELLOW;
                } else if (red.isSelected()) {
                    createdStar = new ImageIcon("Resources/redSt.png");
                    clrSt = java.awt.Color.RED;
                } else {
                    JOptionPane pane = new JOptionPane("Choose colour of a new Star.");
                    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    JDialog dial = pane.createDialog(frame, "Wrong input!");
                    dial.setVisible(true);
                    throw new NumberFormatException("name");
                }
                JOptionPane pane = new JOptionPane("Click on the space to set location of the Star");
                pane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Choose location");
                dial.setVisible(true);
                space.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        try {
                            oos.write("star".getBytes());
                            oos.flush();
                            x = e.getX();
                            y = e.getY();
                            space.removeMouseListener(this);
                            Star newSt = new Star(shineSt, nameSt, x - 450, y - 360, visErth, visMoon, clrSt);
                            byte[] frst;
                            ByteArrayOutputStream bytestr = new ByteArrayOutputStream();
                            ststr = new ObjectOutputStream(bytestr);
                            ststr.writeObject(newSt);
                            ststr.flush();
                            frst = bytestr.toByteArray();
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException exc) {

                            }
                            oos.write(frst);
                            oos.flush();
                            namein.setText("");
                            shinein.setText("");
                            trfls.clearSelection();
                            trfls2.clearSelection();
                            clrs.clearSelection();
                        } catch (IOException exc) {
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }

                });

            } catch (NumberFormatException exc) {
                if (!exc.getMessage().equals("name")) {
                    JOptionPane pane = new JOptionPane("'Shine' field must contain integer value!");
                    pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                    JDialog dial = pane.createDialog(frame, "Wrong input!");
                    dial.setVisible(true);
                }
            }

        });
        addPanel.add(heading);
        addPanel.add(name);
        addPanel.add(namein);
        addPanel.add(eart);
        addPanel.add(tr);
        addPanel.add(fls);
        addPanel.add(moon);
        addPanel.add(tr2);
        addPanel.add(fls2);
        addPanel.add(shn);
        addPanel.add(shinein);
        addPanel.add(clr);
        addPanel.add(white);
        addPanel.add(yellow);
        addPanel.add(orange);
        addPanel.add(red);
        addPanel.add(disc);
        addPanel.add(add);
        addPanel.add(stimpanel);
        addPanel.add(bombPanel);
        right.add(addPanel);

    }
}
