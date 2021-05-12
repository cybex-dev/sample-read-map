import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.function.BiConsumer;

public class Main {

    public static void main(String[] args) throws URISyntaxException {
        new Main();
    }

    Map<String, Map<String, String>> map = new HashMap<>();

    public Main() throws URISyntaxException {
        String data = "./data";
        String data2 = "./folder/data2";

        readFile(data);
        readFile(data2);
        display();
    }

    private String toString(Map.Entry<String, String> entry) {
        return "\n\t[" + entry.getKey() + "] " + entry.getValue();
    }

    private void display() {
        map.forEach((key, value) -> {
            Optional<String> reduce = value.entrySet().stream().map(this::toString).reduce((o, o2) -> o + o2);
            String ss = "- " + key + " -" + reduce.orElse("[empty]");
            System.out.println(ss);
        });
    }

    private void readFile(String data) {
        InputStream resourceAsStream = Main.class.getResourceAsStream(data);
        if (resourceAsStream != null) {
            Scanner scanner = new Scanner(resourceAsStream);

            String key = "";
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                if (s.length() == 0) {
                    continue;
                }
                if (s.split("=").length == 2) {
                    String[] split = s.split("=");
                    map.get(key).put(split[0], split[1]);
                } else {
                    key = s.substring(1, s.length() - 1);
                    map.put(key, new HashMap<>());
                }
            }
        }
    }
}
