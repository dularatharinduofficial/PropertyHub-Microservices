package com.soc.auth.controller;

import com.soc.auth.model.Property;
import com.soc.auth.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/auth/properties")
public class PropertyController {

    @Autowired
    private PropertyRepository propertyRepository;

    @PostMapping
    public Property addProperty(@RequestBody Property property) {
        return propertyRepository.save(property);
    }

    @GetMapping
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }
    
    @PutMapping("/{id}")
    public Property updateProperty(@PathVariable String id, @RequestBody Property propertyDetails) {
        return propertyRepository.findById(id).map(property -> {
            property.setTitle(propertyDetails.getTitle());
            property.setLocation(propertyDetails.getLocation());
            property.setPrice(propertyDetails.getPrice());
            property.setDescription(propertyDetails.getDescription()); 
            property.setContactNumber(propertyDetails.getContactNumber()); 

            if (propertyDetails.getImageUrl() != null && !propertyDetails.getImageUrl().isEmpty()) {
                property.setImageUrl(propertyDetails.getImageUrl());
            }
            return propertyRepository.save(property);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable String id) {
        propertyRepository.deleteById(id);
    }
}