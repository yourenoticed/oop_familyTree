import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FamilyTree implements Serializable {
    private List<Human> familyTree;
    
    public FamilyTree(List<Human> familyTree) {
        this.familyTree = familyTree;
    }
    
    public FamilyTree() {
        this(new ArrayList<Human>());
    }
    
    public List<Human> getFamilyTree() {
        return this.familyTree;
    }

    private void addToParents(Human person) {
        if (person.getFather() != null) {
            person.getFather().addChild(person);
        }
        if (person.getMother() != null) {
            person.getMother().addChild(person);
        }
    }

    private void addParent(Human parent) {
        for (Human child : parent.getChildren()) {
            if (parent.getSex().equals(Sex.Male)) child.setFather(parent);
            else child.setMother(parent);
        }
    }

    public boolean addPerson(Human person) {
        if (!this.familyTree.contains(person)) {
            this.familyTree.add(person);
            this.addToParents(person);
            this.addParent(person);
            return true;
        }
        return false;
    }
    
    public boolean removePerson(Human person) {
        if (this.familyTree.contains(person)) {
            this.familyTree.remove(person);
            return true;
        }
        return false;
    }
    
    public boolean removePerson(long id) {
        return this.familyTree.remove(this.getById(id));
    }

    public Human getById(long id) {
        if (this.checkId(id)) {
            for (Human person : familyTree) {
                if (person.getId() == id) return person;
            }
        }
        return null;
    }

    private boolean checkId(long id) {
        if (id > 0) return true;
        return false;
    }

    public List<Human> getSiblings(Human person) {
        List<Human> siblings = new ArrayList<>();
        if (person.getMother() != null) {
            for (Human child : person.getMother().getChildren()) {
                if (!siblings.contains(child) && !child.equals(person)) siblings.add(child);
            }
        }
        if (person.getFather() != null) {
            for (Human child : person.getFather().getChildren()) {
                if (!siblings.contains(child) && !child.equals(person)) siblings.add(child);
            }
        }
        return siblings;
    }

    public List<Human> getSiblings(long personId) {
        return this.getSiblings(this.getById(personId));
    }

    public double medianAge() {
        double ageSum = 0;
        int count = 0;
        for (Human person : this.familyTree) {
            if (person.getDeathDate() == null) {
                ageSum += person.getAge();
                count++;
            }
        }

        return ageSum / count;
    }

    public double medianLifeSpan() {
        double lifeSpanSum = 0;
        int count = 0;
        for (Human person : this.familyTree) {
            if (person.getDeathDate() != null) {
                lifeSpanSum += person.getAge();
                count++;
            }
        }

        return lifeSpanSum / count;
    }

    public double medianChildrenAmount() {
        double sumChildrenAmount = 0;
        for (Human person : this.familyTree) {
            sumChildrenAmount += person.getChildrenAmount();
        }

        return sumChildrenAmount / this.familyTree.size();
    }

    @Override
    public String toString() {
        StringBuilder familyTreeBuilder = new StringBuilder();
        for (Human person : this.familyTree) {
            familyTreeBuilder.append(person.getFullName()).append("\n");
        }
        return familyTreeBuilder.toString();
    }
}