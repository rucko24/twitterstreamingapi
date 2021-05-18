package com.acciona.mstwitterstreamingapi.storage;


import com.acciona.mstwitterstreamingapi.model.Tweet;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

@Log4j2
@Component
public class TweetStorageImpl implements TweetStorage {
    private final Queue<Tweet> queue = new ConcurrentLinkedQueue();
    private final Map<String, Long> hashTagClasification = new ConcurrentHashMap();
    private static final int LIMIT = 1000000;

    @Override
    public void ingest(Tweet tweet){
        if(queue.size() < LIMIT){
            queue.add(tweet);
        }else{
            queue.poll();
            queue.add(tweet);
        }

        log.info("user: "+tweet.getUser()+" text: " +tweet.getText());
        log.info(tweet);
    }

    @Override
    public List<Tweet> findAll(int first, int offset) {
        return queue.stream()
                .skip(offset)
                .limit(first)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tweet> findValidated() {
        return queue
                .stream()
                .filter(Tweet::isValid)
                .collect(Collectors.toList());
    }

    @Override
    public void markValidate(UUID id) {
        queue.forEach(tweet -> {
            if(tweet.getId().equals(id)){
                tweet.setValid(true);
            }
        });
    }

    @Override
    public void classifyHashTag(String hashTag) {
        Long count = hashTagClasification.getOrDefault(hashTag,0L);
        hashTagClasification.put(hashTag,count + 1);
    }

    @Override
    public List<String> getTopHashTap(int top) {
        return hashTagClasification.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey).limit(top).collect(Collectors.toList());
    }
}
