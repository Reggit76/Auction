package com.auction.controller;

import com.auction.model.Lot;
import com.auction.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lots")
public class LotController {

    @Autowired
    private LotService lotService;

    @PostMapping
    public ResponseEntity<Lot> createLot(@RequestBody Lot lot) {
        Lot createdLot = lotService.createLot(lot);
        return ResponseEntity.ok(createdLot);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lot> getLotById(@PathVariable Long id) {
        return lotService.getLotById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lot> updateLot(@PathVariable Long id, @RequestBody Lot lot) {
        lot.setId(id);
        Lot updatedLot = lotService.updateLot(lot);
        return ResponseEntity.ok(updatedLot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLot(@PathVariable Long id) {
        lotService.deleteLot(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Lot>> getActiveLots() {
        List<Lot> activeLots = lotService.getActiveLots();
        return ResponseEntity.ok(activeLots);
    }
}