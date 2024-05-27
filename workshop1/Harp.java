class Harp extends StringFamily {
    public Harp(double price) {
        super(price);
    }

    @Override
    public String MakeSound() { return "vibrating strings"; }

    @Override
    public String howToPlay() {
        return "with the thumb and first three fingers";
    }

    @Override
    public String howToFix() {
        return "replace the strings";
    }

    @Override
    public String getPitchType() {
        return "Has seven levels of pitch";
    }
}
