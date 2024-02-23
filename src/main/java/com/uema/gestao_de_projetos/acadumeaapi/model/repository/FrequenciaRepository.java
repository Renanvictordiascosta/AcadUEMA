package com.uema.gestao_de_projetos.acadumeaapi.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.uema.gestao_de_projetos.acadumeaapi.model.entity.Frequencia;

public interface FrequenciaRepository extends JpaRepository<Frequencia,Long>{
		

	@Query("SELECT COUNT(f) FROM Frequencia f WHERE f.matricula.id = :id_matricula AND f.matricula.alunos.id = :id_aluno AND f.frequencia = 'PRESENTE'")
	Long obterPresencaPorAlunoNaMatricula(@Param("id_matricula") Long idMatricula, @Param("id_aluno") Long id_Aluno);

	@Query("SELECT COUNT(f) FROM Frequencia f WHERE f.matricula.id = :id_matricula AND f.matricula.alunos.id = :id_aluno AND f.frequencia = 'AUSENTE'")
	Long obterAusenciaPorAlunoNaMatricula(@Param("id_matricula") Long idMatricula, @Param("id_aluno") Long id_Aluno);

}
