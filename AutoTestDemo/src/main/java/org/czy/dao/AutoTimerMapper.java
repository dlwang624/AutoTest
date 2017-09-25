package org.czy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.czy.entity.AutoTimer;

public interface AutoTimerMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autotimer
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autotimer
     *
     * @mbg.generated
     */
    int insert(AutoTimer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autotimer
     *
     * @mbg.generated
     */
    int insertSelective(AutoTimer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autotimer
     *
     * @mbg.generated
     */
    AutoTimer selectByPrimaryKey(Integer id);
    
    AutoTimer selectByExecDate(@Param("execdate")String execdate,@Param("ip")String ip);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autotimer
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(AutoTimer record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table autotimer
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(AutoTimer record);
    
    List<AutoTimer> selectByQcdbID(Integer id);
    
    List<AutoTimer> selectByQcdbAndID(@Param("id")Integer id,@Param("uid")Integer uid);
    
    List<AutoTimer> selectByQcdbIDAndWeek(@Param("week")String week,@Param("ip")String ip);
    
    List<AutoTimer> selectByIP(String ip);
}