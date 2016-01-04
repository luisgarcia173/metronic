package br.com.metronic.service;

import java.math.BigDecimal;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.metronic.models.PaymentData;

public class IntegrandoComPagamento implements Runnable {

	private DeferredResult<ModelAndView> result;
	private BigDecimal value;
	private RestTemplate restTemplate;

	public IntegrandoComPagamento(DeferredResult<ModelAndView> result, BigDecimal value, RestTemplate restTemplate) {
		super();
		this.result = result;
		this.value = value;
		this.restTemplate = restTemplate;
	}

	@Override
	public void run() {
		ModelAndView modelAndView = new ModelAndView("payment/success");
		String uriToPay = "http://book-payment.herokuapp.com/payment"; //REST service for payments, allow max R$ 500 per transaction
		try {
			String response = restTemplate.postForObject(uriToPay, new PaymentData(value), String.class);
			modelAndView.addObject("paymentMessage", response);
		} catch (HttpClientErrorException exception) {
			modelAndView.addObject("paymentMessage", "Transação não foi aceita pela operadora");
		}
		result.setResult(modelAndView);
	}

}
