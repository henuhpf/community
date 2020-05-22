package code.community.mapper;

import code.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url) values(#{name}, #{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
    @Update("update user set token=#{token} where id=#{id}")
    void updateById(User user);
    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") String accountId);
}
