package org.czy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.czy.entity.Project;

public interface ProjectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated
     */
    int insert(Project record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated
     */
    int insertSelective(Project record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated
     */
    Project selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Project record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table project
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Project record);
    
    List<Project> selectAll();
    
    /**
     * @author dengpeng
     * @param ids 获取包含ids中id的对象
     * @return
     */
    List<Project> selectAllByProID(@Param("ids") String[] ids);
    
    Project selectByPID(String projectid);
    
    List<String> selectByProID(@Param("ids") String[] ids);
}