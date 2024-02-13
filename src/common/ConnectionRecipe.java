package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
recipe DB에 접속하기 위한 클래스
*/


public class ConnectionRecipe {
   public static boolean DEBUG = false;
   
   private static String driver = "oracle.jdbc.driver.OracleDriver";
   private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
   private static String dbId = "recipe";
   private static String dbPw = "recipe";
   private static Connection conn;
   
   public static Connection getConnection() {
      try {
         if(conn.isClosed()) connectionRecipe();
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return conn;
   }
   public static void connectionRecipe() {
      // recipe DB에 접속
      try {
         Class.forName(driver);
         conn = DriverManager.getConnection(url, dbId, dbPw);
         if(DEBUG) System.out.println("접속 성공");
      } catch(ClassNotFoundException e) {
         e.printStackTrace();
         if(DEBUG) System.out.println("접속 실패");
      } catch(SQLException e) {
         e.printStackTrace();
         if(DEBUG) System.out.println("접속 실패");
      }
   }
   public static void disConnectionRecipe() {
      // recipe DB에 접속 종료
      try {
         if(conn != null) conn.close();
         if(DEBUG) System.out.println("접속 종료");
      } catch(SQLException e) {
         e.printStackTrace();
         if(DEBUG) System.out.println("접속 종료 실패");
      } catch(NullPointerException e) {
         e.printStackTrace();
         if(DEBUG) System.out.println("접속 종료 실패");
      }
   }
}