package ikpmd.westgeestoonk.course_manager.Database;


import ikpmd.westgeestoonk.course_manager.Enums.Toetsing;

public class DatabaseInfo {

    public class CourseTables {
        public static final String COURSE = "Courses";   // NAAM VAN JE TABEL
    }

    public class CourseColumn {
        public static final String NAAM = "Naam";
        public static final String EC = "EC";
        public static final String VAKCODE = "Vakcode";
        public static final String TOETSING = "Toetsing";
        public static final String PERIODE = "Periode";
        public static final String TOETSMOMENT = "Toetsmoment";
        public static final String CIJFER = "Cijfer";
        public static final String JAAR = "Jaar";
    }

}
