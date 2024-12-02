package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.wb8demo2 <username> <password>");
            System.exit(1);
        }

        // get the username and password from the command line args
        String username = args[0];
        String password = args[1];


        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running){
            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();

            switch (option){
                case 1 -> {
                    displayProducts(username, password);
                }
                case 2 -> {
                    displayCustomer(username, password);
                }
                case 0 -> {
                    running = false;
                    System.out.println("Exiting...");
                }
            }
        }


    }

    public static void displayProducts(String username, String password){
//
//        if (args.length != 2) {
//            System.out.println(
//                    "Application needs two arguments to run: " +
//                            "java com.pluralsight.wb8demo2 <username> <password>");
//            System.exit(1);
//        }
//
//        // get the username and password from the command line args
//        String username = args[0];
//        String password = args[1];

        try{

            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection;

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

            // create statement
            // the statement is tied to the open connection
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM northwind.products;");

            // 2. Execute your query
            ResultSet results = pStatement.executeQuery();
            // process the results
            while (results.next()) {
                String productId = results.getString("ProductId");
                String productName = results.getString("ProductName");
                double unitPrice = results.getDouble("UnitPrice");
                int unitsInStock = results.getInt("UnitsInStock");
                System.out.println("Product ID: " + productId);
                System.out.println("Name: " + productName);
                System.out.println("Price: " + unitPrice);
                System.out.println("Stock: " + unitsInStock);
                System.out.println("----------------------------------------");
            }
            // 3. Close the connection
            results.close();
            pStatement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("There was an issue finding a class:");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.out.println("There was an SQL issue:");
            e.printStackTrace();
        }
    }

    public static void displayCustomer(String username, String password){
        try{

            // load the MySQL Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection;

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

            // create statement
            // the statement is tied to the open connection
            PreparedStatement pStatement = connection.prepareStatement("SELECT * FROM northwind.products;");

            // 2. Execute your query
            ResultSet results = pStatement.executeQuery();
            // process the results
            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("companyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("phoneNumber");
                System.out.println("Contact Name: " + contactName);
                System.out.println("Company Name: " + companyName);
                System.out.println("City: " + city);
                System.out.println("Country: " + country);
                System.out.println("Phone Number: " + phone);
                System.out.println("----------------------------------------");
            }
            // 3. Close the connection
            results.close();
            pStatement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            System.out.println("There was an issue finding a class:");
            e.printStackTrace();
        }
        catch (SQLException e) {
            System.out.println("There was an SQL issue:");
            e.printStackTrace();
        }

    }



}