import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ScrollResultSetDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection = ConnectionDemo.getConnection();
        //Ustawiamy możliwość przewijania ResultSet w obu kierunkach
        Statement selectAll = connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY
        );
        ResultSet records = selectAll.executeQuery("select * from demo");
        SelectDemo.printDemoTable(records);
        //skrolujemy records na początek
        records.beforeFirst();
        SelectDemo.printDemoTable(records);
    }
}