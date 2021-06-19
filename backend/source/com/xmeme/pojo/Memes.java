package com.xmeme.pojo;

import com.xmeme.clientobjects.ClientMeme;
import com.xmeme.utils.Constants;
import com.xmeme.utils.IOCommonUtil;
import org.hibernate.annotations.DynamicUpdate;
import org.json.JSONObject;

import javax.persistence.*;

@Entity
@Table(name = "memes")
@DynamicUpdate
public class Memes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meme_id")
    private long meme_id;
    @Column(name = "caption")
    private String caption;
    @Column(name = "url" )
    private String url;
    @Column(name = "created_time", updatable = false)
    private long created_time;

    @JoinColumn(name = "owner_id", updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private MemeCreator memeCreator;

    @Column(name = "owner_id",insertable = false, updatable = false)
    private long owner_id;


    public Memes() {
    }

    public Memes(ClientMeme memeObjectDetails,MemeCreator creator) {
        if(IOCommonUtil.isValidObject(memeObjectDetails)) {
            this.caption = memeObjectDetails.getCaption();
            this.url = memeObjectDetails.getUrl();
        }
        this.created_time = IOCommonUtil.getCurrentMillSecondsinUTC();
        this.memeCreator =creator;
    }

    public long getMemeId() {
        return meme_id;
    }

    public void setMemeId(long memeID) {
        this.meme_id = memeID;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreatedTime() {
        return created_time;
    }

    public void setCreatedTime(long createdTimeToSet) {
        this.created_time = createdTimeToSet;
    }

    public MemeCreator getMemeCreator() {
        return this.memeCreator;
    }

    public long getOwnerID() {
        return this.memeCreator.getOwnerID();
    }

    public void setMemeCreator(MemeCreator memeCreator) {
        this.memeCreator = memeCreator;
    }

    /**
     * @return JSONObject which is sent to the client
     */
    public JSONObject valueAsJSON(){
        JSONObject clientJSON = IOCommonUtil.addJSONKeyValue(null, Constants.CLIENT_KEY_URL,this.url);
        clientJSON = IOCommonUtil.addJSONKeyValue(clientJSON, Constants.CLIENT_KEY_CAPTION,this.caption);
        clientJSON = IOCommonUtil.addJSONKeyValue(clientJSON, Constants.CLIENT_MEME_ID,this.meme_id);
        clientJSON = IOCommonUtil.addJSONKeyValue(clientJSON, Constants.CLIENT_CREATED_TIME,
                String.valueOf(this.created_time));
        clientJSON = IOCommonUtil.addJSONKeyValue(clientJSON, Constants.MEME_CREATOR_NAME,this.getMemeCreator().getOwnerName());
        clientJSON = IOCommonUtil.addJSONKeyValue(clientJSON, Constants.MEME_CREATOR_ID,this.getMemeCreator().getOwnerID());
        return clientJSON;
    }
}
