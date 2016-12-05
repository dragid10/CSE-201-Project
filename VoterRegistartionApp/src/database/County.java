package database;

import java.util.ArrayList;
import java.util.Collection;

import static database.VoterData.voterDataHashmap;

public class County {
    // ================================================================================= Variables
    private String county;
    private int totalDemVotes;
    private int totalRepVotes;
    private int totalOthVotes;

    public static ArrayList<Integer> demVoteTotals = new ArrayList<>();
    public static ArrayList<Integer> repVoteTotals = new ArrayList<>();
    public static ArrayList<Integer> othVoteTotals = new ArrayList<>();

    // ================================================================================= Constructors
    public County(String county) {
        super();
        this.county = county;
        totalDemVotes = 0;
        totalRepVotes = 0;
        totalOthVotes = 0;

    }
    // ================================================================================= Getters / Setters

    private void setTotalDemVotes(String countyName) {
        int total = 0;
        Collection<VoterData> test = voterDataHashmap.get(countyName);
        Object[] testArray = test.toArray();
        for (Object aTestArray : testArray) {
            VoterData cur = (VoterData) aTestArray;
            totalDemVotes += cur.getDemVotes();
        }
    }

    private void setTotalRepVotes(String countyName) {
        int total = 0;
        Collection<VoterData> test = voterDataHashmap.get(countyName);
        Object[] testArray = test.toArray();
        for (Object aTestArray : testArray) {
            VoterData cur = (VoterData) aTestArray;
            totalRepVotes += cur.getRepVotes();
        }
    }

    private void setTotalOthVotes(String countyName) {
        int total = 0;
        Collection<VoterData> test = voterDataHashmap.get(countyName);
        Object[] testArray = test.toArray();
        for (Object aTestArray : testArray) {
            VoterData cur = (VoterData) aTestArray;
            totalOthVotes += cur.getOthVotes();
        }
    }

    private String getCounty() {
        return county;
    }

    private int getTotalDemVotes(String countyName) {
        return totalDemVotes;
    }

    private int getTotalRepVotes(String countyName) {
        return totalRepVotes;
    }

    private int getTotalOthVotes(String countyName) {
        return totalOthVotes;
    }

    // ================================================================================= Public Methods

    public int getDemVotingData() {
        return getTotalDemVotes(getCounty());
    }

    public int getRepVotingData() {
        return getTotalRepVotes(getCounty());
    }

    public int getOthVotingData() {
        return getTotalOthVotes(getCounty());
    }

    public void calcTotals() {
        setTotalDemVotes(getCounty());
        setTotalRepVotes(getCounty());
        setTotalOthVotes(getCounty());
    }

    // ================================================================================= Private Methods
    private void getTotals() {
        getTotalDemVotes(getCounty());
        getTotalRepVotes(getCounty());
        getTotalOthVotes(getCounty());
    }

}
