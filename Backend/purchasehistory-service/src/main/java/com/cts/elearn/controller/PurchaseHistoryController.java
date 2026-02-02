package com.cts.elearn.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.elearn.entity.PurchaseHistory;
import com.cts.elearn.event.CoursePurchasedEvent;
import com.cts.elearn.service.PurchaseHistoryService;

@RestController
@RequestMapping("/purchasehistory")
public class PurchaseHistoryController {

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    
    @Autowired(required = false)
    private KafkaTemplate<String, Object> kafkaTemplate;


    @PostMapping("/test-purchase")
    public String testPurchase() {

        
    	CoursePurchasedEvent event =
    		    CoursePurchasedEvent.of(
    		        1L,     // learnerId
    		        101L,   // courseId
    		        1L      // serviceId
    		    );

        kafkaTemplate.send(
        	    "course.purchased",
        	    event.getLearnerId().toString(),
        	    event
        	);


        return "Test purchase event sent";
    }


    @PostMapping
    public PurchaseHistory addPurchase(@RequestBody PurchaseHistory purchaseHistory) {
        return purchaseHistoryService.create(purchaseHistory);
    }

    @GetMapping
    public List<PurchaseHistory> getAllPurchases() {
        return purchaseHistoryService.getAllPurchases();
    }

    @GetMapping("/{id}")
    public PurchaseHistory getPurchaseById(@PathVariable Long id) {
        return purchaseHistoryService.getPurchaseById(id);
    }

    @GetMapping("/learner/{learnerId}")
    public List<PurchaseHistory> getPurchasesByLearner(@PathVariable Long learnerId) {
        return purchaseHistoryService.getPurchasesByLearner(learnerId);
    }

    @PutMapping
    public PurchaseHistory updatePurchase(@RequestBody PurchaseHistory purchaseHistory) {
        return purchaseHistoryService.updatePurchase(purchaseHistory);
    }

    @DeleteMapping("/{id}")
    public void deletePurchase(@PathVariable Long id) {
        purchaseHistoryService.deletePurchase(id);
    }
}
