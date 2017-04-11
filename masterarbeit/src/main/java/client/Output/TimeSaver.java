package client.Output;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimeSaver {

    private List<Long> times;
    private NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);

    public TimeSaver() {
        times = new ArrayList<>();
    }

    public void addTime(long l) {
        times.add(l);
    }

    public long getMin() {
        if (times.isEmpty()) {
            return Long.MAX_VALUE;
        } else {
            long min = Long.MAX_VALUE;
            for (long time : times) {
                if (time < min) {
                    min = time;
                }
            }
            return min;
        }
    }

    public long getMax() {
        if (times.isEmpty()) {
            return Long.MIN_VALUE;
        } else {
            long max = Long.MIN_VALUE;
            for (long time : times) {
                if (time > max) {
                    max = time;
                }
            }
            return max;
        }
    }

    public long getAverage() {
        if (times.isEmpty()) {
            return 0;
        } else {
            long average = 0;
            for (long time : times) {
                average += time;
            }
            average = average / times.size();
            return average;
        }
    }

    public int getNumberOfTimes() {
        return times.size();
    }

    public String getNumberOfTimesAsString() {
        return String.valueOf(nf.format(times.size()));
    }

    public String getMinAsString() {
        if (times.isEmpty()) {
            return "not available";
        } else {
            return String.valueOf(nf.format(getMin()));
        }
    }

    public String getMaxAsString() {
        if (times.isEmpty()) {
            return "not available";
        } else {
            return String.valueOf(nf.format(getMax()));
        }
    }

    public String getAverageAsString() {
        if (times.isEmpty()) {
            return "not available";
        } else {
            return String.valueOf(nf.format(getAverage()));
        }
    }

}
