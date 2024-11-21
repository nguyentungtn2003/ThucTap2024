package com.cinema.demo.booking_apis.apis;


import com.cinema.demo.booking_apis.dtos.TicketDTO;
import com.cinema.demo.booking_apis.services.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/tickets")
public class TicketApi {
    @Autowired
    private ITicketService ticketService;

    @GetMapping
    public List<TicketDTO> getTicketsByUserId(@RequestParam Long userId){
        return ticketService.getTicketsByUserId(userId);
    }
}
