package org.example.query.database;

import org.example.utils.ConstantUtils;
import org.example.utils.FileUtils;

import java.io.IOException;
import java.util.Scanner;

public class DBCreation {
    private static Scanner sc = new Scanner(System.in);

    /**
     * This method is checking if database is already exists if not then it will create.
     */
    public static void createDBIfNotExists()
    {
        FileUtils.createDirIfNotExists(ConstantUtils.DB_PATH);
        String dbName = FileUtils.getDirectory(ConstantUtils.DB_PATH);

        if(dbName == null || dbName.isEmpty())
        {
            System.out.println("Enter Database Name : ");
            dbName = sc.nextLine();
        }
        else
        {
            System.out.println("Database Name : " + dbName);
        }

        FileUtils.createDirIfNotExists(ConstantUtils.DB_PATH + dbName);
        FileUtils.createDirIfNotExists(ConstantUtils.DB_PATH + dbName + ConstantUtils.META_DIR);
        FileUtils.createDirIfNotExists(ConstantUtils.DB_PATH + dbName + ConstantUtils.DATA_DIR);
    }
}
