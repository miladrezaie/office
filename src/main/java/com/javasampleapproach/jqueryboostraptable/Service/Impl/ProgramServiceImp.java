package com.javasampleapproach.jqueryboostraptable.Service.Impl;

import com.javasampleapproach.jqueryboostraptable.Service.ProgramService;
import com.javasampleapproach.jqueryboostraptable.model.Location;
import com.javasampleapproach.jqueryboostraptable.model.Program;
import com.javasampleapproach.jqueryboostraptable.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Program> getAllPrograms() {
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
