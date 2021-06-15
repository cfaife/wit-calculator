package com.witsoftware.rest.controller;

import com.witsoftware.rest.calculator.dto.MathOperationResultDTO;
import com.witsoftware.rest.calculator.model.MathOperation;
import com.witsoftware.rest.calculator.utils.Constants;
import com.witsoftware.rest.calculator.utils.Operator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.MDC;


import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class MathOperationController {

    private static final Logger logger = LoggerFactory.getLogger(MathOperationController.class);

    private RabbitTemplate rabbitTemplate;

    @Autowired
    MathOperationController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate =  rabbitTemplate;
    }

    @GetMapping("/sum")

    public ResponseEntity<MathOperationResultDTO> getSum(@RequestParam("a") int a, @RequestParam("b") int b  ){

        MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                a,
                b,
                Operator.ADDITION);

        logger.info("created mathOperation for "+Operator.ADDITION +" with uuid:" +mathOperation.getUuid() );

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.OPERATION_ROUTING_KEY,mathOperation);

        logger.info("Pushed the mathOperation "+ mathOperation.getUuid() +" to the Queue" );

        Double result = Double.valueOf(a+b);
        MDC.put("result",result.toString());
        MDC.put("request-id",mathOperation.getUuid().toString());


        return  buildResponse(mathOperation.getUuid(),result);

    }
    @GetMapping("/sub")
    public ResponseEntity<MathOperationResultDTO> getSub(@RequestParam("a") int a, @RequestParam("b") int b  ){

        MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                a,
                b,
                Operator.SUBTRACTION);

        logger.info("created mathOperation for "+Operator.SUBTRACTION +" with uuid:" +mathOperation.getUuid() );

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.OPERATION_ROUTING_KEY,mathOperation);

        logger.info("Pushed the mathOperation "+ mathOperation.getUuid() +" to the Queue" );

        Double result = Double.valueOf(a-b);
        MDC.put("result",result.toString());
        MDC.put("request-id",mathOperation.getUuid().toString());


        return  buildResponse(mathOperation.getUuid(),result);


    }
    @GetMapping("/mult")
    public ResponseEntity<MathOperationResultDTO> getMult(@RequestParam("a") int a, @RequestParam("b") int b  ){

        MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                a,
                b,
                Operator.MULTIPLICATION);
        logger.info("created mathOperation for "+Operator.SUBTRACTION +" with uuid:" +mathOperation.getUuid() );


        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.OPERATION_ROUTING_KEY,mathOperation);
        logger.info("Pushed the mathOperation "+ mathOperation.getUuid() +" to the Queue" );

        Double result = Double.valueOf(a*b);
        MDC.put("result",result.toString());
        MDC.put("request-id",mathOperation.getUuid().toString());


        return  buildResponse(mathOperation.getUuid(),result);

    }
    @GetMapping("/div")
    public ResponseEntity<MathOperationResultDTO> getDivisionOf(@RequestParam("a") int a, @RequestParam("b") int b  ){
        MathOperation mathOperation = new MathOperation(UUID.randomUUID(),
                a,
                b,
                Operator.DIVISION);
        logger.info("created mathOperation for "+Operator.DIVISION +" with uuid:" +mathOperation.getUuid() );

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.OPERATION_ROUTING_KEY,mathOperation);

        logger.info("Pushed the mathOperation "+ mathOperation.getUuid() +" to the Queue" );


        Double result = Double.valueOf(a/b);
        MDC.put("result",result.toString());
        MDC.put("request-id",mathOperation.getUuid().toString());

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
