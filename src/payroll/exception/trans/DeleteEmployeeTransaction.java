package payroll.exception.trans;

import payroll.Employee;
import payroll.NoSuchEmployeeException;
import payroll.PayrollDatabase;
import payroll.Transaction;

public class DeleteEmployeeTransaction implements Transaction {

    private int empId;

    public DeleteEmployeeTransaction(int empId) {
        this.empId = empId;
    }

    @Override
    public void execute() {
        Employee e = PayrollDatabase.getEmployee(empId);
        if (e != null) {
            PayrollDatabase.deleteEmployee(empId);
        } else {
            throw new NoSuchEmployeeException();
        }
        
    }

}
