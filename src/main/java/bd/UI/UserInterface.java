package bd.UI;


import bd.DBService.DBTableModel;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import bd.DBService.Service;

import javax.swing.*;
import java.awt.*;


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
    private JTable table5;
    private JComboBox comboBox5;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;

    public UserInterface() throws Exception {
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
                "Delete"};
        setComboBoxItem(actions, comboBox1);
        setComboBoxItem(actions, comboBox2);
        setComboBoxItem(actions, comboBox3);
        setComboBoxItem(actions, comboBox4);
        comboBox5.addItem("Books");
        comboBox5.addItem("Visitors");

        //работа с таблицами
        Service service = new Service();
        service.openDB();
        service.resultSet = service.statement.executeQuery("select * from books");
        service.setTable(table1, service.resultSet);
        service.resultSet = service.statement.executeQuery("select * from visitors");
        service.setTable(table2, service.resultSet);
        service.closeDB();
        //листнеры
        confirmButton1.addActionListener((e) -> {
            comboBox1.getSelectedItem();
        });
        confirmButton2.addActionListener((e) -> {
            System.out.println("Кнопка нажата. Lambda!");
            comboBox2.getSelectedItem();
        });
        confirmButton3.addActionListener((e) -> {
            System.out.println("Кнопка нажата. Lambda!");
            comboBox3.getSelectedItem();
        });
        confirmButton4.addActionListener((e) -> {
            System.out.println("Кнопка нажата. Lambda!");
            comboBox4.getSelectedItem();
        });
        textField1.addActionListener((e) -> {
            //System.out.println("Кнопка нажата. Lambda!");
            comboBox5.getSelectedItem();
        });

        //добавление всей автоматически сгенеренной GUI
        Container contentPane = getContentPane();
        contentPane.add($$$getRootComponent$$$());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    //Заполняет ComboBox вариантами выбора
    private void setComboBoxItem(String[] string, JComboBox combo) {
        for (String aString : string) {
            combo.addItem(aString);
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
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
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBorder(BorderFactory.createTitledBorder("Database"));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Books", panel2);
        table1 = new JTable();
        table1.setAutoCreateRowSorter(false);
        table1.setCellSelectionEnabled(true);
        table1.setColumnSelectionAllowed(true);
        table1.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
        panel2.add(table1, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        comboBox1 = new JComboBox();
        panel2.add(comboBox1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton1 = new JButton();
        confirmButton1.setText("Confirm");
        panel2.add(confirmButton1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField1 = new JTextField();
        panel2.add(textField1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Visitors", panel3);
        table2 = new JTable();
        table2.setAutoCreateRowSorter(false);
        table2.setCellSelectionEnabled(true);
        panel3.add(table2, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        comboBox2 = new JComboBox();
        panel3.add(comboBox2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton2 = new JButton();
        confirmButton2.setText("Confirm");
        panel3.add(confirmButton2, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField2 = new JTextField();
        panel3.add(textField2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Publishing House", panel4);
        table3 = new JTable();
        table3.setCellSelectionEnabled(true);
        panel4.add(table3, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        comboBox3 = new JComboBox();
        panel4.add(comboBox3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton3 = new JButton();
        confirmButton3.setText("Confirm");
        panel4.add(confirmButton3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField3 = new JTextField();
        panel4.add(textField3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Exemplar", panel5);
        comboBox4 = new JComboBox();
        comboBox4.setEditable(false);
        panel5.add(comboBox4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        confirmButton4 = new JButton();
        confirmButton4.setText("Confirm");
        panel5.add(confirmButton4, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        table4 = new JTable();
        table4.setCellSelectionEnabled(true);
        panel5.add(table4, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        textField4 = new JTextField();
        panel5.add(textField4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Find", panel6);
        table5 = new JTable();
        table5.setCellSelectionEnabled(true);
        panel6.add(table5, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        comboBox5 = new JComboBox();
        panel6.add(comboBox5, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textField5 = new JTextField();
        textField5.setText("");
        panel6.add(textField5, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
