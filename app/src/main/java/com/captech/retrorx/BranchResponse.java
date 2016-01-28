package com.captech.retrorx;

import java.util.ArrayList;

/**
 * Created by cteegarden on 1/25/16.
 *
 * {
 "branchLocations":{
 "status":"SUCCESS",
 "data":{
 "branch":[
 {
 "branchName":"Test Branch One",
 "sobCode":"TB1",
 "stateCode":"VA",
 "branchType":"D"
 }
 ]
 }
 }
 }
 */
public class BranchResponse {
    public BranchLocations branchLocations;
    public class BranchLocations {
        public String status;
        public Data data;

        public class Data{
            public ArrayList<Branch> branch = new ArrayList<>();

            public class Branch{
                public String branchName;
                public String sobCode;
                public String stateCode;
                public String branchType;
            }
        }
    }
}
