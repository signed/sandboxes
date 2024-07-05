package com.github.signed.demo.http;

import com.github.signed.demo.core.BusinessLogic;
import com.github.signed.demo.core.BusinessLogicResult;
import com.github.signed.demo.core.Product;
import com.github.signed.demo.core.Quantity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/logic")
public class BusinessLogicController {

    private final BusinessLogic businessLogic;

    public BusinessLogicController(BusinessLogic businessLogic) {
        this.businessLogic = businessLogic;
    }

    @PostMapping(path = "/verbose", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> executeVerboseLogic(@RequestBody BusinessLogicRequestDTO request) {
        // validate inputs
        final Product product = new Product(request.product);
        final Quantity quantity = new Quantity(request.quantity);
        return httpResponseFor(businessLogic.callLogic(product, quantity));
    }

    private ResponseEntity<?> httpResponseFor(BusinessLogicResult result) {
        if (result.ok.isPresent()) {
            final BusinessLogicResult.BusinessLogicOk ok = result.ok.get();
            final BusinessLogicResponseDTO response = new BusinessLogicResponseDTO(ok.price().value());
            return ResponseEntity.ok(response);
        }

        if (result.outOfStock.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.internalServerError().build();
    }
}
