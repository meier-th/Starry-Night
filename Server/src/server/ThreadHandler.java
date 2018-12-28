package server;

import labLib.Star;
import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;

public class ThreadHandler extends Thread {

    public final Socket sock;
    public static String response;
    private boolean accepted;
    public String userName;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;
    private byte[] mock = new byte[5];

    public ThreadHandler(Socket ch) {
        this.sock = ch;
        try {
            bos = new BufferedOutputStream(sock.getOutputStream());
            bis = new BufferedInputStream(sock.getInputStream());
            userName = askName();
            Server.addName(userName);
            bis.read(mock);
            bos.write("collection".getBytes());
            bos.flush();
            bis.read(mock);
            int size = CollectionControl.vect.size();
            bos.write(String.valueOf(size).getBytes());
            bos.flush();
            bis.read(mock);
            for (Star st : CollectionControl.vect) {
                byte[] frst;
                ByteArrayOutputStream bytestr = new ByteArrayOutputStream();
                ObjectOutputStream ststr = new ObjectOutputStream(bytestr);
                ststr.writeObject(st);
                ststr.flush();
                frst = bytestr.toByteArray();
                bos.write(frst);
                bos.flush();
                bis.read(mock);
            }
        } catch (IOException exc) {
        }
    }

    public synchronized BufferedOutputStream getOutStr() {
        return this.bos;
    }

    public BufferedInputStream getInStr() {
        return this.bis;
    }

    @Override
    public void run() {

        try {

            while (!sock.isClosed()) {

                byte[] buff = new byte[16];
                bis.read(buff);

                String command = new String(buff, Charset.forName("UTF-8"));
                command = command.trim();

                if (command.equalsIgnoreCase("star")) {
                    byte[] get = new byte[1024];
                    bis.read(get);
                    ByteArrayInputStream bs = new ByteArrayInputStream(get);
                    ObjectInputStream oInStr = new ObjectInputStream(bs);
                    Object obj = oInStr.readObject();
                    Star str = (Star) obj;
                    CollectionControl.vect.add(str);
                    Server.addStarToTree(str);
                    Server.added = str;
                    for (ThreadHandler thrd : Server.listOfThreads) {

                        thrd.getOutStr().write("add".getBytes());
                        thrd.getOutStr().flush();
                        /*thrd.getInStr().read(mock);
                        byte[] frst;
                        ByteArrayOutputStream bytestr = new ByteArrayOutputStream();
                        ObjectOutputStream ststr = new ObjectOutputStream(bytestr);
                        ststr.writeObject(str);
                        ststr.flush();
                        frst = bytestr.toByteArray();
                        thrd.getOutStr().write(frst);
                        thrd.getOutStr().flush();*/
                    }
                    

                } else if (command.equalsIgnoreCase("closed")) {
                    sock.close();
                    Server.logs.append("User " + this.userName + " disconnected!\n");
                    synchronized (Server.listModel) {
                        Server.listModel.removeElement(this.userName);
                    }
                    this.userName = null;
                    break;

                } else if (command.equals("which one?")) {
                    byte[] frst;
                    ByteArrayOutputStream bytestr = new ByteArrayOutputStream();
                    ObjectOutputStream ststr = new ObjectOutputStream(bytestr);
                    ststr.writeObject(Server.deleted);
                    ststr.flush();
                    frst = bytestr.toByteArray();
                    bos.write(frst);
                    bos.flush();
                } else if (command.equals("add?")) {
                    byte[] frst;
                    ByteArrayOutputStream bytestr = new ByteArrayOutputStream();
                    ObjectOutputStream ststr = new ObjectOutputStream(bytestr);
                    ststr.writeObject(Server.added);
                    ststr.flush();
                    frst = bytestr.toByteArray();
                    bos.write(frst);
                    bos.flush();
                }
            }
        } catch (IOException | ClassNotFoundException exc) {
            System.out.println(exc.getMessage());

        }
    }

    public void sendCollec() {
        CollectionControl.vect.sort(new StarComparator());
        try {
            bos.write("update".getBytes());
            bos.flush();
            Thread.sleep(30);
            Object[] arrSt = new Object[CollectionControl.vect.size()];
            for (int i = 0; i < arrSt.length; i++) {
                arrSt[i] = CollectionControl.vect.get(i);
            }
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream oustr = new ObjectOutputStream(bs);
            oustr.writeObject(arrSt);
            oustr.flush();
            byte[] serialized = bs.toByteArray();
            String amount = String.valueOf(serialized.length);
            bos.write(amount.getBytes());
            bos.flush();
            Thread.sleep(4);
            bos.write(serialized);
            bos.flush();
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        } catch (InterruptedException exc) {

        }
    }

    private String askName() throws IOException {
        String nameline;
        while (true) {
            byte[] name = new byte[20];
            accepted = true;
            bis.read(name);
            nameline = new String(name);
            nameline = nameline.trim();
            String[] occNames = Server.getOccNames();

            for (String nm : occNames) {
                if (nameline.equals(nm)) {
                    accepted = false;
                }
            }
            if (accepted) {
                break;
            } else {
                bos.write("oc".getBytes());
            }
            bos.flush();
        }
        byte[] bt = "ac".getBytes();
        bos.write(bt);
        bos.flush();
        try {
            Thread.sleep(20);
        } catch (InterruptedException exc) {

        }
        return nameline;

    }
}
