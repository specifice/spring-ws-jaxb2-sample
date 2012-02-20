package net.adsoko.ws.type;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "sampleRequest")
public class SampleRequest {

	@XmlElement
	private String request = null;

	@XmlTransient
	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
}
