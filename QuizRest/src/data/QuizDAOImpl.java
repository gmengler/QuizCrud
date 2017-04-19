package data;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import entities.Question;
import entities.Quiz;

@Transactional
public class QuizDAOImpl implements QuizDAO {
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Quiz> index() {
		String query = "Select q from Quiz q";
		return em.createQuery(query, Quiz.class).getResultList();
	}

	@Override
	public Quiz show(int id) {
		return em.find(Quiz.class, id);
	}

	@Override
	public Quiz create(Quiz quiz) {
		em.persist(quiz);
		em.flush();
		return quiz;
	}

	@Override
	public Quiz update(int id, Quiz quiz) {
		Quiz q = em.find(Quiz.class, id);
		q.setName(quiz.getName());
		em.flush();
		return q;
	}

	@Override
	public boolean destroy(int id) {
		Quiz q = em.find(Quiz.class, id);
		
		if(em.find(Quiz.class, id) == null) {
			return false;
		} else {
			em.remove(q);
			return true;
		}
	}

	@Override
	public Set<Question> showQuestions(int id) {
		String query = "SELECT q FROM Quiz q JOIN FETCH q.questions WHERE q.id = :id";
		
		Quiz quiz = em.createQuery(query, Quiz.class)
				.setParameter("id", id)
				.getSingleResult();
		
		Set<Question> questions = quiz.getQuestions();
		
		return questions;
	}
	

	@Override
	public Question createQuestion(int id, Question q) {
		Quiz quiz = em.find(Quiz.class, id);
		q.setQuiz(quiz);
		em.persist(q);
		em.flush();
		return q;
	}
	
	
	@Override
	public boolean destroyQuestion(int id, int questid) {
		Quiz q = em.find(Quiz.class, id);
		Question ques = em.find(Question.class, questid);
		
		if(em.find(Question.class, questid) == null) {
			return false;
		} else {
			em.remove(ques);
			return true;
		}
	}
	
//	@Override
//	public boolean destroy(int id) {
//		Quiz q = em.find(Quiz.class, id);
//		
//		if(em.find(Quiz.class, id) == null) {
//			return false;
//		} else {
//			em.remove(q);
//			return true;
//		}
//	}
	

}
