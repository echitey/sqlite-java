package com.echitey.java.works;

import sun.security.util.Password;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    /*

        - Create SQLite database in the project root
        - Download SQLite jdbc from https://bitbucket.org/xerial/sqlite-jdbc/downloads/
        - Copy in the project root dir
        - In Project Properties -> Build Path, Add the library
        - Create connection and test it

     */


    public static void main(String[] args) {

        Connection connection = null;
        Statement statement;
        String sqlQuery;

        // Menu
        System.out.println("Menu: \n");
        System.out.println("1- Add\n2- Select All\n3- Delete\n4- Update Password\n");

        while(true) {

            System.out.println("\nEnter your choice: ");

            try {

                // DEFINE CONNECTION
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:demo.db");
                // If database doesnt exist, it will be created
                //System.out.println("Connection OK!");

                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        Scanner userNameScanner = new Scanner(System.in);
                        Scanner passwordScanner = new Scanner(System.in);
                        System.out.println("Enter the Username: ");
                        String userName = userNameScanner.nextLine();
                        System.out.println("Enter the Password: ");
                        String password = passwordScanner.nextLine();

                        System.out.println("Saving  the user data... ");

                        // Insert
                        sqlQuery = "insert into " +
                                "user(user_name, password) " +
                                "values('" + userName + "', '" + password + "')";

                        statement = connection.createStatement();
                        statement.executeUpdate(sqlQuery);
                        System.out.println("User Successfully saved! ");
                        statement.close();
                        connection.close();

                        break;


                    case 2:

                        sqlQuery = "select * from user";
                        // The Result is a type os ResultSet
                        statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(sqlQuery);


                        while (resultSet.next()) {
                            int _id = resultSet.getInt("id");
                            String _username = resultSet.getString("user_name");
                            String _password = resultSet.getString("password");

                            System.out.println("User{" +
                                    "id=" + _id +
                                    ", username='" + _username + '\'' +
                                    ", password='" + _password + '\'' +
                                    '}');
                        }

                        resultSet.close();
                        statement.close();
                        connection.close();

                        break;


                    case 3:

                        Scanner idScanner = new Scanner(System.in);
                        System.out.println("Enter the UserID: ");
                        int id = idScanner.nextInt();

                        System.out.println("Deleting the user data with id: " +id);
                        // Delete
                        sqlQuery = "delete from user " +
                                "where id= " + id;

                        statement = connection.createStatement();
                        statement.executeUpdate(sqlQuery);
                        System.out.println("User Successfully deleted! ");
                        //connection.commit(); // After Create - Update - Delete
                        statement.close();
                        connection.close();

                        break;


                    case 4:

                        idScanner = new Scanner(System.in);
                        System.out.println("Enter the UserID: ");
                        int _id = idScanner.nextInt();

                        Scanner passScanner = new Scanner(System.in);
                        System.out.println("Enter the new Password: ");
                        String _pass = passScanner.nextLine();

                        System.out.println("Editing the user data with id: " +_id);
                        // Edit
                        sqlQuery = "update user " +
                                "set password = '" + _pass + "' " +
                                "where id= " + _id;

                        statement = connection.createStatement();
                        statement.executeUpdate(sqlQuery);
                        //connection.commit(); // After Create - Update - Delete
                        System.out.println("User Successfully edited! ");
                        statement.close();
                        connection.close();

                        break;

                }


            } catch (Exception exc) {
                exc.printStackTrace();
                System.exit(0);
            }
        }
    }

}
