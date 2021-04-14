package com.cycsystems.heymebackend.input;

import java.util.List;

import com.cycsystems.heymebackend.common.BaseInput;
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
public class TipoCambioRequest extends BaseInput {

	private List<TipoCambio> tipoCambios;
	private TipoCambio tipoCambio;

}
