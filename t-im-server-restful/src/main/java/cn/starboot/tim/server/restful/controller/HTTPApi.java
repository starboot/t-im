package cn.starboot.tim.server.restful.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HTTPApi {

	@GetMapping
	public String hello() {
		return "HELLO";
	}
}
