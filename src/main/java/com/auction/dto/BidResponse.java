package com.auction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record BidResponse(
        Long id,
        Long lotId,
        Long userId,
        BigDecimal bidAmount,
        LocalDateTime bidTime
) {}