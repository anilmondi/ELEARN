package com.cts.elearn.service;

import com.cts.elearn.dao.PurchaseHistoryRepository;
import com.cts.elearn.entity.PurchaseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PurchaseHistoryService {

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    public PurchaseHistory create(PurchaseHistory purchaseHistory) {
        return purchaseHistoryRepository.save(purchaseHistory);
    }

    public List<PurchaseHistory> getAllPurchases() {
        return purchaseHistoryRepository.findAll();
    }

    public PurchaseHistory getPurchaseById(Long id) {
        return purchaseHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase history not found with ID: " + id));
    }

    public List<PurchaseHistory> getPurchasesByLearner(Long learnerId) {
        return purchaseHistoryRepository.findByLearnerId(learnerId);
    }

    public PurchaseHistory updatePurchase(PurchaseHistory purchaseHistory) {
        return purchaseHistoryRepository.save(purchaseHistory);
    }

    public void deletePurchase(Long id) {
        purchaseHistoryRepository.deleteById(id);
    }
}
