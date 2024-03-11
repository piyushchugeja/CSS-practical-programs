public interface EncryptionAlgorithm {
    void encrypt(String message) throws Exception;
    void decrypt(String message) throws Exception;
}