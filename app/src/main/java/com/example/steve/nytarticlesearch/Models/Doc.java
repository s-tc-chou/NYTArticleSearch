package com.example.steve.nytarticlesearch.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import javax.annotation.Generated;

public class Doc implements Parcelable {

    @SerializedName("web_url")
    @Expose
    private String webUrl;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("lead_paragraph")
    @Expose
    private String leadParagraph;
    @SerializedName("abstract")
    @Expose
    private Object _abstract;
    @SerializedName("print_page")
    @Expose
    private String printPage;
    @SerializedName("blog")
    @Expose
    private List<Object> blog = new ArrayList<Object>();
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("multimedia")
    @Expose
    private List<Multimedium> multimedia = new ArrayList<Multimedium>();
    @SerializedName("headline")
    @Expose
    private Headline headline;
    @SerializedName("keywords")
    @Expose
    private List<Keyword> keywords = new ArrayList<Keyword>();
    @SerializedName("pub_date")
    @Expose
    private String pubDate;
    @SerializedName("document_type")
    @Expose
    private String documentType;
    @SerializedName("news_desk")
    @Expose
    private String newsDesk;
    @SerializedName("section_name")
    @Expose
    private String sectionName;
    @SerializedName("subsection_name")
    @Expose
    private Object subsectionName;
    //@SerializedName("byline")
    //@Expose
    //private List<Byline> byline = new ArrayList<Byline>();
    @SerializedName("type_of_material")
    @Expose
    private String typeOfMaterial;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("word_count")
    @Expose
    private Integer wordCount;
    @SerializedName("slideshow_credits")
    @Expose
    private Object slideshowCredits;

    /**
     * @return The webUrl
     */
    public String getWebUrl() {
        return webUrl;
    }

    /**
     * @param webUrl The web_url
     */
    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    /**
     * @return The snippet
     */
    public String getSnippet() {
        return snippet;
    }

    /**
     * @param snippet The snippet
     */
    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    /**
     * @return The leadParagraph
     */
    public String getLeadParagraph() {
        return leadParagraph;
    }

    /**
     * @param leadParagraph The lead_paragraph
     */
    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    /**
     * @return The _abstract
     */
    public Object getAbstract() {
        return _abstract;
    }

    /**
     * @param _abstract The abstract
     */
    public void setAbstract(Object _abstract) {
        this._abstract = _abstract;
    }

    /**
     * @return The printPage
     */
    public String getPrintPage() {
        return printPage;
    }

    /**
     * @param printPage The print_page
     */
    public void setPrintPage(String printPage) {
        this.printPage = printPage;
    }

    /**
     * @return The blog
     */
    public List<Object> getBlog() {
        return blog;
    }

    /**
     * @param blog The blog
     */
    public void setBlog(List<Object> blog) {
        this.blog = blog;
    }

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The multimedia
     */
    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    /**
     * @param multimedia The multimedia
     */
    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    /**
     * @return The headline
     */
    public Headline getHeadline() {
        return headline;
    }

    /**
     * @param headline The headline
     */
    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    /**
     * @return The keywords
     */
    public List<Keyword> getKeywords() {
        return keywords;
    }

    /**
     * @param keywords The keywords
     */
    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    /**
     * @return The pubDate
     */
    public String getPubDate() {
        return pubDate;
    }

    /**
     * @param pubDate The pub_date
     */
    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * @return The documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType The document_type
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return The newsDesk
     */
    public String getNewsDesk() {
        return newsDesk;
    }

    /**
     * @param newsDesk The news_desk
     */
    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    /**
     * @return The sectionName
     */
    public String getSectionName() {
        return sectionName;
    }

    /**
     * @param sectionName The section_name
     */
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    /**
     * @return The subsectionName
     */
    public Object getSubsectionName() {
        return subsectionName;
    }

    /**
     * @param subsectionName The subsection_name
     */
    public void setSubsectionName(Object subsectionName) {
        this.subsectionName = subsectionName;
    }


    /**
     * @return The typeOfMaterial
     */
    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    /**
     * @param typeOfMaterial The type_of_material
     */
    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The wordCount
     */
    public Integer getWordCount() {
        return wordCount;
    }

    /**
     * @param wordCount The word_count
     */
    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * @return The slideshowCredits
     */
    public Object getSlideshowCredits() {
        return slideshowCredits;
    }

    /**
     * @param slideshowCredits The slideshow_credits
     */
    public void setSlideshowCredits(Object slideshowCredits) {
        this.slideshowCredits = slideshowCredits;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.webUrl);
        dest.writeString(this.snippet);
        dest.writeString(this.leadParagraph);
        //dest.writeParcelable(this._abstract, flags);
        dest.writeString(this.printPage);
        //dest.writeList(this.blog);
        dest.writeString(this.source);
        dest.writeTypedList(this.multimedia);
        dest.writeParcelable(this.headline, flags);
        dest.writeTypedList(this.keywords);
        dest.writeString(this.pubDate);
        dest.writeString(this.documentType);
        dest.writeString(this.newsDesk);
        dest.writeString(this.sectionName);
        //dest.writeParcelable(this.subsectionName, flags);
        //dest.writeTypedList(this.byline);
        dest.writeString(this.typeOfMaterial);
        dest.writeString(this.id);
        dest.writeValue(this.wordCount);
        //dest.writeParcelable(this.slideshowCredits, flags);
    }

    public Doc() {
    }

    protected Doc(Parcel in) {
        this.webUrl = in.readString();
        this.snippet = in.readString();
        this.leadParagraph = in.readString();
        //this._abstract = in.readParcelable(Object.class.getClassLoader());
        this.printPage = in.readString();
        //this.blog = new ArrayList<Object>();
        // in.readList(this.blog, Object.class.getClassLoader());
        this.source = in.readString();
        this.multimedia = in.createTypedArrayList(Multimedium.CREATOR);
        this.headline = in.readParcelable(Headline.class.getClassLoader());
        this.keywords = in.createTypedArrayList(Keyword.CREATOR);
        this.pubDate = in.readString();
        this.documentType = in.readString();
        this.newsDesk = in.readString();
        this.sectionName = in.readString();
        //this.subsectionName = in.readParcelable(Object.class.getClassLoader());
        //this.byline = in.createTypedArrayList(Byline.CREATOR);
        this.typeOfMaterial = in.readString();
        this.id = in.readString();
        this.wordCount = (Integer) in.readValue(Integer.class.getClassLoader());
        //this.slideshowCredits = in.readParcelable(Object.class.getClassLoader());
    }

    public static final Creator<Doc> CREATOR = new Creator<Doc>() {
        @Override
        public Doc createFromParcel(Parcel source) {
            return new Doc(source);
        }

        @Override
        public Doc[] newArray(int size) {
            return new Doc[size];
        }
    };
}