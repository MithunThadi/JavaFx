package test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This program demonstrates how to use the Watch Service API to monitor change
 * events for a specific directory.
 * 
 * @author Prakash
 *
 */
public class MultipleSourceWatcher implements Runnable {

	public static String sourceDir1;
	public static Map<String, String> metadata1;
	public static String file;

	MultipleSourceWatcher(String sourceDir, String ThreadName,
			Map<String, String> metadata) {
		this.file = ThreadName;
		this.sourceDir1 = sourceDir;
		this.metadata1 = metadata;
	}

	@SuppressWarnings("deprecation")
	public static void watcher(String sourceDir, String ThreadName,
			Map<String, String> metadata) throws InterruptedException {
		sourceDir1=sourceDir;
		metadata1=metadata;
		MultipleSourceWatcher m = new MultipleSourceWatcher(sourceDir1, file, metadata1);
		//MultipleSourceWatcher m=new MultipleSourceWatcher();
		Thread t = new Thread(m);
		t.setName(ThreadName);
	//while(true) {
		t.start();
	     //t.sleep(30000);
		if (t.isAlive()) {
			DBOperations.starting(DBOperations.connectCassandra(), ThreadName);	
			System.out.println(ThreadName + " Created and running"
					+ metadata.get("sourceTriggerPattern"));}
	//}
	}

	@Override
	public void run() {

		ExecutorService executor = Executors.newFixedThreadPool(1);
		//for (int i = 0; i < list.size(); i++) {

			Runnable worker = new SourceRegisterer( sourceDir1,metadata1);
			executor.execute(worker);
		//}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");
	}

	// public static void deleteThread(Thread thread) {
	// boolean status = thread.currentThread().isAlive();
	// if (status == true) {
	// thread.currentThread().interrupt();
	// thread.currentThread().destroy();
	// }
	// }
}
