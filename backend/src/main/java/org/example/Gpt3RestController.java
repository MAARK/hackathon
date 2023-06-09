package org.example;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
public class Gpt3RestController {
    private final Gpt3Client gpt3Client;

    public Gpt3RestController(Gpt3Client gpt3Client) {
        this.gpt3Client = gpt3Client;
    }

    @PostMapping(value="/search", produces = "application/json")
    public String search(@RequestBody String message) throws IOException {
        return gpt3Client.generateResponse(message);
    }

    @PostMapping(value="/interview", produces = "application/json")
    public String interview(@RequestBody UserMessage userMessage) throws IOException {
        return gpt3Client.interview(userMessage);
    }
}
