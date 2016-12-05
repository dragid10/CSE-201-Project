package database;// Import the MultiMap
// Docs here: https://google.github.io/guava/releases/19.0/api/docs/com/google/common/collect/Multimap.html

import com.google.common.collect.HashMultimap;

import java.util.logging.Logger;

public class VoterData {
    //=============================================================== Variables
    private static final Logger LOGGER = Logger.getLogger(VoterData.class.getName());

    private String county;


    private String precinct;
    private int demVotes;
    private int repVotes;
    private int othVotes;

    // HashMap that houses all of the CountyData
    public static HashMultimap<String, VoterData> voterDataHashmap = HashMultimap.create();

    //=============================================================== Constructors
    public VoterData(String precinct, int demVotes, int repVotes, int othVotes) {
        setPrecinct(precinct);
        setDemVotes(demVotes);
        setRepVotes(repVotes);
        setOthVotes(othVotes);
    }

    public VoterData(String county, String precinct, int demVotes, int repVotes, int othVotes) {
        setCounty(county);
        setPrecinct(precinct);
        setDemVotes(demVotes);
        setRepVotes(repVotes);
        setOthVotes(othVotes);
    }

    public VoterData() {
        setPrecinct("Type: Null");
        setDemVotes(0);
        setRepVotes(0);
        setOthVotes(0);
    }

    public VoterData clone() {
        return new VoterData(getCounty(), getPrecinct(), getDemVotes(), getRepVotes(), getOthVotes());
    }

    //=============================================================== Getters / Setters

    public String getCounty() {
        return county;
    }

    public String getPrecinct() {
        return precinct;
    }

    public int getDemVotes() {
        return demVotes;
    }

    public int getRepVotes() {
        return repVotes;
    }

    public int getOthVotes() {
        return othVotes;
    }

    private void setCounty(String county) {
        this.county = county;
    }

    private void setPrecinct(String precinct) {
        this.precinct = precinct;
    }

    private void setDemVotes(int demVotes) {
        this.demVotes = demVotes;
    }

    private void setRepVotes(int repVotes) {
        this.repVotes = repVotes;
    }

    private void setOthVotes(int othVotes) {
        this.othVotes = othVotes;
    }


}