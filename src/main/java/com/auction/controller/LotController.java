package com.auction.controller;

import com.auction.dto.LotRequest;
import com.auction.dto.LotResponse;
import com.auction.model.Lot;
import com.auction.service.LotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lots")
public class LotController {
    private final LotService lotService;

    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @PostMapping
    public ResponseEntity<LotResponse> createLot(@RequestBody LotRequest lotRequest) {
        Lot lot = convertToEntity(lotRequest);
        Lot createdLot = lotService.createLot(lot);
        return ResponseEntity.ok(convertToDto(createdLot));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LotResponse> getLotById(@PathVariable Long id) {
        return lotService.getLotById(id)
                .map(this::convertToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LotResponse> updateLot(@PathVariable Long id, @RequestBody LotRequest lotRequest) {
        return lotService.getLotById(id)
                .map(existingLot -> {
                    existingLot.setTitle(lotRequest.title()); // Исправлено название метода
                    existingLot.setDescription(lotRequest.description());
                    existingLot.setImageUrl(lotRequest.imageUrl());
                    existingLot.setStartingPrice(lotRequest.startingPrice());
                    existingLot.setMinBidStep(lotRequest.minBidStep());
                    existingLot.setEndTime(lotRequest.endTime());
                    Lot updatedLot = lotService.updateLot(existingLot);
                    return ResponseEntity.ok(convertToDto(updatedLot));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLot(@PathVariable Long id) {
        lotService.deleteLot(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LotResponse>> getActiveLots() {
        List<Lot> activeLots = lotService.getActiveLots();
        List<LotResponse> response = activeLots.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private Lot convertToEntity(LotRequest request) {
        Lot lot = new Lot();
        lot.setTitle(request.title());
        lot.setDescription(request.description());
        lot.setImageUrl(request.imageUrl());
        lot.setStartingPrice(request.startingPrice());
        lot.setMinBidStep(request.minBidStep());
        lot.setEndTime(request.endTime());
        return lot;
    }

    private LotResponse convertToDto(Lot lot) {
        return new LotResponse(
                lot.getId(),
                lot.getTitle(),
                lot.getDescription(),
                lot.getImageUrl(),
                lot.getStartingPrice(),
                lot.getCurrentBid(),
                lot.getMinBidStep(),
                lot.getEndTime(),
                lot.getStatus()
        );
    }
}