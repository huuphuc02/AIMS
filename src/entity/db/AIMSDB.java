// Source code is decompiled from a .class file using FernFlower decompiler.
package entity.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AIMSDB {
  public AIMSDB() {
  }

  public static Connection getConnection() {
    Connection connection = null;
    String url = "jdbc:mysql://localhost:3306/aims";
    String user = "root";
    String password = "";

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(url, user, password);
      System.out.println("Connected to the database");
    } catch (SQLException | ClassNotFoundException var5) {
      System.out.println("Error: " + var5.getMessage());
    }

    return connection;
  }

  public static void main(String[] args) {
    Connection connection = getConnection();

    try {
      if (connection != null) {
        connection.close();
        System.out.println("Disconnected from the database");
      }
    } catch (SQLException var3) {
      System.out.println("Error: " + var3.getMessage());
    }

  }
}
