package jdbc;

import jdbc.ConnectionDemo;
import jdbc.CreateTableStatementDemo;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Scanner;public class CRUDApp {
    static Scanner scanner = new Scanner(System.in);
    public static int menu(){
        System.out.println("1. Utwórz tabelę");
        System.out.println("2. Dodaj rekord");
        System.out.println("3. Wyświetl tabelę");
        System.out.println("4. Zaktualizuj tabelę");
        System.out.println("5. Usuń rekord");
        System.out.println("6. Znajdź");
        System.out.println("0. Wyjście");
        while(!scanner.hasNextInt()){
            scanner.nextLine();
        }
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }
    public static void insertRecord() throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        System.out.println("Podaj imię:");
        String name = scanner.nextLine();
        System.out.println("Podaj punkty:");
        int points = scanner.nextInt();
        Connection connection = ConnectionDemo.getConnection();
        PreparedStatement insert = connection.prepareStatement(
                "insert into demo(name, points) values(? ,?)"
        );
        insert.setString(1, name);
        insert.setInt(2, points);
        insert.executeUpdate();
        connection.close();
    }
    //**************************************************
    public static void deleteRecord() throws IllegalAccessException, InstantiationException,
            SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException
    {
        System.out.println("Podaj który rekord chcesz usunąć? ");
        Scanner scan = new Scanner(System.in);
        int id = scan.nextInt();
        Connection connection = ConnectionDemo.getConnection();
        Statement delete = connection.createStatement();
        delete.executeUpdate("delete from demo where id ="+id);
        connection.close();
    }
    //************
    public static void updateRecord() throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        System.out.println("Podaj, który rekord chcesz edytować? ");
        int id = scanner.nextInt();
        System.out.println("Podaj nowe imię:");
        String name = scanner.next();
        System.out.println("Podaj nowe punkty:");
        int points = scanner.nextInt();    Connection connection = ConnectionDemo.getConnection();    //zapytanie zabepieczone przed SQL injection
        PreparedStatement update = connection.prepareStatement("update demo set name = ?, points = ? where id =?");
        //wstawienie wartości parametru w miejscu pierwszego pytajnika
        update.setString(1, name);
        //wstawienie wartości parametru w miejscu drugiego pytajnika
        update.setInt(2,points);
        //wstawienie wartości parametru w miejscu trzeciego pytajnika
        update.setInt(3,id);
        // wykonanie zapytania UPDATE
        update.executeUpdate();
        System.out.println("rekord " + id + " został edytowany");
        connection.close();
    }
    //***********wyszukiwanie
    public static void find() throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        Connection connection = ConnectionDemo.getConnection();
        System.out.println("Podaj dane do wyszukania:");
        String dataToSearch = scanner.nextLine();

        PreparedStatement find = connection.prepareStatement("select * from demo where point = ? or name = ?");
        find.setString(1, dataToSearch);
        find.setInt(2, Integer.parseInt(dataToSearch));
        //wywołanie zapytania
        ResultSet resultSet = find.executeQuery();
        SelectDemo.printDemoTable(resultSet);
        connection.close();
    }
    //**************
    //***********
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, SQLException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        while(true){
            switch (menu()){
                case 1:
                    //TODO zdefiniować metodę tworzenie własnej tabeli
                    CreateTableStatementDemo.createTableDemo();
                    break;
                case 2:
                    insertRecord();
                    break;
                case 3:
                    //wyświetl tabelę
                    break;
                case 4:
                    updateRecord();
                    break;
                case 5:
                    deleteRecord();
                    break;
                case 6:
                    find();
                    break;

                case 0:
                    System.exit(0);
                    //TODO dodać modyfikację, usuwanie i wyszukiwanie rekordów
            }
        }
    }
}