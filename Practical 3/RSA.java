import java.math.BigInteger;
import java.util.Scanner;

public class RSA {
    private int p, q, n, e, d;

    RSA(int p, int q, int e) {
        this.p = p;
        this.q = q;
        this.n = p * q;
        this.e = e;
        this.d = this.calculatePrivateKey();
    }

    private int calculatePrivateKey() {
        int phi = (p - 1) * (q - 1);
        BigInteger eBig = BigInteger.valueOf(e);
        BigInteger phiBig = BigInteger.valueOf(phi);
        BigInteger dBig = eBig.modInverse(phiBig);
        return dBig.intValue();
    }

    public String encryptString(String text) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            BigInteger cBig = BigInteger.valueOf((int) c);
            BigInteger enc = cBig.modPow(BigInteger.valueOf(e), BigInteger.valueOf(n));
            encrypted.append(enc).append(" ");
        }
        return encrypted.toString();
    }

    public String decryptString(String ciphertext) {
        StringBuilder plaintextBuilder = new StringBuilder();
        String[] encryptedChars = ciphertext.split(" ");
        for (String encryptedChar : encryptedChars) {
            if (!encryptedChar.equals(" ")) {
                BigInteger encrypted = new BigInteger(encryptedChar);
                BigInteger decrypted = encrypted.modPow(BigInteger.valueOf(d), BigInteger.valueOf(n));
                char plaintextChar = (char) decrypted.intValue();
                plaintextBuilder.append(plaintextChar);
            }
        }
        return plaintextBuilder.toString();
    }

    private static String toAscii(String text) {
        StringBuilder as = new StringBuilder();
        for (char c : text.toCharArray())
            as.append((int) c).append(" ");
        return as.toString();
    }

    private static String toChar(String text) {
        StringBuilder as = new StringBuilder();
        for (String ascii : text.split(" "))
            as.append((char) Integer.parseInt(ascii));
        return as.toString();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("\n1. Encrypt\n2. Decrypt\n\nEnter your choice: ");
        int choice = input.nextInt();
        System.out.print("Enter values for p, q & e: ");
        int p = input.nextInt();
        int q = input.nextInt();
        int e = input.nextInt();
        RSA rsa = new RSA(p, q, e);
        input.nextLine();
        if (choice == 1) {
            System.out.print("Enter your message: ");
            String message = input.nextLine();
            String cipher = rsa.encryptString(toAscii(message));
            System.out.println("Encrypted message: " + cipher);
        } else if (choice == 2) {
            System.out.print("Enter your message: ");
            String message = input.nextLine();
            String decipher = rsa.decryptString(message);
            System.out.println("Decrypted message: " + toChar(decipher));
        }
        input.close();
    }
}