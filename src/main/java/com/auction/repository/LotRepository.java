package com.auction.repository;

import com.auction.model.Lot;
import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {
    List<Lot> findByStatus(String status);
    List<Lot> findByOwner(User owner);
}