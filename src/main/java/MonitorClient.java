import com.sun.management.OperatingSystemMXBean;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Scanner;

public class MonitorClient {

    public static void main(String[] args) throws Exception {
        System.out.println("Verbinden met: 192.168.10.21");
        for (int i = 0; i < args.length; ++i)
            System.out.printf("%d: %s\n", i, args[i]);
        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
        var output = socket.getOutputStream();
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        while (true) {
            double CPUload = 0;

            int sampleRate = 20;
            for (int i = 0; i < sampleRate; i++){
                CPUload+= osBean.getCpuLoad();
//                Thread.sleep(1000/sampleRate);
            }
            String message = (CPUload/sampleRate) + " " + calucateDiskusage() + "\n";
            output.write(message.getBytes(StandardCharsets.UTF_8));
            output.flush();
        }
    }

    public static double calucateDiskusage(){
        double totalUsablespace = 0;
        double totalSpace = 0;

        NumberFormat nf = NumberFormat.getNumberInstance();
        for (Path root : FileSystems.getDefault().getRootDirectories()) {

            try {
                FileStore store = Files.getFileStore(root);
                if(store.type().equals("CDFS"))
                    continue;
                totalUsablespace+= store.getUsableSpace();
                totalSpace+= store.getTotalSpace();
            } catch (IOException ignored) {
            }
        }
        return totalUsablespace/totalSpace;
    }

}
