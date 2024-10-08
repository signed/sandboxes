package sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@SpringBootApplication
@RestController
public class BootApplication {

	private static final Logger logger = LoggerFactory.getLogger(BootApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

	@RequestMapping(method = POST, path = "/")
	@ResponseBody
	public ResponseText method(@RequestBody RequestText requestText) {
		logger.info("enter=processing");
		ResponseText responseText = new ResponseText();
		responseText.responseText = "processed(" + requestText.requestText + ")";
		logger.info("exit=completed");
		return responseText;
	}

	public static class RequestText {
		public String requestText;
	}

	public static class ResponseText {
		public String responseText;
	}

}
