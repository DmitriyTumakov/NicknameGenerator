package ru.netology;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter3 = new AtomicInteger(0);
    public static AtomicInteger counter4 = new AtomicInteger(0);
    public static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                for (int j = 0; j < texts[i].length(); j++) {
                    if (!(texts[i].charAt(j) == texts[i].charAt(((texts[i].length() - 1) - j)))) {
                        break;
                    }
                    increaseCounter(texts[i], j);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                for (int j = 0; j < texts[i].length(); j++) {
                    if (j < texts[i].length() - 1) {
                        if (!(texts[i].charAt(j) == texts[i].charAt(j + 1))) {
                            break;
                        }
                    }
                    increaseCounter(texts[i], j);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                for (int j = 0; j < texts[i].length(); j++) {
                    if (texts[i].contains("a") && j == 0) {
                        if (!(texts[i].charAt(j) == 'a')) {
                            break;
                        }
                    }
                    if (!(j == texts[i].length() - 1) && texts[i].charAt(j) == 'a' && !(texts[i].charAt(j + 1) == 'a' || texts[i].charAt(j + 1) == 'b')) {
                        break;
                    }
                    if (!(j == texts[i].length() - 1) && texts[i].charAt(j) == 'b' && !(texts[i].charAt(j + 1) == 'b' || texts[i].charAt(j + 1) == 'c')) {
                        break;
                    }
                    if (!(j == texts[i].length() - 1) && (texts[i].charAt(j) == 'c' && !(texts[i].charAt(j + 1) == 'c'))) {
                        break;
                    }
                    increaseCounter(texts[i], j);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.printf("Красивых слов с длиной 3: %s\n" , counter3);
        System.out.printf("Красивых слов с длиной 4: %s\n" , counter4);
        System.out.printf("Красивых слов с длиной 5: %s\n" , counter5);
    }

    private static void increaseCounter(String text, int j) {
        if (j == text.length() - 1) {
            if (text.length() == 3) {
                counter3.getAndIncrement();
            } else if (text.length() == 4) {
                counter4.getAndIncrement();
            } else if (text.length() == 5) {
                counter5.getAndIncrement();
            }
        }
    }


    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}