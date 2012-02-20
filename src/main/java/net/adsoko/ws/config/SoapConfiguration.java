package net.adsoko.ws.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.server.endpoint.adapter.AbstractMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.DefaultMethodEndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.method.MarshallingPayloadMethodProcessor;
import org.springframework.ws.server.endpoint.adapter.method.MethodArgumentResolver;
import org.springframework.ws.server.endpoint.adapter.method.MethodReturnValueHandler;
import org.springframework.ws.transport.http.WsdlDefinitionHandlerAdapter;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.XsdSchemaCollection;
import org.springframework.xml.xsd.commons.CommonsXsdSchemaCollection;

@Configuration
public class SoapConfiguration {

	/**
	 * The JAXB 2 Marshaller used by the endpoint(s).
	 * 
	 * @return
	 */
	@Bean
	public Jaxb2Marshaller payloadMarshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath(ClassUtils.getPackageName(net.adsoko.ws.type.Marker.class));
		marshaller.setSchemas(schemaResources());
		return marshaller;
	}

	@Bean
	public MarshallingPayloadMethodProcessor payloadMethodProcessor() {
		MarshallingPayloadMethodProcessor processor = new MarshallingPayloadMethodProcessor();
		processor.setMarshaller(payloadMarshaller());
		processor.setUnmarshaller(payloadMarshaller());
		return processor;
	}

	@Bean
	public AbstractMethodEndpointAdapter methodEndpointAdapter() {
		DefaultMethodEndpointAdapter adapter = new DefaultMethodEndpointAdapter();
		adapter.setMethodArgumentResolvers(getMethodArgumentResolvers());
		adapter.setMethodReturnValueHandlers(getMethodReturnValueHandlers());
		return adapter;
	}

	final private List<MethodArgumentResolver> getMethodArgumentResolvers() {
		List<MethodArgumentResolver> resolvers = new ArrayList<MethodArgumentResolver>();
		resolvers.add(payloadMethodProcessor());
		return resolvers;
	}

	final private List<MethodReturnValueHandler> getMethodReturnValueHandlers() {
		List<MethodReturnValueHandler> handlers = new ArrayList<MethodReturnValueHandler>();
		handlers.add(payloadMethodProcessor());
		return handlers;
	}

	@Bean
	public WsdlDefinitionHandlerAdapter wsdlDefinitionHandlerAdapter() {
		WsdlDefinitionHandlerAdapter adapter = new WsdlDefinitionHandlerAdapter();
		adapter.setTransformLocations(true);
		return adapter;
	}

	@Bean
	public Wsdl11Definition wsdl11Definition() {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setRequestSuffix("Request");
		definition.setResponseSuffix("Response");
		definition.setSchemaCollection(schemaCollection());
		definition.setPortTypeName("Sample");
		definition.setLocationUri("/sample");
		definition.setTargetNamespace("http://adsoko.net/ws/2012/Core");
		return definition;
	}

	@Bean
	public Resource[] schemaResources() {
		return new ClassPathResource[] { new ClassPathResource("/xsd/adsoko-ws-core.xsd") };
	}

	@Bean
	public XsdSchemaCollection schemaCollection() {
		CommonsXsdSchemaCollection collection = new CommonsXsdSchemaCollection();
		collection.setXsds(schemaResources());
		collection.setInline(true);
		return collection;
	}
}
