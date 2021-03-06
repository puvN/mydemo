package com.puvn.servicetwo.controller;

import com.puvn.servicetwo.exception.ExceptionDTO;
import com.puvn.servicetwo.model.Stat;
import com.puvn.servicetwo.model.UserAnalytic;
import com.puvn.servicetwo.service.MyService;
import com.puvn.servicetwo.status.StatusDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Eugeniy Lukin (eugeniylukin@gmail.com) created on 27.06.2020.
 */
@RestController
@RequestMapping(value = "/")
public class KafkaController {

	private final MyService myService;

	@Autowired
	KafkaController(MyService myService) {
		this.myService = myService;
	}

	@ApiOperation(value = "Возвращает аналитику платежей по всем пользователям",
			notes = "Возвращает список платежей всех пользователей")
	@GetMapping(value = "/analytic")
	public @ResponseBody
	List<UserAnalytic> getApplicationConfig() {
		return this.myService.getAllUsersAnalytic();
	}

	@ApiOperation(value = "Возвращает аналитику платежей по пользователю",
			notes = "Возвращает информацию по пользователю")
	@GetMapping(value = "/analytic/{userId}")
	public @ResponseBody
	UserAnalytic getUserAnalytic(@PathVariable("userId") String userId) {
		var result = this.myService.getUserAnalytic(userId);
		if (result == null) {
			throw new RuntimeException();
		} else {
			return result;
		}
	}

	@ApiOperation(value = "Возвращает статистику по пользователю",
			notes = "Возвращает статистику по пользователю")
	@GetMapping(value = "/analytic/{userId}/stats")
	public @ResponseBody
	Stat getUserStats(@PathVariable("userId") String userId) {
		var userAnalytic = this.myService.getUserAnalytic(userId);
		if (userAnalytic == null) {
			throw new RuntimeException();
		} else {
			return this.myService.getUserStats(userAnalytic);
		}
	}

	@ApiOperation(value = "Возвращает ошибку",
			notes = "Возвращает ошибку")
	@GetMapping(value = "/error")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody
	ExceptionDTO getError() {
		return new ExceptionDTO();
	}

	@ApiOperation(value = "Возвращает ошибку",
			notes = "Возвращает ошибку")
	@GetMapping(value = "/admin/health")
	public @ResponseBody
	StatusDTO getHealth() {
		return new StatusDTO();
	}
}