class Flute extends WoodwindFamily {
    public Flute(double price) {
        super(price);
    }

    @Override
    public String MakeSound() { return "guiding a stream of air"; }

    @Override
    public String howToPlay() {
        return "by blowing into the flute";
    }

    @Override
    public String howToFix() {
        return "N/A: it cannot be fixed";
    }

    @Override
    public String getPitchType() {
        return "Fundamental pitch is middle C";
    }
}
