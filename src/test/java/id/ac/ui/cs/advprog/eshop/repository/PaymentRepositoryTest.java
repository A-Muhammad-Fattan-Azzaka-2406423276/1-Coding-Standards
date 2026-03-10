package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSavePayment() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", data);
        Payment result = paymentRepository.save(payment);
        assertEquals(payment, result);
    }

    @Test
    void testGetPaymentById() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", data);
        paymentRepository.save(payment);
        Payment result = paymentRepository.getById("p-001");
        assertEquals(payment, result);
    }

    @Test
    void testGetPaymentByIdNotFound() {
        assertNull(paymentRepository.getById("nonexistent-id"));
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> data1 = new HashMap<>();
        data1.put("voucherCode", "ESHOP1234ABC5678");
        paymentRepository.save(new Payment("p-001", "VOUCHER_CODE", data1));

        Map<String, String> data2 = new HashMap<>();
        data2.put("bankName", "BCA");
        data2.put("referenceCode", "REF001");
        paymentRepository.save(new Payment("p-002", "BANK_TRANSFER", data2));

        List<Payment> result = paymentRepository.getAllPayments();
        assertEquals(2, result.size());
    }
}
