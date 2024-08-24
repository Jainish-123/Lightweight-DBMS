package org.example.authentication;

import org.example.exceptions.FileHandlingException;
import org.example.utils.ConstantUtils;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.UUID;

public class Authentication {
    private static Scanner sc = new Scanner(System.in);
    public static String userName;

    /**
     * This method will do authentication.
     */
    public static void doAuthentication()
    {
        System.out.println("Press 1 to do SignUp or Press 2 to do Login : ");
        String choice = sc.nextLine();

        System.out.println("Enter UserName : ");
        String userName = sc.nextLine();

        System.out.println("Enter Password : ");
        String password = sc.nextLine();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        String captcha = uuid.substring(0, 6);
        System.out.println("Captcha : " + captcha);

        while(true)
        {
            System.out.println("Enter above  captcha : ");
            String enteredCaptcha = sc.nextLine();
            if(captcha.equals(enteredCaptcha))
            {
                break;
            }
        }

        if(choice.equalsIgnoreCase("1"))
        {
            if(!signUp(userName, password))
            {
                doAuthentication();
            }
        }
        else if(choice.equalsIgnoreCase("2"))
        {
            if(!login(userName, password))
            {
                doAuthentication();
            }
        }
        else
        {
            System.out.println("Wrong input.");
        }
    }

    /**
     * This method will do user signup.
     * @param userName
     * @param password
     * @return
     */
    private static boolean signUp(String userName, String password)
    {
        String hashedPassword = hashPassword(password);

        String filePath = ConstantUtils.DB_PATH + ConstantUtils.CRED_FILE;

        try
        {
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            String line = userName + ConstantUtils.DELIMINATOR + hashedPassword;
            bw.write(line);
            bw.newLine();
            bw.flush();
            bw.close();

            System.out.println("SignUP successful.");
            Authentication.userName = userName;
            return true;
        }
        catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * This method will do user login.
     * @param userName
     * @param password
     * @return
     */
    private static boolean login(String userName, String password)
    {
        String hashedPassword = hashPassword(password);
        String filePath = ConstantUtils.DB_PATH + ConstantUtils.CRED_FILE;

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String nextLine;
            boolean isUserPresent = false;
            while((nextLine = br.readLine()) != null)
            {
                String[] userData = nextLine.split(ConstantUtils.DELIMINATOR);
                if(userName.equals(userData[0]) && hashedPassword.equals(userData[1]))
                {
                    isUserPresent = true;
                    System.out.println("Login Successful");
                    Authentication.userName = userName;
                    break;
                }
            }
            if(!isUserPresent)
            {
                System.out.println("Invalid username and/or password");
                return false;
            }
            return true;

        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * This method encrypt user's password.
     * https://www.javatpoint.com/java-md5-hashing-example
     * @param password
     * @return
     */
    private static String hashPassword(String password)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger bigInteger = new BigInteger(1, messageDigest);
            String hashtext = bigInteger.toString(16);
            while (hashtext.length() < 32)
            {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException ex)
        {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
