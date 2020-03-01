package learning.spring.zxh.demo.mapper;

import learning.spring.zxh.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question " +
            "(title,content,gmt_create,gmt_modified,tag,creator) " +
            "values (#{title},#{content},#{gmtCreate},#{gmtModified},#{tag},#{creator})")
    public void creat(Question question);
}
