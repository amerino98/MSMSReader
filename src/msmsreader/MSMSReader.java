/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msmsreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Álvaro Merino
 */
public class MSMSReader {

    /**
     *
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void getFichero() throws ClassNotFoundException, IOException, FileNotFoundException {
        String myPath = System.getProperty("user.dir");
        // Hacer un for para listar todos los elementos de la carpeta resources

        // Utilizar patrones regulares para obtener el organismo
        // y el organo
        // 1_Mouse_Adrenal gland_1
        // organismo: Mouse
        // órgano: Adrenal gland
        // Split by _
        File myDirectory = new File(myPath);
        String[] files = myDirectory.list();
        for (String file : files) {
            //System.out.println(file);
        }
    }

    public static void getLectura() throws ClassNotFoundException, IOException, FileNotFoundException {

        String myPath = System.getProperty("user.dir");
        // Hacer un for para listar todos los elementos de la carpeta resources

        // Utilizar patrones regulares para obtener el organismo
        // y el organo
        // 1_Mouse_Adrenal gland_1
        // organismo: Mouse
        // órgano: Adrenal gland
        // Split by _
        File myDirectory = new File(myPath);
        String[] files = myDirectory.list();
        for (String file : files) {
            //System.out.println(file);
        }

        File myFile = new File(myPath, "2_Mouse_Brain_1.xlsx");

        FileInputStream fis = new FileInputStream(myFile);
        // Return first sheet
        // from the XLSX workbook
        try ( // Finds the workbook instance for XLSX file
                 XSSFWorkbook myWorkBook = new XSSFWorkbook(fis)) {
            // Return first sheet
            // from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = mySheet.iterator();
            int i = 0;
            int k = 0;
            int j = 0;
            Elemento arrayObjetos[] = new Elemento[5000];
            while (rowIterator.hasNext()) {
                Elemento e = new Elemento();
                // Peak peak = new Peak();
                Row row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                if (j >= 11) {
                    while (cellIterator.hasNext()) {
                        //System.out.print(k + "\t");
                        Cell cell = cellIterator.next();
                        switch (k) {
                            case 1:
                                //System.out.print("AVERAGE RETENTION TIME:" + cell.getStringCellValue() + "\t");
                                e.setRetentiontime(cell.getNumericCellValue());
                                break;
                            case 3:
                                //System.out.print("NAME:" + cell.getStringCellValue() + "\t");
                                e.setName(cell.getStringCellValue());
                                break;
                            case 4:
                                // System.out.print("ADDUCT" + cell.getStringCellValue() + "\t");
                                e.setAdduct(cell.getStringCellValue());
                                String w = cell.getStringCellValue();
                                try {
                                    w = w.substring(w.length() - 1, w.length());
                                    e.setIonMode(w);
                                } catch (Exception a) {
                                    System.out.println("\n aduct" + a.getMessage());
                                }

                                break;
                            case 10:
                                // System.out.print("FORMULA: " + cell.getStringCellValue() + "\t");
                                e.setFormula(cell.getStringCellValue());
                                break;
                            case 11:
                                //System.out.print("ONTOLOGY: " + cell.getStringCellValue() + "\t");
                                e.setOntology(cell.getStringCellValue());
                                break;
                            case 13:
                                // System.out.print("SMILES: " + cell.getStringCellValue() + "\t");
                                e.setSmiles(cell.getStringCellValue());
                                break;

                            case 12:
                                //System.out.print("INCHIKEY: " + cell.getStringCellValue() + "\t");
                                e.setInchikey(cell.getStringCellValue());
                                break;
                            case 30:
                                //System.out.print("MZ/SPECTRUM: " + cell.getStringCellValue() + "\t");

                                /*switch (cell.getCellTypeEnum()) {
                                    case NUMERIC:
                                        // System.out.print("MZ/SPECTRUM: " + cell.getNumericCellValue() + "\t");
                                        double n = cell.getNumericCellValue();
                                        String s = String.valueOf(n);
                                        System.out.println(n);
                                        //if (s.isEmpty() == false) {
                                        //System.out.println(p);
                                        //Picos h = new Picos();
                                        getPeakIntensitytoFromString(s, e);
                                        // System.out.println(h);
                                        //e.setPeaks(h);
                                        //}
                                        break;
                                    case STRING:*/
                                try {
                                String p = cell.getStringCellValue();
                                if (p.isEmpty() == false) {
                                    //System.out.println(p);
                                    //Picos h = new Picos();
                                    getPeakIntensitytoFromString(p, e);
                                    // System.out.println(h);
                                    //e.setPeaks(h);
                                }
                            } catch (Exception a) {
                                System.out.println("\n" + a.getMessage());
                            }
                            break;
                            // }

                            //break;
                            default:
                        }
                        k++;
                    }
                    System.out.println("\n");
                    System.out.println(e);
                    arrayObjetos[i] = e;
                    k = 0;

                    i++;
                }
                j++;

            }
        }

    }

    /**
     *
     * @param p
     * @param e
     */
    public static void getPeakIntensitytoFromString(String p, Elemento e) {
        // System.out.println("MZ/SPECTRUM: " + p + "\t");
        List<Double> piks = new ArrayList();
        List<Integer> intensity = new ArrayList();
        Picos d = new Picos();
        int z = 0;
        int a = 0;
        int i = 0;
        while (a != -1 && z != -1) {
            if (p.isEmpty()) {
                break;
            } else {
                a = p.indexOf(":");
                if (a != -1) {
                    double pico = Double.parseDouble(p.substring(0, a));
                    i++;
                    // System.out.println(pico);
                    piks.add(pico);
                    p = p.substring(a + 1, p.length());
                    //System.out.println(p);
                    z = p.indexOf(" ");
                    if (z != -1) {
                        double intensidad = Double.parseDouble(p.substring(0, z));
                        // System.out.println(intensidad);
                        intensity.add((int) intensidad);
                        p = p.substring(z + 1, p.length());
                        //System.out.println(p);
                    } else {
                        double intensidad = Double.parseDouble(p);
                        intensity.add((int) intensidad);
                    }
                } else {
                    double intensidad = Double.parseDouble(p);
                    intensity.add((int) intensidad);
                    //System.out.println(intensidad);
                }

            }
        }
        e.setNumPeaks(i);
        d.setMz(piks);
        d.setIntensity(intensity);
        // System.out.println(piks);
        // System.out.println(intensity);
        e.setPeaks(d);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, FileNotFoundException {
        getLectura();
        /*
		 * Elemento[] a= getLectura(); int i; for (i=0;i<=a.length;i++) {
		 * System.out.println(a[i]); }
         */
    }
}
