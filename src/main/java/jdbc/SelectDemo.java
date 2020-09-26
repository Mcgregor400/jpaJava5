package jdbc;

import jdbc.ConnectionDemo;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;

public class SelectDemo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection = ConnectionDemo.getConnection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj literę:");
        String letter = scanner.nextLine();
        //zapytanie podatne na SQL injection
        //Statement find = connection.createStatement();
        //ResultSet resultSet = find.executeQuery("select * from demo where id = " + letter);

        //zapytanie zabezpieczone przed SQL injection
        PreparedStatement find = connection.prepareStatement("select * from demo where id = ?");
        //wstawienie wartości parametru w miejscu pierwszego pytajnika
        find.setInt(1, Integer.parseInt(letter));
        //wywołanie zapytania
        ResultSet resultSet = find.executeQuery();
        printDemoTable(resultSet);
        connection.close();
    }

    public static void printDemoTable(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            System.out.print("id: " + resultSet.getInt("id"));
            //pobieramy wartość z kolumny points
            int points =  resultSet.getInt("points");
            //testujemy czy poprzednio pobrana wartość z kolumny 'points' nie była w bazie NULL'em
            boolean isPointsWasNull = resultSet.wasNull();
            System.out.print(", points: " + (isPointsWasNull ? "null" : points));
            System.out.println(", name: " + resultSet.getString("name"));
        }
    }
}