package mnta.sck;

import java.util.List;

public class Monster {
    public String monsterName;
    public Integer level;
    public Integer attack;
    public Integer defence;
    public Integer health;
    public Integer speed;
    public List<String> specialAbilities;

    public OriginOfMon originOfMon;

    public static class OriginOfMon {
        public String castle;
        public String building;
        public Boolean upgraded;
    }
}
