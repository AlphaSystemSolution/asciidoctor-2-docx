var adapter = Java.type("com.alphasystem.openxml.builder.wml.WmlAdapter");
var factory = Java.type("com.alphasystem.openxml.builder.wml.WmlBuilderFactory");

var handleBold = function (rprBuilder) {
    return rprBuilder.withB(true).withBCs(true);
};

var handleItalic = function (rprBuilder) {
    return rprBuilder.withI(true).withICs(true);
};

var handleUnderline = function (rprBuilder) {
    return rprBuilder.withU(factory.getUBuilder().withVal(org.docx4j.wml.UnderlineEnumeration.SINGLE).getObject());
};

var handleStrikeThrough = function (rprBuilder) {
    return rprBuilder.withStrike(true);
};

var handleLiteral = function (rprBuilder) {
    return rprBuilder.withRFonts(factory.getRFontsBuilder().withAscii("Courier New").withHAnsi("Courier New").withCs("Courier New").getObject());
};

var handleSubscript = function (rprBuilder) {
    return rprBuilder.withVertAlign(org.docx4j.wml.STVerticalAlignRun.SUBSCRIPT);
};

var handleSuperscript = function (rprBuilder) {
    return rprBuilder.withVertAlign(org.docx4j.wml.STVerticalAlignRun.SUPERSCRIPT);
};

var handleHyperlink = function (rprBuilder) {
    return rprBuilder.withRStyle(adapter.getRStyle("Hyperlink"));
};

var handleColor = function (rprBuilder, color) {
    return rprBuilder.withColor(adapter.getColor(color));
};

var handleStyle = function (rprBuilder, styleName) {
    return rprBuilder.withRStyle(adapter.getRStyle(styleName));
};

var handleExample = function () {
    var border = adapter.getBorder(org.docx4j.wml.STBorder.SINGLE, 4, 0, "E0E0DC");
    var tblBorders = factory.getTblBordersBuilder().withTop(border).withLeft(border)
        .withBottom(border).withRight(border).withInsideH(border).withInsideV(border).getObject();

    var tblPr = factory.getTblPrBuilder().withTblBorders(tblBorders).getObject();

    var shade = factory.getCTShdBuilder().withVal(org.docx4j.wml.STShd.CLEAR).withColor("auto").withFill("FFFEF7").getObject();
    var tcPr = factory.getTcPrBuilder().withShd(shade).getObject();

    return new com.alphasystem.openxml.builder.wml.TableAdapter(1).startTable(tblPr).startRow()
        .addColumn(0, null, tcPr, null).endRow().getTable();
};

var handleInformalExample = function () {
    return handleExample();
};

var handleSideBar = function () {
    var border = adapter.getBorder(org.docx4j.wml.STBorder.SINGLE, 4, 0, "E0E0DC");
    var tblBorders = factory.getTblBordersBuilder().withTop(border).withLeft(border)
        .withBottom(border).withRight(border).withInsideH(border).withInsideV(border).getObject();

    var tblPr = factory.getTblPrBuilder().withTblBorders(tblBorders).getObject();

    var shade = factory.getCTShdBuilder().withVal(org.docx4j.wml.STShd.CLEAR).withColor("auto").withFill("F8F8F7").getObject();
    var tcPr = factory.getTcPrBuilder().withShd(shade).getObject();

    return new com.alphasystem.openxml.builder.wml.TableAdapter(1).startTable(tblPr).startRow().addColumn(0, null, tcPr, null).endRow().getTable();
};

var handleCaution = function (captionText) {
    return handleAdmonition(15, captionText);
};

var handleImportant = function (captionText) {
    return handleAdmonition(20, captionText);
};

var handleNote = function (captionText) {
    return handleAdmonition(15, captionText);
};

var handleTip = function (captionText) {
    return handleAdmonition(15, captionText);
};

var handleWarning = function (captionText) {
    return handleAdmonition(15, captionText);
};

var handleAdmonition = function (widthOfCaptionColumn, captionText) {
    var widthOfContentColumn = 100.0 - widthOfCaptionColumn;
    return new com.alphasystem.openxml.builder.wml.TableAdapter(widthOfCaptionColumn, widthOfContentColumn)
        .startTable("AdmonitionTable").startRow()
        .addColumn(0, adapter.getParagraph(captionText))
        .addColumn(1).endRow().getTable();
};