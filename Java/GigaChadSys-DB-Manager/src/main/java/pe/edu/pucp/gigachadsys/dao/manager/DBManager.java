package pe.edu.pucp.gigachadsys.dao.manager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBManager {

    private static DBManager instance;
    private Properties properties;

    private String url;
    private String user;
    private String password;

    private final String DB_CREDENTIALS_FILE = "db.properties";

    private HikariDataSource ds;

    private DBManager() {
        properties = new Properties();

        try {
            InputStream inputStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(DB_CREDENTIALS_FILE);

            properties.load(inputStream);

        } catch (IOException ex) {
            System.out.println("Error loading properties file: " + ex.getMessage());
        }

        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String database = properties.getProperty("database");

        this.url = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false"
                + "&allowPublicKeyRetrieval=true"
                + "&serverTimezone=UTC";

        this.user = properties.getProperty("user");
        this.password = properties.getProperty("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el driver de MySQL - " + e.getMessage());
        }

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(this.url);
        config.setUsername(this.user);
        config.setPassword(this.password);
        config.setMaximumPoolSize(15);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        this.ds = new HikariDataSource(config);
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            System.out.println("Error connecting to DB: " + e.getMessage());
            return null;
        }
    }
}