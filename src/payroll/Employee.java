package payroll;

public class Employee {

    private int empId;
    private String name;
    private String address;
    private PaymentClassification paymentClassification;
    private PaymentMethod paymentMethod;


    public Employee(int empId, String name, String address) {
        this.empId = empId;
        this.name = name;
        this.address = address;
    }

    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
  
    public PaymentClassification getPaymentClassification() {
        return paymentClassification;
    }

        public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentClassification(PaymentClassification paymentClassification) {
        this.paymentClassification = paymentClassification;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
