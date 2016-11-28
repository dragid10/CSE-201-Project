package VotingData;


public class VoterData {
    private String precinct;
    private int demVotes;
    private int repVotes;
    private int othVotes;


    public VoterData(int demVotes, int repVotes, int othVotes) {
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
