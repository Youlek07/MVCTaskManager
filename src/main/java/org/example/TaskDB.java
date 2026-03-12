package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDB {

    private final String url = "jdbc:sqlite:tasks.db";

    public TaskDB() {
        utworzTabele();
    }

    private void utworzTabele() {
        String sql = """
                CREATE TABLE IF NOT EXISTS tasks (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT,
                    is_done INTEGER NOT NULL DEFAULT 0
                );
                """;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean dodajZadanie(String title, String description) {
        String sql = "INSERT INTO tasks(title, description, is_done) VALUES(?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, description);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void usunZadanie(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ustawWykonane(int id, boolean wykonane) {
        String sql = "UPDATE tasks SET is_done = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, wykonane ? 1 : 0);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> pobierzWszystkie() {
        List<Task> lista = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Task(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("is_done") == 1
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
