package test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Quiz;

public class QuizTest {
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private Quiz quiz = null;
	
	@Test
	public void test() {
	  boolean pass = true;
	  assertEquals(pass, true);
	}
	
	@Before
	public void setUp() throws Exception{
		emf = Persistence.createEntityManagerFactory("Quiz");
		em = emf.createEntityManager();
		quiz = em.find(Quiz.class, 10);
	}
	
	@After
	public void tearDown() throws Exception{
		em.close();
		emf.close();
	}
	
	@Test
	public void test_quiz_db() {
		assertEquals("States", quiz.getName());
	}
	
	@Test
	public void test_get_questions() {
		assertEquals(5, quiz.getQuestions().size());
	}

}
