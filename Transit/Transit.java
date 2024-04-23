import java.util.ArrayList;
/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Name: Srotriyo Sengupta; Net Id: ss3414; Email: ss3414@rutgers.edu
 * 
 */
public class Transit 
{
    /**
     * Makes a layered linked list representing the given arrays of train stations, bus
     * stops, and walking locations. Each layer begins with a location of 0, even though
     * the arrays don't contain the value 0.
     * 
     * @param trainStations Int array listing all the train stations
     * @param busStops Int array listing all the bus stops
     * @param locations Int array listing all the walking locations (always increments by 1)
     * @return The zero node in the train layer of the final layered linked list
     */
    public static TNode makeList(int[] trainStations, int[] busStops, int[] locations) 
    {
        TNode head = new TNode(0, null, null);
        TNode ppointer = head;
        for(int stations = 0; stations < trainStations.length; stations++)
        {
            TNode pointer = new TNode(trainStations[stations], null, null);
            ppointer.next = pointer;
            ppointer = ppointer.next; //new Tnode(trainStations[stations], null, null);
        }
        TNode bus_head = new TNode(0, null, null);
        TNode trainpointer = head;
        trainpointer.down = bus_head;
        trainpointer = trainpointer.next;
        ppointer = bus_head;
        for(int buses = 0; buses < busStops.length; buses++)
        {
            TNode pointer = new TNode(busStops[buses], null, null);
            if (trainpointer != null && trainpointer.location == pointer.location)
            {
            trainpointer.down = pointer;
            trainpointer = trainpointer.next;
            }
            ppointer.next = pointer;
            ppointer = ppointer.next;
        }
        TNode loct_head = new TNode(0,null,null);
        ppointer = loct_head;
        TNode buspointer = bus_head;
        buspointer.down = loct_head;
        buspointer = buspointer.next;
        for(int loct = 0; loct < locations.length; loct++)
        {
            TNode pointer = new TNode(locations[loct], null, null);
        if(buspointer != null && buspointer.location == pointer.location)
        {
            buspointer.down = pointer;
            buspointer = buspointer.next;
        }
            ppointer.next = pointer;
            ppointer = ppointer.next;
        } 
        return head;
    }
    // this is the end of makeList
    /**
     * Modifies the given layered list to remove the given train station but NOT its associated
     * bus stop or walking location. Do nothing if the train station doesn't exist
     * 
     * @param trainZero The zero node in the train layer of the given layered list
     * @param station The location of the train station to remove
     */
    public static void removeTrainStation(TNode trainZero, int station) 
    {
        TNode riderrrr = trainZero;
        while(station >= riderrrr.location)
        {
            if (station == riderrrr.next.location)
            {
                riderrrr.next = riderrrr.next.next;
                break;
            }
            riderrrr = riderrrr.next; 
        }
     }
     // this is the end of removeTrainStation
    /**
     * Modifies the given layered list to add a new bus stop at the specified location. Do nothing
     * if there is no corresponding walking location.
     * 
     * @param trainZero The zero node in the train layer of the given layered list
     * @param busStop The location of the bus stop to add
     */
    public static void addBusStop(TNode trainZero, int busStop) 
    {
        TNode addstop = trainZero.down;
        while(busStop > addstop.next.location)
        {
            addstop = addstop.next;
            if(addstop.next == null)
            {
                break;
            }
        }
        if(addstop.next == null)
        {
            TNode x = new TNode (busStop,null,null);
            addstop.next = x;
            TNode y = trainZero.down.down;
            for(int k = 0; k < busStop; k++)
            {
                y = y.next;
            }
            x.down = y;
            // this will connect x and y
        }
        else if(addstop.next.location == busStop)
        {
            return;
        }
        else
        {
            TNode x = new TNode (busStop,addstop.next,null);
            addstop.next = x;
            TNode y = trainZero.down.down;
            for(int k = 0; k < busStop; k++)
            {
                y = y.next;
            }
            x.down = y;
            // this will also connect x and y
        }
    }
    // this is the end of addBusStop
    /**
     * Determines the optimal path to get to a given destination in the walking layer, and 
     * collects all the nodes which are visited in this path into an arraylist. 
     * 
     * @param trainZero The zero node in the train layer of the given layered list
     * @param destination An int representing the destination
     * @return
     */
    public static ArrayList<TNode> bestPath(TNode trainZero, int destination) 
    {
        ArrayList<TNode> fastestRoute =new ArrayList<TNode>();
        //fastestRoute.add(trainZero);
        TNode currrr = trainZero;
        while((currrr.next != null) && (destination >= currrr.next.location))
        {
            if(destination == currrr.next.location)
            {
                    fastestRoute.add(currrr);
                    fastestRoute.add(currrr.next);
                    fastestRoute.add(currrr.next.down);
                    fastestRoute.add(currrr.next.down.down);
                    return fastestRoute;
            }
            else if(destination > currrr.next.location)
            {
                fastestRoute.add(currrr);
                currrr=currrr.next;
            }
        }
        fastestRoute.add(currrr);
        currrr=currrr.down;
        while((currrr.next != null) && (destination >= currrr.next.location))
        {
            if(destination == currrr.next.location)
            {
                fastestRoute.add(currrr);
                fastestRoute.add(currrr.next);
                fastestRoute.add(currrr.next.down);
                return fastestRoute;
            }
            else if(destination > currrr.next.location)
            {
                fastestRoute.add(currrr);
                currrr = currrr.next;
            }
        }
        fastestRoute.add(currrr);
        currrr = currrr.down;
        while(currrr.next != null && destination >= currrr.next.location)
        {
            if(destination >= currrr.next.location)
            {
                fastestRoute.add(currrr);
                currrr = currrr.next;
            }
        }
        fastestRoute.add(currrr);
         return fastestRoute;
    }
    // this is the end of bestPath
    /**
     * Returns a deep copy of the given layered list, which contains exactly the same
     * locations and connections, but every node is a NEW node.
     * 
     * @param trainZero The zero node in the train layer of the given layered list
     * @return
     */
    public static TNode duplicate(TNode trainZero) 
    {
        ArrayList <Integer> a = new ArrayList <>();
        ArrayList <Integer> b = new ArrayList <>();
        ArrayList <Integer> c = new ArrayList <>();
        TNode p = trainZero;
        TNode q = trainZero.down;
        TNode r = trainZero.down.down;
        while(p.next != null)
        {
            a.add(p.next.location);
            p = p.next;
        }
        while(q.next != null)
        {
            b.add(q.next.location);
            q = q.next;
        }
        while(r.next != null)
        {
            c.add(r.next.location);
            r = r.next;
        }
        return makeList(convert(a),convert(b),convert(c));
    }
    public static int[] convert(ArrayList<Integer>a1)
    {    
    int[] array = new int [a1.size()];
    for(int o1 = 0; o1 < a1.size(); o1++)
    {
        array[o1] = a1.get(o1);
    }
    return array;
    }
    // this is the end of duplicate
    /**
     * Modifies the given layered list to add a scooter layer in between the bus and
     * walking layer.
     * 
     * @param trainZero The zero node in the train layer of the given layered list
     * @param scooterStops An int array representing where the scooter stops are located
     */
    public static void addScooter(TNode trainZero, int[] scooterStops) 
    {
        TNode buscurrrr = trainZero.down;
        TNode walkcurrrr = trainZero.down.down;
        TNode walkzeroooo = trainZero.down.down;
        //this will make the layer of the scooter
        TNode scootzeroooo = new TNode(0, null, walkzeroooo);
        TNode scootcurrrr = scootzeroooo;
        for(int k = 0; k < scooterStops.length; k++)
        {
            scootcurrrr.next = new TNode(scooterStops[k], null, null);
            scootcurrrr=scootcurrrr.next;
        }
         //this will rezero scootcurrrr and connects the scooter to walk
        scootcurrrr = scootzeroooo;
        for(int i = 0; i < scooterStops.length; i++)
        {
            walkcurrrr = trainZero.down.down;
            while(walkcurrrr.next != null)
            {
                if (scootcurrrr.location == walkcurrrr.location)
                {
                    scootcurrrr.down = walkcurrrr;
                }
                walkcurrrr = walkcurrrr.next;
            }
            scootcurrrr = scootcurrrr.next;
        }
        //this will rezero scootcurrrr and will connect bus to the scooter
        scootcurrrr = scootzeroooo;
        while(buscurrrr != null)
        {
            scootcurrrr = scootzeroooo;
            for(int j = 0; j < scooterStops.length; j++)
            {
                if (buscurrrr.location == scootcurrrr.location)
                {
                    buscurrrr.down = scootcurrrr;
                }
                scootcurrrr = scootcurrrr.next;
            }
            buscurrrr = buscurrrr.next;
        }
    }
    // this is the end of addScooter
}
// This is the end of the Transit program
