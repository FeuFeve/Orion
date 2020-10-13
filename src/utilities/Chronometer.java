package utilities;

public class Chronometer {

    private long startTime, endTime, startPause, endPause, duration;


    public void start() {
        startTime = System.currentTimeMillis();
        endTime = 0;
        startPause = 0;
        endPause = 0;
        duration = 0;
    }

    public void pause() {
        if (startTime == 0) {
            return;
        }
        startPause = System.currentTimeMillis();
    }

    public void resume() {
        if (startTime == 0) {
            return;
        }
        if (startPause == 0) {
            return;
        }
        endPause = System.currentTimeMillis();
        startTime = startTime + endPause - startPause;
        endTime = 0;
        startPause = 0;
        endPause = 0;
        duration = 0;
    }

    public void stop() {
        if (startTime == 0) {
            return;
        }
        endTime = System.currentTimeMillis();
        duration = (endTime - startTime) - (endPause - startPause);
        startTime = 0;
        endTime = 0;
        startPause = 0;
        endPause = 0;
    }

    public long getDurationSec() {
        return duration / 1000;
    }

    public long getDurationMs() {
        return duration;
    }

    public String getDurationSecTxt() {
        return timeToHMSMS(getDurationSec() * 1000);
    }

    public String getDurationMsTxt() {
        return timeToHMSMS(getDurationMs());
    }

    /**
     *
     * @param timeInMs time (duration) in milliseconds
     * @return a duration in the "..h ..min ..s ...ms" format
     */
    public static String timeToHMSMS(long timeInMs) {
        String r = "";

        int timeInSec = (int) (timeInMs / 1000);
        if (timeInSec > 0) {
            int h = timeInSec / 3600;
            int m = (timeInSec % 3600) / 60;
            int s = timeInSec % 60;

            if (h > 0) r = h + "h";
            if (m > 0) r += " " + m + "min";
            if (s > 0) r += " " + s + "s";
        }

        int ms = (int) (timeInMs % 1000);
        if (ms > 0) r += " " + ms + "ms";

        r = r.trim();

        return r;
    }

}
