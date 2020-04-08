package model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** testen van de getters van id en cursusnaam methoden van Course
 */

class CourseTest extends TestCase {
    private final Course Course_Test = new Course(2, "Testen", 15);

    @Test
    void testGetId() throws Exception {
        assertEquals(Course_Test.getId(), 2);
    }

    @Test
    void testGetCoursename() throws Exception{
        assertEquals(Course_Test.getCoursename(),"Testen");
    }


  }