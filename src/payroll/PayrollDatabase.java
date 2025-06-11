package payroll;

import java.util.HashMap;
import java.util.Map;

public class PayrollDatabase {

    private static Map<Integer, Employee> employees = new HashMap<>();

    public static Employee getEmployee(int empId) {
        return employees.get(empId);
    }

    public static void saveEmployee(Employee e) {
        employees.put(e.getEmpId(),e);
    }

}
