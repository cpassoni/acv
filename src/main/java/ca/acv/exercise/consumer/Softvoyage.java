package ca.acv.exercise.consumer;

import ca.acv.exercise.model.Xml;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.Util;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "default", url = "https://lib.softvoyage.com/cgi-bin", configuration = Softvoyage.Configuration.class)
public interface Softvoyage {
	@GetMapping(value = "/gate_dest_hotels.xml?code_ag=ACV&alias=ACV&language=en&tour_to_display=VAC&with_cdata=y&with_hotel_links=y")
	Xml getXml();

	class Configuration {
		@Bean
		public Decoder feignDecoder() {
			return (response, type) -> {
				String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
				JavaType javaType = TypeFactory.defaultInstance().constructType(type);
				XmlMapper xmlMapper = new XmlMapper();
				return xmlMapper.readValue(bodyStr, javaType);
			};
		}
	}
}
