package com.ensah.services;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ensah.bo.*;
import com.ensah.repositories.*;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class InventaireServiceImp implements IInventaireService {

    @Autowired
    private InventaireRepository inventaireRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ReceptionRepository receptionRepository;

    @Autowired
    private LivraisonRepository livraisonRepository;

    @Autowired
    private EcartRepository ecartRepository;

    @Override
    public List<Produit> telechargerInventaire(Inventaire i){
        List<Livraison> livraisons = livraisonRepository.rechercherParDatesEntrepot(LocalDate.of(2025, 1, 1),i.getDateInventaire(),i.getEntrepot());
        List<Reception> receptions = receptionRepository.rechercherParDatesEntrepot(LocalDate.of(2025, 1, 1),i.getDateInventaire(),i.getEntrepot());

        // Dictionnaire pour calculer la quantité par produit
        Map<String, Integer> dicProduitQuantite = new HashMap<>();
        for (Reception r : receptions) {
            dicProduitQuantite.put(r.getProduit(), dicProduitQuantite.getOrDefault(r.getProduit(), 0) + r.getQuantite());
        }

        // Soustraire les quantités de livraison
        for (Livraison l : livraisons) {
            dicProduitQuantite.put(l.getProduit(), dicProduitQuantite.getOrDefault(l.getProduit(), 0) - l.getQuantite());
        }

        // Créer et enregistrer les écarts
        //Cree liste des produit a afficher
        List<Produit> produits = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : dicProduitQuantite.entrySet()) {
            Produit produit = produitRepository.trouverProduit(entry.getKey());

            //fekola inventaire quantite ghada tebedel 3ela 7essabo ya3eni 3ela 7essab
            //depot maghadach tchekel lina mochekil 7it 7ena kan5edemo ghir be quant deyal
            //recep o livraison xdeyal kola entrepot

            produit.setQuantite(entry.getValue());
            produits.add(produit);
        }

        //catrage3 lina list deyal lproduit likayenin fehadak entrepot
        //o li ghadi ne3amero bihom lfichier + nezido lihom wa7ed partiya
        //deyal justification fe fichier ye3amrouha bach
        //fach ne9eraw lfichier jedid deyal inve physi neda5elo justification
        //l ecart
        return produits;

    }

    @Override
    public List<Produit> ajouterInventaire(Inventaire i) {
        inventaireRepository.save(i);

        List<Produit> produits = telechargerInventaire(i);

        for (Produit produit : produits) {
            if (produit != null) { // Vérifier si le produit existe
                Ecart ecart = new Ecart(i, produit, produit.getQuantite(), -10, "");
                ecartRepository.save(ecart);
            }
        }

        //ByteArrayInputStream f = exporterInventaireExcel(produits);

        // Enfin, sauvegarder l'inventaire
        return produits;

    }

    @Override
    public void modifierInventaire(Inventaire i) {
        inventaireRepository.save(i);
    }

    @Override
    public List<Ecart> afficher(String entrepot,LocalDate date) {
        Inventaire i = inventaireRepository.findByEntrepotAndDateInventaireContainsIgnoreCase(entrepot,date);
        if(i.isValide()) {
            return ecartRepository.findByInventaireId(i.getId());

        }else {
            return null;
        }
    }



    /*@Override
    public List<Ecart> calculeEcart(LocalDate d,String entrepot,MultipartFile file) {
    	Inventaire i = inventaireRepository.findInv(d,entrepot);
    	return ecartRepository.findByInventaire(i);

    }*/

    @Override
    public List<Ecart> calculeEcart(LocalDate d, String entrepot, MultipartFile file) {
        Inventaire i = inventaireRepository.findInv(d, entrepot);
        List<Ecart> ecarts = ecartRepository.findByInventaire(i);
        Map<String, Long> produits = new HashMap<>();
        Map<String, String> produits2 = new HashMap<>();

        try (InputStream is = file.getInputStream(); HSSFWorkbook workbook = new HSSFWorkbook(is)) {
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Lire l'en-tête pour localiser les colonnes
            Map<String, Integer> headerMap = new HashMap<>();
            if (rowIterator.hasNext()) {
                Row header = rowIterator.next();
                for (Cell cell : header) {
                    headerMap.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
                }
            }
            Integer nomIndex = headerMap.get("nom");
            Integer quantiteIndex = headerMap.get("quantite");
            Integer JustificationIndex = headerMap.get("justification");

            if (nomIndex == null || quantiteIndex == null) {
                throw new RuntimeException("Colonnes 'Code Produit' ou 'Quantite' non trouvées dans le fichier Excel.");
            }

            // Lecture des données ligne par ligne
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                Cell nomCell = row.getCell(nomIndex);
                Cell quantiteCell = row.getCell(quantiteIndex);
                Cell JustificationCell = row.getCell(JustificationIndex);

                if (nomCell != null && quantiteCell != null) {
                    String nom = nomCell.getStringCellValue().trim().toLowerCase();
                    long quantite = (long)quantiteCell.getNumericCellValue();
                    String justification = (String)JustificationCell.getStringCellValue();
                    produits.put(nom, quantite);
                    produits2.put(nom,justification);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier Excel", e);
        }

        // Mettre à jour les écarts
        for (Ecart e : ecarts) {
            String nomProduit = e.getProduit().getNom().toLowerCase();
            if (produits.containsKey(nomProduit)) {
                long quantiteApres = produits.get(nomProduit);
                e.setQuantiteApres(quantiteApres);
                e.setDifference(quantiteApres - e.getQuantiteAvant());
                e.setJustification(produits2.get(nomProduit));
                ecartRepository.save(e);
            }
        }

        return ecarts;
    }




   /* @Override
    public List<Ecart> calculeEcart(LocalDate d,String entrepot,MultipartFile file) {
    	Inventaire i = inventaireRepository.findInv(d,entrepot);
    	List<Ecart> l = ecartRepository.findByInventaire(i);
    	List<Map<String, Object>> produits = new ArrayList<>();
		//lecture deyal lfichier
    	 try (InputStream is = file.getInputStream(); HSSFWorkbook workbook = new HSSFWorkbook(is)) {
    		 HSSFSheet sheet = workbook.getSheetAt(0);
             Iterator<Row> rowIterator = sheet.iterator();

             // Lire la première ligne (en-tête) pour connaître les indices des colonnes
             Map<String, Integer> headerMap = new HashMap<>();
             if (rowIterator.hasNext()) {
            	 Row header = rowIterator.next();
                 for (Cell cell : header) {
                     headerMap.put(cell.getStringCellValue().trim().toLowerCase(), cell.getColumnIndex());
                 }
             }

             Integer nomIndex = headerMap.get("nom");
             Integer quantiteIndex = headerMap.get("quantite");

             if (nomIndex == null || quantiteIndex == null) {
                 throw new RuntimeException("Colonnes 'Nom' ou 'Quantite' non trouvées");
             }

             // Parcourir les lignes restantes
             while (rowIterator.hasNext()) {
            	 Row row = rowIterator.next();
                 Cell nomCell = row.getCell(nomIndex);
                 Cell quantiteCell = row.getCell(quantiteIndex);

                 String nom = nomCell != null ? nomCell.getStringCellValue() : "";
                 double quantite = quantiteCell != null ? quantiteCell.getNumericCellValue() : 0.0;

                 Map<String, Object> produit = new HashMap<>();
                 produit.put("nom", nom);
                 produit.put("quantite", quantite);

                 produits.add(produit);
             }
         }

    	 for(Ecart e : l) {
    		 e.setQuantiteApres(produits[e.getProduit()]);
    		 e.getDifference(produits[e.getProduit()]-e.getQuantiteAvant());
    		 ecartRepository.save(e);
    	 }

         return l;
    }*/


    @Override
    public void valideInventaire(Long id/*,user*/) {
        inventaireRepository.valideInventaire(id/*,user*/);

    }

    @Override
    public void supprimerInventaire(Inventaire i) {
        inventaireRepository.delete(i);
    }

    @Override
    public Inventaire selectionnerInventaire(Long id) {
        return inventaireRepository.findById(id).orElse(null);
    }

    @Override
    public List<Inventaire> listeInventaire() {
        return inventaireRepository.findAll();
    }
    /*public void exporterInventaireExcel(HttpServletResponse responce) {

    	//https://www.youtube.com/watch?v=5CfHKTh86hw

        List<Produit> produits = produitRepository.findAll();

    	System.out.println("ha na hena");

        try (HSSFWorkbook workbook = new HSSFWorkbook ()) {
        	HSSFSheet sheet = workbook.createSheet("Inventaire");

            // En-têtes
        	HSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("Code Produit");
            header.createCell(1).setCellValue("Nom");
            header.createCell(2).setCellValue("Quantite");
            header.createCell(3).setCellValue("Unite");
            header.createCell(4).setCellValue("Prix Unitaire");
            header.createCell(5).setCellValue("Commantaire");


            // Remplir les données (peut être vide si exception avant)
            int rowIdx = 1;
            for (Produit p : produits) {
            	HSSFRow row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getNom());
                row.createCell(2).setCellValue(p.getQuantite());
                row.createCell(3).setCellValue(p.getUnite());
                row.createCell(4).setCellValue(p.getPrixUnitaire());
            }

            ServletOutputStream out = responce.getOutputStream();
            workbook.write(out);
            workbook.close();
            out.close();

        } catch (Exception e) {
            // En cas d'erreur dans la génération du fichier, on retourne quand même un fichier vide avec en-têtes
            try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            	HSSFSheet sheet = workbook.createSheet("Inventaire");
            	HSSFRow header = sheet.createRow(0);
                header.createCell(0).setCellValue("Code Produit");
                header.createCell(1).setCellValue("Nom");
                header.createCell(2).setCellValue("Quantité");
                header.createCell(3).setCellValue("Unite");
                header.createCell(4).setCellValue("Prix Unitaire");
                header.createCell(5).setCellValue("Commantaire");

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                workbook.write(out);
                System.out.println("ha na hena");
            } catch (Exception ex) {
                // Dernier recours : retourner un flux vide (vraiment en cas de grave erreur)
            }
        }
    }*/

    @Override
    public ByteArrayInputStream exporterInventaireExcel(List<Produit> produits) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Inventaire> rechercherInventaire(LocalDate date1, LocalDate date2, String e) {
        // TODO Auto-generated method stub
        return null;
    }

    //hena nezido invantaire jedide o ntelecharjiw lfichier
    @Override
    public void exporterInventaireExcel(HttpServletResponse responce,Inventaire i) throws Exception{



        List<Produit> produits = ajouterInventaire(i);

        HSSFWorkbook workbook = new HSSFWorkbook ();
        HSSFSheet sheet = workbook.createSheet("Inventaire");

        // En-têtes
        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Code Produit");
        header.createCell(1).setCellValue("Nom");
        header.createCell(2).setCellValue("Quantite");
        header.createCell(3).setCellValue("Unite");
        header.createCell(4).setCellValue("Prix Unitaire");
        header.createCell(5).setCellValue("Justification");


        // Remplir les données (peut être vide si exception avant)
        int rowIdx = 1;
        for (Produit p : produits) {
            HSSFRow row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNom());
            row.createCell(2).setCellValue(p.getQuantite());
            row.createCell(3).setCellValue(p.getUnite());
            row.createCell(4).setCellValue(p.getPrixUnitaire());
            row.createCell(5).setCellValue("");
        }

        ServletOutputStream out = responce.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();


    }

    //hena bach ntelechargiw invantaire 3adena fe tableau deja existe
    public List<Produit> produitInventaire(Inventaire i) {


        List<Produit> produits = telechargerInventaire(i);
        return produits;

    }

    @Override
    public void telechargerInventaireExcel(HttpServletResponse responce,Long id) throws Exception{
        Inventaire i = inventaireRepository.findById(id).orElse(null);
        List<Produit> produits = produitInventaire(i);

        HSSFWorkbook workbook = new HSSFWorkbook ();
        HSSFSheet sheet = workbook.createSheet("Inventaire");

        // En-têtes
        HSSFRow header = sheet.createRow(0);
        header.createCell(0).setCellValue("Code Produit");
        header.createCell(1).setCellValue("Nom");
        header.createCell(2).setCellValue("Quantite");
        header.createCell(3).setCellValue("Unite");
        header.createCell(4).setCellValue("Prix Unitaire");
        header.createCell(5).setCellValue("Commantaire");


        // Remplir les données (peut être vide si exception avant)
        int rowIdx = 1;
        for (Produit p : produits) {
            HSSFRow row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(p.getId());
            row.createCell(1).setCellValue(p.getNom());
            row.createCell(2).setCellValue(p.getQuantite());
            row.createCell(3).setCellValue(p.getUnite());
            row.createCell(4).setCellValue(p.getPrixUnitaire());
        }

        ServletOutputStream out = responce.getOutputStream();
        workbook.write(out);
        workbook.close();
        out.close();



    }


}