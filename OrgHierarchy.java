// Tree node
class Node {
	public Node boss;//pointer to the boss
	public int ID;// ID of the employee
	public int level;// level of the employee
	public Node parent; // pointer to the parent in the AVL tree
	public Node left; // pointer to the left child in AVL tree
	public Node right;// pointer to the right child in AVL tree
	public int height;// height of the node in the AVL tree
	public int balanceFactor;// balance factor of the node
	public Node[] employees; // list of the employees just under the given node
	public int empno; // number of the employees just under the given node

	//constructor
	public Node(){
		Node[] emplist = new Node[1];
		this.employees = emplist;
		empno =0;
	}

	//method to update the height and balance factor of a node
	public void balance(){
		//no child case
		if(this.left == null && this.right == null){
			this.height = 1;
			this.balanceFactor =0;
		}
		// single right child case
		else if(this.left == null){
			this.height = 1 + this.right.height;
			this.balanceFactor = this.right.height;
		}
		//single left child case
		else if(this.right == null){
			this.height = 1 + this.left.height;
			this.balanceFactor = - this.left.height;
		}
		//single left child case
		else{
			this.height = 1 + Math.max(this.left.height, this.right.height);
			this.balanceFactor = this.right.height - this.left.height;
		}
	}

	//method of single left rotation for AVL tree
	public Node SingleLeftRotation(){
		Node B = this.right;
		this.right = B.left;
		if(this.right != null) {
			this.right.parent = this;
		}
		B.left = this;
		B.parent = this.parent;
		//updating height and balance factor of this
		this.balance();
		//updating height and balance factor of B
		B.balance();

		if(B.parent!= null){
			if(B.parent.right== this){
				B.parent.right = B;
			}
			else{
				B.parent.left = B;
			}
		}
		this.parent = B;
		return B;
	}

	//method of single right rotation for AVL tree
	public Node SingleRightRotation(){
		Node B = this.left;
		this.left = B.right;
		if(this.left != null) {
			this.left.parent = this;
		}
		B.right = this;
		B.parent = this.parent;
		//updating height and balance factor of this
		this.balance();
		//updating height and balance factor of B
		B.balance();

		if(this.parent!= null) {
			if (B.parent.left == this) {
				this.parent.left = B;
			} else {
				this.parent.right = B;
			}
		}
		this.parent = B;
		return B;
	}

	//method of double left-right rotation for AVL tree
	public Node DoubleLeftRightRotation(){
		Node y = this.left;
		Node z = y.right;
		y.right = z.left;
		if(z.left!=null){
			z.left.parent = y;
		}
		z.left = y;
		z.parent = this;
		this.left = z;
		//updating height and balance factor of y
		y.balance();
		//updating height and balance factor of z
		z.balance();
		//updating height and balance factor of this
		this.balance();
		y.parent = z;
		return this.SingleRightRotation();
	}

	//method of double right-left rotation for AVL tree
	public Node DoubleRightLeftRotation(){
		Node y = this.right;
		Node z = y.left;
		y.left = z.right;
		if(z.right !=null){
			z.right.parent = y;
		}
		z.right = y;
		z.parent = this;
		this.right = z;
		//updating height and balance factor of y
		y.balance();
		//updating height and balance factor of z
		z.balance();
		//updating height and balance factor of this
		this.balance();
		y.parent = z;
		return this.SingleLeftRotation();
	}
}

