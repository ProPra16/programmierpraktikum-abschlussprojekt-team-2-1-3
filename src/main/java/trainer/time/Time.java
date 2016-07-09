package trainer.time;


public class Time {

    private long start;
    private long end;
    private long duration;

    public Time(long start, long end) {
        this.start = start;
        this.end = end;
        duration = end - start;
    }

    public long getDuration() {
        return duration;
    }
}
