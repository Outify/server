package com.mcc.outify.apis;

import com.mcc.outify.weathers.LocationList;
import com.mcc.outify.weathers.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weathers")
public class WeatherController {

    private final WeatherService weatherService;

    private final LocationList locationList;

    @GetMapping("/data")
    public Map<String, Object> getLocationWeather(@RequestParam("addr") String addr) {
        Map<String, Object> resData = weatherService.getLocationWeather(addr);

        return resData;
    }

    @PostMapping("/locations")
    public ResponseEntity<String> uploadLocations(@RequestParam("file") MultipartFile file) {
        try {
            locationList.readExcel(file.getInputStream());
            return ResponseEntity.ok("위치 정보를 성공적으로 업로드했습니다.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(400).body("파일 업로드에 실패했습니다. 다시 한번 확인해 주세요.");
        }
    }

}
