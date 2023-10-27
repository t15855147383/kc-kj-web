package com.ruoyi.controller;

import com.ruoyi.business.domain.TImages;
import com.ruoyi.business.mapper.TImagesMapper;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.core.redis.RedisKey;
import com.ruoyi.common.utils.file.ExifUtil;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.framework.config.RedisConfig;
/*import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;*/
import com.ruoyi.kafka.service.KafkaProducerService;
import org.apache.commons.codec.binary.Base64;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kafka")
public class UserController extends BaseController {
    @Autowired
    private KafkaProducerService kafkaProducerService;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private RedisCache redisCache;
    @RequestMapping("/setRedis")
    private void setRedis(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name","123");
        map.put("test","345");
        redisCache.setCacheMap(RedisKey.TEST_001,map);
    }
    @RequestMapping("/getRedis")
    private Map<String,Object> getRedis(){
        Map<String,Object> map = redisCache.getCacheMap(RedisKey.TEST_001);
        redisCache.deleteObject(RedisKey.TEST_001);
        return map;
    }

    private String topicName="kc-kj-web-test";

    @RequestMapping("/cotextLoads")
    public void cotextLoads() {
        kafkaProducerService.sendMessage(topicName, "测试");
    }

    @RequestMapping("/sendMessage1")
    public void sendMessage1() {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topicName, 0, System.currentTimeMillis(), "topic-key", "测试");
        producerRecord.headers().add("user", "zhangsan".getBytes());
        kafkaProducerService.sendMessage(producerRecord);
    }

    @RequestMapping("/sendMessage2")
    public void sendMessage2() {
        String event = "测试";
        Map<String, Object> map = new HashMap<>();
        map.put("user", "zhangsan");
        MessageHeaders headers = new MessageHeaders(map);
        Message<String> message = MessageBuilder.createMessage(event, headers);
        kafkaTemplate.setDefaultTopic(topicName);
        kafkaProducerService.sendMessage(message);
    }

    @RequestMapping("/upload")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file)
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            int lastUnixPos = fileName.lastIndexOf('/');
            int lastWindowsPos = fileName.lastIndexOf('\\');
            int index = Math.max(lastUnixPos, lastWindowsPos);
            String thumbnailFileName = fileName.substring(0,index) + "/thumbnail_" + fileName.substring(index + 1);
            System.out.println(thumbnailFileName);

            File source_file = new File(filePath + "/" + fileName);
            Image source_src = javax.imageio.ImageIO.read(source_file);
            int width = source_src.getWidth(null);
            int height = source_src.getHeight(null);
            if(width > 380){
              height = new BigDecimal("380.00000").divide(new BigDecimal(String.valueOf(width)), RoundingMode.HALF_UP).multiply(new BigDecimal(String.valueOf(height))).intValue();
              width = 380;
            }
            BufferedImage thumbnail_tag = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            thumbnail_tag.getGraphics().drawImage(source_src,0,0,width,height,null);
            FileOutputStream out=new FileOutputStream(filePath + "/" + thumbnailFileName);
            /*JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(thumbnail_tag);*/
            out.close();

            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", "http://localhost/dev-api/api/kafka/getFile?name=" + fileName);
            ajax.put("thumbnail","http://localhost/dev-api/api/kafka/getFile?name=" + thumbnailFileName);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            File file_ = new File(file.getOriginalFilename());
            org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), file_);
            ajax.put("exif", ExifUtil.getImageTime(file_));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
    @RequestMapping("/getFile")
    public byte[] getFile(@RequestParam("name") String name)
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            File file = new File(filePath + "/" + name);
            byte[] by = new byte[(int) file.length()];
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
            return by;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
