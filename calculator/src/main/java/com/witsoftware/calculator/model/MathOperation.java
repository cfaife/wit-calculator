package com.witsoftware.calculator.model;

import com.witsoftware.calculator.utils.Operator;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class MathOperation implements Serializable {

    private String uuid;

    private int a;

    private int b;

    private Operator operator;

}
