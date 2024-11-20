package org.zephyrsoft.teachernotes.extractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.zephyrsoft.teachernotes.extractor.model.Group;
import org.zephyrsoft.teachernotes.extractor.model.Pupil;
import org.zephyrsoft.teachernotes.extractor.model.Remark;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEvent;
import com.itextpdf.kernel.pdf.event.AbstractPdfDocumentEventHandler;
import com.itextpdf.kernel.pdf.event.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFontFactory.EmbeddingStrategy;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.AreaBreakType;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Exporter {
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void write(final List<Group> groups, final File pdfTargetFile) {
        try (OutputStream out = new FileOutputStream(pdfTargetFile)) {
            PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN, PdfEncodings.WINANSI, EmbeddingStrategy.PREFER_EMBEDDED);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD, PdfEncodings.WINANSI, EmbeddingStrategy.PREFER_EMBEDDED);

            PdfDocument pdf = new PdfDocument(new PdfWriter(out));
            pdf.addEventHandler(PdfDocumentEvent.START_PAGE, new AbstractPdfDocumentEventHandler() {
                @Override
                public void onAcceptedEvent(AbstractPdfDocumentEvent event) {
                    PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
                    PdfPage page = docEvent.getPage();
                    int pageNum = docEvent.getDocument().getPageNumber(page);
                    PdfCanvas canvas = new PdfCanvas(page);
                    canvas.beginText();
                    canvas.setFontAndSize(normalFont, 10);
                    Rectangle pageSize = page.getPageSizeWithRotation();
                    canvas.moveText(pageSize.getWidth() / 2 - 10, 15);
                    canvas.showText(String.format("- %d -", pageNum));
                    canvas.endText();
                    canvas.stroke();
                    canvas.release();
                }
            });
            Document doc = new Document(pdf);
            doc.setMargins(50, 50, 50, 50);

            Color black = DeviceRgb.BLACK;
            Color red = DeviceRgb.RED;
            Color green = DeviceRgb.GREEN;

            boolean isFirst = true;
            for (Group group : groups) {
                if (!isFirst) {
                    doc.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
                addParagraph(group.getName(), boldFont, 20, black, 0, doc);
                addParagraph("", normalFont, 20, black, 0, doc);

                for (Pupil p : group.getPupils()) {
                    addParagraph(p.getName(), normalFont, 16, black, 20, doc);

                    for (Remark r : p.getRemarks()) {
                        Color color = switch (r.getType()) {
                            case POSITIVE -> green;
                            case NEGATIVE -> red;
                            case NEUTRAL -> black;
                        };
                        addParagraph(DATE.format(r.getTimestamp()) + ": " + r.getText(), normalFont, 12, color, 40, doc);
                    }
                }

                isFirst = false;
            }

            doc.close();
        } catch (Exception e) {
            throw new RuntimeException("error while creating PDF document", e);
        }
    }

    private static void addParagraph(String text, PdfFont font, int size, final Color color, int margin, Document doc) {
        Paragraph p = new Paragraph();
        p.setFont(font);
        p.setFontSize(size);
        p.setFontColor(color);
        p.setPaddingLeft(margin);
        p.add(text);
        doc.add(p);
    }

}
