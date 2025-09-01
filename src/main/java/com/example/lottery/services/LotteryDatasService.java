package com.example.lottery.services;

import com.example.lottery.dtos.LotteryDataDTO;
import com.example.lottery.dtos.ResultDTO;
import com.example.lottery.entities.LotteryDataEntity;
import com.example.lottery.repositories.LotteryDataRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LotteryDatasService {

	private final LotteryDataRepository lotteryDataRepository;
	private final String LOTTERY_DATA_URL = "https://bet.szerencsejatek.hu/cmsfiles/otos.csv";
	private final String RNADC_URL = "http://www.randomnumberapi.com/api/v1.0/random?min=1&max=90&count=5";
	private final String RDO_URL = "https://www.random.org/integer-sets/?sets=1&num=5&min=1&max=90&sort=on&order=index&format=plain&rnd=new";
	private final ModelMapper modelMapper;

	public List<LotteryDataDTO> getLotteryDatas() {

		StringBuffer content = new StringBuffer();
		List<LotteryDataDTO> lotteryDataDTOList = new ArrayList<>();

		/*try {
			URL url = new URL(LOTTERY_DATA_URL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				lotteryDataDTOList.add(inputLineToLotteryDatDTO(inputLine));
			}

			in.close();
			con.disconnect();*/
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(LotteryDatasService.class.getResourceAsStream("/lottery.txt")))) {

			String line;

			while ((line = reader.readLine()) != null) {
				lotteryDataDTOList.add(inputLineToLotteryDatDTO(line));
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (LotteryDataDTO data : lotteryDataDTOList) {

			LotteryDataEntity entity = modelMapper.map(data, LotteryDataEntity.class);
			lotteryDataRepository.save(entity);
		}

		return lotteryDataDTOList;
	}

	private LotteryDataDTO inputLineToLotteryDatDTO(String inputLine) {

		String[] lineArray = inputLine.split(";");
		List<String> dates = new ArrayList<>();
		List<String> pcsAndRewards = new ArrayList<>();
		List<String> winningNumbers = new ArrayList<>();

		for (int i = 0; i < lineArray.length; i++) {

			if (i < 3) {
				dates.add(lineArray[i]);
			} else if (i < 11) {
				pcsAndRewards.add(lineArray[i]);
			} else {
				winningNumbers.add(lineArray[i]);
			}
		}

		return new LotteryDataDTO(dates, pcsAndRewards, winningNumbers);
	}

	public List<ResultDTO> getMatchCheckingResult(List<LotteryDataDTO> lotteryDataDTOList, List<String> numbersFromApi) {

		List<ResultDTO> resultDTOS = new ArrayList<>();

		for (LotteryDataDTO lotteryDTO : lotteryDataDTOList) {

			int matchCount = (int) lotteryDTO.getWinningNumbers().stream()
					.filter(numbersFromApi::contains)
					//.peek(n -> System.out.println("Matched: " + n)) // itt látod a tényleges egyezéseket
					.count();

			if (matchCount > 1) {

				String prize = switch (matchCount) {
					case 5 -> lotteryDTO.getMatch5Reward();
					case 4 -> lotteryDTO.getMatch4Reward();
					case 3 -> lotteryDTO.getMatch3Reward();
					case 2 -> lotteryDTO.getMatch2Reward();
					default -> "0";
				};
				resultDTOS.add(new ResultDTO(lotteryDTO.getDateString(), lotteryDTO.getWinningNumbers(), numbersFromApi, matchCount, prize));
			}


		}
		resultDTOS.sort(Comparator.comparingInt(ResultDTO::getMatchPcs).reversed()
				                .thenComparing(ResultDTO::getMatchReward).reversed());

		return resultDTOS;
	}

	public List<String> getRandomNumbersFromRandomDotOrg() {

		List<String> numbers = new ArrayList<>();

		try {
			URL url = new URL(RDO_URL);
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = in.readLine();

			// Tipp: típusbiztos deszerializálás
			numbers = Arrays.stream(line.split(" ")).toList();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return numbers;
	}

	public List<String> getRandomNumbersFromRNADC() {
		List<String> sortedNumbers = new ArrayList<>();

		while (sortedNumbers.size() != 5) {
			try {
				URL url = new URL(RNADC_URL);
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
				String inputLine = in.readLine();

				// típusbiztos deszerializálás
				List<Integer> numbers = new ObjectMapper()
						.readValue(inputLine, new TypeReference<List<Integer>>() {
						});

				// duplikátum eltávolítása és rendezés
				Set<Integer> uniqueNumbers = new HashSet<>(numbers);
				List<Integer> sortedList = new ArrayList<>(uniqueNumbers);
				Collections.sort(sortedList);

				// String-gé alakítás
				sortedNumbers = sortedList.stream()
						.map(String::valueOf)
						.collect(Collectors.toList());

				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sortedNumbers;
	}

}
