package harsu.atmos2k15.atmos.com.atmos2k15.set;

/**
 * Created by admin on 14-08-2015.
 */
public class EventSet {
    private int id;
    private Long start_time;
    private String name;
    private String img_link;
    private String venue;
    private int image_downloaded;
    private  boolean favourite;

    public String getVenue() {
        return venue;
    }

    public EventSet(int id, Long start_time,String name,String img_link,String venue,int image_downloaded,boolean favourite) {
        this.id = id;

        this.start_time = start_time;
        this.name=name;
        this.img_link=img_link;
        this.venue=venue;
        this.image_downloaded=image_downloaded;
        this.favourite=favourite;
    }

    public int getId() {
        return id;
    }

    public int getImage_downloaded() {
        return image_downloaded;
    }

    public Long getStart_time() {
        return start_time;
    }

    public String getName() {
        return name;
    }

    public String getImg_link() {
        return img_link;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
