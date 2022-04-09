package com.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@RequestMapping("/")/*intercepta a url*/
	public String index() {
		return"index";/*retorna para uma pagina html*/
	}
	
}
