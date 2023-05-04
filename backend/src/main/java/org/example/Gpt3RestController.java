package org.example;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class Gpt3RestController {
    private final Gpt3Client gpt3Client;

    public Gpt3RestController(Gpt3Client gpt3Client) {
        this.gpt3Client = gpt3Client;
    }

    @PostMapping("/search")
    public String search(@RequestBody String message) throws IOException {
        return gpt3Client.generateResponse(message);
    }
}