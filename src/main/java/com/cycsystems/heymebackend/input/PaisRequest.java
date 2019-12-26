package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Pais;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PaisRequest extends BaseInput {

    private Pais pais;
}
