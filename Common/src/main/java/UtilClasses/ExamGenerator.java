package UtilClasses;

import java.io.Serializable;
import java.util.List;

import CloneEntities.CloneExam;
import CloneEntities.CloneQuestion;

public class ExamGenerator implements Serializable {

	private static final long serialVersionUID = -977546118714483831L;

	CloneExam exam;
	List<CloneQuestion> questions;
	List<Integer> questionsPoint;

	public ExamGenerator(CloneExam exam, List<CloneQuestion> questions, List<Integer> questionsPoint) {
		this.exam = exam;
		this.questions = questions;
		this.questionsPoint = questionsPoint;
	}

	public CloneExam getExam() {
		return exam;
	}

	public void setExam(CloneExam exam) {
		this.exam = exam;
	}

	public List<CloneQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<CloneQuestion> questions) {
		this.questions = questions;
	}

	public List<Integer> getQuestionsPoint() {
		return questionsPoint;
	}

	public void setQuestionsPoint(List<Integer> questionsPoint) {
		this.questionsPoint = questionsPoint;
	}
	
	

	
}
