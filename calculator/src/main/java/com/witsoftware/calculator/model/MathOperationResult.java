package com.witsoftware.calculator.model;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class MathOperationResult implements Serializable {

    public String operationId;

    private Double result;

}
