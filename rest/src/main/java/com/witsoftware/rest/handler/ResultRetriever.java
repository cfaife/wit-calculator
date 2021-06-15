package com.witsoftware.rest.handler;

import com.witsoftware.rest.calculator.model.MathOperation;
import com.witsoftware.rest.calculator.model.MathOperationResult;
import com.witsoftware.rest.calculator.utils.Constants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class ResultRetriever {

    //@RabbitListener(queues = Constants.RESULT_QUEUE)
    public MathOperationResult getResult(MathOperationResult mathOperationResult){
     return  mathOperationResult;
    }
}
