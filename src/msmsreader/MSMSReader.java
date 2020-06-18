/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msmsreader;

//import com.mysql.jdbc.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.apache.log4j.BasicConfigurator;
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
    public static OrganOrganism getOrganoOrganism() throws ClassNotFoundException, IOException, FileNotFoundException {
        OrganOrganism o = new OrganOrganism();
        String myPath = System.getProperty("user.dir");
        String j, especie, organo;
        int id;
        int i = 1;
        File myDirectory = new File(myPath);
        String[] files = myDirectory.list();
        String d;
        ArrayList<String> prueba1 = new ArrayList<String>();
        ArrayList<String> prueba2 = new ArrayList<String>();
        ArrayList<Integer> prueba4 = new ArrayList<Integer>();
        try {
            for (String file : files) {
                int z = file.indexOf(".xlsx");
                if (z != -1) {
                    d = file;
                    int a = d.indexOf("_");
                    j = d.substring(0, a);
                    id = Integer.parseInt(j);
                    d = d.substring(a + 1, d.length());
                    // System.out.println(d);
                    a = d.indexOf("_");
                    j = d.substring(0, a);
                    especie = j;
                    d = d.substring(a + 1, d.length());
                    a = d.indexOf(".");
                    j = d.substring(0, a);
                    organo = j;
                    prueba1.add(organo);
                    prueba2.add(especie);
                    prueba4.add(id);
                }

            }

            o.organ = prueba1;

            o.id = prueba4;
            o.organism = prueba2;

        } catch (Exception r) {

        }

        return o;

    }

    public static ArrayList<Compound> getFichero() throws ClassNotFoundException, IOException, FileNotFoundException {

        String myPath = System.getProperty("user.dir");

        int i = 1;
        File myDirectory = new File(myPath);
        String[] files = myDirectory.list();
        ArrayList<Compound> prueba2 = new ArrayList<Compound>();
        ArrayList<Compound> result2 = new ArrayList<Compound>();

        for (String file : files) {
            int z = file.indexOf(".xlsx");
            if (z != -1) {
                System.out.println(i);
                prueba2 = getLectura(file);
                result2.addAll(prueba2);
                i++;
            }
        }
        return result2;
    }

    public static ArrayList<Compound> getLectura(String d) throws ClassNotFoundException, IOException, FileNotFoundException {
        System.out.println(d);
        String myPath = System.getProperty("user.dir");
        File myFile = new File(myPath, d);
        FileInputStream fis = new FileInputStream(myFile);
        BasicConfigurator.configure();
        int a = d.indexOf("_");
        String j = d.substring(0, a);
        int id = Integer.parseInt(j);
        d = d.substring(a + 1, d.length());
        a = d.indexOf("_");
        j = d.substring(0, a);
        String especie = j;
        d = d.substring(a + 1, d.length());
        a = d.indexOf(".xlsx");
        j = d.substring(0, a);
        String organo = j;

        try ( XSSFWorkbook myWorkBook = new XSSFWorkbook(fis)) {
            File directorio = new File(myPath + "/" + id + especie + organo);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }
            Logger logger = Logger.getLogger(id + especie + organo + "_no_structuresLog");
            FileHandler fh;
            String archivo = "/" + id + especie + organo + "_no_structures.log";
            fh = new FileHandler(directorio + archivo);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = mySheet.iterator();
            int i = 0;
            int k;
            int rowNumb = 0;
            int x;
            ArrayList<Compound> prueba = new ArrayList<Compound>();

            while (rowIterator.hasNext()) {
                Compound e = new Compound();
                e.setOrgan(organo);
                e.setOrganism(especie);
                e.setId(id);
                Row row = rowIterator.next();
                // For each row, iterate through each columns
                Iterator<Cell> cellIterator = row.cellIterator();
                if (rowNumb >= 10 && rowNumb <= mySheet.getLastRowNum()) {
                    if (row.getLastCellNum() != 93 && row.getLastCellNum() != 50) {
                        for (k = 0; k <= row.getLastCellNum(); k++) {
                            Cell cell = row.getCell(k);
                            try {

                                switch (k) {
                                    case 1:
                                        try {
                                        switch (cell.getCellTypeEnum()) {
                                            case NUMERIC:
                                                Double f;

                                                f = cell.getNumericCellValue();
                                                // System.out.print("rt: " + cell.getStringCellValue() + "\t");
                                                if (f != null) {
                                                    e.setRetentiontime(cell.getNumericCellValue());
                                                }
                                                break;
                                            case STRING:
                                                if (cell.getStringCellValue().equals("null") == false) {
                                                    String s = cell.getStringCellValue();
                                                    if (s.isEmpty() == false || s.isBlank() == false || s.equals("null") == false) {
                                                        e.setRetentiontime(Double.parseDouble(s));
                                                    }

                                                }
                                                break;
                                            default:

                                        }
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 3:
                                        //System.out.print("NAME:" + cell.getStringCellValue() + "\t");
                                       try {
                                        String h = cell.getStringCellValue();

                                        e.setName(h);
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 4:
                                        // System.out.print("ADDUCT" + cell.getStringCellValue() + "\t");
                                        try {
                                        String v = cell.getStringCellValue();

                                        e.setAdduct(v);
                                        String w = v;
                                        try {
                                            w = w.substring(w.length() - 1, w.length());
                                            e.setIonMode(w);
                                        } catch (Exception p) {
                                            /* System.out.println("\n aduct " + p.getMessage());
                                            System.out.println("row " + rowNumb);
                                            System.out.println("column: " + k);*/
                                        }
                                    } catch (Exception r) {
                                        /*System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 9:
                                        try {
                                        switch (cell.getCellTypeEnum()) {
                                            case NUMERIC:
                                                Double g;

                                                g = cell.getNumericCellValue();
                                                // System.out.print("precursormz: " + cell.getStringCellValue() + "\t");
                                                if (g != null) {
                                                    e.setPrecursorMz(cell.getNumericCellValue());
                                                }

                                                break;
                                            case STRING:
                                                if (cell.getStringCellValue().equals("null") == false) {
                                                    String s = cell.getStringCellValue();
                                                    if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                        e.setPrecursorMz(Double.parseDouble(s));
                                                    }

                                                }
                                                break;
                                            default:

                                        }
                                    } catch (Exception r) {

                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 10:
                                        // System.out.print("FORMULA: " + cell.getStringCellValue() + "\t");
try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false || s.isBlank() == false || s.equals("null") == false) {
                                                e.setFormula(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 11:
                                        //System.out.print("ONTOLOGY: " + cell.getStringCellValue() + "\t");
try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false || s.isBlank() == false || s.equals("null") == false) {
                                                e.setOntology(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 13:
                                        // System.out.print("SMILES: " + cell.getStringCellValue() + "\t");
                                        try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setSmiles(s);
                                            } else {
                                                e.smiles = null;
                                                logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());
                                            }

                                        } else {
                                            e.smiles = null;
                                            logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());
                                        }
                                    } catch (Exception r) {
                                        e.smiles = null;
                                        logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());

                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;

                                    case 12:
                                        //System.out.print("INCHIKEY: " + cell.getStringCellValue() + "\t");
try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setInchikey(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        // logger.info("Sin Inchikey");
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 30:
                                        //System.out.print("MZ/SPECTRUM: " + cell.getStringCellValue() + "\t");


                                        try {
                                        String q = cell.getStringCellValue();
                                        String p = q;
                                        if (p.isEmpty() == false | p.equals("null") == false | p.isBlank() == false) {
                                            //System.out.println(p);
                                            //Picos h = new Peak();
                                            getPeakIntensitytoFromString(p, e);
                                            // System.out.println(h);
                                            //e.setPeaks(h);
                                        } else {
                                            e.setNumPeaks(0);
                                        }

                                    } catch (Exception r) {
                                        e.setNumPeaks(0);
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("CELL: " + cell.toString());
                                        System.out.print("MZ/SPECTRUM: " + cell.getNumericCellValue() + "\t");
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;

                                    default:
                                }
                            } catch (Exception r) {
                                /* System.out.println("\n" + r.getMessage());
                                System.out.println(r);
                                System.out.println("row " + rowNumb);
                                System.out.println("column: " + k);*/
                            }
                            //k++;
                        }
                    }
                    if (row.getLastCellNum() == 93) {
                        for (k = 0; k <= row.getLastCellNum(); k++) {
                            Cell cell = row.getCell(k);
                            try {
                                switch (k) {
                                    case 1:
                                       try {
                                        switch (cell.getCellTypeEnum()) {
                                            case NUMERIC:
                                                Double f;

                                                f = cell.getNumericCellValue();
                                                // System.out.print("precursormz: " + cell.getStringCellValue() + "\t");
                                                if (f != null) {
                                                    e.setRetentiontime(cell.getNumericCellValue());
                                                }

                                                break;
                                            case STRING:
                                                if (cell.getStringCellValue().equals("null") == false) {
                                                    String s = cell.getStringCellValue();
                                                    if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                        e.setRetentiontime(Double.parseDouble(s));
                                                    }

                                                }
                                                break;
                                            default:

                                        }
                                    } catch (Exception r) {
                                        /*System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 5:
                                        //System.out.print("NAME:" + cell.getStringCellValue() + "\t");
                                        try {
                                        String h = cell.getStringCellValue();

                                        e.setName(h);
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("CELL: " + cell.toString());
                                        System.out.print("MZ/SPECTRUM: " + cell.getNumericCellValue() + "\t");
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 6:
                                        // System.out.print("ADDUCT" + cell.getStringCellValue() + "\t");
                                       try {
                                        String v = cell.getStringCellValue();

                                        e.setAdduct(v);
                                        String w = v;
                                        try {
                                            w = w.substring(w.length() - 1, w.length());
                                            e.setIonMode(w);
                                        } catch (Exception p) {
                                            /* System.out.println("\n aduct " + p.getMessage());
                                            System.out.println("row " + rowNumb);
                                            System.out.println("column: " + k);*/
                                        }
                                    } catch (Exception r) {
                                        /*System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 12:
                                       try {
                                        switch (cell.getCellTypeEnum()) {
                                            case NUMERIC:
                                                Double g;

                                                g = cell.getNumericCellValue();
                                                // System.out.print("precursormz: " + cell.getStringCellValue() + "\t");
                                                if (g != null) {
                                                    e.setPrecursorMz(cell.getNumericCellValue());
                                                }

                                                break;
                                            case STRING:
                                                if (cell.getStringCellValue().equals("null") == false) {
                                                    String s = cell.getStringCellValue();
                                                    if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                        e.setPrecursorMz(Double.parseDouble(s));
                                                    }

                                                }

                                                break;
                                            default:

                                        }
                                    } catch (Exception r) {
                                        /*  System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 13:
                                        // System.out.print("FORMULA: " + cell.getStringCellValue() + "\t");
                                       try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setFormula(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 14:
                                        //System.out.print("ONTOLOGY: " + cell.getStringCellValue() + "\t");
                                        try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setOntology(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 16:
                                        // System.out.print("SMILES: " + cell.getStringCellValue() + "\t");
                                       try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setSmiles(s);
                                            } else {
                                                logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());
                                                e.smiles = null;
                                            }

                                        } else {
                                            e.smiles = null;
                                            logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());
                                        }
                                    } catch (Exception r) {
                                        e.smiles = null;
                                        logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());
                                        /*System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }

                                    break;

                                    case 15:
                                        //System.out.print("INCHIKEY: " + cell.getStringCellValue() + "\t");
                                        try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setInchikey(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        // logger.info("Sin Inchikey");
                                        /*  System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }

                                    break;
                                    case 35:
                                        //System.out.print("MZ/SPECTRUM: " + cell.getStringCellValue() + "\t");


                                        try {
                                        String q = cell.getStringCellValue();
                                        String p = q;
                                        if (p.isEmpty() == false | p.equals("null") == false | p.isBlank() == false) {
                                            //System.out.println(p);
                                            //Picos h = new Peak();
                                            getPeakIntensitytoFromString(p, e);
                                            // System.out.println(h);
                                            //e.setPeaks(h);
                                        } else {
                                            e.setNumPeaks(0);
                                        }

                                    } catch (Exception r) {
                                        e.setNumPeaks(0);
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("CELL: " + cell.toString());
                                        System.out.print("MZ/SPECTRUM: " + cell.getNumericCellValue() + "\t");
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;

                                    default:
                                }
                            } catch (Exception r) {
                                /*  System.out.println("\n" + r.getMessage());
                                System.out.println(r);
                                System.out.println("row " + rowNumb);
                                System.out.println("column: " + k);*/
                            }
                            //k++;
                        }
                    }
                    if (row.getLastCellNum() == 50) {
                        for (k = 0; k <= row.getLastCellNum(); k++) {
                            Cell cell = row.getCell(k);
                            try {

                                switch (k) {
                                    case 2:
                                       try {
                                        switch (cell.getCellTypeEnum()) {
                                            case NUMERIC:
                                                Double f;

                                                f = cell.getNumericCellValue();
                                                // System.out.print("precursormz: " + cell.getStringCellValue() + "\t");
                                                if (f != null) {
                                                    e.setRetentiontime(cell.getNumericCellValue());
                                                }

                                                break;
                                            case STRING:
                                                if (cell.getStringCellValue().equals("null") == false) {
                                                    String s = cell.getStringCellValue();
                                                    if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                        e.setRetentiontime(Double.parseDouble(s));
                                                    }

                                                }
                                                break;
                                            default:

                                        }
                                    } catch (Exception r) {
                                        /*System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 6:
                                        //System.out.print("NAME:" + cell.getStringCellValue() + "\t");
                                      try {
                                        String h = cell.getStringCellValue();

                                        e.setName(h);
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 7:
                                        // System.out.print("ADDUCT" + cell.getStringCellValue() + "\t");
                                        try {
                                        String v = cell.getStringCellValue();

                                        e.setAdduct(v);
                                        String w = v;
                                        try {
                                            w = w.substring(w.length() - 1, w.length());
                                            e.setIonMode(w);
                                        } catch (Exception p) {
                                            /*System.out.println("\n aduct " + p.getMessage());
                                            System.out.println("row " + rowNumb);
                                            System.out.println("column: " + k);*/
                                        }
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }

                                    break;
                                    case 13:
                                       try {
                                        switch (cell.getCellTypeEnum()) {
                                            case NUMERIC:
                                                Double g;

                                                g = cell.getNumericCellValue();
                                                // System.out.print("precursormz: " + cell.getStringCellValue() + "\t");
                                                if (g != null) {
                                                    e.setPrecursorMz(cell.getNumericCellValue());
                                                }

                                                break;
                                            case STRING:
                                                if (cell.getStringCellValue().equals("null") == false) {
                                                    String s = cell.getStringCellValue();
                                                    if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                        e.setPrecursorMz(Double.parseDouble(s));
                                                    }

                                                }
                                                break;
                                            default:

                                        }
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;
                                    case 14:
                                        // System.out.print("FORMULA: " + cell.getStringCellValue() + "\t");
                                        try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setFormula(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        /*   System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }

                                    break;
                                    case 15:
                                        //System.out.print("ONTOLOGY: " + cell.getStringCellValue() + "\t");
                                        try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setOntology(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }

                                    break;
                                    case 17:
                                        // System.out.print("SMILES: " + cell.getStringCellValue() + "\t");
try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setSmiles(s);
                                            } else {
                                                logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());
                                                e.smiles = null;
                                            }

                                        } else {
                                            logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());
                                            e.smiles = null;
                                        }
                                    } catch (Exception r) {
                                        e.smiles = null;
                                        logger.info("ROW:" + String.valueOf(rowNumb + 1) + ", NAME:" + e.getName());
                                        /*  System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }

                                    break;

                                    case 16:
                                        //System.out.print("INCHIKEY: " + cell.getStringCellValue() + "\t");
                                        try {
                                        if (cell.getStringCellValue().equals("null") == false) {
                                            String s = cell.getStringCellValue();
                                            if (s.isEmpty() == false | s.isBlank() == false | s.equals("null") == false) {
                                                e.setInchikey(s);
                                            }

                                        }
                                    } catch (Exception r) {
                                        // logger.info("Sin Inchikey");
                                        /*System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }

                                    break;
                                    case 36:
                                        //System.out.print("MZ/SPECTRUM: " + cell.getStringCellValue() + "\t");


                                        try {
                                        String q = cell.getStringCellValue();
                                        String p = q;
                                        if (p.isEmpty() == false | p.equals("null") == false | p.isBlank() == false) {

                                            getPeakIntensitytoFromString(p, e);

                                        } else {
                                            e.setNumPeaks(0);
                                        }

                                    } catch (Exception r) {
                                        e.setNumPeaks(0);
                                        /* System.out.println("\n" + r.getMessage());
                                        System.out.println(r);
                                        System.out.println("CELL: " + cell.toString());
                                        System.out.print("MZ/SPECTRUM: " + cell.getNumericCellValue() + "\t");
                                        System.out.println("row " + rowNumb);
                                        System.out.println("column: " + k);*/
                                    }
                                    break;

                                    default:
                                }
                            } catch (Exception r) {
                                /* System.out.println("\n" + r.getMessage());
                                System.out.println(r);
                                System.out.println("row " + rowNumb);
                                System.out.println("column: " + k);*/
                            }

                        }
                    }

                    try {

                        //System.out.println("\n");
                        // System.out.println(e);
                        if (e.smiles != null) {
                            prueba.add(e);
                        }
                    } catch (Exception h) {
                        //System.out.println("\n" + h.getMessage());
                    }
                    i++;
                }
                rowNumb++;

            }
            return prueba;
        }

    }

    /**
     *
     * @param p
     * @param e
     */
    public static void getPeakIntensitytoFromString(String p, Compound e) {

        List<Double> piks = new ArrayList();
        List<Integer> intensity = new ArrayList();
        Peak l = new Peak();
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

                    piks.add(pico);
                    p = p.substring(a + 1, p.length());

                    z = p.indexOf(" ");
                    if (z != -1) {
                        double intensidad = Double.parseDouble(p.substring(0, z));

                        intensity.add((int) intensidad);
                        p = p.substring(z + 1, p.length());

                    } else {
                        double intensidad = Double.parseDouble(p);
                        intensity.add((int) intensidad);
                    }
                } else {
                    double intensidad = Double.parseDouble(p);
                    intensity.add((int) intensidad);
                }

            }
        }
        e.setNumPeaks(i);
        l.setMz(piks);
        l.setIntensity(intensity);
        e.setPeaks(l);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, FileNotFoundException, SQLException {
        int i;
        ArrayList<Compound> todos2 = new ArrayList<Compound>();
        OrganOrganism o = new OrganOrganism();
        o = getOrganoOrganism();
        todos2 = getFichero();
        DatabaseInsertion sql = new DatabaseInsertion();
        try {
            sql.connect();
            sql.createOrganism();
            sql.createOrgan();
            sql.createOrgan_Compound();
            System.out.println("Organisms to be inserted: \n" + o.organism);
            sql.InsertOrganism(o);
            System.out.println("Organs to be inserted: \n" + o.organ);
            sql.InsertOrgan(o);
            sql.disconnect();
        } catch (Exception h) {
            System.out.println("\n" + h.getMessage());
        }
        String myPath = System.getProperty("user.dir");
        File directorio = new File(myPath + "/" + "In our DataBase");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        Logger logger = Logger.getLogger("There is inchi_key in our DataBase");
        FileHandler fh;
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH.mm.ss dd-MM-yyyy");
        String historial = hourdateFormat.format(date);
        String namefile = historial + "_compound.log";
        String archivo = "/" + namefile;
        // This block configure the logger with handler and formatter
        fh = new FileHandler(directorio + archivo);
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        try {
            int j = 0;
            int k = 0;
            int p = 0;
            int l = 0;
            int e = 0;
            sql.connect();

            for (i = 0; i < todos2.size(); i++) {
                int compound_id = sql.checkInchi(todos2.get(i));
                if (compound_id == -1) {
                    //logger.info("NAME: " + todos2.get(i).name + "   INCHIKEY: " + todos2.get(i).inchikey);
                    k++;
                } else {
                    int insert = sql.InsertOrganCompound(todos2.get(i), compound_id);

                    if (insert == 1) {
                        l++;
                        int msms_id = sql.InsertCompoundMsms(todos2.get(i), compound_id);
                        if (msms_id != -1) {
                            sql.InsertCompoundMsPeaks(todos2.get(i), msms_id);
                            //System.out.println("Inserted MS/MS from compound : \n");
                            //System.out.println(todos2.get(i));
                            logger.info("INCHIKEY: " + todos2.get(i).inchikey + " COMPOUND_ID: " + compound_id + " MSMS_ID: " + msms_id + " NAME_ORGAN: " + todos2.get(i).organ + " NAME_ORGANISM: " + todos2.get(i).organism);
                            p++;
                        }
                    } else {
                        int msms_id = sql.InsertCompoundMsms2(todos2.get(i), compound_id);
                        if (msms_id != -1) {
                            sql.InsertCompoundMsPeaks(todos2.get(i), msms_id);
                            //System.out.println("Inserted MS/MS from compound : \n");
                            //System.out.println(todos2.get(i));
                            logger.info("INCHIKEY: " + todos2.get(i).inchikey + " COMPOUND_ID: " + compound_id + " MSMS_ID: " + msms_id + " NAME_ORGAN: " + todos2.get(i).organ + " NAME_ORGANISM: " + todos2.get(i).organism);
                            p++;
                        }
                    }
                    j++;
                }
                e++;
            }
            System.out.println("Extracted compounds: " + e);
            System.out.println("Compounds without compound_id in the database: " + k);
            System.out.println("Compounds with compound_id in the database: " + j);
            System.out.println("Relations between organ and compounds : " + l);
            System.out.println("Inserted compounds: " + p);
            sql.disconnect();
        } catch (Exception h) {
            System.out.println("\n" + h.getMessage());
        }
    }
}
