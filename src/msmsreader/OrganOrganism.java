/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msmsreader;

import java.util.List;

/**
 *
 * @author Álvaro Merino
 */
public class OrganOrganism {

    public List<String> organ;

    /**
     *
     */
    public List<String> organism;

    /**
     *
     */
    public List<Integer> id;

    public OrganOrganism(List<String> organ, List<String> organism, List<Integer> id) {
        this.organ = organ;
        this.organism = organism;
        this.id = id;
    }

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public List<String> getOrgan() {
        return organ;
    }

    public void setOrgan(List<String> organ) {
        this.organ = organ;
    }

    public List<String> getOrganism() {
        return organism;
    }

    public void setOrganism(List<String> organism) {
        this.organism = organism;
    }

    @Override
    public String toString() {
        return "OrganoOrganism{" + "organ=" + organ + ", organism=" + organism + ", id=" + id + '}';
    }

    public OrganOrganism() {
    }

}
