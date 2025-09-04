package com.example.lottery.controllers;

import com.example.lottery.dtos.LotteryDataDTO;
import com.example.lottery.dtos.ResultDTO;
import com.example.lottery.services.LotteryDatasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Slf4j
@Controller
@AllArgsConstructor
@Tag(name = "Download existing lottery data")
public class LotteryDataController {

	private final LotteryDatasService lotteryDatasService;
	private List<LotteryDataDTO> lotteryDataDTOList;
	private List<String> numbersFromRNADC = new ArrayList<>();
	private List<String> numbersFromRDO = new ArrayList<>();

	@GetMapping("/")
	public String home(Model model) {

		return "index";
	}

	@GetMapping("/start")
	public String initialize(Model model) {
		log.info("Serving home page...");
		lotteryDataDTOList = lotteryDatasService.getLotteryDatas();
		log.debug("Loaded {} lottery records from DB/service", lotteryDataDTOList.size());

		model.addAttribute("message", "Keyd[oldal");
		model.addAttribute("results", new ArrayList<>());

		return "home";
	}

	@GetMapping("/main")
	public String getAlldatas(Model model) {
		log.info("Fetching random numbers from external APIs...");
		numbersFromRNADC = lotteryDatasService.getRandomNumbersFromRNADC();
		numbersFromRDO = lotteryDatasService.getRandomNumbersFromRandomDotOrg();

		log.debug("randomnumberapi.com returned: {}", numbersFromRNADC);
		log.debug("random.org returned: {}", numbersFromRDO);

		Map<String, List<String>> getApisResults = new HashMap<>();
		getApisResults.put("randomnumberapi.com", numbersFromRNADC);
		getApisResults.put("random.org", numbersFromRDO);
		model.addAttribute("apiResults", getApisResults);

		return "fragments :: apiResultsFragment";
	}

	@GetMapping("/matchCheck")
	public String getMatchCheckingResult(Model model) {
		log.info("Checking lottery results against generated numbers...");

		Map<String, List<ResultDTO>> getMatchResults = new HashMap<>();
		getMatchResults.put("randomnumberapi.com",
				lotteryDatasService.getMatchCheckingResult(lotteryDataDTOList, numbersFromRNADC));
		getMatchResults.put("random.org",
				lotteryDatasService.getMatchCheckingResult(lotteryDataDTOList, numbersFromRDO));

		log.debug("Match results calculated: {}", getMatchResults);

		model.addAttribute("getMatchResults", getMatchResults);

		return "fragments :: resultsFragment";
	}
}
