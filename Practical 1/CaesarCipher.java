import java.util.Scanner;
import java.io.*;
public class CaesarCipher {
    private String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static Scanner input = new Scanner(System.in);
    private int key;
    private String message;
    public StringBuilder cipher;

    CaesarCipher(int key, String message) {
        this.key = key;
        this.message = message;
        cipher = new StringBuilder();
    }

    public void encrypt() {
        message = message.toUpperCase();
        for (char ch : message.toCharArray()) {
            if (!Character.isAlphabetic(ch)) {
                cipher.append(ch);
                continue;
            }
            int ciphered = ((ch - 'A') + key) % 26;
            cipher.append(upperCase.charAt(ciphered));
        }
    }

    public void decrypt() {
        message = message.toUpperCase();
        for (char ch : message.toCharArray()) {
            if (!Character.isAlphabetic(ch)) {
                cipher.append(ch);
                continue;
            }
            int ciphered = ((ch - 'A') - key + 26) % 26;
            cipher.append(upperCase.charAt(ciphered));
        }
    }

    public static void main(String[] args) {
        System.out.println("Save the message in data.txt file.");
        System.out.println("1: Encrypt\n2: Decrypt\n3: Exit");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        if (choice == 3)
            System.exit(0);
        System.out.print("Enter the key for Caesar cipher: ");
        int key = input.nextInt();

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

        CaesarCipher cc = new CaesarCipher(key, message);
        if (choice == 1)
            cc.encrypt();
        else if (choice == 2)
            cc.decrypt();
        else
            System.out.println("Invalid choice.");
        
        System.out.println("Encrypted message: " + cc.cipher.toString());
    }
}