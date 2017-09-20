package test;

import java.io.File;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.datastax.driver.core.Session;

public class xmlCreator {
	//public static String xmlFilePath = "D:\\test.xml";

	static void access(HashMap<String, String> v) throws ParserConfigurationException, TransformerException, NoSuchFieldException, SecurityException, InterruptedException {
		System.out.println(v);
		String xmlFilePath="";
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		org.w3c.dom.Document document = dBuilder.newDocument();
		Element rootElement = ((org.w3c.dom.Document) document).createElement("Monitor");
		((Node) document).appendChild(rootElement);

		Element monitorname = ((org.w3c.dom.Document) document).createElement("MonitorName");
		((Node) rootElement).appendChild(monitorname);

		Element jobname = ((org.w3c.dom.Document) document).createElement("JobName");
		((Node) rootElement).appendChild(jobname);

		Element sourcedetails = ((org.w3c.dom.Document) document).createElement("SourceDetails");
		((Node) rootElement).appendChild(sourcedetails);
		Element sourcedirectory = ((org.w3c.dom.Document) document).createElement("SourceDirectory");
		((Node) sourcedetails).appendChild(sourcedirectory);
		Element sourcetriggerpattern = ((org.w3c.dom.Document) document).createElement("SourceTriggerPattern");
		((Node) sourcedetails).appendChild(sourcetriggerpattern);
		Element sourcefilepattern = ((org.w3c.dom.Document) document).createElement("SourceFilePattern");
		((Node) sourcedetails).appendChild(sourcefilepattern);

		Element destinationdetails = ((org.w3c.dom.Document) document).createElement("DestinationDetails");
		((Node) rootElement).appendChild(destinationdetails);
		Element destinationdirectory = ((org.w3c.dom.Document) document).createElement("DestinationDirectory");
		((Node) destinationdetails).appendChild(destinationdirectory);
		Element destinationfilepattern = ((org.w3c.dom.Document) document).createElement("DestinationFilePattern");
		((Node) destinationdetails).appendChild(destinationfilepattern);
		Element destinationtriggerpattern = ((org.w3c.dom.Document) document)
				.createElement("DestinationTriggerPattern");
		((Node) destinationdetails).appendChild(destinationtriggerpattern);

		Element pollDetails = ((org.w3c.dom.Document) document).createElement("PollDetails");
		((Node) rootElement).appendChild(pollDetails);
		Element pollInterval = ((org.w3c.dom.Document) document).createElement("PollInterval");
		((Node) pollDetails).appendChild(pollInterval);
		Element pollUnits = ((org.w3c.dom.Document) document).createElement("PollUnits");
		((Node) pollDetails).appendChild(pollUnits);

		Element monitorxmlfilename = ((org.w3c.dom.Document) document).createElement("MonitorXMLFileName");
		((Node) rootElement).appendChild(monitorxmlfilename);

		for (Entry<String, String> metaData : ((HashMap<String, String>) v).entrySet()) {
			String key = (String) metaData.getKey();
			String value = (String) metaData.getValue();

			if (metaData.getKey().toString() == "monitorName") {
				monitorname
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				rootElement.appendChild(monitorname);
			}
			if (metaData.getKey().toString() == "jobName") {
				jobname.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				rootElement.appendChild(jobname);
			}
			if (metaData.getKey().toString() == "sourceDetails") {
				sourcedetails
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				rootElement.appendChild(sourcedetails);
			}
			if (metaData.getKey().toString() == "sourceDirectory") {
				sourcedirectory
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				sourcedetails.appendChild(sourcedirectory);
			}
			if (metaData.getKey().toString() == "sourceTriggerPattern") {
				sourcetriggerpattern
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				sourcedetails.appendChild(sourcetriggerpattern);
			}
			if (metaData.getKey().toString() == "sourceFilePattern") {
				sourcefilepattern
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				sourcedetails.appendChild(sourcefilepattern);
			}

			if (metaData.getKey().toString() == "destinationDetails") {
				destinationdetails
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				rootElement.appendChild(destinationdetails);
			}
			if (metaData.getKey().toString() == "destinationDirectory") {
				destinationdirectory
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				destinationdetails.appendChild(destinationdirectory);
			}
			if (metaData.getKey().toString() == "destinationFilePattern") {
				destinationfilepattern
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				destinationdetails.appendChild(destinationfilepattern);
			}
			if (metaData.getKey().toString() == "destinationTriggerPattern") {
				destinationtriggerpattern
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				destinationdetails.appendChild(destinationtriggerpattern);
			}

			if (metaData.getKey().toString() == "pollUnits") {
				pollUnits.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				pollDetails.appendChild(pollUnits);
			}
			if (metaData.getKey().toString() == "pollInterval") {
				pollInterval
						.appendChild(((org.w3c.dom.Document) document).createTextNode(metaData.getValue().toString()));
				pollDetails.appendChild(pollInterval);
			}
			if (metaData.getKey().toString() == "monitorXml") {
				xmlFilePath=metaData.getValue().toString();
			}

		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		DOMSource domSource = new DOMSource((Node) document);

		StreamResult streamResult = new StreamResult(new File(xmlFilePath));

		transformer.transform(domSource, streamResult);
		System.out.println("created successfully");

		//DBCheck.checkMonitor(v);
		Session session=DBOperations.connectCassandra();
		if(DBOperations.DBMonitorCheck(session, v.get("monitorName"))==null) {
			DBOperations.starting(session, v.get("monitorName"));
		}
		MultipleSourceWatcher.watcher(v.get("sourceDirectory"), v.get("monitorName"), v);
		}

}
