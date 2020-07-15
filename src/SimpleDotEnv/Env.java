package SimpleDotEnv;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Env {

    private static final String fileName = ".env";
    private static String path = "";

    private static final Map<String, String> envs = new HashMap<>();

    public static void setPath(String path) {
        Env.path = path;
    }

    public static String get(String nameEnv) {
        try {
            if (envs.isEmpty()) {
                setEnvs();
            }

            return envs.get(nameEnv);
        } catch (Exception e) {
            return null;
        }
    }

    private static void setEnvs() {
        File file = new File(path + fileName);
        if (file.exists()) {
            String fileText = getFileText(file);
            String[] fileTextRows = fileText.replaceAll("\r\n", "\n").replaceAll("\r", "\n").split("\n");
            for (String fileTextRow : fileTextRows) {
                String[] env = fileTextRow.split("=", 2);
                if (env.length == 2) {
                    envs.put(env[0], env[1]);
                }
            }
        }
    }

    public static Map<String, String> getEnvs() {
        if (envs.isEmpty()) {
            setEnvs();
        }
        return envs;
    }

    public static String getFileText(File file) {
        try {
            file = file.getAbsoluteFile();
            Scanner scan = new Scanner(file, "latin1");
            String text = scan.useDelimiter("\\A").next();
            scan.close();
            return text;
        } catch (Exception e) {
            return "";
        }
    }
}
