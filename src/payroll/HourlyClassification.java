package payroll;

public class HourlyClassification implements PaymentClassification {
    private double hourlyRate;

    public HourlyClassification(double hourlyRate) {
        this.hourlyRate = hourlyRate;     
    }

    public double getHourlyRate() {
       return hourlyRate;
    }

}
