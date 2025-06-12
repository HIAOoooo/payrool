package payroll;

public class AddSalariedEmployeeTransaction extends AddEmployeeTransaction {

    private double salary;

    public AddSalariedEmployeeTransaction(int empId, String name, String address, double salary) {
        super(empId, name, address);
        this.salary = salary;
    }

    @Override
    protected PaymentClassification getPaymentClassification() {
        return new SalariedClassification(salary);
    }

}
