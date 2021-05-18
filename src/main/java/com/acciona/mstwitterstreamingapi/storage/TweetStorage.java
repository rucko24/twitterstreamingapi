package com.acciona.mstwitterstreamingapi.storage;

import com.acciona.mstwitterstreamingapi.model.Tweet;

import java.util.List;
import java.util.UUID;

public interface TweetStorage {
    void ingest(Tweet tweet);
    List<Tweet> findAll(int first, int offset);
    List<Tweet> findValidated();
    void markValidate(UUID id);
    void classifyHashTag(String hashTag);
    List<String> getTopHashTap(int top);
}
