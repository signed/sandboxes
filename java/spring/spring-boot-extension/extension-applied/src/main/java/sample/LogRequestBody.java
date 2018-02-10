package sample;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = RestController.class)
public class LogRequestBody {

	@ModelAttribute
	public void dumpRequestBody(@RequestBody String requestString) {
		System.out.println("ControllerAdvise");
	}

}
