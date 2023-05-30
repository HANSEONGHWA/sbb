package com.mysite.sbb.answer;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    /**
     * 답변 등록
     */
    @PostMapping("/create/{id}")
    public Answer create(Question question, String content, SiteUser author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }

    /**
     * 답변 조회
     */
    public Answer getAnswer(Integer id){
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()){
            return answer.get();
        }else {
            throw new DataNotFoundException("answer not found");
        }
    }

    /**
     * 답변 수정
     */
    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }


}
