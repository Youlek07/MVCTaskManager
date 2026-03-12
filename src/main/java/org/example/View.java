package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class View extends JFrame {

    private DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Tytuł", "Opis", "Wykonane"}, 0) {
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return switch (columnIndex) {
                case 0 -> Integer.class;
                case 1 -> String.class;
                case 2 -> String.class;
                case 3 -> Boolean.class;
                default -> Object.class;
            };
        }

        @Override
        public boolean isCellEditable(int row, int col) {
            return col == 3;
        }
    };

    public JTable tabela = new JTable(tableModel);

    private JTextField titleField = new JTextField(12);
    private JTextField descriptionField = new JTextField(16);

    private JButton wczytajBtn = new JButton("Wczytaj Zadania");
    private JButton dodajBtn = new JButton("Dodaj Zadanie");
    private JButton usunBtn = new JButton("Usuń");

    private JLabel statusLabel = new JLabel("Status: Oczekuje...");
    private JProgressBar progressBar = new JProgressBar();

    public View() {
        super("Task Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(750, 450);

        Font fontTabela = new Font("Segoe UI", Font.PLAIN, 14);
        Font fontUI = new Font("Segoe UI", Font.BOLD, 13);

        tabela.setFont(fontTabela);
        tabela.setRowHeight(24);
        tabela.getTableHeader().setFont(fontUI);
        tabela.setAutoCreateRowSorter(true);
        tabela.setFillsViewportHeight(true);
        tabela.setGridColor(new Color(200, 200, 220));
        tabela.getTableHeader().setBackground(new Color(25, 50, 120));
        tabela.getTableHeader().setForeground(Color.WHITE);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(160);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(280);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(80);

        titleField.setFont(fontUI);
        descriptionField.setFont(fontUI);

        wczytajBtn.setFont(fontUI);
        wczytajBtn.setBackground(new Color(33, 150, 243));
        wczytajBtn.setForeground(Color.WHITE);

        dodajBtn.setFont(fontUI);
        dodajBtn.setBackground(new Color(76, 175, 80));
        dodajBtn.setForeground(Color.WHITE);

        usunBtn.setFont(fontUI);
        usunBtn.setBackground(new Color(244, 67, 54));
        usunBtn.setForeground(Color.WHITE);

        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));

        progressBar.setIndeterminate(true);
        progressBar.setVisible(false);
        progressBar.setPreferredSize(new Dimension(120, 18));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        topPanel.setBackground(new Color(235, 240, 255));
        topPanel.add(new JLabel("Tytuł:"));
        topPanel.add(titleField);
        topPanel.add(new JLabel("Opis:"));
        topPanel.add(descriptionField);
        topPanel.add(dodajBtn);
        topPanel.add(wczytajBtn);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        bottomPanel.setBackground(new Color(235, 240, 255));
        bottomPanel.add(usunBtn);
        bottomPanel.add(statusLabel);
        bottomPanel.add(progressBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 250));
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        this.add(mainPanel);
    }

    public void setLista(List<Task> zadania) {
        tableModel.setRowCount(0);
        for (Task t : zadania) {
            tableModel.addRow(new Object[]{
                    t.getId(),
                    t.getTitle(),
                    t.getDescription(),
                    t.isDone()
            });
        }
    }

    public String getTitleText() { return titleField.getText().trim(); }
    public String getDescriptionText() { return descriptionField.getText().trim(); }

    public Integer getSelectedId() {
        int row = tabela.getSelectedRow();
        if (row == -1) return null;
        return (Integer) tabela.getValueAt(row, 0);
    }

    public void clearInputFields() {
        titleField.setText("");
        descriptionField.setText("");
    }

    public void setStatus(String msg, java.awt.Color color) {
        statusLabel.setText("Status: " + msg);
        statusLabel.setForeground(color);
    }

    public void setLoadButtonEnabled(boolean enabled) { wczytajBtn.setEnabled(enabled); }
    public void setAddButtonEnabled(boolean enabled) { dodajBtn.setEnabled(enabled); }
    public void setProgressVisible(boolean visible) { progressBar.setVisible(visible); }

    public void addWczytajListener(ActionListener l) { wczytajBtn.addActionListener(l); }
    public void addDodajListener(ActionListener l) { dodajBtn.addActionListener(l); }
    public void addUsunListener(ActionListener l) { usunBtn.addActionListener(l); }

    public JTable getTabela() {
        return tabela;
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Błąd", JOptionPane.ERROR_MESSAGE);
    }
}
