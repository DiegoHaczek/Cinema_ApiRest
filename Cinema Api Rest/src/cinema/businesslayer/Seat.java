package cinema.businesslayer;


public class Seat{

    private int row;
    private int column;
    private int price;

    public Seat(){}
    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = (row <= 4) ? 10 : 8 ;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object s){
        if (this.row == ((Seat) s).getRow()){
            if(this.column == ((Seat) s).getColumn()){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "row: " + row
                + "\ncolumn: " + column
                + "\nprice: " + price;
    }

}