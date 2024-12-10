package com.xvpi.stugraman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvpi.stugraman.beans.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from user where user_id=#{arg0} and password_hash=#{arg1} and role=#{arg2} ")
    User login(String userId, String password, String role);
    @Delete("DELETE FROM user WHERE user_id = #{arg0}")
    int deleteUserById(String id);
    @Insert("insert into user (user_id,username,password_hash,role,person_id) values (#{arg0},#{arg1},#{arg2}," +
            "#{arg3},#{arg4})")
    int addUser(String id, String name, String password, String role, String id1);
    @Select("select * from user where user_id=#{arg0}")
    User getUserById(String id);
    @Update( "UPDATE user SET password_hash = #{arg1} WHERE user_id = #{arg0}")
    int changePassword(String id, String password);
    @Update( "UPDATE user SET last_login_at = CURRENT_TIMESTAMP WHERE user_id = #{arg0}")
    void updateTime(String userId);
    @Delete("DELETE FROM user")
    void deleteAllUsers();

}
