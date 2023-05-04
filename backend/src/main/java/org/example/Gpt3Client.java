package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
public class Gpt3Client {
    public static final String SYSTEM_DATA = "You are a detective chatbot. " +
            "Our TD was kidnapped and we have a list of suspects in next CSV table is between <>. " +
            " \n <" +
            "First name,Last name,Office location,Toilet paper orientation,Is a hotdog a sandwich,Socks with sandals,Last seen in the office,Missed meetings,Check in time,Check out time\n" +
            "Jocelyn,Hartman,\"Boston, MA\",over the roll,yes,Fashion Faux Pas,yesterday,none,7:30,17:00\n" +
            "Landon,Sosa,\"Charlottesville, VA\",under the roll,no,Fashion Faux Pas,yesterday,10% missed,8:00,16:30\n" +
            "Jacqueline,Rowe,\"Columbus, OH\",over the roll,yes,Completely reasonable,today,75% missed,6:00,14:00\n" +
            "Bradford,Blackwell,\"Durham, NC\",under the roll,no,Completely reasonable,today,none,9:00,18:00\n" +
            "Leona,Obrien,\"New York, NY\",over the roll,no,Fashion Faux Pas,today,10% missed,8:30,17:30\n" +
            "Lilah,Holt,\"Porto Alegre, BR\",under the roll,no,Completely reasonable,today,75% missed,6:30,15:30\n" +
            "Koen,Da Silva,\"Vancouver, BC\",over the roll,no,Fashion Faux Pas,yesterday,none,8:15,16:45\n" +
            "Harmony,Carr,\"Boston, MA\",under the roll,yes,Completely reasonable,today,none,7:45,16:15\n" +
            "Winston,Shaw,\"Charlottesville, VA\",over the roll,no,Completely reasonable,yesterday,75% missed,6:45,15:15\n" +
            "Nola,Cook,\"Columbus, OH\",under the roll,yes,Fashion Faux Pas,yesterday,10% missed,9:30,18:30\n" +
            "Bryce,Hall,\"Durham, NC\",over the roll,no,Completely reasonable,today,none,8:30,17:30\n" +
            "Wesley,Lawrence,\"New York, NY\",under the roll,no,Fashion Faux Pas,yesterday,75% missed,6:30,15:30\n" +
            "Gia,Hall,\"Porto Alegre, BR\",over the roll,no,Completely reasonable,today,10% missed,8:00,16:00\n" +
            "Elyse,Callahan,\"Vancouver, BC\",under the roll,no,Fashion Faux Pas,today,none,9:00,18:00\n" +
            "Harvey,Reeves,\"Boston, MA\",over the roll,no,Completely reasonable,yesterday,75% missed,6:15,14:45\n" +
            "Sydney,Kidd,\"Charlottesville, VA\",under the roll,no,Completely reasonable,today,none,8:15,16:45\n" +
            "Graeme,Freeman,\"Columbus, OH\",over the roll,yes,Fashion Faux Pas,today,10% missed,7:00,16:00\n" +
            "Audrina,Black,\"Durham, NC\",under the roll,no,Completely reasonable,today,none,8:30,17:30\n" +
            "Beau,Mayo,\"New York, NY\",over the roll,no,Fashion Faux Pas,yesterday,75% missed,6:30,15:30\n" +
            "Alyssa,Murphy,\"Porto Alegre, BR\",under the roll,no,Completely reasonable,today,10% missed,9:00,18:00\n" +
            "John,Smith,\"Boston, MA\",over the roll,no,Fashion Faux Pas,today,none,7:30,17:00\n" +
            "Mary,Jones,\"Charlottesville, VA\",under the roll,yes,Completely reasonable,yesterday,10% missed,8:00,16:30\n" +
            "David,Lee,\"Columbus, OH\",over the roll,no,Completely reasonable,today,none,6:00,14:00\n" +
            "Emily,Nguyen,\"Durham, NC\",under the roll,yes,Fashion Faux Pas,today,75% missed,9:00,18:00\n" +
            "Michael,Kim,\"New York, NY\",over the roll,no,Fashion Faux Pas,yesterday,none,8:30,17:30\n" +
            "Sarah,Li,\"Porto Alegre, BR\",under the roll,no,Completely reasonable,yesterday,10% missed,6:30,15:30\n" +
            "Kevin,Chen,\"Vancouver, BC\",over the roll,no,Completely reasonable,today,none,8:15,16:45\n" +
            "Olivia,Lin,\"Boston, MA\",under the roll,yes,Fashion Faux Pas,today,75% missed,7:45,16:15\n" +
            "Daniel,Kim,\"Charlottesville, VA\",over the roll,no,Completely reasonable,yesterday,none,6:45,15:15\n" +
            "Jessica,Tran,\"Columbus, OH\",under the roll,no,Fashion Faux Pas,today,10% missed,9:30,18:30\n" +
            "Andrew,Nguyen,\"Durham, NC\",over the roll,yes,Completely reasonable,today,none,8:30,17:30\n" +
            "Catherine,Kim,\"New York, NY\",under the roll,no,Fashion Faux Pas,yesterday,75% missed,6:30,15:30\n" +
            "Thomas,Lee,\"Porto Alegre, BR\",over the roll,yes,Completely reasonable,yesterday,none,8:00,16:00\n" +
            "Natalie,Lin,\"Vancouver, BC\",under the roll,no,Fashion Faux Pas,today,10% missed,9:00,18:00\n" +
            "Matthew,Kim,\"Boston, MA\",over the roll,no,Completely reasonable,today,none,6:15,14:45\n" +
            "Sophia,Wu,\"Charlottesville, VA\",under the roll,yes,Fashion Faux Pas,yesterday,75% missed,8:15,16:45\n" +
            "Eric,Liu,\"Columbus, OH\",over the roll,yes,Completely reasonable,today,none,7:00,16:00\n" +
            "Grace,Kim,\"Durham, NC\",under the roll,no,Fashion Faux Pas,today,10% missed,8:30,17:30\n" +
            "Ryan,Chen,\"New York, NY\",over the roll,no,Fashion Faux Pas,yesterday,none,6:30,15:30\n" +
            "Isabella,Kim,\"Porto Alegre, BR\",under the roll,yes,Completely reasonable,today,75% missed,9:00,18:00\n" +
            "Nicholas,Lin,\"Vancouver, BC\",over the roll,no,Fashion Faux Pas,today,10% missed,7:30,17:00\n" +
            "Lily,Kim,\"Boston, MA\",under the roll,yes,Completely reasonable,today,none,8:00,16:30\n" +
            "Joshua,Tran,\"Charlottesville, VA\",over the roll,no,Fashion Faux Pas,yesterday,75% missed,6:00,14:00\n" +
            "Ella,Nguyen,\"Columbus, OH\",under the roll,no,Completely reasonable,today,none,9:00,18:00\n" +
            "William,Kim,\"Durham, NC\",over the roll,yes,Fashion Faux Pas,yesterday,10% missed,8:30,17:30\n" +
            "Ava,Lee,\"New York, NY\",under the roll,no,Completely reasonable,today,none,6:30,15:30\n" +
            "Justin,Kim,\"Porto Alegre, BR\",over the roll,no,Fashion Faux Pas,today,75% missed,8:15,16:45\n" +
            "Samantha,Lin,\"Vancouver, BC\",under the roll,yes,Completely reasonable,yesterday,none,7:45,16:15\n" +
            "Brandon,Kim,\"Boston, MA\",over the roll,no,Fashion Faux Pas,today,10% missed,6:45,15:15" +
            "" +
            ">";


    private final OkHttpClient client;
    private final String apiKey;

    public Gpt3Client() {
        client = new OkHttpClient();
        apiKey = "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";
    }

    public String generateResponse(String prompt) throws IOException {

        List<ChatMessage> messages = List.of(
                new ChatMessage("system", SYSTEM_DATA),
                new ChatMessage("user", prompt + "\n" +
                        "Format the response in an JSON array where each item has a property 'name' which contains the person name " +
                        "and a property 'description' which contains the a summary answer related to this person. \n" +
                        "Return only the json without other explanations."));


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

        if (response.isSuccessful()) {
            JsonObject jsonObject = (new Gson()).fromJson(response.body().string(), JsonObject.class);

            // Extract content message from JSON object
            String contentMessage = jsonObject.get("choices").getAsJsonArray().get(0).getAsJsonObject()
                    .get("message").getAsJsonObject().get("content").getAsString();

            return contentMessage;
        }

        throw new RuntimeException("the GPT response was not Successful");
    }
}