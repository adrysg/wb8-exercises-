package com.pluralsight;

import java.sql.*;

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
}