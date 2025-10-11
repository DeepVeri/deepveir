package com.deepecho.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    
    public static byte[] cropAndRotateImage(MultipartFile file, int x, int y, int width, int height, int rotate) throws IOException {
        // 读取原始图片
        BufferedImage originalImage = ImageIO.read(file.getInputStream());
        
        // 创建裁剪后的图片
        BufferedImage croppedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = croppedImage.createGraphics();
        
        // 设置图片质量
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 执行裁剪
        g.drawImage(originalImage, 0, 0, width, height, x, y, x + width, y + height, null);
        g.dispose();
        
        // 如果需要旋转
        if (rotate != 0) {
            double rads = Math.toRadians(rotate);
            double sin = Math.abs(Math.sin(rads));
            double cos = Math.abs(Math.cos(rads));
            
            int w = croppedImage.getWidth();
            int h = croppedImage.getHeight();
            
            int newWidth = (int) Math.floor(w * cos + h * sin);
            int newHeight = (int) Math.floor(h * cos + w * sin);
            
            BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = rotatedImage.createGraphics();
            
            g2d.translate((newWidth - w) / 2, (newHeight - h) / 2);
            g2d.rotate(rads, w / 2, h / 2);
            g2d.drawImage(croppedImage, 0, 0, null);
            g2d.dispose();
            
            croppedImage = rotatedImage;
        }
        
        // 转换为字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(croppedImage, "png", outputStream);
        return outputStream.toByteArray();
    }
    
    public static byte[] resizeImage(byte[] imageData, int maxWidth, int maxHeight) throws IOException {
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageData));
        
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        
        // 计算新的尺寸
        int newWidth = originalWidth;
        int newHeight = originalHeight;
        
        if (originalWidth > maxWidth) {
            newWidth = maxWidth;
            newHeight = (int) ((double) originalHeight * maxWidth / originalWidth);
        }
        
        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) ((double) originalWidth * maxHeight / originalHeight);
        }
        
        // 创建新图片
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        
        // 设置图片质量
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        
        // 转换为字节数组
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", outputStream);
        return outputStream.toByteArray();
    }
} 