
package server;
import labLib.Star;
import java.util.Comparator;

public class StarComparator implements Comparator<Star> {
    @Override
    public int compare(Star star1, Star star2){
        return star1.compareTo(star2);
    }
    
}
