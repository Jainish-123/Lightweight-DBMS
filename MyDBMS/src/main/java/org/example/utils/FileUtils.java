package org.example.utils;

import org.example.exceptions.FileHandlingException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    /**
     * This method will create directory if it not exists.
     * @param dirPath
     */
    public static void createDirIfNotExists(String dirPath)
    {
        File dir = new File(dirPath);
        if(!dir.exists())
        {
            boolean creationFlag = dir.mkdirs();
            if(!creationFlag)
            {
                throw new FileHandlingException("Directory is not created");
            }
        }
    }

    /**
     * This method will give directory name for given directory path.
     * @param dirPath
     * @return
     */
    public static String getDirectory(String dirPath)
    {
        File rootDir = new File(dirPath);
        ArrayList<File> directories = new ArrayList<File>(Arrays.asList(rootDir.listFiles(File::isDirectory)));
        return directories.isEmpty()?null:directories.get(0).getName();
    }

    /**
     * This method will write provided content into txt and csv file.
     * https://sentry.io/answers/write-to-file-java/
     * @param filePath
     * @param fileData
     * @param append
     */
    public static void writeFile (String filePath, List<List<String >> fileData, boolean append)
    {
        try
        {
            FileWriter fw = new FileWriter(filePath, append);
            BufferedWriter bw = new BufferedWriter(fw);

            if(filePath.contains("csv"))
            {
                for(List<String> rowData : fileData)
                {
                    String line = String.join(",", rowData);
                    bw.write(line);
                    bw.newLine();
                }
            }
            else
            {
                for(List<String> rowData : fileData)
                {
                    String line = String.join(ConstantUtils.DELIMINATOR, rowData);
                    bw.write(line);
                    bw.newLine();
                }
            }

            bw.flush();
            bw.close();

        } catch (IOException ex) {
            throw new FileHandlingException(ex.getMessage());
        }
    }

    /**
     * This method will read content from txt file.
     * @param filePath
     * @return
     */

    public static List<List<String>> readFile(String filePath)
    {
        List<List<String>> fileData = new ArrayList<>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String nextLine;
            while((nextLine = br.readLine()) != null)
            {
                List<String> rowData = Arrays.asList(nextLine.split(ConstantUtils.DELIMINATOR));
                fileData.add(rowData);
            }
            br.close();
        } catch (IOException ex) {
            throw new FileHandlingException(ex.getMessage());
        }

        return fileData;
    }
}
