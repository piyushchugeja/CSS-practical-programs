import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class PlayfairCipher {
    private char[][] keyMatrix;
    public PlayfairCipher(String key) {
        initializeKeyMatrix(key);
    }
    private void initializeKeyMatrix(String key) {
        keyMatrix = new char[5][5];
        String keyWithoutDuplicates = removeDuplicateChars(key + "ABCDEFGHIKLMNOPQRSTUVWXYZ");
        int k = 0;
        for (int i = 0; i < 5; i++) 
            for (int j = 0; j < 5; j++) 
                keyMatrix[i][j] = keyWithoutDuplicates.charAt(k++);
    }
    private String removeDuplicateChars(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);
            if (result.indexOf(String.valueOf(currentChar)) == -1)
                result.append(currentChar);
        }
        return result.toString();
    }
    private int[] getPosition(char ch) {
        int[] position = new int[2];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keyMatrix[i][j] == ch) {
                    position[0] = i;
                    position[1] = j;
                    return position;
                }
            }
        }
        return null;
    }
    private String encryptDigraph(char[][] matrix, char a, char b) {
        int[] posA = getPosition(a);
        int[] posB = getPosition(b);
        if (posA[0] == posB[0])
            return "" + matrix[posA[0]][(posA[1] + 1) % 5] + matrix[posB[0]][(posB[1] + 1) % 5];
        else if (posA[1] == posB[1])
            return "" + matrix[(posA[0] + 1) % 5][posA[1]] + matrix[(posB[0] + 1) % 5][posB[1]];
        else
            return "" + matrix[posA[0]][posB[1]] + matrix[posB[0]][posA[1]];
    }
    private String decryptDigraph(char[][] matrix, char a, char b) {
        int[] posA = getPosition(a);
        int[] posB = getPosition(b);
        if (posA[0] == posB[0])
            return "" + matrix[posA[0]][(posA[1] + 4) % 5] + matrix[posB[0]][(posB[1] + 4) % 5];
        else if (posA[1] == posB[1])
            return "" + matrix[(posA[0] + 4) % 5][posA[1]] + matrix[(posB[0] + 4) % 5][posB[1]];
        else
            return "" + matrix[posA[0]][posB[1]] + matrix[posB[0]][posA[1]];
    }
    public String encrypt(String plainText) {
        StringBuilder cipherText = new StringBuilder();
        if (plainText.length() % 2 != 0) plainText+= "Z";
        char[] chars = plainText.toCharArray();
        for (int i = 0; i < plainText.length(); i += 2) 
            cipherText.append(encryptDigraph(keyMatrix, chars[i], chars[i + 1]));
        return cipherText.toString();
    }
    public String decrypt(String cipherText) {
        StringBuilder plainText = new StringBuilder();
        char[] chars = cipherText.toCharArray();
        for (int i = 0; i < cipherText.length(); i += 2) 
            plainText.append(decryptDigraph(keyMatrix, chars[i], chars[i + 1]));
        return plainText.toString();
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("1: Encrypt\n2: Decrypt\n3: Exit");
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        if (choice == 3) System.exit(0);
        System.out.print("Enter the key for Playfair cipher: ");
        input.nextLine();
        String key = input.nextLine();
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
        PlayfairCipher pc = new PlayfairCipher(key);
        if (choice == 1)
            System.out.println("Cipher text: " + pc.encrypt(message));
        else if (choice == 2)
            System.out.println("Plain text: " + pc.decrypt(message));
        input.close();
    }
}