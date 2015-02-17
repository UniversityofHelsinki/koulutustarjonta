package fi.helsinki.koulutustarjonta.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResult {

    public enum State {OK, ERROR}

    // DB generated id
    private long id;
    private Date started;
    private State state;
    private String errors;
}
