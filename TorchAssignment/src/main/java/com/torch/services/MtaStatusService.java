package com.torch.services;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.torch.model.LineStatus;
import com.torch.model.LineStatusHistory;
import com.torch.model.SubwayLine;
import com.torch.repositories.LineStatusHistoryRepository;

@Service
public class MtaStatusService {

	private static final String LOG_DELAY = "Line %s is experiencing delays";
	private static final String LOG_RECOVERY = "Line %s is now recovered";
	
	@Autowired
	private LineStatusHistoryRepository statusRepository;
	
	private Logger log = LoggerFactory.getLogger(MtaStatusService.class);
	
	@Value(value="${mta_service_status_url}")
	private String mtaUrl;
	
	@Scheduled(fixedDelay=5000)
	public void checkLineStatuses() {
//		log.info("Checking Status Updates");
		
		try {
			parseMtaXml();
		} catch (SAXException | IOException | ParserConfigurationException e) {
			log.error("Error reading mta status info", e);
		}
		
//		log.info("Done Checking Status Updates");
	}
	
	private void checkStatusTransition(SubwayLine line, LineStatus status) {
		
		LineStatusHistory lineUpdate = new LineStatusHistory(line, status);

		List<LineStatusHistory> histories = statusRepository.findSubwayLineHistory(line);
		
		if(histories.size() == 0 || histories.get(0).getStatus() != status) {
			statusRepository.save(lineUpdate);
			
			logChange(line, status);
		}
	}
	
	private void logChange(SubwayLine line, LineStatus status) {
		if(status.equals(LineStatus.DELAYS)) {
			log.info(String.format(LOG_DELAY, line.getName()));
		}
		else {
			log.info(String.format(LOG_RECOVERY, line.getName()));			
		}
	}
	
	private void parseMtaXml() throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(mtaUrl);
		
		
		NodeList nList = doc.getElementsByTagName("line");
		
		for (int i = 0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				String name = eElement.getElementsByTagName("name").item(0).getTextContent();
				String status = eElement.getElementsByTagName("status").item(0).getTextContent();
				
				SubwayLine line = SubwayLine.getFromName(name);
				if(line != null) {
//					System.out.println("Name : " + name);
//					System.out.println("Status : " + status);			
					
					LineStatus lineStatus = LineStatus.NOT_DELAYED;
					if(status.equalsIgnoreCase(LineStatus.DELAYS.toString())) {
						lineStatus = LineStatus.DELAYS;
					}
					
					checkStatusTransition(line, lineStatus);
				}
			}
		}
	}

}
