package com.mysite.sbb.answer;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    /**
     * 답변 등록
     */
    @PostMapping ("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createAnswer
            (Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = this.questionService.getQuestion(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.create(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

    /**
     * 답변 수정
     */
    // 버튼 클릭 시 요청되는 get방식의  'answer/modify/답변id' 형태의 URL을 처리하기 위한 controller
    // URL의 답변 아이디를 통해 조회한 단변 데이터의 내용을 AnswerForm 객체에 대입해 answer_form.html 템플릿에서 시용할 수 있도록 함.
    // 답변 수정 시 기존 내용이 필요하므로 AnswerFrom 객체에 조회한 값을 저장해야함.
    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal){
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    //답변 수정 완료 후 질문 상세 페이지로 돌아가기 위해 answer.getQuestion.getId()로 질문의 아이디를 가져왔다.
    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal){
        if (bindingResult.hasErrors()){
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    /**
     * 답변 삭제
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id ){
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
}
