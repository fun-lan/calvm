package src;

import java.util.*;
import static java.util.Map.entry;

public class Instruction {
    public static final byte push = 0;
    public static final byte pop = 1;
    public static final byte dup = 2;
    public static final byte flip = 3;
    public static final byte add = 4;
    public static final byte sub = 5;
    public static final byte mul = 6;
    public static final byte div = 7;
    public static final byte print = 8;
    public static final byte end = 9;

    public static Map<String, Byte> map = Map.ofEntries(entry("push", push), entry("pop", pop), entry("dup", dup),
            entry("flip", flip), entry("add", add), entry("sub", sub), entry("mul", mul), entry("div", div),
            entry("print", print), entry("end", end));
}
