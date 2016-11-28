package VotingData;


public class Data {
    private String countName;
    private int demVotes;
    private int repVotes;
    private int othVotes;


    public Data(String countName, int demVotes, int repVotes, int othVotes) {
        this.countName = "null";
        setDemVotes(0);
        setRepVotes(0);
        setOthVotes(0);
    }

    public String getCountName() {
        return countName;
    }

    public void setCountName(String countName) {
        this.countName = countName;
    }

    public int getDemVotes() {
        return demVotes;
    }

    public void setDemVotes(int demVotes) {
        this.demVotes = demVotes;
    }

    public int getRepVotes() {
        return repVotes;
    }

    public void setRepVotes(int repVotes) {
        this.repVotes = repVotes;
    }

    public int getOthVotes() {
        return othVotes;
    }

    public void setOthVotes(int othVotes) {
        this.othVotes = othVotes;
    }
}
