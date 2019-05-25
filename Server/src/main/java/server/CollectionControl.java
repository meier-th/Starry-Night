package server;

import com.google.gson.*;
import java.io.*;
import java.util.ArrayList;

public class CollectionControl {

    private static File file;
    public static ArrayList<Star> vect = new ArrayList<>();
    private static final Gson gson = new Gson();

    public static void begin() {
        try {
            String path = "./../stars.txt";
           // System.out.println(path);
            file = new File(path);
        } catch (Exception exc) {
            System.out.println(exc.getMessage());
        }
        if ((file==null)||(!file.canRead()) || (!file.canWrite()) || (!file.exists()) || (!file.isFile())) {
            System.out.println("File can not be reached or used.");
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                int count = 0;
                while (br.ready()) {
                    String frmt = br.readLine();

                    if (frmt != null) {
                        Star a = gson.fromJson(frmt, Star.class);
                        CollectionControl.vect.add(a);
                        count++;
                    }
                }
                

            } catch (IOException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }

    public static void finish() {
        if (file==null){
            file = new File("./../stars.txt");
        }
        try (FileWriter fw = new FileWriter(file)) {
            for (int i = 0; i < vect.size(); i++) {
                String line = gson.toJson(vect.get(i), Star.class);
                fw.write(line + "\r\n");
                fw.flush();

            }
        } catch (IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
}
