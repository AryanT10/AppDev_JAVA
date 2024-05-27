interface IFixable {
    String howToFix();
}

interface IPlayable {
    String howToPlay();
}

abstract class MusicalInstrument implements IPlayable, IFixable, Comparable<MusicalInstrument> {
    private double price;

    public MusicalInstrument(double price) {
        this.price = price;
    }

    public abstract String MakeSound();

    public double getPrice() {
        return price;
    }

    public abstract String getPitchType();

    @Override
    public int compareTo(MusicalInstrument other) {
        return Double.compare(this.getPrice(), other.getPrice());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
