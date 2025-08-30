package com.example.lottery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LotteryDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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
}
