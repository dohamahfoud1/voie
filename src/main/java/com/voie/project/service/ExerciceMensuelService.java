package com.voie.project.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.voie.project.models.ExerciceMensuel;
import com.voie.project.repository.ExerciceMensuelRepository;

@Service
public class ExerciceMensuelService {

	@Autowired
    private ExerciceMensuelRepository exerciceMensuelRepository;

    public ExerciceMensuel cloturerExercice(LocalDate mois) {
        ExerciceMensuel exercice = exerciceMensuelRepository.findByMois(mois);
        if (exercice == null) {
            exercice = new ExerciceMensuel();
            exercice.setMois(mois);
        }
        exercice.setCloture(true);
        exercice.setArchive(true);
        return exerciceMensuelRepository.save(exercice);
    }

    public ExerciceMensuel decloturerExercice(Long id) {
        ExerciceMensuel exercice = exerciceMensuelRepository.findById(id).orElseThrow();
        exercice.setCloture(false);
        exercice.setArchive(false);
        return exerciceMensuelRepository.save(exercice);
    }

    public List<ExerciceMensuel> getArchives() {
        return exerciceMensuelRepository.findByArchiveTrue();
    }

    public LocalDate getNextMonth(LocalDate currentMonth) {
        return currentMonth.plusMonths(1);
    }
}