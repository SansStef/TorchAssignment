package com.torch.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torch.model.LineStatus;
import com.torch.model.LineStatusHistory;
import com.torch.model.SubwayLine;
import com.torch.repositories.LineStatusHistoryRepository;

@Service
public class LineUptimeService {

	@Autowired
	private LineStatusHistoryRepository statusHistoryRepository;
	
	public double calculateOverallUptime(SubwayLine line) {
		
		double delayTime = 0;
		double totalTime = 0;
		
		List<LineStatusHistory> history = statusHistoryRepository.findSubwayLineHistoryAsc(line);
		
		LineStatusHistory prev = history.get(0);
		for(int i = 1; i < history.size(); i++) {
			LineStatusHistory current = history.get(i);
			
			totalTime += current.getUpdatedAt().getTime() - prev.getUpdatedAt().getTime();
			
			if(prev.getStatus() == LineStatus.DELAYS) {
				delayTime += current.getUpdatedAt().getTime() - prev.getUpdatedAt().getTime();
			}
			
			prev = current;
		}
		
		totalTime += System.currentTimeMillis() - prev.getUpdatedAt().getTime();
		if(prev.getStatus() == LineStatus.DELAYS) {
			delayTime += System.currentTimeMillis() - prev.getUpdatedAt().getTime();
			
		}
		
		return 1.0 - (delayTime / totalTime);
	}
}
