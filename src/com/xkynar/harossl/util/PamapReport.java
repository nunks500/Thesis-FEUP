/**
 * Copyright 2018 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package com.xkynar.harossl.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import pt.up.fe.specs.contextwa.EnsembleModel;

public class PamapReport {

    private int user;
    private EnsembleModel model;
    private long time;

    // private long energy;
    public PamapReport(int user, EnsembleModel model, long time) {
        this.user = user;
        this.model = model;
        this.time = time;
    }

    public int getUser() {
        return user;
    }

    public EnsembleModel getModel() {
        return model;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return report(true).toString();
    }

    public StringBuilder report(boolean includePredictions) {
        StringBuilder sb = new StringBuilder();
        final int numSamplesTotal = model.getTotalNumSamples();
        int numberOfCorrectClassifications = model.numberOfCorrectClassifications();
        double acc = numberOfCorrectClassifications / (double) numSamplesTotal;
        sb.append("Total samples: " + numSamplesTotal + "\n");
        sb.append("Samples correct: " + model.numberOfCorrectClassifications() + "\n");
        sb.append(String.format("Accuracy: %.2f%%\n", acc * 100));
        sb.append("Prediction p/classifer: "
                + Arrays.stream(model.getCorrectClassifPerClassifier())
                        .mapToObj(d -> String.format("%d (%.2f%%)", d, d / (double) numSamplesTotal * 100f))
                        .collect(Collectors.joining(", "))
                + "\n");
      
        if (includePredictions) {
            System.out.println("-Predictions Per classification-");
            model.getPredictions().forEach(p -> System.out.println(p.toString(model.getDataHandler())));
            System.out.println(model.getPredictions().get(542).getPredictedPerClassifier());
            System.out.println(model.getPredictions().size());

        }
        
        return sb;
    }
}