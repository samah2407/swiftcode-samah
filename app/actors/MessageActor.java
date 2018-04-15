package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

public class MessageActor extends UntypedActor {
    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }

    private final ActorRef out;

    public MessageActor(ActorRef out) {
        this.out = out;
    }

    private FeedService feedService = new FeedService();
    private NewsAgentService newsAgentService = new NewsAgentService();

    @Override
    public void onReceive(Object message) throws Throwable {
        ObjectMapper objectMapper = new ObjectMapper();
        Message messageObj = new Message();
        if (message instanceof String) {

            messageObj.text = (String) message;
            messageObj.sender = Message.Sender.USER;
            out.tell(objectMapper.writeValueAsString(messageObj), self());
            String query = newsAgentService.getNewsAgentResponse("find" + message, UUID.randomUUID()).query;
            FeedResponse feedResponse = feedService.getFeedByQuery(query);
            messageObj.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + query;
            messageObj.feedResponse = feedResponse;
            messageObj.sender = Message.Sender.BOT;
            out.tell(objectMapper.writeValueAsString(messageObj), self());

        }


    }
}