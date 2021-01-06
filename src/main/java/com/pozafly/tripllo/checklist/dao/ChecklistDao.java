package com.pozafly.tripllo.checklist.dao;

import com.pozafly.tripllo.checklist.model.response.ChecklistResultMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChecklistDao {
    public List<ChecklistResultMap> readChecklist(Long cardId);
    public int countChecklist(Long carId);
    public void createChecklist(Map<String, Object> checklistInfo);
    public void updateChecklist(Map<String, Object> checklistInfo);
    public void deleteChecklist(Long checklistId);
}
