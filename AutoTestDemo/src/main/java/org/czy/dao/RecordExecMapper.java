package org.czy.dao;

import org.czy.entity.RecordExec;

public interface RecordExecMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recordexec
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recordexec
     *
     * @mbg.generated
     */
    int insert(RecordExec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recordexec
     *
     * @mbg.generated
     */
    int insertSelective(RecordExec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recordexec
     *
     * @mbg.generated
     */
    RecordExec selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recordexec
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(RecordExec record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table recordexec
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(RecordExec record);
}