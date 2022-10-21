package com.texoit.grafilmes.util;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.ZoneId;
import java.util.List;

/**
 * @author Carlos Lima on 18/10/2022
 */
@Component
@Configuration
public class Util {

	public static String LOG_PREFIX;

	public static final ZoneId ZONA_ID = ZoneId.of("America/Sao_Paulo");

	@Value("${server.undertow.accesslog.prefix}")
	public void setLogPrefix(final String logPrefix) {
		Util.LOG_PREFIX = logPrefix;
	}

	public static <T> List<T> readData(
		final String filename, final Class<T> clazz, final char separator, final boolean skipTitle)
		throws FileNotFoundException {
		return new CsvToBeanBuilder<T>(new FileReader(filename))
			.withType(clazz)
			.withSeparator(separator)
			.withSkipLines(skipTitle ? 1 : 0)
			.build()
			.parse();
	}
}
