package com.rentme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.rentme.data_transfer_objects.PaymentConfirmationRequest;
import com.rentme.model.Invoice;
import com.rentme.model.Notification;
import com.rentme.model.NotificationType;
import com.rentme.model.Rental;
import com.rentme.model.RentalStatus;
import com.rentme.repository.InvoiceRepository;
import com.rentme.repository.NotificationRepository;
import com.rentme.repository.RentalRepository;
import com.rentme.service.NotificationService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

  private final BraintreeGateway gateway;
  private final RentalRepository rentalRepository;
  private final NotificationRepository notificationRepository;
  private final NotificationService notificationService;
  private final InvoiceRepository invoiceRepository;

  @Autowired
  public PaymentController(BraintreeGateway gateway, RentalRepository rentalRepository,
      NotificationRepository notificationRepository, NotificationService notificationService,
      InvoiceRepository invoiceRepository) {
    this.gateway = gateway;
    this.rentalRepository = rentalRepository;
    this.notificationRepository = notificationRepository;
    this.notificationService = notificationService;
    this.invoiceRepository = invoiceRepository;
  }

  @GetMapping("/token")
  public ResponseEntity<String> generateClientToken() {
    String clientToken = gateway.clientToken().generate();
    return ResponseEntity.ok(clientToken);
  }

  @PostMapping("/confirm")
  public ResponseEntity<?> confirmPayment(@RequestBody PaymentConfirmationRequest request) {
    TransactionRequest transactionRequest =
        new TransactionRequest().amount(new java.math.BigDecimal(request.getAmount()))
            .paymentMethodNonce(request.getNonce()).options().submitForSettlement(true).done();

    Result<Transaction> result = gateway.transaction().sale(transactionRequest);

    if (result.isSuccess()) {// payment succeed:
      // تحديث حالة الإيجار

      Rental rental = rentalRepository.findById(request.getRentalId())
          .orElseThrow(() -> new RuntimeException("Rental not found"));

      rental.setStatus(RentalStatus.PAID);
      rentalRepository.save(rental);
      Notification paidNotif = notificationRepository
          .findByTypeAndRelatedId(NotificationType.RENTAL_APPROVED, rental.getId()).get(0);
      paidNotif.setIsPaid(true);
      notificationRepository.save(paidNotif);
      int days = rental.getEndDate().compareTo(rental.getStartDate());
      double pricePerDay = rental.getTotalPrice() / (days * 1.02);
      double subtotal = pricePerDay * days;
      double serviceFee = rental.getTotalPrice() - subtotal;

      Invoice invoice = new Invoice();
      invoice.setInvoiceNumber("INV-" + System.currentTimeMillis() % 100000);
      invoice.setToolName(rental.getToolName());
      invoice.setToolPicUrl(rental.getToolPic());
      invoice.setRenterName(rental.getRenterName());
      invoice.setRenterId(rental.getRenterId());
      invoice.setUserId(rental.getRenterId());
      invoice.setStartDate(rental.getStartDate());
      invoice.setEndDate(rental.getEndDate());
      invoice.setDays(days);
      invoice.setPricePerDay(pricePerDay);
      invoice.setSubtotal(subtotal);
      invoice.setServiceFee(serviceFee);
      invoice.setTotalPrice(rental.getTotalPrice());

      invoiceRepository.save(invoice);

      return ResponseEntity.ok("Payment successful, Invoice id:" + invoice.getId());
    } else {
      return ResponseEntity.badRequest().body("Payment failed: " + result.getMessage());
    }
  }
}
