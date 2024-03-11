import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class Encryptions {
    private File dataFile;
    private String message;
    private String key;
    private String encryptionAlgorithm, hashingAlgorithm;

    public Encryptions(String key, String encryptionAlgorithm, String hashingAlgorithm) {
        this.key = key;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.hashingAlgorithm = hashingAlgorithm;
    }

    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    public void hashMessage() {
        try {
            MessageDigest hasher = MessageDigest.getInstance(hashingAlgorithm);
            hasher.update(message.getBytes(StandardCharsets.UTF_8));
            byte[] digest = hasher.digest();
            String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            System.out.println("\n" + hashingAlgorithm + " hash: " + hash + "\n");
        } catch (NoSuchAlgorithmException e) {
            System.err.println("An error occurred while hashing the message.");
        }
    }

    public void fetchMessage() {
        System.out.println("Fetching message from file: " + dataFile.getName());
        message = "";
        try (Scanner reader = new Scanner(dataFile)) {
            while (reader.hasNextLine())
                message += reader.nextLine();
        } catch (FileNotFoundException e) {
            System.err.println("An error occurred while locating the file.");
        }
    }

    public void encrypt() {
        hashMessage();
        try {
            System.out.println("Encrypting message using " + encryptionAlgorithm + " algorithm...");
            EncryptionAlgorithm algorithm = getEncryptionAlgorithm();
            algorithm.encrypt(message);
        } catch (Exception e) {
            System.err.println("An error occurred while encrypting the message.");
            e.printStackTrace();
        }
    }

    public void decrypt() {
        try {
            System.out.println("Decrypting message using " + encryptionAlgorithm + " algorithm...");
            EncryptionAlgorithm algorithm = getEncryptionAlgorithm();
            algorithm.decrypt(message);
        } catch (Exception e) {
            System.err.println("An error occurred while decrypting the message.");
            e.printStackTrace();
        }
        this.dataFile = new File("decrypted.txt");
        this.fetchMessage();
        hashMessage();
    }

    private EncryptionAlgorithm getEncryptionAlgorithm() {
        if (encryptionAlgorithm.equals("AES")) {
            return new AES(key);
        } else if (encryptionAlgorithm.equals("DES")) {
            return new DES(key);
        } else {
            System.err.println("Invalid encryption algorithm.");
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Encryptions program.");
        String hashingAlgorithm, encryptionAlgorithm, key;
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("1. Encrypt\n2. Decrypt\n3. Exit\n");
            System.out.print("Enter your choice: ");
            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                System.out.print("Save the message to a file (data.txt) and press enter...");
                input.nextLine();
                File dataFile = new File("data.txt");
                System.out.print("Enter the encryption algorithm (AES/DES): ");
                encryptionAlgorithm = input.nextLine();
                System.out.print("Enter the hashing algorithm (MD5/SHA-1): ");
                hashingAlgorithm = input.nextLine();
                System.out.print("Enter the key: ");
                key = input.nextLine();
                Encryptions encryptions = new Encryptions(key, encryptionAlgorithm, hashingAlgorithm);
                encryptions.setDataFile(dataFile);
                encryptions.fetchMessage();
                encryptions.encrypt();
            } else if (choice == 2) {
                System.out.print("Save the message to a file (encrypted.txt) and press enter... ");
                input.nextLine();
                File dataFile = new File("encrypted.txt");
                System.out.print("Enter the encryption algorithm (AES/DES): ");
                encryptionAlgorithm = input.nextLine();
                System.out.print("Enter the hashing algorithm (MD5/SHA-1): ");
                hashingAlgorithm = input.nextLine();
                System.out.print("Enter the key: ");
                key = input.nextLine();
                Encryptions encryptions = new Encryptions(key, encryptionAlgorithm, hashingAlgorithm);
                encryptions.setDataFile(dataFile);
                encryptions.fetchMessage();
                encryptions.decrypt();
            } else if (choice == 3) {
                System.out.println("Exiting the program...");
                System.exit(0);
            } else {
                System.err.println("Invalid choice.");
            }
        }
    }
}