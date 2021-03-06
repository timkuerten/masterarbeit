package client.Output;

public class TimeSaverManager {

    private final TimeSaver getProfileByUuidTimeSaver;
    protected final TimeSaver getProfileByThirdPartyIDTimeSaver;
    private final TimeSaver getProfileByRangeTimeSaver;
    private final TimeSaver getProfileByMultiRangeTimeSaver;
    private final TimeSaver insertProfileTimeSaver;
    private final TimeSaver updateProfileTimeSaver;
    private final TimeSaver getSchemaTimeSaver;
    private final TimeSaver addSchemaTimeSaver;

    public TimeSaverManager() {
        getProfileByUuidTimeSaver = new TimeSaver();
        getProfileByThirdPartyIDTimeSaver = new TimeSaver();
        getProfileByRangeTimeSaver = new TimeSaver();
        getProfileByMultiRangeTimeSaver = new TimeSaver();
        insertProfileTimeSaver = new TimeSaver();
        updateProfileTimeSaver = new TimeSaver();
        getSchemaTimeSaver = new TimeSaver();
        addSchemaTimeSaver = new TimeSaver();
    }

    public void getProfileByUuid(long estimatedTime) {
        getProfileByUuidTimeSaver.addTime(estimatedTime);
    }

    public void getProfileByThirdPartyID(long estimatedTime) {
        getProfileByThirdPartyIDTimeSaver.addTime(estimatedTime);
    }

    public void getProfileByRange(long estimatedTime) {
        getProfileByRangeTimeSaver.addTime(estimatedTime);
    }

    public void getProfileByMultiRange(long estimatedTime) {
        getProfileByMultiRangeTimeSaver.addTime(estimatedTime);
    }

    public void insertProfile(long estimatedTime) {
        insertProfileTimeSaver.addTime(estimatedTime);
    }

    public void updateProfile(long estimatedTime) {
        updateProfileTimeSaver.addTime(estimatedTime);
    }

    public void getSchema(long estimatedTime) {
        getSchemaTimeSaver.addTime(estimatedTime);
    }

    public void addSchema(long estimatedTime) {
        addSchemaTimeSaver.addTime(estimatedTime);
    }

    public String printOutTimeSavers() {
        String returnString;

        returnString = "getProfileByUuid:\n";
        returnString += "\t#:   " + getProfileByUuidTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + getProfileByUuidTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + getProfileByUuidTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + getProfileByUuidTimeSaver.getAverageAsString() + "\n";

        returnString += "getProfilesByThirdPartyID:\n";
        returnString += "\t#:   " + getProfileByThirdPartyIDTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + getProfileByThirdPartyIDTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + getProfileByThirdPartyIDTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + getProfileByThirdPartyIDTimeSaver.getAverageAsString() + "\n";

        returnString += "getProfilesByRange:\n";
        returnString += "\t#:   " + getProfileByRangeTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + getProfileByRangeTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + getProfileByRangeTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + getProfileByRangeTimeSaver.getAverageAsString() + "\n";

        returnString += "getProfilesByMultiRange:\n";
        returnString += "\t#:   " + getProfileByMultiRangeTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + getProfileByMultiRangeTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + getProfileByMultiRangeTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + getProfileByMultiRangeTimeSaver.getAverageAsString() + "\n";

        returnString += "insertProfile:\n";
        returnString += "\t#:   " + insertProfileTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + insertProfileTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + insertProfileTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + insertProfileTimeSaver.getAverageAsString() + "\n";

        returnString += "updateProfile:\n";
        returnString += "\t#:   " + updateProfileTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + updateProfileTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + updateProfileTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + updateProfileTimeSaver.getAverageAsString() + "\n";

        returnString += "getSchema:\n";
        returnString += "\t#:   " + getSchemaTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + getSchemaTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + getSchemaTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + getSchemaTimeSaver.getAverageAsString() + "\n";

        returnString += "addSchema:\n";
        returnString += "\t#:   " + addSchemaTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + addSchemaTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + addSchemaTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + addSchemaTimeSaver.getAverageAsString();

        return returnString;
    }

}
