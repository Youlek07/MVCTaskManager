package org.example;

import javax.swing.*;

public class AddTaskWorker extends SwingWorker<Boolean, Void> {

    private final TaskDB taskDB;
    private final View view;
    private final String title;
    private final String description;

    public AddTaskWorker(TaskDB taskDB, View view, String title, String description) {
        this.taskDB = taskDB;
        this.view = view;
        this.title = title;
        this.description = description;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        return taskDB.dodajZadanie(title, description);
    }

    @Override
    protected void done() {
        try {
            boolean success = get();

            if (success) {
                view.setStatus("Zadanie dodane. Odświeżanie listy...", java.awt.Color.BLUE);
                view.clearInputFields();

                view.setLoadButtonEnabled(false);
                view.setProgressVisible(true);
                new LoadTasksWorker(taskDB, view).execute();
            } else {
                view.setStatus("Błąd: nie udało się dodać zadania.", java.awt.Color.RED);
                view.setAddButtonEnabled(true);
                view.setProgressVisible(false);
            }

        } catch (Exception ex) {
            view.setStatus("Błąd podczas dodawania: " + ex.getMessage(), java.awt.Color.RED);
            view.setAddButtonEnabled(true);
            view.setProgressVisible(false);
            ex.printStackTrace();
        }
    }
}
