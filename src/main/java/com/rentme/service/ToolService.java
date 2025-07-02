package com.rentme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rentme.model.Tool;
import com.rentme.repository.ToolRepository;

@Service
public class ToolService {

    @Autowired
    private ToolRepository toolRepository;

    public Tool save(Tool tool) {
        return toolRepository.save(tool);
    }

    public List<Tool> findByOwnerId(Long ownerId) {
        return toolRepository.findByOwnerId(ownerId);
    }

    public List<Tool> findAll() {
        return toolRepository.findAll();
    }

    public Tool findById(Long id) {
        return toolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tool not found with ID: " + id));
    }

    public void delete(Long id) {
        toolRepository.deleteById(id);
    }


}
