package klik.shared.rpc;

import com.gwtplatform.dispatch.shared.Result;

public class SendGreetingResult implements Result {

	private String message;
	private boolean isSetUp;

	SendGreetingResult() {
	}

	public SendGreetingResult(final String message, final boolean isSetUp) {
		this.message = message;
		this.isSetUp = isSetUp;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSetUp() {
		return isSetUp;
	}
}
