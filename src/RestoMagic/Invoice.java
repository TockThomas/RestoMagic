package RestoMagic;

import java.time.LocalDateTime;

public class Invoice {
    private int invoiceID;
    private Order order;
    private LocalDateTime timestamp;
    private double totalPrice;

    private String generateInvoiceText(){
        return "";
    }
}
