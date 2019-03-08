package pcube.servey.NavgationDrawerUtils;

import android.graphics.drawable.Drawable;

/**
 * Created by Chinmay on 12/28/2016.
 */
public class NavDrawerItem {
    private String title;
    private Drawable thumbnail;
    private String slug;


    public NavDrawerItem() {

    }

    public NavDrawerItem(String title, Drawable thumbnail, String slug) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Drawable thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
