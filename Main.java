// Necessary imports
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // Initialize a global ArrayList that will store all the words from the "sgb-words.txt" file
    public static ArrayList<String> words = new ArrayList<>();

    // Turn counter to allow 6 guesses
    public static int turnCounter = 0;

    // Method to get user input about the status of each letter in the guessed word: 1 for correct position, 0 for wrong position, -1 for absent
    public static int containLetter(Scanner scanner) {
        System.out.println("If the letter is green, return 1, if yellow return 0, and if gray, return -1");
        while (true) {
            if (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                if (num >= -1 && num <= 1) {
                    return num;
                } else {
                    System.out.println("Enter a valid number (-1, 0, or 1)");
                }
            } else {
                System.out.println("Enter a valid number (-1, 0, or 1)");
                scanner.next(); // consume the invalid input
            }
        }
    }

    // This method simulates one turn of guessing 
    public static String oneTurn(String word, Scanner scanner) {

        // Initialize an ArrayList of letters to store all letters found in the guessed word
        ArrayList<String> letters = new ArrayList<>();
        int[] letterValue = new int[5];
        turnCounter++;

        // If the words list is empty, fail
        if (words.isEmpty()) {
            System.out.println("No more possible words remaining.");
            return null;
        }

        // Add letters to ArrayList and get their feedback
        for (int i = 0; i < 5; i++) {
            letters.add(String.valueOf(word.charAt(i)));
            System.out.print("Enter feedback for " + word.charAt(i) + ": ");
            letterValue[i] = containLetter(scanner);
        }

        int sum = 0;

        for (int i = 0; i < 5; i++) {
            sum += letterValue[i];
        }

        // Check if the guessed word is the same as the target word
        if (sum == 5) {
            System.out.println("Target Word Found: Mission Completed");
            return null;
        }

        // Invoke this function to remove words
        removeWords(letters, letterValue);

        // If words are not empty, guess a random word
        if (!words.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(words.size());
            return words.get(randomIndex);

        // Else, fail
        } else {
            System.out.println("No more possible words remaining.");
            return null;
        }
    }

    // Function removes words from ArrayList based on user feedback
    public static void removeWords(ArrayList<String> letters, int[] letterValue) {

        // Check if the letters contain a duplicate
        for (int i = 0; i < 5; i++) {
            char letter = letters.get(i).charAt(0);
            int finalI = i;

            // Check if letter is gray
            if (letterValue[i] == -1) {

                // If letter is gray and there are more occurrences of the letter, only remove words containing that letter in the same position
                if (countOccurrences(letters, letter) > 1) {
                    words.removeIf(word -> word.charAt(finalI) == letter);

                // Else remove all words containing that letter
                } else {
                    words.removeIf(word -> word.contains(letters.get(finalI)));
                }

            // If letter is yellow, remove all letters that do not contain that letter and words containing letter in the same position
            } else if (letterValue[i] == 0) {
                words.removeIf(word -> word.charAt(finalI) == letter || !word.contains(letters.get(finalI)));

            // If green, remove all words that do not contain that letter in the same position
            } else if (letterValue[i] == 1) {
                words.removeIf(word -> word.charAt(finalI) != letter);
            }
        }
    }

    // Count occurrences of a letter
    public static int countOccurrences(ArrayList<String> letters, char letter) {
        int count = 0;
        for (String str : letters) {
            if (str.charAt(0) == letter) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            File file = new File("sgb-words.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                words.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String currentWord = "slate";
        System.out.println("Try slate");
        String nextWord = "";

        // Run the game 6 times, if nextWord is null, exit as we have found the target word
        while (turnCounter < 6 && nextWord != null) {
            nextWord = oneTurn(currentWord, scanner);
            if (nextWord == null) {
                break;
            }
            currentWord = nextWord;
            System.out.println("Try " + currentWord);
        }
    }
}
