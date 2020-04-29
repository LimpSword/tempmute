package fr.limpsword.tempmute.sanction;

import java.util.concurrent.TimeUnit;

public final class TimeParser {

    public static long getTime(String format) {
        if (format.endsWith("s")) {
            long value = Integer.parseInt(format.split("s")[0]) * TimeUnit.SECONDS.toMillis(1);
            return value < 0 ? 0 : value;
        } else if (format.endsWith("m")) {
            long value = Integer.parseInt(format.split("m")[0]) * TimeUnit.MINUTES.toMillis(1);
            return value < 0 ? 0 : value;
        } else if (format.endsWith("h")) {
            long value = Integer.parseInt(format.split("h")[0]) * TimeUnit.HOURS.toMillis(1);
            return value < 0 ? 0 : value;
        } else if (format.endsWith("d")) {
            long value = Integer.parseInt(format.split("d")[0]) * TimeUnit.DAYS.toMillis(1);
            return value < 0 ? 0 : value;
        } else if (format.endsWith("M")) {
            long value = Integer.parseInt(format.split("M")[0]) * TimeUnit.DAYS.toMillis(31);
            return value < 0 ? 0 : value;
        } else if (format.endsWith("Y")) {
            long value = Integer.parseInt(format.split("Y")[0]) * TimeUnit.DAYS.toMillis(365);
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