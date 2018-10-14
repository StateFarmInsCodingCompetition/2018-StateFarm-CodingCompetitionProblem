package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PointOfInterestParser {


    // Stacks for storing the elements and objects.
    private Stack<String> elements = new Stack<String>();
    private Stack<PointOfInterest> objects = new Stack<PointOfInterest>();


    public List<PointOfInterest> parse(String fileName) throws IOException {
        List<PointOfInterest> pointOfInterests = new ArrayList<>();

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        InputStream in;
        try {
            fileName = fileName.startsWith("/") ? fileName.substring(1) : fileName;
            in = new FileInputStream(new File(getClass().getClassLoader().getResource(fileName).getFile()).getAbsolutePath());
        } catch (IOException e) {
            System.err.println("file for reading couldn't be found");
            return pointOfInterests;
        }
        XMLStreamReader streamReader = null;
        try {
            streamReader = inputFactory.createXMLStreamReader(in);
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return null;
        }
        try {
            if (streamReader != null) {
                // seek to first 'node' tag
                while (streamReader.hasNext() &&
                        !(streamReader.isStartElement() && streamReader.getLocalName().equals("node")))
                    streamReader.next();

                while(streamReader.hasNext()) {
                    if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT &&
                           streamReader.getLocalName().equals("node")) {
//                    String id = streamReader.getAttributeValue(0);
                        String lat = streamReader.getAttributeValue(1);
                        String lon = streamReader.getAttributeValue(2);
//                    String version = streamReader.getAttributeValue(3);
//                    String tstamp = streamReader.getAttributeValue(4);
//                    String changeSet = streamReader.getAttributeValue(5);
//                    String uid = streamReader.getAttributeValue(6);
//                    String user = streamReader.getAttributeValue(7);

                        PointOfInterest currentPoi = new PointOfInterest(lat, lon);

                        streamReader.nextTag(); // advance to 'tag' or 'node'
                        while (streamReader.hasNext()) {
                            if (streamReader.getEventType() == XMLStreamReader.START_ELEMENT) {
                                if (streamReader.getLocalName().equals("tag")){
                                    currentPoi.getDescriptors().put(
                                            streamReader.getAttributeValue(0),
                                            streamReader.getAttributeValue(1));
                                } else if (streamReader.getLocalName().equals("node")) {
                                    break; // move on to the next node, we're done parsing this one
                                }
                            }
                            streamReader.next();
                        }
                        pointOfInterests.add(currentPoi);
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return pointOfInterests;
    }

    /*
     * reads a file into a byte array
     *
     * from https://stackoverflow.com/questions/11223868/how-to-create-bytearrayinputstream-from-a-file-in-java
     */
    private static byte[] readContentIntoByteArray(File file)
    {
        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bFile;
    }

}
