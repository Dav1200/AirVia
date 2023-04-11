import javax.swing.RowFilter;
import java.security.KeyStore;

/**
 * class to search
 */
public class Search extends RowFilter {
    private final String searchText;

    Search(String searchText){
        this.searchText = searchText;
    }

    /**
     * searches a specific column in the table
     * @param entry
     * @return all the entries of the specified search text
     */
    @Override
    public boolean include(Entry entry) {

        //get the 0th column of that row
        //return whether true or false
        return entry.getStringValue(0).indexOf(searchText) >= 0;
    }
}