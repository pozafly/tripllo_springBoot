package com.pozafly.tripllo.checklist.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ChecklistItemDao {
    public void createChecklistItem(Map<String, Object> checklistItemInfo);
    public void updateChecklistItem(Map<String, Object> checklistItemInfo);
    public void deleteChecklistItem(Long checklistItemId);
}
