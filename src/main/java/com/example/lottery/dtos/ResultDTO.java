package com.example.lottery.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO {

	private String date;
	private List<String> winningNumbers;
	//private List<String> randomNumbers;
	private List<String> matchedNumbers;
	private int matchPcs = 0;
	private String matchReward;

	/*public ResultDTO(String date, List<String> winningNumbers, int matchPcs, String matchReward) {
	}*/

	/*public static void main(String[] args) {

		List<String> first = new ArrayList<>(List.of("1", "23", "35", "44", "55"));
		List<String> second = new ArrayList<>(List.of("7", "24", "36", "88", "89"));

		System.out.println(first.retainAll(second));
		System.out.println(first);
		System.out.println(second);
	}*/

}
