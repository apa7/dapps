package top.apa7.dapp.utils.gaussianBlur.filter;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;

public class ConvolveFilter extends AbstractBufferedImageOp {
	
	static final long serialVersionUID = 2239251672685254626L;
	public static int ZERO_EDGES = 0;
	public static int CLAMP_EDGES = 1;
	public static int WRAP_EDGES = 2;
	protected Kernel kernel;
	public boolean alpha;
	private int edgeAction;

	public ConvolveFilter() {
		this(new float[9]);
	}

	public ConvolveFilter(float matrix[]) {
		this(new Kernel(3, 3, matrix));
	}

	public ConvolveFilter(int rows, int cols, float matrix[]) {
		this(new Kernel(cols, rows, matrix));
	}

	public ConvolveFilter(Kernel kernel) {
		this.kernel = null;
		alpha = true;
		edgeAction = CLAMP_EDGES;
		this.kernel = kernel;
	}

	public void setKernel(Kernel kernel) {
		this.kernel = kernel;
	}

	public Kernel getKernel() {
		return kernel;
	}

	public void setEdgeAction(int edgeAction) {
		this.edgeAction = edgeAction;
	}

	public int getEdgeAction() {
		return edgeAction;
	}

	public BufferedImage filter(BufferedImage src, BufferedImage dst) {
		int width = src.getWidth();
		int height = src.getHeight();
		if (dst == null)
			dst = createCompatibleDestImage(src, null);
		int inPixels[] = new int[width * height];
		int outPixels[] = new int[width * height];
		getRGB(src, 0, 0, width, height, inPixels);
		convolve(kernel, inPixels, outPixels, width, height, alpha, edgeAction);
		setRGB(dst, 0, 0, width, height, outPixels);
		return dst;
	}

	public BufferedImage createCompatibleDestImage(BufferedImage src,
			ColorModel dstCM) {
		if (dstCM == null)
			dstCM = src.getColorModel();
		return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(
				src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(),
				null);
	}

	public Rectangle2D getBounds2D(BufferedImage src) {
		return new Rectangle(0, 0, src.getWidth(), src.getHeight());
	}

