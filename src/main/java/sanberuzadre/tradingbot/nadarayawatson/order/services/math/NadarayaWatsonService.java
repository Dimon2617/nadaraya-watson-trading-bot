package sanberuzadre.tradingbot.nadarayawatson.order.services.math;

import org.springframework.stereotype.Component;
import sanberuzadre.tradingbot.nadarayawatson.order.dtos.response.oco_order.NewOcoOrderResponseDTO;
import sanberuzadre.tradingbot.nadarayawatson.order.websocket.command.market.dtoTest.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
public class NadarayaWatsonService {

    private List<Double> original = new ArrayList<>();
    private List<Double> high = new ArrayList<>();
    private List<Double> low = new ArrayList<>();
    private List<Double> close = new ArrayList<>();
    private Consumer<Double> callback;

    /**
     * window - ширина вікна, вага кожної свічки
     */
    private int h = 8;

    /**
     * relative weighting - вплив часового проміжку
     */
    private double alpha = 8.0;

    /**
     * start regression at bar
     */
    private int x_0 = 25;

    private double nearFactor = 1.5;
    private double farFactor = 8.0;

    /**
     * проміжок часу в секундах
     */
    private int period = 1;

    private int limiter;

    private TradeSignal onUpperFar;
    private TradeSignal onLowerFar;

    private Root originalData;

    public void initializeCallbacks(TradeSignal onUpperFar, TradeSignal onLowerFar) {
        this.onUpperFar = onUpperFar;
        this.onLowerFar = onLowerFar;
    }

    public void calculate(Root dataRoot) {
        acceptData(dataRoot);

        if (limiter < h) {
            limiter++;
            return;
        }

        double[] originalPrice = toArray(original);
        var estimation = calculateNadarayaWatsonEstimation(originalPrice, h, alpha);
        var atr = calculateATR(toArray(high), toArray(low), toArray(close), period);

        double[][] envelopeBounds = calculateEnvelopes(
                estimation,
                atr,
                nearFactor,
                farFactor);

        int lastIndex = estimation.length - 1;
        makeDecision(envelopeBounds[0][lastIndex], envelopeBounds[3][lastIndex]);
    }

    private void acceptData(Root dataRoot) {
        originalData = dataRoot;
        original.add(parseString(dataRoot.open));

        high.add(parseString(dataRoot.high));
        low.add(parseString(dataRoot.low));
        close.add(parseString(dataRoot.close));

        shiftLists();
    }

    private double[] calculateNadarayaWatsonEstimation(double[] data, int h, double alpha) {
        double[] estimation = new double[data.length];

        for (int i = 0; i < data.length; i++) {
            double numerator = 0.0;
            double denominator = 0.0;

            for (int j = Math.max(0, i - h + 1); j <= Math.min(data.length - 1, i + h - 1); j++) {
                double kernelWeight = calculateRationalQuadraticKernelWeight(i - j, alpha);
                numerator += data[j] * kernelWeight;
                denominator += kernelWeight;
            }

            estimation[i] = numerator / denominator;
        }

        return estimation;
    }

    private double calculateRationalQuadraticKernelWeight(int distance, double alpha) {
        return Math.pow(1.0 + (distance * distance) / (alpha * alpha), -0.5 * (alpha + 1));
    }

    public double[][] calculateEnvelopes(double[] estimation, double[] atr, double nearFactor, double farFactor) {
        int length = estimation.length;
        double[] upperFar = new double[length];
        double[] upperNear = new double[length];
        double[] lowerNear = new double[length];
        double[] lowerFar = new double[length];
        double[] upperAvg = new double[length];
        double[] lowerAvg = new double[length];

        for (int i = 0; i < length; i++) {
            upperFar[i] = estimation[i] + farFactor * atr[i];
            upperNear[i] = estimation[i] + nearFactor * atr[i];
            lowerNear[i] = estimation[i] - nearFactor * atr[i];
            lowerFar[i] = estimation[i] - farFactor * atr[i];
            upperAvg[i] = (upperFar[i] + upperNear[i]) / 2.0;
            lowerAvg[i] = (lowerFar[i] + lowerNear[i]) / 2.0;
        }

        return new double[][]{upperFar, upperNear, lowerNear, lowerFar, upperAvg, lowerAvg};
    }

    public double[] calculateATR(double[] high, double[] low, double[] close, int period) {
        double[] trueRange = new double[close.length];
        double[] atr = new double[close.length];

        for (int i = 1; i < close.length; i++) {
            double range1 = high[i] - low[i];
            double range2 = Math.abs(high[i] - close[i - 1]);
            double range3 = Math.abs(low[i] - close[i - 1]);

            trueRange[i] = Math.max(range1, Math.max(range2, range3));
        }

        atr[period - 1] = calculateSMA(trueRange, period);

        for (int i = period; i < close.length; i++) {
            atr[i] = (atr[i - 1] * (period - 1) + trueRange[i]) / period;
        }

        return atr;
    }

    private double calculateSMA(double[] data, int period) {
        double sum = 0.0;

        for (int i = 0; i < period; i++) {
            sum += data[i];
        }

        return sum / period;
    }

    private double parseString(String value) {
        return Double.parseDouble(value);
    }

    private double[] toArray(List<Double> data) {
        return data.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }

    private void shiftLists() {
        if (original.size() > h) {
            original.remove(0);
            high.remove(0);
            low.remove(0);
            close.remove(0);
        }
    }

    private void makeDecision(double upperFar, double lowerFar) {
        double origPrice = Double.parseDouble(originalData.open);
        var avrg = (upperFar + lowerFar) / 2;
        if (callback != null) {
            callback.accept(avrg);
        }
        if (origPrice > upperFar) {
            //onUpperFar.onSignal(origPrice);
        } else if (origPrice < lowerFar) {
            //onLowerFar.onSignal(origPrice);
        } else {
            System.out.println("skip");
        }
    }

    public void setCallback(Consumer<Double> callback) {
        this.callback = callback;
    }

    /**
     * Щоб подивитись на отримані рамки - викликаємо цей метод перед прийняттям рішення
     *
     * @param envelopes отримані рамки
     * @param index     індекс отриманого значення
     */
    private void displayEnvelopes(double[][] envelopes, int index) {
        if (index < 0) {
            return;
        }
        System.out.println();
        System.out.println("Open value: " + originalData.open);
        System.out.println("Upper Far: " + envelopes[0][index]);
        System.out.println("Upper AVG: " + envelopes[4][index]);
        System.out.println("Upper Near: " + envelopes[1][index]);
        System.out.println("Lower Near: " + envelopes[2][index]);
        System.out.println("Lower AVG: " + envelopes[5][index]);
        System.out.println("Lower Far: " + envelopes[3][index]);
    }

}