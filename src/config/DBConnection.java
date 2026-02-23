package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:h2:/storage/emulated/0/AideProjects/NexusBanking/database/bankdb;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM '/storage/emulated/0/AideProjects/NexusBanking/src/database/schema.sql'";
    private static final String USER = "sa";
    private static final String PASSWORD = "";


    
	

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
