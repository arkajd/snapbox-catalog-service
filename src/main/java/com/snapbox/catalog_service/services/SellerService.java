package com.snapbox.catalog_service.services;

import com.snapbox.catalog_service.dtos.AddSellerRequest;
import com.snapbox.catalog_service.models.Seller;
import com.snapbox.catalog_service.repositories.SellerRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository){
        this.sellerRepository = sellerRepository;
    }

    public void addSeller(AddSellerRequest addSellerRequest){
        Seller seller = new Seller();
        seller.setName(addSellerRequest.getName());
        seller.setDescription(addSellerRequest.getDescription());
        sellerRepository.save(seller);
    }

    @PostConstruct
    public void initializeCategories() {
        if (sellerRepository.count() == 0) { // Check if there are no categories yet
            Seller seller = new Seller();
            seller.setName("WholeSeller Private Limited");
            seller.setDescription("Sell products at wholesale price.");
            sellerRepository.save(seller);
        }
    }
}
