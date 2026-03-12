package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            TaskDB taskDB = new TaskDB();
            View view = new View();
            Controller controller = new Controller(taskDB, view);

            view.setVisible(true);
        });
    }
}