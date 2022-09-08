package telran.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;

public class WorkingDaysAdjuster implements TemporalAdjuster {

int[] daysOff;
int nDays;
public int[] getDaysOff() {
	return daysOff;
}
public void setDaysOff(int[] daysOff) {
	this.daysOff = daysOff;
}
public int getnDays() {
	return nDays;
}
public void setnDays(int nDays) {
	this.nDays = nDays;
}
public WorkingDaysAdjuster(int[] daysOff, int nDays) {
	
	this.daysOff = daysOff;
	this.nDays = nDays;
}
public WorkingDaysAdjuster() {
}
	@Override
	public Temporal adjustInto(Temporal temporal) {
		if (daysOff == null || daysOff.length == 0) {	// all days are "working"
			return temporal.plus(nDays, ChronoUnit.DAYS);
		}
		if (daysOff.length == 7) {	// all days are "off"
			return temporal; // due to test requirement
		}
		
		int workingDaysInWeek = 7 - daysOff.length;
		Temporal result = temporal.plus(nDays / workingDaysInWeek, ChronoUnit.WEEKS);
		int remainedDays = nDays % workingDaysInWeek; // no more than 5 days 
		do {
			while(isDayOff(result)) {
				result = result.plus(1, ChronoUnit.DAYS);
			}
			result = result.plus(1, ChronoUnit.DAYS);
		}
		while(--remainedDays > 0) ;
		return result;
		
	}

	// Actually performs n=daysOff.length (from 1 to 6) comparisons 6-n times. 
	// This is 1*5 or 2*4 or 3*3 - no more that 9 comparisons totally.
	// That's why "lookup table" or "binary search" optimisation is not required here.
	private boolean isDayOff(Temporal date) {
		return Arrays.stream(daysOff).anyMatch(n->n==ChronoField.DAY_OF_WEEK.getFrom(date));
	}
}
