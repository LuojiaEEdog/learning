package learning.spring.zxh.demo.service;


import learning.spring.zxh.demo.dto.QuestionDTO;
import learning.spring.zxh.demo.mapper.QuestionMapper;
import learning.spring.zxh.demo.mapper.UserMapper;
import learning.spring.zxh.demo.model.Question;
import learning.spring.zxh.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    public List<QuestionDTO> list() {
        List<Question> questions = questionMapper.list();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);

            if (user == null) {
                questionDTO.setAvatarUrl("https://i0.hdslb.com/bfs/archive/1be2fd76cc98cdc6a595c05c3134fbf937a1c126.png");
            }else{
                if (user.getAvatarUrl() != null) {
                    questionDTO.setAvatarUrl(user.getAvatarUrl());
                } else {
                    questionDTO.setAvatarUrl("https://i0.hdslb.com/bfs/archive/1be2fd76cc98cdc6a595c05c3134fbf937a1c126.png");
                }
                questionDTOList.add(questionDTO);
            }
        }
        return questionDTOList;
    }
}
