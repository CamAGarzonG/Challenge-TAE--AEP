package testExampleAPI.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {
    private String name;
    private String url;
    private List<AbilityInfo> abilities;

    // Getters and setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getUrl() {return url;}
    public void setUrl(String url) {this.url = url;}

    public List<AbilityInfo> getAbilities() {return abilities;}
    public void setAbilities(List<AbilityInfo> abilities) {this.abilities = abilities;}

    public static class AbilityInfo {
        private Ability ability;
        private boolean is_hidden;
        private int slot;

        public Ability getAbility() { return ability; }
        public void setAbility(Ability ability) { this.ability = ability; }

        public boolean isIs_hidden() { return is_hidden; }
        public void setIs_hidden(boolean is_hidden) { this.is_hidden = is_hidden; }

        public int getSlot() { return slot; }
        public void setSlot(int slot) { this.slot = slot; }
    }
}