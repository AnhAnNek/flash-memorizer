package com.universityteam.flashmemorizer.dto;

import com.universityteam.flashmemorizer.enums.ERating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardReview {
    private Long id;
    private String term;
    private String desc;
    private ERating rating;
    private boolean isCorrect;
}
