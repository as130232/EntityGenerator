package util.generator.EntityGenerator;


import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import util.generator.EntityGenerator.entityBulider.EntityBulider;
import util.generator.EntityGenerator.model.Table;
import util.generator.EntityGenerator.util.DataBaseUtil;

@SpringBootApplication
public class EntityGeneratorApplication {

	public static void main(String[] args) {
		// SpringApplication.run(EntityGeneratorApplication.class, args);
		Connection connection = null;
		List<Table> tableList = null;
		try {
			Class.forName(AppConfig.driverName);

			connection = DriverManager.getConnection(AppConfig.url, AppConfig.username, AppConfig.psw);

			tableList = DataBaseUtil.getAllTables(connection);
			EntityBulider entityBulider = new EntityBulider();
			for (Table table : tableList) {
				System.out.println("Start build " + table.getTableName() + " entity.");
				entityBulider.buildBean(table);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
