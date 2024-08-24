package org.example.log;

import org.example.authentication.Authentication;
import org.example.utils.ConstantUtils;
import org.example.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Log {

    /**
     * This method is inserting log into csv file.
     * @param query
     */
    public static void insertLog(String query)
    {
        String userName = Authentication.userName;
        query = query.replace(',', '#');
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date currentDate = new Date();

        String filePath = ConstantUtils.DB_PATH + ConstantUtils.LOG_FILE;
        File file = new File(filePath);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        List<List<String>> fileData = new ArrayList<>();
        List<String> rowData = new ArrayList<>();
        rowData.add(userName);
        rowData.add(formatter.format(currentDate));
        rowData.add(query);
        fileData.add(rowData);
//        System.out.println(rowData);

        FileUtils.writeFile(filePath, fileData, true);
    }
}
