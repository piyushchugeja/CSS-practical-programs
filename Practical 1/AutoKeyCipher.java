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
        String message = "ATTACK AT DAWN";
        String key = "L";
        AutoKeyCipher akc = new AutoKeyCipher();
        String encryptedText = akc.encrypt(message, key);
        System.out.println("Encrypted message: " + encryptedText);
        System.out.println("Decrypted message: " + akc.decrypt(encryptedText, key));
    }
}