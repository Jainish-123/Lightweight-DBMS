package org.example.query.parser;

import org.example.query.execution.implemention.ExecuteCreate;
import org.example.query.execution.implemention.ExecuteInsert;
import org.example.query.execution.implemention.ExecuteSelect;
import org.example.query.meta.ColumnMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {

    /**
     * This method is doing initial parsing for query.
     * @param query
     */
    public static void parseQuery(String query)
    {
        if(query.isEmpty())
        {
            throw new IllegalArgumentException("Query cannot be null or empty");
        }

        String[] splitQuery = query.split("\\s+");

        if(splitQuery[0].equalsIgnoreCase("CREATE"))
        {
            parseQueryForCreate(query);
        }
        else if(splitQuery[0].equalsIgnoreCase("INSERT"))
        {
            parseQueryForInsert(query);
        }
        else if(splitQuery[0].equalsIgnoreCase("SELECT"))
        {
            parseQueryForSelect(query);
        }
        else
        {
            System.out.println("Wrong query syntax");
        }
    }

    /**
     * This method is parsing create table query.
     * @param query
     */
    private static void parseQueryForCreate(String query)
    {
        String[] splitQuery = query.split("\\s+");
        String tableName = splitQuery[2];
        if(tableName.contains("("))
        {
            tableName= tableName.substring(0, tableName.lastIndexOf('(')).toLowerCase();
        }
        String columnString = query.substring(query.indexOf('(') + 1, query.lastIndexOf(')')).toLowerCase();
//        System.out.println("Column String : " + columnString);
        String[] columnData = columnString.split(",");

        List<ColumnMeta> columnList = new ArrayList<>();

        for (String column : columnData)
        {
            String[] splitColumn = column.trim().split("\\s+");
            columnList.add(new ColumnMeta(splitColumn[0], splitColumn[1]));
        }

        if(!tableName.isEmpty() && !columnList.isEmpty())
        {
            ExecuteCreate executeCreate = new ExecuteCreate(tableName, columnList);
            executeCreate.execute();
        }
    }

    /**
     * This method is parsing insert query.
     * https://stackoverflow.com/questions/40879952/parsing-insert-sql-query-in-java-using-regular-expression
     * @param query
     */
    private static void parseQueryForInsert(String query)
    {
        String regex = "(INSERT INTO) (\\S+).*\\((.*?)\\).*(VALUES).*\\((.*?)\\)(.*\\;?)";
        Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = re.matcher(query);

        if(m.find())
        {
            String tableName = m.group(2);
            String columnNamesStr = m.group(3);
            String columnValuesStr = m.group(5);

            String[] columnNames = columnNamesStr.split(",");
            List<String> columnNamesList = new ArrayList<>();
            for(String column : columnNames)
            {
                columnNamesList.add(column.trim());
            }

            String[] columnValues = columnValuesStr.split(",");
            List<String> columnValuesList = new ArrayList<>();
            for(String column : columnValues)
            {
                columnValuesList.add(column.trim());
            }

            if(!tableName.isEmpty())
            {
                ExecuteInsert executeInsert = new ExecuteInsert(tableName, columnNamesList, columnValuesList);
                executeInsert.execute();
            }
        }
        else
        {
            System.out.println("Syntax error in insert query.");
        }
    }

    /**
     * This method is parsing select query.
     * @param query
     */
    private static void parseQueryForSelect(String query)
    {
        String regex = "";

        if(query.toUpperCase().contains("WHERE"))
        {
            regex = "(SELECT) (\\S+).* (FROM) (\\S+).*(WHERE) (\\S+).*(=)(.*\\;?)";
        }
        else
        {
            regex = "(SELECT) (\\S+).* (FROM) (\\S+).*.*(.\\;?)";
        }

        Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = re.matcher(query);

        if(m.find())
        {
            String tableName = m.group(4);
            String columnNamesStr = m.group(2);
            String[] columnNames = columnNamesStr.split(",");
            List<String> columnNamesList = new ArrayList<>();
            for(String column : columnNames)
            {
                columnNamesList.add(column.trim());
            }

            if(!tableName.isEmpty())
            {
                ExecuteSelect executeSelect = new ExecuteSelect(tableName, columnNamesList);
                executeSelect.execute();
            }
        }
        else
        {
            System.out.println("Syntax error in select query.");
        }
    }

}
