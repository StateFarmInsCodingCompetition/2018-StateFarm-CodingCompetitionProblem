package com.codingcompetition.statefarm.utility;

import com.codingcompetition.statefarm.model.PointOfInterest;
import org.hamcrest.*;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;

public class PointOfInterestParserTest {

    @Test
    public void canInterpretLatitudeFromDataSource() throws Exception {

        PointOfInterestParser parser = new PointOfInterestParser();
        List<PointOfInterest> interpretedData = parser.parse("/small-metro.xml");
        MatcherAssert.assertThat(interpretedData.size(), equalTo(78848));
        final List<PointOfInterest> collect = interpretedData.stream().filter(aPointOfInterest -> aPointOfInterest.getLatitude() == null).collect(Collectors.toList());
        MatcherAssert.assertThat(collect.size(), equalTo(0));
    }

    @Test
    public void canInterpretLongitudeFromDataSource() throws Exception {

        PointOfInterestParser parser = new PointOfInterestParser();
        List<PointOfInterest> interpretedData = parser.parse("/small-metro.xml");
        MatcherAssert.assertThat(interpretedData.size(), equalTo(78848));
        final List<PointOfInterest> collect = interpretedData.stream().filter(aPointOfInterest -> aPointOfInterest.getLongitude() == null).collect(Collectors.toList());
        MatcherAssert.assertThat(collect.size(), equalTo(0));
    }

    @Test
    public void canInterpretTagsWithinNodes() throws Exception {
        PointOfInterestParser parser = new PointOfInterestParser();
        List<PointOfInterest> interpretedData = parser.parse("/small-metro.xml");
        final List<PointOfInterest> collect = interpretedData.stream().filter(aPointOfInterest -> aPointOfInterest.getDescriptors().size() != 0).collect(Collectors.toList());
        MatcherAssert.assertThat(collect.size(), not(equalTo(0)));

    }

}
