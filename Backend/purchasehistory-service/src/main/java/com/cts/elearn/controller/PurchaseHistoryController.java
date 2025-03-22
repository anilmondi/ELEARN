package com.cts.elearn.controller;

import com.cts.elearn.entity.PurchaseHistory;
import com.cts.elearn.service.PurchaseHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchasehistory")
public class PurchaseHistoryController {

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

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
