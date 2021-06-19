package com.xmeme.pojo;

import com.xmeme.utils.IOCommonUtil;

import javax.persistence.*;

@Entity
@Table(name = "meme_creators")
public class MemeCreator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private long owner_id;

    @Column(name = "owner_name")
    private String owner_name;


    @Column(name = "joined_time")
    private long joined_time;

    public MemeCreator() {
    }

    public MemeCreator(String ownerNameToSet) {
        this.owner_name = ownerNameToSet;
        this.joined_time = IOCommonUtil.getCurrentMillSecondsinUTC();
    }

    public long getOwnerID() {
        return this.owner_id;
    }

    public void setId(long ownerIDToSet) {
        this.owner_id = ownerIDToSet;
    }

    public String getOwnerName() {
        return owner_name;
    }

    public void setOwnerName(String ownerNameToSet) {
        this.owner_name = ownerNameToSet;
    }


    public long getJoinedTime() {
        return this.joined_time;
    }

    public void setJoinedTime(long joinedTimeToSet) {
        this.joined_time = joinedTimeToSet;
    }
}
