package src;

import java.io.*;
import java.nio.*;
import java.util.*;

public class Runtime {

    public static void main(String[] args) throws Exception {
        if (args.length < 1)
            throw new Exception("Bytecode file not specified");
        new Runtime(args[0]).run();
    }

    /* Internal state. */
    private FileInputStream bytecode;
    private Stack<Integer> stack = new Stack<>();

    private Runtime(String name) throws FileNotFoundException {
        bytecode = new FileInputStream(name);
    }

    private void run() throws Exception {
        int b;
        while ((b = bytecode.read()) != -1) {
            exec(b);
        }
    }

    private void exec(int opcode) throws Exception {
        switch ((byte) opcode) {
            case Instruction.push:
                var buf = ByteBuffer.allocate(4);
                for (int i = 0; i < 4; i++)
                    buf.put((byte) bytecode.read());
                int operand = buf.getInt(0);
                stack.push(operand);
                break;

            case Instruction.pop:
                stack.pop();
                break;

            case Instruction.dup: {
                int i = stack.peek();
                stack.push(i);
                break;
            }

            case Instruction.flip: {
                int x = stack.pop();
                int y = stack.pop();
                stack.push(x);
                stack.push(y);
                break;
            }

            case Instruction.add: {
                int y = stack.pop();
                int x = stack.pop();
                stack.push(y + x);
                break;
            }

            case Instruction.sub: {
                int y = stack.pop();
                int x = stack.pop();
                stack.push(y - x);
                break;
            }

            case Instruction.mul: {
                int y = stack.pop();
                int x = stack.pop();
                stack.push(y * x);
                break;
            }

            case Instruction.div: {
                int y = stack.pop();
                int x = stack.pop();
                stack.push((int) (y / x));
                break;
            }

            case Instruction.print:
                System.out.println(stack.peek());
                break;

            case Instruction.end:
                System.exit(0);

            default:
                throw new Exception("Unknown opcode: " + opcode);
        }
    }

}
