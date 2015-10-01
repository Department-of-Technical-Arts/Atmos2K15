package harsu.atmos2k15.atmos.com.atmos2k15.set;

/**
 * Created by admin on 01-10-2015.
 */
public class ScheduleSet {
    int event_id, event_tag;
    long start_time;
    String name, venue;

    public ScheduleSet(int event_id, int event_tag, String name, long start_time, String venue) {
        this.event_id = event_id;
        this.name=name;
        this.event_tag = event_tag;
        this.start_time = start_time;
        this.venue = venue;
    }

    public int getEvent_id() {
        return event_id;
    }

    public int getEvent_tag() {
        return event_tag;
    }

    public long getStart_time() {
        return start_time;
    }

    public String getVenue() {
        return venue;
    }

    public String getName() {
        return name;
    }
}
