import com.sun.tools.javac.Main;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class MonitorGUI {

    static HashMap<Socket, Long> serverTime = new HashMap<>();

    static JProgressBar procesbelastingProgressBar = new JProgressBar();
    static JProgressBar beschikbaarheidProgressBar = new JProgressBar();
    static JProgressBar diskUsageProgressBar = new JProgressBar();

    static ServerSocket serverSocket = null;

    static JTextField UptimeField = new JTextField(3);
    static JTextField DowntimeField = new JTextField(3);

    static Instant eersteVerbindingstijd = null;

    static JCheckBox beschikbaarheidsCheck = new JCheckBox();


    public static void main(String[] args) {

        UptimeField.setText("0 seconden");
        DowntimeField.setText("0 seconden");

        procesbelastingProgressBar.setValue(0);
        beschikbaarheidProgressBar.setValue(0);
        diskUsageProgressBar.setValue(0);

        procesbelastingProgressBar.setString("");
        beschikbaarheidProgressBar.setString("");
        diskUsageProgressBar.setString("");

        beschikbaarheidsCheck.setSelected(false);

        JFrame frame = new JFrame("Graphic Tracer Monitoring");
        frame.setSize(720, 450);

        frame.setLayout(new MigLayout("", "[grow,fill]"));

        frame.add(new JLabel("Beschikbaar"));
        frame.add(beschikbaarheidsCheck);

        procesbelastingProgressBar.setStringPainted(true);
        procesbelastingProgressBar.setMaximum(100);

        frame.add(procesbelastingProgressBar, "skip 5, wrap");

        frame.add(new JLabel("Diskruimte"));

        beschikbaarheidProgressBar.setStringPainted(true);
        beschikbaarheidProgressBar.setMaximum(100);

        frame.add(diskUsageProgressBar, "skip 1");

        frame.add(new JLabel("Uptime"), "skip 3");
        frame.add(UptimeField, "wrap");

        frame.add(beschikbaarheidProgressBar, "skip 2");

        frame.add(new JLabel("Downtime"), "skip 3");
        frame.add(DowntimeField, "wrap");


        diskUsageProgressBar.setStringPainted(true);
        diskUsageProgressBar.setMaximum(100);

//        new Thread(() -> {
//            while (true) {
//                diskUsageProgressBar.setValue(new Random().nextInt(0, 100));
//                beschikbaarheidProgressBar.setValue(new Random().nextInt(0, 100));
//                procesbelastingProgressBar.setValue(new Random().nextInt(0, 100));
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(6789);
                while (true) {
                    handleClient(serverSocket.accept());
                }

            } catch (Exception t) {
                t.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Er is een fout opgetreden!");
            }
        }).start();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                if(serverSocket != null){
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.exit(0);
            }
        });

        Thread beschikbaarheidThread = new Thread(() -> {


            int totalTicks = 0;
            int ticksAlive = 0;

            Instant laatsteUpdate = null;

            while(true){
                boolean wasOnline = false;
                if(!serverTime.isEmpty()){
                    long time = serverTime.values().iterator().next();
                    long delta = System.currentTimeMillis() - time;

                    if(delta < 3000){
                        ticksAlive++;
                        wasOnline = true;
                        laatsteUpdate = Instant.now();
                    }
                }
                if(!wasOnline && laatsteUpdate != null){
                    DowntimeField.setText((Duration.between(laatsteUpdate, Instant.now())).getSeconds()+" seconden");
                    beschikbaarheidsCheck.setSelected(false);
                } else {
                    DowntimeField.setText("0 seconden");
                }

                totalTicks++;
                double beschikbaarheid = (ticksAlive * 100d) / totalTicks;
                beschikbaarheidProgressBar.setValue((int) beschikbaarheid);

                beschikbaarheidProgressBar.setString("Beschikbaarheid " + String.format("%.2f", beschikbaarheid) + " %");

                if(eersteVerbindingstijd != null && wasOnline){
                    UptimeField.setText((Duration.between(eersteVerbindingstijd, Instant.now())).getSeconds()+" seconden");
                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        beschikbaarheidThread.start();

        JButton terugKnop = new JButton("Terug");
        frame.add(new JLabel(), "push, wrap");
        frame.add(terugKnop);
        terugKnop.addActionListener((ef)->{
            Arrays.stream(StartPagina.getFrames()).forEach(frame1 -> frame1.dispose());
            eersteVerbindingstijd = null;
            beschikbaarheidThread.stop();
            try {
                serverSocket.close();
                for(Socket socket : serverTime.keySet()){
                    socket.close();
                }
                serverTime.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new Thread(StartPagina::new).start();
            frame.dispose();
        });
//        frame.pack();

        frame.setVisible(true);
    }



    public static void handleClient(Socket socket) {
        serverTime.put(socket, System.currentTimeMillis());

        eersteVerbindingstijd = Instant.now();

        try {
            var input = new Scanner(new BufferedInputStream(socket.getInputStream()));
            while (true) {
                while (!input.hasNextLine()) {
                    Thread.yield();
                }
//                System.out.println("Binnen: " + input.nextLine());
                String lijn = input.nextLine();
                String[] parts = lijn.split(" ");

                if (parts.length != 2){
                    JOptionPane.showMessageDialog(null, "Een verouderde monitorserver probeert contact te maken");
                    return;
                }
                double processorbelasting = (Double.parseDouble(parts[0])*100);
                double diskUsage = (Double.parseDouble(parts[1])*100);

                beschikbaarheidsCheck.setSelected(true);

                procesbelastingProgressBar.setValue((int) processorbelasting);
                procesbelastingProgressBar.setString("Procesbelasting " + String.format("%.2f", processorbelasting) + " %");

                diskUsageProgressBar.setValue((int) diskUsage);
                diskUsageProgressBar.setString("Gebruikt " + String.format("%.2f", diskUsage) + " %");

                serverTime.put(socket, System.currentTimeMillis());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
