package msmsreader;

import java.io.Serializable;

public class Compound implements Serializable {

    private static final long serialVersionUID = 1L;
    public Integer id;
    public String organism;
    public String organ;

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
    public Peak peaks;

    public Compound() {
    }

    @Override
    public String toString() {
        return "Compound{" + "id=" + id + ", organism=" + organism + ", organ=" + organ + ", name=" + name + ", precursorMz=" + precursorMz + ", adduct=" + adduct + ", smiles=" + smiles + ", inchikey=" + inchikey + ", ontology=" + ontology + ", formula=" + formula + ", retentiontime=" + retentiontime + ", ionMode=" + ionMode + ", numPeaks=" + numPeaks + ", peaks=" + peaks + '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
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

    /**
     *
     * @return
     */
    public Peak getPeaks() {
        return peaks;
    }

    /**
     *
     * @param peaks
     */
    public void setPeaks(Peak peaks) {
        this.peaks = peaks;
    }

    public Compound(Integer id, String organism, String organ, String name, Double precursorMz, String adduct, String smiles, String inchikey, String ontology, String formula, Double retentiontime, String ionMode, Integer numPeaks, Peak peaks) {
        this.id = id;
        this.organism = organism;
        this.organ = organ;
        this.name = name;
        this.precursorMz = precursorMz;
        this.adduct = adduct;
        this.smiles = smiles;
        this.inchikey = inchikey;
        this.ontology = ontology;
        this.formula = formula;
        this.retentiontime = retentiontime;
        this.ionMode = ionMode;
        this.numPeaks = numPeaks;
        this.peaks = peaks;
    }

}
