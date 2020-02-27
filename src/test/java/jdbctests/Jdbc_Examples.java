package jdbctests;

import org.testng.annotations.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Jdbc_Examples {
    String dbUrl = "jdbc:oracle:thin:@3.90.148.246:1521:xe";
    String dbUserName = "hr";
    String dbPassword = "hr";

    @Test
    public void test1() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select region_name from regions");

            while (rs.next()) {
                System.out.println(rs.getString("region_name"));
            }
            System.out.println("-------------------------------");
            rs = statement.executeQuery("select * from countries");
            while (rs.next()) {
                System.out.println(rs.getString(2));
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void CountAndNavigate() {
        try {
            Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("select * from departments");
            //how to find how many record(rows) i have for the query
            //go to last row
            rs.last();
            //get the row count
            int rowCount = rs.getRow();
            System.out.println("rowCount = " + rowCount);
            System.out.println(rs.getString("department_name"));
            //we need to move before the first row to get full list since we are at the last row right now
            rs.beforeFirst();
            System.out.println("---------------While loop starts-------------");
            while (rs.next()) {
                System.out.println(rs.getString("department_name"));
            }

            rs.close();
            st.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void metadata() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from departments");
        //get the databases related data inside the dbMetadata object
        DatabaseMetaData dbMetadata = connection.getMetaData();
        System.out.println("User: " + dbMetadata.getUserName());
        System.out.println("Database product name: " + dbMetadata.getDatabaseProductName());
        System.out.println("Database product version: " + dbMetadata.getDatabaseProductVersion());
        System.out.println("Driver: " + dbMetadata.getDriverName());
        System.out.println("Driver version: " + dbMetadata.getDriverVersion());
        //get the resultset object metadata
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        //how many column we have ?
        System.out.println("Column count:" + rsMetadata.getColumnCount());
        //column names
        //System.out.println(rsMetadata.getColumnName(8));
        //print all the column names dynamically
        int columnCount = rsMetadata.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            System.out.println(rsMetadata.getColumnName(i));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }


    @Test
    public void listMap() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select * from departments");
        //get the resultset object metadata
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        //how many column we have ?
        System.out.println("Column count:"+rsMetadata.getColumnCount());
        //column names
        // System.out.println(rsMetadata.getColumnName(8));
        //print all the column names dynamically
        int columnCount = rsMetadata.getColumnCount();
        for (int i = 1; i <=columnCount; i++) {
            //System.out.println(rsMetadata.getColumnName(i));
        }
        //creating list for keeping rows
        List<Map<String,Object>> queryData = new ArrayList<>();
        Map<String,Object> row1 = new HashMap<>();
        row1.put("first_name","Steven");
        row1.put("last_name","King");
        row1.put("salary",24000);
        row1.put("job_id","AD_PRESS");
        System.out.println(row1.toString());
        // get the first
        System.out.println(row1.get("first_name"));
        //--------------get row 2
        Map<String,Object> row2 = new HashMap<>();
        row2.put("first_name","Neena");
        row2.put("last_name","Kochhar");
        row2.put("salary",17000);
        row2.put("job_id","AD_VP");
        //adding rows to my list
        queryData.add(row1);
        queryData.add(row2);
        System.out.println("Neena Salary: "+queryData.get(1).get("salary"));
        System.out.println("Steven JobId: "+queryData.get(0).get("job_id"));
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Test
    public void queryresult() throws SQLException {
        Connection connection = DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet resultSet = statement.executeQuery("select first_name,last_name,salary,job_id \n" +
                "from employees\n" +
                "where rownum<=5");
        //get the resultset object metadata
        ResultSetMetaData rsMetadata = resultSet.getMetaData();
        //creating list for keeping rows
        List<Map<String,Object>> queryData = new ArrayList<>();
        Map<String,Object> row1 = new HashMap<>();
        resultSet.next();
        row1.put(rsMetadata.getColumnName(1),resultSet.getObject(1));
        row1.put(rsMetadata.getColumnName(2),resultSet.getObject(2));
        row1.put(rsMetadata.getColumnName(3),resultSet.getObject(3));
        row1.put(rsMetadata.getColumnName(4),resultSet.getObject(4));
        System.out.println(row1.toString());
        // get the first
        System.out.println(row1.get("first_name"));
        //--------------get row 2
        Map<String,Object> row2 = new HashMap<>();
        resultSet.next();
        row2.put(rsMetadata.getColumnName(1),resultSet.getObject(1));
        row2.put(rsMetadata.getColumnName(2),resultSet.getObject(2));
        row2.put(rsMetadata.getColumnName(3),resultSet.getObject(3));
        row2.put(rsMetadata.getColumnName(4),resultSet.getObject(4));
        //adding rows to my list
        queryData.add(row1);
        queryData.add(row2);
        System.out.println(queryData.get(0).toString());
        System.out.println(queryData.get(1).toString());
        resultSet.close();
        statement.close();
        connection.close();
    }



}


