package src;

import java.io.*;
import java.nio.*;
import java.util.*;

public class Compiler {

    public static void main(String[] args) throws Exception {
        if (args.length < 1)
            throw new Exception("Souce file not specified");
        writeBytecode(compile(readFile(args[0])));
    }

    private static List<String> readFile(String name) throws FileNotFoundException {
        var lines = new ArrayList<String>();
        for (var scanner = new Scanner(new File(name)); scanner.hasNextLine(); lines.add(scanner.nextLine())) {
        }
        return lines;
    }

    private static List<Byte> compile(List<String> instructions) throws Exception {
        var bytecode = new ArrayList<Byte>();
        for (String instruction : instructions)
            bytecode.addAll(compileInstruction(instruction));
        return bytecode;
    }

    private static List<Byte> compileInstruction(String instruction) throws Exception {
        var out = new ArrayList<Byte>();
        String[] ops = instruction.split(" "); // [ "push", "42" ]
        String opcode = ops[0];

        if (!Instruction.map.containsKey(opcode))
            throw new Exception("Unknown opcode: " + opcode);

        out.add(Instruction.map.get(opcode));

        if (opcode.equals("push")) { // write int as bytes
            int operand = Integer.parseInt(ops[1]);
            var buf = ByteBuffer.allocate(4).putInt(operand).array();
            for (byte b : buf)
                out.add(b);
        }

        return out;
    }

    private static void writeBytecode(List<Byte> bytecode) throws FileNotFoundException, IOException {
        var writer = new FileOutputStream("out.bc");
        for (int b : bytecode)
            writer.write(b);
        writer.close();
    }

}