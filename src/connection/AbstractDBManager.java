package connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class AbstractDBManager {
	private static String dbPassword;

	private static String dbUrl;

	private static String dbUsername;

	protected String query = new String();

	protected String response = new String();

	private static Properties getPropertiesQuietly() {
		final Properties properties = new Properties();
		try {
			properties.load(AbstractDBManager.class.getClassLoader()
					.getResourceAsStream("db.properties"));
		} catch (final IOException e) {
			throw new RuntimeException("Cannot load db properties file", e);
		}
		return properties;
	}

	protected AbstractDBManager() {
		loadDBProperties();
	}

	private static void loadDriver(final String dbDriver) {
		try {
			Class.forName(dbDriver);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("Cannot load the driver class", e);
		}
	}

	protected static void loadDBProperties() {
		final Properties properties = getPropertiesQuietly();
		final String dbDriver = properties.getProperty("db.driver");
		dbUrl = properties.getProperty("db.url");
		dbUsername = properties.getProperty("db.username");
		dbPassword = properties.getProperty("db.password");
		loadDriver(dbDriver);
	}

	protected java.sql.Date convertJavaDateToSqlDate(final java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	protected Connection createConnection() {
		try {
			return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
		} catch (final SQLException e) {
			throw new RuntimeException("Cannot create the connection", e);
		}
	}

	protected void closeConnection() {
		try {
			DriverManager.getConnection(dbUrl, dbUsername, dbPassword).close();
		} catch (final SQLException e) {
			throw new RuntimeException("Cannot create the connection", e);
		}
	}

}
