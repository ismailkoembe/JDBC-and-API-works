package jdbctests;


import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //Connection string, parameter
        String dbUrl = "jdbc:oracle:thin:@3.90.148.246:1521:xe";
        String dbUserName = "hr";
        String dbPassword = "hr";
        databaseMetaData(dbUrl, dbUserName, dbPassword);
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from regions");
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                System.out.println(rs.getString(1) + "  " + rs.getString(2));
            }

//            while (rs.next()) {
//                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
//                    System.out.print(rs.getString(i) + " | ");
//                }
//                System.out.println();
//            }

            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public static void databaseMetaData(String dbUrl, String dbUserName, String dbPassword) {
            try {
                Connection connection = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
                DatabaseMetaData dbmd = connection.getMetaData();
                System.out.println("-----------------------------------------------------");
                System.out.println("DatabaseProductName    " + dbmd.getDatabaseProductName());
                System.out.println("DatabaseProductVersion " + dbmd.getDatabaseProductVersion());
                System.out.println("DriverName             " + dbmd.getDriverName());
                System.out.println("DriverVersion          " + dbmd.getDriverVersion());
                System.out.println("SQLKeywords            " + dbmd.getSQLKeywords());
                System.out.println("SystemFunctions        " + dbmd.getSystemFunctions());
                System.out.println("NumericFunctions       " + dbmd.getNumericFunctions());
                System.out.println("StringFunctions        " + dbmd.getStringFunctions());
                System.out.println("TimeDateFunctions      " + dbmd.getTimeDateFunctions());
                System.out.println("URL                    " + dbmd.getURL());
                System.out.println("UserName               " + dbmd.getUserName());

            } catch (SQLException ex) {
                System.out.println(ex);
                return;
            }
            System.out.println("databaseMetaData printed");
        }

}

