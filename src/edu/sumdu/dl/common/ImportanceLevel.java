package edu.sumdu.dl.common;

public class ImportanceLevel {

    private int importanceLevel;/*value from 0 to 100*/


    public ImportanceLevel(int level) {
        if (level > 100 || level < 0) {
            throw new WrongParameterException(level + " is not between 0 and 100");
        }
        importanceLevel = level;
    }
    public static final ImportanceLevel NONE_IMPORTANCE = new ImportanceLevel(0);
    public static final ImportanceLevel LOW_IMPORTANCE = new ImportanceLevel(30);
    public static final ImportanceLevel MEDIUM_IMPORTANCE = new ImportanceLevel(50);
    public static final ImportanceLevel HIGH_IMPORTANCE = new ImportanceLevel(90);
    public static final ImportanceLevel VERY_HIGH_IMPORTANCE = new ImportanceLevel(100);

    public int getLevel() {
        return importanceLevel;
    }

    @Override
    public String toString() {
        return "ImportanceLevel [importanceLevel=" + importanceLevel + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + importanceLevel;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ImportanceLevel)) {
            return false;
        }
        ImportanceLevel other = (ImportanceLevel) obj;
        if (importanceLevel != other.importanceLevel) {
            return false;
        }
        return true;
    }
}
