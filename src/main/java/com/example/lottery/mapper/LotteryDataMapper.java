package com.example.lottery.mapper;

import com.example.lottery.dtos.LotteryDataDTO;
import com.example.lottery.entities.LotteryDataEntity;

import java.util.List;

public class LotteryDataMapper {

	public LotteryDataDTO toDTO(LotteryDataEntity entity) {

		LotteryDataDTO dto = new LotteryDataDTO(
			List.of(entity.getYearString(), entity.getWeekString(), entity.getDateString()),
			List.of(entity.getMatch5Pcs(), entity.getMatch5Reward(),
				entity.getMatch4Pcs(), entity.getMatch4Reward(),
				entity.getMatch3Pcs(), entity.getMatch3Reward(),
				entity.getMatch2Pcs(), entity.getMatch2Reward()
			),
			List.of(
				entity.getWinningNumber1(),
				entity.getWinningNumber2(),
				entity.getWinningNumber3(),
				entity.getWinningNumber4(),
				entity.getWinningNumber5()
			)
		);

		return dto;
	}

	public LotteryDataEntity fromDTO(LotteryDataDTO dto) {

		LotteryDataEntity entity = new LotteryDataEntity();

		entity.setYearString(dto.getYearString());
		entity.setWeekString(dto.getWeekString());
		entity.setDateString(dto.getDateString());
		entity.setMatch5Pcs(dto.getMatch5Pcs());
		entity.setMatch5Reward(dto.getMatch5Reward());
		entity.setMatch4Pcs(dto.getMatch4Pcs());
		entity.setMatch4Reward(dto.getMatch4Reward());
		entity.setMatch3Pcs(dto.getMatch3Pcs());
		entity.setMatch3Reward(dto.getMatch3Reward());
		entity.setMatch2Pcs(dto.getMatch2Pcs());
		entity.setMatch2Reward(dto.getMatch2Reward());
		entity.setWinningNumber1(dto.getWinningNumber1());
		entity.setWinningNumber2(dto.getWinningNumber2());
		entity.setWinningNumber3(dto.getWinningNumber3());
		entity.setWinningNumber4(dto.getWinningNumber4());
		entity.setWinningNumber5(dto.getWinningNumber5());

		return entity;
	}
}
