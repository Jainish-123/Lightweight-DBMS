package org.example.query.execution.implemention;

import org.example.query.execution.ExecuteQuery;
import org.example.query.meta.ColumnMeta;
import org.example.utils.ConstantUtils;
import org.example.utils.FileUtils;

import javax.swing.plaf.synth.SynthLookAndFeel;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class ExecuteInsert implements ExecuteQuery {
    private String tableName;
    private List<String> columnNames;
    private List<String> columnValues;

    public ExecuteInsert(String tableName, List<String> columnNames, List<String> columnValues) {
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.columnValues = columnValues;
    }

    /**
     * This method is executing insert query.
     */
    @Override
    public void execute()
    {
        String db_name = FileUtils.getDirectory(ConstantUtils.DB_PATH);
        if(db_name != null && !db_name.isEmpty())
        {
            File metaFile = new File(ConstantUtils.DB_PATH  + db_name + "/" + ConstantUtils.META_DIR + this.tableName + ConstantUtils.EXTENSION);
            File dataFile = new File(ConstantUtils.DB_PATH  + db_name + "/" + ConstantUtils.DATA_DIR + this.tableName + ConstantUtils.EXTENSION);

            if(metaFile.exists() && dataFile.exists())
            {
                List<List<String>> fileReadData = FileUtils.readFile(metaFile.getAbsolutePath());
                List<String> metaColumnNames = new ArrayList<>();

                for (List<String> columnData  : fileReadData)
                {
                    metaColumnNames.add(columnData.get(0).trim());
                }

                Collections.sort(metaColumnNames);
                Collections.sort(this.columnNames);

                if(new HashSet<>(metaColumnNames).containsAll(this.columnNames) && new HashSet<>(this.columnNames).containsAll(metaColumnNames))
                {
                    List<List<String>> fileWriteData = new ArrayList<>();
                    fileWriteData.add(this.columnValues);
                    FileUtils.writeFile(dataFile.getAbsolutePath(), fileWriteData, true);
                    System.out.println("Data successfully inserted in " + tableName);
                }
                else
                {
                    System.out.println("Column names are different");
                }
            }
            else
            {
                System.out.println("Table not exists");
            }
        }
    }
}
