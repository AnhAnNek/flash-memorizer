package com.universityteam.flashmemorizer.strategy.reviewcard;

import com.universityteam.flashmemorizer.dto.CardDTO;
import com.universityteam.flashmemorizer.dto.CardReview;

import java.util.List;

public interface ReviewStrategy<T extends CardReview> {
    List<T> generateTest(List<CardDTO> cards);
}