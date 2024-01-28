import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AutoKeyCipher {
    private String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String encrypt(String message, String key) {
        message = message.toUpperCase();
        int length = message.length();
        String newKey = key;
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (!Character.isAlphabetic(message.charAt(i))) {
                cipher.append(message.charAt(i));
                continue;
            }
            int p = alphabets.indexOf(message.charAt(i));
            int k = alphabets.indexOf(newKey);
            int c = (p + k) % 26;
            cipher.append(alphabets.charAt(c));
            newKey = String.valueOf(message.charAt(i));
        }
        return cipher.toString();
    }

    public String decrypt(String message, String key) {
        message = message.toUpperCase();
        int length = message.length();
        String newKey = key;
        StringBuilder plainText = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (!Character.isAlphabetic(message.charAt(i))) {
                plainText.append(message.charAt(i));
                continue;
            }
            int c = alphabets.indexOf(message.charAt(i));
            int k = alphabets.indexOf(newKey);
            int p = (c - k + 26) % 26;
            plainText.append(alphabets.charAt(p));
            newKey = String.valueOf(alphabets.charAt(p));
        }
        return plainText.toString();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Save the message in data.txt file.");
        System.out.println("1: Encrypt\n2: Decrypt\n3: Exit");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        if (choice == 3)
            System.exit(0);
        System.out.print("Enter the key for Autokey cipher: ");
        input.nextLine();
        String key = input.nextLine();

        // get data from file
        File file = new File("data.txt");
        String message = "";
        try {
            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                message += fileReader.nextLine();
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }

        AutoKeyCipher akc = new AutoKeyCipher();
        if (choice == 1)
            System.out.println(akc.encrypt(message, key));
        else if (choice == 2)
            System.out.println(akc.decrypt(message, key));
        else
            System.out.println("Invalid choice.");
        
        input.close();
    }
}