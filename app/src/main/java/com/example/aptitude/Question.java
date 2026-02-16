package com.example.aptitude;

import java.io.Serializable;

public class Question implements Serializable {
    private String questionText;
    private String optA, optB, optC, optD;
    private int correctAnsNo; // 1, 2, 3, or 4
    private int userSelectedOption = -1;

    public Question(String q, String a, String b, String c, String d, int ans) {
        this.questionText = q;
        this.optA = a; this.optB = b; this.optC = c; this.optD = d;
        this.correctAnsNo = ans;
    }

    // Getters
    public String getQuestionText() { return questionText; }
    public String getOptA() { return optA; }
    public String getOptB() { return optB; }
    public String getOptC() { return optC; }
    public String getOptD() { return optD; }
    public int getCorrectAnsNo() { return correctAnsNo; }
    public int getUserSelectedOption() { return userSelectedOption; }
    public void setUserSelectedOption(int userSelectedOption) { this.userSelectedOption = userSelectedOption; }
}
