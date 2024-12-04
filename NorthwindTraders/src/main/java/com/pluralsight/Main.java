package com.pluralsight;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // load the MySQL Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot load SQL driver...");
            System.exit(1);
        }

        if (args.length != 2) {
            System.out.println(
                    "Application needs two arguments to run: " +
                            "java com.pluralsight.wb8demo2 <username> <password>");
            System.exit(1);
        }

        // get the username and password from the command line args
        String username = args[0];
        String password = args[1];



        boolean running = true;

        try {
            while (running) {
                System.out.println("Please select from the following options: ");
                System.out.println("1) Display All Products");
                System.out.println("2) Display All Customers");
                System.out.println("3) Display All Categories");
                System.out.println("0) Exit");
                System.out.print("Enter selection: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> displayProducts(username, password);
                    case 2 -> displayCustomer(username, password);
                    case 3 -> displayCategories(username, password);
                    case 0 -> running = false;
                    default -> System.out.println("Invalid selection, please try again.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void displayProducts(String username, String password) {

        try {
            // 1. open a connection to the database
            // use the database URL to point to the correct database

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

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

        } catch (SQLException e) {
            System.out.println("There was an SQL issue:");
            e.printStackTrace();
        }
    }

    private static void displayCustomer(String username, String password) {

        try {
            // 1. open a connection to the database
            // use the database URL to point to the correct database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);

            // create statement
            // the statement is tied to the open connection
            PreparedStatement pStatement = connection.prepareStatement("""
                    SELECT ContactName, CompanyName, City, Country, Phone
                    FROM northwind.customers
                    WHERE Country IS NOT NULL
                    ORDER BY Country;""");

            // 2. Execute your query
            ResultSet results = pStatement.executeQuery();
            // process the results
            while (results.next()) {
                String contactName = results.getString("ContactName");
                String companyName = results.getString("CompanyName");
                String city = results.getString("City");
                String country = results.getString("Country");
                String phone = results.getString("Phone");
                System.out.println("Contact Name: " + contactName);
                System.out.println("Company Name: " + companyName);
                System.out.println("City: " + city);
                System.out.println("Country: " + country);
                System.out.println("Phone: " + phone);
                System.out.println("----------------------------------------");
            }
            // 3. Close the connection
            results.close();
            pStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("There was an SQL issue:");
            e.printStackTrace();
        }

    }

    private static void displayCategories(String username, String password) {
        Scanner scanner = new Scanner(System.in);

        // 1. open a connection to the database
        // use the database URL to point to the correct database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/northwind", username, password);) {

            // create statement
            // the statement is tied to the open connection
            PreparedStatement pStatement = connection.prepareStatement("""
                    SELECT CategoryID, CategoryName
                    FROM northwind.categories
                    ORDER BY CategoryID;""");
            {

            // 2. Execute your query
            // process the results
                try (ResultSet results = pStatement.executeQuery()) {
                    while (results.next()) {
                        int categoryID = results.getInt("CategoryID");
                        String categoryName = results.getString("CategoryName");
                        System.out.println("Category ID: " + categoryID);
                        System.out.println("Category Name: " + categoryName);
                        System.out.println("----------------------------------------");
                    }
                }

                System.out.print("\nPlease enter a Category ID: ");
                int selectedCategoryID = scanner.nextInt();
                System.out.println("----------------------------------------");

                try (PreparedStatement statement = connection.prepareStatement("""
                    SELECT ProductID, ProductName, UnitPrice, UnitsInStock
                    FROM northwind.products
                    WHERE CategoryID = ?;""")) {

                    statement.setInt(1, selectedCategoryID);

                    try (ResultSet idresults = statement.executeQuery()) {
                        while (idresults.next()) {
                            int productID = idresults.getInt("ProductID");
                            String productName = idresults.getString("ProductName");
                            double unitPrice= idresults.getDouble("UnitPrice");
                            int unitsInStock = idresults.getInt("UnitsInStock");

                            System.out.println("Product ID: " + productID);
                            System.out.println("Product Name: " + productName);
                            System.out.println("Unit Price: $" + unitPrice);
                            System.out.println("Units in stock: " + unitsInStock);
                            System.out.println("----------------------------------------");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("There was an SQL issue: ");
            e.printStackTrace();
        }
    }
}
