package com.ruoyi.common.utils.file;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.file.FileSystemMetadataReader;
import com.ruoyi.common.utils.DateUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExifUtil {
    public static void main(String[] arge){
        File file = new File("D:\\ruoyi\\uploadPath\\upload\\2023\\10\\11\\0034034892281415_b_20231011164048A002.jpg");
        System.out.println(ExifUtil.getImageTime(file));
    }

    public static String getCreationTime(File file) {

        BasicFileAttributes attr = null;
        try {
            Path path = file.toPath();
            attr = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建时间
        Instant instant = attr.creationTime().toInstant();
        // 更新时间
//        Instant instant = attr.lastModifiedTime().toInstant();
        // 上次访问时间
//        Instant instant = attr.lastAccessTime().toInstant();
        String format = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault()).format(instant);
        return format;
    }
    private static Long getFileCreateTime(String filePath) {
        File file = new File(filePath);
        try {
            Path path = Paths.get(filePath);
            BasicFileAttributeView basicview = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
            BasicFileAttributes attr = basicview.readAttributes();
            Long lastModified = file.lastModified();
            return attr.creationTime().toMillis();
        } catch (Exception e) {
            e.printStackTrace();
            return file.lastModified();
        }
    }
    public static Map<String,Object> getImageTime(File jpegFile) {
        Map<String,Object> result = new HashMap<String,Object>();
        Metadata metadata;
        try {
            metadata = ImageMetadataReader.readMetadata(jpegFile);
            Iterator<Directory> it = metadata.getDirectories().iterator();
            while (it.hasNext()) {
                Directory exif = it.next();
                Iterator<Tag> tags = exif.getTags().iterator();
                while (tags.hasNext()) {
                    Tag tag = (Tag) tags.next();
                    switch (tag.getTagName()) {
                        case "Image Width": result.put("width",tag.getDescription().split(" ")[0]);break;
                        case "Image Height": result.put("height",tag.getDescription().split(" ")[0]);break;
                        case "Detected MIME Type": result.put("mime",tag.getDescription());break;
                        case "Expected File Name Extension": result.put("suffix",tag.getDescription());break;
                        case "File Name": result.put("name",tag.getDescription());break;
                        case "File Size": result.put("size",tag.getDescription().split(" ")[0]);break;
                        case "File Modified Date":
                            String[] dateStrs = tag.getDescription().split(" ");
                            dateStrs[4] = dateStrs[4].replace(":","");
                            SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd hh:mm:ss Z yyyy",Locale.CHINA);
                            Date date = sf.parse(String.join(" ",dateStrs));
                            result.put("date", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,date));
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
