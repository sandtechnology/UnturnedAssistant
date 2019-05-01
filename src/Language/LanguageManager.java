package Language;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Locale;

public class LanguageManager {
    private final static LanguageManager languageManager = new LanguageManager();
    private HashMap<String, String> languageMappings = new HashMap<>();

    private LanguageManager() {
        Path defaultPath;
        Path path;
        try {
            defaultPath = Paths.get(getClass().getResource("/assets/lang/en_US.lang").toURI());
            path = Paths.get(getClass().getResource("/assets/lang/" + Locale.getDefault().toString() + ".lang").toURI());
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
            defaultPath = path = Paths.get("");
        }
        if (Files.exists(path)) {
            loadLanguageFiles(path);
        }
        else {
            loadLanguageFiles(defaultPath);
        }
    }

    public static String getLocalizedMessage(String key) {
        return languageManager.languageMappings.getOrDefault(key, key);
    }

    private void loadLanguageFiles(Path path) {
        try {
            Files.readAllLines(path).forEach(x ->
            {
                String[] strings = x.split("=", 2);
                if (strings.length == 2) {
                    languageMappings.put(strings[0], strings[1]);
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
