package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    private static final List<String> VALID_STATUSES =
            List.of("WAITING_CONFIRMATION", "SUCCESS", "REJECTED");

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = determineInitialStatus(method, paymentData);
    }

    private String determineInitialStatus(String method, Map<String, String> paymentData) {
        if ("VOUCHER_CODE".equals(method)) {
            return validateVoucher(paymentData.get("voucherCode")) ? "SUCCESS" : "REJECTED";
        } else if ("BANK_TRANSFER".equals(method)) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");
            if (bankName == null || bankName.isEmpty() ||
                    referenceCode == null || referenceCode.isEmpty()) {
                return "REJECTED";
            }
            return "WAITING_CONFIRMATION";
        }
        return "WAITING_CONFIRMATION";
    }

    private boolean validateVoucher(String code) {
        if (code == null || code.length() != 16) return false;
        if (!code.startsWith("ESHOP")) return false;
        long digitCount = code.chars().filter(Character::isDigit).count();
        return digitCount == 8;
    }

    public void setStatus(String status) {
        if (VALID_STATUSES.contains(status)) {
            this.status = status;
        }
    }
}
