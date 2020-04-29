package com.cycsystems.heymebackend.input;

import com.cycsystems.heymebackend.common.BaseInput;
import com.cycsystems.heymebackend.common.Canal;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CanalRequest extends BaseInput {

    private Canal canal;
    private List<Canal> canals;
}
