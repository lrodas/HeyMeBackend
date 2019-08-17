package com.cycsystems.heymebackend.output;

import com.cycsystems.heymebackend.common.BaseOutput;

public class ImagenResponse extends BaseOutput {

	private String img;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return "ImagenResponse [img=" + img + ", toString()=" + super.toString() + "]";
	}
}
