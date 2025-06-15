package payroll.test.java.payall;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import payroll.Employee;
import payroll.PaymentClassification;
import payroll.PayrollDatabase;
import payroll.Transaction;
import payroll.classification.CommissionedClassification;
import payroll.classification.SalesReceipt;
import payroll.exception.NotCommissionedClassificationException;
import payroll.exception.trans.AddCommissionedEmployeeTransaction;
import payroll.exception.trans.AddHourlyEmployeeTransaction;
import payroll.exception.trans.AddSalariedEmployeeTransaction;
import payroll.exception.trans.SalesReceiptTransaction;

public class SalesReceiptTests {

    //为销售经理登记一个销售凭条
    @Test
    public void testAddOneSalesReceiptToCommissionedEmployee() {
        int empId = 4001;
        new AddCommissionedEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 2000.0, 0.02).execute();

        String date = "2025-06-01";
        double amount = 1000.0;
        Transaction t = new SalesReceiptTransaction(empId, date, amount);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        SalesReceipt sr = cc.getSalesReceiptOfDate(date);
        assertEquals(date, sr.getDate());
        assertEquals(amount, sr.getAmount(),0.001);
    }

    //为销售经理登记多个销售凭条
    @Test
    public void testAddTwoSalesReceiptsToCommissionedEmployee() {
        int empId = 4002;
        new AddCommissionedEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 2000.0, 0.02).execute();

        String date1 = "2025-06-01";
        double amount1 = 1000.0;
        new SalesReceiptTransaction(empId, date1, amount1).execute();

        String date2 = "2025-06-02";
        double amount2 = 2000.0;
        new SalesReceiptTransaction(empId, date2, amount2).execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        SalesReceipt sr1 = cc.getSalesReceiptOfDate(date1);
        assertEquals(date1, sr1.getDate());
        assertEquals(amount1, sr1.getAmount(), 0.01);
        SalesReceipt sr2 = cc.getSalesReceiptOfDate(date2);
        assertEquals(date2, sr2.getDate());
        assertEquals(amount2, sr2.getAmount(), 0.01);
    }

    //为钟点工登记销售凭条，应抛出异常
    @Test
    public void testAddSalesReceiptToHourlyEmployee() {
        int empId = 4003;
        new AddHourlyEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 12.5).execute();

        assertThrows(NotCommissionedClassificationException.class, () -> {
            new SalesReceiptTransaction(empId,"2025-06-01",1000.0).execute();
        });
    }

    //weiyuexinyuangong
    @Test
    public void testAddSalesReceiptToSalariedEmployee() {
        int empId = 4003;
        new AddSalariedEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 3000.0).execute();

        assertThrows(NotCommissionedClassificationException.class, () -> {
            new SalesReceiptTransaction(empId, "2025-06-01", 1000.0).execute();
        });

    }

}
