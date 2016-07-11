package trainer.time;


public class Time {

    long start;
    long end;

    public Time(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getDuration() {
        return end - start;
    }
}
