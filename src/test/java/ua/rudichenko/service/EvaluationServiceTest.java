package ua.rudichenko.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class EvaluationServiceTest {

    private EvaluationService evaluationService = new EvaluationService();


    @Test
    public void initialTest() {
        String input = "7\n" +
                "C 1.1 8.15.1 P 15.10.2012 83\n" +
                "C 1 10.1 P 01.12.2012 65\n" +
                "C 1.1 5.5.1 P 01.11.2012 117\n" +
                "D 1.1 8 P 01.01.2012-01.12.2012\n" +
                "C 3 10.2 N 02.10.2012 100\n" +
                "D 1 * P 8.10.2012-20.11.2012\n" +
                "D 3 10 P 01.12.2012";
        String result = evaluationService.evaluateData(input);
        String expected = "83\n" +
                "100\n" +
                "-";
        assertEquals(expected, result);
    }

    @Test
    public void whenInputIsEmptyThenReturnNull() {
        String input = "";
        String result = evaluationService.evaluateData(input);
        assertNull(result);
    }

    @Test
    public void whenOneQueryThenReturnDash() {
        String input = "7\n" +
                "D 3 10 P 01.12.2012";
        String expected = "-";
        String result = evaluationService.evaluateData(input);
        assertEquals(expected, result);
    }

    @Test
    public void when0QueriesThenReturnNull() {
        String input = "7\n" +
                "C 1.1 8.15.1 P 15.10.2012 83\n" +
                "C 1 10.1 P 01.12.2012 65\n" +
                "C 1.1 5.5.1 P 01.11.2012 117\n" +
                "C 3 10.2 N 02.10.2012 100\n";
        String result = evaluationService.evaluateData(input);
        assertNull(result);
    }

    @Test
    public void whenWaitingTimelineMatchingParametersAndDateEqualsToSecondDateOfQueryThenReturnResult() {
        String input = "7\n" +
                "C 1 10.1 P 01.12.2012 65\n" +
                "D * 10 P 01.11.2012-01.12.2012";
        String result = evaluationService.evaluateData(input);
        String expected = "65";
        assertEquals(expected, result);
    }

    @Test
    public void whenWaitingTimelineMatchingParametersAndDateEqualsToFirstDateOfQueryThenReturnResult() {
        String input = "7\n" +
                "C 1 10.1 P 01.11.2012 65\n" +
                "D * 10 P 01.11.2012-01.12.2012";
        String result = evaluationService.evaluateData(input);
        String expected = "65";
        assertEquals(expected, result);
    }

    @Test
    public void whenWaitingTimelineMatchingParametersAndDateIsInTheRangeThenReturnResult() {
        String input = "7\n" +
                "C 1 10.1 P 15.11.2012 65\n" +
                "D * 10 P 01.11.2012-01.12.2012";
        String result = evaluationService.evaluateData(input);
        String expected = "65";
        assertEquals(expected, result);
    }
}