package uy.ideasoft.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AvaticaPostgresTest
{
	public static void main(String[] args) throws SQLException
	{
		String database_url = "http://localhost:8765";
		
		String connectUrl = "jdbc:avatica:remote:url=" + database_url + ";serialization=protobuf; authentication=NONE"; 
		System.err.println("URL = " +connectUrl);

		Properties connectionProperties = new Properties();
		//connectionProperties.setProperty("<avatica_user>", "admin");
		//connectionProperties.setProperty("<avatica_password>", "admin");
		//connectionProperties.setProperty("user", "postgres");
		//connectionProperties.setProperty("password", "postgres");

		String query = "SELECT 1337";
		query="select * from mytemptable";
		try {
			Connection connection = DriverManager.getConnection(connectUrl, connectionProperties);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1));
			}
		} finally {
			
		}
	}
}
