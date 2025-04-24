package com.auction.dto;

import com.auction.model.LotStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LotResponse(
        Long id,
        String title,
        String description,
        String imageUrl,
        BigDecimal startingPrice,
        BigDecimal currentBid,
        BigDecimal minBidStep,
        LocalDateTime endTime,
        LotStatus status
) {}