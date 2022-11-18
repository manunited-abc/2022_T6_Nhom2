package nlu.lotteries_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nlu.lotteries_api.service.LotteryService;

@RestController
@RequestMapping(value = "api/lottery")
public class LotteyController {
	@Autowired
	LotteryService lotteryService;

	@GetMapping("/latest")
	public ResponseEntity<?> getCategory(@RequestParam String region) {
		System.out.println(region);
		return ResponseEntity.ok(lotteryService.getLotteryLatest(region));
	}
}
