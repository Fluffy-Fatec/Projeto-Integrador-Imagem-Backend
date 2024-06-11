package com.imagem.backend.services;

import com.imagem.backend.domain.IaDetails;
import com.imagem.backend.domain.Review;
import com.imagem.backend.repositories.IaDetailsRepository;
import com.imagem.backend.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IaDetailService {

    private final IaDetailsRepository iaDetailsRepository;

    private final ReviewRepository reviewRepository;

    public IaDetails getLastDeployIa(){
        return iaDetailsRepository.findTopByOrderByIaDatetimeDeployDesc();
    }

    public List<IaDetails> getAllDeployIa(){
        return iaDetailsRepository.findAll();
    }

    public Map<Integer,Long> countSentiment(){

        // Criar um mapa para armazenar as contagens
        Map<Integer, Long> sentimentCount = reviewRepository.findAll().stream()
                .filter(review -> review.getSentimentoPredito() != null && !review.getSentimentoPredito().isEmpty())
                .collect(Collectors.groupingBy(
                        review -> Integer.parseInt(review.getSentimentoPredito()),
                        Collectors.counting()
                ));

        return new HashMap<>(sentimentCount);
    }

    public Integer countReview(){
        return reviewRepository.findAll().size();
    }

    public Map<YearMonth, List<IaDetails>> getDetailForEachMonth(){
        List<IaDetails> reviews = iaDetailsRepository.findAll();
        Map<YearMonth, List<IaDetails>> reviewsByMonth = reviews.stream()
                .collect(
                        HashMap::new, // Cria um novo HashMap
                        (map, review) -> {
                            YearMonth yearMonth = YearMonth.from(review.getIaDatetimeDeploy().toLocalDate());
                            map.computeIfAbsent(yearMonth, k -> new ArrayList<>()).add(review);
                        },
                        HashMap::putAll
                );

        // Exibe as listas de reviews por mês
        reviewsByMonth.forEach((yearMonth, reviewList) -> {
            System.out.println("Mês: " + yearMonth.getMonth() + " " + yearMonth.getYear());
            reviewList.forEach(review -> System.out.println(review.getId() + ": " + review.getIaDatetimeDeploy()));
            System.out.println("----");
        });

        return new HashMap<>(reviewsByMonth);
    }
}
