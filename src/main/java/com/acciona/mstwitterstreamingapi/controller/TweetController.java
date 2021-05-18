package com.acciona.mstwitterstreamingapi.controller;

import com.acciona.mstwitterstreamingapi.model.Tweet;
import com.acciona.mstwitterstreamingapi.storage.TweetStorage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 *
 */
@RestController
public class TweetController {

    @Autowired
    private TweetStorage tweetStorage;

    @ApiOperation(value = "Get list of tweets in memory, it's possible to set a limit and offset", response = Iterable.class)
    @GetMapping("/tweets")
    public List<Tweet> findAll(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset){
        var limitOp = Optional.ofNullable(limit).orElse(100);
        var offsetOp = Optional.ofNullable(offset).orElse(0);

        return tweetStorage.findAll(limitOp,offsetOp);
    }

    @ApiOperation(value = "Validate a tweet user, using the current id", response = String.class)
    @PutMapping("/tweets/{id}")
    public String validate(@PathVariable String id){
        try{
            tweetStorage.markValidate(UUID.fromString(id));
            return "validated!";
        }catch (Exception e){
            return "Wrong UI format!";
        }
    }

    @ApiOperation(value = "Get list of validated tweets in memory", response = Iterable.class)
    @GetMapping("/tweets/valid")
    public List<Tweet> validTweets(){
        return tweetStorage.findValidated();
    }

    @ApiOperation(value = "Get the 10 most popular hashtags default 10, you can set a higher value", response = Iterable.class)
    @GetMapping("/hashtags/top")
    public List<String> topHashTags(@RequestParam(required = false) Integer top){
        var topOp = Optional.ofNullable(top).orElse(10);

        return tweetStorage.getTopHashTap(topOp);
    }


}
