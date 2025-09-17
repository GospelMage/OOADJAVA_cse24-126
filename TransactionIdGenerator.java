import java.util.concurrent.atomic.AtomicInteger;

public class TransactionIdGenerator {
    private static final AtomicInteger GEN = new AtomicInteger(1);
    public static int nextId() { return GEN.getAndIncrement(); }
}
