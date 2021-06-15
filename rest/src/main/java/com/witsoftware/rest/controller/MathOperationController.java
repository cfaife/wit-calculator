package com.witsoftware.rest.controller;

import com.witsoftware.calculator.model.MathOperation;
import com.witsoftware.calculator.model.MathOperationResult;
import com.witsoftware.calculator.utils.Constants;
import com.witsoftware.calculator.utils.Operator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1")
public class MathOperationController {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    MathOperationController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate =  rabbitTemplate;
    }

    @GetMapping("/sum")
    public MathOperationResult getSum(@RequestParam("a") int a, @RequestParam("b") int b  ){

        MathOperation mathOperation = new MathOperation(UUID.randomUUID().toString(),
                a,
                b,
                Operator.ADDITION);

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.ROUTING_KEY,mathOperation);

        return new MathOperationResult();

    }
    @GetMapping("/sub")
    public MathOperationResult getSub(@RequestParam("a") int a, @RequestParam("b") int b  ){
        MathOperation mathOperation = new MathOperation(UUID.randomUUID().toString(),
                a,
                b,
                Operator.SUBTRACTION);

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.ROUTING_KEY,mathOperation);

        return new MathOperationResult();


    }
    @GetMapping("/mult")
    public MathOperationResult getMult(@RequestParam("a") int a, @RequestParam("b") int b  ){
        MathOperation mathOperation = new MathOperation(UUID.randomUUID().toString(),
                a,
                b,
                Operator.MULTIPLICATION);

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.ROUTING_KEY,mathOperation);

        return new MathOperationResult();

    }
    @GetMapping("/div")
    public MathOperationResult getDivisionOf(@RequestParam("a") int a, @RequestParam("b") int b  ){
        MathOperation mathOperation = new MathOperation(UUID.randomUUID().toString(),
                a,
                b,
                Operator.DIVISION);

        this.rabbitTemplate.convertAndSend(Constants.EXCHANGE,
                Constants.ROUTING_KEY,mathOperation);
        return new MathOperationResult();

    }
}
