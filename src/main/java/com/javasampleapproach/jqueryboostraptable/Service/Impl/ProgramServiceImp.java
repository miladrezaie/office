package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.ProgramService;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.Program;
import com.javasampleapproach.jqueryboostraptable.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramServiceImp implements ProgramService {

    private final ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImp(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public Page<Program> getAllTruePrograms(Pageable page) {
        return programRepository.findByStatusIsTrue(page);
    }

    @Override
    public Page<Program> getAllAwaitPrograms(Pageable page) {
        return programRepository.findByStatusIsFalse(page);
    }

    @Override
    public List<Program> findAll() {
        return programRepository.findAll();
    }

    public void saveProgram(Program program) {
        programRepository.save(program);
    }

    public void deleteProgram(Long id) {
        programRepository.deleteById(id);
    }

    public Program getProgram(Long id) {
        return programRepository.findById(id).get();
    }

    public Optional<Program> findByIdProgram(Long id) {
        return programRepository.findById(id);
    }
}
