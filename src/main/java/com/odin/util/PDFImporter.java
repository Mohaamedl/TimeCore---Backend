package com.odin.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 * Utility class for importing data from PDF files.
 */
public class PDFImporter {
    
    private static final Logger LOGGER = Logger.getLogger(PDFImporter.class.getName());
    
    static {

        Logger.getLogger("org.apache.pdfbox").setLevel(Level.SEVERE);
        Logger.getLogger("org.apache.fontbox").setLevel(Level.SEVERE);
    }

    /**
     * Default constructor.
     */
    public PDFImporter() {
    }

    /**
     * Main method for testing PDF import functionality.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        try {

            String pdfPath = PDFImporter.class.getClassLoader()
                    .getResource("pdfs/Cronograma_24.0226_janeiro_2025.pdf")
                    .getFile();
            
            File pdfFile = new File(pdfPath);
            if (!pdfFile.exists()) {
                System.err.println("pdf in: " + pdfPath);
                return;
            }
            
            String text = readPdfText(pdfPath);
            if (text != null && !text.trim().isEmpty()) {
                System.out.println("text PDF:");
                System.out.println("----------------------------------------");
                System.out.println(text);
                System.out.println("----------------------------------------");
            } else {
                System.out.println("no text in PDF.");
            }
            
        } catch (Exception e) {
            System.err.println("Error  reading PDF: " + e.getMessage());
        }
    }
    
    /**
     * Reads all text from a PDF file.
     *
     * @param pdfPath Path to the PDF file
     * @return String containing all text from the PDF
     * @throws IOException if there's an error reading the file
     */
    public static String readPdfText(String pdfPath) throws IOException {
        File file = new File(pdfPath);
        if (!file.exists()) {
            throw new IOException("File not found : " + pdfPath);
        }

        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);
            return stripper.getText(document);
        }
    }

    /**
     * Reads text from a specific page range in a PDF file.
     *
     * @param pdfPath Path to the PDF file
     * @param startPage Starting page number (1-based)
     * @param endPage Ending page number (inclusive)
     * @return String containing text from the specified page range
     * @throws IOException if there's an error reading the file
     */
    public String readPdfTextFromPages(String pdfPath, int startPage, int endPage) throws IOException {
        try (PDDocument document = Loader.loadPDF(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            return stripper.getText(document);
        }
    }

    /**
     * Reads text from a PDF file page by page.
     *
     * @param pdfPath Path to the PDF file
     * @return List of strings, where each string contains text from one page
     * @throws IOException if there's an error reading the file
     */
    public List<String> readPdfTextByPages(String pdfPath) throws IOException {
        List<String> pageTexts = new ArrayList<>();
        
        try (PDDocument document = Loader.loadPDF(new File(pdfPath))) {
            PDFTextStripper stripper = new PDFTextStripper();
            int pageCount = document.getNumberOfPages();
            
            for (int i = 1; i <= pageCount; i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                String pageText = stripper.getText(document);
                pageTexts.add(pageText);
            }
        }
        
        return pageTexts;
    }

    /**
     * Gets the number of pages in a PDF file.
     *
     * @param pdfPath Path to the PDF file
     * @return number of pages in the PDF
     * @throws IOException if there's an error reading the file
     */
    public int getPageCount(String pdfPath) throws IOException {
        try (PDDocument document = Loader.loadPDF(new File(pdfPath))) {
            return document.getNumberOfPages();
        }
    }

    /**
     * Checks if a file is a valid PDF.
     *
     * @param filePath Path to the file to check
     * @return true if the file is a valid PDF, false otherwise
     */
    public boolean isValidPDF(String filePath) {
        try (PDDocument document = Loader.loadPDF(new File(filePath))) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Reads a PDF from the resources directory.
     *
     * @param pdfName Name of the PDF file in the pdfs/ directory
     * @return String containing the text of the PDF
     * @throws IOException if there's an error reading the file
     */
    public static String readPdfFromResources(String pdfName) throws IOException {
        try {

            var resourceUrl = PDFImporter.class.getClassLoader().getResource("pdfs/" + pdfName);
            if (resourceUrl != null) {
                return readPdfText(resourceUrl.getFile());
            }


            Path resourcePath = Path.of("src", "main", "resources", "pdfs", pdfName);
            if (Files.exists(resourcePath)) {
                return readPdfText(resourcePath.toString());
            }


            Path currentPath = Path.of("pdfs", pdfName);
            if (Files.exists(currentPath)) {
                return readPdfText(currentPath.toString());
            }

            throw new IOException("PDF not found: " + pdfName + 
                "\npaths  tested:\n" +
                "- classpath:pdfs/" + pdfName + "\n" +
                "- " + resourcePath + "\n" +
                "- " + currentPath.toAbsolutePath());
            
        } catch (Exception e) {
            throw new IOException("Error reading PDF: " + e.getMessage(), e);
        }
    }
}
