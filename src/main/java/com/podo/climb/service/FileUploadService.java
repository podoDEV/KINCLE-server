package com.podo.climb.service;

import com.podo.climb.model.response.FileUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

@Service
public class FileUploadService {

    @Value("${file.path}")
    private String filePath;
    private static final String prefixUrl = "/img/";

    public FileUploadResponse restore(MultipartFile multipartFile) {
        String url;
        try {
            // 파일 정보
            String originFilename = multipartFile.getOriginalFilename();
            String extName
                    = originFilename.substring(originFilename.lastIndexOf("."), originFilename.length());
            Long size = multipartFile.getSize();

            // 서버에서 저장 할 파일 이름
            String saveFileName = genSaveFileName(extName);

            writeFile(multipartFile, saveFileName);
            url = prefixUrl + saveFileName;
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
        FileOutputStream fos = new FileOutputStream(filePath + saveFileName);
        fos.write(data);
        fos.close();

        return result;
    }
}
