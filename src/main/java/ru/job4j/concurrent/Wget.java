package ru.job4j.concurrent;

/**
 * @author Shegai Evgenii
 * @version 1.0
 * @since 17.05.2021
 * скачивание файла с ограничением по скорости
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Wget.class.getName());
    private final String url;
    private final String name;
    private int speed;
    private long countTime;
    private int count;
    private FileOutputStream fos;

    public Wget(String url, String name, int speed) {
        this.url = url;
        this.name = name;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(this.url).openStream())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int countBytes = 0;
            long start = System.currentTimeMillis();
            if (count == 1) {
               this.fos = new FileOutputStream(this.name);
            }
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
              if (countTime != 0) {
                  countBytes += bytesRead;
                  if (countBytes <= 1024) {
                      recFile(dataBuffer, new int[] {0, bytesRead}, fos);
                  } else {
                      if (countTime < this.speed) {
                          this.speed = Math.toIntExact(countTime);
                      }
                      Thread.sleep(this.speed);
                      recFile(dataBuffer,  new int[] {0, bytesRead}, fos);
                      countBytes = 0;
                  }
              }
                countTime = System.currentTimeMillis() - start;
              count++;
              if (count == 1) {
                  break;
              }
            }
        } catch (Exception e) {
           LOG.error(e.getMessage(), e);
        }
    }

    private void recFile(byte[] buffer, int[] diapozon, FileOutputStream fos) throws IOException {
     fos.write(buffer, diapozon[0], diapozon[1]);
    }

    public static void main(String[] args)  {
        if (args.length < 3) {
            throw new IllegalArgumentException("Enter valid data");
        }
    Wget wget = new Wget(args[0], args[1], Integer.parseInt(args[2]));
    wget.run();
    Thread thread = new Thread(wget);
      thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
        System.out.println(thread.getState());
        System.out.println(Thread.currentThread().getState());
    }
}
