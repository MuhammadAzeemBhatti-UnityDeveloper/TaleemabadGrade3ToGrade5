package com.orenda.taimo.grade3tograde5.Models;

public class MathsAdditionModel {
    int operand1;
    int operand2;
    int ans;
    char operator;

    public MathsAdditionModel(int operand1, int operand2, int ans, char operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.ans = ans;
        this.operator = operator;
    }

    public int getOperand1() {
        return operand1;
    }

    public void setOperand1(int operand1) {
        this.operand1 = operand1;
    }

    public int getOperand2() {
        return operand2;
    }

    public void setOperand2(int operand2) {
        this.operand2 = operand2;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public char getOperator() {
        return operator;
    }

    public void setOperator(char operator) {
        this.operator = operator;
    }
}
