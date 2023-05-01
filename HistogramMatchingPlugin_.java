
import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class HistogramMatchingPlugin_ implements PlugInFilter { 
	
	 ImagePlus imR;
     
     public int setup(String arg0, ImagePlus imA) {
         return DOES_8G;
     }
     
     public void run(ImageProcessor ipA) {
         if (!dialog()) 
             return;
         //Refrence image
         ImageProcessor ipR = imR.getProcessor();
         
         // get histograms of Target, Refrence images
         int[] hA = ipA.getHistogram();
         int[] hR = ipR.getHistogram();
         (new PlotHistogram(hA, "H.Target Image(A)")).show(); 
         (new PlotHistogram(hR, "H.Referance Image(R)")).show();
         (new PlotHistogram(Histogrammanpassung.Cdf(hA), "Cumulative H(A)")).show(); 
         (new PlotHistogram(Histogrammanpassung.Cdf(hR), "Cumulative H(R)")).show(); 
         
         
         Histogrammanpassung m = new Histogrammanpassung();
         //mapping function fhs
         int[] F = m.matchHistograms(hA, hR);
         
 		 // modify the target image
         ipA.applyTable(F);
         int[] hAm = ipA.getHistogram();
         (new PlotHistogram(hAm, "H.modified Image(Á)")).show(); 
         (new PlotHistogram(Histogrammanpassung.Cdf(hAm), "Cumulative H(Á)")).show(); 
         
     }
     
     
  // create dialog, list of open images and get image titles
     boolean dialog() {
         
    	 //getIDList() Returns a list of the IDs of open images. Returns null if no image windows are open
         int[] windowList = WindowManager.getIDList();
         if(windowList==null){
        	 //void noImage() Displays a "no images are open" dialog box
             IJ.noImage();
             return false;
         }
         
         String[] windowTitles = new String[windowList.length];
         for (int i = 0; i < windowList.length; i++) {
             ImagePlus imp = WindowManager.getImage(windowList[i]);
             if (imp != null)
            	 //String getShortTitle()
                 windowTitles[i] = imp.getShortTitle();
             else
                 windowTitles[i] = "untitled";
         }
         
         GenericDialog gd = new GenericDialog("Select Reference Image");
         //void addChoice​() add dds a popup menu
         gd.addChoice("Reference Image:", windowTitles, windowTitles[0]);
         //void showDialog() Displays this dialog box.
         gd.showDialog(); 
         //boolean wasCanceled() Returns true if the user clicked on "Cancel"
         if (gd.wasCanceled()) 
             return false;
         else {
        	 //int getNextChoiceIndex() Returns the index of the selected item in the next popup menu
             int img2Index = gd.getNextChoiceIndex();
             imR = WindowManager.getImage(windowList[img2Index]);
             return true;
         }
     }
 
 }
