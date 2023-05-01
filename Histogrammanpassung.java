
public class Histogrammanpassung {
	
	// compute mapping function f():
	public int[] matchHistograms (int[] hA, int[] hR) {
		int K = hA.length;
		// get (CDF) of histograms target, reference images
		double[] PA = Cdf(hA); 
		double[] PR = Cdf(hR);
	    //function f()
		int[] fhs = new int[K]; 
		for (int a = 0; a < K; a++) {
			int j = K - 1;
			do {
				fhs[a] = j;
				j--;
			} while (j >= 0 && PA[a] <= PR[j]);
		}
		return fhs;
	}
	
	// returns (cdf) of histogram 
	public static double[] Cdf (int[] h) {
		
		int K = h.length;
		int n = 0;			
		for (int i=0; i<K; i++)	{ 	
			n = n + h[i]; 
		}
		double[] P = new double[K];
		int c = h[0];
		P[0] = (double) c / n;
		for (int i = 1; i < K; i++) {
	    	c = c + h[i];
	        P[i] = (double) c / n;
	    }
	    return P;
	}
	
	// normalize, find the max histogram entry
	public static double[] normalizeHistogram (int[] h) {
		int max = h[0];
		for (int i = 0; i < h.length; i++) {
			if (h[i] > max)
				max = h[i];
		}
		if (max == 0) return null;
		
		double[] hn = new double[h.length];
		double s = 1.0/max;
		for (int i = 0; i < h.length; i++) {
			hn[i] = s * h[i];
		}
		return hn;
	}
	
}