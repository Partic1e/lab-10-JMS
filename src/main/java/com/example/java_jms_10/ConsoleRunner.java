package com.example.java_jms_10;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final RabbitTemplate rabbitTemplate;

    private final Receiver receiver;

    public ConsoleRunner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Write message:");
            String message = scanner.nextLine();
            rabbitTemplate.convertAndSend(App.fanoutExchangeName, "null", message);
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        }
    }
}
