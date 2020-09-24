import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableEmployees {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        CreateTableempl();
       return;

    }
    static Connection getConnection2() throws InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/intitute?serverTimezone=Europe/Warsaw",
                "root","12345");
    }

    public static void CreateTableempl()
            throws IllegalAccessException, InstantiationException, SQLException,
            NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection1 =CreateTableEmployees.getConnection2();
        Statement createTable = connection1.createStatement();
        createTable.execute("drop table if exists employees");
        boolean execute = createTable.execute("create table employees(id integer primary key auto_increment, name varchar(20),last_name varchar(20),points integer)");
        int rows = createTable.executeUpdate("insert into employees values" +
                "(1, 'Rotter','Ala', 120 ),"+
                "(2, 'Kowalski','Piotr', 12 ),"+
                "(3, 'Nowak','Rafal', 5)"

        );
        System.out.println("Wartość zwrócona przez polecenie: " + execute);
        System.out.println("Dodano wierszy : " + rows);
        connection1.close();
      }
}

