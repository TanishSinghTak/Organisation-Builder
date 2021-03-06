# Organisation-Builder
Hierarchy system to store the list of employees of an organisation using a combination of AVL tree and generic tree (N-array tree).
## Introduction
In this, we maintain the list of employees in a company. For each employee, we store many details associated with it in which two of them are most concerned to us:
* ID of the employee (ID is an unique integer for every employee and no two employees can have same ID).
* The level of the employee (Level denotes where the person stands in the hierarchy). Level 1 denotes the highest post in the company (say the owner), level 2 comes below level 1 and so on.

The other details that we store for an employee related to the generic tree:
* **Boss**: Immediate Boss of the employee.
* **Employees** : List of the immediate employees working under the given employee.
* **Empno**: Number of the immediate employees working under the given employee.

The other details that we store for an employee related to the AVL tree:
* **Parent**: Parent of the employee node in AVL tree.
* **Left**: Left child of the employee node in AVL tree.
* **Right**: Right child of the employee node in AVL tree.
* **Height**: height of the employee node in AVL tree.
* **Balance Factor**: Balance Factor of the employee node in AVL tree.

Only one individual is at level 1 (Owner of the company), however numerous employees can be at level i > 1. Each level i employee reports to a level i-1 employee, who is his or her immediate boss. We can create a chain of employees A', A", A"',... from an employee A, where A works under A', A' works under A", and so on. Each employee in the series A', A", A"',... can be referred to as a boss of A.

## Working
In this we have made two classes:
* **Node** : This is the node for our combinational tree which stores all the information regarding our employee.
* **OrgHierarchy** : This is our combinational tree in which we store the list of our employees. We use the generic tree to maintain the hierarchy system of the organization and AVL tree for efficient operations required to maintain the organization.

We have also implemented various methods which we use to build our organization and manage it. All the methods and their implementations that we have made are:
* Methods for the Node class:
  * **Balance** : Method to update the balance factor of any node according to it's position in AVL tree at any time.
  * **Rotation Methods** : Methods for the different rotations in an AVL tree Single left, Single right, Double left-right, Double right-left.
* Methods for the OrgHierarchy class:
  * **IsEmpty** : Tells whether the organization is empty or not.
  * **Size** : Method to find the number of employees working in an organization.
  * **level** : Method to find the level of a given ID of an employee. We used the normal binary search method in an AVL tree to find the employee with the given ID and get it's level.
  * **hireOwner** : Method to set the owner of the organization.
  * **hireEmployee** : Method to hire an employee with a given ID under an already existing employee of the organization. We have used the algorithm of inserting a node in an AVL tree through various rotations if unbalancing occurs to insert the new employee node. We also update the generic tree by updating the **employees** list of the given boss ID.
  *  **fireEmployee** : We have implemented two versions for this method. In first one we can only fire employees who don't have any employees working under them. In second version we have to transfer all the employees working under the employee that we need to fire to another employee whose ID will be given. We have used the algorithm of deleting a node in an AVL tree through various rotations if unbalancing occurs, to delete the employee node. We also update the generic tree by updating the **employees** list of required nodes.
  *  **boss** : Method to find the boss of a given ID of an employee. We used the normal binary search method in an AVL tree to find the employee with the given ID and get it's boss.
  *  **lowestCommonBoss** : Method to find the common boss of given two ID's with the highest level amongst them. 
  *  **subtree** : Method for putting the subtree rooted at the given node in a stack (According to AVL tree).
  *  **maxlevel** : Method to find the max level in an array of the given node.
  *  **countSortlevel** : Method to sort an array of nodes according to their levels using count sort algorithm.
  *  **countSortID** : Method to count sort an array of nodes according to the digits of the ID's of nodes so that we can use this count sort in radix sort.
  *  **radixSort** : Method to sort an array of nodes according to their ID's using radix sort algorithm.
  *  **toString** : Returns the whole organization rooted at given ID in a String format. The return string contains the employees (ids) level-wise. So, first the input id (root) will appear, then all employees reporting directly under that id, and then all employees directly under these employee ids and so on. Among employees at the same level, the ID's are sorted in an increasing order of the ID's. We have used different methods given above to implement this. First of all we have taken the list of nodes rooted on the given node using subtree and then applied the radix sort and countSortlevel in order to get the final string in an efficient way.

## Conclusion
From all these methods and classes we can store the list of the employees working in an organization and update it easily in an efficient way because we have used the combination of an AVL tree with a Generic tree(N-array tree). From this we can easily implement our methods like hiring and firing an employee in O(logn) time complexity which is very efficient than the case if we only use generic tree to store the list which might take O(n) time or greater than this. 
