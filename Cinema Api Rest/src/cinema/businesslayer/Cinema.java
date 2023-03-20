package cinema.businesslayer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.boot.autoconfigure.domain.EntityScan;


import java.util.*;

public class Cinema {

    private int total_rows;
    private int total_columns;
    private ArrayList<Seat> available_seats;
    @JsonIgnore
    private Map<String,Seat> purchased_seats;

    public Cinema(){}
    public Cinema(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.available_seats = new ArrayList<>();
        setAvailable_seats();
        this.purchased_seats = new HashMap<>();
    }

    public Map<String, Seat> getPurchased_seats() {
        return purchased_seats;
    }

    public ArrayList<Seat> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats() {
        for (int i = 1 ; i<total_rows+1; i++){
            for (int j = 1 ; j<total_columns+1 ; j++){
                this.available_seats.add(new Seat(i,j));
            }
        }
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("total_rows: " + total_rows).append("\ntotal_columns: " + total_columns);
        for(Seat s : this.getAvailable_seats()){
            output.append("\n" + s.toString());
        }

        return output.toString();
    }
}
