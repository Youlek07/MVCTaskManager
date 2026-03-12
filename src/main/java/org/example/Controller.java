package org.example;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Controller {

    private final TaskDB taskDB;
    private final View view;

    public Controller(TaskDB taskDB, View view) {
        this.taskDB = taskDB;
        this.view = view;

        view.addWczytajListener(e -> {
            view.setStatus("Ładowanie danych... Proszę czekać.", java.awt.Color.BLUE);
            view.setLoadButtonEnabled(false);
            view.setProgressVisible(true);

            new LoadTasksWorker(taskDB, view).execute();
        });

        view.addDodajListener(e -> {
            String title = view.getTitleText();
            String description = view.getDescriptionText();

            if (title.isBlank()) {
                view.showError("Tytuł nie może być pusty!");
                return;
            }

            view.setAddButtonEnabled(false);
            view.setLoadButtonEnabled(false);
            view.setProgressVisible(true);
            view.setStatus("Dodawanie zadania...", java.awt.Color.BLUE);

            new AddTaskWorker(taskDB, view, title, description).execute();
        });

        view.addUsunListener(e -> {
            Integer id = view.getSelectedId();

            if (id == null) {
                view.showError("Zaznacz zadanie do usunięcia!");
                return;
            }

            taskDB.usunZadanie(id);

            view.setStatus("Usunięto. Odświeżanie listy...", java.awt.Color.BLUE);
            view.setLoadButtonEnabled(false);
            view.setProgressVisible(true);
            new LoadTasksWorker(taskDB, view).execute();
        });

        view.getTabela().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTabela().getSelectedRow();
                int col = view.getTabela().getSelectedColumn();

                if (row != -1 && col == 3) {
                    int id = (Integer) view.getTabela().getValueAt(row, 0);
                    boolean nowyStatus = (Boolean) view.getTabela().getValueAt(row, 3);
                    taskDB.ustawWykonane(id, nowyStatus);
                }
            }
        });
    }
}
