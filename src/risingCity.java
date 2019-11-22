import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main executing class for buildings.
 * Uses Red-Black tree and Min-Heap
 * @author Akib
 */
public class risingCity {
    private static String INPUT_FILE;
    private static final String OUT_FILE = "output_file.txt";
    private Rbt tree = new Rbt();
    private MinHeap heap = new MinHeap();
    private FileWriter fileWriter;
    private HeapNode currentBuilding =null;//Current building being executed
    private int t = 0;//Global time counter
    private int currentSlotEndTime = 0;//Time at which the 5s slot will end
    private int currentBuildingCompletionTime = 0;//Time at which the current building being executed will end if continued indefinitely
    private boolean debug = false;//set true for console outputs

    /**
     * Accept input file name as argument
     * @param args
     */
    public static void main(String[] args) {
        INPUT_FILE = args[0];
        risingCity city = new risingCity();
        city.begin();
    }

    /**
     * Main risingCity logic is written in this method.
     * -Read commands for each line and calls the corresponding method
     * -Each command is processed only when the Global time is equal to
     * the command execution time
     * -In a unit of time, command will be processed and current building
     * will be updated, if needed.
     *
     */
    private void begin() {
        BufferedReader br = null;
        FileReader fr = null;

        try {
            URL path = ClassLoader.getSystemResource(INPUT_FILE);
            fr = new FileReader(new File(path.toURI()));
            br = new BufferedReader(fr);

            fileWriter = new FileWriter(OUT_FILE);

            String sCurrentLine;
            String[] params;

            while ((sCurrentLine = br.readLine()) != null) {
                if (debug) System.out.println("Time:" + t);
                if (debug) System.out.println(sCurrentLine);

                //Sample Input: "13: PrintBuilding(10,300)"
                Pattern p = Pattern.compile("(^\\d+): ([a-zA-Z]+)\\((.+)\\)");
                Matcher m = p.matcher(sCurrentLine);

                if (m.find()) {
                    params = m.group(3).split(",");
                    int cmdExecTime = Integer.parseInt(m.group(1));//Time at which next command will be executed

                    while (cmdExecTime != t){//If not global time, update current building and timings
                        dispatchOrUpdateBuilding();
                        incrementTime();
                        if (debug) System.out.println("Time:" + t);
                    }

                    if (debug) System.out.println("Processing:" + m.group(1) +","+m.group(2));

                    switch (m.group(2)) {
                        case "Insert": {
                            insertBuilding(params);
                            break;
                        }
                        case "Print":
                        case "PrintBuilding": {
                            printBuilding(params);
                            break;
                        }
                    }
                }
                dispatchOrUpdateBuilding();
                incrementTime();
            }
            executeRemainingBuildings();//Case when all lines have been read but buildings are still waiting to be executed
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                fileWriter.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * When all commands are processed but buildings still remain
     * @throws IOException
     */
    private void executeRemainingBuildings() throws IOException {
        while (currentBuilding != null){
            if (debug) System.out.println("Time:" + t);
            dispatchOrUpdateBuilding();
            incrementTime();
        }
    }

    /**
     * Increments both global time and current building's executed time
     */
    private void incrementTime() {
        t++;
        if (currentBuilding != null) {
            currentBuilding.key = currentBuilding.key + 1;
        }
    }

    /**
     * In heap, the keys are executed times of all buildings. So, extract min will
     * output the building that will be of least executed time. This building will run
     * upto 5 days or upto it's total execution time if it lies in current slot.
     * Otherwise, we will re-insert the building in heap at the end of current slot.
     * @throws IOException
     */
    private void dispatchOrUpdateBuilding() throws IOException {
        if (currentBuilding == null) {
            if (heap.isEmpty()) {
                if (debug) System.out.println("No building to dispatch!");
            }
            else {
                currentBuilding = heap.extractMin();
                currentSlotEndTime = t+5;
                currentBuildingCompletionTime = t + currentBuilding.rbNode.totalTime - currentBuilding.key;
                if (debug) System.out.println("Dispatched Building:"+ currentBuilding.rbNode.key+" at time:"+t);
            }
        }
        else {
            if (currentBuildingCompletionTime <= currentSlotEndTime){
                if (t == currentBuildingCompletionTime){
                    //Building completed, so remove from tree and reset current building fields
                    if (debug) System.out.println("Building Completed:"+ currentBuilding.rbNode.key+" at time"+t);
                    printBuildingInFormat(tree.search(currentBuilding.rbNode.key), t);
                    tree.delete(currentBuilding.rbNode.key);
                    currentBuilding = null;
                    currentSlotEndTime = 0;
                    currentBuildingCompletionTime = 0;
                    dispatchOrUpdateBuilding();//recur to dispatch next building as processor is idle
                }
            }
            else {
                //Slot ends
                if (t == currentSlotEndTime){
                    //re-insert building in heap and reset current building fields
                    heap.insert(currentBuilding);
                    currentBuilding = null;
                    currentSlotEndTime = 0;
                    currentBuildingCompletionTime = 0;
                    dispatchOrUpdateBuilding();//recur to dispatch next building as processor is idle
                }
            }
        }
    }

    /**
     * Executes PrintBuilding Command
     * if command of form PrintBuilding(buildingNum), search for buildingNum.
     * If found, print in format, else print empty tuple if commmand of form
     * PrintBuilding(buildingNum1, buildingNum2), searches for buildingNum in given range.
     * @param params
     * @throws IOException
     */
    private void printBuilding(String[] params) throws IOException {
        if (params.length == 1){
            int buildingNum = Integer.parseInt(params[0]);
            RbNode rbNode = tree.search(buildingNum);
            if (rbNode == null) {
                printEmpty();
            }
            else {
                printBuildingInFormat(rbNode);
            }
        }
        else {
            int building1 = Integer.parseInt(params[0]);
            int building2 = Integer.parseInt(params[1]);
            List<RbNode> list = tree.searchInRange(building1, building2);//Search in tree

            if (!list.isEmpty()){
                StringBuilder sb = new StringBuilder();
                for (RbNode node: list){
                    sb.append("("+node.key+","+node.heapNode.key+","+node.totalTime + "),");
                }
                sb.deleteCharAt(sb.length()-1);//remove last comma
                sb.append("\n");
                fileWriter.write(sb.toString());
            }
            else {
                printEmpty();
            }
        }
    }

    private void printEmpty() throws IOException {
        fileWriter.write("(0,0,0)\n");
    }

    /**
     * If the building to be printed is being executed, then the executed time needs to be fetched
     * from current building. Else heap node pointer to the red-black node will give the executed time
     * @param node
     * @throws IOException
     */
    private void printBuildingInFormat(RbNode node) throws IOException {
        if (node.key == currentBuilding.rbNode.key) {
            fileWriter.write("("+ node.key+"," + currentBuilding.key+"," + node.totalTime + ")\n");
        } else {
            fileWriter.write("(" + node.key + "," + node.heapNode.key + "," + node.totalTime + ")\n");
        }
    }

    /**
     * Process insert Building command.
     * Create red-black and min-heap nodes and point to each other
     * Then insert in tree and heap
     * @param params
     */
    private void insertBuilding(String[] params) {
        int num = Integer.parseInt(params[0]);
        int tTime = Integer.parseInt(params[1]);
        if (debug) System.out.println("Inserting buildingNum:"+num+", total time:"+tTime);
        RbNode rbNode = new RbNode(num);
        rbNode.totalTime = tTime;
        HeapNode heapNode = new HeapNode(0);
        rbNode.heapNode = heapNode;
        heapNode.rbNode = rbNode;
        tree.insertNode(rbNode);
        heap.insert(heapNode);
    }

    private void printBuildingInFormat(RbNode node, int finishDay) throws IOException {
        fileWriter.write("("+ node.key+"," + finishDay+")\n");
    }
}