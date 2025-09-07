package com.example.lottery.dtos;

import lombok.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class LotteryDataDTO {

	private String yearString;
	private String weekString;
	private String dateString;
	private String match5Pcs;
	private String match5Reward;
	private String match4Pcs;
	private String match4Reward;
	private String match3Pcs;
	private String match3Reward;
	private String match2Pcs;
	private String match2Reward;
	private String winningNumber1;
	private String winningNumber2;
	private String winningNumber3;
	private String winningNumber4;
	private String winningNumber5;

	public LotteryDataDTO(List<String> dates, List<String> pcsAndRewards, List<String> winningNumbers) {
		this.yearString = dates.get(0);
		this.weekString = dates.get(1);
		this.dateString = dates.get(2);
		this.match5Pcs = pcsAndRewards.get(0);
		this.match5Reward = pcsAndRewards.get(1);
		this.match4Pcs = pcsAndRewards.get(2);
		this.match4Reward = pcsAndRewards.get(3);
		this.match3Pcs = pcsAndRewards.get(4);
		this.match3Reward = pcsAndRewards.get(5);
		this.match2Pcs = pcsAndRewards.get(6);
		this.match2Reward = pcsAndRewards.get(7);
		this.winningNumber1 = winningNumbers.get(0);
		this.winningNumber2 = winningNumbers.get(1);
		this.winningNumber3 = winningNumbers.get(2);
		this.winningNumber4 = winningNumbers.get(3);
		this.winningNumber5 = winningNumbers.get(4);
	}

	public List<String> getWinningNumbers() {

		return List.of(winningNumber1, winningNumber2, winningNumber3, winningNumber4, winningNumber5);
	}
}
