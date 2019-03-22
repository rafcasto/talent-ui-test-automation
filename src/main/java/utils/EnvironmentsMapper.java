package utils;

public enum EnvironmentsMapper {
    QA("QA"),
    DEV("DEV");
    private final String Env;

    EnvironmentsMapper(final String Env) {
        this.Env = Env;
    }

    public String toString() {
        return Env;
    }
}
