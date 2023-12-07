package backend;

public class Date {

    private int day;
    private int month;
    private int year;

    public Date(int d1, int m1, int y1){
         this.day = d1;
         this.month = m1;
         this.year = y1;
    }

    public void setDate(int d1,int m1, int y1){
     this.day = d1;
     this.month = m1;
     this.year = y1;
    }

    public int getDay(){
        return day;
    }

    public int getMonth(){
        return month;
    }

    public int getYear(){
        return year;
    }

    public String getDate(){
        return String.format("%d/%d/%d", day,month,year);
    }

    public static Date compareDates(Date d1,Date d2){
        if(d1.getYear()>d1.getYear()){
            return d1;
         }
         else if(d2.getYear()>d2.getYear()){
           return d2;
         }
         else{
          if(d1.getMonth()>d2.getMonth()){
           return d1;
          }
          else if(d2.getMonth()>d1.getMonth()){
            return d2;
          }
          else{
            if(d1.getDay()>d2.getDay()){
                return d1;
               }
            else{
                 return d2;
               }
          }
    }
}

    public String toString(){
        return String.format("%d/%d/%d", day,month,year);
    }

    
}
