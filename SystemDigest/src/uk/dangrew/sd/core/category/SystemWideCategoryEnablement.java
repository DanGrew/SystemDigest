package uk.dangrew.sd.core.category;

import uk.dangrew.kode.system.SystemWideObject;

/**
 * Global access to {@link CategoryEnablement} for a system.
 */
public class SystemWideCategoryEnablement extends SystemWideObject< CategoryEnablement >{

   private static final CategoryEnablement source = new CategoryEnablement();
   
   public SystemWideCategoryEnablement() {
      super( source );
   }//End Constructor

}//End Class
