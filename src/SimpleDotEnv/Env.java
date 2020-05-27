package SimpleDotEnv;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Env {

    private static final String fileName = ".env";
    private static String path = "";

    private static final List<String[]> envs = new ArrayList<>();

    public static void setPath(String path) {
        Env.path = path;
    }

    public static String get(String nameEnv) {
        try {
            if (envs.isEmpty()) {
                setEnvs();
            }

            String[] env = envs.stream().filter(e -> e[0].equals(nameEnv)).findFirst().get();

            return env[1];
        } catch (Exception e) {
            return "";
        }
    }

    private static void setEnvs() {
        File file = new File(path + fileName);
        if (file.exists()) {
            String fileText = getFileText(file);
            String[] fileTextRows = fileText.split("\n");
            for (String fileTextRow : fileTextRows) {
                String[] env = fileTextRow.split("=", 2);
                if (env.length == 2) {
                    envs.add(env);
                }
            }
        }
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
