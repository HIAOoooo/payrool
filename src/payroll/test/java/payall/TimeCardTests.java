package payroll.test.java.payall;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import payroll.Employee;
import payroll.NoSuchEmployeeException;
import payroll.PaymentClassification;
import payroll.PayrollDatabase;
import payroll.Transaction;
import payroll.classification.HourlyClassification;
import payroll.classification.TimeCard;
import payroll.exception.NotHourlyClassificationException;
import payroll.exception.trans.AddCommissionedEmployeeTransaction;
import payroll.exception.trans.AddHourlyEmployeeTransaction;
import payroll.exception.trans.AddSalariedEmployeeTransaction;
import payroll.exception.trans.TimeCardTransaction;

public class TimeCardTests {
    
    //登记时间卡
    //TimeCard empid date hours
    //测试1:为钟点工登记一个时间卡
    @Test
    void testAddOneTimeCardToHourlyEmployee() {
        int empId = 3001;
        new AddHourlyEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 12.5).execute();

        String date = "2025-6-1";
        double hours = 7.5;
        Transaction t = new TimeCardTransaction(empId, date, hours);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals(empId, e.getEmpId());
        PaymentClassification pc = e.getPaymentClassification();
        org.junit.Assert.assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        TimeCard tc = hc.getTimeCardOfDate(date);
        assertNotNull(tc);
        assertEquals(date, tc.getDate());
        assertEquals(hours, tc.getHours(), 0.001);
    }

    //测试2：登记多个时间卡
    @Test
    void testAddTwoTimeCardsToHourlyEmployee() {
        int empId = 3002;
        new AddHourlyEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 12.5).execute();
        String date1 = "2025-6-1";
        double hours1 = 5.5;
        new TimeCardTransaction(empId, date1, hours1).execute();

        String date2 = "2025-6-2";
        double hours2 = 10;
        new TimeCardTransaction(empId, date2, hours2).execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        PaymentClassification pc = e.getPaymentClassification();
        HourlyClassification hc = (HourlyClassification) pc;

        TimeCard tc1 = hc.getTimeCardOfDate(date1);
        assertEquals(date1, tc1.getDate());
        assertEquals(hours1, tc1.getHours(), 0.01);

        TimeCard tc2 = hc.getTimeCardOfDate(date2);
        assertEquals(date2, tc2.getDate());
        assertEquals(hours2, tc2.getHours(), 0.001);
    }

    //测试3：为月薪雇员登记时间卡，应该抛出异常
    @Test
    void testAddTimeCardToSalariedEmployee() {
        int empId = 3003;
        new AddSalariedEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 3000.0).execute();

        assertThrows(NotHourlyClassificationException.class, () -> {
            new TimeCardTransaction(empId, "2025-6-1", 5.5).execute();
        });
    }

    //测试4
    @Test
    void testAddTimeCardToCommissionedEmployee() {
        int empId = 3004;
        new AddCommissionedEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 2000.0, 0.02).execute();

        assertThrows(NotHourlyClassificationException.class, () -> {
            new TimeCardTransaction(empId, "2025-6-1", 5.5).execute();
        });
    }

    //测试5
    @Test
    void testAddTimeCardToEmployeeNotExists() {
        int empId = 300500;
        assertNull(PayrollDatabase.getEmployee(empId));

        assertThrows(NoSuchEmployeeException.class, () -> {
            new TimeCardTransaction(empId, "2025-6-1", 5.5).execute();
        });
    }




}