	public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
		if (dstPt == null)
			dstPt = new Point2D.Double();
		dstPt.setLocation(srcPt.getX(), srcPt.getY());
		return dstPt;
	}

	public RenderingHints getRenderingHints() {
		return null;
	}

	public static void convolve(Kernel kernel, int inPixels[], int outPixels[],
			int width, int height, int edgeAction) {
		convolve(kernel, inPixels, outPixels, width, height, true, edgeAction);
	}

	public static void convolve(Kernel kernel, int inPixels[], int outPixels[],
			int width, int height, boolean alpha, int edgeAction) {
		if (kernel.getHeight() == 1)
			convolveH(kernel, inPixels, outPixels, width, height, alpha,
					edgeAction);
		else if (kernel.getWidth() == 1)
			convolveV(kernel, inPixels, outPixels, width, height, alpha,
					edgeAction);
		else
			convolveHV(kernel, inPixels, outPixels, width, height, alpha,
					edgeAction);
	}

	public static void convolveHV(Kernel kernel, int inPixels[],
			int outPixels[], int width, int height, boolean alpha,
			int edgeAction) {
		int index = 0;
		float matrix[] = kernel.getKernelData(null);
		int rows = kernel.getHeight();
		int cols = kernel.getWidth();
		int rows2 = rows / 2;
		int cols2 = cols / 2;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float r = 0.0F;
				float g = 0.0F;
				float b = 0.0F;
				float a = 0.0F;
				for (int row = -rows2; row <= rows2; row++) {
					int iy = y + row;
					int ioffset;
					if (iy >= 0 && iy < height)
						ioffset = iy * width;
					else if (edgeAction == CLAMP_EDGES) {
						ioffset = y * width;
					} else {
						if (edgeAction != WRAP_EDGES)
							continue;
						ioffset = ((iy + height) % height) * width;
					}
					int moffset = cols * (row + rows2) + cols2;
					for (int col = -cols2; col <= cols2; col++) {
						float f = matrix[moffset + col];
						if (f == 0.0F)
							continue;
						int ix = x + col;
						if (ix < 0 || ix >= width)
							if (edgeAction == CLAMP_EDGES) {
								ix = x;
							} else {
								if (edgeAction != WRAP_EDGES)
									continue;
								ix = (x + width) % width;
							}
						int rgb = inPixels[ioffset + ix];
						a += f * (float) (rgb >> 24 & 255);
						r += f * (float) (rgb >> 16 & 255);
						g += f * (float) (rgb >> 8 & 255);
						b += f * (float) (rgb & 255);
					}

				}

				int ia = alpha ? PixelUtils.clamp((int) ((double) a + 0.5D))
						: 255;
				int ir = PixelUtils.clamp((int) ((double) r + 0.5D));
				int ig = PixelUtils.clamp((int) ((double) g + 0.5D));
				int ib = PixelUtils.clamp((int) ((double) b + 0.5D));
				outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
			}

		}

	}

	public static void convolveH(Kernel kernel, int inPixels[],
			int outPixels[], int width, int height, boolean alpha,
			int edgeAction) {
		int index = 0;
		float matrix[] = kernel.getKernelData(null);
		int cols = kernel.getWidth();
		int cols2 = cols / 2;
		for (int y = 0; y < height; y++) {
			int ioffset = y * width;
			for (int x = 0; x < width; x++) {
				float r = 0.0F;
				float g = 0.0F;
				float b = 0.0F;
				float a = 0.0F;
				int moffset = cols2;
				for (int col = -cols2; col <= cols2; col++) {
					float f = matrix[moffset + col];
					if (f != 0.0F) {
						int ix = x + col;
						if (ix < 0) {
							if (edgeAction == CLAMP_EDGES)
								ix = 0;
							else if (edgeAction == WRAP_EDGES)
								ix = (x + width) % width;
						} else if (ix >= width)
							if (edgeAction == CLAMP_EDGES)
								ix = width - 1;
							else if (edgeAction == WRAP_EDGES)
								ix = (x + width) % width;
						int rgb = inPixels[ioffset + ix];
						a += f * (float) (rgb >> 24 & 255);
						r += f * (float) (rgb >> 16 & 255);
						g += f * (float) (rgb >> 8 & 255);
						b += f * (float) (rgb & 255);
					}
				}

				int ia = alpha ? PixelUtils.clamp((int) ((double) a + 0.5D))
						: 255;
				int ir = PixelUtils.clamp((int) ((double) r + 0.5D));
				int ig = PixelUtils.clamp((int) ((double) g + 0.5D));
				int ib = PixelUtils.clamp((int) ((double) b + 0.5D));
				outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
			}

		}

	}

	public static void convolveV(Kernel kernel, int inPixels[],
			int outPixels[], int width, int height, boolean alpha,
			int edgeAction) {
		int index = 0;
		float matrix[] = kernel.getKernelData(null);
		int rows = kernel.getHeight();
		int rows2 = rows / 2;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				float r = 0.0F;
				float g = 0.0F;
				float b = 0.0F;
				float a = 0.0F;
				for (int row = -rows2; row <= rows2; row++) {
					int iy = y + row;
					int ioffset;
					if (iy < 0) {
						if (edgeAction == CLAMP_EDGES)
							ioffset = 0;
						else if (edgeAction == WRAP_EDGES)
							ioffset = ((y + height) % height) * width;
						else
							ioffset = iy * width;
					} else if (iy >= height) {
						if (edgeAction == CLAMP_EDGES)
							ioffset = (height - 1) * width;
						else if (edgeAction == WRAP_EDGES)
							ioffset = ((y + height) % height) * width;
						else
							ioffset = iy * width;
					} else {
						ioffset = iy * width;
					}
					float f = matrix[row + rows2];
					if (f != 0.0F) {
						int rgb = inPixels[ioffset + x];
						a += f * (float) (rgb >> 24 & 255);
						r += f * (float) (rgb >> 16 & 255);
						g += f * (float) (rgb >> 8 & 255);
						b += f * (float) (rgb & 255);
					}
				}

				int ia = alpha ? PixelUtils.clamp((int) ((double) a + 0.5D))
						: 255;
				int ir = PixelUtils.clamp((int) ((double) r + 0.5D));
				int ig = PixelUtils.clamp((int) ((double) g + 0.5D));
				int ib = PixelUtils.clamp((int) ((double) b + 0.5D));
				outPixels[index++] = ia << 24 | ir << 16 | ig << 8 | ib;
			}

		}

	}

	public String toString() {
		return "Blur/Convolve...";
	}
}
