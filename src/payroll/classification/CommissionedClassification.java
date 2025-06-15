package payroll.classification;

import java.util.LinkedHashMap;
import java.util.Map;

import payroll.PaymentClassification;

public class CommissionedClassification implements PaymentClassification {

    private double salary;
    private double commissionRate;
    private Map<String, SalesReceipt> salesReceipts = new LinkedHashMap<>();

    public CommissionedClassification(double salary, double commissionRate) {
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    public double getSalary() {
        return salary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    public SalesReceipt getSalesReceiptOfDate(String date) {
        return salesReceipts.get(date);
    }

    public void addSalesReceipt(SalesReceipt sr) {
        salesReceipts.put(sr.getDate(), sr);
    }

}
