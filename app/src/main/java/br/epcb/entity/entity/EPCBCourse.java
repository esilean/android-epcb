package br.epcb.entity.entity;

import java.io.Serializable;

/**
 * Created by Leandro Bevilaqua on 10/08/2016.
 */

public class EPCBCourse implements Serializable {

    private String mId;
    private String mName;
    private String mTitle;
    private String mDescription;
    private int mLockedDays;
    private String mURLThumbnail;
    private String mURLYoutube;
    private String mURLVimeo;
    private String mURLOtherVideo;
    private String mURLPDFFile;
    private int mOrder;
    private boolean mActive;
    private String mModuleId;
    private EPCBModule mEPCBModule;

    private boolean mLocked;

    public EPCBCourse() {
    }

    public EPCBCourse(String id, String name, String title, String description, int lockedDays, String urlThumbnail, String urlYoutube, String urlVimeo, String urlOtherVideo, String urlPDFFile, int order, boolean active, String moduleId, EPCBModule epcbModule) {
        this.mId = id;
        mName = name;
        mTitle = title;
        mDescription = description;
        mLockedDays = lockedDays;
        mURLThumbnail = urlThumbnail;
        mURLYoutube = urlYoutube;
        mURLVimeo = urlVimeo;
        mURLOtherVideo = urlOtherVideo;
        mURLPDFFile = urlPDFFile;
        mOrder = order;
        mActive = active;
        mModuleId = moduleId;
        mEPCBModule = epcbModule;
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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
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

    public String getURLThumbnail() {
        return mURLThumbnail;
    }

    public void setURLThumbnail(String urlThumbnail) {
        mURLThumbnail = urlThumbnail;
    }

    public String getURLYoutube() {
        return mURLYoutube;
    }

    public void setURLYoutube(String urlYoutube) {
        mURLYoutube = urlYoutube;
    }

    public String getURLVimeo() {
        return mURLVimeo;
    }

    public void setURLVimeo(String urlVimeo) {
        mURLVimeo = urlVimeo;
    }

    public String getURLOtherVideo() {
        return mURLOtherVideo;
    }

    public void setURLOtherVideo(String urlOtherVideo) {
        mURLOtherVideo = urlOtherVideo;
    }

    public String getURLPDFFile() {
        return mURLPDFFile;
    }

    public void setURLPDFFile(String urlPDFFile) {
        mURLPDFFile = urlPDFFile;
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

    public String getModuleId() {
        return mModuleId;
    }

    public void setModuleId(String moduleId) {
        mModuleId = moduleId;
    }

    public EPCBModule getEPCBModule() {
        return mEPCBModule;
    }

    public void setEPCBModule(EPCBModule epcbModule) {
        mEPCBModule = epcbModule;
    }

    public boolean getLocked() {
        return mLocked;
    }

    public void setLocked(boolean locked) {
        mLocked = locked;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
