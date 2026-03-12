package org.example;

import javax.swing.*;
import java.util.List;

public class LoadTasksWorker extends SwingWorker<List<Task>, Void> {

    private final TaskDB taskDB;
    private final View view;

    public LoadTasksWorker(TaskDB taskDB, View view) {
        this.taskDB = taskDB;
        this.view = view;
    }

    @Override
    protected List<Task> doInBackground() throws Exception {
        Thread.sleep(4000);
        return taskDB.pobierzWszystkie();
    }

    @Override
    protected void done() {
        try {
            List<Task> lista = get();

            view.setLista(lista);
            view.setStatus("Gotowe. Wczytano " + lista.size() + " zadań.", java.awt.Color.GREEN.darker());

        } catch (Exception ex) {
            view.setStatus("Błąd podczas wczytywania: " + ex.getMessage(), java.awt.Color.RED);
            ex.printStackTrace();
        } finally {
            view.setLoadButtonEnabled(true);
            view.setProgressVisible(false);
        }
    }
}
