package fi.helsinki.koulutustarjonta.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Hannu Lyytikainen
 */
@Getter
@Setter
public class Exam {

    private String oid;
    private String lang;
    private String type;
    private String description;
    private List<ExamEvent> events;
}
