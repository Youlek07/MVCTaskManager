**1. Jaka jest podstawowa funkcja wątku EDT w aplikacjach Swing?**

Podstawową funkcją wątku EDT (Event Dispatch Thread) jest bezpieczna obsługa, aktualizacja oraz malowanie komponentów interfejsu GUI.

**2. Dlaczego próba bezpośredniego wykonania zapytania SQL w metodzie actionPerformed przycisku jest zła?**

Jest to zła praktyka ze względu na to, że zapytanie do bazy danych trwa kilka sekund, co spowoduje, że aplikacja najzwyczajniej w świecie przestanie reagować na interakcje użytkownika co sprawi wrażenie, że aplikacja się zawiesiła.

**3. Która metoda klasy SwingWorker jest bezpieczna do aktualizacji komponentów GUI (np. JLabel) po zakończeniu długiej operacji?**

Metodą klasy SwingWorker, która jest bezpieczna do aktualizacji komponentów GUI po zakończeniu długiej operacji jest metoda done().

**4. W którym wątku (EDT czy roboczym) jest wykonywana metoda doInBackground()?**

Metoda doInBackground() jest wykonywana w wątku roboczym.
