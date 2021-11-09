package io.honeymon.book;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.asciidoctor.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://github.com/asciidoctor/asciidoctorj">AsciidoctorJ: AsciidoctorJ: Java bindings for Asciidoctor</a>
 */
public class BookGenerator {

    public static final String FILE_BOOK_ADOC = "book.adoc";
    public static final String DIR_ROOT = "src/docs";
    public static final String DIR_DOCS_IMAGES = "src/docs/images";
    public static final String DIR_PUBLICATION = "publication";
    public static final String DIR_PUBLICATION_IMAGES = "publication/images";
    public static final List<String> IMG_FILE_EXTENSIONS = Arrays.asList("png", "jpg");


    public static void main(String[] args) {
        try {
//            cleanPublicationDir();
            makePublicationDir();
            copyImageFiles(new File(DIR_ROOT));

            File bookFile = Paths.get(DIR_ROOT, FILE_BOOK_ADOC).toFile();
            Asciidoctor asciidoctor = Asciidoctor.Factory.create();

            /**
             * Convert to HTML
             */
            Options htmlOptions = OptionsBuilder.options()
                    .backend("html5")
                    .safe(SafeMode.SAFE)
                    .toDir(new File(DIR_PUBLICATION))
                    .get();

            System.out.println("Rendering HTML.");
            asciidoctor.convertFile(bookFile, htmlOptions);

            /**
             * Convert to PDF
             */
            Map<String, Object> pdfAttrs = new HashMap<>();
            pdfAttrs.put("pdf-theme", "src/for-pdf/themes/default-pdf-theme.yml");
            pdfAttrs.put("pdf-fontsdir", "src/for-pdf/fonts");
            Options pdfOptions = OptionsBuilder.options()
                    .backend("pdf")
                    .safe(SafeMode.SAFE)
                    .attributes(pdfAttrs)
                    .toDir(new File(DIR_PUBLICATION))
                    .get();

            System.out.println("Rendering PDF.");
            asciidoctor.convertFile(bookFile, pdfOptions);

            /**
             * Convert to docbook
             */
            Options docbookOption = OptionsBuilder.options()
                    .backend("docbook5")
                    .safe(SafeMode.SAFE)
                    .toDir(new File(DIR_PUBLICATION))
                    .get();

            System.out.println("Rendering DOCBOK.");
            asciidoctor.convertFile(bookFile, docbookOption);

            /**
             * Convert to epub3
             * @see <a href="https://github.com/asciidoctor/asciidoctorj#converting-to-epub3">Converting to EPUB3</a>
             */
            System.out.println("Rendering EPUB.");
            Options epub3Option = OptionsBuilder.options()
                    .backend("epub3")
                    .safe(SafeMode.SAFE)
                    .inPlace(true)
                    .toDir(new File(DIR_PUBLICATION)) // epub 변환시 대상 디렉터리 초기화함
                    .get();

            asciidoctor.convertFile(bookFile, epub3Option);

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyImageFiles(File file) throws IOException {
        if(file.getAbsolutePath().contains(DIR_DOCS_IMAGES)) {
            return; //exclude copied image files
        }

        if(file.isDirectory()){
            for(File el: file.listFiles()) {
                copyImageFiles(el);
            }
        } else {
            copyImageFile(file);
        }
    }

    private static void copyImageFile(File el) throws IOException {
        if (IMG_FILE_EXTENSIONS.contains(FilenameUtils.getExtension(el.getName()))) {
            //PDF 용
            FileUtils.copyFile(el, Paths.get(DIR_DOCS_IMAGES, el.getName()).toFile());

            //HTML 용
            FileUtils.copyFile(el, Paths.get(DIR_PUBLICATION_IMAGES, el.getName()).toFile());
        }
    }


    private static void makePublicationDir() {
        try {
            FileUtils.forceMkdir(new File(DIR_DOCS_IMAGES));
            FileUtils.forceMkdir(new File(DIR_PUBLICATION));
            FileUtils.forceMkdir(new File(DIR_PUBLICATION_IMAGES));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
