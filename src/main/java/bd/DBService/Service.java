package bd.DBService;

import javax.swing.*;
import java.sql.*;

/**
 * Created by Александр on 01.07.14.
 */
public class Service {
    //здесь различные методы типа добавить удалить изменить
    private Connection connection = null;//Соединение с БД
    public Statement statement = null;//Для хранения запросов
    public ResultSet resultSet = null;//Результат запросов
    DBTableModel dbTableModel = new DBTableModel(true);//модель для таблицы
    //подключение и работа с БД
    public void openDB() {
        String user = "root";//логин
        String password = "Cs38093809";//пароль
        String url = "jdbc:mysql://localhost:3306/Library";//url адрес
        String driver = "com.mysql.jdbc.Driver";//имя драйвера
        try {
            Class.forName(driver);//регистрация драйвера
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //Присоединяемся и работа с БД
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from books");
            /*while (resultSet.next()) {
                System.out.println(resultSet.getString(3));
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void setTable(JTable table, ResultSet rs) throws Exception {
        table.setModel(dbTableModel);
        dbTableModel.setDataSource(rs);
    }

    public ResultSet getResultSet(){
        return resultSet;
    }
    public Statement getStatement(){
        return statement;
    }
}

