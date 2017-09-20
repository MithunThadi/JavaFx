package test;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SourceRegisterer implements Runnable {

	public static String command;
	public static Map<String, String> metaDataMap;

	public SourceRegisterer(String str, Map metadata) {
		this.command = str;
		this.metaDataMap = metadata;
	}

	@Override
	public void run() {
		// connect to Db check status
		try {
			// String sourceTriggerPattern =
			// metadata.get("sourceTriggerPattern");
			// Map<String, String> entry = new HashMap();

			// entry.entrySet()= metadata.entrySet();
			// if (entry.getKey() == "sourceTriggerPattern")
			// sourceTriggerPattern = entry.getValue();
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path monitoredDirectory = Paths.get(command);
			// System.out.println(sourceTriggerPattern);
			monitoredDirectory.register(watcher, ENTRY_CREATE, ENTRY_MODIFY);

			System.out.println("Watch Service registered for dir: "
					+ monitoredDirectory.getFileName());
			boolean Monitor_status = true;
			while (Monitor_status) {
				WatchKey key;
				// metadata.get("")
				// Set entry = metadata.entrySet();
				// System.out.println(value);
				DBOperations.started(DBOperations.connectCassandra(), metaDataMap.get("monitorName"));
				List fileList = new LinkedList<String>();
				try {
					key = watcher.take();
					//key.wait(30000);
					
					List keyList = key.pollEvents();
					//trigger pattern checker
					WatchEvent event;
					fileList.add(key);
					ExecutorService executor=null;
					
				 
					for (int i = 0; i < keyList.size(); i++) {
						event = (WatchEvent) keyList.get(i);
						if(event!=null) {
						fileList.add(keyList.get(i));
						
						executor = Executors
								.newFixedThreadPool(keyList.size());
						//executor.wait(10000);
						if ((event.context()).toString().contains(
								metaDataMap.get("sourceTriggerPattern").replace(
										"*", ""))) {
							Runnable worker = new MultipleMessage(fileList,
									metaDataMap,watcher);
							// System.out.println(value);
							System.out.println("this is source register");
							// System.out.println(value.replace("*", ""));
							executor.execute(worker);
							
						}
					
					}
						//watcher.close();
					executor.shutdown();
//					while (!executor.isTerminated()) {
//					}
					System.out.println("Finished all threads");
					boolean valid = key.reset();
					if (!valid) {
						break;
					}
					}
				} catch (InterruptedException ex) {
					return;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