public class OrgHierarchy implements OrgHierarchyInterface{

//root node
Node root;//pointer to root node of the AVL tree
Node owner;//pointer to owner of the organisation
int size;//total no. of the employees

//constructor of the organisation
public OrgHierarchy(){
	this.size = 0;
}

//method to find if the organisation is empty or nat
public boolean isEmpty(){
	//your implementation
	return this.root == null;
} 

//method to find the no. of employees in organisation
public int size(){
	//your implementation
	return size;
}

//method to find the level of given id
public int level(int id) throws IllegalIDException {
	//your implementation
	// throwing error if organisation is empty
	if (this.root == null) {
		throw new IllegalIDException("Organisation is empty");
	} else {
		// finding the node with the ID
		Node curr = this.root;
		while (curr != null) {
			if (curr.ID == id) {
				return curr.level;
			} else if (id < curr.ID) {
				curr = curr.left;
			}
			else{
				curr = curr.right;
			}
		}
	}
	//throwing error if we can't find the node
	throw new IllegalIDException("No employee with this ID exist");
}

//method to hire the owner of the company
public void hireOwner(int id) throws NotEmptyException{
	//your implementation
	if(this.owner != null) {
		//throwing error if the owner already exist
		throw new NotEmptyException("Organisation is not empty, Owner already exists");
	}
	else {
		//creating node for the owner
		Node owner = new Node();
		owner.ID = id;
		owner.boss = null;
		owner.level = 1;
		owner.parent = null;
		owner.left = null;
		owner.right = null;
		owner.height = 1;
		owner.balanceFactor = 0;
		this.root = owner;
		this.size += 1;
		this.owner = owner;
	}
}

//method to hire employee
public void hireEmployee(int id, int bossid) throws IllegalIDException{
	//your implementation
	if (this.root == null) {
		throw new IllegalIDException("Organisation is empty");
	} else {
		// finding boss node in tree
		Node boss = this.root;
		while (boss != null) {
			if (boss.ID == bossid) {
				break;
			} else if (bossid < boss.ID) {
				boss = boss.left;
			}
			else{
				boss = boss.right;
			}
		}
		// throwing error if boss doesn't exist
		if(boss == null){
			throw new IllegalIDException("boss doesn't exist");
		}
		//finding employee node
		Node parent = new Node(); // node whose child we have to assign our new employee
		Node curr = this.root;
		while (curr != null) {
			if (curr.ID == id) {
				break;
			} else if (id < curr.ID) {
				parent = curr;
				curr = curr.left;
			}
			else{
				parent = curr;
				curr = curr.right;
			}
		}
		// throwing error if employee already exists
		if(curr != null){
			throw new IllegalIDException("employee already exist");
		}
		// creating new node for the employee
		Node newemp = new Node();
		newemp.level = boss.level + 1;
		newemp.ID = id;
		newemp.parent = parent;
		newemp.boss = boss;
		newemp.height = 1;
		newemp.balanceFactor =0;
		newemp.left = null;
		newemp.right = null;
		//adding new employee in the employees list of it's boss
		//case:- if the employee list of the boss node is full
		//increase its length by doubling it
		if(boss.empno == boss.employees.length){
			Node[] newemplist = new Node[2* boss.employees.length];
			for(int i = 0; i < boss.employees.length;i++){
				newemplist[i] = boss.employees[i];
			}
			boss.employees = newemplist;
		}
		boss.employees[boss.empno] = newemp;
		boss.empno+=1;
		//assigning the node as the child of its parent node in AVL tree
		if (id > parent.ID) {
			parent.right = newemp;
		} else {
			parent.left = newemp;
		}

		// updating the attributes of all the ancestors of new node
		Node child = newemp;
		Node parentnd = parent;
		String path = "";
		int f=0;
		while (parentnd != null) {
			parentnd.balance();
			//keeping record of the path covered by the node
			if (parentnd.right == child) {
				path += "r";
			} else {
				path += "l";
			}
			if (parentnd.balanceFactor < -1 || parentnd.balanceFactor > 1) {
				f=1;
				break;
			} else {
				child = parentnd;
				parentnd = parentnd.parent;
			}
		}
		//case- no unbalanced node found
		if(f==0){
			//just chill
		}
		//if we find an unbalanced node
		else{
			Node unbal = parentnd;
			// Applying rotations after inserting new employee accordingly if tree gets unbalanced
			Node newroot = new Node();
			if (unbal != null && (unbal.balanceFactor >1  || unbal.balanceFactor < -1)) {
				// balancing tree
				int length = path.length() - 1;
				char dir1 = path.charAt(length);
				char dir2 = path.charAt(length - 1);
				// different cases of rotations
				if (dir1 == 'r' && dir2 == 'r') {
					newroot = unbal.SingleLeftRotation();
				} else if (dir1 == 'l' && dir2 == 'l') {
					newroot = unbal.SingleRightRotation();
				} else if (dir1 == 'l' && dir2 == 'r') {
					newroot = unbal.DoubleLeftRightRotation();
				} else if (dir1 == 'r' && dir2 == 'l') {
					newroot = unbal.DoubleRightLeftRotation();
				}
			}
			// updating values of remaining ancestor nodes
			if (unbal == this.root) {
				this.root = newroot;
			} else {
				while (newroot.parent != null) {
					newroot.parent.height = 1 + Math.max(newroot.parent.left.height, newroot.parent.right.height);
					newroot.parent.balanceFactor = newroot.parent.right.height - newroot.parent.left.height;
					newroot = newroot.parent;
				}
			}
		}
		//increasing size of organisation
		this.size +=1;
	}
}

//method to fire employee with no employee working under him
public void fireEmployee(int id) throws IllegalIDException{
	//your implementation
	// throwing error if organisation is empty
	if(this.root == null){
		throw new IllegalIDException("Organisation is empty");
	}
	else {
		//finding employee
		Node emp = this.root;
		System.out.println(emp);
		while (emp != null) {
			if (emp.ID == id) {
				break;
			} else if (id < emp.ID) {
				emp = emp.left;
			} else {
				emp = emp.right;
			}
		}
		// throwing error if employee doesn't exist
		if (emp == null) {
			throw new IllegalIDException("employee doesn't exist");
		}
		//throwing error if employees are working under the given employee
		if (emp.empno != 0) {
			throw new IllegalIDException("not a leaf node employee");
		}
		// removing employee from its boss's employee list
		if(emp.boss!=null) {
			Node[] newlist = new Node[emp.boss.employees.length];
			for (int i = 0; i < emp.boss.empno; i++) {
				int j=0;
				if (emp.boss.employees[i].ID != id) {
					//selecting employees which have ID different from given ID
					newlist[j] = emp.boss.employees[i];
					j++;
				}
			}
			emp.boss.employees = newlist;
			emp.boss.empno -= 1;
		}

		//deleting employee from tree
		Node unbal;
		//case- if the node to be deleted has no child-just delete it
		if (emp.left == null && emp.right == null) {
			unbal = emp.parent;
			if (unbal.left == emp) {
				unbal.left = null;
			} else {
				unbal.right = null;
			}
			emp.parent = null;
			emp.boss = null;
		}
		//case- if the node to be deleted has only right child-
		// assign the right child as child to the parent of the given node
		else if (emp.left == null) {
			unbal = emp.parent;
			if (unbal.left == emp) {
				unbal.left = emp.right;
			} else {
				unbal.right = emp.right;
			}
			emp.right.parent = unbal;
			emp.right = null;
			emp.parent = null;
			emp.boss = null;
		}
		//case- if the node to be deleted has only left child-
		// assign the left child as child to the parent of the given node
		else if (emp.right == null) {
			unbal = emp.parent;
			if (unbal.left == emp) {
				unbal.left = emp.left;
			} else {
				unbal.right = emp.left;
			}
			emp.left.parent = unbal;
			emp.left = null;
			emp.parent = null;
			emp.boss = null;
		}
		//case- if the node to be deleted has both left and right child-
		// find the inorder successor of the given node and swap from it
		else {
			//finding inorder successor
			Node insucc = emp.right;
			while (insucc.left != null) {
				insucc = insucc.left;
			}
			unbal = insucc.parent;
			//swap inorder successor with the node
			if (unbal.left == insucc) {
				unbal.left = insucc.right;
			} else {
				unbal.right = insucc.right;
			}
			if(insucc.right!=null) {
				insucc.right.parent = unbal;
			}
			insucc.left = emp.left;
			insucc.right = emp.right;
			insucc.parent = emp.parent;
			if (emp.parent != null) {
				if (emp.parent.left == emp) {
					emp.parent.left = insucc;
				} else {
					emp.parent.right = insucc;
				}
			}
			emp.left.parent = insucc;
			emp.right.parent = insucc;
			emp.left = null;
			emp.right = null;
			emp.parent = null;
			emp.boss = null;
		}
		int f = 0;

		//updating the attributes of ancestor
		while (unbal != null) {
			unbal.balance();
			//break loop if we find any unbalanced node
			if (unbal.balanceFactor < -1 || unbal.balanceFactor > 1) {
				f = 1;
				break;
			} else {
				unbal = unbal.parent;
			}
		}
		//case- no unbalanced node found
		if (f == 0) {
			//just chill
		}
		//case- if any unbalanced node found
		else {
			// Applying rotations after deleting accordingly if tree gets unbalanced
			Node newroot = new Node();
			// different cases of rotations applied according to the unbalanced tree
			if(unbal.balanceFactor < -1){

				if(unbal.left.balanceFactor>0){
					newroot = unbal.DoubleLeftRightRotation();
				}
				else{
					newroot = unbal.SingleLeftRotation();
				}
			}
			else if(unbal.balanceFactor > 1){
				if(unbal.right.balanceFactor<0){
					newroot = unbal.DoubleRightLeftRotation();
				}
				else{
					newroot = unbal.SingleRightRotation();
				}
			}

			// updating values of ancestor nodes
			if (unbal == this.root) {
				this.root = newroot;
			} else {
				while (newroot.parent != null) {
					newroot.parent.height = 1 + Math.max(newroot.parent.left.height, newroot.parent.right.height);
					newroot.parent.balanceFactor = newroot.parent.right.height - newroot.parent.left.height;
					newroot = newroot.parent;
				}
			}
		}
	}
	//decreasing size of organisation
	this.size-=1;
}

//method to fire employee with employees working under him
public void fireEmployee(int id, int manageid) throws IllegalIDException{
	//your implementation
	//throwing error if the organisation is empty
	if(this.root == null){
		throw new IllegalIDException("Organisation is empty");
	}
	else {
		//finding employee
		Node emp = this.root;
		while (emp != null) {
			if (emp.ID == id) {
				break;
			} else if (id < emp.ID) {
				emp = emp.left;
			} else {
				emp = emp.right;
			}
		}
		// throwing error if employee doesn't exist
		if (emp == null) {
			throw new IllegalIDException("employee doesn't exist");
		}
		//finding manage employee
		Node manageemp = this.root;
		while (manageemp != null) {
			if (manageemp.ID == manageid) {
				break;
			} else if (manageid < manageemp.ID) {
				manageemp = manageemp.left;
			} else {
				manageemp = manageemp.right;
			}
		}
		// throwing error if manage employee doesn't exist
		if (manageemp == null) {
			throw new IllegalIDException("manage employee doesn't exist");
		}
		//throwing error if the employee needed to fire is owner itself
		if(emp.level ==1){
			throw new IllegalIDException("Can't fire owner");
		}
		// removing employee from its boss's employee list
		if(emp.boss!=null) {
			Node[] newlist = new Node[emp.boss.employees.length];
			int j=0;
			for (int i = 0; i < emp.boss.empno; i++) {
				//taking all employees in the list except the one which we have to fire
				if (emp.boss.employees[i].ID != id) {
					newlist[j] = emp.boss.employees[i];
					j++;
				}
			}
			emp.boss.employees = newlist;
			emp.boss.empno -= 1;
		}
		//giving employees of given employee to new employee
		Node[] newlist = new Node[emp.employees.length + manageemp.employees.length];
		for(int i =0; i< manageemp.empno; i++){
			newlist[i] = manageemp.employees[i];
		}
		for(int i = 0 ; i< emp.empno; i++){
			newlist[i+manageemp.empno] = emp.employees[i];
		}
		manageemp.employees = newlist;
		manageemp.empno += emp.empno;
		//assigning manage id as boss to each new employee under it
		for(int i = 0; i< emp.empno; i++){
			emp.employees[i].boss = manageemp;
		}
		//deleting employee from tree
		Node unbal;
		//case- if the node to be deleted has no child-just delete it
		if (emp.left == null && emp.right == null) {
			unbal = emp.parent;
			if (unbal.left == emp) {
				unbal.left = null;
			} else {
				unbal.right = null;
			}
			emp.parent = null;
			emp.boss = null;
			emp.employees = null;
		}
		//case- if the node to be deleted has only right child-
		// assign the right child as child to the parent of the given node
		else if (emp.left == null) {
			unbal = emp.parent;
			if (unbal.left == emp) {
				unbal.left = emp.right;
			} else {
				unbal.right = emp.right;
			}
			emp.right.parent = unbal;
			emp.right = null;
			emp.parent = null;
			emp.boss = null;
			emp.employees = null;
		}
		//case- if the node to be deleted has only leftt child-
		// assign the left child as child to the parent of the given node
		else if (emp.right == null) {
			unbal = emp.parent;
			if (unbal.left == emp) {
				unbal.left = emp.left;
			} else {
				unbal.right = emp.left;
			}
			emp.left.parent = unbal;
			emp.left = null;
			emp.parent = null;
			emp.boss = null;
			emp.employees = null;
		}
		//case- if the node to be deleted has both left and right child-
		// find the inorder successor of the given node and swap from it
		else {
			//finding inorder successor
			Node insucc = emp.right;
			while (insucc.left != null) {
				insucc = insucc.left;
			}
			unbal = insucc.parent;
			//swap inorder successor with the node
			if (unbal.left == insucc) {
				unbal.left = insucc.right;
			} else {
				unbal.right = insucc.right;
			}
			if(insucc.right != null) {
				insucc.right.parent = unbal;
			}
			insucc.left = emp.left;
			insucc.right = emp.right;
			insucc.parent = emp.parent;
			if (emp.parent != null) {
				if (emp.parent.left == emp) {
					emp.parent.left = insucc;
				} else {
					emp.parent.right = insucc;
				}
			}
			emp.left.parent = insucc;
			emp.right.parent = insucc;
			emp.left = null;
			emp.right = null;
			emp.parent = null;
			emp.boss = null;
			emp.employees = null;
		}
		int f = 0;

		//updating the attributes of the ancestors
		while (unbal != null) {
			unbal.balance();
			//break loop if we found any unbalanced node
			if (unbal.balanceFactor < -1 || unbal.balanceFactor > 1) {
				f = 1;
				break;
			} else {
				unbal = unbal.parent;
			}
		}
		//case- no unbalanced node is found
		if (f == 0) {
			//just chill
		} else {
			// Applying rotations after deleting accordingly if tree gets unbalanced
			Node newroot = new Node();
			//different cases of rotation according to the different type of unbalanced trees
			if(unbal.balanceFactor < -1){
				if(unbal.left.balanceFactor>0){
					newroot = unbal.DoubleLeftRightRotation();
				}
				else{
					newroot = unbal.SingleLeftRotation();
				}
			}
			else if(unbal.balanceFactor > 1){
				if(unbal.right.balanceFactor<0){
					newroot = unbal.DoubleRightLeftRotation();
				}
				else{
					newroot = unbal.SingleRightRotation();
				}
			}

			// updating values of ancestor nodes
			if (unbal == this.root) {
				this.root = newroot;
			} else {
				while (newroot.parent != null) {
					newroot.parent.height = 1 + Math.max(newroot.parent.left.height, newroot.parent.right.height);
					newroot.parent.balanceFactor = newroot.parent.right.height - newroot.parent.left.height;
					newroot = newroot.parent;
				}
			}
		}
	}
	//decreasing the size of the organisation
	this.size-=1;
} 

//method to find the boss of given ID
public int boss(int id) throws IllegalIDException{
	//your implementation
	//throwing error if the organisation is empty
	if (this.root == null) {
		throw new IllegalIDException("Organisation is empty");
	} else {
		//finding the given node
		Node curr = this.root;
		while (curr != null) {
			//case - node is found
			if (curr.ID == id) {
				//if the node is owner then return -1
				if(curr.level == 1){
					return -1;
				}
				// else returning the boss of the given node
				else {
					return curr.boss.ID;
				}
			} else if (id < curr.ID) {
				curr = curr.left;
			}
			else{
				curr = curr.right;
			}
		}
	}
	//throwing error if we can't find the employee
	throw new IllegalIDException("No employee with this ID exist");
}

//method to find the lowest common boss of 2 given ID's
public int lowestCommonBoss(int id1, int id2) throws IllegalIDException {
	//your implementation
	//throwing error if the organisation is empty
	if (this.root == null) {
		throw new IllegalIDException("Organisation is empty");
	}
	//if any of the ID is of owner then return -1
	else if(id1 == owner.ID || id2 == owner.ID){
		return -1;
	}
	else{
		//finding node 1 with id1
		Node node1 = this.root;
		while (node1 != null) {
			if (node1.ID == id1) {
				break;
			} else if (id1 < node1.ID) {
				node1 = node1.left;
			}
			else{
				node1 = node1.right;
			}
		}
		//throwing error if we can't find the node 1
		if(node1 == null){
			throw new IllegalIDException("id1 doesn't exist");
		}

		//finding node 1 with id1
		Node node2 = this.root;
		while (node2 != null) {
			if (node2.ID == id2) {
				break;
			} else if (id2 < node2.ID) {
				node2 = node2.left;
			}
			else{
				node2 = node2.right;
			}
		}
		//throwing error if we can't find the node 2
		if(node2 == null){
			throw new IllegalIDException("id2 doesn't exist");
		}

		//creating stack to store ancestors of node 1
		MyStack stack1 = new MyStack();
		while(node1 != null){
			stack1.push(node1);
			node1 = node1.boss;
		}
		//creating stack to store ancestors of the node 2
		MyStack stack2 = new MyStack();
		while(node2 != null){
			stack2.push(node2);
			node2 = node2.boss;
		}
		Node commonboss = new Node();
		//finding the last common ancestor from the 2 stacks
		try {
			while (stack1.top() == stack2.top()) {
				commonboss = (Node) stack1.pop();
				stack2.pop();
			}
		}
		catch (EmptyStackException e){
			e.printStackTrace();
		}
		return commonboss.ID;
	}
}

/* Ignore */
// method for inorder traversal of tree
//public MyStack Inorder(Node n,MyStack stack){
//	if(n == null){
//		return stack;
//	}
//	Inorder(n.left, stack);
//	stack.push(new Triplet<>(n.ID,n.level,n));
//	Inorder(n.right, stack);
//	return stack;
//}
// /* Ignore */

