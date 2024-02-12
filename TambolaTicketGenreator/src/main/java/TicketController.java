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
