package ru.job4j.concurrent.pool;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 03.06.2021
 * this class shows use ExecutorService
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime()
            .availableProcessors());

    public void emailTo(User user) {
       service.execute(() -> {
            String subject = String.format("Notification  %s to email %s", user.getName(),
                    user.getEmail());
            String body = String.format("Add new event to %s", user.getName());
            send(subject, body, user.getEmail());
       });
    }

    public void close() {
          service.shutdown();
          while (!service.isTerminated()) {
              try {
                  Thread.sleep(100);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
    }

    public void send(String subject, String body, String email) {

    }
}
