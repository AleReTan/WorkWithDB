package bd.UI;


import bd.DBService.DBTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import bd.DBService.Service;

import javax.swing.*;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Created by Александр on 07.07.14.
 */

//Класс основного окна
public class UserInterface extends JFrame {
    private JPanel panel1;
    private JTabbedPane tabbedPane1;
    public JTable table1;
    public JTable table2;
    public JTable table3;
    public JTable table4;
    private JTextField textField1;
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

        //подключение к БД
        service.openDB();                                                                                                                           //Подключаемся к БД
        refreshDB();                                                                                                                                //Считываем первый раз данные
        //листнеры на кнопки и т.д.
        refreshButton.addActionListener((e) -> {                                                                                                    //Листнер на кнопку "Обновить"
            try {
                refreshDB();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        });

        addButton.addActionListener((e) -> {                                                                                                        //Листнер на кнопку "Добавить"
            dialog.service.openDB();
            dialog.addFlag = true;
            Integer newRowCount = table1.getRowCount() + 1;
            dialog.textField1.setText(newRowCount.toString());

            for (int i = 0; i < table4.getRowCount(); i++) {

                dialog.comboBox1.addItem(table4.getValueAt(i, 0).toString());
            }
            for (int i = 0; i < table3.getRowCount(); i++) {

                dialog.comboBox2.addItem(table3.getValueAt(i, 1).toString());
            }

            dialog.setVisible(true);

        });

        editButton.addActionListener((e) -> {
            if (table1.getSelectedRow() != -1) {
                dialog.service.openDB();
                Integer id = table1.getSelectedRow() + 1;
                dialog.textField1.setText(id.toString());
                dialog.textField2.setText(table1.getValueAt(id - 1, 1).toString());
                dialog.textField3.setText(table1.getValueAt(id - 1, 2).toString());
                dialog.textField4.setText(table1.getValueAt(id - 1, 3).toString());
                if (dialog.textField5.getText().isEmpty()) dialog.textField5.setText("");
                else dialog.textField5.setText(table1.getValueAt(id - 1, 4).toString());
                if (dialog.textField6.getText().isEmpty()) dialog.textField6.setText("");
                else dialog.textField6.setText(table1.getValueAt(id - 1, 5).toString());

                for (int i = 0; i < table4.getRowCount(); i++) {
                    dialog.comboBox1.addItem(table4.getValueAt(i, 0).toString());
                }
                for (int i = 0; i < table3.getRowCount(); i++) {
                    dialog.comboBox2.addItem(table3.getValueAt(i, 1).toString());
                }
                //dialog.textField7.setText(table1.getValueAt(id - 1, 6).toString());
                //dialog.textField8.setText(table1.getValueAt(id - 1, 7).toString());
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
                    refreshDB();
                } else textField1.setText("Вначале нужно выбрать строку");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //добавление всей автоматически сгенеренной GUI
        Container contentPane = getContentPane();
        contentPane.add($$$getRootComponent$$$());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {                                     //Листнер на крестик, чтобы при ее нажатие на него, вначале происходило отключение от БД. и затем завершение работы
            @Override
            public void windowClosing(WindowEvent e) {
                service.closeDB();
                System.out.println("Success");
                System.exit(0);
            }
        });
    }

    // Заполняет ComboBox вариантами выбора

    private void setComboBoxItem(String[] string, JComboBox<String> combo) {
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
        panel3.setLayout(new GridLayoutManager(2, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Books", panel3);
        textField1 = new JTextField();
        panel3.add(textField1, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        jScrollPane1 = new JScrollPane();
        panel3.add(jScrollPane1, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        table1.setAutoCreateRowSorter(false);
        table1.setCellSelectionEnabled(false);
        table1.setColumnSelectionAllowed(false);
        table1.setRowSelectionAllowed(false);
        table1.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        jScrollPane1.setViewportView(table1);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Visitors", panel4);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel4.add(scrollPane1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table2 = new JTable();
        table2.setAutoCreateRowSorter(false);
        table2.setCellSelectionEnabled(true);
        scrollPane1.setViewportView(table2);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Publishing House", panel5);
        final JScrollPane scrollPane2 = new JScrollPane();
        panel5.add(scrollPane2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table3 = new JTable();
        table3.setCellSelectionEnabled(true);
        scrollPane2.setViewportView(table3);
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Exemplar", panel6);
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
