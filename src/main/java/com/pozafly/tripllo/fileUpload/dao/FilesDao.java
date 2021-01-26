package com.pozafly.tripllo.fileUpload.dao;

import com.pozafly.tripllo.fileUpload.model.Files;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FilesDao {
    public List<Files> readFile(Long cardId);
    public Files readFileOne(Long fileId);
    public int createFile(Map<String, Object> fileInfo);
    public void deleteFile(Long id);
    public int countFiles(Long carId);
}
