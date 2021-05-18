package com.acciona.mstwitterstreamingapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 *
 */
@Data
public class Tweet {

    @ApiModelProperty(notes = "Id of the tweet", name = "id", required = true, value = "Random String value generated with UUID.randomUUID()")
    private UUID id;

    @ApiModelProperty(notes = "Twitter user", name = "user", required = true, value = "Screen name of twitter user")
    private String user;

    @ApiModelProperty(notes = "Twitter text user", name = "text", required = true, value = "Returns the text of the status")
    private String text;

    @ApiModelProperty(notes = "Twitter GeoLocation user", name = "localization", required = true, value = "Returns the geolocalization of the current user")
    private GeoLocation localization;

    @ApiModelProperty(notes = "Used to validate a tweet", name = "valid", required = false, value = "")
    private boolean valid;

    public Tweet(UUID id, String user, String text, GeoLocation localization, boolean valid) {
        this.id = id;
        this.user = user;
        this.text = text;
        this.localization = localization;
        this.valid = valid;
    }

    @AllArgsConstructor
    @Data
    public static class GeoLocation {

        @ApiModelProperty(notes = "latitude of the current user", name = "valid", required = true, value = "")
        private double latitude;

        @ApiModelProperty(notes = "longitude of the current user", name = "valid", required = true, value = "")
        private double longitude;

    }
}
