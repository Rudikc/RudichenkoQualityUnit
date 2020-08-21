package ua.rudichenko.service;

import ua.rudichenko.entity.WaitingTimeline;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EvaluationService implements IEvaluationService {

    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public String evaluateData(String input) {
        String[] lines = input.split("\n");
        List<WaitingTimeline> waitingTimelines = new ArrayList<WaitingTimeline>();
        StringBuilder builder = new StringBuilder();

        try {
            for (String line : lines) {
                String[] params = line.split(" ");
                if (line.startsWith("C")) {
                    processWaitingTimeline(params, waitingTimelines);
                } else if (line.startsWith("D")) {
                    builder.append(processQuery(params, waitingTimelines)).append("\n");
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String result = builder.toString();
        return result.length() == 0 ? null : result.substring(0, result.length() - 1);
    }

    private void processWaitingTimeline(String[] params, List<WaitingTimeline> waitingTimelines) throws ParseException {
        String service = params[1];
        String question = params[2];
        String responseType = params[3];
        Date date = dateFormat.parse(params[4]);
        int time = Integer.parseInt(params[5]);
        waitingTimelines.add(new WaitingTimeline(service, question, responseType, date, time));
    }

    private String processQuery(String[] params, List<WaitingTimeline> waitingTimelines) {
        String service = params[1].equals("*") ? "" : params[1];
        String question = params[2].equals("*") ? "" : params[2];
        String[] dates = params[4].split("-");

        OptionalDouble average = waitingTimelines
                .stream()
                .filter(q -> q.getService().startsWith(service)
                        && q.getQuestion().startsWith(question)
                        && isMatchingDate(q, dates))
                .mapToInt(WaitingTimeline::getTime)
                .average();
        return average.isPresent() ? String.valueOf((int) average.getAsDouble()) : "-";
    }

    private boolean isMatchingDate(WaitingTimeline wtl, String[] dates) {
        try {
            Date dateFrom = dateFormat.parse(dates[0]);
            Date dateTo = dates.length == 1 ? null : dateFormat.parse(dates[1]);
            if (wtl.getDate().equals(dateFrom) ||
                    wtl.getDate().equals(dateTo) ||
                    (dateTo != null && wtl.getDate().after(dateFrom) && wtl.getDate().before(dateTo))) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }
}