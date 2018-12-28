package server;

import com.google.gson.Gson;
import java.awt.Color;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.tree.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import javax.swing.event.*;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import labLib.Star;

public class Server {

    public static ArrayList <ThreadHandler> listOfThreads = new ArrayList<>();
    private static JFrame frame;
    private static JPanel topSide;
    private static JPanel middle;
    private static JPanel main;
    private static JPanel additional;
    private static JPanel editional;
    private static JPanel lowerCom;
    private static JPanel lowleft;
    private static JPanel mock;
    private final static DefaultMutableTreeNode white = new DefaultMutableTreeNode("White stars");
    private final static DefaultMutableTreeNode yellow = new DefaultMutableTreeNode("Yellow stars");
    private final static DefaultMutableTreeNode orange = new DefaultMutableTreeNode("Orange stars");
    private final static DefaultMutableTreeNode red = new DefaultMutableTreeNode("Red stars");
    public static JTree tree;
    private static DefaultTreeModel model;
    public static DefaultMutableTreeNode currentNode = null;
    private static DefaultMutableTreeNode root;
    private final static JList usersList = new JList();
    private static JTextField shinein;
    private static Star selected;
    private static JTextField namein;
    private static JTextField xin;
    private static JTextField yin;
    private static JComboBox colours;
    private static JComboBox trfls;
    private static JComboBox trfls2;
    private static DefaultMutableTreeNode underEdit;
    private static File music;
    public static JTextArea logs;
    public static DefaultListModel listModel;
    private static ListSelectionModel listSelecModel;
    private static String usName = null;
    public static Boolean bomb = true;
    public static Star deleted;
    private static String oldSt;
    public static Star added;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            guiSetUp();
        });

        CollectionControl.begin();
        try (ServerSocket sock = new ServerSocket(4988)) {
            
            
            while (!sock.isClosed()) {
                Socket ch = sock.accept();
                
                
                ThreadHandler handler = new ThreadHandler(ch);
                handler.start();
                listOfThreads.add(handler);
                String loc = handler.userName;
                loc = loc.trim();
                logs.append("A user "+loc+" is connected!\n");
            }
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private static void guiSetUp() {
        frame = new JFrame("Server Application");
        Container cont = frame.getContentPane();
        cont.setBackground(Color.LIGHT_GRAY);
        cont.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowClosing(WindowEvent ev) {
                CollectionControl.finish();
                System.exit(0);
            }

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        logs = new JTextArea(5, 40);
        logs.setEditable(false);
        logs.setLayout(new FlowLayout());
        logs.setText("Logs:\n");
        music = new File("Resources/button.wav");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        topSide = new JPanel();
        middle = new JPanel();
        main = new JPanel();
        additional = new JPanel();
        listModel = new DefaultListModel();
        editional = new JPanel();
        lowerCom = new JPanel();
        lowleft = new JPanel();
        lowleft.setBackground(Color.LIGHT_GRAY);
        lowleft.setPreferredSize(new Dimension(1280,194));
        lowleft.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        lowerCom.setBackground(Color.LIGHT_GRAY);
        JScrollPane aroundLogs = new JScrollPane(logs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        lowleft.add(aroundLogs);
        lowerCom.setPreferredSize(new Dimension(1280, 60));
        lowerCom.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        main.setBackground(Color.LIGHT_GRAY);
        main.setPreferredSize(new Dimension(960, 370));
        main.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        setUpJTree();
        middle.setBackground(Color.LIGHT_GRAY);
        middle.setPreferredSize(new Dimension(1280, 96));
        middle.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 36));
        additional.setPreferredSize(new Dimension(300, 370));
        additional.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        additional.setBackground(Color.LIGHT_GRAY);
        editional.setPreferredSize(new Dimension(300,370));
        editional.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        editional.setBackground(Color.LIGHT_GRAY);
        editional.setVisible(false);
        mock = new JPanel();
        mock.setPreferredSize(new Dimension(300, 370));
        mock.setBackground(Color.LIGHT_GRAY);
        mock.setVisible(true);
        prepareAddPressed();
        editPrepare();
        additional.setVisible(false);
        JLabel heading = new JLabel("Collection of Stars");
        heading.setFont(new Font("Verdana", 1, 25));
        usersList.setPreferredSize(new Dimension(280, 350));
        TitledBorder bord = new TitledBorder("Connected users:");
        bord.setTitleFont(new Font("Verdana", 1, 20));
        bord.setTitleJustification(TitledBorder.CENTER);
        bord.setTitlePosition(TitledBorder.TOP);
        usersList.setBorder(bord);
        usersList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        listSelecModel = usersList.getSelectionModel();
        listSelecModel.addListSelectionListener((ListSelectionEvent event) -> {
            usName = (String)usersList.getSelectedValue(); 
        });
        heading.setVisible(true);
        topSide.setBackground(Color.LIGHT_GRAY);
        topSide.setPreferredSize(new Dimension(1280, 60));
        topSide.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        frame.add(topSide);
        frame.add(middle);
        frame.add(mock);
        frame.add(additional);
        frame.add(editional);
        frame.add(main);
        frame.add(lowerCom);
        frame.add(lowleft);
        middle.add(heading);
        customButton addButton = makeCommandButton("Add");
        customButton deleteButton = makeCommandButton("Delete");
        customButton editButton = makeCommandButton("Edit");
        customButton clearButton = makeCommandButton("Clear");
        customButton banOne = makeCommandButton("Ban user");
        customButton banAll = makeCommandButton("Ban all");
        banOne.addActionListener((ActionEvent event) -> {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream(music);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
            } catch (UnsupportedAudioFileException|IOException|LineUnavailableException exc) {
                System.out.println(exc.getMessage());
            }
            if (usName==null) {
                JOptionPane pane = new JOptionPane("You should choose a user by clicking on him and then press 'Ban user' button to ban him.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Select user to ban.");
                dial.setVisible(true);
                return;
            }
            listOfThreads.stream().filter((handl) -> (handl.userName.equals(usName))).forEach((handl) -> {
                try {
                handl.getOutStr().write("banned".getBytes());
                handl.getOutStr().flush();
                } catch (IOException exc){}
                handl.interrupt();
                handl.userName=null;
            });
            String loc = usName;
            loc = loc.trim();
            logs.append("User "+loc+" was banned.\n");
            listModel.removeElement(usName);
            usName = null;
        });
        banAll.addActionListener((ActionEvent event) -> {
            try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(music);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
        } catch (UnsupportedAudioFileException|IOException|LineUnavailableException exc) {
            System.out.println(exc.getMessage());
        }
            listOfThreads.stream().forEach((thread) -> {
                try {
                thread.getOutStr().write("banned".getBytes());
                thread.getOutStr().flush();
                } catch (IOException exc) {}
                thread.userName=null;
                thread.interrupt();
            });
            listModel.removeAllElements();
            logs.append("All users were banned.\n");  
        });
        deleteButton.addActionListener((ActionEvent ev) -> {
            try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(music);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
        } catch (UnsupportedAudioFileException|IOException|LineUnavailableException exc) {
            System.out.println(exc.getMessage());
        }
            if (editional.isVisible()&&underEdit.equals(currentNode)) {
                JOptionPane pane = new JOptionPane("You cannot remove a Star while editing it.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Operation failure.");
                dial.setVisible(true);
                return;
            }
            if (currentNode == null) {
                JOptionPane pane = new JOptionPane("You have to choose a Star from JTree by clicking on it and then press \"Delete\" button.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Star isn't chosen!");
                dial.setVisible(true);
                return;
            }
            if (!currentNode.isLeaf() || currentNode.getUserObject() instanceof String) {
                JOptionPane pane = new JOptionPane("You have to choose a Star, not a directory.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Directory chosen!");
                dial.setVisible(true);
                tree.clearSelection();
                return;
            }
            for (ThreadHandler thrd : listOfThreads) {
                try{
                thrd.getOutStr().write("delete".getBytes());
                thrd.getOutStr().flush();
                
                
                } catch (IOException exc){}
            }
            deleted = (Star)currentNode.getUserObject();
            CollectionControl.vect.remove(deleted);
            model.removeNodeFromParent(currentNode);
            currentNode = null;
        });
        clearButton.addActionListener((ActionEvent ev) -> {
            try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(music);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
        } catch (UnsupportedAudioFileException|IOException|LineUnavailableException exc) {
            System.out.println(exc.getMessage());
        }
            int n = JOptionPane.showConfirmDialog(
                    frame, "Are you sure? All Stars will be deleted.", "Clear", JOptionPane.YES_NO_OPTION);
            if (n == JOptionPane.YES_OPTION) {
                CollectionControl.vect.clear();
                white.removeAllChildren();
                orange.removeAllChildren();
                red.removeAllChildren();
                yellow.removeAllChildren();
                model.reload();
                for (ThreadHandler thrd : listOfThreads) {
                    try {
                    thrd.getOutStr().write("clear".getBytes());
                    thrd.getOutStr().flush();
                    } catch (IOException exc){}
                }
            }
        });
        editButton.addActionListener((ActionEvent ev) -> {
            try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(music);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
        } catch (UnsupportedAudioFileException|IOException|LineUnavailableException exc) {
            System.out.println(exc.getMessage());
        }
            if (currentNode == null) {
                JOptionPane pane = new JOptionPane("You have to choose a Star from JTree by clicking on it and then press \"Edit\" button.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Star isn't chosen!");
                dial.setVisible(true);
                return;
            }
            if (!currentNode.isLeaf() || currentNode.getUserObject() instanceof String) {
                JOptionPane pane = new JOptionPane("You have to choose a Star, not a directory.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Directory chosen!");
                dial.setVisible(true);
                tree.clearSelection();
                return;
            }
            Gson gson = new Gson();
            oldSt = gson.toJson((Star)currentNode.getUserObject());
            editPressed();
        });
        addButton.addActionListener((ActionEvent event) -> { 
        try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(music);
        Clip clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
        } catch (UnsupportedAudioFileException|IOException|LineUnavailableException exc) {
            System.out.println(exc.getMessage());
        }
            addPressed();
        });
        JScrollPane withtree = new JScrollPane(tree);
        withtree.setPreferredSize(new Dimension(640, 360));
        main.add(withtree);
        main.add(usersList);
        topSide.add(addButton);
        topSide.add(deleteButton);
        topSide.add(editButton);
        topSide.add(clearButton);
        lowerCom.add(banOne);
        lowerCom.add(banAll);
        frame.setVisible(true);
    }

    private static customButton makeCommandButton(String text) {
        customButton button = new customButton(text);
        button.setPreferredSize(new Dimension(150, 40));
        button.setForeground(Color.DARK_GRAY);
        button.setBackground(new Color(238, 188, 98));
        button.setVisible(true);
        button.setFont(new Font("Verdana", 1, 19));
        return button;
    }

    public static void addPressed() {
        editional.setVisible(false);
        mock.setVisible(false);
        additional.setVisible(true);
    }

    public static void prepareAddPressed() {
        JLabel visEarth = new JLabel("Visible from Earth:");
        visEarth.setPreferredSize(new Dimension(120, 30));
        JLabel visMoon = new JLabel("Visible from Moon:");
        visMoon.setPreferredSize(new Dimension(120, 30));
        JLabel name = new JLabel("Name:");
        name.setPreferredSize(new Dimension(120, 30));
        JLabel shine = new JLabel("Shine:");
        shine.setPreferredSize(new Dimension(120, 30));
        JLabel xcoord = new JLabel("X coordinate:");
        xcoord.setPreferredSize(new Dimension(120, 30));
        JLabel ycoord = new JLabel("Y coordinate:");
        ycoord.setPreferredSize(new Dimension(120, 30));
        JLabel colour = new JLabel("Colour:");
        colour.setPreferredSize(new Dimension(120, 30));
        ButtonGroup eart = new ButtonGroup();
        JTextField shinein = new JTextField();
        shinein.setPreferredSize(new Dimension(100, 30));
        JTextField namein = new JTextField();
        namein.setPreferredSize(new Dimension(100, 30));
        JTextField xin = new JTextField();
        xin.setPreferredSize(new Dimension(100, 30));
        JTextField yin = new JTextField();
        yin.setPreferredSize(new Dimension(100, 30));
        String[] colrs = {"white", "yellow", "orange", "red"};
        JComboBox colours = new JComboBox(colrs);
        colours.setPreferredSize(new Dimension(100, 30));
        String[] trfl = {"true", "false"};
        JComboBox trfls = new JComboBox(trfl);
        JComboBox trfls2 = new JComboBox(trfl);
        trfls.setPreferredSize(new Dimension(100, 30));
        trfls2.setPreferredSize(new Dimension(100, 30));
        trfls.setPreferredSize(new Dimension(100, 30));
        JRadioButtonMenuItem tr = new JRadioButtonMenuItem("true");
        JRadioButtonMenuItem fls = new JRadioButtonMenuItem("false");
        eart.add(tr);
        eart.add(fls);
        ButtonGroup mon = new ButtonGroup();
        JRadioButtonMenuItem trm = new JRadioButtonMenuItem("true");
        JRadioButtonMenuItem flsm = new JRadioButtonMenuItem("false");
        mon.add(trm);
        mon.add(flsm);
        trfls.add(tr);
        trfls.add(fls);
        trfls2.add(trm);
        trfls2.add(flsm);
        colours.setPreferredSize(new Dimension(100, 30));
        JButton commit = new JButton("add");
        commit.setFont(new Font("Verdana", 1, 15));
        commit.setFocusPainted(false);
        commit.setPreferredSize(new Dimension(100, 30));
        JButton cancel = new JButton("cancel");
        cancel.setFont(new Font("Verdana", 1, 15));
        cancel.setFocusPainted(false);
        cancel.addActionListener((ActionEvent event) -> {
            namein.setText("");
            trfls.setSelectedItem("true");
            trfls2.setSelectedItem("true");
            shinein.setText("");
            xin.setText("");
            yin.setText("");
            colours.setSelectedItem("white");
            addCommitted();
        });
        cancel.setPreferredSize(new Dimension(100, 30));
        additional.add(name);
        additional.add(namein);
        additional.add(visEarth);
        additional.add(trfls);
        additional.add(visMoon);
        additional.add(trfls2);
        additional.add(shine);
        additional.add(shinein);
        additional.add(xcoord);
        additional.add(xin);
        additional.add(ycoord);
        additional.add(yin);
        additional.add(colour);
        additional.add(colours);
        additional.add(cancel);
        additional.add(commit);
        commit.addActionListener((ActionEvent event) -> {
            try {
            String name1 = namein.getText();
            if (shinein.getText().equals("") || namein.getText().equals("") ||xin.getText().equals("") || yin.getText().equals(""))
                throw new Exception("");
            int Shine = Integer.parseInt(shinein.getText());
            if (Shine<0 || Shine>5000) {
                JOptionPane pane = new JOptionPane("'Shine' value must lay between 0 and 5000.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Wrong input!");
                dial.setVisible(true);
                throw new Exception("no");
            }
            int xcoord1 = Integer.parseInt(xin.getText());
            if (xcoord1 < -430 || xcoord1 > 430) {
                JOptionPane pane = new JOptionPane("X coordinate value must lay between -430 and 430.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Wrong input!");
                dial.setVisible(true);
                throw new Exception("no");
            }
            int ycoord1 = Integer.parseInt(yin.getText());
            if (ycoord1 < -350 || ycoord1 > 350) {
                JOptionPane pane = new JOptionPane("Y coordinate value must lay between -350 and 350.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Wrong input!");
                dial.setVisible(true);
                throw new Exception("no");
            }
            Color clr = null;
            switch ((String) colours.getSelectedItem()) {
                case "white": {
                    clr = Color.WHITE;
                    break;
                }
                case "yellow": {
                    clr = Color.YELLOW;
                    break;
                }
                case "orange": {
                    clr = Color.ORANGE;
                    break;
                }
                case "red": {
                    clr = Color.RED;
                    break;
                }
            }
            boolean visErth = (trfls.getSelectedItem().equals("true"));
            boolean visMoon1 = trfls2.getSelectedItem().equals("true");
            added = new Star(Shine, name1, xcoord1, ycoord1, visErth, visMoon1, clr);
            CollectionControl.vect.add(added);
            namein.setText("");
            trfls.setSelectedItem("true");
            trfls2.setSelectedItem("true");
            shinein.setText("");
            xin.setText("");
            yin.setText("");
            colours.setSelectedItem("white");
            addStarToTree(added);
            addCommitted();
            for (ThreadHandler thrd :Server.listOfThreads){
                thrd.getOutStr().write("add".getBytes());
                thrd.getOutStr().flush();
                        }
            } catch (Exception exc) {
                if (!exc.getMessage().equals("no")) {
                JOptionPane pane = new JOptionPane("Make sure you input all fields and 'Shine', 'X coordinate' and 'Y coordinate' contain only numeric integer values.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Wrong input!");
                dial.setVisible(true);
                System.out.println(exc.getMessage());
                }
            }
        });

    }

    public static void addName(String name) {
        
        listModel.addElement(name);
        usersList.setModel(listModel);
    }

    public static void addStarToTree(Star str) {
        DefaultMutableTreeNode star = new DefaultMutableTreeNode(str);
        
        if (str.getColour().equals(Color.WHITE)) {
            model.insertNodeInto(star, white, 0);
        } else if (str.getColour().equals(Color.YELLOW)) {
            model.insertNodeInto(star, yellow, 0);
        } else if (str.getColour().equals(Color.ORANGE)) {
            model.insertNodeInto(star, orange, 0);
        } else if (str.getColour().equals(Color.RED)) {
            model.insertNodeInto(star, red, 0);
        }
    }

    public static void addCommitted() {
        additional.setVisible(false);
        mock.setVisible(true);
    }

    private static void setUpJTree() {
        root = new DefaultMutableTreeNode("Stars");
        root.add(white);
        root.add(yellow);
        root.add(orange);
        root.add(red);
        tree = new JTree(root);
        tree.setCellRenderer(new MyTreeCellRenderer());
        model = (DefaultTreeModel) tree.getModel();
        CollectionControl.vect.stream().forEach((st) -> {
            addStarToTree(st);
        });
        model.reload();
        tree.addTreeSelectionListener((TreeSelectionEvent event) -> {
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)Server.tree.getLastSelectedPathComponent();
        if (node == null)
            return;
        Server.currentNode = node;
        
        });
        ToolTipManager.sharedInstance().registerComponent(tree);
    }

    private static class MyTreeCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            if (value instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                
                if (node.getUserObject() instanceof Star) {
                    Star st = (Star)node.getUserObject();
                    setToolTipText("name: "+st.toString()+", shine: "+String.valueOf(st.getShine())+", location: x: "+String.valueOf(st.getCoordinates()[0])+", y:"+String.valueOf(st.getCoordinates()[1]));
                    if (st.getColour().equals(Color.RED))
                        setIcon(new ImageIcon("Resources/redSt.png"));
                    else if (st.getColour().equals(Color.WHITE))
                        setIcon(new ImageIcon("Resources/whtSt.png"));
                    else if (st.getColour().equals(Color.YELLOW))
                        setIcon(new ImageIcon("Resources/yellSt.png"));
                    else 
                        setIcon(new ImageIcon("Resources/orngSt.png"));
                } else {
                    setIcon(UIManager.getIcon("FileView.directoryIcon"));
                    setToolTipText("Directory " +(String)node.getUserObject());
                }
            }
            return this;
        }
    }
    
    public static void editPrepare() {
        JLabel visEarth = new JLabel("Visible from Earth:");
            visEarth.setPreferredSize(new Dimension(120, 30));
            JLabel visMoon = new JLabel("Visible from Moon:");
            visMoon.setPreferredSize(new Dimension(120, 30));
            JLabel name = new JLabel("Name:");
            name.setPreferredSize(new Dimension(120, 30));
            JLabel shine = new JLabel("Shine:");
            shine.setPreferredSize(new Dimension(120, 30));
            JLabel xcoord = new JLabel("X coordinate:");
            xcoord.setPreferredSize(new Dimension(120, 30));
            JLabel ycoord = new JLabel("Y coordinate:");
            ycoord.setPreferredSize(new Dimension(120, 30));
            JLabel colour = new JLabel("Colour:");
            colour.setPreferredSize(new Dimension(120, 30));
            
        
        shinein = new JTextField();
        shinein.setPreferredSize(new Dimension(100, 30));
        
        namein = new JTextField();
        namein.setPreferredSize(new Dimension(100, 30));
        
        xin = new JTextField();
        xin.setPreferredSize(new Dimension(100, 30));
        
        yin = new JTextField();
        yin.setPreferredSize(new Dimension(100, 30));
        
        String[] colrs = {"white", "yellow", "orange", "red"};
        colours = new JComboBox(colrs);
        
        colours.setPreferredSize(new Dimension(100, 30));
        String[] trfl = {"true", "false"};
        trfls = new JComboBox(trfl);
        trfls2 = new JComboBox(trfl);
        trfls.setPreferredSize(new Dimension(100, 30));
        trfls2.setPreferredSize(new Dimension(100, 30));
        trfls.setPreferredSize(new Dimension(100, 30));
        
        colours.setPreferredSize(new Dimension(100, 30));
        JButton commit = new JButton("save");
        commit.setFont(new Font("Verdana", 1, 15));
        commit.setFocusPainted(false);
        commit.setPreferredSize(new Dimension(100, 30));
        JButton cancel = new JButton("cancel");
        cancel.setFont(new Font("Verdana", 1, 15));
        cancel.setFocusPainted(false);
        
        cancel.setPreferredSize(new Dimension(100, 30));
        editional.add(name);
        editional.add(namein);
        editional.add(visEarth);
        editional.add(trfls);
        editional.add(visMoon);
        editional.add(trfls2);
        editional.add(shine);
        editional.add(shinein);
        editional.add(xcoord);
        editional.add(xin);
        editional.add(ycoord);
        editional.add(yin);
        editional.add(colour);
        editional.add(colours);
        editional.add(cancel);
        editional.add(commit);
        
        cancel.addActionListener((ActionEvent event) -> {
        namein.setText("");
            trfls.setSelectedItem("true");
            trfls2.setSelectedItem("true");
            shinein.setText("");
            xin.setText("");
            yin.setText("");
            colours.setSelectedItem("white");
            editional.setVisible(false);
            mock.setVisible(true);
        });
        commit.addActionListener((ActionEvent event) -> {
            try {
            String name1 = namein.getText();
            if (name1.equals(""))
                throw new NumberFormatException();
            int Shine = Integer.parseInt(shinein.getText());
            if (Shine<0||Shine>5000){
                JOptionPane pane = new JOptionPane("'Shine' value must lay between 0 and 5000.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Wrong input!");
                dial.setVisible(true);
                throw new NumberFormatException("no");
            }    
            int xcoord1 = Integer.parseInt(xin.getText());
            if (xcoord1<-430 ||xcoord1>430) {
                JOptionPane pane = new JOptionPane("X coordinate value must lay between -430 and 430.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Wrong input!");
                dial.setVisible(true);
                throw new NumberFormatException("no");
            }
            int ycoord1 = Integer.parseInt(yin.getText());
            if (ycoord1<-350 ||ycoord1>350) {
                JOptionPane pane = new JOptionPane("Y coordinate value must lay between -350 and 350.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Wrong input!");
                dial.setVisible(true);
                throw new NumberFormatException("no");
            }
            Color clr = null;
            switch ((String) colours.getSelectedItem()) {
                case "white": {
                    clr = Color.WHITE;
                    break;
                }
                case "yellow": {
                    clr = Color.YELLOW;
                    break;
                }
                case "orange": {
                    clr = Color.ORANGE;
                    break;
                }
                case "red": {
                    clr = Color.RED;
                    break;
                }
            }
            boolean visErth = (trfls.getSelectedItem().equals("true"));
            boolean visMoon1 = trfls2.getSelectedItem().equals("true");
            Star added = new Star(Shine, name1, xcoord1, ycoord1, visErth, visMoon1, clr);
            Gson gson = new Gson();
            String newSt = gson.toJson(added);
            try {
            for (ThreadHandler thrd : listOfThreads) {
                thrd.getOutStr().write(oldSt.getBytes());
                thrd.getOutStr().write("!".getBytes());
                thrd.getOutStr().write(newSt.getBytes());
                thrd.getOutStr().flush();
            } }
            catch (IOException exc) {
                System.out.println(exc.getMessage());
            }
            namein.setText("");
            trfls.setSelectedItem("true");
            trfls2.setSelectedItem("true");
            shinein.setText("");
            xin.setText("");
            yin.setText("");
            colours.setSelectedItem("white");
            CollectionControl.vect.remove((Star)underEdit.getUserObject());
            CollectionControl.vect.add(added);
            model.removeNodeFromParent(underEdit);
            underEdit=null;
            addStarToTree(added);
            
            editional.setVisible(false);
            mock.setVisible(true);
            currentNode = null;
            } catch (NumberFormatException exc) {
                if (!exc.getMessage().equals("no")) {
                JOptionPane pane = new JOptionPane("Make sure you input all fields and 'Shine', 'X coordinate' and 'Y coordinate' contain only numeric integer values.");
                pane.setMessageType(JOptionPane.WARNING_MESSAGE);
                JDialog dial = pane.createDialog(frame, "Wrong input!");
                dial.setVisible(true);
                }
            }
         
        });
    }
    
    private static void editPressed() {
        underEdit = currentNode;
        selected = (Star)underEdit.getUserObject();
        shinein.setText(String.valueOf(selected.getShine()));
        namein.setText(selected.toString());
        xin.setText(String.valueOf(selected.getCoordinates()[0]));
        yin.setText(String.valueOf(selected.getCoordinates()[1]));
        if (selected.getColour().equals(Color.WHITE))
            colours.setSelectedItem("white");
        else if (selected.getColour().equals(Color.YELLOW))
            colours.setSelectedItem("yellow");
        else if (selected.getColour().equals(Color.ORANGE))
            colours.setSelectedItem("orange");
        else if (selected.getColour().equals(Color.RED))
            colours.setSelectedItem("red");
        if (!selected.visibleFromEarth())
            trfls.setSelectedItem("false");
        
        if (!selected.visibleFromMoon())
            trfls2.setSelectedItem("false");
        mock.setVisible(false);
        additional.setVisible(false);
        editional.setVisible(true);
    }
    
    public static String[] getOccNames() {
        if (!listOfThreads.isEmpty()) {
        String[]names = new String[listOfThreads.size()];
        int index =0;
        for (ThreadHandler thrd : listOfThreads) {
            names[index] = thrd.userName;
            index++;
        }
        return names;
    }
    else return new String[0];
    }
    
}