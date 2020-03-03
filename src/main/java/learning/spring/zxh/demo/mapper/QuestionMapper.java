package learning.spring.zxh.demo.mapper;

import learning.spring.zxh.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question " +
            "(title,content,gmt_create,gmt_modified,tag,creator) " +
            "values (#{title},#{content},#{gmtCreate},#{gmtModified},#{tag},#{creator})")
    void creat(Question question);

    @Select("select * from question")
    List<Question> list();
}
