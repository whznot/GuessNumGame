import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.print("""
                \s
                Welcome to the game where you have to guess a number from 1 to 100 that I have guessed.
                For each new game you get 400 points, but for each wrong guess you will lose 25 points.
                To make the game fair, I will answer whether my number is less or more than the number you wrote.
                \s
                """);

        Scanner sc = new Scanner(System.in);
        String username;
        String password;
        int userGuess;
        String userDecision;
        int score = 0;

        while (true) {
            System.out.print("To start a game enter your username: ");
            username = sc.nextLine();

            if (isUsernameTaken(username)) {
                System.out.println("This username is already taken. If it is your account, enter the password or rewrite your username: use keyword \"back\"");
                String passwordLogin = sc.nextLine();
                if (passwordLogin.equals("back")) continue;
                else if (isCorrectPassword(passwordLogin)) {
                    System.out.println("Password is correct!");
                    break;
                } else {
                    System.out.println("Incorrect password.");
                }
            } else {
                break;
            }
        }

        System.out.print("Enter your password: ");
        password = sc.nextLine();

        Random random = new Random();
        int randomInt = random.nextInt(100) + 1;
        score += 400;

        System.out.println("I just made up a number to guess!");
        System.out.print("Enter your guess: ");
        userGuess = sc.nextInt();

        while (userGuess != randomInt) {
            if (userGuess > randomInt) {
                System.out.println("Go smaller!");
            } else {
                System.out.println("Go bigger!");
            }
            score -= 25;
            System.out.print("Enter your guess: ");
            userGuess = sc.nextInt();
        }
        System.out.println("Got it! Your final score: " + score);

        System.out.println("Let's play once more! (y/n)");
        userDecision = sc.nextLine();
        if (userDecision.equals("y")) {
            
        } else {
            System.out.println("See ya!");
            sc.close();
        }

        saveUserData(username, password, score);
    }

    private static boolean isCorrectPassword(String passwordLogin) {
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