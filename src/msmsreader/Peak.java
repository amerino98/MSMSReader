package msmsreader;

import java.io.Serializable;
import java.util.List;

public class Peak implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public List<Double> mz;
    public List<Integer> intensity;

    @Override
    public String toString() {
        return "PEAK [mz=" + mz + ", intensity=" + intensity + "]";
    }

    public Peak() {
    }

    public Peak(List<Double> mz, List<Integer> intensity) {
        super();
        this.mz = mz;
        this.intensity = intensity;
    }

    public List<Double> getMz() {
        return mz;
    }

    public List<Integer> getIntensity() {
        return intensity;
    }

    public void setMz(List<Double> mz) {
        this.mz = mz;
    }

    public void setIntensity(List<Integer> intensity) {
        this.intensity = intensity;
    }

}
