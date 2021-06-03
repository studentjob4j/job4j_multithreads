package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 03.06.2021
 * this class shows use ExecutorService
 */

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService service;

    public EmailNotification() {
        this.service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void emailTo(User user) {
       service.execute(() -> {
            String subject = String.format("Notification  %s to email %s", user.name, user.email);
            String body = String.format("Add new event to %s", user.name);
            send(subject, body, user.email);
       });
    }

    public void close() {
          service.shutdown();
    }

    public void send(String subject, String body, String email) {

    }

     public class User {
        private String name;
        private String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)  {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            User user = (User) o;
            return Objects.equals(name, user.name) && Objects.equals(email, user.email);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, email);
        }

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + ", email='" + email + '\'' + '}';
        }
    }

    public static void main(String[] args) {
        EmailNotification notification = new EmailNotification();
        EmailNotification.User user =  notification.new User("test", "google.com");
        notification.emailTo(user);
        notification.close();
    }
}
