package client;

public class TimeSaverManager {

    public final TimeSaver getProfileByUuidTimeSaver;
    public final TimeSaver getProfileByThirdPartyIDTimeSaver;
    public final TimeSaver getProfileByRangeTimeSaver;
    public final TimeSaver insertProfileTimeSaver;
    public final TimeSaver updateProfileTimeSaver;
    public final TimeSaver getSchemaTimeSaver;
    public final TimeSaver addSchemaTimeSaver;
    public final TimeSaver changeSchemaTimeSaver;

    public TimeSaverManager() {
        getProfileByUuidTimeSaver = new TimeSaver();
        getProfileByThirdPartyIDTimeSaver = new TimeSaver();
        getProfileByRangeTimeSaver = new TimeSaver();
        insertProfileTimeSaver = new TimeSaver();
        updateProfileTimeSaver = new TimeSaver();
        getSchemaTimeSaver = new TimeSaver();
        addSchemaTimeSaver = new TimeSaver();
        changeSchemaTimeSaver = new TimeSaver();
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

    public void changeSchema(long estimatedTime) {
        changeSchemaTimeSaver.addTime(estimatedTime);
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
        returnString += "\tavg: " + addSchemaTimeSaver.getAverageAsString() + "\n";

        returnString += "changeSchema:\n";
        returnString += "\t#:   " + changeSchemaTimeSaver.getNumberOfTimesAsString() + "\n";
        returnString += "\tmin: " + changeSchemaTimeSaver.getMinAsString() + "\n";
        returnString += "\tmax: " + changeSchemaTimeSaver.getMaxAsString() + "\n";
        returnString += "\tavg: " + changeSchemaTimeSaver.getAverageAsString();

        return returnString;
    }

}
