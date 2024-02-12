import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TambolaTicketGenerator {
    private static final String DB_URL = "jdbc:mysql://localhost/tambola_tickets";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "#Myadmin@123";

    public static void main(String[] args) {
        int numberOfSets = 5; // Change this to the desired number of sets

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            for (int i = 0; i < numberOfSets; i++) {
                List<List<Integer>> tickets = generateTambolaTickets();
                for (List<Integer> ticket : tickets) {
                    saveTicket(conn, ticket);
                }
            }
            System.out.println("Tickets generated and saved successfully!");
        } catch (SQLException e) {
            System.err.println("Error retrieving tickets: " + e.getMessage());
        }
    }

   private static List<List<Integer>> generateTambolaTickets() {
    List<List<Integer>> tickets = new ArrayList<>();
    List<Integer> numbers = new ArrayList<>();
    for (int i = 1; i <= 90; i++) {
        numbers.add(i);
    }
    Collections.shuffle(numbers);
    for (int t = 0; t < 6; t++) {
        List<Integer> ticket = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                int number = getRandomNumber(numbers, i * 10 + 1, (i + 1) * 10);
                ticket.add(number);
            }
        }
        tickets.add(ticket);
    }
    return tickets;
}

    private static int getRandomNumber(List<Integer> numbers, int from, int to) {
        int randomNumber = 0;
        for (int i = from; i < to; i++) {
            if (!numbers.isEmpty() && numbers.contains(i)) {
                randomNumber = i;
                numbers.remove(Integer.valueOf(i));
                break;
            }
        }
        return randomNumber;
    }

    private static void saveTicket(Connection conn, List<Integer> ticket) throws SQLException {
        String sql = "INSERT INTO tickets (ticket_number, column_1, column_2, column_3, column_4, column_5, column_6, column_7, column_8, column_9) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ticket.get(0));
            for (int i = 1; i <= 9; i++) {
                pstmt.setInt(i + 1, ticket.get(i));
            }
            pstmt.executeUpdate();
        }
    }
}
