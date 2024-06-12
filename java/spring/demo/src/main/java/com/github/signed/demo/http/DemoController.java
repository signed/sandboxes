package com.github.signed.demo.http;

import com.github.signed.demo.core.ReadDemosCommand;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demos")
public class DemoController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetDemosDto> demos() {
        final ReadDemosCommand command = new ReadDemosCommand();
        final GetDemosDto dto = new GetDemosDto();
        dto.demos.add("one");
        dto.demos.add("two");
        return ResponseEntity.ok(dto);
    }
}
