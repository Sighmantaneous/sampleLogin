package ie.atu.week5.samplelogin;

import org.springframework.web.bind.annotation.*;

import java.sql.*;


@RestController
@RequestMapping("/homepage")
public class LoginController {

    MariaDB db  = new MariaDB();

    @GetMapping("/register")
        public String register(@RequestParam String username, @RequestParam String password){
        try (Connection conn = db.getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement(
                    "SELECT COUNT(*) FROM userlogin WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) return "Username already exists!";


            PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO userlogin (username, password) VALUES (?, ?)");
            insertStmt.setString(1, username);
            insertStmt.setString(2, password); // hash in real apps
            insertStmt.executeUpdate();

            return "User registered!";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    @GetMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        try (Connection conn = db.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT password FROM userlogin WHERE username = ?");
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) { // compare hashed passwords in real apps
                    return "Login successful!";
                }
            }
            return "Invalid username or password.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    }

