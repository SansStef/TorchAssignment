package com.torch.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.torch.exception.LineNotFoundException;
import com.torch.model.LineStatusHistory;
import com.torch.model.LineStatusResponse;
import com.torch.model.LineUptime;
import com.torch.model.SubwayLine;
import com.torch.repositories.LineStatusHistoryRepository;
import com.torch.services.LineUptimeService;

@Controller
public class LineController {

	@Autowired
	private LineStatusHistoryRepository statusRepository;
	@Autowired
	private LineUptimeService uptimeService;
	
    @GetMapping("/status/{line}")
    public ResponseEntity<LineStatusResponse> getLineStatus(@PathVariable("line") String line) throws LineNotFoundException{
    	
    	SubwayLine trainLine = SubwayLine.getFromName(line);
    	if(trainLine == null) {
    		throw new LineNotFoundException();    		
    	}
    	
    	LineStatusHistory currentStatus = statusRepository.findSubwayLineHistory(trainLine).get(0);
    	
    	LineStatusResponse lineResponse = new LineStatusResponse();
    	lineResponse.setSubwayLine(currentStatus.getSubwayLine());
    	lineResponse.setStatus(currentStatus.getStatus());
    	
    	return new ResponseEntity<LineStatusResponse>(lineResponse, HttpStatus.OK);
    }
	
    @GetMapping("/uptime/{line}")
    public ResponseEntity<LineUptime> getLineUptime(@PathVariable("line") String line) throws LineNotFoundException{
    	
    	SubwayLine trainLine = SubwayLine.getFromName(line);
    	if(trainLine == null) {
    		throw new LineNotFoundException();    		
    	}
    	
    	LineUptime uptime = new LineUptime();
    	uptime.setSubwayLine(trainLine);
    	uptime.setUptime(uptimeService.calculateOverallUptime(trainLine));
    	
    	return new ResponseEntity<LineUptime>(uptime, HttpStatus.OK);
    }
}
