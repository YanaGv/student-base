package model;

import java.util.ArrayList;

public class Student {
    private String lastName;
    private String firstName;
    private String middleName;
    private int group;
    private ArrayList<Integer> workInTerm;
    private int allWork = 0;

    public Student(String lastName, String firstName, String middleName, int group, ArrayList<Integer> workInTerm) {
        setFullName(lastName, firstName, middleName);
        this.group = group;
        this.workInTerm = workInTerm;
        for(int work: workInTerm) allWork+=work;
    }
    public String getFullName(){
        return lastName+" "+firstName+" "+middleName;
    }
    public String getLastName(){ return lastName; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public int getGroup(){
        return group;
    }
    public int getAllWork() { return allWork; }
    public ArrayList<Integer> getWorkInTerm(){
        return workInTerm;
    }

    public void setFullName(String lastName, String firstName, String middleName){
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    public void setGroup(int group){
        this.group = group;
    }
    public void setWorkInTerm(ArrayList<Integer> workInTerm){
        this.workInTerm = workInTerm;
    }
}
