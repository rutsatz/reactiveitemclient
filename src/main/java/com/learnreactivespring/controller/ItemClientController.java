package com.learnreactivespring.controller;

import com.learnreactivespring.domain.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ItemClientController {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping("/client/retrieve")
    public Flux<Item> getAllItemsUsingRetrieve() {

        return webClient.get().uri("/v1/items")
                /* Retrieve vai nos dar o response body direto. */
                .retrieve()
                .bodyToFlux(Item.class)
                .log("Items in Client Project retrieve : ");
    }

    @GetMapping("/client/exchange")
    public Flux<Item> getAllItemsUsingExchange() {

        return webClient.get().uri("/v1/items")
                /* Com o exchange, é nossa responsabilidade extrair o response body. */
                .exchange()
                .flatMapMany(clientResponse -> clientResponse.bodyToFlux(Item.class))
                .log("Items in Client Project exchange : ");
    }

    @GetMapping("/client/retrieve/singleItem")
    public Mono<Item> getOneItemsUsingRetrieve() {

        String id = "ABC";

        return webClient.get().uri("/v1/items/{id}", id)
                /* Retrieve vai nos dar o response body direto. */
                .retrieve()
                .bodyToMono(Item.class)
                .log("Items in Client Project retrieve single Item : ");
    }

    @GetMapping("/client/exchange/singleItem")
    public Mono<Item> getOneItemsUsingExchange() {

        String id = "ABC";

        return webClient.get().uri("/v1/items/{id}", id)
                /* Retrieve vai nos dar o response body direto. */
                .exchange()
                .flatMap(clientResponse -> clientResponse.bodyToMono(Item.class))
                .log("Items in Client Project exchange single Item : ");
    }

}
