package cinema.presentation;

import cinema.businesslayer.Cinema;
import cinema.businesslayer.CinemaService;
import cinema.businesslayer.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @GetMapping("/seats")
    public Cinema getCinema(){
        return cinemaService.showCinema();
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseTicket(@RequestBody Seat seat) {
        return cinemaService.purchaseTicket(seat);
    }

    @PostMapping("/return")
    public ResponseEntity returnTicket(@RequestBody String token) {
        return cinemaService.returnTicket(token);
    }

    @PostMapping("/stats")
    public ResponseEntity showStats(@RequestParam(required = false) String password) {
        return cinemaService.showStats(password);
    }

}
