import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketPrinter {

    private static final String DB_URL = "jdbc:mysql://localhost/tambola_tickets";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "#Myadmin@123";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM tickets";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("Ticket:");
                        for (int i = 1; i <= 9; i++) {
                            System.out.print(rs.getInt("column_" + i) + "\t");
                        }
                        System.out.println();
                    }
                }
            }
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }
}

/*Certainly! Let's break down the full project code deeply:

1. Main Application Class
java
Copy code
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TambolaticketgenreatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TambolaticketgenreatorApplication.class, args);
    }
}
Explanation:
This class TambolaticketgenreatorApplication is the entry point of the Spring Boot application.
@SpringBootApplication annotation is used to enable various features like auto-configuration and component scanning.
SpringApplication.run() method starts the Spring Boot application.
2. Controller Class
java
Copy code
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/tickets")
    public List<String> getTickets(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM tickets LIMIT ? OFFSET ?";
        return jdbcTemplate.queryForList(sql, String.class, pageSize, offset);
    }
}
Explanation:
This class TicketController is a REST controller responsible for handling HTTP requests related to ticket generation and retrieval.
@RestController annotation is used to indicate that this class is a controller which also returns the response body.
@Autowired annotation injects an instance of JdbcTemplate, which is used for interacting with the database.
@GetMapping("/tickets") annotation maps the HTTP GET requests for the /tickets endpoint to the getTickets() method.
The getTickets() method retrieves tickets from the database using a SQL query constructed based on the provided page and pageSize parameters.
3. Maven Dependencies
xml
Copy code
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
Explanation:
These dependencies are managed by Maven and are required for the Spring Boot application to work.
spring-boot-starter-web provides all necessary dependencies to set up a Spring MVC application.
spring-boot-starter-jdbc provides support for JDBC-based database access with Spring JDBC and JdbcTemplate.
h2 is an in-memory database used for development and testing purposes.
4. Database Configuration
properties
Copy code
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
Explanation:
These properties are specified in the application.properties file.
They configure the connection to the H2 in-memory database.
spring.datasource.url specifies the JDBC URL for connecting to the H2 database.
spring.datasource.driver-class-name specifies the JDBC driver class name.
spring.datasource.username and spring.datasource.password specify the database credentials.
spring.h2.console.enabled=true enables the H2 console for database management.
This explanation should provide a comprehensive understanding of the project code. If you have any further questions or need clarification on specific aspects, feel free to ask!
*/
