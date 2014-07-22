package bd.UI;


import bd.DBService.DBTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import bd.DBService.Service;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;


/**
 * Created by Александр on 07.07.14.
 */
public class UserInterface extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    public JTable table1;
    private JComboBox comboBox1;
    private JButton confirmButton1;
    public JTable table2;
    private JComboBox comboBox2;
    private JButton confirmButton2;
    public JTable table3;
    private JComboBox comboBox3;
    private JButton confirmButton3;
    public JTable table4;
    private JComboBox comboBox4;
    private JButton confirmButton4;
    private JComboBox comboBox5;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JButton refreshButton1;
    private JButton refreshButton2;
    private JButton refreshButton3;
    private JButton refreshButton4;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JPanel panel2;
    private JScrollPane jScrollPane1;
    public Service service = new Service();
    public DialogFrame dialog = new DialogFrame(UserInterface.this);

    //конструктор класса
    public UserInterface() throws Exception {
        $$$setupUI$$$();    //установка сгенеренного интерфейса
        //Получение разрешения экрана и установка его высоты и ширины
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocation(screenWidth / 4, screenHeight / 4);
        setTitle("WorkWithDB");

        //установка ComboBox'ов
        String actions[] = {"Add",
                "Delete",
                "Edit"};
        setComboBoxItem(actions, comboBox1);
        setComboBoxItem(actions, comboBox2);
        setComboBoxItem(actions, comboBox3);
        setComboBoxItem(actions, comboBox4);

        //Подсказки
        textField1.setToolTipText("Пример входных данных: Название книги;Автор;Жанр;Год выпуска, формат: YYYY;Количество страниц;id экземпляра;id издательства");
        textField2.setToolTipText("Пример входных данных: Имя;Фамилия;День рождения, формат: YYYY-MM-DD;Контактный телефон");
        textField3.setToolTipText("Пример входных данных: Название издательства");
        textField4.setToolTipText("Пример входных данных: Наличие(0 или 1);Дата взятия, формат: YYYY-MM-DD;Дата возврата, формат: YYYY-MM-DD;id посетителя");
        textField6.setToolTipText("Укажите id книги");
        textField7.setToolTipText("Укажите id посетителя");
        textField8.setToolTipText("Укажите id издательства");
        textField9.setToolTipText("Укажите id экземпляра");


        //подключение к БД
        service.openDB();                                                                                                                               //подключаемся к БД
        refreshDB();                                                                                                                                    //считываем первый раз данные
        //листнеры на кнопки и т.д.
        refreshButton.addActionListener((e) -> {
            try {
                refreshDB();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });

        addButton.addActionListener((e) -> {
            dialog.addFlag = true;
            Integer newRowCount = table1.getRowCount() + 1;
            dialog.textField1.setText(newRowCount.toString());
            dialog.setVisible(true);
            dialog.save(service);
        });

        editButton.addActionListener((e) -> {
            if (table1.getSelectedRow() != -1) {
                Integer id = table1.getSelectedRow() + 1;
                dialog.textField1.setText(id.toString());
                dialog.textField2.setText(table1.getValueAt(id - 1, 1).toString());
                dialog.textField3.setText(table1.getValueAt(id - 1, 2).toString());
                dialog.textField4.setText(table1.getValueAt(id - 1, 3).toString());
                dialog.textField5.setText(table1.getValueAt(id - 1, 4).toString());
                dialog.textField6.setText(table1.getValueAt(id - 1, 5).toString());
                dialog.textField7.setText(table1.getValueAt(id - 1, 6).toString());
                dialog.textField8.setText(table1.getValueAt(id - 1, 7).toString());
                dialog.setVisible(true);

            } else textField1.setText("Вначале нужно выбрать строку");
        });

        deleteButton.addActionListener((e) -> {
            try {
                if (table1.getSelectedRow() != -1) {
                    int id = table1.getSelectedRow() + 1;
                    service.preparedStatement = service.connection.prepareStatement("delete from books where idBooks = ?");                         //подготовленный запрос
                    service.preparedStatement.setInt(1, id);                                                                                        //здесь и ниже передаем в подготовленный запрос параметры
                    service.preparedStatement.executeUpdate();
                } else textField1.setText("Вначале нужно выбрать строку");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });


/*
        confirmButton1.addActionListener((e) -> {
            try {
                String string = comboBox1.getSelectedItem().toString();                                                                                 //получаем то, что выбрано в comboBox'е
                switch (string) {                                                                                                                       //в зависимости от выбранного, выполняем действия
                    case "Add": {
                        //получаем id записи
                        String newText = textField1.getText();                                                                                          //получаем введенный текст
                        if (newText.isEmpty() || textField6.getText().isEmpty()) {                                                                    //проверка на пустые строки
                            textField1.setText("An empty string is not allowed.");
                            break;
                        } else {
                            String[] row = newText.split(";");                                                                                          //массив строк, с помощью разделителя
                            int newId = Integer.parseInt(textField6.getText());                                                                         //получаем id записи
                            if (row.length == 7) {
                                service.preparedStatement = service.connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?, ?, ?, ?);");  //подготовленный запрос
                                service.preparedStatement.setInt(1, newId);                                                                             //здесь и ниже передаем в подготовленный запрос параметры
                                service.preparedStatement.setString(2, row[0]);
                                service.preparedStatement.setString(3, row[1]);
                                service.preparedStatement.setString(4, row[2]);
                                service.preparedStatement.setString(5, row[3]);
                                service.preparedStatement.setString(6, row[4]);
                                service.preparedStatement.setString(7, row[5]);
                                service.preparedStatement.setString(8, row[6]);
                                service.preparedStatement.executeUpdate();                                                                              //выполняет заданный запрос
                                break;
                            } else {
                                textField6.setText("Wrong number input data.");
                                break;
                            }
                        }
                    }
                    case "Delete": {
                        if (textField6.getText().isEmpty()) {
                            textField1.setText("An empty string is not allowed.");
                            break;
                        } else {
                            int id = Integer.parseInt(textField6.getText());                                                                                //получаем id записи
                            service.preparedStatement = service.connection.prepareStatement("delete from books where idBooks = ?");                         //подготовленный запрос
                            service.preparedStatement.setInt(1, id);                                                                                        //здесь и ниже передаем в подготовленный запрос параметры
                            service.preparedStatement.executeUpdate();                                                                                      //выполняет заданный запрос
                            break;
                        }
                    }
                    case "Edit": {
                        String newText = textField1.getText();                                                                                          //получаем введенный текст
                        if (newText.isEmpty() || textField6.getText().isEmpty()) {                                                                    //проверка на пустые строки
                            textField1.setText("An empty string is not allowed.");
                            break;
                        } else {
                            String[] row = newText.split(";");                                                                                          //массив строк, с помощью разделителя
                            int newId = Integer.parseInt(textField6.getText());                                                                         //получаем id записи
                            //service.preparedStatement = service.connection.prepareStatement("UPDATE books SET BookTitle=?,bookAuthor=?,Genre=?,PublishingYear=?,NumberOfPage=?,idExemplar=?,idPublishingHouse=? where idExemplar=?");//("UPDATE INTO books VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
                            if (row.length == 7) {
                                service.preparedStatement = service.connection.prepareStatement("delete from books where idBooks = ?");                 //подготовленный запрос
                                service.preparedStatement.setInt(1, newId);                                                                             //здесь передаем в подготовленный запрос параметр
                                service.preparedStatement.executeUpdate();
                                service.preparedStatement = service.connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?, ?, ?, ?);");  //подготовленный запрос
                                service.preparedStatement.setInt(1, newId);                                                                             //здесь передаем в подготовленный запрос параметр
                                service.preparedStatement.setString(2, row[0]);
                                service.preparedStatement.setString(3, row[1]);
                                service.preparedStatement.setString(4, row[2]);
                                service.preparedStatement.setString(5, row[3]);
                                service.preparedStatement.setString(6, row[4]);
                                service.preparedStatement.setString(7, row[5]);
                                service.preparedStatement.setString(8, row[6]);
                                service.preparedStatement.executeUpdate();                                                                              //выполняет заданный запрос
                                break;
                            } else {
                                textField6.setText("Wrong number input data.");
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        //остальные листнеры имеют схожую структуру, отличие в запросах
        /*
        confirmButton2.addActionListener((e) -> {
            try {
                String string = comboBox2.getSelectedItem().toString();
                switch (string) {
                    case "Add": {
                        String newText = textField2.getText();
                        if (newText.isEmpty() || textField7.getText().isEmpty()) {
                            textField2.setText("An empty string is not allowed.");
                            break;
                        } else {
                            String[] row = newText.split(";");
                            int newId = Integer.parseInt(textField7.getText());
                            if (row.length == 4) {
                                service.preparedStatement = service.connection.prepareStatement("INSERT INTO visitors VALUES (?, ?, ?, ?, ?);");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.setString(2, row[0]);
                                service.preparedStatement.setString(3, row[1]);
                                service.preparedStatement.setString(4, row[2]);
                                service.preparedStatement.setString(5, row[3]);
                                service.preparedStatement.executeUpdate();
                                break;
                            } else {
                                textField7.setText("Wrong number input data.");
                                break;
                            }
                        }
                    }
                    case "Delete": {
                        if (textField7.getText().isEmpty()) {
                            textField2.setText("An empty string is not allowed.");
                            break;
                        } else {
                            int id = Integer.parseInt(textField7.getText());
                            service.preparedStatement = service.connection.prepareStatement("delete from visitors where idVisitors = ?");
                            service.preparedStatement.setInt(1, id);
                            service.preparedStatement.executeUpdate();
                            break;
                        }
                    }

                    case "Edit": {
                        String newText = textField2.getText();
                        if (newText.isEmpty() || textField7.getText().isEmpty()) {
                            textField2.setText("An empty string is not allowed.");
                            break;
                        } else {
                            String[] row = newText.split(";");
                            int newId = Integer.parseInt(textField7.getText());
                            if (row.length == 4) {
                                service.preparedStatement = service.connection.prepareStatement("delete from visitors where idVisitors = ?");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.executeUpdate();
                                service.preparedStatement = service.connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?);");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.setString(2, row[0]);
                                service.preparedStatement.setString(3, row[1]);
                                service.preparedStatement.setString(4, row[2]);
                                service.preparedStatement.setString(5, row[3]);
                                service.preparedStatement.executeUpdate();
                                break;
                            } else {
                                textField7.setText("Wrong number input data.");
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        confirmButton3.addActionListener((e) -> {
            try {
                String string = comboBox3.getSelectedItem().toString();
                switch (string) {
                    case "Add": {
                        String newText = textField3.getText();
                        if (newText.isEmpty() || textField8.getText().isEmpty()) {
                            textField3.setText("An empty string is not allowed.");
                            break;
                        } else {
                            String[] row = newText.split(";");
                            int newId = Integer.parseInt(textField8.getText());
                            if (row.length == 1) {
                                service.preparedStatement = service.connection.prepareStatement("INSERT INTO publishinghouse VALUES (?, ?);");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.setString(2, row[0]);
                                service.preparedStatement.executeUpdate();
                                break;
                            } else {
                                textField8.setText("Wrong number input data.");
                                break;
                            }
                        }
                    }
                    case "Delete": {
                        if (textField8.getText().isEmpty()) {
                            textField3.setText("An empty string is not allowed.");
                            break;
                        } else {
                            int id = Integer.parseInt(textField8.getText());
                            service.preparedStatement = service.connection.prepareStatement("delete from publishinghouse where idPublishingHouse = ?");
                            service.preparedStatement.setInt(1, id);
                            service.preparedStatement.executeUpdate();
                            break;
                        }
                    }

                    case "Edit": {
                        String newText = textField3.getText();
                        if (newText.isEmpty() || textField7.getText().isEmpty()) {
                            textField3.setText("An empty string is not allowed.");
                            break;
                        } else {
                            String[] row = newText.split(";");
                            int newId = Integer.parseInt(textField8.getText());
                            if (row.length == 1) {
                                service.preparedStatement = service.connection.prepareStatement("delete from publishinghouse where idPublishinghouse = ?");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.executeUpdate();
                                service.preparedStatement = service.connection.prepareStatement("INSERT INTO publishinghouse VALUES (?, ?);");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.setString(2, row[0]);
                                service.preparedStatement.executeUpdate();
                                break;
                            } else {
                                textField8.setText("Wrong number input data.");
                                break;
                            }
                        }
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        confirmButton4.addActionListener((e) -> {
            try {
                String string = comboBox4.getSelectedItem().toString();
                switch (string) {
                    case "Add": {
                        String newText = textField4.getText();
                        if (newText.isEmpty() || textField9.getText().isEmpty()) {
                            textField4.setText("An empty string is not allowed.");
                            break;
                        } else {
                            String[] row = newText.split(";");
                            int newId = Integer.parseInt(textField9.getText());
                            if (row.length == 4) {
                                service.preparedStatement = service.connection.prepareStatement("INSERT INTO exemplar VALUES (?, ?, ?, ?, ?);");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.setString(2, row[0]);
                                if (row[1].isEmpty()) service.preparedStatement.setNull(3, Types.DATE);
                                else service.preparedStatement.setString(3, row[1]);
                                if (row[2].isEmpty()) service.preparedStatement.setNull(4, Types.DATE);
                                else service.preparedStatement.setString(4, row[2]);
                                service.preparedStatement.setString(5, row[3]);
                                service.preparedStatement.executeUpdate();
                                break;
                            } else {
                                textField9.setText("Wrong number input data.");
                                break;
                            }
                        }
                    }
                    case "Delete": {
                        if (textField9.getText().isEmpty()) {
                            textField4.setText("An empty string is not allowed.");
                            break;
                        } else {
                            int id = Integer.parseInt(textField9.getText());
                            service.preparedStatement = service.connection.prepareStatement("delete from exemplar where idExemplar = ?");
                            service.preparedStatement.setInt(1, id);
                            service.preparedStatement.executeUpdate();
                            break;
                        }
                    }

                    case "Edit": {
                        String newText = textField4.getText();
                        if (newText.isEmpty() || textField9.getText().isEmpty()) {
                            textField4.setText("An empty string is not allowed.");
                            break;
                        } else {
                            String[] row = newText.split(";");
                            int newId = Integer.parseInt(textField9.getText());
                            if (row.length == 4) {
                                service.preparedStatement = service.connection.prepareStatement("delete from exemplar where idExemplar = ?");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.executeUpdate();
                                service.preparedStatement = service.connection.prepareStatement("INSERT INTO exemplar VALUES (?, ?, ?, ?, ?);");
                                service.preparedStatement.setInt(1, newId);
                                service.preparedStatement.setString(2, row[0]);
                                if (row[1].isEmpty()) service.preparedStatement.setNull(3, Types.DATE);
                                else service.preparedStatement.setString(3, row[1]);
                                if (row[2].isEmpty()) service.preparedStatement.setNull(4, Types.DATE);
                                else service.preparedStatement.setString(4, row[2]);
                                service.preparedStatement.setString(5, row[3]);
                                service.preparedStatement.executeUpdate();
                                break;
                            } else {
                                textField9.setText("Wrong number input data.");
                                break;
                            }
                        }
                    }

                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        refreshButton1.addActionListener((e) -> {                           //листнер к кнопке обновить
            try {
                refreshDB();                                                //метод обновляющий бд
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });                                                                 //к другим кнопкам листнеры аналогичные
        refreshButton2.addActionListener((e) -> {
            try {
                refreshDB();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });
        refreshButton3.addActionListener((e) -> {
            try {
                refreshDB();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });
        refreshButton4.addActionListener((e) -> {
            try {
                refreshDB();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });
*/
        //добавление всей автоматически сгенеренной GUI
        Container contentPane = getContentPane();
        contentPane.add($$$getRootComponent$$$());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {                                     //листнер на крестик, чтобы при ее нажатие на него, вначале происходило отключение от БД. и затем завершение работы
            @Override
            public void windowClosing(WindowEvent e) {
                service.closeDB();
                System.out.println("Success");
                System.exit(0);
            }
        });
    }

    //Заполняет ComboBox вариантами выбора
    private void setComboBoxItem(String[] string, JComboBox combo) {
        for (String aString : string) {
            combo.addItem(aString);
        }
    }

    //Обновляет таблицы, путем получения данных из БД
    public void refreshDB() throws Exception {
        service.resultSet = service.statement.executeQuery("select * from books");
        DBTableModel dbTableModel1 = new DBTableModel(false);//модель для таблицы 1
        table1.setModel(dbTableModel1);
        dbTableModel1.setDataSource(service.resultSet);

        service.resultSet = service.statement.executeQuery("select * from visitors");
        DBTableModel dbTableModel2 = new DBTableModel(false);//модель для таблицы 2
        table2.setModel(dbTableModel2);
        dbTableModel2.setDataSource(service.resultSet);

        service.resultSet = service.statement.executeQuery("select * from publishinghouse");
        DBTableModel dbTableModel3 = new DBTableModel(false);//модель для таблицы 3
        table3.setModel(dbTableModel3);
        dbTableModel3.setDataSource(service.resultSet);

        service.resultSet = service.statement.executeQuery("select * from exemplar");
        DBTableModel dbTableModel4 = new DBTableModel(false);//модель для таблицы 4
        table4.setModel(dbTableModel4);
        dbTableModel4.setDataSource(service.resultSet);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBorder(BorderFactory.createTitledBorder("Database"));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Books", panel3);
        comboBox1 = new JComboBox();
        panel3.add(comboBox1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton1 = new JButton();
        confirmButton1.setText("Confirm");
        panel3.add(confirmButton1, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel3.add(textField1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField6 = new JTextField();
        panel3.add(textField6, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        refreshButton1 = new JButton();
        refreshButton1.setText("Refresh");
        panel3.add(refreshButton1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        jScrollPane1 = new JScrollPane();
        panel3.add(jScrollPane1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        table1.setAutoCreateRowSorter(false);
        table1.setCellSelectionEnabled(false);
        table1.setColumnSelectionAllowed(false);
        table1.setRowSelectionAllowed(true);
        table1.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        jScrollPane1.setViewportView(table1);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Visitors", panel4);
        comboBox2 = new JComboBox();
        panel4.add(comboBox2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton2 = new JButton();
        confirmButton2.setText("Confirm");
        panel4.add(confirmButton2, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        panel4.add(textField2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField7 = new JTextField();
        panel4.add(textField7, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        refreshButton2 = new JButton();
        refreshButton2.setText("Refresh");
        panel4.add(refreshButton2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel4.add(scrollPane1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table2 = new JTable();
        table2.setAutoCreateRowSorter(false);
        table2.setCellSelectionEnabled(true);
        scrollPane1.setViewportView(table2);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Publishing House", panel5);
        comboBox3 = new JComboBox();
        panel5.add(comboBox3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton3 = new JButton();
        confirmButton3.setText("Confirm");
        panel5.add(confirmButton3, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField3 = new JTextField();
        panel5.add(textField3, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField8 = new JTextField();
        panel5.add(textField8, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        refreshButton3 = new JButton();
        refreshButton3.setText("Refresh");
        panel5.add(refreshButton3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane2 = new JScrollPane();
        panel5.add(scrollPane2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table3 = new JTable();
        table3.setCellSelectionEnabled(true);
        scrollPane2.setViewportView(table3);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Exemplar", panel6);
        comboBox4 = new JComboBox();
        comboBox4.setEditable(false);
        panel6.add(comboBox4, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton4 = new JButton();
        confirmButton4.setText("Confirm");
        panel6.add(confirmButton4, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField4 = new JTextField();
        panel6.add(textField4, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField9 = new JTextField();
        panel6.add(textField9, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        refreshButton4 = new JButton();
        refreshButton4.setText("Refresh");
        panel6.add(refreshButton4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane3 = new JScrollPane();
        panel6.add(scrollPane3, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table4 = new JTable();
        table4.setCellSelectionEnabled(true);
        scrollPane3.setViewportView(table4);
        panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        addButton.setToolTipText("Добавление в БД");
        panel2.add(addButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Edit");
        editButton.setToolTipText("Редактирование строки");
        panel2.add(editButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        deleteButton.setToolTipText("Удаление");
        panel2.add(deleteButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        refreshButton = new JButton();
        refreshButton.setText("Refresh");
        refreshButton.setToolTipText("Обновление БД");
        panel2.add(refreshButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
