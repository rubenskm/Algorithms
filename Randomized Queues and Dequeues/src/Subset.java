public class Subset {
    /*
        % echo A B C D E F G H I | java Subset 3       % echo AA BB BB BB BB BB CC CC | java Subset 8
    C                                              BB
    G                                              AA
    A                                              BB
                                                   CC
    % echo A B C D E F G H I | java Subset 3       BB
    E                                              BB
    F                                              CC
    G                                              BB
     */

    public static void main(String[] args) {
        int size;
        if (args.length > 0) {
            size = Integer.parseInt(args[0]);
        } else {
            System.out.print("need a number\n");
            return;
        }

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int j = 0; j < size; j++) {
            System.out.println(rq.dequeue());
        }
    }
}
