package com.mills.quarters.controllers;

import com.google.common.collect.ImmutableList;
import com.mills.quarters.IntegrationTest;
import com.mills.quarters.models.Quarter;
import org.junit.Ignore;
import org.junit.Test;

import static com.mills.quarters.builders.QuarterBuilder.quarterBuilder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan on 10/04/16.
 */
public class BellBoardControllerTest extends IntegrationTest {

    @Test
    @Ignore
    public void testListAllQuarters()
        throws Exception
    {
        //TODO: Write some tests
    }
}
