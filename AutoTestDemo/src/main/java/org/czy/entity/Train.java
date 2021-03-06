package org.czy.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Train {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column train.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column train.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column train.author
     *
     * @mbg.generated
     */
    private String author;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column train.time
     *
     * @mbg.generated
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column train.fileurl
     *
     * @mbg.generated
     */
    private String fileurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column train.temp
     *
     * @mbg.generated
     */
    private String temp;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column train.id
     *
     * @return the value of train.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column train.id
     *
     * @param id the value for train.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column train.title
     *
     * @return the value of train.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column train.title
     *
     * @param title the value for train.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column train.author
     *
     * @return the value of train.author
     *
     * @mbg.generated
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column train.author
     *
     * @param author the value for train.author
     *
     * @mbg.generated
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column train.time
     *
     * @return the value of train.time
     *
     * @mbg.generated
     */
    public Date getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column train.time
     *
     * @param time the value for train.time
     *
     * @mbg.generated
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column train.fileurl
     *
     * @return the value of train.fileurl
     *
     * @mbg.generated
     */
    public String getFileurl() {
        return fileurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column train.fileurl
     *
     * @param fileurl the value for train.fileurl
     *
     * @mbg.generated
     */
    public void setFileurl(String fileurl) {
        this.fileurl = fileurl == null ? null : fileurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column train.temp
     *
     * @return the value of train.temp
     *
     * @mbg.generated
     */
    public String getTemp() {
        return temp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column train.temp
     *
     * @param temp the value for train.temp
     *
     * @mbg.generated
     */
    public void setTemp(String temp) {
        this.temp = temp == null ? null : temp.trim();
    }
}