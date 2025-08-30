package com.example.lottery.repositories;

import com.example.lottery.entities.LotteryDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryDataRepository extends JpaRepository<LotteryDataEntity, Long> {
}
