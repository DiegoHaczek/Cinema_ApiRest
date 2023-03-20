package cinema.businesslayer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class CinemaService {

    Cinema cinema = new Cinema(9,9);
    
    public Cinema showCinema(){
        return cinema;
    }
    
    public Seat requestSeat(int row, int column){
        int index = cinema.getAvailable_seats().indexOf(new Seat(row,column));
        if (index != -1){
            Seat soldSeat = cinema.getAvailable_seats().remove(index);
            return soldSeat;
        }else{
            return null;
        }
    }

    public boolean tokenExists(String token){
        System.out.println(token);
        return cinema.getPurchased_seats().containsKey(token);
    }

    public Seat getSeat(String token){
        return cinema.getPurchased_seats().get(token);
    }

    public ResponseEntity purchaseTicket(Seat seat){
        if (seat.getRow()>9 || seat.getRow()<=0 || seat.getColumn()>9 || seat.getColumn()<=0){
            return new ResponseEntity(Map.of("error",
                    "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }else{
            Seat requestedSeat = requestSeat(seat.getRow(), seat.getColumn());
            //System.out.println(requestedSeat);
            if (requestedSeat==null){
                return new ResponseEntity(Map.of("error",
                        "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
            }else{
                try {
                    String token = UUID.randomUUID().toString();
                    cinema.getPurchased_seats().put(token,requestedSeat);
                    Map<String,Object> map = new LinkedHashMap<>();
                    map.put("token",token);
                    map.put("ticket",requestedSeat);
                    ObjectMapper objectmapper = new ObjectMapper();
                    String json = objectmapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
                   // System.out.println(cinema.getPurchased_seats());
                    return new ResponseEntity(json, HttpStatus.OK);
                }catch (Exception e){
                    System.out.println(e);}
            }   return null;
        }
    }

    public ResponseEntity returnTicket(String token) {
        String formattedToken = (token.split("\""))[3];
        if (tokenExists(formattedToken)){
            try {
                Seat seat = getSeat(formattedToken);
                cinema.getPurchased_seats().remove(formattedToken,seat);
                cinema.getAvailable_seats().add(seat);
                Map<String, Object> map = Map.of("returned_ticket", seat);
                ObjectMapper objectmapper = new ObjectMapper();
                String json = objectmapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
                return new ResponseEntity(json, HttpStatus.OK);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            return new ResponseEntity(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public ResponseEntity showStats (String password){
        if ("super_secret".equals(password)){
            try{
            final int[] income = {0};
            cinema.getPurchased_seats().forEach((k,v)-> income[0] +=v.getPrice() );
            Map<String,Integer> map = new LinkedHashMap<>();
            map.put("current_income",income[0]);
            map.put("number_of_available_seats",cinema.getAvailable_seats().size());
            map.put("number_of_purchased_tickets",cinema.getPurchased_seats().size());
            ObjectMapper objectmapper = new ObjectMapper();
            String json = objectmapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
            return new ResponseEntity(json, HttpStatus.OK);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
        }else{
            return new ResponseEntity(Map.of("error",
                    "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }
}
