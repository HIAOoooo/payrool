package payroll;

public class AddHourlyEmployeeTransaction implements Transaction {

    private int empId;
    private String name;
    private String address;
    private double hourlyRate;

    public AddHourlyEmployeeTransaction(int empId, String name, String address, double hourlyRate) {
        this.empId = empId;
        this.name = name;
        this.address = address;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public void execute() {
        Employee e = new Employee(empId, name, address);
        e.setPaymentClassification(new HourlyClassification(hourlyRate));
        e.setPaymentMethod(new HoldMethod());
        PayrollDatabase.saveEmployee(e);
    }

}
