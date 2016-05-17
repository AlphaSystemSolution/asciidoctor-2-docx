package com.alphasystem.asciidoctor2docx;

import com.alphasystem.SystemException;
import com.alphasystem.asciidoc.model.AsciiDocumentInfo;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.ast.StructuredDocument;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import static java.nio.file.Files.exists;
import static org.apache.commons.io.FilenameUtils.getExtension;

/**
 * @author sali
 */
public final class DocumentBuilder {

    private final static Asciidoctor asciiDoctor = Asciidoctor.Factory.create();

    static {
        // initialize Application controller
        ApplicationController.getInstance();
    }

    public static DocumentContext buildDocument(Path srcPath) throws SystemException {
        final File srcFile = srcPath.toFile();
        if (!exists(srcPath)) {
            throw new NullPointerException("Source file does not exists.");
        }

        final Path fileNamePath = srcPath.getFileName();
        final String fileName = fileNamePath.toString();
        final String extension = getExtension(fileName);
        AsciiDocumentInfo documentInfo = new AsciiDocumentInfo();
        documentInfo.setSrcFile(srcFile);
        StructuredDocument structuredDocument = asciiDoctor.readDocumentStructure(srcFile, new HashMap<>());
        documentInfo.populateAttributes(structuredDocument.getHeader().getAttributes());
        return null;
    }
}
