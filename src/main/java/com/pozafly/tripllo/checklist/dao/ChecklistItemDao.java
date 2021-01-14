package com.pozafly.tripllo.checklist.dao;

import com.pozafly.tripllo.checklist.model.ChecklistItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ChecklistItemDao {
    public ChecklistItem readChecklistItem(Long checklistItemId);
    public void createChecklistItem(Map<String, Object> checklistItemInfo);
    public void updateChecklistItem(Map<String, Object> checklistItemInfo);
    public void deleteChecklistItem(Long checklistItemId);
}
