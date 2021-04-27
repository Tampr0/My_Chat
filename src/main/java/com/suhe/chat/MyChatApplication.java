package com.suhe.chat;

import com.suhe.chat.backend.ChatMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

@SpringBootApplication
public class MyChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyChatApplication.class, args);
    }

    @Bean
    UnicastProcessor<ChatMessage> publisher(){
        return UnicastProcessor.create();
    }
    @Bean
    Flux<ChatMessage> messages(UnicastProcessor<ChatMessage> publisher) {
        return publisher.replay(30).autoConnect();
    }
}