	// method for putting the subtree rooted at the given node in the stack
public MyStack subtree(Node n,MyStack stack){
	//base-case
	if(n == null){
		return stack;
	}
	//using recursive approach to store the subtree
	stack.push(n);
	for(int i =0; i<n.empno ; i++){
		subtree(n.employees[i], stack);
	}
	return stack;
}

//method to find the max level in an array of the given node
public static int Maxlevel( Node level[]) {
	int max = level[0].level;
	for (int i = 1; i < level.length; i++) {
		if (level[i].level > max) {
			max = level[i].level;
		}
	}
	return max;
}

// count sort of an array of nodes according to the levels of nodes
public static  Node[] countSortlevel( Node arr[]) {
	//getting the max level
	int max = Maxlevel(arr);
	//creating count array with max+1 zeroes
	int[] count = new int[max+1];
	for(int i =0; i <= max ; i++){
		count[i] =0;
	}
	//assigning frequency of every integer in the
	// given array to the respective index in count array
	for(int i =0; i < arr.length; i++){
		count[arr[i].level] += 1;
	}
	//computing cumulative frequency
	for(int i =1; i <= max ; i++){
		count[i] += count[i-1];
	}
	// assigning cumulative frequencies one index ahead
	// for stable sort
	for(int i =max; i >= 1 ; i--){
		count[i] = count[i-1];
	}
	//creating new sorted array
	Node[] sortedarr = new Node[arr.length];
	for(int i =0; i < arr.length; i++){
		sortedarr[count[arr[i].level]] = arr[i];
		count[arr[i].level] += 1;
	}
	return sortedarr;
}

// count sort of an array of nodes according to the digits of the ID's of nodes
// this count sort is for use in radix sort
public static void countSortID( Node arr[], int digit) {
	//getting the max ID
	int max = arr[0].ID;
	for (int i = 1; i < arr.length; i++) {
		if (arr[i].ID > max) {
			max = arr[i].ID;
		}
	}
	//creating count array and since we have only 10 digits
	// from 0,1...9 we have size of count array = 10
	int[] count = new int[10];
	for(int i =0; i <= 9 ; i++){
		count[i] =0;
	}
	//assigning frequency of every digit at specified place in number
	//in the given array to the respective index in count array
	for(int i =0; i < arr.length; i++){
		count[(arr[i].ID / digit) % 10] += 1;
	}
	//computing cumulative frequency
	for(int i =1; i <= 9 ; i++){
		count[i] += count[i-1];
	}
	// assigning cumulative frequencies one index ahead
	// for stable sort
	for(int i =9; i >= 1 ; i--){
		count[i] = count[i-1];
	}
	count[0] =0;

	//creating new sorted array
	Node[] sortedarr = new Node[arr.length];
	for(int i =0; i < arr.length; i++){
		sortedarr[count[(arr[i].ID /digit)% 10]] = arr[i];
		count[(arr[i].ID / digit) % 10] += 1;
	}
	//assigning values of the sorted array to array
	for (int i = 0; i < arr.length; i++) {
		arr[i] = sortedarr[i];
	}
}

// count sort of an array of nodes according to the ID's of nodes
public static void radixSort( Node arr[]){
	//getting max ID of the array
	int max = arr[0].ID;
	for (int i = 1; i < arr.length; i++) {
		if (arr[i].ID > max) {
			max = arr[i].ID;
		}
	}
	// sorting the array using count sort
	// for different places of digits in the max ID
	for(int digit =1; max/digit > 0; digit*=10){
		countSortID(arr,digit);
	}
}

//method to form the string of all the employees working under the given employee ID in sorted manner
public String toString(int id) throws IllegalIDException {
	//your implementation
	//finding employee
	Node emp = this.root;
	while (emp != null) {
		if (emp.ID == id) {
			break;
		} else if (id < emp.ID) {
			emp = emp.left;
		} else {
			emp = emp.right;
		}
	}
	// throwing error if employee doesn't exist
	if (emp == null) {
		throw new IllegalIDException("employee doesn't exist");
	}
	//creating stack for storing the subtree
	MyStack stack = new MyStack();
	stack = subtree(emp,stack);
	int members = stack.length;
	//creating array to store the subtree members
	Node[] employees = new Node[members];
	try {
		for (int i = 0; i < members; i++) {
			employees[i] = (Node) stack.pop();
		}
	} catch (EmptyStackException e) {
		e.printStackTrace();
	}
	//first sorting the array according to the ID's of the nodes
	radixSort(employees);
	// then sorting the nodes according their level
	Node[] employeeList = countSortlevel(employees);
	//creating final string
	String ans = "";
	for (int i = 0; i < members; i++) {
		ans += Integer.toString(employeeList[i].ID);
		if(i!=members-1){
			ans+=" ";
		}
	}
	return ans;
}
}