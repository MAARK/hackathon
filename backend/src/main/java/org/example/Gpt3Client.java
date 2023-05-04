package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class Gpt3Client {
    private final OkHttpClient client;
    private final String apiKey;

    public Gpt3Client() {
        client = new OkHttpClient();
        apiKey = "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";
    }

    public String generateResponse(String prompt) throws IOException {

        List<ChatMessage> messages = List.of(
                new ChatMessage("system", "system data"),
                new ChatMessage("user", prompt));

        MediaType mediaType = MediaType.parse("application/json");
        Map<String, Object> bodyMap = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messages,
                "temperature", 0.5);
        Gson gson = new GsonBuilder().create();
        String bodyJson = gson.toJson(bodyMap);
        RequestBody body = RequestBody.create(mediaType, bodyJson);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();
        Response response = client.newCall(request).execute();


        return response.body().string();
    }
}