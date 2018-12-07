package checkthreadsafety;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DateFormatNotThreadSafeSingleThread {
	public static void main(String[] args) throws Exception {

	    final DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

	    Callable<Date> task = new Callable<Date>(){
	        public Date call() throws Exception {
	            return format.parse("01/02/2018");
	        }
	    };

	    //create pool with 10 threads
	    ExecutorService executorService = Executors.newFixedThreadPool(1);
	    List<Future<Date>> results = new ArrayList<Future<Date>>();

	   
	    for(int i = 0 ; i < 20 ; i++){
	        results.add(executorService.submit(task));
	    }
	    executorService.shutdown();
	    
	    results
	    .stream()
	    .map(i -> toDate(i))
	    .forEach(System.out::println);
	    
	   
	}
	
	private static Date toDate(Future<Date> date) {
		try {
	        return date.get();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
}
