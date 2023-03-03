package com.zhq.devtools.adapter;

import static com.just.agentweb.AgentWebUIControllerImplBase.build;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.pdf.PrintedPdfDocument;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author ZhangHuiQiang
 * @Date 2023/3/1 15:53
 * Description
 */
public class MyPrintDocumentAdapter extends PrintDocumentAdapter {
    private Context context;
    private String documentName;
    private int printPageCount;
    private PrintedPdfDocument printedPdfDocument;

    public MyPrintDocumentAdapter(Context context, String documentName, int printPageCount) {
        this.context = context;
        this.documentName=documentName;
        this.printPageCount=printPageCount;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
        printedPdfDocument = new PrintedPdfDocument(context, newAttributes);
        if (cancellationSignal.isCanceled()) {
            callback.onLayoutCancelled();
            return;
        }
        int pages = computePageCount(newAttributes);
        if (pages > 0) {
            // Return print information to print framework
            PrintDocumentInfo info = new PrintDocumentInfo.Builder(documentName)
                    .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                    .setPageCount(pages).build();
            // Content layout reflow is complete
            callback.onLayoutFinished(info, true);
        } else {
            // Otherwise report an error to the print framework
            callback.onLayoutFailed("Page count calculation failed.");
        }

    }

    @Override
    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
// Iterate over each page of the document,
// check if it's in the output range.
        for (int i = 0; i < printPageCount; i++) {
// Check to see if this page is in the output range.
//            if (containsPage(pageRanges, i)) {
// If so, add it to writtenPagesArray. writtenPagesArray.size()
// is used to compute the next output page index.
//                writtenPagesArray.append(writtenPagesArray.size(), i);
                PdfDocument.Page page = printedPdfDocument.startPage(i);
// check for cancellation
                if (cancellationSignal.isCanceled()) {
                    callback.onWriteCancelled();
                    printedPdfDocument.close();
                    printedPdfDocument = null;
                    return;
                }
// Draw page content for printing
//                drawPage(page);
// Rendering is complete, so page can be finalized.
                printedPdfDocument.finishPage(page);
//            }
        }
// Write PDF document to file
        try {
            printedPdfDocument.writeTo(new FileOutputStream(
                    destination.getFileDescriptor()));
        } catch (IOException e) {
            callback.onWriteFailed(e.toString());
            return;
        } finally {
            printedPdfDocument.close();
            printedPdfDocument = null;
        }
//        PageRange[] writtenPages = computeWrittenPages();
// Signal the print framework the document is complete
//        callback.onWriteFinished(writtenPages);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    private int computePageCount(PrintAttributes printAttributes) {
        int itemsPerPage = 4; // default item count for portrait mode
        PrintAttributes.MediaSize pageSize = printAttributes.getMediaSize();
        if (!pageSize.isPortrait()) {
            // Six items per page in landscape orientation
            itemsPerPage = 6;
        }
        // Determine number of print items
        int printItemCount = printPageCount;
        return (int) Math.ceil(printItemCount / itemsPerPage);
    }

    private void drawPage(PdfDocument.Page page) {
        Canvas canvas = page.getCanvas();
// units are in points (1/72 of an inch)
        int titleBaseLine = 72;
        int leftMargin = 54;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(36);
        canvas.drawText("Test Title", leftMargin, titleBaseLine, paint);
        paint.setTextSize(11);
        canvas.drawText("Test paragraph", leftMargin, titleBaseLine + 25, paint);
        paint.setColor(Color.BLUE);
        canvas.drawRect(100, 100, 172, 172, paint);
    }
}
