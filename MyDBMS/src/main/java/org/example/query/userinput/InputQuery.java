package org.example.query.userinput;

import org.example.log.Log;
import org.example.query.parser.QueryParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputQuery {
    private static Scanner sc = new Scanner(System.in);

    /**
     * This method is taking queries from user and also handles transaction.
     */
    public static void getQueryFromUser()
    {
        try
        {
            System.out.println("Press any key to write query or \"exit\" to close program : ");
            boolean executionFlag = true;
            List<String> queryList = new ArrayList<>();
            while(!sc.nextLine().equalsIgnoreCase("Exit"))
            {
                String query = "";
                System.out.print("SQL > : ");
                while(sc.hasNextLine())
                {
                    String line = sc.nextLine();
                    query = query + line + "\n";
                    if(line.contains(";"))
                    {
                        Log.insertLog(query);
                        break;
                    }
                }

//                System.out.println("Your Query : \n" + query);

                if(query.trim().equalsIgnoreCase("begin transaction;"))
                {
                    executionFlag = false;
                }
                else if(query.trim().equalsIgnoreCase("commit;"))
                {
                    executionFlag = true;
                }
                else if(query.trim().equalsIgnoreCase("rollback;"))
                {
                    queryList.clear();
                    executionFlag = true;
                    System.out.println("Transaction roll backed successfully");
                }

                if(!executionFlag && !query.trim().equalsIgnoreCase("begin transaction;"))
                {
                    queryList.add(query);
                }

                if(!queryList.isEmpty() && executionFlag)
                {
                    for(String q : queryList)
                    {
                        QueryParser.parseQuery(q.trim());
                    }
                    queryList.clear();
                }
                else if(executionFlag && !query.trim().equalsIgnoreCase("rollback;"))
                {
                    QueryParser.parseQuery(query.trim());
                }

                System.out.println("Press any key to write query or \"exit\" to close program : ");
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
