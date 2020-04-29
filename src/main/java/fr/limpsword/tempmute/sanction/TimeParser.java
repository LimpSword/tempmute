package fr.limpsword.tempmute.sanction;

import java.util.concurrent.TimeUnit;

public final class TimeParser {

    public static long getTime(String format) {
        if (format.endsWith("s")) {
            long value = Integer.parseInt(format.split("s")[0]) * 1000;
            return value < 0 ? 0 : value;
        } else if (format.endsWith("m")) {
            long value = Integer.parseInt(format.split("m")[0]) * 60000;
            return value < 0 ? 0 : value;
        } else if (format.endsWith("h")) {
            long value = (long) (Integer.parseInt(format.split("h")[0]) * 3.6e6);
            return value < 0 ? 0 : value;
        } else if (format.endsWith("d")) {
            long value = (long) (Integer.parseInt(format.split("d")[0]) * 8.64e7);
            return value < 0 ? 0 : value;
        } else if (format.endsWith("M")) {
            long value = (long) (Integer.parseInt(format.split("M")[0]) * 2.628e+9);
            return value < 0 ? 0 : value;
        } else if (format.endsWith("Y")) {
            long value = (long) (Integer.parseInt(format.split("Y")[0]) * 3.154e+10);
            return value < 0 ? 0 : value;
        }
        return 0L;
    }

    public static String getDurationString(long duration) {
        duration = TimeUnit.MILLISECONDS.toSeconds(duration);

        long days = TimeUnit.SECONDS.toDays(duration);
        duration -= TimeUnit.DAYS.toSeconds(days);

        long hours = TimeUnit.SECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toSeconds(hours);

        long minutes = TimeUnit.SECONDS.toMinutes(duration);
        duration -= TimeUnit.MINUTES.toSeconds(minutes);

        long seconds = TimeUnit.SECONDS.toSeconds(duration);

        StringBuilder result = new StringBuilder();
        if (days > 0) {
            result.append(days).append(" day(s) ");
        }
        if (hours > 0) {
            result.append(hours).append(" hour(s) ");
        }
        if (minutes > 0) {
            result.append(minutes).append(" minute(s) ");
        }
        if (seconds > 0) {
            result.append(seconds).append(" second(s)");
        }
        return result.toString();
    }
}