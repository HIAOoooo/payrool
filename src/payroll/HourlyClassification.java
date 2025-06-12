package payroll;

import java.util.LinkedHashMap;
import java.util.Map;

public class HourlyClassification implements PaymentClassification {
    private double hourlyRate;
    private Map<String, TimeCard> timeCards = new LinkedHashMap<>();

    public HourlyClassification(double hourlyRate) {
        this.hourlyRate = hourlyRate;     
    }

    public double getHourlyRate() {
       return hourlyRate;
    }

    public TimeCard getTimeCardOfDate(String date) {
        return timeCards.get(date);
    }

    public void addTimeCard(TimeCard tc) {
        timeCards.put(tc.getDate(), tc);
    }

}
