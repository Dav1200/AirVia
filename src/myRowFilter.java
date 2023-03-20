import javax.swing.RowFilter;
import java.security.KeyStore;

public class myRowFilter extends RowFilter {
    private String searchText;

    myRowFilter(String searchText){
        this.searchText = searchText;
    }

    @Override
    public boolean include(Entry entry) {

        //get the 0th column of that row
        //return whether true or false
        return entry.getStringValue(0).indexOf(searchText) >= 0;
    }
}