package com.youtubetags.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.youtubetags.model.TagAnalysisHistory;

public interface TagAnalysisRepository extends JpaRepository<TagAnalysisHistory, Long> {

}
