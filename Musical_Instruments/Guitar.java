class Guitar extends StringFamily {
    public Guitar(double price) {
        super(price);
    }

    @Override
    public String MakeSound() { return "vibrating strings"; }

    @Override
    public String howToPlay() {
        return "by strumming the strings";
    }

    @Override
    public String howToFix() {
        return "replace the strings";
    }

    @Override
    public String getPitchType() {
        return "Low to high pitch";
    }
}
