package org.czy.entity;

public class Switcham {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column switcham.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column switcham.qid
     *
     * @mbg.generated
     */
    private Integer qid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column switcham.amdesc
     *
     * @mbg.generated
     */
    private String amdesc;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column switcham.amurl
     *
     * @mbg.generated
     */
    private String amurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column switcham.amtemp
     *
     * @mbg.generated
     */
    private String amtemp;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column switcham.id
     *
     * @return the value of switcham.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column switcham.id
     *
     * @param id the value for switcham.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column switcham.qid
     *
     * @return the value of switcham.qid
     *
     * @mbg.generated
     */
    public Integer getQid() {
        return qid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column switcham.qid
     *
     * @param qid the value for switcham.qid
     *
     * @mbg.generated
     */
    public void setQid(Integer qid) {
        this.qid = qid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column switcham.amdesc
     *
     * @return the value of switcham.amdesc
     *
     * @mbg.generated
     */
    public String getAmdesc() {
        return amdesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column switcham.amdesc
     *
     * @param amdesc the value for switcham.amdesc
     *
     * @mbg.generated
     */
    public void setAmdesc(String amdesc) {
        this.amdesc = amdesc == null ? null : amdesc.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column switcham.amurl
     *
     * @return the value of switcham.amurl
     *
     * @mbg.generated
     */
    public String getAmurl() {
        return amurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column switcham.amurl
     *
     * @param amurl the value for switcham.amurl
     *
     * @mbg.generated
     */
    public void setAmurl(String amurl) {
        this.amurl = amurl == null ? null : amurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column switcham.amtemp
     *
     * @return the value of switcham.amtemp
     *
     * @mbg.generated
     */
    public String getAmtemp() {
        return amtemp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column switcham.amtemp
     *
     * @param amtemp the value for switcham.amtemp
     *
     * @mbg.generated
     */
    public void setAmtemp(String amtemp) {
        this.amtemp = amtemp == null ? null : amtemp.trim();
    }
}