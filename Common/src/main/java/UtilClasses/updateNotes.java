package UtilClasses;

import java.io.Serializable;

import CloneEntities.CloneStudentTest;

public class updateNotes implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -5157389064812196209L;
	private String NotesToUpdate;
    private CloneStudentTest st;
    private int grade;
    
    public updateNotes(String NotesToUpdate, CloneStudentTest st) {
        this.NotesToUpdate = NotesToUpdate;
        this.st = st;
    }
    
    public String getNotesToUpdate() {
        return NotesToUpdate;
    }
    
    public CloneStudentTest getStudentTest() {
        return st;
    }

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}
}
