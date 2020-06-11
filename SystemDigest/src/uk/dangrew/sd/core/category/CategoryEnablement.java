package uk.dangrew.sd.core.category;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import uk.dangrew.kode.observable.PrivatelyModifiableObservableListImpl;

/**
 * Provides a mapping of the {@link Category}s that are enabled, or disabled, at any given time.
 */
public class CategoryEnablement {
   
   private final Map< Category, Boolean > enablement;
   private final ObservableList< Category > categories;
   private final ObservableList< Category > publicCategories;
   
   CategoryEnablement() {
      this.enablement = new HashMap<>();
      this.categories = FXCollections.observableArrayList();
      this.publicCategories = new PrivatelyModifiableObservableListImpl<>( categories );
   }//End Constructor
   
   public ObservableList< Category > categories() {
      return publicCategories;
   }//End Method

   public boolean isEnabled( Category category ) {
      if ( !enablement.containsKey( category ) ) {
         return false;
      }
      return enablement.get( category );
   }//End Method

   public void setEnabled( Category category, boolean enabled ) {
      enablement.put( category, enabled );
   }//End Method

   void register( Category category ) {
      if ( categories.contains( category ) ) {
         return;
      }
      categories.add( category );
   }//End Method

}//End Class
