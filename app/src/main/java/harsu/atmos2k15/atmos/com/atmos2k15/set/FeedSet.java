package harsu.atmos2k15.atmos.com.atmos2k15.set;

/**
 * Created by admin on 01-10-2015.
 */
public class FeedSet {
    int event_id;
    String name;
    String message;
    long posted_time;

    public FeedSet(int event_id, String name, String message, long posted_time) {
        this.event_id = event_id;
        this.name = name;
        this.message = message;
        this.posted_time = posted_time;
    }

    public int getEvent_id() {
        return event_id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public long getPosted_time() {
        return posted_time;
    }
}
