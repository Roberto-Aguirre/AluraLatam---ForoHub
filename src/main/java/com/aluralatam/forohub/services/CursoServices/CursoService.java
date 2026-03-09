package com.aluralatam.forohub.services.CursoServices;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aluralatam.forohub.dtos.CursoDTO;
import com.aluralatam.forohub.entities.Curso;
import com.aluralatam.forohub.exceptions.CustomNotFoundException;
import com.aluralatam.forohub.repository.CursoRepository;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public List<CursoDTO> listar() {
        return cursoRepository.findAll().stream().map(CursoDTO::new).toList();
    }

    public Optional<CursoDTO> buscarPorId(Long id) {
        return cursoRepository.findById(id).map(CursoDTO::new);
    }

    public Optional<Curso> buscarEntidadPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso guardar(CursoDTO curso) {
        Curso nuevoCurso = Curso.builder()
                .nombreCurso(curso.nombreCurso())
                .build();
        return cursoRepository.save(nuevoCurso);
    }

    public Curso actualizar(Long id, CursoDTO curso) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("Curso no encontrado"));

        Curso cursoActualizado = Curso.builder()
            .id(cursoExistente.getId())
            .nombreCurso(curso.nombreCurso())
            .topics(cursoExistente.getTopics())
            .build();

        return cursoRepository.save(cursoActualizado);
    }

    public void eliminarPorId(Long id) {
        cursoRepository.deleteById(id);
    }
}
