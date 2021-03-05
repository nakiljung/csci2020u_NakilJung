package sample;

public class StudentRecord {
    private String studentID;
    private float assignment;
    private float midterm;
    private  float finalExam;
    private float finalMark;
    private String letterGrade;

    public StudentRecord(String studentID, float assignment, float midterm, float finalExam){
        this.studentID = studentID;
        this.assignment = assignment;
        this.midterm = midterm;
        this.finalExam = finalExam;
        this.finalMark = (float) (this.assignment *.20 + this.midterm*.30 + this.finalExam*.50);

        if (finalMark >= 80){
            this.letterGrade = "A";
        }else if (finalMark >= 70){
            this.letterGrade = "B";
        }else if (finalMark >= 60){
            this.letterGrade = "C";
        }else if (finalMark >= 50){
            this.letterGrade = "D";
        }else{
            this.letterGrade = "F";
        }
    }

    public String getStudentID() {
        return this.studentID;
    }

    public float getAssignment() {
        return this.assignment;
    }

    public float getMidterm() {
        return this.midterm;
    }
    public float getFinalExam() {
        return this.finalExam;
    }

    public float getFinalMark() {
        return this.finalMark;
    }

    public String getLetterGrade() {
        return this.letterGrade;
    }

}
