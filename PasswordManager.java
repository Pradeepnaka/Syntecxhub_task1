package yntecx;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordManager {

    private static final String SECRET_KEY = "SyntecxHubSecret"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Master Password to Access Manager: ");
        String inputMaster = scanner.nextLine();

        if (!inputMaster.equals("admin123")) {
            System.out.println("Wrong Master Password! Access Denied.");
            scanner.close();
            return;
        }

        System.out.println("\n--- Access Granted to Password Manager ---");
        
        while (true) {
            System.out.println("\n1. Add New Password");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                System.out.print("Enter Website Name: ");
                String website = scanner.nextLine();

                System.out.print("Enter Username: ");
                String username = scanner.nextLine();

                System.out.print("Enter Password: ");
                String password = scanner.nextLine();

                String encryptedPassword = encrypt(password);

                String record = "Website: " + website + " | Username: " + username + " | Encrypted Password: " + encryptedPassword + "\n";
                
                saveToFile(record);
                System.out.println("Password Saved Securely!");

            } else if (choice == 2) {
                System.out.println("Exiting Password Manager. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
        scanner.close();
    }

    public static String encrypt(String plainText) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes); 
        } catch (Exception e) {
            return "Encryption Error: " + e.getMessage();
        }
    }
    private static void saveToFile(String record) {
        try (FileWriter fw = new FileWriter("log_passwords.txt", true)) {
            fw.write(record);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
