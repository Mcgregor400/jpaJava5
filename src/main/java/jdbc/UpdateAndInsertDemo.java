package jdbc;

import jdbc.ConnectionDemo;
import jdbc.CreateTableStatementDemo;
import jdbc.SelectDemo;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateAndInsertDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        //tworzymy tabelę demo
        CreateTableStatementDemo.createTableDemo();

        Connection connection = ConnectionDemo.getConnection();
        Statement selectAll = connection.createStatement(
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE
        );
        ResultSet records = selectAll.executeQuery("select * from demo");

        SelectDemo.printDemoTable(records);
        records.beforeFirst();
        //logika updat'u i insert'u nie jest istotna, ważne jak to robimy
        while(records.next()){
            int points = records.getInt("points");
            //założenie update'u: jeśli w polu "points" jest wartość null to zmieniamy ją na 0
            if (records.wasNull()){
                records.updateInt("points", 0);
                records.updateRow();
            }
            //założenie instert'u: jeśli w polu name jest null to wstawiamy nowy rekord
            if (records.getString("name") == null){
                //przesunięcie kursora do nowego wiersza
                records.moveToInsertRow();
                //ustawiamy wartości pól nowego rekordu
                records.updateInt("id", 7);
                records.updateString("name", "Nowy");
                records.updateInt("points", 10);
                //zapisanie i wstawienie nowego wiersza
                records.insertRow();
            }
        }
        records = selectAll.executeQuery("select * from demo");
        SelectDemo.printDemoTable(records);
    }
}