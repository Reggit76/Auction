package com.auction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record LotRequest(
        String title,
        String description,
        String imageUrl,
        BigDecimal startingPrice,
        BigDecimal minBidStep,
        LocalDateTime endTime
) {}