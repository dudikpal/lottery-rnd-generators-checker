/*
package com.example.lottery;


import com.example.lottery.dtos.LotteryDataDTO;
import com.example.lottery.dtos.ResultDTO;
import com.example.lottery.services.LotteryDatasService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LotteryDatasServiceAPITest {

	private final LotteryDatasService service = new LotteryDatasService();

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
		List<ResultDTO> result = service.getMatchCheckingResult(input, numbersFromApi);

		// THEN
		assertThat(result).isNotEmpty();
		assertThat(result.get(0).getMatchPcs()).isEqualTo(3);
		assertThat(result.get(0).getMatchReward()).isEqualTo("5000");
		assertThat(result.get(0).getDate()).isEqualTo("2024 - 35. hét");
		assertThat(result.get(0).getWinningNumbers()).containsAll(Arrays.asList("10","20","30","40","50"));
	}
}


*/
