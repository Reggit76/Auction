package com.auction.controller;

import com.auction.dto.BidRequest;
import com.auction.dto.BidResponse;
import com.auction.service.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidController {
    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    public ResponseEntity<?> placeBid(@RequestBody BidRequest bidRequest) {
        try {
            BidResponse bid = bidService.placeBid(bidRequest);
            return ResponseEntity.ok(bid);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/lot/{lotId}")
    public ResponseEntity<List<BidResponse>> getBidsByLot(@PathVariable Long lotId) {
        return ResponseEntity.ok(bidService.getBidsByLotId(lotId));
    }
}