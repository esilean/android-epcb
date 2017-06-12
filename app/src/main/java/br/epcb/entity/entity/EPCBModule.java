package br.epcb.entity.entity;


import java.io.Serializable;

public class EPCBModule implements Serializable {

    private String mId;
    private String mName;
    private String mDescription;
    private int mLockedDays;
    private int mOrder;
    private boolean mActive;

    private boolean mLocked;

    public EPCBModule(){}

    public EPCBModule(String id, String name, String description, int lockedDays, int order, boolean active) {
        this.mId = id;
        mName = name;
        mDescription = description;
        mLockedDays = lockedDays;
        mOrder = order;
        mActive = active;
    }

    public String getId() {
        return mId;
    }
    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }
    public void setDescription(String description) {
        mDescription = description;
    }

    public int getLockedDays() {
        return mLockedDays;
    }
    public void setLockedDays(int lockedDays) {
        mLockedDays = lockedDays;
    }

    public int getOrder() {
        return mOrder;
    }
    public void setOrder(int order) {
        mOrder = order;
    }

    public boolean getActive() {
        return mActive;
    }
    public void setActive(boolean active) {
        mActive = active;
    }

    public boolean getLocked() {
        return mLocked;
    }
    public void setLocked(boolean locked) {
        mLocked = locked;
    }

    @Override
    public String toString() {
        return "EPCBModule{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mLockedDays='" + mLockedDays + '\'' +
                ", mOrder='" + mOrder + '\'' +
                ", mActive=" + mActive +
                '}';
    }
}
