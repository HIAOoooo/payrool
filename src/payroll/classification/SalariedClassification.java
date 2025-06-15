package payroll.classification;



import payroll.PaymentClassification;

public class SalariedClassification implements PaymentClassification {

    private double salary;
    private double commissionRate;


    public SalariedClassification(double salary, double commissionRate) {
		this.salary = salary;
        this.commissionRate = commissionRate;
	}

	public double getSalary() {
        return salary;
    }

    public double getCommissionRate() {
        return commissionRate;
    }




}
