package com.voie.project.controller;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.voie.project.models.ExerciceMensuel;
import com.voie.project.service.ExerciceMensuelService;

@Controller
public class ExerciceMensuelController {

    @Autowired
    private ExerciceMensuelService exerciceMensuelService;

    
	@GetMapping("/cloturerExercice")
	public String cloturer(Model model) {
		List<ExerciceMensuel> archives = exerciceMensuelService.getArchives();
        model.addAttribute("archives", archives);
      	return "/cloturerExercice";
	}
	 @PostMapping("/cloturerExercice")
	    public String cloturerExercice(@RequestParam("mois") String mois, Model model) {
	        YearMonth yearMonth = YearMonth.parse(mois);
	        LocalDate moisDate = yearMonth.atEndOfMonth();
	        ExerciceMensuel exercice = exerciceMensuelService.cloturerExercice(moisDate);
	        LocalDate nextMonth = exerciceMensuelService.getNextMonth(moisDate);
	        model.addAttribute("exercice", exercice);
	        model.addAttribute("nextMonth", nextMonth);
	        return "redirect:/cloturerExercice";
	    }


	    @PostMapping("/decloturerExercice")
	    public String decloturerExercice(@RequestParam("id") Long id, Model model) {
	        ExerciceMensuel exercice = exerciceMensuelService.decloturerExercice(id);
	        model.addAttribute("exercice", exercice);
	        return "redirect:/cloturerExercice";
	    }
}
