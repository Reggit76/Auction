package com.auction.controller;

import com.auction.model.Bid;
import com.auction.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    @Autowired
    private BidService bidService;

    @PostMapping
    public ResponseEntity<Bid> placeBid(@RequestBody Bid bid) {
        try {
            Bid placedBid = bidService.placeBid(bid);
            return ResponseEntity.ok(placedBid);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/lot/{lotId}")
    public ResponseEntity<List<Bid>> getBidsByLot(@PathVariable Long lotId) {
        List<Bid> bids = bidService.getBidsByLotId(lotId);
        return ResponseEntity.ok(bids);
    }
}