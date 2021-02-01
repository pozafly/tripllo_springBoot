package com.pozafly.tripllo.hashtag.model.response;

import com.pozafly.tripllo.board.model.Board;
import lombok.Data;

import java.util.List;

@Data
public class HashtagResult {

    private String name;
    private List<Board> boards;

}
