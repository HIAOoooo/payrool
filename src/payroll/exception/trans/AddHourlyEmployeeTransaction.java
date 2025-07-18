package payroll.exception.trans;

import payroll.PaymentClassification;
import payroll.classification.HourlyClassification;

public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {

    private double hourlyRate;

    public AddHourlyEmployeeTransaction(int empId, String name, String address, double hourlyRate) {
        super(empId, name, address);
        this.hourlyRate = hourlyRate;
    }

    @Override
    protected PaymentClassification getPaymentClassification() {
        return new HourlyClassification(hourlyRate);
    }

}
