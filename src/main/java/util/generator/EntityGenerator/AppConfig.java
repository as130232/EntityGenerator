package util.generator.EntityGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

public class AppConfig {
	public static String url;
	public static String username;
	public static String psw;
	public static String catalog;
	public static String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static String schemaPattern = null;
	public static String tableNamePattern = null;
	public static String[] types = { "TABLE" };
	public static String outputPath = ".";
	public static String rootPackageName = "com.mes";

	static {
		Properties properties = new Properties();
		try {
			// properties.load(new
			// FileInputStream(System.getProperty("user.dir") + "./db.ini"));
			properties.load(new FileInputStream("D:/beanGeneratro" + "./db.ini"));
			String dbIp = properties.getProperty("dbip");
			String dbPort = properties.getProperty("dbport");
			String dbname = properties.getProperty("dbname");
			catalog = properties.getProperty("dbname");
			username = properties.getProperty("username");
			psw = properties.getProperty("psw");

			url = "jdbc:sqlserver://" + dbIp + ":" + dbPort + ";DatabaseName=" + dbname;
		} catch (IOException e) {
			System.err.println("AppConfig fail.");
			e.printStackTrace();
		}
	}
}
