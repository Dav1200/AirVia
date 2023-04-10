import javax.swing.*;


import javax.swing.RowFilter;
        import java.security.KeyStore;

public class RowFilterBlanks extends RowFilter {
    private final String searchText;

    RowFilterBlanks(String searchText){
        this.searchText = searchText;
    }

    @Override
    public boolean include(Entry entry) {

        //get the 0th column of that row
        //return whether true or false
        return entry.getStringValue(1).indexOf(searchText) >= 0;
    }
}