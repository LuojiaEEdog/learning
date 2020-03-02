package learning.spring.zxh.demo.model;


import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String content;
    private Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Integer creator;

}
