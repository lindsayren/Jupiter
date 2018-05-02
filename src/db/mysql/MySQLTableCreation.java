package db.mysql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class MySQLTableCreation {
	// Run this as Java application to reset db schema.
	public static void main(String[] args) {
		try {
			// This is java.sql.Connection. Not com.mysql.jdbc.Connection.
			Connection conn = null;

			// Step 1 Connect to MySQL.
			try {
				System.out.println("Connection to " + MySQLDBUtil.URL);
				//把driver加到list里
				//com.mysql.jdbc.Driver 这个class 可以建立和mysql的连接
				//也可以写成 com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver()，强制jvm加载这个class运行静态块，注册了driver自己，加到driver list里
				//但是创建了一个没用的reference，所以写成下面的方法
				Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance();
				//遍历driver的list，建立与mysql的连接
				conn = DriverManager.getConnection(MySQLDBUtil.URL);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			if (conn == null) {
				return;
			}
			// Step 2 Drop tables in case they exist
			Statement stmt = conn.createStatement();
			//需要先做,有foreign key指向 users 和item			
			//需要先做，有foreign key指向 users 和item
			String sql = "DROP TABLE IF EXISTS history";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS categories";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS items";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS users";
			stmt.executeUpdate(sql);


			//Step 3 Create tables
			//item id-- primary key not null
			//executeQuery 和 executeUpdate返回值不同 前者可以返回读到的内容， 后者返回int
			sql = "CREATE TABLE items " + "(item_id VARCHAR(255) NOT NULL, " + " name VARCHAR(255), " + "rating FLOAT,"
					+ "address VARCHAR(255), " + "image_url VARCHAR(255), " + "url VARCHAR(255), " + "distance FLOAT, "
					+ " PRIMARY KEY ( item_id ))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE categories " + "(item_id VARCHAR(255) NOT NULL, " + " category VARCHAR(255) NOT NULL, "
					+ " PRIMARY KEY ( item_id, category), " + "FOREIGN KEY (item_id) REFERENCES items(item_id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE users " + "(user_id VARCHAR(255) NOT NULL, " + " password VARCHAR(255) NOT NULL, "
					+ " first_name VARCHAR(255), last_name VARCHAR(255), " + " PRIMARY KEY ( user_id ))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE history " + "(user_id VARCHAR(255) NOT NULL , " + " item_id VARCHAR(255) NOT NULL, "
					+ "last_favor_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " + " PRIMARY KEY (user_id, item_id),"
					+ "FOREIGN KEY (item_id) REFERENCES items(item_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id))";
			stmt.executeUpdate(sql);
			//INSERT INTO users VALUES("1111","2222")
			//Step 4 Insert fake user
			sql = "INSERT INTO users VALUES (\"1111\", \"3229c1097c00d497a0fd282d586be050\", \"John\", \"Smith\")";
			stmt.executeUpdate(sql);

			System.out.println("Import is done successfully.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

