/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hunglt.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author pc
 */
public class DBHelper {
    public static Connection getConnection() throws /*ClassNotFoundException*/NamingException, SQLException{
//        //1. Load Driver
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        //2. Create Connection String protocolL://ip:port;databaseName=
//        String url = "jdbc:sqlserver://"//protocol
//                + "localhost:1433;"//container
//                + "databaseName=WEB";
//        //3. get Connection from Driver Manager
//        Connection con = DriverManager.getConnection(url, "sa", "12345");
//        //4. return con
//        return con;
        //====================== đã set up Data Source
        //1. get current context
        Context context = new InitialContext();
        Context tomcatContext = (Context)context.lookup("java:comp/env");
        //2. lookup data Source
        DataSource ds = (DataSource) tomcatContext.lookup("DS007");
        //3. open connection
        Connection con = ds.getConnection();
        
        //4. Return connection
        return con;
    }
}
