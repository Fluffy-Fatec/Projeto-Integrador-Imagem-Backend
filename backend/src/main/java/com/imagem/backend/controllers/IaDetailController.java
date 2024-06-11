package com.imagem.backend.controllers;

import com.imagem.backend.domain.IaDetails;
import com.imagem.backend.domain.TermFunction;
import com.imagem.backend.services.IaDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class IaDetailController {

    private final IaDetailService iaDetailService;

    @GetMapping("/accuracy")
    public ResponseEntity<IaDetails> getLastDeploy(){

        IaDetails iaDetails = iaDetailService.getLastDeployIa();

        return ResponseEntity.ok().body(iaDetails);
    }
    @GetMapping("/count/sentiment")
    public ResponseEntity<Map<Integer,Long>> countSentiment(){

        Map<Integer,Long> iaDetails = iaDetailService.countSentiment();

        return ResponseEntity.ok().body(iaDetails);
    }

    @GetMapping("/count/review")
    public ResponseEntity<Integer> countTotalReview(){

        Integer iaDetails = iaDetailService.countReview();

        return ResponseEntity.ok().body(iaDetails);
    }

    @GetMapping("/accuracy/all")
    public ResponseEntity<List<IaDetails>> listTermFunction() {

        List<IaDetails> iaDetails = iaDetailService.getAllDeployIa();

        return ResponseEntity.ok().body(iaDetails);
    }

    @GetMapping("/accuracy/month")
    public ResponseEntity<Map<YearMonth, List<IaDetails>>> groupDetailByMonth(){

        Map<YearMonth, List<IaDetails>> iaDetails = iaDetailService.getDetailForEachMonth();

        return ResponseEntity.ok().body(iaDetails);
    }
}