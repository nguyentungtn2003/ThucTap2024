package com.cinema.demo.booking_apis.apis;

import com.cinema.demo.booking_apis.dtos.BookingRequestDTO;
import com.cinema.demo.booking_apis.services.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bills")
public class BillApi {
    @Autowired
    private IInvoiceService invoiceService;

    @PostMapping("/create-new-bill")
    public ResponseEntity<String> createNewBill(@RequestBody BookingRequestDTO bookingRequestDTO) {
        try {
            invoiceService.createNewInvoice(bookingRequestDTO);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>("Bạn đã mua vé xem phim thành công !", HttpStatus.OK);
    }
}
