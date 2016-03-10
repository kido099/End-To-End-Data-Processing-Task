package sax_parser;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class createTable {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection c = null;
	       Statement stmt = null;
	       try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/mydb1",
	            "postgres", "1221");
	         System.out.println("Opened database successfully");

	         stmt = c.createStatement();
	         // Article
	         String sql1 = "CREATE TABLE Article " +
	                      "(pubkey         TEXT PRIMARY KEY     NOT NULL," +
	                      " title          TEXT," +
	                      " journal        TEXT," +
	                      " year           TEXT)";
	         // Inproceedings
	         String sql2 = "CREATE TABLE  Inproceedings" +
                     "(pubkey         TEXT PRIMARY KEY     NOT NULL," +
                     " title          TEXT," +
                     " booktitle      TEXT," +
                     " year           TEXT)";
	         // Authorship
	         String sql3 = "CREATE TABLE Authorship" +
                     "(pubkey         TEXT     NOT NULL," +
                     " author         TEXT)";
	         stmt.executeUpdate(sql1);
	         stmt.executeUpdate(sql2);
	         stmt.executeUpdate(sql3);
	         stmt.close();
	         c.close();
	       } catch ( Exception e ) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	       }
	       System.out.println("Table created successfully");
	}

}
