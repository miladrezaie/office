package com.javasampleapproach.jqueryboostraptable.Service;

import com.javasampleapproach.jqueryboostraptable.model.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProgramService {

    Page<Program> getAllTruePrograms(Pageable page);

    Page<Program> getAllAwaitPrograms(Pageable page);

//    Page<Program> getAllAwaitPrograms(Pageable page);

    List<Program> findAll();

    void saveProgram(Program program);

    void deleteProgram(Long id);

    Program getProgram(Long id);

    Optional<Program> findByIdProgram(Long id);
}
