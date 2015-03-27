package la.serendipity.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class Test {
	@RequestMapping(value="/test")
	public String get() {
		return "TESTa";
	}
}
