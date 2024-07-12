import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.print("""
                ======================================================================
                Welcome to the game where you have to guess a number from 1 to 100 that I have guessed.
                For each new game you get 400 points, but for each wrong guess you will lose 25 points.
                To make the game fair, I will answer whether my number is less or more than the number you wrote.
                ======================================================================
                """);

        Scanner sc = new Scanner(System.in);
        String username;
        String password = "";
        int score = 0;

        while (true) {
            System.out.print("To start a game enter your username: ");
            username = sc.nextLine();

            if (isUsernameTaken(username)) {
                System.out.println("This username already exists. If it's yours, enter the password. Otherwise, reenter username using \"back\" keyword");
                String passwordLogin = sc.nextLine();
                if (passwordLogin.equalsIgnoreCase("back")) continue;
                if (isCorrectPassword(username, passwordLogin)) {
                    System.out.println("Password is correct!");
                    break;
                }
                System.out.println("Incorrect password.");
            } else {
                System.out.print("Enter your password: ");
                password = sc.nextLine();
                break;
            }
        }

        do {
            Random random = new Random();
            int randomInt = random.nextInt(100) + 1;
            score += 400;

            System.out.println("I just made up a number to guess!");
            int userGuess = getUserGuess(sc);

            while (userGuess != randomInt) {
                System.out.println(userGuess > randomInt ? "Go smaller!" : "Go bigger!");
                score -= 25;
                userGuess = getUserGuess(sc);
            }

            System.out.println("You got it! Your total score is " + score);
            System.out.println("Let's play once more! (y/n)");
        } while (sc.nextLine().equalsIgnoreCase("y"));

        System.out.println("See ya!");
        saveUserData(username, password, score);
        sc.close();
    }

    private static int getUserGuess(Scanner sc) {
        while (true) {
            System.out.print("Enter your guess: ");
            if (sc.hasNextInt()) {
                int guess = sc.nextInt();
                if (guess >= 1 && guess <= 100) {
                    return guess;
                } else {
                    System.out.print("Enter a number between 1 and 100");
                }
            } else {
                System.out.print("Enter a valid number: ");
                sc.next();
            }
        }
    }

    private static boolean isCorrectPassword(String username, String passwordLogin) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(passwordLogin)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Data reading error " + e.getMessage());
        }
        return false;
    }

    private static boolean isUsernameTaken(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("accounts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Data reading error " + e.getMessage());
        }
        return false;
    }

    private static void saveUserData(String username, String password, int score) {
        try {
            FileWriter writer = new FileWriter("accounts.txt", true);
            writer.write(username + "," + password + "," + score + System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            System.err.println("Saving data error " + e.getMessage());
        }
    }
}