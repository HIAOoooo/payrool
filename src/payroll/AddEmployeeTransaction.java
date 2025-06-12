package payroll;

public abstract class AddEmployeeTransaction implements Transaction{
    private int empId;
    private String name;
    private String address;

    public AddEmployeeTransaction(int empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }

    @Override
    public void execute() {
        Employee e = new Employee(empId, name, address);
        e.setPaymentClassification(getPaymentClassification());
        e.setPaymentMethod(new HoldMethod());
        PayrollDatabase.saveEmployee(e);
    }

    protected abstract PaymentClassification getPaymentClassification();
}
