package com.alphasystem.asciidoc.model;

import org.asciidoctor.AttributesBuilder;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.Placement;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

import static com.alphasystem.util.AppUtil.USER_HOME_DIR;
import static com.alphasystem.util.AppUtil.toRelativePath;
import static org.apache.commons.lang3.StringUtils.*;
import static org.asciidoctor.SafeMode.SAFE;

/**
 * @author sali
 */
public class AsciiDocumentInfo {

    private static String getFailSafeString(Map<String, Object> attributes, String key) {
        return stripToNull((String) attributes.get(key));
    }

    private static boolean getFailSafeBoolean(Map<String, Object> attributes, String key) {
        return attributes.get(key) != null;
    }

    private final AttributesBuilder attributesBuilder;
    private final OptionsBuilder optionsBuilder;
    private String documentType;
    private String documentName;
    private String documentTitle;
    private String backend;
    private String stylesDir;
    private File customStyleSheetFile;
    private boolean linkCss;
    private String includeDir;
    private String imagesDir;
    private String iconsDir;
    private String icons;
    private String iconFontName;
    private String idPrefix;
    private String idSeparator;
    private boolean docInfo2;
    private String sourceLanguage;
    private String lastUpdateLabel;
    private boolean omitLastUpdatedTimeStamp;
    private boolean compact;
    private boolean experimental;
    private boolean toc;
    private String tocClass;
    private String tocPlacement;
    private String tocPosition;
    private String tocTitle;
    private String appendixCaption;
    private String cautionCaption;
    private String exampleCaption;
    private String figureCaption;
    private String importantCaption;
    private String noteCaption;
    private String tableCaption;
    private String tipCaption;
    private String untitledLabel;
    private String versionLabel;
    private String warningCaption;
    private boolean sectionNumbers;
    private boolean hideUriSchema;
    private File srcFile;
    private File previewFile;

    public AsciiDocumentInfo() {
        attributesBuilder = AttributesBuilder.attributes();
        optionsBuilder = OptionsBuilder.options().attributes(attributesBuilder);
        setDocumentType(null);
        setBackend(null);
        setStylesDir(null);
        setIcons(null);
        setLinkCss(true);
        setOmitLastUpdatedTimeStamp(true);
        setCompact(true);
        optionsBuilder.safe(SAFE);
    }

    /**
     * Copy Constructor
     *
     * @param src source object, cannot be null.
     * @throws IllegalArgumentException
     */
    public AsciiDocumentInfo(AsciiDocumentInfo src) throws IllegalArgumentException {
        this();
        if (src == null) {
            throw new IllegalArgumentException("source object cannot be null");
        }
        setDocumentType(src.getDocumentType());
        setDocumentName(src.getDocumentName());
        setDocumentTitle(src.getDocumentTitle());
        setDocInfo2(src.isDocInfo2());
        setIncludeDir(src.getIncludeDir());
        setImagesDir(src.getImagesDir());
        setIconsDir(src.getIconsDir());
        setIcons(src.getIcons());
        setIconFontName(src.getIconFontName());
        setLinkCss(src.isLinkCss());
        setSrcFile(src.getSrcFile());
        setPreviewFile(src.getPreviewFile());
        setStylesDir(src.getStylesDir());
        setCustomStyleSheetFile(src.getCustomStyleSheetFile());
        setSourceLanguage(src.getSourceLanguage());
        setLastUpdateLabel(src.getLastUpdateLabel());
        setOmitLastUpdatedTimeStamp(src.isOmitLastUpdatedTimeStamp());
        setCompact(src.isCompact());
        setIdSeparator(src.getIdSeparator());
        setIdPrefix(src.getIdPrefix());
        setSectionNumbers(src.isSectionNumbers());
        setExperimental(src.isExperimental());
        setToc(src.isToc());
        setTocTitle(src.getTocTitle());
        setTocPlacement(src.getTocPlacement());
        setTocPlacement(src.getTocPosition());
        setTocClass(src.getTocClass());
        setAppendixCaption(src.getAppendixCaption());
        setCautionCaption(src.getCautionCaption());
        setExampleCaption(src.getExampleCaption());
        setFigureCaption(src.getFigureCaption());
        setImportantCaption(src.getImportantCaption());
        setNoteCaption(src.getNoteCaption());
        setTableCaption(src.getTableCaption());
        setTipCaption(src.getTipCaption());
        setUntitledLabel(src.getUntitledLabel());
        setVersionLabel(src.getVersionLabel());
        setWarningCaption(src.getWarningCaption());
    }

