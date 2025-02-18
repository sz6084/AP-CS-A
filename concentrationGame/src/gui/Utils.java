package gui;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Utils {
    public static String runCmd(String cmd) {
        try {
            String output = "";
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", cmd});
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output += line + "\n";
            }
            return output;
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void playSound(String filename) {
        String cmd = "aplay " + filename;
        Thread soundThread = new Thread(() -> {
            runCmd(cmd);
        });
        soundThread.start();
    }
}
