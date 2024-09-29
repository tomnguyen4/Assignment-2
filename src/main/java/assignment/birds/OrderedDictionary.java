package assignment.birds;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/birds/DictionaryException.java
     */
    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {         
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current.getData();
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }
    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws birds.DictionaryException
     */
    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        Node newNode = new Node(r);
        if (root.isEmpty()) {
            root = newNode;
            return;
        }

        Node current = root;
        Node parent = null;
        int comparison = 0;

        while (current != null) {
            parent = current;
            comparison = r.getDataKey().compareTo(current.getData().getDataKey());

            if (comparison == 0) {
                // Record with the same key exists
                throw new DictionaryException("A record with the same key already exists.");
            } else if (comparison < 0) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }

        // Insert new node at the correct position
        if (comparison < 0) {
            parent.setLeftChild(newNode);
        } else {
            parent.setRightChild(newNode);
        }
    }

    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws birds.DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
        root = removeNode(root, k);
    }

    private Node removeNode(Node current, DataKey key) throws DictionaryException {
        if (current == null) {
            throw new DictionaryException("No such record key exists.");
        }

        int comparison = key.compareTo(current.getData().getDataKey());

        if (comparison < 0) {
            current.setLeftChild(removeNode(current.getLeftChild(), key));
        } else if (comparison > 0) {
            current.setRightChild(removeNode(current.getRightChild(), key));
        } else {
            // Node to be deleted found
            if (current.getLeftChild() == null && current.getRightChild() == null) {
                return null;
            } else if (current.getLeftChild() == null) {
                return current.getRightChild();
            } else if (current.getRightChild() == null) {
                return current.getLeftChild();
            } else {
                // Node with two children
                BirdRecord smallestValue = findSmallest(current.getRightChild());
                current.setData(smallestValue);
                current.setRightChild(removeNode(current.getRightChild(), smallestValue.getDataKey()));
            }
        }
        return current;
    }

    private BirdRecord findSmallest(Node root) {
        return root.getLeftChild() == null ? root.getData() : findSmallest(root.getLeftChild());
    }

    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException {
        Node current = root;
        Node successor = null;

        while (current != null) {
            int comparison = k.compareTo(current.getData().getDataKey());

            if (comparison < 0) {
                successor = current;
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }

        if (successor == null) {
            throw new DictionaryException("There is no successor for the given record key.");
        }

        return successor.getData();
    }

    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * largest key smaller than k; it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException {
        Node current = root;
        Node predecessor = null;

        while (current != null) {
            int comparison = k.compareTo(current.getData().getDataKey());

            if (comparison > 0) {
                predecessor = current;
                current = current.getRightChild();
            } else {
                current = current.getLeftChild();
            }
        }

        if (predecessor == null) {
            throw new DictionaryException("There is no predecessor for the given record key.");
        }

        return predecessor.getData();
    }

    /**
     * Returns the record with smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public BirdRecord smallest() throws DictionaryException {
        if (root.isEmpty()) {
            throw new DictionaryException("Dictionary is empty.");
        }

        Node current = root;
        while (current.getLeftChild() != null) {
            current = current.getLeftChild();
        }

        return current.getData();
    }

    /*
     * Returns the record with largest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     */
    @Override
    public BirdRecord largest() throws DictionaryException {
        if (root.isEmpty()) {
            throw new DictionaryException("Dictionary is empty.");
        }

        Node current = root;
        while (current.getRightChild() != null) {
            current = current.getRightChild();
        }

        return current.getData();
    }

    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }
}
