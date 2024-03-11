import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.spec.*;
import java.util.Base64;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class DES implements EncryptionAlgorithm {
    private String key;
    private String salt;
    private File output;

    DES(String key) {
        this.key = key;
        this.salt = "12345678";
    }

    private SecretKey getKeyFromKey() throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(StandardCharsets.UTF_8), 1024, 128);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "DES");
        return secret;
    }

    private IvParameterSpec getIV() {
        return new IvParameterSpec(new byte[8]);
    }

    private void processMessage(String message, int mode) throws Exception {
        SecretKey secret = getKeyFromKey();
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(mode, secret, getIV());
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        byte[] processed;
        if (mode == Cipher.ENCRYPT_MODE) {
            processed = cipher.doFinal(bytes);
            writeToFile(Base64.getEncoder().encodeToString(processed), "encrypted.txt");
        } else {
            byte[] decodedMessage = Base64.getDecoder().decode(message);
            processed = cipher.doFinal(decodedMessage);
            writeToFile(new String(processed), "decrypted.txt");
        }
    }

    public void encrypt(String message) throws Exception {
        processMessage(message, Cipher.ENCRYPT_MODE);
    }

    public void decrypt(String message) throws Exception {
        processMessage(message, Cipher.DECRYPT_MODE);
    }

    private void writeToFile(String data, String fileName) {
        output = new File(fileName);
        try (FileWriter writer = new FileWriter(output)) {
            writer.write(data);
            System.out.println("Encrypted message written to file: " + output.getName());
        } catch (Exception e) {
            System.err.println("An error occurred while writing to file.");
        }
    }
}
