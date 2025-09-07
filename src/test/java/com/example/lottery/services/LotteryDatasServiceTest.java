package com.example.lottery.services;

import com.example.lottery.dtos.LotteryDataDTO;
import com.example.lottery.dtos.ResultDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class LotteryDatasServiceTest {

	@Autowired
	private LotteryDatasService lotteryService;

	@Test
	void testGetMatchCheckingResult_withMatches() {
		// GIVEN
		LotteryDataDTO dto = new LotteryDataDTO();
		dto.setYearString("2024");
		dto.setWeekString("35");
		dto.setWinningNumber1("10");
		dto.setWinningNumber2("20");
		dto.setWinningNumber3("30");
		dto.setWinningNumber4("40");
		dto.setWinningNumber5("50");
		dto.setMatch2Reward("500 Ft");
		dto.setMatch3Reward("5000 Ft");

		List<LotteryDataDTO> input = new ArrayList<>();
		input.add(dto);

		List<String> numbersFromApi = Arrays.asList("20", "30", "99", "100", "10");

		// WHEN
		List<ResultDTO> result = lotteryService.getMatchCheckingResult(input, numbersFromApi);

		// THEN
		assertEquals(1, result.size());
		ResultDTO res = result.get(0);

		assertEquals("2024 - 35. hét", res.getDate());
		assertEquals(3, res.getMatchPcs());
		assertEquals("5000", res.getMatchReward()); // mert 3 találat → match3Reward
	}

	@Test
	void testGetMatchCheckingResult_noMatches() {
		// GIVEN
		LotteryDataDTO dto = new LotteryDataDTO();
		dto.setYearString("2024");
		dto.setWeekString("36");
		dto.setWinningNumber1("1");
		dto.setWinningNumber2("2");
		dto.setWinningNumber3("3");
		dto.setWinningNumber4("4");
		dto.setWinningNumber5("5");
		dto.setMatch2Reward("100 Ft");

		List<LotteryDataDTO> input = List.of(dto);
		List<String> numbersFromApi = Arrays.asList("10", "20", "30", "40", "50");

		// WHEN
		List<ResultDTO> result = lotteryService.getMatchCheckingResult(input, numbersFromApi);

		// THEN
		assertTrue(result.isEmpty()); // mert nincs 2 vagy több találat
	}

	@Test
	void testGetMatchCheckingResult_emptyInput() {
		// GIVEN
		List<LotteryDataDTO> input = new ArrayList<>();
		List<String> numbersFromApi = Arrays.asList("1", "2", "3");

		// WHEN
		List<ResultDTO> result = lotteryService.getMatchCheckingResult(input, numbersFromApi);

		// THEN
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}
}