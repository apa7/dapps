package top.apa7.dapp.utils.gaussianBlur;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import top.apa7.dapp.utils.gaussianBlur.filter.GaussianFilter;

public class GaussianBlurUtil {
    public GaussianBlurUtil(){}
    
    /**
     * @Description:生成高斯模糊图片
     * @DateTime:2017年8月1日 下午2:53:56
     * @return void
     * @throws
     */
    public static void setGaussianBlurImg(String imgPath, int filterNum) {
    	try{
            File file = new File(imgPath);
            BufferedImage b1 = ImageIO.read(file);
            GaussianFilter filter = new GaussianFilter(filterNum);
            BufferedImage blurredImage = filter.filter(b1, new BufferedImage(b1.getWidth(), b1.getHeight(), 2));
            ImageIO.write(blurredImage, "png", file);
            if(Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN))
                Desktop.getDesktop().open(file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
