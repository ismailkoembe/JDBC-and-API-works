package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.*;

public class DynamicQuerryData {
        //connection string
        String dbUrl = "jdbc:oracle:thin:@3.90.148.246:1521:xe";
        String dbUsername = "hr";
        String dbPassword = "hr";

    @Test
    public void queryresult() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUsername,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from employees");
        //get the resultset object metadata
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        //-------DYNAMIC LIST RESULT FOR ALL QUERIES--------------
        //main list
        List<Map<String,Object>> queryData = new ArrayList<>();
        //number of columns
        int colCount = rsMetadata.getColumnCount();
        //loop through all rows
        while(resultSet.next()){
            Map<String,Object> row = new HashMap<>();
            //some code to put values
            for (int i = 1; i <=colCount ; i++) {
                row.put(rsMetadata.getColumnName(i),resultSet.getObject(i));
            }
            //put the row inside the list
            queryData.add(row);
        }
        //print the table
        for (Map<String, Object> row : queryData) {
            System.out.println(row.toString());
        }
        //close connections
        resultSet.close();
        statement.close();
        connection.close();
    }

}

