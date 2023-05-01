
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ImageProcessor;

public class PlotHistogram {
	
	static final int size = 255;

    int width =  256;
    int height = 128;  
    int base = height-1;
    int paintValue = 0;
	ImagePlus iplus;
	ImageProcessor ip;
	int[] H = new int[256];
	
     public PlotHistogram(int[] h, String title) {
		this(Histogrammanpassung.normalizeHistogram(h), title);
	}
	
	public PlotHistogram(double[] nH, String title) {
		createHistogramImage(title);
		for (int i = 0; i < nH.length; i++) {
			H[i] = (int) Math.round(height * nH[i]);
		}
		draw();
		
	}
		
	void createHistogramImage(String title) {
		//static ImagePlus createByteImage()
		iplus  = NewImage.createByteImage(title,width,height,1,0);
		//getProcessor() Returns a reference to the current ImageProcesso
		ip = iplus.getProcessor();
        ip.setValue(size);
        //void fill() Fills the image or ROI bounding rectangle with the current fill/draw value
        ip.fill();
	}
	
	void draw() {
		//void setValue​() Sets the default fill/draw value
		ip.setValue(0);
		//void drawLine​() Draws a line from (x1,y1) to (x2,y2)
		ip.drawLine(0,base,width-1,base);
		ip.setValue(paintValue);
		int u = 0;
		for (int i=0; i<H.length; i++) {
			int k = H[i];
			if (k > 0){
			ip.drawLine(u, base-1, u, base-k);
			}
			u = u + 1;
		}
	}
	
	void update() {
		//void updateAndDraw() Updates this image from the pixel data in its associated ImageProcessor
		iplus.updateAndDraw();
	}
	
	public void show() {
		//void show() Opens a window to display this image and clears the status bar
		iplus.show();
        update();
	}
	
	
	
	
	
}