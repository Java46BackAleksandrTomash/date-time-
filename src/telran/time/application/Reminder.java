package telran.time.application;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder {
	
	private static final long DEFAULT_AMOUNT_OF_TIME = 3600000;  // default in 1 hour
	private static ChronoUnit unit;

	public static void main(String[] args) {
		long [] reminderArgs;
		try {
			reminderArgs = getReminderArgs(args);
			getReminder(reminderArgs[0], reminderArgs[1]);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
	}

	private static long[] getReminderArgs(String[] args) throws Exception {
		long [] res = new long[2];
		if(args.length > 1) {
			unit = getUnit(args);
			res[0] = getInterval(args[0]);
			res[1] = args.length > 2 ? getAmountOfTime(args[2], res[0]) : DEFAULT_AMOUNT_OF_TIME;
		} else {
			throw new Exception("mandatory arguments are not set (interval, unit)");
		}
		return res;
	}

	private static ChronoUnit getUnit(String[] args) throws Exception {
		try {
			return ChronoUnit.valueOf(args[1].toUpperCase());			
		} catch (Exception e) {
			throw new Exception("incorrect name of the time unit - " + args[1]);
		}
	}	

	private static long getInterval(String intervalStr) throws Exception {
		try {
			long interval = getValueInMilliseconds(Integer.parseInt(intervalStr));
			if(interval < 0) {
				throw new Exception("negative interval");
			}
			return interval;
		} catch (NumberFormatException e) {
			throw new Exception("interval is not a number");
		}
	}	

	private static long getAmountOfTime(String endStr, long interval) throws Exception {
		try {
			long amountOfTime = getValueInMilliseconds(Integer.parseInt(endStr));
			if(amountOfTime < 0) {
				throw new Exception("negative amount of time");
			}
			if(amountOfTime < interval) {
				throw new Exception("amount of time should be greater than the interval");
			}
			return amountOfTime;
		} catch (NumberFormatException e) {
			throw new Exception("amount of time is not a number");
		}
	}
	
	private static long getValueInMilliseconds(long value) {
		return ChronoUnit.MILLIS.equals(unit) ? value : Duration.of(value, unit).toMillis(); 
	}

	private static void getReminder(long interval, long amountOfTime) {
		Timer timer = new Timer();
		timer.schedule(
				new TimerTask() {
					long numberOfWholeTimes = amountOfTime / interval;
					@Override
			        public void run() {      
			    		System.out.println("\007\007\007");
			    		numberOfWholeTimes--;
			    		if (numberOfWholeTimes <= 0) {
			    			timer.cancel();
			    		}
			        }
			    },
				interval, interval);
	}

}
/////////////////////////Granovskiy//////////////////////////////////
/*

public class Reminder {
	 static final long DEFAULT_BEEPS_DURATION = 3_600_000; //in milliseconds
	static long intervalOfBeeps;
	static ChronoUnit unit;
	static long beepsDuration;
	
	public static void main(String[] args) {
		//mandatory args[0] interval value
		//mandatory args[1] ChronoUnit enum string value (case insensitive)
		//optional args[2] when ended in the given ChronoUnit (see args[1]), default in 1 hour
		//beep System.out.println("\007\007\007") - will sound only on real console
		// example of launching: java -jar reminder.jar 10 seconds 100  -
		// each 10 seconds during 100 seconds there should be beeps
		try {
			setIntervalOfBeeps(args);
			setUnit(args);
			intervalToMillis();
			setBeepsDuration(args);
			runBeeps();
		}catch(RuntimeException e) {
			e.printStackTrace();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void runBeeps() {
		Instant start = Instant.now();
		
		do {
			waitingFor(intervalOfBeeps);
			System.out.println("\007\007\007");
		}while(ChronoUnit.MILLIS.between(start, Instant.now()) < beepsDuration);
		
	}

	private static void waitingFor(long periodInMillis) {
		Instant start = Instant.now();
		do {
			
		}while(ChronoUnit.MILLIS.between(start, Instant.now()) < periodInMillis);
		
	}

	private static void setBeepsDuration(String[] args) throws Exception{
		if (args.length < 3) {
			beepsDuration = DEFAULT_BEEPS_DURATION; //one hour to milliseconds
		} else {
			try {
				beepsDuration = Long.parseLong(args[2]);
			} catch (NumberFormatException e) {
				throw new Exception("Beeps duration should be a number");
			}
			if (beepsDuration < 0) {
				throw new Exception("Beeps duration can't be a negative number");
			}
			beepsDuration = Duration.of(beepsDuration, unit).toMillis();
		}
		
	}

	private static void intervalToMillis() {
		intervalOfBeeps = Duration.of(intervalOfBeeps, unit).toMillis();//conversion to milliseconds from unit
		
	}

	private static void setUnit(String[] args) throws Exception {
		if (args.length < 2) {
			throw new Exception("Chrono Unit should be specified as the second argument");
		}
		try {
			unit = ChronoUnit.valueOf(args[1].toUpperCase());
			
		} catch (IllegalArgumentException e) {
			throw new Exception("Wrong Chrono Unit");
		}
		
	}

	private static void setIntervalOfBeeps(String[] args) throws Exception {
		if (args.length == 0) {
			throw new Exception("Interval between beeps should be specified as the first argument");
		}
		try {
			intervalOfBeeps = Long.parseLong(args[0]);
		}catch (NumberFormatException e) {
			throw new Exception("Interval between beeps should be a number");
		}
		if (intervalOfBeeps < 0) {
			throw new Exception("interval can't be a negative number");
		}
		
	}

}
*/





