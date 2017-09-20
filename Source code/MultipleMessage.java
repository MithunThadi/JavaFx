package test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datastax.driver.core.Session;

public class MultipleMessage implements Runnable {
	private List fileList;
	private static Map<String, String> metadata1;
	private String key, value;
	private WatchService watcher;

	public MultipleMessage(List str, Map metadata, WatchService watcher) {
		this.watcher = watcher;
		this.metadata1 = metadata;
		this.fileList = str;
	}

	@Override
	public void run() {

		// System.out.println("this si multiplemessage");
		WatchKey key = (WatchKey) fileList.get(0);
		WatchEvent event = (WatchEvent) fileList.get(1);

		WatchEvent.Kind kind = event.kind();

		@SuppressWarnings("unchecked")
		WatchEvent<Path> ev = (WatchEvent<Path>) event;
		// Path fileName = ev.context();
		Path filePath = (Path) key.watchable();
		try {
			if ("deleted".equalsIgnoreCase(DBOperations.DBMonitorCheck(DBOperations.connectCassandra(), metadata1.get("monitorName")))){
				DBOperations.deletingThread(DBOperations.connectCassandra(), metadata1.get("monitorName"));
				watcher.close();
			}
		} catch (NoSuchFieldException | SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String fileName = ev
//				.context()
//				.toString()
//				.replace(metadata1.get("triggerPattern").replace("*", ""),
//						metadata1.get("triggerPattern").replace("*", ""));
		String fileName = ev
				.context()
				.toString()
				.replace(metadata1.get("sourceTriggerPattern").replace("*", ""),metadata1.get("sourceFilePattern").replace("*", ""));
		
		String sFilePath = filePath + "\\" + fileName;
		System.out.println(filePath + ",  " + kind.name() + ": " + fileName);
		System.out.println("sfilePath is " + sFilePath);
		Session session=DBOperations.connectCassandra();
		
		Map<String,String> transferMetaData=new HashMap();
		String transferId=UniqueIDTest.generate();
		//transferMetaData.put("", transferId)
		transferMetaData.put("transferId", transferId);
		transferMetaData.put("sourceFile", sFilePath);
		DBOperations.transferDetails(session, metadata1,transferMetaData);
		// insert data base transfer_id in transfer_event
		DBOperations.transferEventDetails(session,metadata1,transferMetaData);
		FilesPublisher2Kafka.getMessages(sFilePath,metadata1,transferMetaData);

		System.out.println(kind.name() + ": " + fileName);

	}

}
