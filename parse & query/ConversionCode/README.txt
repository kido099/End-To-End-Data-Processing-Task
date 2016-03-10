I have two java files to finish the Part1 job: createTable.java and MySaxParser.java.

Before running these two files, you need to create your database in PostgreSql, I use the graphical interface for psql: pgadmin.

In createTable.java file, I create three tables in the database: Article, Inproceedings, and Authorship. I use JDBC to access the PostgreSql. So first you need to run this file.

In MySaxParser.java, I parse the dblp.xml and write the data into the database. Every time after reading an article or a inproceedings, I insert the data into the database.
Remember, you need to make the database name and the password the same between when you create the database in pgadmin and in java file. So secondly, you need to run this file.