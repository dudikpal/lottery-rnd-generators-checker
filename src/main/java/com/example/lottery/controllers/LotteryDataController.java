package com.example.lottery.controllers;
import com.example.lottery.dtos.LotteryDataDTO;
import com.example.lottery.dtos.ResultDTO;
import com.example.lottery.services.LotteryDatasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
@AllArgsConstructor
@Tag(name = "Download existing lottery data")
public class LotteryDataController {

	public LotteryDatasService lotteryDatasService;
	List<LotteryDataDTO> lotteryDataDTOList;
	List<String> numbersFromRNADC = new ArrayList<>();
	List<String> numbersFromRDO = new ArrayList<>();

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("message", "Keyd[oldal");
		lotteryDataDTOList = lotteryDatasService.getLotteryDatas();
		model.addAttribute("results", new ArrayList<>());

		return "home";
	}

	@GetMapping("/start")
	public String getAllDatas(Model model) {
		numbersFromRNADC = lotteryDatasService.getRandomNumbersFromRNADC();
		numbersFromRDO = lotteryDatasService.getRandomNumbersFromRandomDotOrg();
		Map<String, List<String>> getApisResults = new HashMap<>();
		getApisResults.put("randomnumberapi.com", numbersFromRNADC);
		getApisResults.put("random.org", numbersFromRDO);
		model.addAttribute("apiResults", getApisResults);

		// csak az API results konténert rendereljük
		return "fragments :: apiResultsFragment";
	}


	@GetMapping("/download")
	@Operation(summary = "Get existing lottery datas from lottery api")
	@ApiResponse(responseCode = "201", description = "get lottery datas success")
	public String getLotteryDatas () {

		lotteryDataDTOList = lotteryDatasService.getLotteryDatas();

		return  String.format("%s \n %s", lotteryDataDTOList.get(0).toString(), lotteryDataDTOList.get(1).toString()) ;
	}

	@GetMapping("/matchCheck")
	public String getMatchCheckingResult(Model model) {

		Map<String, List<ResultDTO>> getMatchResults = new HashMap<>();
		getMatchResults.put("randomnumberapi.com", lotteryDatasService.getMatchCheckingResult(lotteryDataDTOList, numbersFromRNADC));
		getMatchResults.put("random.org", lotteryDatasService.getMatchCheckingResult(lotteryDataDTOList, numbersFromRDO));
		model.addAttribute("getMatchResults", getMatchResults);
		System.out.println(getMatchResults);

		// csak a results konténert rendereljük
		return "fragments :: resultsFragment";
	}


}
