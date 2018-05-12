package me.zsr.rss.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Discover {
    @Id
    private Long id;

    @Index(unique = true)
    private String key;

    private String title;

    private String desc;

    private String iconUrl;

    private String url;

    @Generated(hash = 911986006)
    public Discover(Long id, String key, String title, String desc, String iconUrl,
            String url) {
        this.id = id;
        this.key = key;
        this.title = title;
        this.desc = desc;
        this.iconUrl = iconUrl;
        this.url = url;
    }

    @Generated(hash = 156017668)
    public Discover() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Discover clone() {
        Discover discover = new Discover();
        discover.url = this.url;
        discover.title = this.title;
        return discover;
    }
}
