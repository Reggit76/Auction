package com.auction.dto;

import java.math.BigDecimal;

public record BidRequest(
        Long lotId,
        Long userId,
        BigDecimal bidAmount
) {}