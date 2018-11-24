
package dao;

import java.sql.Connection;
import oracle.jdbc.pool.OracleDataSource;

public class Database {
    private static OracleDataSource  ods;
    static  {
        try {
           ods = new OracleDataSource();
           ods.setDriverType("thin");
           ods.setUser("personaldiary");
           ods.setPassword("pd");
           ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
        }
        catch(Exception ex) {
            System.out.println("Error in static init block : " + ex.getMessage());
        }
    }
    
    public static Connection getConnection() throws Exception {
        return  ods.getConnection();
    }
    
}
