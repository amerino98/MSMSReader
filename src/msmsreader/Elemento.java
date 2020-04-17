package msmsreader;

import java.io.Serializable;

public class Elemento implements Serializable {

    private static final long serialVersionUID = 1L;
    public String name;
    public Double precursorMz;
    public String adduct;
    public String smiles;
    public String inchikey;
    public String ontology;
    public String formula;
    public Double retentiontime;
    public String ionMode;
    public Integer numPeaks;
    // Objecto de tipo peak que contenga un mz y una intensidad
    public Picos peaks;

    public Elemento() {
    }

    @Override
    public String toString() {
        return "Elemento{" + "name=" + name + ", precursorMz=" + precursorMz + ", adduct=" + adduct + ", smiles=" + smiles + ", inchikey=" + inchikey + ", ontology=" + ontology + ", formula=" + formula + ", retentiontime=" + retentiontime + ", ionMode=" + ionMode + ", numPeaks=" + numPeaks + ", peaks=" + peaks + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrecursorMz() {
        return precursorMz;
    }

    public void setPrecursorMz(Double precursorMz) {
        this.precursorMz = precursorMz;
    }

    public String getAdduct() {
        return adduct;
    }

    public void setAdduct(String adduct) {
        this.adduct = adduct;
    }

    public String getSmiles() {
        return smiles;
    }

    public void setSmiles(String smiles) {
        this.smiles = smiles;
    }

    public String getInchikey() {
        return inchikey;
    }

    public void setInchikey(String inchikey) {
        this.inchikey = inchikey;
    }

    public String getOntology() {
        return ontology;
    }

    public void setOntology(String ontology) {
        this.ontology = ontology;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Double getRetentiontime() {
        return retentiontime;
    }

    public void setRetentiontime(Double retentiontime) {
        this.retentiontime = retentiontime;
    }

    public String getIonMode() {
        return ionMode;
    }

    public void setIonMode(String ionMode) {
        this.ionMode = ionMode;
    }

    public Integer getNumPeaks() {
        return numPeaks;
    }

    public void setNumPeaks(Integer numPeaks) {
        this.numPeaks = numPeaks;
    }

    public Picos getPeaks() {
        return peaks;
    }

    public void setPeaks(Picos peaks) {
        this.peaks = peaks;
    }

}
