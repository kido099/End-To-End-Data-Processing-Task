package sax_parser;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySaxParser {
	private static Connection c = null;
	private static PreparedStatement stmt = null;
	private static String text = "";
	
	class UserHandler extends DefaultHandler {

		boolean isarticle = false;
		boolean isinproceedings = false;
		
		boolean Btitle = false;
		boolean Bbooktitle = false;
		boolean Bjournal = false;
		boolean Byear = false;
		boolean Bauthor = false;
		
		String publicKey = "";
		String tit = "";
		String booktit = "";
		String jour = "";
		String yea = "";


	   @Override
	   public void startElement(String uri, 
	      String localName, String qName, Attributes attributes)
	         throws SAXException {
		   
	      if (qName.equalsIgnoreCase("article")) {
	         String pubkey = attributes.getValue("key");
	         publicKey = pubkey;
	         isarticle = true;
	      } 
	      else if(qName.equalsIgnoreCase("inproceedings")) {
	    	 String pubkey = attributes.getValue("key");
	    	 publicKey = pubkey;
	    	 isinproceedings = true;
	      }
	      else if (qName.equalsIgnoreCase("title") && (isarticle || isinproceedings)) {
	         Btitle = true;
	      } else if (qName.equalsIgnoreCase("booktitle") && isinproceedings) {
	         Bbooktitle = true;
	      } else if (qName.equalsIgnoreCase("journal") && isarticle) {
	         Bjournal = true;
	      }
	      else if (qName.equalsIgnoreCase("year") && (isarticle || isinproceedings)) {
	         Byear = true;
	      }
	      else if(qName.equalsIgnoreCase("author") && (isarticle || isinproceedings)) {
	    	 Bauthor = true;
	      }
	      
	   }
	   
	   @Override
	   public void endElement(String uri, 
	      String localName, String qName) throws SAXException {
		   
	    	  if (Btitle) {
	    			tit = text;
	    		    Btitle = false;
	    	  }
	    	  else if (Bjournal) {
	    			jour = text;
	    		    Bjournal = false;
	    	  }
	    	  else if (Byear) {
	    			yea = text;
	    		    Byear = false;
	    	  }
	    	  else if (Bbooktitle) {
	    			booktit = text;
	    		    Bbooktitle = false;
	    	  }
	    	  
	    	  else if(Bauthor) {
	       		try {
	  		         
	       			System.out.print(publicKey+" ");
	  		    	String theauthor = text;
	  		    		  
	  		    	System.out.println("author: " 
	  		    	      + theauthor);
	  		    	Bauthor = false;

	  		    	String sql = "INSERT INTO Authorship (pubkey, author) "
	     		               + "VALUES (?, ?)";
	  		    	stmt = c.prepareStatement(sql);
	  		    	stmt.setString(1, publicKey);
	  		    	stmt.setString(2, theauthor);
	  		         
	  		    	stmt.executeUpdate();
	  		    	stmt.close();
	       		} catch (Exception e) {
	  		       System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	  		       System.exit(0);
	       		}
	       		System.out.println("Table---Authorship: Records created successfully");
	    	  } 
		   
	      if (qName.equalsIgnoreCase("article")) {

	         isarticle = false;
	         
	         System.out.print(publicKey+" ");
	         System.out.print(tit+" ");
	         System.out.print(jour+" ");
	         System.out.print(yea+" ");
	         System.out.println();
	         // insert data to article table
		      try {
			         String sql = "INSERT INTO article (pubkey,title,journal,year) "
				               + "VALUES (?, ?, ?, ?)";
			         stmt = c.prepareStatement(sql);
			         stmt.setString(1, publicKey);
			         stmt.setString(2, tit);
			         stmt.setString(3, jour);
			         stmt.setString(4, yea);
			         stmt.executeUpdate();
			         stmt.close();
			   } catch (Exception e) {
			         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			         System.exit(0);
			   }
			   System.out.println("Table---article: Records created successfully");
	         
			   System.out.println("End Element : " + qName);
	      }
	      
	      else if(qName.equalsIgnoreCase("inproceedings")) {

	    	 isinproceedings = false;
	    	 
	    	 System.out.print(publicKey+" ");
	         System.out.print(tit+" ");
	         System.out.print(booktit+" ");
	         System.out.print(yea+" ");
	         System.out.println();
	         // insert data to inproceedings table
		      try {
			         String sql = "INSERT INTO inproceedings (pubkey,title,booktitle,year) "
				               + "VALUES (?, ?, ?, ?)";
			         stmt = c.prepareStatement(sql);
			         stmt.setString(1, publicKey);
			         stmt.setString(2, tit);
			         stmt.setString(3, booktit);
			         stmt.setString(4, yea);
			         stmt.executeUpdate();
			         stmt.close();
			  } catch (Exception e) {
			         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
			         System.exit(0);
			  }
			  System.out.println("Table---inproceedings: Records created successfully");
	         
	         
	          System.out.println("End Element : " + qName);
	      }
	      
          text = "";
	   }

	   @Override
	   public void characters(char ch[], 
	      int start, int length) throws SAXException {

          text += String.copyValueOf(ch, start, length).trim();
	   }
}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// open the connection
		try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/mydb1",
	            "postgres", "1221");
	         c.setAutoCommit(false);
	         System.out.println("Table---Authorship: Opened database successfully");
		} catch (Exception e) {
	       System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	       System.exit(0);
		}
        System.out.println("Opened database successfully");
        
		try {	
	         File inputFile = new File("dblp.xml");
	         SAXParserFactory factory = SAXParserFactory.newInstance();
	         SAXParser saxParser = factory.newSAXParser();
	         UserHandler userhandler = new MySaxParser().new UserHandler();
	         saxParser.parse(inputFile, userhandler);     
	    } 
		catch (Exception e) {
	         e.printStackTrace();
	    }
		
		// close the connection
		try {
			c.commit();
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Closed database successfully");
	}

}
