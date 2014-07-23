package bd.UI;

import bd.DBService.Service;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Types;

/**
 * Created by Александр on 21.07.2014.
 */

//Класс диалогового окна, в котором задаются параметры строки
public class DialogFrame extends JDialog {
    public JTextField textField1;
    public JTextField textField2;
    public JTextField textField5;
    public JTextField textField6;
    public JTextField textField7;
    public JTextField textField3;
    public JTextField textField4;
    public JTextField textField8;
    public JButton сохранитьButton;
    public JButton отменитьButton;
    private JPanel panel1;
    public JComboBox comboBox1;
    public JComboBox comboBox2;
    public boolean addFlag = false;             //Флаг, позоляющий понять, нажата была кнопка "Добавить" или "Редактировать"
    Service service = new Service();

    public DialogFrame(JFrame owner) {
        super(owner, true);
        $$$setupUI$$$();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 3, screenHeight / 3);
        setLocation(screenWidth / 4, screenHeight / 4);
        Container contentPane = getContentPane();
        contentPane.add($$$getRootComponent$$$());
        textField1.setVisible(false);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        отменитьButton.addActionListener((e) -> {
            setVisible(false);
            textField1.setText("");
            textField2.setText("");
            textField3.setText("");
            textField4.setText("");
            textField5.setText("");
            textField6.setText("");
            comboBox1.removeAllItems();
            comboBox2.removeAllItems();
//            textField7.setText("");
//            textField8.setText("");
            service.closeDB();
        });


        сохранитьButton.addActionListener((e) -> {                                                                                                  //Листнер на кнопку "Сохранить"
                    try {
                        if (addFlag) {                                                                                                              //Если true выполняется код добавление новой строки в БД
                            service.preparedStatement = service.connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?, ?, ?, ?);");  //подготовленный запрос
                            if (textField1.getText().isEmpty())
                                textField1.setText("Это поле не может быть пустым");                                //Некоторые строки не могут быть пустыми, для них идет проверка
                            else {                                                                                                                  //Если нажать кнопку сохранить с незаполненными данными, в строках где это запрещено,
                                service.preparedStatement.setInt(1, Integer.parseInt(textField1.getText()));                                        //вылетит исключение, которое обработано выводом сообщения в консоль
                            }
                            if (textField2.getText().isEmpty()) textField2.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setString(2, textField2.getText());
                            }
                            if (textField3.getText().isEmpty()) textField3.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setString(3, textField3.getText());
                            }
                            service.preparedStatement.setString(4, textField4.getText());
                            if (textField5.getText().isEmpty()) service.preparedStatement.setNull(5, Types.DATE);
                            else {
                                service.preparedStatement.setString(5, textField5.getText());
                            }
                            if (textField6.getText().isEmpty()) service.preparedStatement.setNull(6, Types.INTEGER);
                            else {
                                service.preparedStatement.setString(6, textField6.getText());
                            }
                            /*if (textField7.getText().isEmpty()) textField7.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setString(7, textField7.getText());
                            }
                            if (textField8.getText().isEmpty()) textField8.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setString(8, textField8.getText());
                            }*/
                            service.preparedStatement.setString(7, comboBox1.getSelectedItem().toString());
                            service.preparedStatement.setString(8, Integer.toString(comboBox2.getSelectedIndex() + 1));
                            service.preparedStatement.executeUpdate();

                        } else {                                                                                                                    //Если addFlag — false, выполнится код для редактирования строки в БД
                            service.preparedStatement = service.connection.prepareStatement("delete from books where idBooks = ?");                 //подготовленный запрос
                            service.preparedStatement.setInt(1, Integer.parseInt(textField1.getText()));
                            service.preparedStatement.executeUpdate();
                            service.preparedStatement = service.connection.prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
                            if (textField1.getText().isEmpty()) textField1.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setInt(1, Integer.parseInt(textField1.getText()));
                            }
                            if (textField2.getText().isEmpty()) textField2.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setString(2, textField2.getText());
                            }
                            if (textField3.getText().isEmpty()) textField3.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setString(3, textField3.getText());
                            }
                            service.preparedStatement.setString(4, textField4.getText());
                            if (textField5.getText().isEmpty()) service.preparedStatement.setNull(5, Types.DATE);
                            else {
                                service.preparedStatement.setString(5, textField5.getText());
                            }
                            if (textField6.getText().isEmpty()) service.preparedStatement.setNull(6, Types.INTEGER);
                            else {
                                service.preparedStatement.setString(6, textField6.getText());
                            }
                            service.preparedStatement.setString(7, comboBox1.getSelectedItem().toString());
                            service.preparedStatement.setString(8, Integer.toString(comboBox2.getSelectedIndex() + 1));
                            /*
                            if (textField7.getText().isEmpty()) textField7.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setString(7, textField7.getText());
                            }
                            if (textField8.getText().isEmpty()) textField8.setText("Это поле не может быть пустым");
                            else {
                                service.preparedStatement.setString(8, textField8.getText());
                            }*/
                            service.preparedStatement.executeUpdate();
                        }
                        setVisible(false);
                        textField1.setText("");
                        textField2.setText("");
                        textField3.setText("");
                        textField4.setText("");
                        textField5.setText("");
                        textField6.setText("");
                        comboBox1.removeAllItems();
                        comboBox2.removeAllItems();
                        //textField7.setText("");
                        //textField8.setText("");
                        addFlag = false;
                        service.closeDB();

                    } catch (Exception e1) {
                        System.out.println("Ошибка! Проверьте входные данные.");
                    }
                }

        );

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
        panel1.setLayout(new GridLayoutManager(15, 2, new Insets(0, 0, 0, 0), -1, -1));
        textField1 = new JTextField();
        panel1.add(textField1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField2 = new JTextField();
        panel1.add(textField2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField5 = new JTextField();
        panel1.add(textField5, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField6 = new JTextField();
        panel1.add(textField6, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField3 = new JTextField();
        panel1.add(textField3, new GridConstraints(9, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textField4 = new JTextField();
        panel1.add(textField4, new GridConstraints(12, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setBackground(UIManager.getColor("Button.darcula.color1"));
        label1.setEnabled(true);
        label1.setForeground(new Color(-16776961));
        label1.setText("Название книги:");
        panel1.add(label1, new GridConstraints(4, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setEnabled(true);
        label2.setForeground(new Color(-16776961));
        label2.setText("Автор книги:");
        panel1.add(label2, new GridConstraints(7, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Жанр книги:");
        panel1.add(label3, new GridConstraints(10, 0, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Количество страниц:");
        panel1.add(label4, new GridConstraints(4, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setEnabled(true);
        label5.setForeground(new Color(-16776961));
        label5.setText("Номер экземпляра:");
        panel1.add(label5, new GridConstraints(7, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setEnabled(true);
        label6.setForeground(new Color(-16776961));
        label6.setText("Издательство:");
        panel1.add(label6, new GridConstraints(10, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Год выпуска:");
        panel1.add(label7, new GridConstraints(0, 1, 3, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        сохранитьButton = new JButton();
        сохранитьButton.setText("Сохранить");
        panel1.add(сохранитьButton, new GridConstraints(14, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        отменитьButton = new JButton();
        отменитьButton.setText("Отменить");
        panel1.add(отменитьButton, new GridConstraints(14, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox1 = new JComboBox();
        panel1.add(comboBox1, new GridConstraints(9, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBox2 = new JComboBox();
        panel1.add(comboBox2, new GridConstraints(12, 1, 2, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
