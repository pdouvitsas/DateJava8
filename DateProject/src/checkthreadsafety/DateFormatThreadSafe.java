package checkthreadsafety;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DateFormatThreadSafe {
	public static void main(String[] args) throws Exception {

	    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");;

	    Callable<LocalDate> task = new Callable<LocalDate>(){
	        public LocalDate call() throws Exception {
	            return LocalDate.parse("01/02/2018", formatter);
	        }
	    };

	    //create pool with 10 threads
	    ExecutorService executorService = Executors.newFixedThreadPool(10);
	    List<Future<LocalDate>> results = new ArrayList<Future<LocalDate>>();

	   
	    for(int i = 0 ; i < 20 ; i++){
	        results.add(executorService.submit(task));
	    }
	    executorService.shutdown();
	    
	    results
	    .stream()
	    .map(i -> toDate(i))
	    .forEach(System.out::println);
	    
	   
	}
	
	private static LocalDate toDate(Future<LocalDate> date) {
		try {
	        return date.get();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
}
