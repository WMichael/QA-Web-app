package uk.co.wmichael.qas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import uk.co.wmichael.qas.model.Question;
import uk.co.wmichael.qas.model.QuestionRepository;

@Controller
public class QAController {
    @Autowired // This means to get the bean called questionRepository
    private QuestionRepository questionRepository;

    @PostMapping("/question/add")
    public String addNewQuestion (@ModelAttribute Question question, ModelMap model) {
        questionRepository.save(question);
        model.put("id",question.getId());
        return "question";
    }

    @GetMapping("/question/add")
    public String addNewQuestion (Model model) {
        model.addAttribute("question", new Question());
        return "addQuestion";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("questions", questionRepository.findAll());
        return "index";
    }

    @GetMapping("/question/{id}")
    public String question(@PathVariable int id, Model model){
        if (questionRepository.findById(id).isPresent()) {
            model.addAttribute("question", questionRepository.findById(id).get());
        }
        else {
            model.addAttribute("errorMsg", "Question not found!");
        }

        return "question";
    }
}
