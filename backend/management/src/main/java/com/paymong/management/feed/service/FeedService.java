package com.paymong.management.feed.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.management.feed.vo.FeedFoodReqVo;
import com.paymong.management.feed.vo.FeedSnackReqVo;
import com.paymong.management.global.client.AuthServiceClient;
import com.paymong.management.global.client.CommonServiceClient;
import com.paymong.management.global.dto.AddPointDto;
import com.paymong.management.global.dto.CommonCodeDto;
import com.paymong.management.global.dto.FindCommonCodeDto;
import com.paymong.management.global.exception.NotFoundActionException;
import com.paymong.management.global.exception.NotFoundMongException;
import com.paymong.management.global.exception.UnknownException;
import com.paymong.management.mong.entity.Mong;
import com.paymong.management.mong.repository.MongRepository;
import com.paymong.management.status.dto.FindStatusReqDto;
import com.paymong.management.status.dto.FindStatusResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class FeedService {
    private final MongRepository mongRepository;
    private final CommonServiceClient commonServiceClient;
    private final AuthServiceClient authServiceClient;

    @Transactional
    public void feedFood(FeedFoodReqVo feedFoodReqVo) throws Exception{
        // auth에서 mongId 받아오기

        // mongId로 해당 mong 찾기
        Mong mong = mongRepository.findByMongId(feedFoodReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());

        // foodCode에 따라 해당 액션 찾기
        String foodCode = feedFoodReqVo.getFoodCode().substring(2);
        int code = Integer.parseInt(foodCode);

        FindStatusReqDto findStatusReqDto = new FindStatusReqDto();
        if(code == 0){
            findStatusReqDto.setCode("AT000");
        }else if(1<=code && 3>=code){
            findStatusReqDto.setCode("AT010");
        }else if(4<=code && 6>=code){
            findStatusReqDto.setCode("AT020");
        }else{
            findStatusReqDto.setCode("AT030");
        }

        ObjectMapper om = new ObjectMapper();
        ResponseEntity<Object> response = commonServiceClient.findStatus(findStatusReqDto);
        if(response.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundActionException();
        FindStatusResDto status = om.convertValue(response.getBody(), FindStatusResDto.class);

        ResponseEntity<Object> feedCode = commonServiceClient.findCommonCode(new FindCommonCodeDto(feedFoodReqVo.getFoodCode()));
        if(feedCode.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundActionException();
        CommonCodeDto food = om.convertValue(feedCode.getBody(), CommonCodeDto.class);
        // 이름까지 받아옴
        ResponseEntity<Object> addPoint = authServiceClient.addPoint(String.valueOf(mong.getMemberId()), new AddPointDto(status.getPoint(), food.getName() + " 구매"));
        if(addPoint.getStatusCode() != HttpStatus.OK) throw new UnknownException();

        mong.setWeight(mong.getWeight() + status.getWeight());

    }

    @Transactional
    public void feedSnack(FeedSnackReqVo feedSnackReqVo) throws Exception{
        // auth에서 mongId 받아오기

        // mongId로 해당 mong 찾기
        Mong mong = mongRepository.findByMongId(feedSnackReqVo.getMongId())
                .orElseThrow(() -> new NotFoundMongException());

        // foodCode에 따라 해당 액션 찾기
        String foodCode = feedSnackReqVo.getSnackCode().substring(2);
        int code = Integer.parseInt(foodCode);

        FindStatusReqDto findStatusReqDto = new FindStatusReqDto();
        if(code>= 0 && code<= 3){
            findStatusReqDto.setCode("AT001");
        }else{
            findStatusReqDto.setCode("AT011");
        }

        ObjectMapper om = new ObjectMapper();
        ResponseEntity<Object> response = commonServiceClient.findStatus(findStatusReqDto);
        if(response.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundActionException();
        FindStatusResDto status = om.convertValue(response.getBody(), FindStatusResDto.class);

        ResponseEntity<Object> feedCode = commonServiceClient.findCommonCode(new FindCommonCodeDto(feedSnackReqVo.getSnackCode()));
        if(feedCode.getStatusCode()== HttpStatus.BAD_REQUEST) throw new NotFoundActionException();
        CommonCodeDto food = om.convertValue(feedCode.getBody(), CommonCodeDto.class);
        // 이름까지 받아옴
        ResponseEntity<Object> addPoint = authServiceClient.addPoint(String.valueOf(mong.getMemberId()), new AddPointDto(status.getPoint(), food.getName() + " 구매"));
        if(addPoint.getStatusCode() != HttpStatus.OK) throw new UnknownException();

        mong.setWeight(mong.getWeight() + status.getWeight());
        mong.setHealth(mong.getHealth() + status.getHealth());
        mong.setStrength(mong.getStrength() + status.getStrength());
    }
}
