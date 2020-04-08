package controller;

import model.Answer;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FillOutQuizControllerTest {

    @Test
    void randomizeAnswers() {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer("AAA", true));
        answers.add(new Answer("BBB", false));
        answers.add(new Answer("CCC", false));
        answers.add(new Answer("DDD", false));

        FillOutQuizController fillOutQuizController = new FillOutQuizController();
        List<Answer> randomizedAnswers = fillOutQuizController.randomizeAnswers(answers);

        assertFalse(answers.get(0).equals(randomizedAnswers.get(0))
                 &&   (answers.get(1).equals(randomizedAnswers.get(1)))
               &&   (answers.get(3).equals(randomizedAnswers.get(3)))
                 &&   (answers.get(2).equals(randomizedAnswers.get(2))));

    }
}