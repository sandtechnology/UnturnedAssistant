package Language;

import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class LanguageManager {
    private final static LanguageManager languageManager = new LanguageManager();
    private HashMap<String, String> languageMappings = new HashMap<>();

    private LanguageManager() {
        loadLanguageFiles(Locale.getDefault().toString());
    }

    public static String getI18nText(String key) {
        return languageManager.languageMappings.getOrDefault(key, key);
    }


    private void loadLanguageFiles(String locale) {
        Scanner scanner = new Scanner(getClass().getResourceAsStream("/assets/lang/" + locale + ".lang"), "UTF-8").useDelimiter("\n");
        while (scanner.hasNext()) {
            String[] strings = scanner.next().split("=", 2);
                if (strings.length == 2) {
                    languageMappings.put(strings[0], strings[1]);
                }
        }
        //fallback to zh_CN
        if (languageMappings.isEmpty()) {
            loadLanguageFiles("zh_CN");
        }
    }
}
