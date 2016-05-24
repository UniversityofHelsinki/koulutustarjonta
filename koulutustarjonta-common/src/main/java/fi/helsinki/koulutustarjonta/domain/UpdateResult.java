package fi.helsinki.koulutustarjonta.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateResult {

    public enum State {OK, ERROR}

    // DB generated id
    private long id;
    private Date started;
    private State state;
    private String errors;
}
