package mnta.sck;

import java.util.List;

public class Human {
    public String name;
    public Integer age;
    public Boolean isLearning;
    public List<String> hobbies;
    public Id id;

    public static class Id {
        public Integer number;
        public String issueDate;
    }

}
