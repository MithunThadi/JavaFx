package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import kafka.admin.AdminUtils;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;

import org.I0Itec.zkclient.ZkClient;

import com.datastax.driver.core.Session;

public class FilesPublisher2Kafka {
	// private final static byte PART_SIZE = 120;
	public static Map<String,String> transferMetaData1;
	//public static Map<String,String> metaData1;
	private final static int PART_SIZE = 4 * 1024 * 1024;
	static ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000,
			ZKStringSerializer$.MODULE$);
	static ConsumerConnector consumerConnector = null;
	public static Session session1;
	public static int publishCount=0;
	public static int consumerCount=0;
	public static void publish(String TOPIC, String Key, String Message, Map<String, String> metadata) {
		// System.out.println("Admin utils :::::;"
		// + AdminUtils.topicExists(zkClient, TOPIC));
		//transferMetaData1=metadata;
		//int publishCount=0;
		boolean b = false;
		if (AdminUtils.topicExists(zkClient, TOPIC) == b) {
			createTopic(TOPIC);
		}
		// createTopic(TOPIC);
		String Status = "published succesfully";
		Properties ppts = new Properties();
		//ppts.put("metadata.broker.list", "localhost:9092");
		ppts.put("metadata.broker.list", "localhost:9092,localhost:9093,localhost:9094");
		ppts.put("serializer.class", "kafka.serializer.StringEncoder");
		ppts.put("reconnect.backoff.ms", "50");
		ppts.put("retry.backoff.ms", "100");
		ppts.put("producer.type", "async");
		// ppts.put("log.retention.minutes", 2);
		// ppts.put("auto.offset.reset", "smallest");
		ppts.put("message.max.bytes", "1073741824");
		// replica.fetch.max.bytes=15728640
		// ppts.put("replica.fetch.max.bytes", "1073741824");
		ProducerConfig producerConfig = new ProducerConfig(ppts);
		kafka.javaapi.producer.Producer<String, String> producer = new kafka.javaapi.producer.Producer<String, String>(
				producerConfig);
		KeyedMessage<String, String> message = new KeyedMessage<String, String>(TOPIC, Key, Message);
		producer.send(message);
		publishCount++;
		String incrementPublish=Integer.toString(publishCount);
//		data base code to update  publishCount in transfer_event
		transferMetaData1.put("incrementPublish", incrementPublish);
		Session session=DBOperations.connectCassandra();
		session1=session;
		DBOperations.updateTransferEventPublishDetails(session,transferMetaData1);
		// System.out.println(message);
		producer.close();
		consume(TOPIC,metadata);
		//Session session=DBOperations.connectCassandra();
		DBOperations.updateTransferDetails(session,transferMetaData1,metadata);
		System.out.println("Consumed Successfullly");
	}
	public static void consume(String TOPIC, Map<String, String> metadata)
	{
		String topic = TOPIC;
		//int consumerCount=0;
		Properties props = new Properties();
		props.put("zookeeper.connect", "localhost:2181");
		//String id=UUID.randomUUID().toString().replace("-","");
		props.put("group.id","testgroup" );
		// props.put("bootstrap.servers", "localhost:9093");
		// props.put("group.id", "testgroup");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "smallest");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer",
				"org.apache.kafka.common.serialization.StringDeserializer");
		// props.put("auto.offset.reset", "smallest");
		props.put("fetch.message.max.bytes", "1073741824");
		// props.put("fetch.message.max.bytes", "52428800");
		ConsumerConfig conConfig = new ConsumerConfig(props);
		consumerConnector = Consumer.createJavaConsumerConnector(conConfig);
		Map<String, Integer> topicCount = new HashMap<String, Integer>();
		topicCount.put(topic, new Integer(1));
		// ConsumerConnector creates the message stream for each topic
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumerConnector
				.createMessageStreams(topicCount);
		// Get Kafka stream for topic 'mytopic'`
		List<KafkaStream<byte[], byte[]>> kStreamList = consumerStreams
				.get(topic);
		// System.out.println(kStreamList);
		// Iterate stream using ConsumerIterator
		for (final KafkaStream<byte[], byte[]> kStreams : kStreamList) {
			ConsumerIterator<byte[], byte[]> consumerIte = kStreams
					.iterator();

			File fl = new File(metadata.get("destinationDirectory")+"\\"+TOPIC);
			transferMetaData1.put("destinationFile", metadata.get("destinationDirectory")+"\\"+TOPIC);
			//Session session=DBOperations.connectCassandra();
			
			// String str;
			FileWriter fw;
			while (consumerIte.hasNext()) {
				try {
					fw = new FileWriter(fl, true);
					fw.write(new String(consumerIte.next().message()));
					
					consumerCount++;
					String incrementConsumer=Integer.toString(consumerCount);
//data base code to update consumerCount in transfer_event
					
					fw.close();
					transferMetaData1.put("incrementConsumer", incrementConsumer);
					DBOperations.updateTransferEventConsumeDetails(session1, transferMetaData1);
					consumerConnector.shutdown();
				}
				
				catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	
	// Shut down the Consumer Connector
	if (consumerConnector != null)
		consumerConnector.shutdown();
	}

	public static String createTopic(String topicName) {
		// ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000,
		// ZKStringSerializer$.MODULE$);
		// zkClient.deleteRecursive(ZkUtils.getTopicPath(topicName));
		Properties topicConfig = new Properties();
		AdminUtils.createTopic(zkClient, topicName, 1, 1, topicConfig);
		// zkClient.close();

		return topicName;
	}

	public static String deleteTopic(String topicName) {
		String status = topicName + " : topic is deleted successfully";
		//1 st 10000 is for connection timeout
		//2nd 10000 is session timeout
		ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000,
				ZKStringSerializer$.MODULE$);
		zkClient.deleteRecursive(ZkUtils.getTopicPath(topicName));
		return status;
	}

	public static String getMessages(String sFilePath, Map<String, String> metadata, Map<String, String> transferMetaData) {
		transferMetaData1=transferMetaData;
		String status = null;
		String delimiter = "\\\\";
		File inputFile = new File(sFilePath);
		FileInputStream inputStream;
		String Key;
		String sfile = null;
		String ss[] = sFilePath.split(delimiter);
		int c = ss.length;
		sfile = ss[c - 1];
//		we have to add code for dividing the file before split basing on size
		
		int fileSize = (int) inputFile.length();
		System.out.println("filesize is"+fileSize);
		int nChunks = 0, read = 0, readLength = PART_SIZE;
		byte[] byteChunkPart;
		try {
			inputStream = new FileInputStream(inputFile);
			while (fileSize > 0) {
				if (fileSize <= 5) {
					readLength = fileSize;
				}
				if (inputStream.available() < readLength) {
					byteChunkPart = new byte[inputStream.available()];
					// IOUtils.copyLarge();
					read = inputStream.read(byteChunkPart, 0,
							inputStream.available());
				} else {
					byteChunkPart = new byte[readLength];
					read = inputStream.read(byteChunkPart, 0, readLength);
				}
				fileSize -= read;
				assert (read == byteChunkPart.length);
				nChunks++;
				Key = sfile + "." + (nChunks - 1);
				System.out.println(sfile);
				 publish(sfile, Key, new String(byteChunkPart),metadata);
              
			}
			inputStream.close();
			// To Do code to update and insert into DB
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return status;
	}


}
