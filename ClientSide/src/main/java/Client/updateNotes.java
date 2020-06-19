package Client;

import CloneEntities.CloneStudentTest;

public class updateNotes {
	
    private String NotesToUpdate;
    private CloneStudentTest st;
    
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
}
