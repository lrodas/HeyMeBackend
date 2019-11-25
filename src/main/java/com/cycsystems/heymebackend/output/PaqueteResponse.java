package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.Consumo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaqueteResponse extends BaseOutput {

	private Consumo consumo;
}
