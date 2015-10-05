package harsu.atmos2k15.atmos.com.atmos2015.set;

/**
 * Created by admin on 02-10-2015.
 */
public class EventDataSet {
    int id, image_downloaded, favourite;
    String tag, tab, name, venue, details, contacts, image_link;
    Long start_time, end_time;
    Double cost;


    public EventDataSet(
            int id,
            String tag,
            String tab,
            String name,
            Long start_time,
            Long end_time,
            String venue,
            String details,
            String contacts,
            String image_link,
            int image_downloaded,
            Double cost,
            int favourite) {
        this.id = id;
        this.image_downloaded = image_downloaded;
        this.favourite = favourite;
        this.tag = tag;
        this.tab = tab;
        this.name = name;
        this.venue = venue;
        this.details = details;
        this.contacts = contacts;
        this.image_link = image_link;
        this.start_time = start_time;
        this.end_time = end_time;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public int getImage_downloaded() {
        return image_downloaded;
    }

    public int getFavourite() {
        return favourite;
    }

    public String getTag() {
        return tag;
    }

    public String getTab() {
        return tab;
    }

    public String getName() {
        return name;
    }

    public String getVenue() {
        return venue;
    }

    public String getDetails() {
        return details;
    }

    public String getContacts() {
        return contacts;
    }

    public String getImage_link() {
        return image_link;
    }

    public Long getStart_time() {
        return start_time;
    }

    public Long getEnd_time() {
        return end_time;
    }

    public Double getCost() {
        return cost;
    }
}
