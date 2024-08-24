package org.example.query.execution.implemention;

import org.example.exceptions.TableAlreadyExistsException;
import org.example.query.execution.ExecuteQuery;
import org.example.query.meta.ColumnMeta;
import org.example.utils.ConstantUtils;
import org.example.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExecuteCreate implements ExecuteQuery {

    private String tableName;
    private List<ColumnMeta> columnList;

    public ExecuteCreate(String tableName, List<ColumnMeta> columnList) {
        this.tableName = tableName;
        this.columnList = columnList;
    }

    /**
     * This method is executing create query.
     */
    @Override
    public void execute()
    {
        try
        {
            String db_name = FileUtils.getDirectory(ConstantUtils.DB_PATH);
            if(db_name != null && !db_name.isEmpty())
            {
                File metaFile = new File(ConstantUtils.DB_PATH  + db_name + "/" + ConstantUtils.META_DIR + this.tableName + ConstantUtils.EXTENSION);
                File dataFile = new File(ConstantUtils.DB_PATH  + db_name + "/" + ConstantUtils.DATA_DIR + this.tableName + ConstantUtils.EXTENSION);

                if(!metaFile.exists() || !dataFile.exists())
                {
                    metaFile.createNewFile();
                    dataFile.createNewFile();

                    List<List<String>> fileData = new ArrayList<>();
                    for(ColumnMeta column : this.columnList)
                    {
                        List<String> rowData = Arrays.asList(column.getClmName(), column.getDataType());
                        fileData.add(rowData);
                    }
                    FileUtils.writeFile(metaFile.getAbsolutePath(), fileData, false);
                    System.out.println(this.tableName + " successfully created.");
                }
                else
                {
                    throw new TableAlreadyExistsException(this.tableName + " already exists.");
                }
            }
        } catch (IOException ex)
        {
            System.out.println(this.tableName + " not created!! " + ex.getMessage());
        }
    }
}
