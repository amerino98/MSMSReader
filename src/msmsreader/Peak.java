/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msmsreader;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Álvaro Merino
 */
public class Peak implements Serializable {

    private static final long serialVersionUID = 1L;
    public List<Double> mz;
    public List<Integer> intensity;

    public Peak() {
    }

    @Override
    public String toString() {
        return "Peak{" + "mz=" + mz + ", intensity=" + intensity + '}';
    }

    public List<Double> getMz() {
        return mz;
    }

    public void setMz(List<Double> mz) {
        this.mz = mz;
    }

    public List<Integer> getIntensity() {
        return intensity;
    }

    public void setIntensity(List<Integer> intensity) {
        this.intensity = intensity;
    }

    public Peak(List<Double> mz, List<Integer> intensity) {
        this.mz = mz;
        this.intensity = intensity;
    }

}
