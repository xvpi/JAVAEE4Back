package com.xvpi.stugraman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvpi.stugraman.beans.Person;
import com.xvpi.stugraman.beans.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PersonMapper extends BaseMapper<Person> {

    @Delete("DELETE FROM person WHERE person_id = #{arg0}")
    int deletePersonById(String id);
    @Insert("insert into person (person_id,name,gender,type) values (#{arg0},#{arg1},#{arg2}," +
            "#{arg3})")
    int addPerson(String id, String name, String gender, String role);
    @Select("SELECT * FROM person where person_id = #{arg0}")
    Person getPersonById(String id);
    @Select("SELECT name FROM person where person_id = #{arg0}")
    String getNameById(String sid);
}
