package payroll;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

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
}
