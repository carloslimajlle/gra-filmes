package com.texoit.grafilmes.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Carlos Lima on 21/10/2022
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(final String code) {
		super(code);
	}

	public BadRequestException(final String code, final Throwable origem) {
		super(code, origem);
	}
}
