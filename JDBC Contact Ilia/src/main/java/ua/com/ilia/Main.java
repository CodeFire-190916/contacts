package ua.com.ilia;

import java.sql.*;
import java.util.Scanner;

/**
 * Created by Илья on 13.02.2017.
 */
public class Main {

    private static final String CONNECT_STRING = "jdbc:sqlite:db.sqlite";
    private static final String SELECT_ALL_ASC = "SELECT * FROM contacts ORDER BY firstName ASC";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("+-----------+");
            System.out.println("| CONTACTS: |");
            System.out.println("+~~~~~~~~~~~+");
            System.out.println("| a) Show   |");
            System.out.println("| b) Add    |");
            System.out.println("| c) Edit   |");
            System.out.println("| d) Delete |");
            System.out.println("| 0) Exit   |");
            System.out.println("+-----------+");

            String input = scan.nextLine().toLowerCase();

            switch (input) {
                case "a":
                    showAllContacts();
                    break;
                case "b":
                    System.out.println("Pls, enter NAME");
                    String firstName = scan.nextLine();
                    System.out.println("Pls, enter SURNAME");
                    String lastName = scan.nextLine();
                    System.out.println("Pls, enter main numder");
                    String mainNumber = scan.nextLine();
                    System.out.println("Pls, enter secondary numder");
                    String secondaryNumber = scan.nextLine();
                    System.out.println("Pls, enter skype");
                    String skype = scan.nextLine();
                    System.out.println("Pls, enter email");
                    String email = scan.nextLine();
                    addNewContact(firstName, lastName, mainNumber, secondaryNumber, skype, email);
                    break;
                case "c":
                    String field;
                    System.out.println("Pls, enter contact id");
                    showAllContacts();
                    int num = scan.nextInt();
                    System.out.println("Pls, choose the field which you want to edit from the table below");
                    System.out.println("10. First Name");
                    System.out.println("20. Last Name");
                    System.out.println("30. Main Number");
                    System.out.println("40. Secondary Number");
                    System.out.println("50. Skype");
                    System.out.println("60. Email");
                    int data = scan.nextInt();
                    if (data == 10){
                        field = "firstName";
                    }else if (data == 20){
                        field = "lastName";
                    }else if (data == 30){
                        field = "mainNumber";
                    }else if (data == 40){
                        field = "mainNumber";
                    } else if (data == 50){
                        field = "skype";
                    }else {
                        field = "email";
                    }
                    System.out.println("Pls, enter new value");
                    String newData = scan.nextLine();
                    editContact(num, field, newData);
                                        break;
                case "d":
                    System.out.println("Pls, enter contact id");
                    showAllContacts();
                    num = scan.nextInt();
                    deleteContact(num);
                    break;
                case "0":
                    System.out.println("GOOD BYE!");
                    return;
            }
        }

    }

    private static void showAllContacts() {
        try (Connection conn = DriverManager.getConnection(CONNECT_STRING)) {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(SELECT_ALL_ASC);

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("firstName");
                String surname = rs.getString("lastName")
                        + rs.getString("lastName");

                System.out.printf("%03d. %s.%s\n", id, name, surname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addNewContact(String firstName, String lastName, String mainNumber, String secondaryNumber, String skype, String email) {
        try (Connection conn = DriverManager.getConnection(CONNECT_STRING)) {
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("INSERT INTO `contacts` (`firstName`,`lastName`, `mainNumber`, `secondaryNumber`, `skype`, `email`) VALUES ('" + firstName + "', '" + lastName + "', '" + mainNumber + "', '" + secondaryNumber + "', '" + skype + "', '" + email + "')");


            System.out.println("Contact has been added");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private static void editContact(int num, String field, String newData) {
        try (Connection conn = DriverManager.getConnection(CONNECT_STRING)) {
            Statement sm = conn.createStatement();

            sm.executeUpdate("UPDATE `contacts` SET '" + field + "' = '" + newData + "' WHERE `id` = '" + num + "'");


            System.out.println("Contact has been updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    private static void deleteContact(int num) {
        try (Connection conn = DriverManager.getConnection(CONNECT_STRING)) {
            Statement st = conn.createStatement();

            st.executeUpdate("DELETE FROM `contacts` WHERE `id` = '" + num + "' ");

            System.out.println("Contact was deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
