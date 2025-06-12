package payroll;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

public class DeleteEmployeeTests {

    //删除雇员（雇员已存在）
    @Test
    public void testDeleteEmployeetxists() {
        int empId = 2001;
        new AddHourlyEmployeeTransaction(empId, "Bill", "No 391, Huanghe Rd.", 12.5).execute();

        Transaction t = new DeleteEmployeeTransaction(empId);
        t.execute();

        Employee e = PayrollDatabase.getEmployee(empId);
        assertNull(e);

    }

    //删除雇员（雇员不存在）
    @Test
    public void testDeleteEmployeeNotExists() {
        int empId = 200200;
        Employee e = PayrollDatabase.getEmployee(empId);
        assertNull(e);

        assertThrows(NoSuchEmployeeException.class, () -> {
            new DeleteEmployeeTransaction(empId).execute();    
        });

    }

}
