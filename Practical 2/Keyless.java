import java.util.Scanner;
public class Keyless {
    private char[][] matrix;
    private StringBuilder message;
    private int rows, columns;
    Keyless (int column, String message) {
        this.columns = column;
        this.message = new StringBuilder(message);
        this.rows = (int) Math.ceil((double)message.length()/this.columns);
        matrix = new char[this.rows][this.columns];
        while (this.message.length() < (this.rows*this.columns))
            this.message.append('Z');
    }
    void encrypt() {
        int index = 0;
        for (int row = 0; row < this.rows; row++) 
                for (int col = 0; col < this.columns; col++) 
                    matrix[row][col] = this.message.charAt(index++);
        for (int col = 0; col < this.columns; col++) 
            for (int row = 0; row < this.rows; row++)   
                System.out.print(matrix[row][col]);
    }
    void decrypt() {
        int index = 0;
        for (int col = 0; col < this.columns; col++)
                for (int row = 0; row < this.rows; row++)
                    matrix[row][col] = this.message.charAt(index++);
        for (int row = 0; row < this.rows; row++)
            for (int col = 0; col < this.columns; col++) 
                System.out.print(matrix[row][col]);
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("1. Encrypt\n2. Decrypt\n\nEnter your choice: ");
        int choice = input.nextInt();
        input.nextLine();
        System.out.print("Enter your message: ");
        String message = input.nextLine();
        System.err.print("Enter number of columns: ");
        int cols = input.nextInt();

        Keyless k = new Keyless(cols, message);
        if (choice == 1) 
            k.encrypt();
        else if (choice == 2)
            k.decrypt();
        else
            System.out.println("Invalid choice!");;
        input.close();
    }
}