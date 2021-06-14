package Model;

public class Blog {
    private String Title;
    private String image;
    private String Description;
    private String timeStamp;
    private String userId;
    private Blog(){

    }
    public Blog(String title, String image, String description, String timeStamp, String userId) {
        Title = title;
        this.image = image;
        Description = description;
        this.timeStamp = timeStamp;
        this.userId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return Description;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
