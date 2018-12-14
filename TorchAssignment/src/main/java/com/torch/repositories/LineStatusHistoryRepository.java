package com.torch.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.torch.model.LineStatusHistory;
import com.torch.model.SubwayLine;

public interface LineStatusHistoryRepository extends CrudRepository<LineStatusHistory, Long>{

	@Query("select s from LineStatusHistory s where s.subwayLine = ?1 order by updated_at desc")
    public List<LineStatusHistory> findSubwayLineHistory(SubwayLine subwayLine);
	
	@Query("select s from LineStatusHistory s where s.subwayLine = ?1 order by updated_at asc")
    public List<LineStatusHistory> findSubwayLineHistoryAsc(SubwayLine subwayLine);
}
