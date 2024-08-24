package org.example.query.execution.implemention;

import org.example.query.execution.ExecuteQuery;
import org.example.utils.ConstantUtils;
import org.example.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExecuteSelect implements ExecuteQuery {
    private String tableName;
    private List<String> columnNames;

    public ExecuteSelect(String tableName, List<String> columnNames) {
        this.tableName = tableName;
        this.columnNames = columnNames;
    }

    /**
     * This method is executing select query.
     */
    @Override
    public void execute() {
        String db_name = FileUtils.getDirectory(ConstantUtils.DB_PATH);
        if(db_name != null && !db_name.isEmpty())
        {
            File metaFile = new File(ConstantUtils.DB_PATH  + db_name + "/" + ConstantUtils.META_DIR + this.tableName + ConstantUtils.EXTENSION);
            File dataFile = new File(ConstantUtils.DB_PATH  + db_name + "/" + ConstantUtils.DATA_DIR + this.tableName + ConstantUtils.EXTENSION);

            if(metaFile.exists() && dataFile.exists())
            {
                List<List<String>> metafileReadData = FileUtils.readFile(metaFile.getAbsolutePath());
                List<String> metaColumnNames = new ArrayList<>();

                for (List<String> columnData  : metafileReadData)
                {
                    metaColumnNames.add(columnData.get(0).trim());
                }

                List<List<String>> datafileReadData = datafileReadData = FileUtils.readFile(dataFile.getAbsolutePath());

                if(this.columnNames.size() == 1 && this.columnNames.get(0).equalsIgnoreCase("*"))
                {
                    for(String column : metaColumnNames)
                    {
                        System.out.print(column + "\t");
                    }
                    System.out.println();
                    for(List<String> row : datafileReadData)
                    {
                        for(String column : row)
                        {
                            System.out.print(column + "\t");
                        }
                        System.out.println();
                    }
                }
                else
                {
                    List<Integer> indexes = new ArrayList<>();
                    int i = 0;
                    List<List<String>> filteredData = new ArrayList<>();

                    for(String column : this.columnNames)
                    {
                        System.out.print(column + "\t");
                    }

                    System.out.println();

                    for(String column : this.columnNames)
                    {
                        indexes.add(metaColumnNames.indexOf(column));
                    }

                    for(List<String> row : datafileReadData)
                    {
                        i=0;
                        for(String column : row)
                        {
                            if(row.indexOf(column) == indexes.get(i))
                            {
                                System.out.print(column + "\t");
                                i++;
                                if(indexes.size() == i)
                                {
                                    break;
                                }
                            }
                        }
                        System.out.println();
                    }
                }
            }
            else
            {
                System.out.println("Table not exists");
            }
        }
    }
}
