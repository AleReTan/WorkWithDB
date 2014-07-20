package bd.DBService;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Александр on 01.07.14.
 */
public class Service {

    public Connection connection = null;                        //Соединение с БД
    public Statement statement = null;                          //Для хранения запросов
    public PreparedStatement preparedStatement = null;          //Предварительно компилирует запрос
    public ResultSet resultSet = null;                          //Результат запросов

    //подключение к БД
    public void openDB() {
        String user = "root";                                   //логин
        String password = "Cs38093809";                         //пароль
        String url = "jdbc:mysql://localhost:3306/Library";     //url адрес
        String driver = "com.mysql.jdbc.Driver";                //имя драйвера
        try {
            Class.forName(driver);//регистрация драйвера
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //Присоединяемся и работа с БД
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Database currently is unavailable.");
            System.exit(0);
        }

    }

    //отключение от БД
    public void closeDB() {
        //Закрываем все что открывали
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

