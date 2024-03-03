import java.math.BigInteger;
import java.util.Scanner;
public class DiffieHellman {
    private BigInteger p;
    private BigInteger g;

    DiffieHellman(int p, int g) {
        this.p = BigInteger.valueOf(p);
        this.g = BigInteger.valueOf(g);
    }

    private int calculatePublicKey (int privateKey) {
        BigInteger answer;
        if (privateKey == 1)
            answer = g;
        else 
            answer = g.modPow(BigInteger.valueOf(privateKey), p);
        return answer.intValue();
    }

    private int calculateSharedKey (int publicKey, int privateKey) {
        BigInteger answer;
        if (publicKey == 1)
            answer = g;
        else 
            answer = BigInteger.valueOf(publicKey).modPow(BigInteger.valueOf(privateKey), p);
        return answer.intValue();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in) ;
        System.out.println("Users must agree on public numbers p and g");
        System.out.print("Enter value for prime (p): ");
        int p = in.nextInt();
        System.out.print("Enter value for generator (g): ");
        int g = in.nextInt();

        DiffieHellman keyExchange = new DiffieHellman(p, g);

        System.out.print("Enter value of user A's private key: ");
        int p1 = in.nextInt();

        System.out.print("Enter value of user B's private key: ");
        int p2 = in.nextInt();

        int pK1 = keyExchange.calculatePublicKey(p1);
        int pK2 = keyExchange.calculatePublicKey(p2);
        System.out.println("Public key for user A: " + keyExchange.calculatePublicKey(p1));
        System.out.println("Public key for user B: " + keyExchange.calculatePublicKey(p2));

        System.out.println("Shared secret key for user A: " + keyExchange.calculateSharedKey(pK2, p1));
        System.out.println("Shared secret key for user B: " + keyExchange.calculateSharedKey(pK1, p2));
        in.close();
    }
}