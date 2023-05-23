package com.mysite.sbb;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.repository.AnswerRepository;
import com.mysite.sbb.repository.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private QuestionService questionService;
	@Test
	void testJpa() {
		//데이터 저장하기
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?");
//		q1.setContent("sbb에 대해 알고 싶습니다.");
//		q1.setCreatDate(LocalDateTime.now());
//		this.questionRepository.save(q1); // 첫번째 질문 저장
//
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델 질문입니다.");
//		q2.setContent("id는 자동으로 생성되나요?");
//		q2.setCreatDate(LocalDateTime.now());
//		this.questionRepository.save(q2);// 두번째 질문 작성

		//데이터 조회하기
//		List<Question> all = this.questionRepository.findAll();
//		Assertions.assertEquals(3,all.size());
//
//		Question q = all.get(0);
//		Assertions.assertEquals("sbb가 무엇인가요?", q.getSubject());

//		Optional<Question> oq = this.questionRepository.findById(1);
//		if (oq.isPresent()){
//			Question q = oq.get();
//			Assertions.assertEquals("sbb가 무엇인가요?", q.getSubject());
//		}

//		Question q = this.questionRepository.findBySubject("스프링부트 모델 질문입니다.");
//		Assertions.assertEquals(3,q.getId());

//		Question q = this.questionRepository.findBySubjectAndContent(
//				"스프링부트 모델 질문입니다.", "id는 자동으로 생성되나요?");
//		Assertions.assertEquals(3,q.getId());

//		List<Question> qList = this.questionRepository.findBySubjectLike("스프링%");
//		Question q = qList.get(0);
//		Assertions.assertEquals("스프링부트 모델 질문입니다.",q.getSubject());


		//데이터 수정하기
//		Optional<Question> oq = this.questionRepository.findById(1);
//		Assertions.assertTrue(oq.isPresent());
//		Question q = oq.get();
//		q.setSubject("수정된 제목");
//		this.questionRepository.save(q);

		//데이터 삭제하기
//		Assertions.assertEquals(3, this.questionRepository.count());
//		Optional<Question> oq = this.questionRepository.findById(1);
//		Assertions.assertTrue(oq.isPresent());
//		Question q = oq.get();
//		this.questionRepository.delete(q);
//		Assertions.assertEquals(2,questionRepository.count());
	}

	@Test
	@Transactional
	void TestJpa2(){
		//답변 생성 후 저장하기
//		Optional<Question> oq = this.questionRepository.findById(3);
//		Assertions.assertTrue(oq.isPresent());
//		Question q = oq.get();
//
//		Answer a = new Answer();
//		a.setContent("네 자동으로 생성됩니다.");
//		a.setQuestion(q);
//		a.setCreateDate(LocalDateTime.now());
//		this.answerRepository.save(a);

//		//답변 조회하기
//		Optional<Answer> oa = this.answerRepository.findById(1);
//		Assertions.assertTrue(oa.isPresent());
//
//		Answer a = oa.get();
//		Assertions.assertEquals(3, a.getQuestion().getId());
//
//
//		//답변에 연결된 질문 찾기
//		a.getQuestion();

		//질문에 달린 답변 찾기
		Optional<Question> oq = this.questionRepository.findById(3);
		Assertions.assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		Assertions.assertEquals(1,answerList.size());
		Assertions.assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
	//테스트 데이터 생성
	@Test
	void testJpa3(){
		for (int i = 0; i < 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]",i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
	}
}