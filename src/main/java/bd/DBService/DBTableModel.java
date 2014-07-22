package bd.DBService;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

/**
 * Created by Александр on 08.07.2014.
 */

//Класс в котором создаем собственную модель, для ее использования в дальнейшем
public class DBTableModel
        extends AbstractTableModel {
    // здесь мы будем хранить названия столбцов
    private ArrayList<String> columnNames = new ArrayList<>();
    // список типов столбцов
    private ArrayList<Class> columnTypes = new ArrayList<>();
    // хранилище для полученных данных из базы данных
    private ArrayList<ArrayList<Object>> data = new ArrayList<>();

    // конструктор позволяет задать возможность редактирования
    public DBTableModel(boolean editable) {
        this.editable = editable;
    }

    private boolean editable;

    // количество строк
    public int getRowCount() {
        synchronized (data) {
            return data.size();
        }
    }
    // количество столбцов
    public int getColumnCount() {
        return columnNames.size();
    }

    // тип данных столбца
    public Class getColumnClass(int column) {
        return columnTypes.get(column);
    }

    // название столбца
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    // данные в ячейке
    public Object getValueAt(int row, int column) {
        synchronized (data) {
            return (data.get(row)).get(column);
        }
    }

    // возможность редактирования
    public boolean isCellEditable(int row, int column) {
        return editable;
    }

    // замена значения ячейки
    public void setValueAt(
            Object value, int row, int column) {
        synchronized (data) {
            (data.get(row)).set(column, value);
        }
    }

    // получение данных из объекта ResultSet
    public void setDataSource(
            ResultSet rs) throws Exception {
        // удаляем прежние данные
        data.clear();
        columnNames.clear();
        columnTypes.clear();
        // получаем вспомогательную информацию о столбцах
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            // название столбца
            columnNames.add(rsmd.getColumnName(i + 1));
            // тип столбца
            Class type =
                    Class.forName(rsmd.getColumnClassName(i + 1));
            columnTypes.add(type);
        }
        // сообщаем об изменениях в структуре данных
        fireTableStructureChanged();
        // получаем данные
        while (rs.next()) {
            // здесь будем хранить ячейки одной строки
            ArrayList row = new ArrayList();
            for (int i = 0; i < columnCount; i++) {
                if (columnTypes.get(i) == String.class)
                    row.add(rs.getString(i + 1));
                else
                    row.add(rs.getObject(i + 1));
            }
            synchronized (data) {
                data.add(row);
                // сообщаем о прибавлении строки
                fireTableRowsInserted(data.size() - 1, data.size() - 1);
            }
        }
    }
}
