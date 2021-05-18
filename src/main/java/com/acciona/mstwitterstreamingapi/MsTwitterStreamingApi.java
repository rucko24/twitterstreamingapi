package com.acciona.mstwitterstreamingapi;

import com.acciona.mstwitterstreamingapi.service.TwitterIngestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class MsTwitterStreamingApi {

	@Autowired
	TwitterIngestService twitterIngestService;

	public static void main(String[] args) {
		SpringApplication.run(MsTwitterStreamingApi.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onStart(){
		twitterIngestService.stream();
	}

}
