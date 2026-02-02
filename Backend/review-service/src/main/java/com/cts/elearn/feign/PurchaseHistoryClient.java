package com.cts.elearn.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.elearn.dto.PurchaseHistoryResponse;

@FeignClient(name = "purchasehistory-service")
public interface PurchaseHistoryClient {

    @GetMapping("/purchasehistory/learner/{learnerId}")
    List<PurchaseHistoryResponse> getPurchasesByLearner(
        @PathVariable Long learnerId
    );
}
