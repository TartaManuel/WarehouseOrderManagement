package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa in care realizam conexiunile cu baza de date
 * Avem si metode pentru inchiderea conexiunilor pentru cand terminam operatiile
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/db";
    private static final String USER = "root";
    private static final String PASS = "YourPassword";
    //astea sunt constante globale care nu se mai pot modifica

    private static ConnectionFactory singleInstance = new ConnectionFactory();//o instanta la el aparent

    private ConnectionFactory() { //constructor privat; nu o sa mai fie facut altundeva
        try {
            Class.forName(DRIVER); //returneaza the class object cu numele ala
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() { //metoda de creare a conexiunii cu baza de date
        //e de tip Connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    //metoda ce returneaza conexiunea creata cu createConnection
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    //metoda ce inchide conexiunea cand nu ne mai trebuie
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the connection");
            }
        }
    }

    //metoda ce da close la statement, aparent si asta se poate
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the statement");
            }
        }
    }

    //inca un close pentru ResultSet dupa ce nu il mai folosim
    //avem exceptii la toate
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "An error occured while trying to close the ResultSet");
            }
        }
    }
}
