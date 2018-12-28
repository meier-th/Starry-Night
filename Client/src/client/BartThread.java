package client;
public class BartThread extends Thread{
    @Override
    public void run () {
        try {
            Thread.sleep(1350);
        } catch (InterruptedException exc){}
        Client.withBart.setVisible(false);
        try {
            Thread.sleep(4650);
        } catch (InterruptedException exc){}
        
        
        
    }
    
}
