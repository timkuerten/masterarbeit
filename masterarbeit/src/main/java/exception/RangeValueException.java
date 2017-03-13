package exception;

public class RangeValueException extends RuntimeException {

    public RangeValueException() {
        super();
    }

    public RangeValueException(String minValue, String maxValue) {
        super("Minimal value '" + minValue + "' cannot be larger than maximal value '" + maxValue + "'");
    }

}
