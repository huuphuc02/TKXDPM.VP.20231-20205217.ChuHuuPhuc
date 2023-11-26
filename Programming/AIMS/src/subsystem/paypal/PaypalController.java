package subsystem.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import entity.invoice.Invoice;

import java.util.ArrayList;
import java.util.List;

public class PaypalController {

    public String authorizePayment(Invoice invoice) throws PayPalRESTException {
        Payer payer = getPayerInformation(invoice);
        List<Transaction> transactionList = getTransactionInformation(invoice);
        Payment requestPayment = new Payment();
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl("http://127.0.0.1:5500/confirm.html");
        redirectUrls.setCancelUrl("http://127.0.0.1:5500/fail.html");
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setTransactions(transactionList);

        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        APIContext apiContext = new APIContext(Config.CLIENT_ID,Config.CLIENT_SECRET,Config.MODE);
        Payment approvedPayment = new Payment();
        try {
            approvedPayment = requestPayment.create(apiContext);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return getApprovalLink(approvedPayment);
    }

    private Payer getPayerInformation(Invoice invoice) {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName((String) invoice.getOrder().getDeliveryInfo().get("name"));
        payerInfo.setEmail((String) invoice.getOrder().getDeliveryInfo().get("email"));

        payer.setPayerInfo(payerInfo);

        return payer;
    }

    private List<Transaction> getTransactionInformation(Invoice invoice) {
        Details details = new Details();
        details.setShipping(String.valueOf(invoice.getOrder().getShippingFees()));
        details.setSubtotal(String.valueOf(invoice.getOrder().getAmount()));
        details.setTax("0");
        System.out.println(invoice.getOrder().getShippingFees());
        System.out.println(invoice.getOrder().getAmount());
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(invoice.getOrder().getShippingFees()+invoice.getOrder().getAmount()));
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("pay order");

        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(transaction);

        return listTransaction;
    }

    private String getApprovalLink(Payment approvedPayment) {
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;

        for (Links link : links) {
            if (link.getRel().equalsIgnoreCase("approval_url")) {
                approvalLink = link.getHref();
                break;
            }
        }

        return approvalLink;
    }
}
