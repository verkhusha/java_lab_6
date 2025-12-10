import java.util.*;

public class Translator {
    private HashMap<String, String> dictionary;

    public Translator() {
        this.dictionary = new HashMap<>();
    }

    
    public void addWord(String english, String ukrainian) {
        dictionary.put(english.toLowerCase(), ukrainian);
    }


    public String translate(String phrase) {
        String[] words = phrase.toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            String translated = dictionary.getOrDefault(word, "[" + word + "]");
            result.append(translated).append(" ");
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Translator translator = new Translator();

       
        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("java", "джава");
        translator.addWord("programming", "програмування");
        translator.addWord("computer", "комп'ютер");

        System.out.println("Словник ініціалізовано: hello=привіт, world=світ, java=джава, programming=програмування, computer=комп'ютер");

        // Ввід нових слів з клавіатури
        System.out.println("\nДодайте нові слова (формат: english=ukrainian). Введіть 'stop' для завершення:");
        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("stop")) break;

            String[] parts = input.split("=");
            if (parts.length == 2) {
                translator.addWord(parts[0].trim(), parts[1].trim());
                System.out.println("Додано: " + parts[0] + " -> " + parts[1]);
            } else {
                System.out.println("Невірний формат. Використовуйте: слово=переклад");
            }
        }

    
        System.out.print("\nВведіть фразу англійською для перекладу: ");
        String phrase = scanner.nextLine();
        String translation = translator.translate(phrase);
        System.out.println("Переклад: " + translation);

        scanner.close();
    }
}