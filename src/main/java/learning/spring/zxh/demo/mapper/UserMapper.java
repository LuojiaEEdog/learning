package learning.spring.zxh.demo.mapper;

import learning.spring.zxh.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name, account_id,token,gmt_create,gmt_modified,avatar_url)" +
            " values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl}) ")
    //mybatis对#{}的同名元素替代后进行进行sql语句操作，元素来自于形参对象，此处对应user的元素
    void insert(User user);

    @Select("select * from user where token = #{token}")
    //使用@Param注解将对非对象的基本数据类型提取后，放入对应的#{}中进行替代
    User findByToken(@Param("token") String token);
}
