package ro.sdi.lab.client.view;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class ResponseBuffer
{
    private List<FutureResponse<?>> responseList = new LinkedList<>();

    private Timer timer;
    private int delaySeconds;
    private boolean timerActivation;

    public ResponseBuffer(int delaySeconds, boolean timerActivation)
    {
        this.delaySeconds = delaySeconds;
        this.timerActivation = timerActivation;
    }

    public synchronized List<String> getResponses()
    {
        if (timer != null)
            timer.cancel();

        List<FutureResponse<?>> completedFutures =
                responseList.stream()
                            .filter(FutureResponse::available)
                            .collect(Collectors.toList());
        List<String> result =
                completedFutures.stream()
                                .map(FutureResponse::get)
                                .collect(Collectors.toUnmodifiableList());
        responseList.removeAll(completedFutures);
        return result;
    }

    public void add(FutureResponse<?> futureResponse)
    {
        responseList.add(futureResponse);
        if (timer != null)
            timer.cancel();
        if (timerActivation)
        {
            timer = new Timer();
            timer.schedule(new ShowResponsesTask(this), delaySeconds * 1000);
        }
    }

    static class ShowResponsesTask extends TimerTask
    {
        private ResponseBuffer responseBuffer;

        public ShowResponsesTask(ResponseBuffer responseBuffer)
        {
            this.responseBuffer = responseBuffer;
        }

        @Override
        public void run()
        {
            List<String> responses = responseBuffer.getResponses();
            if (!responses.isEmpty())
            {
                System.out.println(String.join("\n", responses));
            }
        }
    }
}
