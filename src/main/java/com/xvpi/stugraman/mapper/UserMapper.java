package com.xvpi.stugraman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvpi.stugraman.beans.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where username=#{arg0} and password_hash=#{arg1} and role=#{arg2} ")
    User login(String username, String password, String role);
    @Delete("DELETE FROM user WHERE user_id = #{arg0}")
    int deleteUserById(String id);
    @Insert("insert into user (user_id,username,password_hash,role,person_id) values (#{arg0},#{arg1},#{arg2}," +
            "#{arg3},#{arg4})")
    int addUser(String id, String name, String password, String role, String id1);
//    @Insert("insert into User (name,gender,type) values (#{arg0},#{arg1},#{arg2})")
//    void insertUser(String username, String gender, String type);
}