package payroll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

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
        assertTrue(pc instanceof HourlyClassification);
        HourlyClassification hc = (HourlyClassification) pc;
        TimeCard tc = hc.getTimeCardOfDate(date);
        assertNotNull(tc);
        assertEquals(date, tc.getDate());
        assertEquals(hours, tc.getHours(), 0.001);
        
    }
}
