package payroll.test.java.payall;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import payroll.Employee;
import payroll.HoldMethod;
import payroll.PaymentClassification;
import payroll.PaymentMethod;
import payroll.PayrollDatabase;
import payroll.Transaction;
import payroll.classification.CommissionedClassification;
import payroll.classification.HourlyClassification;
import payroll.classification.SalariedClassification;
import payroll.exception.trans.AddCommissionedEmployeeTransaction;
import payroll.exception.trans.AddHourlyEmployeeTransaction;
import payroll.exception.trans.AddSalariedEmployeeTransaction;

public class AddEmployeeTest {
    @Test
    void testAddHourlyEmployee() {
        int empId = 1001;
        String name = "Bill";
        String address = "No 391, Huanghe Rd.";
        double hourlyRate = 8.0;

        Transaction t = new AddHourlyEmployeeTransaction(empId, name, address, hourlyRate);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals(empId, e.getEmpId());
        assertEquals(name, e.getName());
        assertEquals(address, e.getAddress());

        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        assertEquals(hourlyRate, hc.getHourlyRate());

        PaymentMethod pm = e.getPaymentMethod();
        assertTrue(pm instanceof HoldMethod);
    }
    

    @Test
    void testAddSalariedEmployee() {
        int empId = 1002;
        String name = "Bill";
        String address = "No 391, Huanghe Rd.";
        double salary = 3000.0;

        Transaction t = new AddSalariedEmployeeTransaction(empId, name, address, salary);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals(empId, e.getEmpId());
        assertEquals(name, e.getName());
        assertEquals(address, e.getAddress());
        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof SalariedClassification);
        SalariedClassification sc = (SalariedClassification) pc;
        assertEquals(salary, sc.getSalary() , 0.001);
        PaymentMethod pm = e.getPaymentMethod();
        assertTrue(pm instanceof HoldMethod);
    }

    //添加销售经理
    @Test
    void testAddCommissionedEmployee() {
        int empId = 1003;
        String name = "Bill";
        String address = "No 391, Huanghe Rd.";
        double salary = 2000.0;
        double commissionRate = 0.02;

        Transaction t = new AddCommissionedEmployeeTransaction(empId, name, address, salary, commissionRate);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNotNull(e);
        assertEquals(empId, e.getEmpId());
        assertEquals(name, e.getName());
        assertEquals(address, e.getAddress());
        PaymentClassification pc = e.getPaymentClassification();
        assertTrue(pc instanceof CommissionedClassification);
        CommissionedClassification cc = (CommissionedClassification) pc;
        assertEquals(salary, cc.getSalary(), 0.01);
        assertEquals(commissionRate, cc.getCommissionRate(), 0.01);
        PaymentMethod pm = e.getPaymentMethod();
        assertTrue(pm instanceof HoldMethod);

    }
}
