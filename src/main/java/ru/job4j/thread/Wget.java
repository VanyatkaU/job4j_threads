package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private static final int SIZE_LOAD = 1024;
    private static final int TIME_WAIT = 1000;
    private final String file;

    public Wget(String url, int speed, String file) {
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] dataBuffer = new byte[SIZE_LOAD];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, SIZE_LOAD)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                if (bytesRead >= speed) {
                    Thread.sleep(TIME_WAIT);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void isValid(String[] parameter) {
        if (parameter.length != 3) {
            throw new IllegalArgumentException("Have to be 3 parameters: url, speed, path to file");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        isValid(args);
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String file = args[2];
        Thread wget = new Thread(new Wget(url, speed, file));
        wget.start();
        wget.join();
    }
}