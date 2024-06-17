class Xylophone extends PercussionFamily {
    public Xylophone(double price) {
        super(price);
    }

    @Override
    public String MakeSound() { return "through resonators"; }

    @Override
    public String howToPlay() {
        return "with two mallets";
    }

    @Override
    public String howToFix() {
        return "replace bars";
    }

    @Override
    public String getPitchType() {
        return "Each bar produces different pitch";
    }
}
