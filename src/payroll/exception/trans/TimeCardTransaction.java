package payroll.exception.trans;

import payroll.Employee;
import payroll.NoSuchEmployeeException;
import payroll.PaymentClassification;
import payroll.PayrollDatabase;
import payroll.Transaction;
import payroll.classification.HourlyClassification;
import payroll.classification.TimeCard;
import payroll.exception.NotHourlyClassificationException;

public class TimeCardTransaction implements Transaction {

    private int empId;
    private String date;
    private double hours;

    public TimeCardTransaction(int empId, String date, double hours) {
        this.empId = empId;
        this.date = date;
        this.hours = hours;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        if(e == null)
            throw new NoSuchEmployeeException();
        PaymentClassification pc = e.getPaymentClassification();
        if (pc instanceof HourlyClassification) {
            HourlyClassification hc = (HourlyClassification) pc;
            TimeCard tc = new TimeCard(date, hours);
            hc.addTimeCard(tc);
        } else {
            throw new NotHourlyClassificationException();
        }
        
    }

}
