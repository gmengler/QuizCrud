package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import data.QuizDAO;
import entities.Question;
import entities.Quiz;

@RestController
public class QuizController {
	
	@Autowired
	QuizDAO quizDao;
	
	@RequestMapping(path = "ping", method = RequestMethod.GET)
	public String ping() {
		return "pong";
	}
	
	// works
	@RequestMapping(path = "quizzes", method = RequestMethod.GET)
	public List<Quiz> indexQuizzes() {
		return quizDao.index();
	}
	
	// works
	@RequestMapping(path = "quizzes/{id}", method = RequestMethod.GET)
	public Quiz showQuiz(@PathVariable int id) {
		return quizDao.show(id);
	}
	
	// works
	@RequestMapping(path = "quizzes", method = RequestMethod.POST)
	public Quiz createQuiz(@RequestBody String jsonQuiz, HttpServletResponse res) {
		res.setStatus(201);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			Quiz newQuiz = mapper.readValue(jsonQuiz, Quiz.class);
			return quizDao.create(newQuiz);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	// works
	@RequestMapping(path = "quizzes/{id}", method = RequestMethod.PUT)
	public Quiz updateQuiz(@PathVariable int id, @RequestBody String quizJSON, HttpServletResponse res) {
		res.setStatus(202);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			Quiz updateQuiz = mapper.readValue(quizJSON, Quiz.class);
			return quizDao.update(id, updateQuiz);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	// works
	@RequestMapping(path = "quizzes/{id}", method = RequestMethod.DELETE)
	public boolean destroyQuiz(@PathVariable int id) {
		return quizDao.destroy(id);
	}
	
	// works
	@RequestMapping(path = "quizzes/{id}/questions", method = RequestMethod.GET)
	public Set<Question> indexQuestions(@PathVariable int id) {
		return quizDao.showQuestions(id);
	}
	
	// works
	@RequestMapping(path = "quizzes/{id}/questions", method = RequestMethod.POST)
	public Question createQuestion(@PathVariable int id, @RequestBody String jsonQuestion, 
			HttpServletResponse res) {
		res.setStatus(201);
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			Question updateQuestion = mapper.readValue(jsonQuestion, Question.class);
			return quizDao.createQuestion(id, updateQuestion);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	// works
	@RequestMapping(path = "quizzes/{id}/questions/{questId}", method = RequestMethod.DELETE)
	public boolean destroyQuestion(@PathVariable int id, @PathVariable int questId) {
		return quizDao.destroyQuestion(id, questId);
	}
	
}