    public OptionsBuilder getOptionsBuilder() {
        return optionsBuilder;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = isBlank(documentType) ? "article" : documentType;
        attributesBuilder.docType(this.documentType);
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getBackend() {
        return backend;
    }

    public void setBackend(String backend) {
        this.backend = (backend == null) ? Backend.HTML.getValue() : backend;
        optionsBuilder.backend(this.backend);
    }

    public String getStylesDir() {
        return stylesDir;
    }

    public void setStylesDir(String stylesDir) {
        this.stylesDir = isBlank(stylesDir) ? "css" : stylesDir;
        if (linkCss) {
            attributesBuilder.stylesDir(this.stylesDir);
        }
    }

    public File getCustomStyleSheetFile() {
        return customStyleSheetFile;
    }

    public void setCustomStyleSheetFile(File customStyleSheetFile) {
        this.customStyleSheetFile = customStyleSheetFile;
        if (this.customStyleSheetFile != null) {
            String styleSheetName = this.customStyleSheetFile.getName();
            attributesBuilder.styleSheetName(styleSheetName);
        }
    }

    public boolean isLinkCss() {
        return linkCss;
    }

    public void setLinkCss(boolean linkCss) {
        this.linkCss = linkCss;
        if (this.linkCss) {
            attributesBuilder.linkCss(isLinkCss()).stylesDir(stylesDir);
        }
    }

    public String getIncludeDir() {
        return includeDir;
    }

    public void setIncludeDir(String includeDir) {
        this.includeDir = includeDir;
    }

    public String getImagesDir() {
        return imagesDir;
    }

    public void setImagesDir(String imagesDir) {
        this.imagesDir = imagesDir;
        attributesBuilder.imagesDir(getImagesDir());
    }

    public String getIconsDir() {
        return iconsDir;
    }

    public void setIconsDir(String iconsDir) {
        this.iconsDir = iconsDir;
        attributesBuilder.iconsDir(getIconsDir());
    }

    public String getIcons() {
        return icons;
    }

    public void setIcons(String icons) {
        this.icons = icons;
        attributesBuilder.icons(getIcons());
    }

    public String getIconFontName() {
        return iconFontName;
    }

    public void setIconFontName(String iconFontName) {
        this.iconFontName = iconFontName;
        boolean localFontName = isNotBlank(iconFontName);
        attributesBuilder.iconFontName(this.iconFontName).iconFontRemote(!localFontName);
    }

    public String getIdPrefix() {
        return idPrefix;
    }

    public void setIdPrefix(String idPrefix) {
        this.idPrefix = idPrefix;
    }

    public String getIdSeparator() {
        return idSeparator;
    }

    public void setIdSeparator(String idSeparator) {
        this.idSeparator = idSeparator;
    }

    public boolean isDocInfo2() {
        return docInfo2;
    }

    public void setDocInfo2(boolean docInfo2) {
        this.docInfo2 = docInfo2;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
        attributesBuilder.sourceLanguage(this.sourceLanguage);
    }

    public String getLastUpdateLabel() {
        return lastUpdateLabel;
    }

    public void setLastUpdateLabel(String lastUpdateLabel) {
        this.lastUpdateLabel = lastUpdateLabel;
    }

    public boolean isOmitLastUpdatedTimeStamp() {
        return omitLastUpdatedTimeStamp;
    }

    public void setOmitLastUpdatedTimeStamp(boolean omitLastUpdatedTimeStamp) {
        this.omitLastUpdatedTimeStamp = omitLastUpdatedTimeStamp;
    }

    public boolean isCompact() {
        return compact;
    }

    public void setCompact(boolean compact) {
        this.compact = compact;
        optionsBuilder.compact(this.compact);
    }

    public boolean isExperimental() {
        return experimental;
    }

    public void setExperimental(boolean experimental) {
        this.experimental = experimental;
        attributesBuilder.experimental(this.experimental);
    }

    public boolean isSectionNumbers() {
        return sectionNumbers;
    }

    public void setSectionNumbers(boolean sectionNumbers) {
        this.sectionNumbers = sectionNumbers;
        attributesBuilder.sectionNumbers(isSectionNumbers());
    }

    public boolean isToc() {
        return toc;
    }

    public void setToc(boolean toc) {
        this.toc = toc;
        attributesBuilder.tableOfContents(isToc());
    }

    public String getTocClass() {
        return tocClass;
    }

    public void setTocClass(String tocClass) {
        this.tocClass = tocClass;
    }

    public String getTocPlacement() {
        return tocPlacement;
    }

    public void setTocPlacement(String tocPlacement) {
        this.tocPlacement = tocPlacement;
        attributesBuilder.tableOfContents((isBlank(tocPlacement) || tocPlacement.equals("auto")) ? Placement.LEFT :
                Placement.valueOf(tocPlacement.toUpperCase()));
    }

    public String getTocPosition() {
        return tocPosition;
    }

    public void setTocPosition(String tocPosition) {
        this.tocPosition = tocPosition;
    }

    public String getTocTitle() {
        return tocTitle;
    }

    public void setTocTitle(String tocTitle) {
        this.tocTitle = tocTitle;
    }

    public String getUntitledLabel() {
        return untitledLabel;
    }

    public void setUntitledLabel(String untitledLabel) {
        this.untitledLabel = untitledLabel;
        attributesBuilder.untitledLabel(getUntitledLabel());
    }

    public String getAppendixCaption() {
        return appendixCaption;
    }

    public void setAppendixCaption(String appendixCaption) {
        this.appendixCaption = appendixCaption;
    }

    public String getCautionCaption() {
        return cautionCaption;
    }

    public void setCautionCaption(String cautionCaption) {
        this.cautionCaption = cautionCaption;
    }

    public String getExampleCaption() {
        return exampleCaption;
    }

    public void setExampleCaption(String exampleCaption) {
        this.exampleCaption = exampleCaption;
    }

    public String getFigureCaption() {
        return figureCaption;
    }

    public void setFigureCaption(String figureCaption) {
        this.figureCaption = figureCaption;
    }

    public String getImportantCaption() {
        return importantCaption;
    }

    public void setImportantCaption(String importantCaption) {
        this.importantCaption = importantCaption;
    }

    public String getNoteCaption() {
        return noteCaption;
    }

    public void setNoteCaption(String noteCaption) {
        this.noteCaption = noteCaption;
    }

    public String getTableCaption() {
        return tableCaption;
    }

    public void setTableCaption(String tableCaption) {
        this.tableCaption = tableCaption;
    }

    public String getTipCaption() {
        return tipCaption;
    }

    public void setTipCaption(String tipCaption) {
        this.tipCaption = tipCaption;
    }

    public String getVersionLabel() {
        return versionLabel;
    }

    public void setVersionLabel(String versionLabel) {
        this.versionLabel = versionLabel;
    }

    public String getWarningCaption() {
        return warningCaption;
    }

    public void setWarningCaption(String warningCaption) {
        this.warningCaption = warningCaption;
    }

    public boolean isHideUriSchema() {
        return hideUriSchema;
    }

    public void setHideUriSchema(boolean hideUriSchema) {
        this.hideUriSchema = hideUriSchema;
    }

    public File getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(File srcFile) {
        this.srcFile = (srcFile == null) ? USER_HOME_DIR : srcFile;
        optionsBuilder.baseDir(this.srcFile.getParentFile());
        setPreview(this.srcFile, previewFile);
    }

    public File getPreviewFile() {
        return previewFile;
    }

    public void setPreviewFile(File previewFile) {
        this.previewFile = previewFile;
        setPreview(srcFile, this.previewFile);
    }

    public void populateAttributes(Map<String, Object> attributes) {
        // attributes.entrySet().forEach(entry -> System.out.println(entry.getKey() + " : " + entry.getValue()));
        String s;
        s = getFailSafeString(attributes, "doctype");
        if (s != null) {
            setDocumentType(s);
        }
        s = getFailSafeString(attributes, "docname");
        if (s != null) {
            setDocumentName(s);
        }
        s = getFailSafeString(attributes, "doctitle");
        if (s != null) {
            setDocumentTitle(s);
        }
        s = getFailSafeString(attributes, "stylesdir");
        if (s != null) {
            setStylesDir(s);
        }
        s = getFailSafeString(attributes, "stylesheet");
        if (s != null) {
            File parent = new File(getSrcFile().getAbsoluteFile(), getStylesDir());
            setCustomStyleSheetFile(new File(parent, s));
        }
        s = getFailSafeString(attributes, "includedir");
        if (s != null) {
            setIncludeDir(s);
        }
        // setValue(this, attributes, "linkcss", "setLinkCss", Boolean.class);
        setLinkCss(getFailSafeBoolean(attributes, "linkcss"));
        setCompact(getFailSafeBoolean(attributes, "compact"));
        setExperimental(getFailSafeBoolean(attributes, "experimental"));
        setSectionNumbers(getFailSafeBoolean(attributes, "sectnums"));
        setHideUriSchema(getFailSafeBoolean(attributes, "hide-uri-scheme"));
        s = getFailSafeString(attributes, "iconsdir");
        if (s != null) {
            setIconsDir(s);
        }
        s = getFailSafeString(attributes, "includedir");
        if (s != null) {
            setIncludeDir(s);
        }
        s = getFailSafeString(attributes, "imagesdir");
        if (s != null) {
            setImagesDir(s);
        }
        s = getFailSafeString(attributes, "iconfont-name");
        if (s != null) {
            setIconFontName(s);
        }
        s = getFailSafeString(attributes, "idprefix");
        if (s != null) {
            setIdPrefix(s);
        }
        s = getFailSafeString(attributes, "idseparator");
        if (s != null) {
            setIdSeparator(s);
        }
        setToc(getFailSafeBoolean(attributes, "toc"));
        s = getFailSafeString(attributes, "toc-class");
        if (s != null) {
            setTocClass(s);
        }
        s = getFailSafeString(attributes, "toc-placement");
        if (s != null) {
            setTocPlacement(s);
        }
        s = getFailSafeString(attributes, "toc-position");
        if (s != null) {
            setTocPosition(s);
        }
        s = getFailSafeString(attributes, "toc-title");
        if (s != null) {
            setTocTitle(s);
        }
        s = getFailSafeString(attributes, "untitled-label");
        if (s != null) {
            setUntitledLabel(s);
        }
        s = getFailSafeString(attributes, "last-update-label");
        if (s != null) {
            setLastUpdateLabel(s);
        }
        s = getFailSafeString(attributes, "appendix-caption");
        if (s != null) {
            setAppendixCaption(s);
        }
        s = getFailSafeString(attributes, "caution-caption");
        if (s != null) {
            setCautionCaption(s);
        }
        s = getFailSafeString(attributes, "example-caption");
        if (s != null) {
            setExampleCaption(s);
        }
        s = getFailSafeString(attributes, "figure-caption");
        if (s != null) {
            setFigureCaption(s);
        }
        s = getFailSafeString(attributes, "important-caption");
        if (s != null) {
            setImportantCaption(s);
        }
        s = getFailSafeString(attributes, "note-caption");
        if (s != null) {
            setNoteCaption(s);
        }
        s = getFailSafeString(attributes, "table-caption");
        if (s != null) {
            setTableCaption(s);
        }
        s = getFailSafeString(attributes, "tip-caption");
        if (s != null) {
            setTipCaption(s);
        }
        s = getFailSafeString(attributes, "version-label");
        if (s != null) {
            setVersionLabel(s);
        }
        s = getFailSafeString(attributes, "warning-caption");
        if (s != null) {
            setWarningCaption(s);
        }
    }

    private void setPreview(File srcFile, File previewFile) {
        if (srcFile == null || previewFile == null) {
            return;
        }
        File baseDir = srcFile.getParentFile();
        Path relativePath = toRelativePath(baseDir, previewFile.getParentFile());
        File destFolder = new File(baseDir, relativePath.toFile().getPath());
        File destFile = new File(destFolder, previewFile.getName());
        optionsBuilder.toDir(destFolder).toFile(destFile).inPlace(baseDir.equals(destFolder));
    }
}
