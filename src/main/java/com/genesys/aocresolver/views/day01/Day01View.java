package com.genesys.aocresolver.views.day01;

import com.genesys.aocresolver.logic.Day01;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;
import io.vavr.collection.List;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@PageTitle("Advent of Code Day 1")
@Route("day01")
@Menu(order = 1, icon = LineAwesomeIconUrl.FILE)
public class Day01View extends VerticalLayout{
    public Day01View() {
        // MemoryBuffer is the receiver where the uploaded file will be stored
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);

        Span uploadInfo = new Span();

        upload.addSucceededListener(event -> {
            // Retrieve file information
            String fileName = event.getFileName();
            InputStream fileData = buffer.getInputStream();

            uploadInfo.setText("Uploaded file: " + fileName);

            // TODO: Add logic to process the InputStream (save file, parse content, etc.)
            try {
                // Convert InputStream to String
                String fileContent = new BufferedReader(new InputStreamReader(fileData))
                        .lines()
                        .collect(Collectors.joining("\n"));

                // Process the file content
                List<List<Integer>> processedData = Day01.processStringToLists(fileContent);
                Number result = Day01.difference(processedData);

                // Display results
                uploadInfo.setId("Answer");
                uploadInfo.setText("Total Absolute Difference: " + result);
            } catch (Exception e) {
                uploadInfo.setId("Error");
                uploadInfo.setText("Error processing file: " + e.getMessage());
                e.printStackTrace();
            }
        });

        upload.addFileRejectedListener(event -> {
            uploadInfo.setText("File upload rejected: " + event.getErrorMessage());
        });

        upload.addFailedListener(event -> {
            uploadInfo.setText("File upload failed: " + event.getReason().getMessage());
        });

        Button clearButton = new Button("Clear", click -> {
            upload.clearFileList();
            uploadInfo.setText("");
        });

        add(new H1("Advent of Code Day 01"), new H2("Day 1: Historian Hysteria"), upload, uploadInfo, clearButton);
    }
}