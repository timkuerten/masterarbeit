package exception;

public class MinMaxValueException extends RuntimeException {

    public MinMaxValueException() {
        super();
    }

    public MinMaxValueException(String minValue, String maxValue) {
        super("Minimal value '" + minValue + "' s larger than maximal value '" + maxValue + "'");
    }

}
