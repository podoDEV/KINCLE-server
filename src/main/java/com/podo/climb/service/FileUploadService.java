package com.podo.climb.service;

import com.podo.climb.model.response.FileUploadResponse;
import com.podo.climb.secure.SecureData;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Calendar;

@Service
public class FileUploadService {


    private final SecureData secureData;
    private static final String prefixUrl = "/img/";

    @Value("${thumbnail.width}")
    private int thumbNailWidth;
    @Value("${thumbnail.height}")
    private int thumbNailHeight;

    public FileUploadService(SecureData secureData) {
        this.secureData = secureData;
    }

    public FileUploadResponse restoreProfileImage(MultipartFile multipartFile) {
        try (InputStream in = multipartFile.getInputStream()) {
            BufferedImage originalImage = ImageIO.read(in);
            String originFilename = multipartFile.getOriginalFilename();
            String extName
                    = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
            String saveFileName = genSaveFileName(extName);

            try (FileOutputStream fos = new FileOutputStream(secureData.getFilePath() + saveFileName)) {
                Thumbnails.of(originalImage).size(thumbNailWidth, thumbNailHeight).outputFormat("png").toOutputStream(fos);
                fos.close();
                String server = InetAddress.getLocalHost().getHostAddress() + ":8080";
                String url = server + prefixUrl + saveFileName;
                return new FileUploadResponse(url);
            }
        } catch (IOException io) {
            throw new RuntimeException();
        }
    }

    public FileUploadResponse restore(MultipartFile multipartFile) {
        String url;
        try {
            // 파일 정보
            String originFilename = multipartFile.getOriginalFilename();
            String extName
                    = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());

            // 서버에서 저장 할 파일 이름
            String saveFileName = genSaveFileName(extName);

            writeFile(multipartFile, saveFileName);
            String server = InetAddress.getLocalHost().getHostAddress() + ":8080";
            url = server + prefixUrl + saveFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new FileUploadResponse(url);
    }


    // 현재 시간을 기준으로 파일 이름 생성
    private String genSaveFileName(String extName) {
        String fileName = "";

        Calendar calendar = Calendar.getInstance();
        fileName += calendar.get(Calendar.YEAR);
        fileName += calendar.get(Calendar.MONTH);
        fileName += calendar.get(Calendar.DATE);
        fileName += calendar.get(Calendar.HOUR);
        fileName += calendar.get(Calendar.MINUTE);
        fileName += calendar.get(Calendar.SECOND);
        fileName += calendar.get(Calendar.MILLISECOND);
        fileName += extName;

        return fileName;
    }


    // 파일을 실제로 write 하는 메서드
    private boolean writeFile(MultipartFile multipartFile, String saveFileName)
            throws IOException {
        boolean result = false;
        byte[] data = multipartFile.getBytes();
        try (FileOutputStream fos = new FileOutputStream(secureData.getFilePath() + saveFileName)) {
            fos.write(data);
        }
        return result;
    }
}
