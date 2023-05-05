package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class Gpt3Client {
    public static final String SYSTEM_DATA = "You are a detective. " +
            "Our TD (Sarah Johnson) was kidnapped the list of our first suspects are in next CSV table that is wrapped between <> delimiters. " +
            "The csv columns are: \n" +
            "Name,Hobby,Workplace,Working Hours,Car,Clothes,Home Address\n " +
            " \n <" +
            "Sarah Johnson,Painting,Google,Mountain View,9am-5pm,Tesla Model S,Business casual,123 Main St, San Francisco, CA 94105\n" +
            "David Lee,Playing basketball,Apple,Cupertino,10am-6pm,BMW X3,Casual,456 Park Ave, Los Angeles, CA 90001\n" +
            "Maria Rodriguez,Yoga,Amazon,Seattle,8am-4pm,Toyota Prius,Athletic wear,789 Broad St, Seattle, WA 98109\n" +
            "John Smith,Photography,Facebook,Menlo Park,11am-7pm,Audi A4,Hipster fashion,456 Elm St, Palo Alto, CA 94301\n" +
            "Emily Chen,Cooking,Microsoft,Redmond,9am-5pm,Honda Civic,Business formal,101 First St, Bellevue, WA 98004\n" +
            "Michael Brown,Playing guitar,IBM,New York City,8am-4pm,Ford Mustang,Rocker style,555 Fifth Ave, New York, NY 10017\n" +
            "Samantha Kim,Hiking,Salesforce,San Francisco,10am-6pm,Subaru Outback,Outdoor gear,777 Market St, San Francisco, CA 94103\n" +
            "James Nguyen,Gaming,Ubisoft,Montreal,11am-7pm,Nissan Altima,Geeky t-shirts,1234 Rue Sainte-Catherine O, Montreal, QC H3G 1P1, Canada\n" +
            "Elizabeth Taylor,Reading,Penguin Random House,London,9am-5pm,None,Business casual,123 Main St, New York, NY 10013\n" +
            "Robert Kim,Surfing,Dropbox,San Francisco,8am-4pm,Volkswagen Beetle,Beachy style,321 Ocean Blvd, Pacifica, CA 94044" +
            "Julia Lee,Horseback riding,Goldman Sachs,New York City,9am-5pm,Audi Q5,Business formal,10 Hudson Yards, New York, NY 10001\n" +
            "Maxwell Garcia,Playing chess,Amazon,Seattle,10am-6pm,Toyota Camry,Business casual,1234 6th Ave, Seattle, WA 98101\n" +
            "Sophie Nguyen,Swimming,Google,San Francisco,9am-5pm,Tesla Model 3,Sportswear,1234 Howard St, San Francisco, CA 94103\n" +
            "Andrew Patel,Playing soccer,Microsoft,Redmond,8am-4pm,Honda Accord,Casual,4567 148th Ave NE, Redmond, WA 98052\n" +
            "Ethan Chen,Playing piano,Facebook,Menlo Park,11am-7pm,Tesla Model X,Business casual,1 Hacker Way, Menlo Park, CA 94025\n" +
            "Isabella Martinez,Traveling,Expedia,Bellevue,9am-5pm,Subaru Crosstrek,Business casual,333 108th Ave NE, Bellevue, WA 98004\n" +
            "William Lee,Reading books,Apple,Cupertino,10am-6pm,BMW 3 Series,Business formal,1 Infinite Loop, Cupertino, CA 95014\n" +
            "Olivia Johnson,Playing tennis,IBM,New York City,8am-4pm,Jeep Wrangler,Sportswear,590 Madison Ave, New York, NY 10022\n" +
            "Henry Kim,Cycling,Oracle,Redwood City,9am-5pm,Ford F-150,Casual,500 Oracle Pkwy, Redwood City, CA 94065\n" +
            "Ava Davis,Hiking,REI,Seattle,10am-6pm,Jeep Grand Cherokee,Outdoor gear,222 Yale Ave N, Seattle, WA 98109" +
            ">\n" +
            "(Please create nice summary, dont respond with tables.)\n" +
            "A List of events where our person was seen last time: \n" +
            "Person's Name,Event Type,Date,Time,Location,Description\n" +
            "Sarah Johnson,Charity Gala,25.04.2023,7pm-10pm,San Francisco Museum of Modern Art,Attended with husband, seen leaving with him at 10pm.\n" +
            "David Lee,Bar Crawl,26.04.2023,9pm-1am,Various bars in downtown San Francisco,Drinking heavily, witnessed arguing with someone at one of the bars.\n" +
            "Emily Patel,Yoga Class,27.04.2023,10am-11am,Zen Wellness Studio,Participating in yoga class, seen leaving alone.\n" +
            "Michael Brown,Business Meeting,28.04.2023,2pm-3pm,Starbucks on Market Street,Meeting with a client, seen checking phone frequently.\n" +
            "Olivia Davis,Shopping,29.04.2023,4pm-6pm,Union Square,Seen browsing at several stores, carrying multiple shopping bags.\n" +
            "Ethan Ramirez,Baseball Game,30.04.2023,1pm-4pm,Oracle Park,Attended with friends, seen drinking beer and cheering loudly.\n" +
            "Samantha Nguyen,Movie Theater,01.05.2023,8pm-10pm,AMC Metreon,Seen watching a romantic comedy alone.\n" +
            "Joshua Kim,Concert,02.05.2023,7pm-10pm,The Fillmore,Attended with girlfriend, seen taking photos and singing along to songs.\n" +
            "Isabella Rodriguez,Gym,03.05.2023,6pm-7pm,24 Hour Fitness,Working out alone, seen using weight machines and jogging on treadmill.\n" +
            "Daniel Martinez,Barbecue,04.05.2023,5pm-8pm,Dolores Park,Attended with family, seen grilling food and playing frisbee.\n" +
            "Sophia Lee,Art Exhibit,05.05.2023,11am-1pm,de Young Museum,Viewing art exhibit, seen taking notes and sketching in a notebook.\n" +
            "Matthew Davis,Bookstore,06.05.2023,2pm-4pm,Green Apple Books,Browsing shelves, seen carrying a copy of a mystery novel.\n" +
            "Ava Garcia,Volunteer Event,07.05.2023,9am-12pm,Glide Memorial Church,Participating in a community service event, seen distributing food to homeless individuals.\n" +
            "Noah Hernandez,Beach Day,08.05.2023,12pm-4pm,Stinson Beach,Seen sunbathing and swimming with friends.\n" +
            "Liam Wilson,Office,09.05.2023,9am-5pm,Google Headquarters,Working in the office, seen attending meetings and using computer.\n" +
            "Emma Thompson,Park,10.05.2023,3pm-5pm,Golden Gate Park,Seen jogging alone with earphones on.\n" +
            "Nathan Chen,Restaurant,11.05.2023,7pm-9pm,The Cheesecake Factory,Having dinner with family, seen chatting and laughing.\n" +
            "Lily Park,Music Festival,12.05.2023,2pm-11pm,Outside Lands Festival,Attended with friends, seen dancing and singing along to music.\n" +
            "Oliver Lee,Art Gallery,13.05.2023,6pm-8pm,Minnesota Street Project,Viewing art exhibit, seen discussing with gallery owner.\n" +
            "Ella Davis,Sports Game,14.05.2023,4pm-7pm,Chase Center,Attended basketball game with friends, seen cheering and high.\n";
    public static final int API_TIMEOUT = 30;


    private final OkHttpClient client;
    private final String apiKey;
    private Map<String, List<ChatMessage>> userInterviewContexts = new HashMap<>();

    public Gpt3Client(@Value("${api.key}") String apiKey) {
        client = new OkHttpClient.Builder()
                .connectTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(API_TIMEOUT, TimeUnit.SECONDS)
                .build();
        this.apiKey = apiKey != null ? apiKey : "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";
    }

    public String generateResponse(String prompt) throws IOException {

        List<ChatMessage> messages = List.of(
                new ChatMessage("system", SYSTEM_DATA
                        + "\n Interviews from witness and suspects: \n"
                        + serializeInterviewMessages()),
                new ChatMessage("user", prompt + "\n" +
                        "Format the response in an JSON array where each item has a property 'name' which contains the person name " +
                        "and a property 'description' which contains the a summary answer related to this person. \n" +
                        "Return only the json without other explanations."));

        return callChatBot(messages);
    }

    private String callChatBot(List<ChatMessage> messages) throws IOException {
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
            return jsonObject.get("choices").getAsJsonArray().get(0).getAsJsonObject()
                    .get("message").getAsJsonObject().get("content").getAsString();
        }

        throw new RuntimeException("Error on getting response from AI" );
    }

    public String interview(UserMessage userMessage)  throws IOException {
        List<ChatMessage> messages;
        if (userInterviewContexts.containsKey(userMessage.getUserName())) {
            messages = userInterviewContexts.get(userMessage.getUserName());
            messages.add(new ChatMessage("user", userMessage.getMessage()));
        } else {
            messages = new ArrayList<>();
            messages.add(new ChatMessage("system", SYSTEM_DATA
                    + "\n Do an interview with the user and ask it creative question to check if he knows something about TD kidnapping" +
                    " and check if it know some suspects or is himself suspicious (use his previous responses to get details about suspicious information).\n" +
                    "  If after 7 questions nothing is interesting he know, tell him 'I finished!' ."));
            messages.add(new ChatMessage("assistant", "What is your name ?"));
            messages.add(new ChatMessage("user", userMessage.getUserName()));

            userInterviewContexts.putIfAbsent(userMessage.getUserName(), messages);
        }

        String assistantMessage = callChatBot(messages);
        if (assistantMessage != null) {
            messages.add(new ChatMessage("assistant", assistantMessage));
            //TODO
//            if (assistantMessage.contains("Thank you!")) {
//                saveChatToFile(userMessage.getUserName(), messages);
//            }
        }
         return assistantMessage;
    }

    private static void saveChatToFile(String userName, List<ChatMessage> messages) throws IOException {
        Path path = Paths.get(userName + ".txt"); // the path of the file to create
        // write the text to the file
        Files.write(path, serializePersonChatMessages(userName, messages).getBytes());
    }

    private String serializeInterviewMessages () {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<ChatMessage>> entry : userInterviewContexts.entrySet()) {
            String personName = entry.getKey();
            List<ChatMessage> chatMessages = entry.getValue();
            sb.append(serializePersonChatMessages(personName, chatMessages));
        }

        return sb.append("\n\n\n").toString();
    }

    private static String serializePersonChatMessages(String personName, List<ChatMessage> chatMessages) {
        StringBuilder sb = new StringBuilder();
        sb.append("Interview of ").append(personName).append(":\n");
        chatMessages.forEach(chatMessage -> {
            if (!chatMessage.getRole().equals("system")) {
                sb.append("- ").append(chatMessage.getRole()).append(": ").append(chatMessage.getContent()).append("\n");
            }
        });
        return sb.toString();
    }
}