package com.witsoftware.rest.controller;

import com.witsoftware.rest.calculator.dto.MathOperationResultDTO;
import com.witsoftware.rest.calculator.model.MathOperation;
import com.witsoftware.rest.calculator.model.MathOperationResult;
import com.witsoftware.rest.calculator.utils.Constants;
import com.witsoftware.rest.calculator.utils.Operator;
import com.witsoftware.rest.handler.ResultRetriever;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class MathOperationController {

    private RabbitTemplate rabbitTemplate;

   // private ResultRetriever resultRetriever;

    @Autowired
    MathOperationController(RabbitTemplate rabbitTemplate
                            /*ResultRetriever resultRetriever*/){
        this.rabbitTemplate =  rabbitTemplate;
        //this.resultRetriever = resultRetriever;
    }

    @GetMapping("/sum")

    public ResponseEntity<MathOperationResultDTO> getSum(@RequestParam("a") int a, @RequestParam("b") int b  ){

        MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                a,
                b,
                Operator.ADDITION);

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.OPERATION_ROUTING_KEY,mathOperation);

        Double result = Double.valueOf(a+b);

        return  buildResponse(mathOperation.getUuid(),result);

    }
    @GetMapping("/sub")
    public ResponseEntity<MathOperationResultDTO> getSub(@RequestParam("a") int a, @RequestParam("b") int b  ){
        MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                a,
                b,
                Operator.SUBTRACTION);

        Double result = Double.valueOf(a-b);

        return  buildResponse(mathOperation.getUuid(),result);


    }
    @GetMapping("/mult")
    public ResponseEntity<MathOperationResultDTO> getMult(@RequestParam("a") int a, @RequestParam("b") int b  ){

        MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                a,
                b,
                Operator.MULTIPLICATION);

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.OPERATION_ROUTING_KEY,mathOperation);

         Double result = Double.valueOf(a*b);

        return  buildResponse(mathOperation.getUuid(),result);

    }
    @GetMapping("/div")
    public ResponseEntity<MathOperationResultDTO> getDivisionOf(@RequestParam("a") int a, @RequestParam("b") int b  ){
        MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                a,
                b,
                Operator.DIVISION);

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.OPERATION_ROUTING_KEY,mathOperation);

        Double result = Double.valueOf(a/b);

        return  buildResponse(mathOperation.getUuid(),result);
    }



    private HttpHeaders setDefaultHeaders(UUID  uuid){
        HttpHeaders headers = new HttpHeaders();
        headers.set("request-id",uuid.toString());

        return headers;
    }

    private  ResponseEntity<MathOperationResultDTO> buildResponse(UUID uuid, Double result){
        return  ResponseEntity
                .ok()
                .headers(setDefaultHeaders(uuid))
                .body(new MathOperationResultDTO(result));
    }

}
