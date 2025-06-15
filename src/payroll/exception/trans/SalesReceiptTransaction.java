package payroll.exception.trans;

import payroll.Employee;
import payroll.PaymentClassification;
import payroll.PayrollDatabase;
import payroll.Transaction;
import payroll.classification.CommissionedClassification;
import payroll.classification.SalesReceipt;
import payroll.exception.NotCommissionedClassificationException;

public class SalesReceiptTransaction implements Transaction {

    private int empId;
    private String date;
    private double amount;

    public SalesReceiptTransaction(int empId, String date, double amount) {
        this.empId = empId;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        PaymentClassification pc = e.getPaymentClassification();
        if (pc instanceof CommissionedClassification) {
            CommissionedClassification cc = (CommissionedClassification) pc;
            SalesReceipt sr = new SalesReceipt(date, amount);
            cc.addSalesReceipt(sr);
        } else {
            throw new NotCommissionedClassificationException();
        }
        
    }

}
