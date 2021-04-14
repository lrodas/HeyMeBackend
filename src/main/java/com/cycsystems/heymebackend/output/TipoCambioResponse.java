package com.cycsystems.heymebackend.output;

import java.util.List;

import com.cycsystems.heymebackend.common.BaseOutput;
import com.cycsystems.heymebackend.common.TipoCambio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TipoCambioResponse extends BaseOutput {

	private List<TipoCambio> tipoCambios;
	private TipoCambio tipoCambio;

}
