import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Keyed {
    private String key;
    private HashMap<Character, Integer> map;
    
    Keyed(String key) {
        this.key = key.toUpperCase();
        map = new HashMap<>();
        for (int i = 0; i < key.length(); i++) 
            map.putIfAbsent(this.key.charAt(i), i);
    }

    public String encrypt (String message) {
        int col = map.size();
        int row = (int) Math.ceil((double) message.length() / col);
        while (message.length() < (row*col)) 
            message += "_";
        char[][] matrix = new char[row][col];
        for (int i = 0, index = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                matrix[i][j] = message.charAt(index++);
        StringBuilder result = new StringBuilder();
        for (Map.Entry <Character, Integer> entry : map.entrySet()) {
            int column = entry.getValue();
            for (int i = 0; i < row; i++) 
                result.append(matrix[i][column]);
        }
        return result.toString();
    }

    public String decrypt (String message) {
        int col = map.size();
        int row = (int) Math.ceil((double) message.length() / col);
        char[][] cipher = new char[row][col];
        for (int j = 0, index = 0; j < col; j++)
            for (int i = 0; i < row; i++) 
                cipher[i][j] = message.charAt(index++);
        int index = 0;
        for (Map.Entry<Character, Integer> entry : map.entrySet()) 
            entry.setValue(index++);
        char[][] decipher = new char[row][col];
        for (int j = 0; j < key.length(); j++) {
            int column = map.get(key.charAt(j));
            for (int i = 0; i < row; i++)
                decipher[i][j] = cipher[i][column];
        }
        
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                decrypted.append(decipher[i][j]);
        return decrypted.toString();
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("1. Encrypt\n2. Decrypt\n\nEnter your choice: ");
        int choice = input.nextInt();
        input.nextLine();
        System.out.print("Enter your message: ");
        String message = input.nextLine();
        System.out.print("Enter your key: ");
        String key = input.nextLine();

        Keyed k = new Keyed (key);
        if (choice == 1) 
            System.out.println("Encrypted message: " + k.encrypt(message));
        else if (choice == 2)
            System.out.println("Decrypted message: " + k.decrypt(message));
        else
            System.out.println("Invalid choice!");;
        input.close();
    }
}