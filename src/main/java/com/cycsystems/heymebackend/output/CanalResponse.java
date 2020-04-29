package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Canal;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CanalResponse extends BaseOutput {

    private Canal canal;
    private List<Canal> canals;
}
