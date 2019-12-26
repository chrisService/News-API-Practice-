package com.example.newsvezba;

public class Section {


    private String sectionId;

    private String webTitle;

    public Section( String sectionId, String webTitle) {

        this.sectionId = sectionId;

        this.webTitle = webTitle;
    }


    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }
}
