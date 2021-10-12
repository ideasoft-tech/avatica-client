package uy.ideasoft.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AvaticaMongoTest
{
	public static void main(String[] args) throws SQLException
	{
		String database_url = "http://localhost:8765";
		//database_url = "http://192.168.10.209:8765";
		
		String connectUrl = "jdbc:avatica:remote:url=" + database_url + ";serialization=protobuf; authentication=NONE";
		System.err.println("URL = " +connectUrl);

		Properties connectionProperties = new Properties();
		connectionProperties.setProperty("user", "admin");
		connectionProperties.setProperty("password", "admin");
		
		System.err.println("CLASSPATH=" + System.getProperty("java.class.path"));

		try {
			Connection connection = DriverManager.getConnection(connectUrl, connectionProperties);
			System.err.println("CONNECTED");
			
			Statement statement = connection.createStatement();
			System.err.println("HAS A STATEMENT");

			String query = "SELECT 1337";
			//query="select * from \"_foodmart\".\"warehouse\"";
			//query="select cast(_MAP['warehouse_id'] as varchar(10)) AS ID, _MAP['warehouse_name'] from \"_foodmart\".\"warehouse\"";
			//query="select * from \"mixed\".\"warehouse\" where _MAP['warehouse_id'] in (select \"mixed\".\"mytempview\".\"id\" from \"mixed\".\"mytempview\" where \"mixed\".\"mytempview\".\"id\" not in (1,3))";
			String innerquery = "select \"mixed\".\"mytempview\".\"id\" from \"mixed\".\"mytempview\" where \"mixed\".\"mytempview\".\"id\" not in (1,3)";
			//query="select * from \"mixed\".\"warehouse\" ";
			//query="select * from \"mixed\".\"mytempview\" MT, \"mixed\".\"warehouse\" MW where MT.\"id\" = _MAP['warehouse_id']";
			//query="select WHID from \"mixed\".\"warehouse\" where WHID in (select \"mixed\".\"mytempview\".\"id\" from \"mixed\".\"mytempview\" where \"mixed\".\"mytempview\".\"id\" not in (1,3))";
			//query = "select \"mixed\".\"mytempview\".\"id\" from \"mixed\".\"mytempview\"";
			query="select WHID, WHNAME from \"mixed\".\"warehouse\" where WHID in (select \"pgschema\".\"mytemptable\".\"id\" from \"pgschema\".\"mytemptable\")";
			query="select WHID, WHNAME from \"mixed\".\"warehouse\" where WHID not in (select \"mixed\".\"mytempview\".\"id\" from \"mixed\".\"mytempview\")";
			//query="select WHNAME from \"mixed\".\"warehouse\", \"mixed\".\"mytempview\" where WHID = \"mixed\".\"mytempview\".\"id\"";
			//query="select \"mytemptable\".\"id\" from \"pgschema\".\"mytemptable\" ";
			System.out.println(query);
			ResultSet resultSet = statement.executeQuery(query);
			System.err.println("HAS A resultSet");System.err.flush();
			
			while (resultSet.next()) {
				int colcount = resultSet.getMetaData().getColumnCount();
				for (int idx = 1; idx <= colcount; idx++) {
					System.out.println(resultSet.getMetaData().getColumnName(idx) + ": " + resultSet.getString(idx));
				}
//				System.out.println(resultSet.getMetaData().getColumnName(1) + ": " + resultSet.getString(1));
//				System.out.println(resultSet.getMetaData().getColumnName(2) + ": " + resultSet.getString(2));
			}
			System.out.flush();
			System.err.println("END OF DATA");System.err.flush();
			
			
/*			ResultSet innerResultSet = statement.executeQuery(innerquery);
			System.err.println("HAS innerResultSet");System.err.flush();
			
			while (innerResultSet.next()) {
				System.out.println("IRS: " + innerResultSet.getString(1));
			}
			System.out.flush();
			System.err.println("END OF innerData");System.err.flush();
*/		} finally {
			
		}
	}
}
