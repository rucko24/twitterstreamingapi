package com.acciona.mstwitterstreamingapi.service;

import com.acciona.mstwitterstreamingapi.model.Tweet;
import com.acciona.mstwitterstreamingapi.storage.TweetStorage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
public class TwitterIngestService {
    private TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
    private List<String> languages = Arrays.asList("es", "en", "fr","it");
    private static final String[] TRACK_TEXT = {"covid-19","covid","vaccin","vaccino","vacuna"};
    private static final String ES = "es";
    private static final String FR = "fr";
    private static final String IT = "it";

    private TweetStorage storage;

    @Autowired
    public TwitterIngestService(TweetStorage storage) {
        this.storage = storage;
    }

    public void stream() {
        StatusListener listener = new StatusAdapter() {
            @Override
            public void onStatus(Status status) {
                var followers = status.getUser().getFollowersCount();
                var location = status.getUser().getLocation();
                var lang = status.getLang();
                if (followers >= 1500 && languages.contains(lang)) {
                    if(Objects.nonNull(location)) {

                        var tweet = new Tweet(UUID.randomUUID(), status.getUser().getScreenName(), status.getText(),
                                new Tweet.GeoLocation(status.getGeoLocation().getLatitude(),
                                        status.getGeoLocation().getLongitude()),
                                false);

                        //save in memmory
                        storage.ingest(tweet);
                        Arrays.stream(status.getHashtagEntities()).forEach( ht -> {
                            storage.classifyHashTag(ht.getText());
                        });
                    }
                }
            }
        };
        log.info("starting streaming...");
        twitterStream.addListener(listener).sample();
        //this.createFilterQuery();
    }

    private void createFilterQuery() {
        //Filter query to init streaming
        final var tweetFilterQuery = new FilterQuery(); // See
        tweetFilterQuery.track(TRACK_TEXT); // OR on keywords
        tweetFilterQuery.language(ES, FR, IT);
        //init streaming
        twitterStream.filter(tweetFilterQuery);
    }
}
