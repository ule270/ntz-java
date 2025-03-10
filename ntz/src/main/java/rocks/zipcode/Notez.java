package rocks.zipcode;

/**
 * ntz main command.
 */
public final class Notez {

    private FileMap filemap;

    public Notez() {
        this.filemap  = new FileMap();
    }
    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     */
    public static void main(String args[]) {
        boolean _debug = true;
        // for help in handling the command line flags and data!
        if (_debug) {
            System.err.print("Args: [");
            for (String a : args) {
                System.err.print(a+" ");
            }
            System.err.println("]");
        }

        Notez ntzEngine = new Notez();

        ntzEngine.loadDatabase();

        /*
         * You will spend a lot of time right here.
         * instead of loadDemoEntries, you will implement a series
         * of method calls that manipulate the Notez engine.
         * See the first one:
         */
//        ntzEngine.loadDemoEntries();

//        ntzEngine.saveDatabase();

        if (args.length == 0) { // there are no commandline arguments
            //just print the contents of the filemap.
            ntzEngine.printResults();
        } else {
            if (args[0].equals("-r")) {
                ntzEngine.addToCategory("General", args);
            } else if (args[0].equals("-c")) {
                ntzEngine.addToCategory(args[1], args);
            } else if (args[0].equals("-f")){
                ntzEngine.forgetNote(args[1], Integer.parseInt(args[2]) -1);
            } else if (args[0].equals("-e")) {
                ntzEngine.editNote(args[1], Integer.parseInt(args[2]) -1, args[3]);
            }
            ntzEngine.saveDatabase();
//          only saving after being modified
        }
    }

    private void saveDatabase() {
        filemap.save();
    }

    private void loadDatabase() {
        filemap.load();
    }

    public void printResults() {
        System.out.println(this.filemap.toString());
    }

    public void loadDemoEntries() {
        filemap.put("General", new NoteList("The Very first Note"));
        filemap.put("note2", new NoteList("A secret second note"));
        filemap.put("category3", new NoteList("Did you buy bread AND eggs?"));
        filemap.put("anotherNote", new NoteList("Hello from ZipCode!"));
    }
    /*
     * Put all your additional methods that implement commands like forget here...
     */

    private void addToCategory(String string, String[] args) {
//        args.length is out of bounds bc greater than max index;
        if (filemap.containsKey(string)){
            filemap.get(string).add(args[args.length -1]);
        } else {
            filemap.put(string, new NoteList(args[args.length -1]));
            // -1 since they are numbered starting at 1 on the readMe
        }
    }
    private void forgetNote(String string, int index){
        if(filemap.containsKey(string)) {
            filemap.get(string).remove(index);
            if (filemap.get(string).size() == 0) {
                filemap.remove(string);
            }
        }
    }

    private void editNote(String string, int index, String message){
        if(filemap.containsKey(string)) {
            filemap.get(string).set(index, message);
        }
    }
}
