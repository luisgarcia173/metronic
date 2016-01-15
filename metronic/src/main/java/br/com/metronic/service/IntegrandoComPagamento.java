package br.com.metronic.service;

public class IntegrandoComPagamento implements Runnable {

	public void run() {}
	
	/*private DeferredResult<ModelAndView> result;
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
	}*/

}
