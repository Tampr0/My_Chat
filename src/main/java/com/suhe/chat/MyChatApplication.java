package com.suhe.chat;

import com.suhe.chat.domain.ChatMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@SpringBootApplication
public class MyChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyChatApplication.class, args);
    }


    @Bean
    Sinks.Many<ChatMessage> publisher() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    Flux<ChatMessage> messages(Sinks.Many<ChatMessage> publisher) {
        return publisher.asFlux().replay(30).autoConnect();
    }
    //todo - let Mono<Boolean> emits a signIn signal and then Mono#concatWith(Publisher) can create a
    // Flux streaming chat messages.

    //todo use Flux.interval(Duration) that produces a Flux<Long> that is infinite
    // and emits regular ticks from a clock to implement a clock into a website.

    //todo consider connecting to the publisher when startButton clicked.

    //todo Got an RPC for non-existent node: 5
    // WARN occurs after refreshing the page and typing a nick name.
    // whatever cause this warn corresponds to startButton click.

}
