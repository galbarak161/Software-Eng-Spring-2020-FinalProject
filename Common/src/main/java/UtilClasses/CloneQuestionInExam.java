package UtilClasses;

import CloneEntities.CloneExam;
import CloneEntities.CloneQuestion;

public class CloneQuestionInExam {

	private int id;
	
	private int pointsForQuestion;

	private CloneQuestion question;

	public CloneQuestionInExam(int pointsForQuestion, CloneQuestion question) {
		this.pointsForQuestion = pointsForQuestion;
		this.question = question;
	}

	public int getId() {
		return id;
	}


	public int getPointsForQuestion() {
		return pointsForQuestion;
	}



	public CloneQuestion getQuestion() {
		return question;
	}



}