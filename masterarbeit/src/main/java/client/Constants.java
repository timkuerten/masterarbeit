package client;

public interface Constants {

    // strings
    public static final long RANDOM_START_VALUE = Long.MAX_VALUE;
    public static final int STRING_MIN_LENGTH = 4; // > 0
    public static final int STRING_MAX_LENGTH = 12; // > 0 && >= STRING_MIN_LENGTH
    public static final boolean UPPERCASE = true;
    public static final boolean LOWERCASE = true;
    public static final boolean NUMBERS = true;

    // schema
    public static final int COUNT_NEW_SCHEMA = 3; // > 0

    // profile key
    public static final double PROBABILITY_OF_SCHEMA_USAGE = 0.8; // >= 0 && <= 1
    public static final double PROBABILITYOF_THIRD_PARTY_ID_USAGE = 0.8; // >= 0 && <= 1

}
