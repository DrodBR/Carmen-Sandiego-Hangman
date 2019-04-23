/**********************************************
 Workshop 4 - Task 1
 Course: JAC444 - Semester 4

 Last Name: Rodrigues
 First Name: Daniel
 ID: 113458178
 Section: SAB

 This assignment represents my own work in accordance with Seneca Academic Policy.

 Daniel Rodrigues
 Date: Feb 22, 2019
 **********************************************/

import java.util.Scanner;
import java.util.Locale;
import java.lang.Character;
import java.io.*;

public class WS4T1 {

    private static void display(int size, char[] theseStars) {
        System.out.print("(Guess) Enter a letter on word ");
        for (int i = 0; i < size; i++) {
            System.out.print(theseStars[i]);
            if (i == size - 1) {
                System.out.print(": ");
            }
        }
    }

    private static int getAndMissed(int size, char[] theseStars, char[] thisPickedChar, Scanner in) {

        int missed = 0;
        char guess;
        boolean isValid = false;
        do {
            guess = Character.toLowerCase(in.next().charAt(0));
            if (Character.isLetter(guess)) {
                isValid = true;
            } else {
                System.out.print("It is not a character. Please, try again: ");
            }
        } while (!isValid);

        for (int i = 0; i < size; i++) {
            if (theseStars[i] == guess) {
                System.out.println("\tYou already tried '" + guess + "'. Please, try again.");
            }
        }

        boolean attempt = false;
        for (int i = 0; i < size; i++) {
            if (thisPickedChar[i] == guess) {
                theseStars[i] = guess;
                attempt = true;
            }
        }
        if (!attempt) {
            System.out.println("\t'" + guess + "' is not in the word");
            missed = 1;
        }

        return missed;
    }

    public static void main(String[] args) {

        // Begin: Workshop 4 - Task 1
        String[] words = null;
        String line;
        try {
            File file = new File("hangman.txt");
            FileReader fReader = new FileReader(file);
            BufferedReader bReader = new BufferedReader(fReader);
            while((line = bReader.readLine()) != null)
                words = line.split(" ");
        }catch(Exception e) {
            System.out.println(e);
        }
        // End: Workshop 4 - Task 1

        Scanner input = new Scanner(System.in).useLocale(Locale.CANADA);

        boolean playAgain;

        System.out.println("\nIn this game, you need to guess which country Carmen Sandiego is hiding in.\n" +
                "Good Luck!\n");

        do {
            // Take one word randomly
            int indexRandom = (int) (Math.random() * words.length);
            String picked = words[indexRandom];
            String lowerPicked = picked.toLowerCase();
            // Convert word String to Char Array
            char[] pickedChar = lowerPicked.toCharArray();

            int pickedSize = pickedChar.length;

            // Word Stars
            char[] stars = new char[pickedSize];
            for (int i = 0; i < pickedSize; i++) {
                stars[i] = '*';
            }

            // Rewrite the Stars if match a letter
            boolean correct;
            int count = 0;
            int missed = 0;

            do {
                display(pickedSize, stars);
                missed += getAndMissed(pickedSize, stars, pickedChar, input);

                correct = true;
                count += 1;
                for (int i = 0; i < pickedSize; i++) {
                    if (stars[i] == '*') {
                        correct = false;
                    }
                }
            } while (!correct);

            // Prints if success
            System.out.print("\nCongratulations! You have discovered that she is hidden in " + picked + "!\n" +
                    "You missed " + missed + " time(s) from " + count + " attempts!\n" +
                    "Your final score is " + (int) (100 * (((double) (count - missed) / count))) + "%.");

            // Play Again?
            boolean playAgainValidateInput = false;
            do {
                System.out.print("\n\nDo you want to play again? (y/n): ");
                char yn = Character.toLowerCase(input.next().charAt(0));
                if (yn == 'y' || yn == 'n') {
                    playAgainValidateInput = true;
                }
                playAgain = (yn == 'y');
            } while (!playAgainValidateInput);

            System.out.println();
        } while (playAgain);

        System.out.println("Thank you for playing!");
    }
}