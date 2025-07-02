package com.rentme.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rentme.model.ScheduleEntry;
import com.rentme.repository.ScheduleEntryRepository;

@Service
public class ScheduleEntryService {

  private final ScheduleEntryRepository repository;

  public ScheduleEntryService(ScheduleEntryRepository repository) {
    this.repository = repository;
  }

  public void saveAll(List<ScheduleEntry> entries) {
    repository.saveAll(entries);
  }
}
