package com.captech.retrorx;

import java.util.ArrayList;

/**
 * Created by cteegarden on 1/25/16.
 */
public class FriendResponse {

    public FriendLocations friendLocations;

    public FriendResponse(){}

    public class FriendLocations {
        public Data data;
        public class Data{
            public ArrayList<Friend> friend = new ArrayList<>();
            public class Friend{
                public String friendName;
                public String friendType;
                public String lat;
                public String lon;
            }
        }
    }
}
