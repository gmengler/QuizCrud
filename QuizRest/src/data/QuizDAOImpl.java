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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Question createQuestion(int id, Question q) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean destroyQuestion(int id, int questid) {
		// TODO Auto-generated method stub
		return false;
	}

}
