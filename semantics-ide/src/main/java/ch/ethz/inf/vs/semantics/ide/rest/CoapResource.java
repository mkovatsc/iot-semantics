package ch.ethz.inf.vs.semantics.ide.rest;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

@Component
@RestxResource
public class CoapResource {

	@POST("/coap")
	@PermitAll
	public CoapResponseJson runRequest(CoapRequest req) {
		CoapClient c = new CoapClient();
		c.setURI(req.getUri());
		if (req.getMethod().toUpperCase().equals("PUT")) {
			return new CoapResponseJson(c.put(req.getReqBody(), MediaTypeRegistry.TEXT_PLAIN));
		} else if (req.getMethod().toUpperCase().equals("POST")) {
			return new CoapResponseJson(c.post(req.getReqBody(), MediaTypeRegistry.TEXT_PLAIN));
		}
		return new CoapResponseJson(c.get());
	}

	static class CoapRequest {
		private String method;
		private String uri;
		private String reqBody;

		public String getReqBody() {
			return reqBody;
		}

		public void setReqBody(String reqBody) {
			this.reqBody = reqBody;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}
	}

	static class CoapResponseJson {
		private boolean success;
		private String status;
		private String payload;
		private String reqBody;

		public CoapResponseJson(CoapResponse coapResponse) {
			if (coapResponse == null) {
				status = "TIMEOUT";
				payload = "";
				success = false;
			} else {

				status = coapResponse.getCode().name();
				payload = coapResponse.getResponseText();
				success = coapResponse.isSuccess();
			}
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public boolean isSuccess() {
			return success;
		}

		public void setSuccess(boolean success) {
			this.success = success;
		}

		public String getPayload() {
			return payload;
		}

		public void setPayload(String payload) {
			this.payload = payload;
		}

		public String getReqBody() {
			return reqBody;
		}

		public void setReqBody(String reqBody) {
			this.reqBody = reqBody;
		}
	}
}
