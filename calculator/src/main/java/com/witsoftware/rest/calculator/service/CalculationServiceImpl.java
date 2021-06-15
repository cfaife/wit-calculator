package com.witsoftware.rest.calculator.service;

import com.witsoftware.rest.calculator.model.MathOperation;
import com.witsoftware.rest.calculator.model.MathOperationResult;
import com.witsoftware.rest.calculator.utils.Constants;
import com.witsoftware.rest.calculator.utils.Operator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalculationServiceImpl implements  CalculationService{


    private ResultSenderService resultSenderService;

    @Autowired
    public  CalculationServiceImpl(ResultSenderService resultSenderService){
        this.resultSenderService = resultSenderService;
    }

    @Override
    @RabbitListener(queues = Constants.OPERATION_QUEUE)
    public void processMathOperation(MathOperation mathOperation) {

        System.out.println("#################### #### >>>>>>>>>>>>>>>");
        Optional<MathOperationResult> mathOperationResult =
                this.operate(mathOperation);

        mathOperationResult.ifPresent(x->{
            resultSenderService.send(mathOperationResult.get());
        });
    }

    private Optional<MathOperationResult> operate(MathOperation mathOperation){
        if(mathOperation.getOperator()== Operator.ADDITION){
            return Optional.of(new MathOperationResult(mathOperation.getUuid(),
                    Double.parseDouble(mathOperation.getA() + mathOperation.getB()+"")
            ));
        }

        if(mathOperation.getOperator()== Operator.SUBTRACTION){
            return Optional.of(new MathOperationResult(mathOperation.getUuid(),
                    Double.parseDouble(mathOperation.getA() - mathOperation.getB()+"")
            ));
        }

        if(mathOperation.getOperator()== Operator.MULTIPLICATION){
            return Optional.of( new MathOperationResult(mathOperation.getUuid(),
                    Double.parseDouble(mathOperation.getA() * mathOperation.getB()+"")
            ));
        }

        if(mathOperation.getOperator()== Operator.DIVISION){
            if(mathOperation.getB()==0){
                throw new IllegalArgumentException("Operating not acceptable");
            }
            return Optional.of( new MathOperationResult(mathOperation.getUuid(),
                    Double.parseDouble(mathOperation.getA() / mathOperation.getB()+"")
            ));
        }
        return Optional.empty();
    }
}
