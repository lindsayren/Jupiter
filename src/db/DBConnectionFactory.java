package db;

import db.mysql.MySQLConnection;

//有这个class的好处(factory pattern)：程序很多地方都需要建立和数据库的连接
//假如没有这个class DBConnection connection= newMYSQLDBConnection();
//当需要改数据库每个地方都要把MYSQL改成MyMongoDB，有这个factory，只需要把DEFAULT_DB改成 mongodb，不用每个地方都改
public class DBConnectionFactory {
	// This should change based on the pipeline.
		private static final String DEFAULT_DB = "mysql";
		
		public static DBConnection getConnection(String db) throws IllegalArgumentException{
			switch(db) {
			case"mysql":
				return new MySQLConnection();
//new MySQLDBConnection()
			case"mongodb":
				return null;//new MyMongoDBConnection()
			default:
				throw new IllegalArgumentException("Invalid db:" + db);			
			}
		}
		public static DBConnection getConnection() throws IllegalArgumentException{
			return getConnection(DEFAULT_DB);
		}

}
