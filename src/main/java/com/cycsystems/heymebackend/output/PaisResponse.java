package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Pais;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaisResponse extends BaseOutput {
    private Pais pais;
    private List<Pais> paises;
}
