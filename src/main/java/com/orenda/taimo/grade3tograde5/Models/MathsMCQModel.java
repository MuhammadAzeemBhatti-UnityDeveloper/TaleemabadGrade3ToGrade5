package com.orenda.taimo.grade3tograde5.Models;

public class MathsMCQModel {

    String operand1;
    String operand2;
    String operator;
    String answer;
    String option;

    public MathsMCQModel(String operand1, String operand2, String operator, String answer, String option) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
        this.answer = answer;
        this.option = option;
    }

    public String getOperand1() {
        return operand1;
    }

    public void setOperand1(String operand1) {
        this.operand1 = operand1;
    }

    public String getOperand2() {
        return operand2;
    }

    public void setOperand2(String operand2) {
        this.operand2 = operand2;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
