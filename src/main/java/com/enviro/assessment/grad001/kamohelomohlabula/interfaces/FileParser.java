package com.enviro.assessment.grad001.kamohelomohlabula.interfaces;

import java.io.File;
import java.net.URI;

public interface FileParser {
    void parseCSV(File csvFile);
    File convertCSVDataToImage(String base64ImageData, String imageFormat, String fileName);
    URI createImageLink(File fileImage);
}
